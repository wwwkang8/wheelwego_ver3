package org.asechs.wheelwego.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.asechs.wheelwego.model.BookingService;
import org.asechs.wheelwego.model.MypageService;
import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class BookingController {
	@Resource(name="bookingServiceImpl")
	BookingService bookingService;
	@Resource(name="mypageServiceImpl2")
	MypageService mypageService;
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
			String bookingNumber=bookingService.getPreviousBookingNumberByCustomerId(id);
			return bookingNumber;
		}
		/**
		 * 황윤상
		 * 2017.06.22 수정중
		 * 예약 - 선행주문이 있는지 체크
		 * ------------------------------------------
		 * 아이디에 해당하는 선행주문이 있는지 체크하여
		 * count가 0이라면 선행주문이 없는 것이므로 ok를,
		 *  0이아니라면 선행주문이 있는 것이므로 no를 반환한다.
		 * @param request
		 * @return
		 */
		   @RequestMapping("afterLogin_foodtruck/checkBooking.do")
		   @ResponseBody
		   public String checkBooking(HttpServletRequest request) {
		      MemberVO memberVO = (MemberVO) request.getSession(false).getAttribute("memberVO");      
		      int count = bookingService.checkBookingState(memberVO.getId());
		      return (count==0) ? "ok":"no";
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
				String state=bookingService.getBookingStateBybookingNumber(bookingNumber);
				if(state.equals("조리완료")){
					request.getSession(false).removeAttribute("bookingNumber");
					return "ok";
				}
				else
					return "fail";
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
				int bookingNumber=bookingService.getPreviousBookingNumberBySellerId(id);
				return bookingNumber;
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
				int bookingNumber=bookingService.getRecentlyBookingNumberBySellerId(id);
				return bookingNumber;
			}
			/**
			 * 정현지
			 * 2017.06.22 수정중
			 * 예약 - 메뉴 예약하기
			 * ---------------------------------
			 * 예약정보를 db에 insert 한 뒤 
			 * 사용된 포인트와 총 결제금액을 이용하여 포인트를 차감/적립시킨다.
			 * @param bookingVO
			 * @param request
			 * @param resultPoint
			 * @param resultTotalAmount
			 * @return
			 */
			@RequestMapping(method = RequestMethod.POST, value="afterLogin_foodtruck/bookingMenu.do")
			public String bookingMenu(BookingVO bookingVO,HttpServletRequest request,String resultPoint,String resultTotalAmount){
				bookingService.bookingMenu(bookingVO);
				String bookingNumber=bookingVO.getBookingNumber();
				bookingService.calPoint(resultPoint, resultTotalAmount, Integer.parseInt(bookingNumber));
				return "redirect:../foodtruck/foodtruck_booking_confirm_result.do";
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
				mv.addObject("myPoint", bookingService.getMyPoint(memberVO.getId()));   
				mv.addObject("bvo",bvo);
				return mv;
			}
			  /**
			   * 강정호
			   * 2017.06.21(수정완료)
			   * 마이페이지 - 사업자 주문 내역
			   * -----------------------------------------------
			   * 코드 설명 : 사업자 주문 내역은 3가지의 메서드로 이루어져 있다.
			   * findtruckNumberBySellerId(sellerId) : 세션의 sellerId를 이용하여 푸드 트럭 넘버를 가져오는 메서드
			   * getBookingVO(foodTruckNumber, pageNo) : 푸드 트럭 넘버에 해당하는 주문 내역을 BookingVO형식으로
			   * 불러온다. 이때 예약 번호, 주문한 회원아이디, 주문상태, 주문 일시 등의 정보를 받아온다.
			   * getBookingDetailVO(): 앞서 받아온 BookingVO에 있는 예약 번호에 해당하는 상세 메뉴 내역을 불러와서
			   * BookingVO에 있는 BookingDetailVO에 set 해주는 메서드이다.
			   * 
			   * 위의 3가지 메서드를 이용해서 사업자의 BookingVO와 BookingDetailVO에 대한 정보를 불러와서
			   * View에서 사업자가 받은 주문 내역을 보여준다.
			   * @param model
			   * @param request
			   * @return
			   */
			   @RequestMapping("afterLogin_mypage/sellerBookingList.do")
			   public String sellerBookingList(Model model, HttpServletRequest request){
				   String sellerId=request.getParameter("sellerId");
				   String pageNo=request.getParameter("pageNo");
				   String foodTruckNumber=mypageService.findtruckNumberBySellerId(sellerId);
				   ListVO bookingNumberList=bookingService.getBookingVO(foodTruckNumber, pageNo);
				   
				   if(bookingNumberList.getBookingNumberList().isEmpty()==false){
					   for(int i=0; i<bookingNumberList.getBookingNumberList().size(); i++){
						   List<BookingDetailVO> bookingDetailVO=bookingService.getBookingDetailVO(bookingNumberList.getBookingNumberList().get(i));
						   bookingNumberList.getBookingNumberList().get(i).setBookingDetail(bookingDetailVO);
					   }
				   }
				   model.addAttribute("bookingList", bookingNumberList);
				   model.addAttribute("truckNumber", foodTruckNumber);
				   return "mypage/mypage_seller_booking_list.tiles";
				   
			   }
			   /** 	  
				정현지
				2017.06.22 (수정완료)
				마이페이지 - 나의 주문내역 리스트 (pagingBean 적용)
				기능설명 : 사용자 아이디로 주문 내역을 list로 받아온다
						주문 내역이 있을 경우, 주문 내역 detail을 받아와 주문 목록(list)에 setting 해준다
						주문번호로 푸드트럭 이름을 가져와 setting 해준다
						주문 내역은 마이페이지 - 주문 내역 리스트에서 확인할 수 있다
			    */
			   @RequestMapping("afterLogin_mypage/customerBookingList.do")
			   public String customerBookingList(Model model, String customerId, String pageNo){
				    ListVO listVO = bookingService.customerBookingList(pageNo, customerId);
				    List<BookingVO> myBookingList = listVO.getBookingMenuList();
				    if (myBookingList.isEmpty() == false){
				       for(int i=0; i<myBookingList.size(); i++){
				          List<BookingDetailVO> myBookingDetailList = bookingService.getBookingDetailVO(myBookingList.get(i));
				          myBookingList.get(i).setBookingDetail(myBookingDetailList);
				          List<String> menuId =  bookingService.findMenuIdByBookingNumber(myBookingList.get(i).getBookingNumber());
				          for(int j=0; j<1; j++){
				        	  String foodtruckNumber = mypageService.findFoodTruckNumberByMenuId(menuId.get(j));
				        	  String foodtruckName = mypageService.findFoodtruckNameByFoodTruckNumber(foodtruckNumber);
				        	  myBookingList.get(i).setFoodtruckName(foodtruckName);
				          }
				       }
				    }    
				    model.addAttribute("myBookingList", myBookingList);
				    model.addAttribute("listVO", listVO);
				    return "mypage/mypage_customer_order_list.tiles";
				   }
			   /**
			    * 강정호
			    * 2017.06.22(수정완료)
			    * 마이페이지 - 주문 상태 업데이트
			    * ----------------------------------------
			    * 코드 설명 : 주문 상태를 결제 완료에서 "조리중", "조리완료"로 변경해주는 메서드
			    */
			   @RequestMapping(value="afterLogin_mypage/updateState.do",method=RequestMethod.POST)
			   @ResponseBody
			   public BookingVO updateBookingState(HttpServletRequest request){
				   String bookingState=request.getParameter("bookingState");
				   String bookingNumber=request.getParameter("bookingNumber");
				   BookingVO bookingVO=new BookingVO();
				   bookingVO.setBookingState(bookingState);
				   bookingVO.setBookingNumber(bookingNumber);
				   bookingService.updateBookingState(bookingVO);
				   return bookingVO;
			   }
			   /**
			    * 박다혜
			    * 2016.06.21 (수정 완료)
			    * 마이페이지 - 마이 포인트 적립/사용 내역
			    * -----------------------------------------------
			    * 사용자 아이디에 해당하는 포인트 적립/사용내역을 불러와
			    * modelAndView 객체에 실어 보낸다.
			    *  이 때 사용자의 현재 포인트를 함께 보여주기 위해
			    *   사용자 아이디에 해당하는 포인트를 조회하여 함께 보낸다.
			    * @param id
			    * @param nowPage
			    * @return
			    */
			   @RequestMapping("afterLogin_mypage/showMyPointList.do")
			   public ModelAndView showMyPointList(String customerId, String nowPage){
				   ListVO pointList=bookingService.getPointListById(customerId, nowPage);
				   ModelAndView mv=new ModelAndView("mypage/mypage_point_list.tiles");
				   mv.addObject("pointList", pointList);
				   int point=bookingService.getMyPoint(customerId);
				   mv.addObject("myPoint", point);
				   return mv;
			   }
}
