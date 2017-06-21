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
	
	/**
	 * 박다혜
	 * 2017.06.21 (수정 완료)
	 * 멤버 - 로그인
	 * --------------------------
	 * DB로부터 자신의 member정보에 해당하는 reMemberVO객체가 존재한다면
	 * 자신이 입력한 패스워드와 DB에 저장된 암호화되어진 패스워드를 비교한다.
	 * 만약 일치한다면 db로부터 받아온 reMemberVO를 resultMember에 저장한다.
	 */
	@Override
	public MemberVO login(MemberVO memberVO) {
			MemberVO reMemberVO = memberDAO.login(memberVO);		
			MemberVO resultMember = null;
	
			if (reMemberVO != null) {
				String rawPassword = memberVO.getPassword(); // 사용자가 입력한 비밀번호
				String encodedPassword = reMemberVO.getPassword(); // 암호화된 비밀번호(DB저장)
				
				//사용자가 입력한 비밀번호와 암호화된 비밀번호를 매치
				if (passwordEncoder.matches(rawPassword, encodedPassword)) 
					resultMember = reMemberVO;
			}
			return resultMember;
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
	/**
	 * 박다혜
	 * 2017.06.21 (수정 완료)
	 * 멤버 - 사업자 아이디에 해당하는 사업자 번호 검색
	 */
	@Override
	public String findBusinessNumberById(String id) {
		return memberDAO.findBusinessNumberById(id);
	}
	/**
	 * 박다혜
	 * 2017.06.21 (수정 완료)
	 * 멤버 - 사업자 아이디에 해당하는 푸드트럭 번호 검색
	 */
	@Override
	public String findFoodTruckNumberById(String id) {
		return memberDAO.findFoodTruckNumberById(id);
	}

}