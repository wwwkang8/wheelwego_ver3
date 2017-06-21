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
import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 본인 이름
 *  수정 날짜 (수정 완료)
 * 대제목[마이페이지/푸드트럭/멤버/게시판/예약] - 소제목
 * ------------------------------------------------------
 * 코드설명
 * 
 * EX)
	박다혜
	 2017.06.21 (수정완료) / (수정중)
 	마이페이지 - 마이트럭설정
	---------------------------------
	~~~~~
  */
@Controller
public class MypageController {
	@Resource(name="mypageServiceImpl")
   private MypageService mypageService;
	@Resource(name="foodTruckServiceImpl")
   private FoodTruckService foodtruckService;


   @RequestMapping("afterLogin_mypage/wishlist.do")
   // 세션이 없으면 홈으로 보냄
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
         System.out.println(listVO);
         modelAndView.addObject("wishlist", listVO);
         modelAndView.addObject("gpsInfo", gpsInfo);
         // System.out.println(listVO.getTruckList());
         return modelAndView;
      }
   }

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
    * 나의 푸드트럭 설정페이지이동 아이디에 일치하는 푸드트럭을 찾아서 정보를 함께 보낸다.
    */
   @RequestMapping("afterLogin_mypage/myfoodtruck_page.do")
   public ModelAndView showMyFoodtruck(HttpServletRequest request) {
      MemberVO memberVO = (MemberVO) request.getSession(false).getAttribute("memberVO");
      String truckNumber = mypageService.findtruckNumberBySellerId(memberVO.getId());
      TruckVO truckVO = mypageService.findtruckInfoByTruckNumber(truckNumber);
      return new ModelAndView("mypage/myfoodtruck_page.tiles", "truckVO", truckVO);
   }

   /**
    * 푸드트럭 정보를 업데이트
    * 
    * @return
    */
   @RequestMapping(method = RequestMethod.POST, value = "afterLogin_mypage/updateMyfoodtruck.do")
   public String updateMyfoodtruck(TruckVO truckVO, HttpServletRequest request) {
      String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
      mypageService.updateMyfoodtruck(truckVO, uploadPath);
      return "mypage/myfoodtruck_page_result.tiles";
   }

   @RequestMapping("afterLogin_mypage/myfoodtruck_menuList.do")
   public ModelAndView showMenuList(HttpServletRequest request) {
      MemberVO memberVO = (MemberVO) request.getSession(false).getAttribute("memberVO");
      String truckNumber = mypageService.findtruckNumberBySellerId(memberVO.getId());
      List<FoodVO> menuList = mypageService.showMenuList(truckNumber);
      ModelAndView mv = new ModelAndView();
      mv.setViewName("mypage/myfoodtruck_menuList.tiles");
      mv.addObject("menuList", menuList);
      mv.addObject("truckNumber", truckNumber);
      return mv;
   }

   @RequestMapping(method = RequestMethod.POST, value = "afterLogin_mypage/registerMenuList.do")
   public String RegisterMenuList(HttpServletRequest request, TruckVO truckVO) {
      MemberVO memberVO = (MemberVO) request.getSession(false).getAttribute("memberVO");
      String truckNumber = mypageService.findtruckNumberBySellerId(memberVO.getId());
      String uploadPath = request.getSession().getServletContext().getRealPath("/resources/upload/");
      mypageService.registerMenuList(truckVO.getFoodList(), truckNumber, uploadPath);
      return "redirect:/afterLogin_mypage/myfoodtruck_menuList.do";
   }

   @RequestMapping("afterLogin_mypage/updateMenu.do")
   public String updateMenu(TruckVO truckVO, String sellerId, HttpServletRequest request) {
      String foodtruckNumber = mypageService.findtruckNumberBySellerId(sellerId);
      truckVO.setFoodtruckNumber(foodtruckNumber);
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
   @RequestMapping("afterLogin_mypage/deleteMyTruck.do")
   public String deleteMyTruck(HttpServletRequest request,String foodtruckNumber) {
      mypageService.deleteMyTruck(foodtruckNumber);
      request.getSession(false).setAttribute("foodtruckNumber", "");
      return "redirect:/afterLogin_mypage/mypage.do";
   }

   @RequestMapping("/afterLogin_mypage/showMyReviewList.do")
   public ModelAndView showMyReiviewList(String customerId, String reviewPageNo, HttpServletRequest request) {
      ListVO reviewList = mypageService.showMyReviewList(customerId, reviewPageNo);
      return new ModelAndView("mypage/mypage_review.tiles", "reviewList", reviewList);
   }

   @RequestMapping("afterLogin_mypage/mypage_review_update.do")
   public ModelAndView ReviewUpdateForm(String reviewNo) {
      ReviewVO reviewVO = mypageService.findReviewInfoByReviewNo(reviewNo);
      System.out.println(reviewVO);
      return new ModelAndView("mypage/mypage_review_update.tiles", "reviewVO", reviewVO);
   }

   @RequestMapping("afterLogin_mypage/deleteMyReview.do")
   @ResponseBody
   public String deleteMyReview(String reviewNo) {
      mypageService.deleteMyReview(reviewNo);
      return "deleteOk";
   }

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

   @RequestMapping("afterLogin_mypage/showMyFoodtruck.do")
   public ModelAndView showMyFoodtruck(String id) {
      String foodtruckNo = mypageService.findtruckNumberBySellerId(id);
      return new ModelAndView("redirect:../foodtruck/foodTruckAndMenuDetail.do", "foodtruckNo", foodtruckNo);
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
   // 내가 슨 게시물 보기로 가기
   @RequestMapping("afterLogin_mypage/showMyContentList.do")
   public String mypage_content(){
	   return "mypage/mypage_content.tiles";
   }
   // 자유게시물 에 해당하는 게시글 보기2
   @RequestMapping("afterLogin_mypage/showMyContentByFreeList.do")
   public ModelAndView showMyContentByFreeList(HttpServletRequest request,String id,String contentPageNo){
	   ModelAndView mv=new ModelAndView("mypage/mypage_content_freeboard.tiles");
	   ListVO contentList=mypageService.showMyContentByFreeList(id,contentPageNo);
	   mv.addObject("contentList", contentList);
	   return mv;
   }
   // 자유게시물 에 해당하는 게시글 삭제
   @RequestMapping("afterLogin_mypage/freeboardDeleteInMaypage.do")
   @ResponseBody
   public String freeboardDeleteInMaypage(String contentNo) {
	   System.out.println("딜리트"+contentNo);
      mypageService.freeboardDeleteInMaypage(contentNo);
      return "deleteOk";
   }
   //창업게시판 에 해당한 게시글보기
   @RequestMapping("afterLogin_mypage/showMyContentBybusinessList.do")
   public ModelAndView showMyContentBybusinessList(HttpServletRequest request,String id,String contentPageNo){
	  
	   ModelAndView mv=new ModelAndView("mypage/mypage_content_business.tiles");
	   ListVO contentList=mypageService.showMyContentBybusinessList(id,contentPageNo);
	   mv.addObject("contentList", contentList);
	   return mv;
   }
   // 창업 에 해당하는 게시글 삭제
   @RequestMapping("afterLogin_mypage/businessDeleteInMaypage.do")
   @ResponseBody
   public String businessDeleteInMaypage(String contentNo) {
	   System.out.println("딜리트"+contentNo);
      mypageService.businessDeleteInMaypage(contentNo);
      return "deleteOk";
   }
   // 규엔에이 게시글 보기
   @RequestMapping("afterLogin_mypage/showMyContentByqnaList.do")
   public ModelAndView showMyContentByqnaList(HttpServletRequest request,String id,String contentPageNo){
	   ModelAndView mv=new ModelAndView("mypage/mypage_content_qna.tiles");
	   ListVO contentList=mypageService.showMyContentByqnaList(id,contentPageNo);
	   mv.addObject("contentList", contentList);
	   return mv;
   }
   // 큐ㅜ엔에이 에 해당하는 게시글 삭제
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
    * 정현지 : 사용자 마이페이지 - 나의 주문내역 리스트
    */
   @RequestMapping("afterLogin_mypage/customerBookingList.do")
   public String customerBookingList(Model model, String customerId, String pageNo){
	    ListVO listVO = mypageService.customerBookingList(pageNo, customerId);
	    List<BookingVO> myBookingList = listVO.getBookingMenuList();
	    
	    if (myBookingList.isEmpty() == false){
	       for(int i=0; i<myBookingList.size(); i++){
	          List<BookingDetailVO> myBookingDetailList = mypageService.getBookingDetailVO(myBookingList.get(i));
	          myBookingList.get(i).setBookingDetail(myBookingDetailList);
	       }
	    }      
	    
	    System.out.println(myBookingList);
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