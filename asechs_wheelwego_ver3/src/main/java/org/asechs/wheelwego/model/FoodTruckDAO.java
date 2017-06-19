package org.asechs.wheelwego.model;

import java.util.List;

import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.FoodVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;

public interface FoodTruckDAO {
	String findFoodtruckNameByMenuId(String menuId);
	List<TruckVO> foodtruckList();
	List<TruckVO> searchFoodTruckList(String name);
	int getTruckListTotalContentCountByName(String name);
	List<TruckVO> searchFoodTruckByGPS(TruckVO gpsInfo);
	TruckVO foodtruckDetail(String foodtruckNo);
	List<FoodVO> foodListDetail(String foodtruckNo);
	void registerReview(ReviewVO reviewVO);
	List<ReviewVO> getReviewListByTruckNumber(PagingBean pagingBean);
	int getReivewTotalCount(String foodtruckNumber);
	void registerBookMark(WishlistVO wishlistVO);
	int getBookMarkCount(WishlistVO wishlistVO);
	List<TruckVO> getFoodTruckListByName(PagingBean pagingBean);
	/*int findTruckNumberInReview(String foodtruckNumber);*/
	int getTruckListTotalContentCountByGPS(TruckVO gpsInfo);
	List<TruckVO> getFoodTruckListByGPS(PagingBean pagingBean);
	List<TruckVO> filteringByDate(PagingBean pagingbean);
	List<TruckVO> filteringByWishlist(PagingBean pagingbean);
	List<TruckVO> filteringByAvgGrade(PagingBean pagingbean);
	double findAvgGradeByTruckNumber(String truckNumber);
	int findWishlistCountByTruckNumber(String foodtruckNumber);
	void bookingMenu(BookingVO bookingVO);
	List<BookingVO> getBookingListBySellerId(String id);
	int getRecentlyBookingNumberBySellerId(String id);
	int getPreviousBookingNumberBySellerId(String id);
	String getBookingStateBybookingNumber(int bookingNumber);
	List<String> getFoodtruckNumberList(TruckVO gpsInfo);
	String getPreviousBookingNumberByCustomerId(String id);


}
