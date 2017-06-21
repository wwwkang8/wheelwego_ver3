package org.asechs.wheelwego.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asechs.wheelwego.model.MemberService;
import org.asechs.wheelwego.model.vo.MemberVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
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
@Controller
public class MemberController {
	@Resource(name="memberServiceImpl2")
	private MemberService memberService;

	/**
	 * 박다혜
	 * 2017.06.21 (수정 완료)
	 * 멤버 - 로그인
	 * ------------------------
	 * login 정보(id, password)를 MemberVO 타입으로 받아와서
	 * 해당하는 정보가 있을 경우 세션에 저장하여 home으로 보낸다.
	 *  만약 사업자라면 자신의 아이디에 해당하는 foodtruck번호를 세션에 함께 저장한다.
	 * 해당 정보가 없을 경우 login_fail로 보낸다.
	 * 
	 * @param request
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "login.do", method = RequestMethod.POST)

	public String login(HttpSession session, MemberVO vo) {
		MemberVO memberVO = memberService.login(vo);
		if (memberVO == null)
			return "member/login_fail";
		else{
			session.setAttribute("memberVO",memberVO);
			if(memberVO.getMemberType().equals("seller")){
				session.setAttribute("foodtruckNumber", memberService.findFoodTruckNumberById(memberVO.getId()));
			}
			return "redirect:home.do";
		}
	}

	/**
	 * 박다혜
	 * 2017.06.21 (수정 완료)
	 * 멤버 - 로그아웃
	 * ------------------------
	 * 세션이 존재한다면 세션을 무효화한다.
	 * @param request
	 * @return
	 */
	@RequestMapping("logout.do")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		return "redirect:home.do";
	}


	// 강정호 회원 수정 메서드
	@RequestMapping(value = "afterLogin_mypage/updateMember.do", method = RequestMethod.POST)
	public String updateMember(MemberVO vo, HttpServletRequest request) {
		memberService.updateMember(vo);
		request.getSession(false).setAttribute("memberVO", vo);
		return "mypage/updateMember_result.tiles";
	}

	// 정현지 id찾기
	@RequestMapping("forgetMemberId.do")
	@ResponseBody
	   public String forgetMemberId(MemberVO vo) {
	      return memberService.forgetMemberId(vo);
	   }
	//정현지 새 비밀번호 설정
	   @RequestMapping("forgetMemberPassword.do")
	   @ResponseBody
	   public int forgetMemberPassword(MemberVO vo) {
	     return memberService.forgetMemberPassword(vo);
	   }
	   
	   //황윤상 id체크
	   @RequestMapping("idcheckAjax.do")
	   @ResponseBody
	   public String idcheckAjax(String id) {      
	      int count=memberService.idcheck(id);
	      return (count==0) ? "ok":"fail";       
	   }


	// 황윤상 registerMember
	@RequestMapping(value = "registerMember.do", method = RequestMethod.POST)
	public String register(MemberVO memberVO, String businessNumber) {
		System.out.println(memberVO);
		memberService.registerMember(memberVO, businessNumber);
		return "redirect:registerResultView.do?id=" + memberVO.getId();
	}

	// 황윤상 registerResult
	@RequestMapping("registerResultView.do")
	public ModelAndView registerResultView(String id) {
		MemberVO memberVO = memberService.findMemberById(id);
		return new ModelAndView("member/register_result.tiles", "memberVO", memberVO);
	}

	// 김래현 회원탈퇴
	@RequestMapping("afterLogin_mypage/deleteAccount.do")
	public String deleteMember(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		MemberVO vo = (MemberVO) session.getAttribute("memberVO");
		System.out.println(vo);
		memberService.deleteMember(vo.getId());

		if (session != null)
			session.invalidate();
		return "home.tiles";
	}

	// 황윤상 비번복호화
	@RequestMapping("getMemberPasswordAjax.do")
	@ResponseBody
	public String getMemberPasswordAjax(String id, String password) {
		String result = memberService.getMemberPassword(id, password);		
		return result;
	}
}