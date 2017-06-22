package org.asechs.wheelwego.model;

import java.util.List;

import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.ListVO;
import org.asechs.wheelwego.model.vo.ReviewVO;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.asechs.wheelwego.model.vo.WishlistVO;

public interface FoodTruckService {
	List<TruckVO> foodtruckList();
	TruckVO foodTruckAndMenuDetail(String foodtruckNo);
	void registerBookMark(WishlistVO wishlistVO);
	int getBookMarkCount(WishlistVO wishlistVO);
	void registerReview(ReviewVO reviewVO);
	ListVO getReviewListByTruckNumber(String reviewPageNo, String foodTruckNumber);
	/*public ListVO getFoodTruckListByName(String pageNo, String name);*/
	//int getAvgGradeByTruckNumber(String foodtruckNumber);
	/*ListVO getFoodTruckListByGPS(String pageNo, TruckVO gpsInfo);*/
	ListVO filtering(String option, String name, String pageNo, TruckVO gpsInfo);
	void bookingMenu(BookingVO bookingVO); //예약
	int getRecentlyBookingNumberBySellerId(String id); //예약
	int getPreviousBookingNumberBySellerId(String id); //예약
	String getBookingStateBybookingNumber(String bookingNumber); //예약
	List<String> getFoodtruckNumberList(TruckVO truckVO);
	String getPreviousBookingNumberByCustomerId(String id); //예약
}
