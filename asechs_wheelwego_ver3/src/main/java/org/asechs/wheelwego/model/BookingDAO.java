package org.asechs.wheelwego.model;

import java.util.HashMap;
import java.util.List;

import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.PagingBean;
import org.asechs.wheelwego.model.vo.PointVO;
import org.asechs.wheelwego.model.vo.TruckVO;

public interface BookingDAO {
	   void bookingMenu(BookingVO bookingVO);
	   /*List<BookingVO> getBookingListBySellerId(String id);*/
	   int getRecentlyBookingNumberBySellerId(String id);
	   int getPreviousBookingNumberBySellerId(String id);
	   String getBookingStateBybookingNumber(int bookingNumber);
	   List<String> getFoodtruckNumberList(TruckVO gpsInfo);
	   String getPreviousBookingNumberByCustomerId(String id);
	   List<BookingVO> customerBookingList(PagingBean pagingBean);
	   
	   public List<BookingVO> getBookingList(int bookingNumber);
	   
	   public void minusPoint(HashMap<String, Integer> pointInfo);

	   public void addPoint(HashMap<String, Object> pointInfo);
	   
	   public int getMyPoint(String customerId);

	   void updateBookingState(BookingVO bookingVO);
	   
	   List<BookingVO> getBookingVO(String foodTruckNumber);

	   List<BookingVO> getBookingVO(PagingBean pagingBean);

	   List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO);
	   
	   public String getBookingNumberByCustomerId(String id);

	   int checkBookingState(String customerId);

	   int getTotalBookingCount(String foodTruckNumber);

	   int getTotalPointCountById(String id);

	   List<PointVO> getPointListById(PagingBean pagingBean);
	   
	   public int getCustomerBookingListCount(String customerId);
	   
	   List<String> findMenuIdByBookingNumber(String bookingNumber);
}
