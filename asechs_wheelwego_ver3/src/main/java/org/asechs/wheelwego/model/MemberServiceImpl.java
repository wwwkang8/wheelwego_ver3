package org.asechs.wheelwego.model;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.SellerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
	@Resource
	private MemberDAO memberDAO;
	@Autowired // Resource와 대비하여 Spring Framework에 종속적이지만 정밀한 DI가 가능함 
	BCryptPasswordEncoder passwordEncoder; //Bcrypt : 비밀번호 패스워드에 특화됨 
	
	@Override
	public MemberVO login(MemberVO memberVO) {
		MemberVO _memberVO = memberDAO.login(memberVO);		
		
		MemberVO result = null;

		if (_memberVO != null) {
			String rawPassword = memberVO.getPassword(); // 입력한 비밀번호
			String encodedPassword = _memberVO.getPassword(); // 암호화된 비밀번호(DB저장)

			if (passwordEncoder.matches(rawPassword, encodedPassword))
				result = _memberVO;
		}
		return result;
	}

	@Override
	public String forgetMemberId(MemberVO vo) {
		return memberDAO.forgetMemberId(vo);
	}

	@Override
	public int forgetMemberPassword(MemberVO vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		return memberDAO.forgetMemberPassword(vo);
	}

	@Override
	public void updateMember(MemberVO vo) {
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		System.out.println("정보 : " + vo);
		memberDAO.updateMember(vo);
	}

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 회원가입결과
	 */
	@Override
	public int idcheck(String id) {
		return memberDAO.idcheck(id);
	}

	@Override
	public MemberVO findMemberById(String id) {
		return memberDAO.findMemberById(id);
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

	@Override
	public void deleteMember(String id) {
		memberDAO.deleteMember(id);
	}
	
	@Override
	public String getMemberType(String id){
		return memberDAO.getMemberType(id);
	}

	@Override
	public String findBusinessNumberById(String id) {
		return memberDAO.findBusinessNumberById(id);
	}
}