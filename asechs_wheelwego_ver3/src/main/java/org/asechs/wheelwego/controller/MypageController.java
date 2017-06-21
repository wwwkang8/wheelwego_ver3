package org.asechs.wheelwego.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asechs.wheelwego.model.FoodTruckService;
import org.asechs.wheelwego.model.MypageService;
import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MypageController {
	@Resource(name="mypageServiceImpl")
   private MypageService mypageService;

	@Resource(name="foodTruckServiceImpl")
   private FoodTruckService foodtruckService;

	/**
	 *  김래현
	 *  2017.06.21 수정완료
	 *  마이페이지/단골트럭/단골트럭리스트 불러오기
	 *  ----------------------- 
	 *    세션이 없으면 홈으로 보냄
	 *    위도 경도를 세팅해주어 현재위치를 알수있음
	 *    
	 */
   @RequestMapping("afterLogin_mypage/wishlist.do")
   public ModelAndView myWishList(HttpServletRequest request, String id, String pageNo,String latitude, String longitude) {
      HttpSession session = request.getSession(false);
      if (session == null) {
         return new ModelAndView("main_home.tiles");
      } else {
         TruckVO gpsInfo = new TruckVO();
         gpsInfo.setLatitude(Double.parseDouble(latitude));
         gpsInfo.setLongitude(Double.parseDouble(longitude));
         ModelAndView modelAndView = new ModelAndView("mypage/mypage_wishlist.tiles");
         ListVO listVO = mypageService.getWishList(pageNo, id);
         modelAndView.addObject("wishlist", listVO);
         modelAndView.addObject("gpsInfo", gpsInfo);
         // System.out.println(listVO.getTruckList());
         return modelAndView;
      }
   }
   /**
    * 김래현
    * 2017.06.21 수정완료
    * 마이페이지/단골트럭/단골트럭 삭제
    * -------------------------
    * 아이디에 저장되어있는 wishlist 중에 클릭한 푸드트럭의 넘버를 기반으로 삭제
    */
   @RequestMapping(value = "afterLogin_mypage/deleteWishList.do", method = RequestMethod.POST)
   @ResponseBody
   public String deleteWishList(String id, String foodtruckNumber) {
      // System.out.println(id + "," + foodtruckNumber);
      WishlistVO wishlistVO = new WishlistVO(foodtruckNumber, id);
      mypageService.deleteWishList(wishlistVO);
      return "success";
   }


  /**
   * 박다혜
   * 2017.06.21 (수정 완료)
   * 마이페이지-마이트럭 등록
   * -------------------------------
   * 사용자가 입력한 트럭 정보를 저장한다.
   * 이때 파일이 있으므로 uploadPath를 설정한다.
   * 트럭 등록 후, session에 foodtruckNumber를 설정해준다.
   * @param truckVO
   * @param request
   * @return
   */
   @RequestMapping(method = RequestMethod.POST, value = "afterLogin_mypage/registerFoodtruck.do")
   public String registerFoodtruck(TruckVO truckVO, HttpServletRequest request) {
      String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
      mypageService.registerFoodtruck(truckVO, uploadPath);
      request.getSession(false).setAttribute("foodtruckNumber", truckVO.getFoodtruckNumber());
      return "mypage/registerMyfoodtruck_result.tiles";
   }

   /**
    * 박다혜
    * 2017.06.21 (수정 완료)
    * 마이페이지 - 마이트럭설정페이지로 이동
    * ------------------------------------------
    * 자신의 트럭번호에 해당하는 트럭정보를 조회하여 modelAndView 객체에 실어 보낸다.
    * @param foodtruckNumber
    * @return
    */
   @RequestMapping("afterLogin_mypage/myfoodtruck_page.do")
   public ModelAndView showMyFoodtruck(String foodtruckNumber) {
      TruckVO truckVO = mypageService.findtruckInfoByTruckNumber(foodtruckNumber);
      return new ModelAndView("mypage/myfoodtruck_page.tiles", "truckVO", truckVO);
   }

   /**
    * 박다혜
    * 2017.06.21 (수정 완료)
    * 마이페이지 - 푸드트럭 설정 업데이트
    * ------------------------------------------
    * 수정된 Truck 정보를 바탕으로 업데이트한다.
    * @param truckVO
    * @param request
    * @return
    */
   @RequestMapping(method = RequestMethod.POST, value = "afterLogin_mypage/updateMyfoodtruck.do")
   public String updateMyfoodtruck(TruckVO truckVO, HttpServletRequest request) {
      String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
      mypageService.updateMyfoodtruck(truckVO, uploadPath);
      return "mypage/myfoodtruck_page_result.tiles";
   }
   /**
    * 박다혜
    * 2017.06.21(수정 완료)
    * 마이페이지 - 메뉴리스트 가져오기
    * ---------------------------------------
    * 트럭 번호에 해당하는 메뉴의 리스트를 반환한다.
    * @param foodtruckNumber
    * @return
    */
   @RequestMapping("afterLogin_mypage/myfoodtruck_menuList.do")
   public ModelAndView showMenuList(String foodtruckNumber) {
      List<FoodVO> menuList = mypageService.showMenuList(foodtruckNumber);
      return new ModelAndView("mypage/myfoodtruck_menuList.tiles","menuList", menuList);
   }
   /**
    * 박다혜
    * 2017.06.21 (수정 완료)
    * 마이페이지 - 메뉴 등록하기
    * @param request
    * @param truckVO
    * @return
    */
   @RequestMapping(method = RequestMethod.POST, value = "afterLogin_mypage/registerMenuList.do")
   public String RegisterMenuList(HttpServletRequest request, TruckVO truckVO) {
      String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
      /*mypageService.registerMenuList(truckVO.getFoodList(), truckVO.getFoodtruckNumber(), uploadPath);*/
      mypageService.registerMenuList(truckVO, uploadPath);
      return "redirect:/afterLogin_mypage/myfoodtruck_menuList.do";
   }
   /**
    * 박다혜
    * 2017.06.21 (수정 완료)
    * 마이페이지 - 메뉴 업데이트하기
    * 
    * @param truckVO
    * @param sellerId
    * @param request
    * @return
    */
   @RequestMapping(method = RequestMethod.POST, value ="afterLogin_mypage/updateMenu.do")
   public String updateMenu(TruckVO truckVO, String foodtruckNumber, HttpServletRequest request) {
      String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
      mypageService.updateMenu(truckVO, uploadPath);
      return "mypage/updateMenu_result.tiles";
   }
   /**
    * 박다혜
    * 2017.06.21 (수정 완료)
    * 마이페이지 - 트럭삭제
    * ---------------------------
    * 트럭 넘버에 해당하는 트럭정보를 삭제하고
    * 세션에 저장된 foodtruckNumber속성을 초기화해준다.
    * @param request
    * @param foodtruckNumber
    * @return
    */
   @RequestMapping(method = RequestMethod.POST, value ="afterLogin_mypage/deleteMyTruck.do")
   public String deleteMyTruck(HttpServletRequest request,String foodtruckNumber) {
      mypageService.deleteMyTruck(foodtruckNumber);
      request.getSession(false).setAttribute("foodtruckNumber", "");
      return "redirect:/afterLogin_mypage/mypage.do";
   }
	/** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 내가 작성한 리뷰 리스트 보기
	기능설명 : 사용자의 아이디로 내가 작성한 리뷰 목록을 받아온다 (pagingBean 적용)
 */
   @RequestMapping("/afterLogin_mypage/showMyReviewList.do")
   public ModelAndView showMyReiviewList(String customerId, String reviewPageNo, HttpServletRequest request) {
      ListVO reviewList = mypageService.showMyReviewList(customerId, reviewPageNo);
      return new ModelAndView("mypage/mypage_review.tiles", "reviewList", reviewList);
   }
   /** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 수정할 리뷰 항목 불러오기
	기능설명 : 내가 쓴 글 리뷰를 수정하기 위해 리뷰 번호로 리뷰 항목을 찾는다
*/
   @RequestMapping("afterLogin_mypage/mypage_review_update.do")
   public ModelAndView ReviewUpdateForm(String reviewNo) {
      ReviewVO reviewVO = mypageService.findReviewInfoByReviewNo(reviewNo);
      return new ModelAndView("mypage/mypage_review_update.tiles", "reviewVO", reviewVO);
   }
   /** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 내가 작성한 리뷰 삭제
	기능설명 : 리뷰 번호를 통해 내가 쓴 글 리뷰를 삭제한다(ajax 통신)
*/
   @RequestMapping("afterLogin_mypage/deleteMyReview.do")
   @ResponseBody
   public String deleteMyReview(String reviewNo) {
      mypageService.deleteMyReview(reviewNo);
      return "deleteOk";
   }
   /** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 내가 작성한 리뷰 수정
	기능설명 : 리뷰 번호를 통해 내가 쓴 글 리뷰를 수정한다
*/
   @RequestMapping("afterLogin_mypage/updateMyReview.do")
   public String updateMyReview(ReviewVO reviewVO) {
      mypageService.updateMyReview(reviewVO);
      return "redirect:/afterLogin_mypage/showMyReviewList.do?customerId=" + reviewVO.getCustomerId();
   }

   @RequestMapping("afterLogin_mypage/checkTruckGPS.do")
   public ModelAndView checkTruckGPS(String sellerId) {
      TruckVO gpsInfo = mypageService.getGPSInfo(sellerId);
      return new ModelAndView("mypage/checkTruckGPS.tiles", "gpsInfo", gpsInfo);
   }

   @RequestMapping("afterLogin_mypage/setTruckGPS.do")
   @ResponseBody
   public String setTruckGPS(String sellerId, String latitude, String longitude) {
      TruckVO gpsInfo = new TruckVO();

      gpsInfo.setSellerId(sellerId);

      if (latitude != null) {
         gpsInfo.setLatitude(Double.parseDouble(latitude));
         gpsInfo.setLongitude(Double.parseDouble(longitude));
      }
      mypageService.setGPSInfo(gpsInfo);

      return "설정 완료";
   }   
   @RequestMapping("afterLogin_mypage/set_TruckGPS.do")
   public String setTruckGPS(String sellerId){
      TruckVO gpsInfo = new TruckVO();
      
      gpsInfo.setSellerId(sellerId);
      
      mypageService.setGPSInfo(gpsInfo);
      
      return "redirect:../afterLogin_mypage/mypage.do";
   }

   @RequestMapping("afterLogin_mypage/test.do")
   public String test() {
      return "mypage/test";
   }

   @RequestMapping("afterLogin_mypage/checkFoodtruckNumber.do")
   @ResponseBody
   public boolean checkFoodtruckNumber(String foodtruckNumber) {
      TruckVO truckVO = mypageService.findtruckInfoByTruckNumber(foodtruckNumber);
      if (truckVO == null)
         return false;
      else
         return true;
   }
	/**
	 * 김호겸 작성
	 *  2017.6.12 (수정 완료)
	 * 마이페이지-내가 쓴 게시물 보기
	 * ------------------------------------------------------
	 *  마이페이지에서 My Contents 를 누르면 3가지 게시판 버튼이 뜬다.
	  */
   @RequestMapping("afterLogin_mypage/showMyContentList.do")
   public String mypage_content(){
	   return "mypage/mypage_content.tiles";
   }
	/**
	 * 김호겸 작성
	 *  2017.6.13 (수정 완료)
	 * 마이페이지-자유게시판 버튼 글릭시 내가 본 글 
	 * ------------------------------------------------------
	 * 자유게시판 버튼을 누르면 자신 아이디로 자신이 쓴 자유게시판에 있는
	 * 글을 불러온다. 이때 페이징 빈을 통해 불러온다.
	  */
   @RequestMapping("afterLogin_mypage/showMyContentByFreeList.do")
   public ModelAndView showMyContentByFreeList(String id,String contentPageNo){
	   ModelAndView mv=new ModelAndView("mypage/mypage_content_freeboard.tiles");
	   ListVO contentList=mypageService.showMyContentByFreeList(id,contentPageNo);
	   mv.addObject("contentList", contentList);
	   return mv;
   }
	/**
	 * 김호겸 작성
	 *  2017.6.12 (수정 완료)
	 * 마이페이지-자유게시판에 있는 게시글 삭제
	 * ------------------------------------------------------
	 * ajax 사용하므로 ResponseBoby를 적용시킨다.
	 * DB 에서 삭제후 컨트롤러에서 삭제가 완료되면 deleteOK를 넘긴다.
	  */
   @RequestMapping("afterLogin_mypage/freeboardDeleteInMaypage.do")
   @ResponseBody
   public String freeboardDeleteInMaypage(String contentNo) {
      mypageService.freeboardDeleteInMaypage(contentNo);
      return "deleteOk";
   }
	/**
	 * 김호겸 작성
	 *  2017.6.14 (수정 완료)
	 * 마이페이지-창업게시판 버튼 글릭시 내가 본 글 
	 * ------------------------------------------------------
	 * 창업게시판 버튼을 누르면 자신 아이디로 자신이 쓴 창업게시판에 있는
	 * 글을 불러온다. 이때 페이징 빈을 통해 불러온다.
	  */
   @RequestMapping("afterLogin_mypage/showMyContentBybusinessList.do")
   public ModelAndView showMyContentBybusinessList(String id,String contentPageNo){
	   ModelAndView mv=new ModelAndView("mypage/mypage_content_business.tiles");
	   ListVO contentList=mypageService.showMyContentBybusinessList(id,contentPageNo);
	   mv.addObject("contentList", contentList);
	   return mv;
   }
	/**
	 * 김호겸 작성
	 *  2017.6.14 (수정 완료)
	 * 마이페이지-창업게시판에 있는 게시글 삭제
	 * ------------------------------------------------------
	 * ajax 사용하므로 ResponseBoby를 적용시킨다.
	 * DB 에서 삭제후 컨트롤러에서 삭제가 완료되면 deleteOK를 넘긴다.
	  */
   @RequestMapping("afterLogin_mypage/businessDeleteInMaypage.do")
   @ResponseBody
   public String businessDeleteInMaypage(String contentNo) {
      mypageService.businessDeleteInMaypage(contentNo);
      return "deleteOk";
   }
	/**
	 * 김호겸 작성
	 *  2017.6.14 (수정 완료)
	 * 마이페이지-QnA게시판 버튼 글릭시 내가 본 글 뜬다. 
	 * ------------------------------------------------------
	 * QnA게시판 버튼을 누르면 자신 아이디로 자신이 쓴 QnA게시판에 있는
	 * 글을 불러온다. 이때 페이징 빈을 통해 불러온다.
	  */
   @RequestMapping("afterLogin_mypage/showMyContentByqnaList.do")
   public ModelAndView showMyContentByqnaList(HttpServletRequest request,String id,String contentPageNo){
	   ModelAndView mv=new ModelAndView("mypage/mypage_content_qna.tiles");
	   ListVO contentList=mypageService.showMyContentByqnaList(id,contentPageNo);
	   mv.addObject("contentList", contentList);
	   return mv;
   }
   /**
	 * 김호겸 작성
	 *  2017.6.14 (수정 완료)
	 * 마이페이지-QnA게시판에 있는 게시글 삭제
	 * ------------------------------------------------------
	 * ajax 사용하므로 ResponseBoby를 적용시킨다.
	 * DB 에서 삭제후 컨트롤러에서 삭제가 완료되면 deleteOK를 넘긴다.
	  */
   @RequestMapping("afterLogin_mypage/qnaDeleteInMaypage.do")
   @ResponseBody
   public String qnaDeleteInMaypage(String contentNo) {
      mypageService.qnaDeleteInMaypage(contentNo);
      return "deleteOk";
   }
   /**
    * 강정호. Seller가 받은 주문 내역 확인하는 메서드
    */
   @RequestMapping("afterLogin_mypage/sellerBookingList.do")
   public String sellerBookingList(Model model, HttpServletRequest request){
	   String sellerId=request.getParameter("sellerId");
	   String pageNo=request.getParameter("pageNo");
	   String foodTruckNumber=mypageService.findtruckNumberBySellerId(sellerId);
	   ListVO bookingNumberList=mypageService.getBookingVO(foodTruckNumber, pageNo);
	   
	   if(bookingNumberList.getBookingNumberList().isEmpty()==false){
		   for(int i=0; i<bookingNumberList.getBookingNumberList().size(); i++){
			   List<BookingDetailVO> bookingDetailVO=mypageService.getBookingDetailVO(bookingNumberList.getBookingNumberList().get(i));
			   bookingNumberList.getBookingNumberList().get(i).setBookingDetail(bookingDetailVO);
		   }
	   }
	   model.addAttribute("bookingList", bookingNumberList);
	   model.addAttribute("truckNumber", foodTruckNumber);
	   return "mypage/mypage_seller_booking_list.tiles";
	   
   }
   /** 	  
	정현지
	2017.06.21 (수정완료)
	마이페이지 - 나의 주문내역 리스트 (pagingBean 적용)
	기능설명 : 사용자 아이디로 주문 내역을 list로 받아온다
			주문 내역이 있을 경우, 주문 내역 detail을 받아와 주문 목록(list)에 setting 해준다
			주문 내역은 마이페이지 - 주문 내역 리스트에서 확인할 수 있다
    */
   @RequestMapping("afterLogin_mypage/customerBookingList.do")
   public String customerBookingList(Model model, String customerId, String pageNo){
	    ListVO listVO = mypageService.customerBookingList(pageNo, customerId);
	    List<BookingVO> myBookingList = listVO.getBookingMenuList();
	    if (myBookingList.isEmpty() == false){
	       for(int i=0; i<myBookingList.size(); i++){
	          List<BookingDetailVO> myBookingDetailList = mypageService.getBookingDetailVO(myBookingList.get(i));
	          myBookingList.get(i).setBookingDetail(myBookingDetailList);
	          // myBookingList.get(i).getBookingNumber(); // 예약번호로 푸드트럭 find하기
	       }
	    }      
	    model.addAttribute("myBookingList", myBookingList);
	    model.addAttribute("listVO", listVO);
	    return "mypage/mypage_customer_order_list.tiles";
	   }

   /**
    * 강정호. 조리 상태 업데이트 해주는 메서드
    */
   @RequestMapping(value="afterLogin_mypage/updateState.do",method=RequestMethod.POST)
   @ResponseBody
   public BookingVO updateBookingState(HttpServletRequest request){
	   String bookingState=request.getParameter("bookingState");
	   String bookingNumber=request.getParameter("bookingNumber");
	   BookingVO bookingVO=new BookingVO();
	   bookingVO.setBookingState(bookingState);
	   bookingVO.setBookingNumber(bookingNumber);
	   mypageService.updateBookingState(bookingVO);
	   return bookingVO;
   }
   /**
    * 포인트적립내역보기
    * @param id
    * @param nowPage
    * @return
    */
   @RequestMapping("afterLogin_mypage/showMyPointList.do")
   public ModelAndView showMyPointList(String customerId, String nowPage){
	   System.out.println(customerId);
	   ListVO pointList=mypageService.getPointListById(customerId, nowPage);
	   ModelAndView mv=new ModelAndView("mypage/mypage_point_list.tiles");
	   mv.addObject("pointList", pointList);
	   int point=mypageService.getMyPoint(customerId);
	   mv.addObject("myPoint", point);
	   return mv;
   }
   
}