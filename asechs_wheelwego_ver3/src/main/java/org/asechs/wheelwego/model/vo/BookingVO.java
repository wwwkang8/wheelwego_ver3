package org.asechs.wheelwego.model.vo;

import java.util.List;

public class BookingVO {
	  private String bookingNumber;
	   private String customerId;
	   private String bookingDate;
	   private String bookingState;
	   private  List<BookingDetailVO> bookingDetail;
	   public BookingVO() {
	      super();
	      // TODO Auto-generated constructor stub
	   }
	   public BookingVO(String bookingNumber, String customerId, String bookingDate, String bookingState,
	         List<BookingDetailVO> bookingDetail) {
	      super();
	      this.bookingNumber = bookingNumber;
	      this.customerId = customerId;
	      this.bookingDate = bookingDate;
	      this.bookingState = bookingState;
	      this.bookingDetail = bookingDetail;
	   }
	   public String getBookingNumber() {
	      return bookingNumber;
	   }
	   public void setBookingNumber(String bookingNumber) {
	      this.bookingNumber = bookingNumber;
	   }
	   public String getCustomerId() {
	      return customerId;
	   }
	   public void setCustomerId(String customerId) {
	      this.customerId = customerId;
	   }
	   public String getBookingDate() {
	      return bookingDate;
	   }
	   public void setBookingDate(String bookingDate) {
	      this.bookingDate = bookingDate;
	   }
	   public String getBookingState() {
	      return bookingState;
	   }
	   public void setBookingState(String bookingState) {
	      this.bookingState = bookingState;
	   }
	   public List<BookingDetailVO> getBookingDetail() {
	      return bookingDetail;
	   }
	   public void setBookingDetail(List<BookingDetailVO> bookingDetail) {
	      this.bookingDetail = bookingDetail;
	   }
	   @Override
	   public String toString() {
	      return "BookingVO [bookingNumber=" + bookingNumber + ", customerId=" + customerId + ", bookingDate=" + bookingDate
	            + ", bookingState=" + bookingState + ", bookingDetail=" + bookingDetail + "]";
	   }

	   	
}

