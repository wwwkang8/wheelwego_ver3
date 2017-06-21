package org.asechs.wheelwego.model;

import javax.annotation.Resource;

import org.asechs.wheelwego.model.vo.MemberVO;
import org.asechs.wheelwego.model.vo.SellerVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
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
@Repository
public class MemberDAOImpl implements MemberDAO {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	public MemberVO login(MemberVO vo) {
		return sqlSessionTemplate.selectOne("member.login", vo);
	}

	@Override
	public String forgetMemberId(MemberVO vo) {
		return sqlSessionTemplate.selectOne("member.forgetMemberId", vo);
	}

	@Override
	public void updateMember(MemberVO vo) {
		sqlSessionTemplate.update("member.updateMember", vo);
	}

	@Override
	public int idcheck(String id) {
		return sqlSessionTemplate.selectOne("member.idcheck", id);
	}

	@Override
	public MemberVO findMemberById(String id) {
		return sqlSessionTemplate.selectOne("member.findMemberById", id);
	}

	@Override
	public void registerCustomer(MemberVO memberVO) {
		sqlSessionTemplate.insert("member.registerMember", memberVO);
		sqlSessionTemplate.insert("member.registerCustomer", memberVO.getId());
	}

	@Override
	public void registerSeller(MemberVO memberVO, SellerVO sellerVO) {
		sqlSessionTemplate.insert("member.registerMember", memberVO);
		sqlSessionTemplate.insert("member.registerSeller", sellerVO);
	}

	@Override
	public String getMemberPassword(String id) {
		return sqlSessionTemplate.selectOne("member.getMemberPassword", id);
	}

	@Override
	public void deleteMember(String id) {
		sqlSessionTemplate.delete("member.deleteMember", id);
	}

	@Override
	public String getMemberType(String id) {
		return sqlSessionTemplate.selectOne("member.getMemberType", id);
	}
	
    @Override
    public int forgetMemberPassword(MemberVO vo) {
       return sqlSessionTemplate.update("member.forgetMemberPassword", vo);
    }

	@Override
	public String findBusinessNumberById(String id) {
		return sqlSessionTemplate.selectOne("member.findBusinessNumberById", id);
	}
}
