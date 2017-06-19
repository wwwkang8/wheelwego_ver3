package org.asechs.wheelwego.model.vo;

public class PointVO {
	private String bookingNumber;
	private String foodtruckName;
	private String pointType;
	private int point;
	private String pointDate;
	public PointVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PointVO(String bookingNumber, String foodtruckName, String pointType, int point, String pointDate) {
		super();
		this.bookingNumber = bookingNumber;
		this.foodtruckName = foodtruckName;
		this.pointType = pointType;
		this.point = point;
		this.pointDate = pointDate;
	}

	public String getBookingNumber() {
		return bookingNumber;
	}
	public void setBookingNumber(String bookingNumber) {
		this.bookingNumber = bookingNumber;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getPointDate() {
		return pointDate;
	}
	public void setPointDate(String pointDate) {
		this.pointDate = pointDate;
	}
	
	public String getFoodtruckName() {
		return foodtruckName;
	}
	public void setFoodtruckName(String foodtruckName) {
		this.foodtruckName = foodtruckName;
	}
	@Override
	public String toString() {
		return "PointVO [bookingNumber=" + bookingNumber + ", foodtruckName=" + foodtruckName + ", pointType="
				+ pointType + ", point=" + point + ", pointDate=" + pointDate + "]";
	}

	
}
