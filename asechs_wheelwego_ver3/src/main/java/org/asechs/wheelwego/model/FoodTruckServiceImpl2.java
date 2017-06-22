package org.asechs.wheelwego.model;

import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;
import org.springframework.stereotype.Service;

@Service
public class FoodTruckServiceImpl2 implements FoodTruckService {
	@Resource
	private FoodTruckDAO foodTruckDAO;

	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭 랜덤 리스트(main)
 	기능설명 : 푸드트럭 정보를 main 화면에서 랜덤으로 보여준다
 			푸드트럭 번호에 해당하는 푸드트럭의 위시리스트 수, 평점 평균도 함께 보여준다
  */
	@Override
	public List<TruckVO> foodtruckList() {
		List<TruckVO> truckList=foodTruckDAO.foodtruckList();
		for(int i=0; i<truckList.size();i++){
			String foodtruckNumber=truckList.get(i).getFoodtruckNumber();
			truckList.get(i).setAvgGrade(foodTruckDAO.findAvgGradeByTruckNumber(foodtruckNumber));
			truckList.get(i).setWishlistCount(foodTruckDAO.findWishlistCountByTruckNumber(foodtruckNumber));
		}
		return truckList;
	}

	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭 상세보기
 	기능설명 : 푸드트럭 클릭시, 푸드트럭 번호를 받아와
 			1) 일치하는 푸드트럭의 상세정보를 가져온다
 			2) 푸드트럭의 위시리스트 수, 리뷰 평점 정보를 가져온다
  */
	@Override
	public TruckVO foodTruckAndMenuDetail(String foodtruckNo){
		TruckVO tvo = foodTruckDAO.foodtruckDetail(foodtruckNo);
		tvo.setAvgGrade(foodTruckDAO.findAvgGradeByTruckNumber(tvo.getFoodtruckNumber()));
		tvo.setWishlistCount(foodTruckDAO.findWishlistCountByTruckNumber(tvo.getFoodtruckNumber()));
		List<FoodVO> fvo = foodTruckDAO.foodListDetail(foodtruckNo);
		tvo.setFoodList(fvo);
		return tvo;
	}
	/**
	 * 김래현
	 * 2017.06.22 수정완료
	 * 푸드트럭 - 단골트럭 등록하기
	 */
	@Override
	public void registerBookMark(WishlistVO wishlistVO) {
		foodTruckDAO.registerBookMark(wishlistVO);		
	}
	/**
	 * 김래현
	 * 2017.06.22 수정완료
	 * 푸드트럭 - 단골트럭 등록여부
	 * -----------------------
	 * 단골트럭 등록이되어있으면 1을반환
	 * else 0을반환하여
	 * select 할때 1인것만 리스트에 나오게 출력
	 */
	@Override
	public int getBookMarkCount(WishlistVO wishlistVO) {
		return foodTruckDAO.getBookMarkCount(wishlistVO);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 푸드트럭 - 리뷰 등록하기
	 */
	@Override
	public void registerReview(ReviewVO reviewVO) {
		foodTruckDAO.registerReview(reviewVO);		
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭 상세보기 - 리뷰 리스트
 	기능설명 : 푸드트럭 클릭시, 푸드트럭 번호를 받아와
 			1) 일치하는 푸드트럭의 리뷰 목록을 보여준다
 			2) 리뷰 리스트에 pagingBean 적용
  */
	@Override
	public ListVO getReviewListByTruckNumber(String reviewPageNo, String foodtruckNumber) {
		int totalCount =foodTruckDAO.getReivewTotalCount(foodtruckNumber);
		if(reviewPageNo==null)
			reviewPageNo="1";
		PagingBean pagingBean=new PagingBean( Integer.parseInt(reviewPageNo),totalCount,foodtruckNumber);
		ListVO pagingList=new ListVO();
		pagingList.setReviewList(foodTruckDAO.getReviewListByTruckNumber(pagingBean));
		pagingList.setPagingBean(pagingBean);
		return pagingList;
	}

	/**
	 * 박다혜
	 * 2017.06.21 (수정완료)
	 * 푸드트럭 - 필터링
	 * -------------------------
	 * pageNo가 없다면 기본 1로 설정해준다.
	 * searchWord가 존재한다면 이름으로 검색하는 것이고
	 * gpsInfo가 존재한다면 자동검색 / 수동검색을 하는 것이다.
	 * 
	 *  따라서 이름으로 검색한다면 이름검색조건에 해당하는 푸드트럭 리스트의 수를
	 *  totalCount로 설정하여 페이징빈 객체를 생성한다.
	 *  gps정보로 검색한다면 위치 반경 1km내의 푸드트럭 리스트 수를 totalCount로 
	 *  설정하여 페이징빈 객체를 생성한다.
	 *  
	 *  이후 option값에 따라 필터링을 실행하고
	 *  각 푸드트럭마다 평점과 즐겨찾기 수를 setting해서 반환한다.
	 */
	@Override
	public ListVO filtering(String option, String searchWord, String pageNo, TruckVO gpsInfo) {
	      int totalCount=0;
	      PagingBean pagingbean =null;
	      if(pageNo==null)
	         pageNo="1";
	      //이름으로 검색할 경우 페이징 객체 설정
	      if(searchWord!=null){
	    	  totalCount=foodTruckDAO.getTruckListTotalContentCountByName(searchWord);
	    	  pagingbean = new PagingBean(Integer.parseInt(pageNo),totalCount,searchWord);
	      }
	      //위치로 검색할 경우 페이징 객체 설정
	      if(gpsInfo!=null){
	    	  totalCount=foodTruckDAO.getTruckListTotalContentCountByGPS(gpsInfo);
	    	  pagingbean = new PagingBean(Integer.parseInt(pageNo),totalCount,null);
	    	  pagingbean.setGpsInfo(gpsInfo);
	      }
	      //option에 따른 필터링
	      List<TruckVO> truckList=null;
	      if(option.equals("byAvgGrade")){
	         truckList=foodTruckDAO.filteringByAvgGrade(pagingbean);
	      }else if(option.equals("byWishlist")){
	         truckList=foodTruckDAO.filteringByWishlist(pagingbean);
	      }else{
	         truckList=foodTruckDAO.filteringByDate(pagingbean);
	      }
	      for(int i=0; i<truckList.size();i++){
	         truckList.get(i).setAvgGrade(foodTruckDAO.findAvgGradeByTruckNumber(truckList.get(i).getFoodtruckNumber()));
	         truckList.get(i).setWishlistCount(foodTruckDAO.findWishlistCountByTruckNumber(truckList.get(i).getFoodtruckNumber()));
	      }
	      ListVO pagingList=new ListVO();
	      pagingList.setTruckList(truckList);
	      pagingList.setPagingBean(pagingbean);
	      return pagingList;
	}
	/**
	 * 정현지
	 * 2017.06.22 수정완료
	 * 예약 - 메뉴 예약하기
	 */
	@Override
	public void bookingMenu(BookingVO bookingVO) {
		foodTruckDAO.bookingMenu(bookingVO);		
	}
	/**
	 * 박다혜
	 * 2017.06.22 수정완료
	 * 예약 - 사업자의 최근 예약번호 가져오기 (ajax)
	 * -----------------------------------------------------
	 * 주문이 들어왔을 때 결제완료 상태이므로 
	 * 주문이 들어왔는지 알기위해 결제완료상태인 최근 예약번호를 가져온다.
	 */
	@Override
	public int getRecentlyBookingNumberBySellerId(String id) {
		return foodTruckDAO.getRecentlyBookingNumberBySellerId(id);
	}
	/**
	 * 박다혜
	 * 2017.06.22 수정완료
	 * 예약 - 사업자의 이전 예약번호 가져오기  (ajax)
	 * -------------------------------------------------------------------
	 * 예약 상태에 상관 없이 사업자의 최근 예약 번호를 가져온다.
	 */
	@Override
	public int getPreviousBookingNumberBySellerId(String id) {
		return foodTruckDAO.getPreviousBookingNumberBySellerId(id);
	}
	/**
	 * 박다혜
	 * 2017.06.22 수정완료
	 * 예약 - 예약번호에 해당하는 예약상태 가져오기  (ajax)
	 */
	@Override
	public String getBookingStateBybookingNumber(String bookingNumber) {
		return foodTruckDAO.getBookingStateBybookingNumber(Integer.parseInt(bookingNumber));
	}
	/**
	 * 황윤상
	 * 2017.06.22 수정완료
	 * 푸드트럭 - gps정보의 반경 1km 내 해당하는 푸드트럭 번호 리스트를 가져온다.
	 */
	@Override
	public List<String> getFoodtruckNumberList(TruckVO gpsInfo) {
		return foodTruckDAO.getFoodtruckNumberList(gpsInfo);
	}
	
	/**
	 * 박다혜
	 * 2017.06.22 수정완료
	 * 예약 - 사용자아이디에 해당하는 결제 완료 상태의 이전 예약넘버 가져오기  (ajax)
	 */
	@Override
	public String getPreviousBookingNumberByCustomerId(String id) {
		return foodTruckDAO.getPreviousBookingNumberByCustomerId(id);
	}
	
}
