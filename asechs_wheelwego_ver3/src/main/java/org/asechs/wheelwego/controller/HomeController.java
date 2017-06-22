package org.asechs.wheelwego.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.asechs.wheelwego.model.FoodTruckService;
import org.asechs.wheelwego.model.vo.TruckVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class HomeController {
	/*
	 * @PathVariable  어노테이션 
	 * : 요청 url 정보를 변수로 할당받기 위한 어노테이션 
	 * 요청 url과 일치하는 url 매핑 메서드가 존재하면 그 메서드가 
	 * 우선적으로 실행되고 
	 * 존재하지 않으면 @PathVariable 메서드가 실행된다 
	 */
	@Resource(name="foodTruckServiceImpl")
	private FoodTruckService foodTruckService;

	/**
	 *	황윤상
	 *	2017.06.21 (수정 완료)
	 *	멤버 - 현재 위치정보 가져오기
	 *	home.do가 실행되면, 자바스크립트 기반의 JSP에서 위치정보 가져오고, main.do를 실행한다.
	 *  즉, main.do가 기존의 home.do를 대체하는 것이다.
	 */	
	@RequestMapping("home.do")
	public String home() {
		return "member/getLocation";
	}
	
	/** 	  
	정현지
	2017.06.21 (수정완료)
 	푸드트럭 - main 랜덤 리스트
 	기능설명 : 푸드트럭 리스트를 받아온 뒤,
 			Collections.shuffle()을 사용하여 음식목록(foodList)를 랜덤으로 뿌려준다
 	황윤상		
 	2017.06.21 (수정 완료)
 	멤버 - 현재 위치정보를 세션에 저장
 	기능설명 : 메인 페이지를 호출하면, 현재의 위치정보를 세션에 저장함으로써, 페이지를 이동해도 위치정보를 유지토록 한다.		
	 */		
	@RequestMapping("main.do")
	public ModelAndView foodtruckList(HttpServletRequest request, String latitude, String longitude) {		
		HttpSession session=request.getSession();
		session.setAttribute("latitude", Double.parseDouble(latitude));
		session.setAttribute("longitude", Double.parseDouble(longitude));
		List<TruckVO> truckList = foodTruckService.foodtruckList();
		Collections.shuffle(truckList); 
		return new ModelAndView("main_home.tiles", "trucklist", truckList);
	}

	@RequestMapping("{viewName}.do")
	public String showView(@PathVariable String viewName){
		return viewName+".tiles";
	}
	/*로그인 인증 서비스 없이 사용 가능한 요청*/
	@RequestMapping("{dirName}/{viewName}.do")
	public String showView(@PathVariable String dirName,
			@PathVariable String viewName){
		System.out.println("no check session");
		return dirName+"/"+viewName+".tiles";
	}
	/*로그인 인증 서비스가 필요한 요청*/
	@RequestMapping("afterLogin_{dirName}/{viewName}.do")
	public String showViewAfterLogin(@PathVariable String dirName,
			@PathVariable String viewName){
		System.out.println("AfterLogin");
		return dirName+"/"+viewName+".tiles";
	}
}
