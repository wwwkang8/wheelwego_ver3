package org.asechs.wheelwego.model;

import java.util.List;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
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
@Repository
public class FoodTruckDAOImpl implements FoodTruckDAO {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public String findFoodtruckNameByMenuId(String menuId){
		return sqlSessionTemplate.selectOne("foodtruck.findFoodtruckNameByMenuId", menuId);
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - main 화면에 보여줄 푸드트럭 리스트
 	기능설명 : foodtruck 리스트를 랜덤 함수를 사용하여 main 화면에 보여준다
  */
	@Override
	public List<TruckVO> foodtruckList() {
		return sqlSessionTemplate.selectList("foodtruck.foodtruckList");
	}

	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭명으로 검색한 푸드트럭 리스트
 	기능설명 : 푸드트럭명으로 검색한 foodtruck 리스트를 받아온다
  */
	@Override
	public List<TruckVO> searchFoodTruckList(String name) {
		return sqlSessionTemplate.selectList("foodtruck.searchFoodTruckList", name);
	}

	@Override
	public int getTruckListTotalContentCountByName(String name) {
		return sqlSessionTemplate.selectOne("foodtruck.getTruckListTotalContentCountByName", name);
	}

	@Override
	public List<TruckVO> searchFoodTruckByGPS(TruckVO gpsInfo) {
		return sqlSessionTemplate.selectList("foodtruck.searchFoodTruckByGPS", gpsInfo);
	}

	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭 상세보기
 	기능설명 : 푸드트럭 번호와 일치하는 푸드트럭 정보를 모두 받아온다
 		   (푸드트럭 정보, 메뉴 정보, 리뷰 정보, 위시리스트 등..)
  */
	@Override
	public TruckVO foodtruckDetail(String foodtruckNo) {
		return sqlSessionTemplate.selectOne("foodtruck.foodtruckDetail", foodtruckNo);
	}

	/* foodtruck 상세보기에 들어갈 menu list */
	@Override
	public List<FoodVO> foodListDetail(String foodtruckNo) {
		return sqlSessionTemplate.selectList("foodtruck.foodListDetail", foodtruckNo);
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭 상세보기 - 리뷰 작성
 	기능설명 : selectKey를 사용하여 reviewNo를 먼저 select 한 뒤, 리뷰를 insert 해준다
	(reviewNo, foodtruckNumber, cumstomerId, reviewContent, sysdate, grade) 
  */
	@Override
	public void registerReview(ReviewVO reviewVO) {
		sqlSessionTemplate.insert("foodtruck.registerReview", reviewVO);
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭 상세보기 - 리뷰 목록
 	기능설명 : foodtruckNumber에 해당하는 리뷰 항목을 리스트로 받아온다(pagingBean 적용)
  */
	@Override
	public List<ReviewVO> getReviewListByTruckNumber(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("foodtruck.getReviewListByTruckNumber", pagingBean);
	}

	@Override
	public int getReivewTotalCount(String foodtruckNumber) {
		return sqlSessionTemplate.selectOne("foodtruck.getReivewTotalCount",foodtruckNumber);
	}
	@Override
	public void registerBookMark(WishlistVO wishlistVO) {
		sqlSessionTemplate.insert("foodtruck.registerBookMark", wishlistVO);
		
	}
	@Override
	public int getBookMarkCount(WishlistVO wishlistVO) {
		
		return sqlSessionTemplate.selectOne("foodtruck.getBookMarkCount",wishlistVO);
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - 푸드트럭명으로 검색한 푸드트럭 리스트
 	기능설명 : 푸드트럭명으로 검색한 푸드트럭 리스트를 pagingBean 적용하여 받아온다
  */
	@Override
	public List<TruckVO> getFoodTruckListByName(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("foodtruck.getFoodTruckListByName",pagingBean);
	}
	
/*	@Override
	public int findTruckNumberInReview(String foodtruckNumber) {
		return sqlSessionTemplate.selectOne("foodtruck.findTruckNumberInReview", foodtruckNumber);
	}*/

	@Override
	public int getTruckListTotalContentCountByGPS(TruckVO gpsInfo) {
		return sqlSessionTemplate.selectOne("foodtruck.getTruckListTotalContentCountByGPS", gpsInfo);
	}

	@Override
	public List<TruckVO> getFoodTruckListByGPS(PagingBean pagingBean) {
		return sqlSessionTemplate.selectList("foodtruck.getFoodTruckListByGPS",pagingBean);
	}
	@Override
	public List<TruckVO> filteringByDate(PagingBean pagingbean) {
		return sqlSessionTemplate.selectList("foodtruck.filteringByDate", pagingbean);
	}

	@Override
	public List<TruckVO> filteringByWishlist(PagingBean pagingbean) {
		System.out.println("DAO gpsInfo:"+pagingbean.getGpsInfo());
		System.out.println("dao 결과 : "+sqlSessionTemplate.selectList("foodtruck.filteringByWishlist", pagingbean));
		return sqlSessionTemplate.selectList("foodtruck.filteringByWishlist", pagingbean);
	}

	@Override
	public List<TruckVO> filteringByAvgGrade(PagingBean pagingbean) {
		return sqlSessionTemplate.selectList("foodtruck.filteringByAvgGrade", pagingbean);
	}
	@Override
	public double findAvgGradeByTruckNumber(String truckNumber) {
		return sqlSessionTemplate.selectOne("foodtruck.findAvgGradeByTruckNumber", truckNumber);
	}

	@Override
	public int findWishlistCountByTruckNumber(String foodtruckNumber) {
		return sqlSessionTemplate.selectOne("foodtruck.findWishlistCountByTruckNumber", foodtruckNumber);
	}
	@Override
	public void bookingMenu(BookingVO bookingVO) {
		sqlSessionTemplate.insert("foodtruck.bookingMenu", bookingVO);
		List<BookingDetailVO> bookingDetailList=bookingVO.getBookingDetail();
		for(int i=0; i<bookingDetailList.size();i++){
			bookingDetailList.get(i).setBookingNumber(bookingVO.getBookingNumber());
			sqlSessionTemplate.insert("foodtruck.bookingDetailMenu", bookingDetailList.get(i));
		}
	}

	@Override
	public List<BookingVO> getBookingListBySellerId(String id) {
		System.out.println("dao"+id);
		return sqlSessionTemplate.selectList("foodtruck.getBookingListBySellerId", id);
	}

	@Override
	public int getRecentlyBookingNumberBySellerId(String id) {
		return sqlSessionTemplate.selectOne("foodtruck.getRecentlyBookingNumberBySellerId", id);
	}

	@Override
	public int getPreviousBookingNumberBySellerId(String id) {
		return sqlSessionTemplate.selectOne("foodtruck.getPreviousBookingNumberBySellerId", id);
	}

	@Override
	public String getBookingStateBybookingNumber(int bookingNumber) {
		return sqlSessionTemplate.selectOne("foodtruck.getBookingStateBybookingNumber", bookingNumber);
	}
	 @Override
	   public List<String> getFoodtruckNumberList(TruckVO gpsInfo) {
	      return sqlSessionTemplate.selectList("foodtruck.getFoodtruckNumberList", gpsInfo);
	   }

	@Override
	public String getPreviousBookingNumberByCustomerId(String id) {
		return sqlSessionTemplate.selectOne("foodtruck.getPreviousBookingNumberByCustomerId", id);
	}
}
