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

@Repository
public class FoodTruckDAOImpl implements FoodTruckDAO {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

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
	 * 황윤상
	 * 2017.06.22 수정완료
	 * 푸드트럭 - 검색명에 해당하는 푸드트럭 리스트의 수 반환
	 */
	@Override
	public int getTruckListTotalContentCountByName(String name) {
		return sqlSessionTemplate.selectOne("foodtruck.getTruckListTotalContentCountByName", name);
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

	/**
	 * 정현지
	 * 2017.06.22 수정완료
	 * 푸드트럭 - 해당 푸드트럭의 음식 리스트 가져오기
	 */
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
	/**
	 * 박다혜
	 * 2017.06.22 수정완료
	 * 푸드트럭 - 해당 푸드트럭의 총 리뷰 수 반환
	 */
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

	/**
	 * 황윤상
	 * 2017.06.22 수정완료
	 * 푸드트럭 - GPS기반 푸드트럭 검색 결과 총 푸드트럭 수 반환
	 * --------------------------------------------------------------------
	 * GPS정보를 바탕으로 그의 반경 1km 이내의 푸드트럭의 총 갯수를 반환한다.
	 */
	@Override
	public int getTruckListTotalContentCountByGPS(TruckVO gpsInfo) {
		return sqlSessionTemplate.selectOne("foodtruck.getTruckListTotalContentCountByGPS", gpsInfo);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 푸드트럭 - 최신순 필터링
	 * --------------------------------
	 * FOODTRUCK 테이블의 register_timeposted 을 기준으로 내림차순 정렬한다.
	 * 
	 * 동적쿼리를 이용하여 이름검색과 위치기반 검색을 구분한다.
	 */
	@Override
	public List<TruckVO> filteringByDate(PagingBean pagingbean) {
		return sqlSessionTemplate.selectList("foodtruck.filteringByDate", pagingbean);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 푸드트럭 - 즐겨찾기순 필터링
	 * -----------------------------------
	 * FOODTRUCK 테이블과 WISHLIST테이블을 foodtruck_number를 기준으로
	 * 조인하고 FOODTRUCK 테이블의 컬럼을 그룹으로 묶어 
	 * 해당 푸드트럭의 customer_id수를 카운트한다
	 * 후에 wishlist count 순을 기준으로 내림차순한다.
	 * 
	 * 동적쿼리를 이용하여 이름검색과 위치기반 검색을 구분한다.
	 */
	@Override
	public List<TruckVO> filteringByWishlist(PagingBean pagingbean) {
		return sqlSessionTemplate.selectList("foodtruck.filteringByWishlist", pagingbean);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 푸드트럭 - 평점순 필터링
	 * ------------------------------
	 * FOODTRUCK 테이블과 REVIEW테이블을 foodtruck_number를 기준으로
	 * 조인하고 FOODTRUCK 테이블의 컬럼을 그룹으로 묶어 
	 * 해당 푸드트럭의 평점을 구한다
	 * 후에 Avg grade를 기준으로 내림차순한다.
	 * 
	 * 동적쿼리를 이용하여 이름검색과 위치기반 검색을 구분한다.
	 * 
	 * nvl() : null값을 원하는 값으로 처리하기 위한 함수
	 * trunc() : 원하는 소수점까지 나타내주는 함수
	 */
	@Override
	public List<TruckVO> filteringByAvgGrade(PagingBean pagingbean) {
		return sqlSessionTemplate.selectList("foodtruck.filteringByAvgGrade", pagingbean);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 푸드트럭 - 푸드트럭 번호에 해당하는 평점 조회
	 * -----------------------------------------------------
	 * FOODTRUCK 테이블과 REVIEW 테이블을 foodtruck_number를 기준으로
	 * Outer 조인한다. (REVIEW 테이블에 없는 foodtruck_number까지 조회하기 위함)
	 */
	@Override
	public double findAvgGradeByTruckNumber(String truckNumber) {
		return sqlSessionTemplate.selectOne("foodtruck.findAvgGradeByTruckNumber", truckNumber);
	}
	/**
	 * 박다혜
	 * 2017.06.21 수정완료
	 * 푸드트럭 - 푸드트럭 번호에 해당하는 즐겨찾기 수 조회
	 * -----------------------------------------------------------
	 * FOODTRUCK 테이블과 WISHLIST 테이블을 foodtruck_number를 기준으로
	 * Outer 조인한다. (WISHLIST 테이블에 없는 foodtruck_number까지 조회하기 위함)
	 */
	@Override
	public int findWishlistCountByTruckNumber(String foodtruckNumber) {
		return sqlSessionTemplate.selectOne("foodtruck.findWishlistCountByTruckNumber", foodtruckNumber);
	}
	
	/**
	 * 정현지
	 * 2017.06.22 수정중
	 * 예약 - 메뉴 예약하기
	 * -----------------------------
	 *  예약 정보를
	 *  1. booking 테이블에 booking_number, customer_id, booking_Date를 insert한다.
	 *  2. 예약정보로부터 상세 예약 정보리스트를 불러와 booking_number를 setting하고
	 *     사용자로부터 입력받은 ,menu_id,menu_quantity를 booking_number와 함께 bookingDetail 테이블에 insert한다.
	 */
	@Override
	public void bookingMenu(BookingVO bookingVO) {
		sqlSessionTemplate.insert("foodtruck.bookingMenu", bookingVO); //Booking table에 data insert
		List<BookingDetailVO> bookingDetailList=bookingVO.getBookingDetail();
		for(int i=0; i<bookingDetailList.size();i++){
			bookingDetailList.get(i).setBookingNumber(bookingVO.getBookingNumber());
			sqlSessionTemplate.insert("foodtruck.bookingDetailMenu", bookingDetailList.get(i));
		}
	}

	/**
	 * 박다혜
	 * 2017.06.22 수정중
	 * 예약 - 사업자의 결제완료상태인 최근 예약번호 가져오기 (ajax)
	 * ----------------------------------------------------------------------
	 * 주문이 들어왔을 때 결제완료 상태이므로 
	 * 주문이 들어왔는지 알기위해 결제완료상태인 최근 예약번호를 가져온다.
	 */
	@Override
	public int getRecentlyBookingNumberBySellerId(String id) {
		return sqlSessionTemplate.selectOne("foodtruck.getRecentlyBookingNumberBySellerId", id);
	}
	/**
	 * 박다혜
	 * 2017.06.22 수정중
	 * 예약 - 사업자의 이전 예약번호 가져오기  (ajax)
	 * -------------------------------------------------------
	 * 예약 상태에 상관 없이 사업자의 최근 예약 번호를 가져온다.
	 */
	@Override
	public int getPreviousBookingNumberBySellerId(String id) {
		return sqlSessionTemplate.selectOne("foodtruck.getPreviousBookingNumberBySellerId", id);
	}
	/**
	 * 박다혜
	 * 2017.06.22 수정중
	 * 예약 - 예약번호에 해당하는 예약상태 가져오기  (ajax)
	 */
	@Override
	public String getBookingStateBybookingNumber(int bookingNumber) {
		return sqlSessionTemplate.selectOne("foodtruck.getBookingStateBybookingNumber", bookingNumber);
	}
	
	/**
	 * 황윤상
	 * 2017.06.22 수정중
	 * 푸드트럭 - gps정보의 반경 1km 내 해당하는 푸드트럭 번호 리스트를 가져온다.
	 */
	 @Override
	   public List<String> getFoodtruckNumberList(TruckVO gpsInfo) {
	      return sqlSessionTemplate.selectList("foodtruck.getFoodtruckNumberList", gpsInfo);
	   }

	 
		/**
		 * 박다혜
		 * 2017.06.22 수정중
		 * 예약 - 사용자아이디에 해당하는 이전의 예약넘버 가져오기  (ajax)
		 * --------------------------------------------------------------------------
		 * 사용자의 아이디에 해당하는 결제완료상태인 이전의 예약번호를 조회하여 반환
		 */
	@Override
	public String getPreviousBookingNumberByCustomerId(String id) {
		return sqlSessionTemplate.selectOne("foodtruck.getPreviousBookingNumberByCustomerId", id);
	}
}
