package org.asechs.wheelwego.model;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.SellerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 본인 이름 수정 날짜 (수정 완료) 대제목[마이페이지/푸드트럭/멤버/게시판/예약] - 소제목
 * ------------------------------------------------------ 코드설명
 * 
 * EX) 박다혜 2017.06.21 (수정완료) / (수정중) 마이페이지 - 마이트럭설정
 * --------------------------------- ~~~~~
 */
@Service
public class MemberServiceImpl2 implements MemberService {
	@Resource
	private MemberDAO memberDAO;
	@Autowired // Resource와 대비하여 Spring Framework에 종속적이지만 정밀한 DI가 가능함
	BCryptPasswordEncoder passwordEncoder; // Bcrypt : 비밀번호 패스워드에 특화됨

	
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

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 회원가입결과
	 * 	회원가입시 id 중복여부를 체크하기 위해 DAO 메서드의 결과를 반환받는다.
	 */
	@Override
	public int idcheck(String id) {
		return memberDAO.idcheck(id);
	}

	@Override
	public MemberVO findMemberById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 패스워드 복호화
	 *	DB에 저장된 패스워드를 복호화 과정을 통해 사용자가 입력한 패스워드와 비교한다.
	 */
	@Override
	public String getMemberPassword(String id, String password) {
		String encodedPassword = memberDAO.getMemberPassword(id); //우선 사용자의 ID에 해당한느 패스워드를 DB에서 받아온다.

		if (passwordEncoder.matches(password, encodedPassword)) // 사용자 입력 패스워드와 DB의 패스워드를 비교하여 결과를 반환한다
			return "ok";
		else
			return "fail";
	}

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 회원가입
	 *	회원가입을 수행 시, 일반 회원과 사업자 회원을 구분한다.
	 */	
	@Override
	public void registerMember(MemberVO memberVO, String businessNumber) {
		memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword())); //회원가입에 앞서 사용자가 입려한 비밀번호를 암호화시킨다.

		if (businessNumber == null) // 사업자 번호가 없으면, 일반회원으로 가입된다.
			memberDAO.registerCustomer(memberVO); 
		else //사업자 번호가 있으면, 사업자회원으로가입된다.
			memberDAO.registerSeller(memberVO, new SellerVO(memberVO, businessNumber));
	}

	@Override
	public void deleteMember(String id) {
		memberDAO.deleteMember(id);
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