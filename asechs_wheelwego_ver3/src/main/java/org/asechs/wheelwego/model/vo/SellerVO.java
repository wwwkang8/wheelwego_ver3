package org.asechs.wheelwego.model.vo;

public class SellerVO extends MemberVO {
	private String businessNumber;

	public SellerVO() {
		super();
	}

	public SellerVO(MemberVO memberVO, String businessNumber) {
		super(memberVO.getId(), memberVO.getPassword(), memberVO.getMemberName(), memberVO.getPostCode(),
				memberVO.getAddress(), memberVO.getAddressDetail(), memberVO.getPhoneNumber(),
				memberVO.getMemberType());
		this.businessNumber = businessNumber;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	@Override
	public String toString() {
		return super.toString() + " " + "SellerVO [businessNumber=" + businessNumber + "]";
	}

}