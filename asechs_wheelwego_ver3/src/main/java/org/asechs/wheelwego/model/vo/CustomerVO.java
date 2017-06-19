package org.asechs.wheelwego.model.vo;

public class CustomerVO extends MemberVO {
	public CustomerVO() {
		super();
	}

	public CustomerVO(MemberVO memberVO) {
		super(memberVO.getId(), memberVO.getPassword(), memberVO.getMemberName(), memberVO.getPostCode(), memberVO.getAddress(),
				memberVO.getAddressDetail(), memberVO.getPhoneNumber(), memberVO.getMemberType());
	}

	@Override
	public String toString() {
		return super.toString();
	}
}