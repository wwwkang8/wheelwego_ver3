package org.asechs.wheelwego.model;

import java.util.List;

import org.asechs.wheelwego.model.vo.BookingDetailVO;
import org.asechs.wheelwego.model.vo.BookingVO;
import org.asechs.wheelwego.model.vo.ListVO;

public interface BookingService {
	List<String> findMenuIdByBookingNumber(String bookingNumber);
	ListVO customerBookingList(String pageNo, String customerId);
	List<BookingVO> getBookingList(int bookingNumber);
	public void calPoint(String usePoint, String totalAmount, int bookingNumber);
	public void minusPoint(int usePoint, int bookingNumber);
	public void addPoint(int totalAmount, int bookingNumber);
	public int getMyPoint(String customerId);
	void updateBookingState(BookingVO bookingVO);
	ListVO getBookingVO(String foodTruckNumber, String pageNo);
	List<BookingDetailVO> getBookingDetailVO(BookingVO bookingVO);
	int checkBookingState(String id);
	ListVO getPointListById(String id, String nowPage);
	void bookingMenu(BookingVO bookingVO);
	int getRecentlyBookingNumberBySellerId(String id);
	int getPreviousBookingNumberBySellerId(String id);
	String getBookingStateBybookingNumber(String bookingNumber);
	String getPreviousBookingNumberByCustomerId(String id); 
}
