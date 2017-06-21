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
@Service
public class FoodTruckServiceImpl2 implements FoodTruckService {
	@Resource
	private FoodTruckDAO foodTruckDAO;

	@Override
	public String findFoodtruckNameByMenuId(String menuId) {
		// TODO Auto-generated method stub
		return null;
	}
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
 	푸드트럭 - 푸드트럭명으로 검색한 푸드트럭 리스트
 	기능설명 : 검색한 푸드트럭명에 해당하는 푸드트럭 리스트를 list로 받아온다
  */
	@Override
	public List<TruckVO> searchFoodTruckList(String name){
		return foodTruckDAO.searchFoodTruckList(name);
	}

	@Override
	public List<TruckVO> searchFoodTruckByGPS(TruckVO gpsInfo) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void registerBookMark(WishlistVO wishlistVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBookMarkCount(WishlistVO wishlistVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void registerReview(ReviewVO reviewVO) {
		// TODO Auto-generated method stub
		
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

	@Override
	public ListVO getFoodTruckListByName(String pageNo, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListVO getFoodTruckListByGPS(String pageNo, TruckVO gpsInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListVO filtering(String option, String name, String pageNo, String latitude, String longitude,
			TruckVO gpsInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void bookingMenu(BookingVO bookingVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRecentlyBookingNumberBySellerId(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPreviousBookingNumberBySellerId(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBookingStateBybookingNumber(String bookingNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getFoodtruckNumberList(TruckVO truckVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPreviousBookingNumberByCustomerId(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
