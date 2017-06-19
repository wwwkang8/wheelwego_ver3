package org.asechs.wheelwego.model.vo;

public class WishlistVO {
	private String foodtruckNumber;
	private String customerId;
	public WishlistVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WishlistVO(String foodtruckNumber, String customerId) {
		super();
		this.foodtruckNumber = foodtruckNumber;
		this.customerId = customerId;
	}
	public String getfoodtruckNumber() {
		return foodtruckNumber;
	}
	public void setfoodtruckNumber(String foodtruckNumber) {
		this.foodtruckNumber = foodtruckNumber;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	@Override
	public String toString() {
		return "WishlistVO [foodtruckNumber=" + foodtruckNumber + ", customerId=" + customerId + "]";
	}
}
