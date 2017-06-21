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
	/**
	 * 박다혜
	 * 2017.06.21 (수정 완료)
	 * 멤버 - 로그인
	 * -------------------------
	 * 사용자가 입력한 정보(Id)에 해당하는 Member정보를 select한다.
	 */
	public MemberVO login(MemberVO vo) {
		return sqlSessionTemplate.selectOne("member.login", vo);
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	멤버 - 아이디 찾기
 	기능설명 : 이름, 휴대폰 번호를 MemberVO 객체로 받아온다
  */
	@Override
	public String forgetMemberId(MemberVO vo) {
		return sqlSessionTemplate.selectOne("member.forgetMemberId", vo);
	}

	@Override
	public void updateMember(MemberVO vo) {
		sqlSessionTemplate.update("member.updateMember", vo);
	}

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 회원가입결과
	 *	회원 아이디를 기반으로, count 값을 계산하여 값이 0이면 중복이 아닌 것으로 간주한다.
	 */
	@Override
	public int idcheck(String id) {
		return sqlSessionTemplate.selectOne("member.idcheck", id);
	}

	@Override
	public MemberVO findMemberById(String id) {
		return sqlSessionTemplate.selectOne("member.findMemberById", id);
	}

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 회원가입
	 *	회원가입 수행 시, member 테이블과, customer 테이블 둘 다 회원 정보를 삽입한다.
	 */	
	@Override
	public void registerCustomer(MemberVO memberVO) {
		sqlSessionTemplate.insert("member.registerMember", memberVO);
		sqlSessionTemplate.insert("member.registerCustomer", memberVO.getId());
	}

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 회원가입
	 *	회원가입 수행 시, member 테이블과, seller 테이블 둘 다 회원 정보를 삽입한다.
	 */	
	@Override
	public void registerSeller(MemberVO memberVO, SellerVO sellerVO) {
		//customer과 달리 매개변수를 sellerVO로 받는 이유는, id와 사업자 번호가 함께 들어가기 때문이다.
		sqlSessionTemplate.insert("member.registerMember", memberVO);
		sqlSessionTemplate.insert("member.registerSeller", sellerVO);
	}

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 패스워드 복호화
	 *	회원 아이디를 기반으로 회원의 비밀번호를 조회한다.
	 */
	@Override
	public String getMemberPassword(String id) {
		return sqlSessionTemplate.selectOne("member.getMemberPassword", id);
	}
	/** 	  
	김래현
	2017.06.21 (수정완료)
 	멤버 - 아이디 삭제
  */
	@Override
	public void deleteMember(String id) {
		sqlSessionTemplate.delete("member.deleteMember", id);
	}

	@Override
	public String getMemberType(String id) {
		return sqlSessionTemplate.selectOne("member.getMemberType", id);
	}
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	멤버 - 새 비밀번호 설정
 	기능설명 : 아이디, 이름, 휴대폰 번호를 MemberVO 객체로 받아와 비밀번호를 update 해준다
  */
    @Override
    public int forgetMemberPassword(MemberVO vo) {
       return sqlSessionTemplate.update("member.forgetMemberPassword", vo);
    }
    /**
     * 박다혜
     * 2017.06.21 (수정완료)
     * 멤버 - 사업자 아이디에 해당하는 사업자 번호 조회
     */
	@Override
	public String findBusinessNumberById(String id) {
		return sqlSessionTemplate.selectOne("member.findBusinessNumberById", id);
	}
	/**
	 * 박다혜
	 * 2017.06.21 (수정완료)
	 * 멤버 - 사업자 자신의 아이디에 해당하는 푸드트럭 넘버 조회
	 */
	@Override
	public String findFoodTruckNumberById(String id) {
		return sqlSessionTemplate.selectOne("member.findFoodTruckNumberById", id);
	}
}
