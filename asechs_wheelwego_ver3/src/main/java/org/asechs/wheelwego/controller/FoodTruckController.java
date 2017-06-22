package org.asechs.wheelwego.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asechs.wheelwego.model.FoodTruckService;
import org.asechs.wheelwego.model.MypageService;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FoodTruckController {
	@Resource(name="foodTruckServiceImpl")
	private FoodTruckService foodTruckService;
	@Resource(name="mypageServiceImpl2")
	private MypageService mypageService; 
	
	/**
	 * 박다혜
	 * 2017.06.21 (수정 중)
	 * 푸드트럭 - 푸드트럭 명으로 검색하기
	 * ------------------------------------------------------------------
	 * option이 null이라면 최신순 필터링을 적용한다.
	 * option과 현재 페이지 번호, 검색 조건에 해당하는 푸드트럭 리스트를 반환받고
	 * modelAndView 객체에 푸드트럭 리스트를 설정한다.
	 *  또한 페이징시를 대비해 검색조건과 option도 함께 설정한다.
	 * 
	 * 
	 * @param name : 검색조건
	 * @param pageNo : 현재 페이지
	 * @param latitude
	 * @param longitude
	 * @param request
	 * @param option : 필터링 옵션
	 * @return
	 */
	@RequestMapping("searchFoodTruckByName.do")
	public ModelAndView searchFoodTruckByName(String name, String pageNo, String latitude, String longitude,HttpServletRequest request,String option) {
		if(option==null)
			option="ByDate";
		
		ModelAndView modelAndView = new ModelAndView("foodtruck/foodtruck_location_select_list.tiles");		
		ListVO listVO =foodTruckService.filtering(option, name, pageNo,null);
		modelAndView.addObject("pagingList", listVO);
		modelAndView.addObject("name", name);
		modelAndView.addObject("option", option);
		modelAndView.addObject("GPSflag", "false");	
		
		HttpSession session=request.getSession(false);
		if(session != null){
			MemberVO memberVO=(MemberVO)session.getAttribute("memberVO");
			if(memberVO != null){
				modelAndView.addObject("heartWishlist",mypageService.heartWishList( memberVO.getId())); //사용자 id의 단골트럭 목록을 보낸다.
			}
		}
		return modelAndView;
	}
	/**
	 * 황윤상 GPS 기반 푸드트럭수동검색
	 * @param name
	 * @return
	 */
	@RequestMapping("searchFoodTruckByGPS.do")
	public ModelAndView searchFoodTruckByGPS(String latitude, String longitude, String pageNo,String option,HttpServletRequest request) {
		if(option==null)
			option="ByDate";
		TruckVO gpsInfo = new TruckVO();
		
		gpsInfo.setLatitude(Double.parseDouble(latitude));
		gpsInfo.setLongitude(Double.parseDouble(longitude));
		
		ModelAndView modelAndView = new ModelAndView("foodtruck/foodtruck_location_select_list.tiles");
		//ListVO listVO1 = foodTruckService.getFoodTruckListByGPS(pageNo, gpsInfo);
		//System.out.println(listVO1);
		ListVO listVO =foodTruckService.filtering(option,null, pageNo,gpsInfo);
		HttpSession session=request.getSession(false);
		String id=null;
		List<WishlistVO> heartWishList=null;
		if(session != null){
			MemberVO memberVO=(MemberVO)session.getAttribute("memberVO");
			if(memberVO != null){
				id = memberVO.getId();
				heartWishList = mypageService.heartWishList(id);
				modelAndView.addObject("heartWishlist",heartWishList);
			}
		}
		modelAndView.addObject("pagingList", listVO);
		modelAndView.addObject("gpsInfo", gpsInfo);
		modelAndView.addObject("option", option);	
		modelAndView.addObject("GPSflag", "true");	
		return modelAndView;
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭 상세보기 
 	기능설명 : 1. 푸드트럭 번호(foodtruckNo)로 푸드트럭 상세정보를 TruckVO 객체로 받아온다
 			2. 푸드트럭 번호로 작성된 리뷰 리스트를 받아온다(리뷰 리스트 pagingBean 적용)
 			-> return 값은 푸드트럭 detail 페이지로 보낸다
  */
	   @RequestMapping("foodtruck/foodTruckAndMenuDetail.do")
	   public ModelAndView foodTruckAndMenuDetail(String foodtruckNo,String reviewPageNo, String latitude, String longitude, HttpServletRequest request){
	      TruckVO truckDetail = foodTruckService.foodTruckAndMenuDetail(foodtruckNo);
	      String bookingPossible = "no";
	      List<String> foodtruckNumberList = foodTruckService.getFoodtruckNumberList(new TruckVO(Double.parseDouble(latitude), Double.parseDouble(longitude)));
	      for (int i = 0; i < foodtruckNumberList.size(); i++)
	      {
	         if (foodtruckNumberList.get(i).equals(truckDetail.getFoodtruckNumber()))
	         {
	            bookingPossible = "ok";
	            break;
	         }            
	      }
	      ModelAndView mv= new ModelAndView();
	      mv.setViewName("foodtruck/foodtruck_detail.tiles");
	      HttpSession session=request.getSession(false);
	      String id=null;
	      if(session != null){
	         MemberVO memberVO=(MemberVO)session.getAttribute("memberVO");
	         if(memberVO != null){
	            id = memberVO.getId();
	            int wishlistFlag=mypageService.getWishListFlag(id, foodtruckNo);
	            mv.addObject("wishlistFlag",wishlistFlag);
	         }
	      }
	      mv.addObject("truckDetailInfo", truckDetail);
	      ListVO reviewList = foodTruckService.getReviewListByTruckNumber(reviewPageNo, foodtruckNo);
	      mv.addObject("reviewlist", reviewList);
	      mv.addObject("bookingPossible", bookingPossible);
	      return mv;
	   }
	   /** 	  
		정현지
		2017.06.21 (수정완료)
	 	푸드트럭 - 리뷰 작성
	 	기능설명 : 평점(grade), 리뷰 내용, 작성일자, 작성자(customerId), 리뷰를 작성한 푸드트럭 번호를 
	 			ajax 통신한 뒤, 통신이 성공하면 다시 푸드트럭 디테일 페이지로 이동한다(reloading)
	  */
	@RequestMapping(value = "afterLogin_foodtruck/registerReview.do", method = RequestMethod.POST)
	@ResponseBody
	public String registerReview(ReviewVO reviewVO){
		foodTruckService.registerReview(reviewVO); // 푸드 트럭 등록
		return "foodtruck/foodtruck_detail.tiles";
	}
	/**
	 *  김래현
	 *	2017.06.22 (수정완료)
	 *	기능설명 : 즐겨찾기 등록
	 *---------------------------
	 * 코드설명:
	 * view 페이지에서 ajax로 통신할때
	 * 컨트롤러에서 리턴값을 on , off 로하여 
	 * 즐겨찾기 등록,삭제 시 하트색을 바꿀수 있게한다
	 *    
	 */
	@RequestMapping(value = "afterLogin_foodtruck/registerBookMark.do", method = RequestMethod.POST)
	@ResponseBody
	public String registerBookMark(String id, String foodtruckNumber){
		String result = null;
		WishlistVO wishlistVO = new WishlistVO(foodtruckNumber, id);
		int count = foodTruckService.getBookMarkCount(wishlistVO);

		if(count != 0){
			result = "off";
			mypageService.deleteWishList(wishlistVO);
		}else{
			foodTruckService.registerBookMark(wishlistVO);
			result = "on";
		}
		return result;
}

	@RequestMapping("afterLogin_foodtruck/getBookMarkCount.do")
	@ResponseBody
	public String getBookMarkCount(WishlistVO wishlistVO){
		String result = null;
		int count = foodTruckService.getBookMarkCount(wishlistVO);
		if(count != 0){
			result = "off";			
		}else{
			result = "on";
		}
		System.out.println(result);
		return result;
	}

	/** 	  
	정현지
	2017.06.21 (수정완료)
 	예약 - 주문 후 주문 내역 확인
 	기능설명 : 주문한 메뉴를 BookingVO 객체로 받아오고(bvo), session으로부터 id를 받아와 포인트 내역을 받아온다(myPoint)
 			-> 주문 메뉴 / 포인트 내역을 주문 내역 페이지에서 확인할 수 있다
  */
	@RequestMapping(value = "afterLogin_foodtruck/foodtruck_booking_confirm.do", method = RequestMethod.POST)
	public ModelAndView foodtruck_booking_confirm(BookingVO bvo,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("foodtruck/foodtruck_booking_confirm.tiles");
		MemberVO memberVO=(MemberVO)request.getSession(false).getAttribute("memberVO");
		mv.addObject("myPoint", mypageService.getMyPoint(memberVO.getId()));   
		mv.addObject("bvo",bvo);
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, value="afterLogin_foodtruck/bookingMenu.do")
	public String bookingMenu(BookingVO bookingVO,HttpServletRequest request,String resultPoint,String resultTotalAmount){
		foodTruckService.bookingMenu(bookingVO);
		String bookingNumber=bookingVO.getBookingNumber();
		mypageService.calPoint(resultPoint, resultTotalAmount, Integer.parseInt(bookingNumber));
		request.getSession(false).setAttribute("bookingNumber", bookingNumber);
		return "redirect:../foodtruck/foodtruck_booking_confirm_result.do";
	}
	/**
	 * 박다혜
	 * 2017.06.22 수정중
	 * 예약 - 사업자의 최근 예약번호 가져오기 (ajax)
	 * @param id
	 * @return
	 */
	@RequestMapping("afterLogin_foodtruck/getRecentlyBookingNumberBySellerId.do")
	@ResponseBody
	public Object getRecentlybookingNumberBySellerId(String id){
		int bookingNumber=foodTruckService.getRecentlyBookingNumberBySellerId(id);
		return bookingNumber;
	}
	/**
	 * 박다혜
	 * 2017.06.22 수정중
	 * 예약 - 사업자의 이전 예약번호 가져오기  (ajax)
	 * @param id
	 * @return
	 */
	@RequestMapping("afterLogin_foodtruck/getPreviousBookingNumberBySellerId.do")
	@ResponseBody
	public Object getPreviousbookingNumberBySellerId(String id){
		int bookingNumber=foodTruckService.getPreviousBookingNumberBySellerId(id);
		return bookingNumber;
	}
	/**
	 * 박다혜
	 * 2017.06.22 수정중
	 * 예약 - 예약번호에 해당하는 예약상태 가져오기  (ajax)
	 * ----------------------------------------------------------------
	 * ajax통신하여
	 * 사용자의 아이디에 해당하는 결제완료상태인 예약번호가 있을 때
	 * 조리완료가 될때까지 계속 통신하도록 한다.
	 * @param bookingNumber
	 * @param request
	 * @return
	 */
	@RequestMapping("afterLogin_foodtruck/getBookingStateBybookingNumber.do")
	@ResponseBody
	public String getBookingStateBybookingNumber(String bookingNumber,HttpServletRequest request){
		String state=foodTruckService.getBookingStateBybookingNumber(bookingNumber);
		if(state.equals("조리완료")){
			request.getSession(false).removeAttribute("bookingNumber");
			return "ok";
		}
		else
			return "fail";
	}
	   @RequestMapping("afterLogin_foodtruck/checkBooking.do")
	   @ResponseBody
	   public String checkBooking(HttpServletRequest request) {
	      System.out.println("실행됨");
	      HttpSession session = request.getSession();
	      MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");      
	      int count = mypageService.checkBookingState(memberVO.getId());
	      System.out.println(count);
	      return (count==0) ? "ok":"no";
	   }
	   /**
	    * 박다혜
	    * 2017.06.22 수정중
	    * 예약 - 사용자아이디에 해당하는 이전의 예약넘버 가져오기  (ajax)
	    * -------------------------------------------------------------------------
	    * ajax통신하여
	    * 사용자의 아이디에 해당하는 결제완료상태인 예약번호가 있다면
	    * 조리완료가 될때까지 계속 통신하도록 한다.
	    * @param id
	    * @return
	    */
		@RequestMapping("afterLogin_foodtruck/getPreviousBookingNumberByCustomerId.do")
		@ResponseBody
		public String getPreviousBookingNumberByCustomerId(String id){
			String bookingNumber=foodTruckService.getPreviousBookingNumberByCustomerId(id);
			return bookingNumber;
		}
		
}
