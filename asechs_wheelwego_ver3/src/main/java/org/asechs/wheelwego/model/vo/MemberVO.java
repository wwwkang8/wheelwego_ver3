package org.asechs.wheelwego.model.vo;

public class MemberVO {
	private String id;
	private String password;
	private String memberName;
	private String postCode;
	private String address;
	private String addressDetail;
	private String phoneNumber;
	private String memberType;
	
	public MemberVO() {
		super();
	}

	public MemberVO(String id, String password, String memberName, String postCode, String address,
			String addressDetail, String phoneNumber, String memberType) {
		super();
		this.id = id;
		this.password = password;
		this.memberName = memberName;
		this.postCode = postCode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.phoneNumber = phoneNumber;
		this.memberType = memberType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setpostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", password=" + password + ", memberName=" + memberName + ", postCode=" + postCode
				+ ", address=" + address + ", addressDetail=" + addressDetail + ", phoneNumber=" + phoneNumber
				+ ", memberType=" + memberType + "]";
	}
}