package org.asechs.wheelwego.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/*
	 Spring Handler Interceptor : 
	DispatcherServlet이 해당 컨트롤러를 호출하기 전,후에 요청과 응답을
	제어하는 역할을 한다. 
	 
	컨트롤러 실행전  preHandle(request,response,handler) 
	컨트롤러 실행후 postHandle(request,response,handler)
	응답완료 afterCompletion(request,response,handler)
	Spring에서 제공하는 HandlerInterceptorAdapter 를 상속받아 
	위와 같은 메서드를 오버라이딩해서 사용한다
	
	
	DispatcherServlet -- HandlerInterceptor -- Handler(Controller)
	                            컨트롤러 영역의 
	                            공통관심사항을 일괄처리
	                            
	                            로그인 인증여부 체크
	                            로그인되어 있지 않은 사용자 요청은 
	                             요청에 해당하는 핸들러(컨트롤러)를 실행하지 않고 index로 redirect
	                            로그인

 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	/**
	 * 해당 컨트롤러 수행전에 실행(제어) 되는 메서드
	 * preHandle 메서드에서 true를 반환하면 기존 컨트롤러가 실행하고
	 * false를 반환하면 기존 컨트롤러는 실행되지 않는다.
	 * 
	 * 인증여부 확인하여 인증되어 있으면 true를 반환
	 * 인증되어 있지 않으면
	 *  index로 리다이렉트 후 false를 반환
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("***********핸들러 인터셉터 실행************"+request.getRequestURI());
		HttpSession session=request.getSession(false);
		if(session != null && session.getAttribute("memberVO")!=null){
			System.out.println("인터셉터 true");
			return true;
		}else{
			response.sendRedirect(request.getContextPath()+"/home.do");
			return false;
		}
	}
}
