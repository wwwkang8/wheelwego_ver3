package org.asechs.wheelwego.model;

import org.asechs.wheelwego.model.vo.MemberVO;

public interface MemberService {
	
	MemberVO login(MemberVO vo);

	String forgetMemberId(MemberVO vo);

	public int forgetMemberPassword(MemberVO vo);

	void updateMember(MemberVO vo);

	int idcheck(String id);

	MemberVO findMemberById(String id);

	String getMemberPassword(String id, String password);

	void registerMember(MemberVO memberVO, String businessNumber);

	void deleteMember(String id);

	String getMemberType(String id);

	String findBusinessNumberById(String id);
}
