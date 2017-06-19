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

	@Override
	public String findFoodtruckNameByMenuId(String menuId){
		return sqlSessionTemplate.selectOne("foodtruck.findFoodtruckNameByMenuId", menuId);
	}
	
	@Override
	public List<TruckVO> foodtruckList() {
		return sqlSessionTemplate.selectList("foodtruck.foodtruckList");
	}

	/* 검색 결과 푸드트럭 리스트 */
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

	/* foodtruck 정보 상세보기 */
	@Override
	public TruckVO foodtruckDetail(String foodtruckNo) {
		return sqlSessionTemplate.selectOne("foodtruck.foodtruckDetail", foodtruckNo);
	}

	/* foodtruck 상세보기에 들어갈 menu list */
	@Override
	public List<FoodVO> foodListDetail(String foodtruckNo) {
		return sqlSessionTemplate.selectList("foodtruck.foodListDetail", foodtruckNo);
	}

	@Override
	public void registerReview(ReviewVO reviewVO) {
		sqlSessionTemplate.insert("foodtruck.registerReview", reviewVO);
	}

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
