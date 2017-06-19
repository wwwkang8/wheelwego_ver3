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
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	/*
	 * public MemberVO login(MemberVO vo){ return memberDAO.login(vo); }
	 */
	
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

	@Override
	public int idcheck(String id) {
		return memberDAO.idcheck(id);
	}

	@Override
	public MemberVO findMemberById(String id) {
		return memberDAO.findMemberById(id);
	}

	@Override
	public void registerMember(MemberVO memberVO, String businessNumber) {
		memberVO.setPassword(passwordEncoder.encode(memberVO.getPassword()));

		if (businessNumber == null)
			memberDAO.registerCustomer(memberVO);
		else
			memberDAO.registerSeller(memberVO, new SellerVO(memberVO, businessNumber));
	}

	@Override
	public String getMemberPassword(String id, String password) {
		String encodedPassword = memberDAO.getMemberPassword(id);

		System.out.println(password);
		System.out.println(encodedPassword);

		if (passwordEncoder.matches(password, encodedPassword))
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