package org.asechs.wheelwego.model;

import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.SellerVO;

public interface MemberDAO {
	 MemberVO login(MemberVO vo);

	String forgetMemberId(MemberVO vo);

	public int forgetMemberPassword(MemberVO vo);

	void updateMember(MemberVO vo);

	int idcheck(String id);

	MemberVO findMemberById(String id);

	void registerCustomer(MemberVO memberVO);

	void registerSeller(MemberVO memberVO, SellerVO sellerVO);

	String getMemberPassword(String id);

	void deleteMember(String id);

	String getMemberType(String id);

	String findBusinessNumberById(String id);
}
