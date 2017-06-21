package org.asechs.wheelwego.model;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 본인 이름
 *  수정 날짜 (수정 완료)
 * 대제목[마이페이지/푸드트럭/멤버/게시판/예약] - 소제목
 * ------------------------------------------------------
 * 코드설명
 * 
 * EX)
	박다혜
	 2017.06.21 (수정완료) / (수정중)
 	마이페이지 - 마이트럭설정
	---------------------------------
	~~~~~
  */
@Service
public class MemberServiceImpl2 implements MemberService {
	@Resource
	private MemberDAO memberDAO;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Override
	public MemberVO login(MemberVO vo) {
		// TODO Auto-generated method stub
		return null;
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	멤버 - 아이디 찾기
 	기능설명 : 이름, 휴대폰 번호가 담긴 MemberVO 객체를 return 한다
  */
	@Override
	public String forgetMemberId(MemberVO vo) {
		return memberDAO.forgetMemberId(vo);
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	멤버 - 새 비밀번호 설정
 	기능설명 : 새로 설정된 비밀번호를 암호화해서 받아와 DB에 update 해준다
  */
	@Override
	public int forgetMemberPassword(MemberVO vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		return memberDAO.forgetMemberPassword(vo);
	}
	@Override
	public void updateMember(MemberVO vo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int idcheck(String id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public MemberVO findMemberById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getMemberPassword(String id, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void registerMember(MemberVO memberVO, String businessNumber) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteMember(String id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getMemberType(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String findBusinessNumberById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}