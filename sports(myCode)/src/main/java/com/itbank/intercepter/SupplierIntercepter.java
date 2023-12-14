package com.itbank.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.itbank.model.MemberDTO;

public class SupplierIntercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) // 요청을 보내는 중간에 처리 함
			throws Exception {

		MemberDTO login = (MemberDTO) request.getSession().getAttribute("login"); // 세션에 저장되어있는 login을 불러온다.
		String requestURL = request.getRequestURL().toString(); // 내가 보낼 URL을 문자열 형식으로 불러온다.

		String cpath = request.getContextPath();

		if (login == null) { // login이 없으면
			response.sendRedirect(cpath + "/member/login?url=" + requestURL); // 함수 실행 후에 요청한 주소로 보내준다.
			return false;
		}

		if (("supp".equals(login.getMemberType()) || "admin".equals(login.getMemberType())) == false) { // 공급자 or 관리자가
																										// 아닌 경우
			// 홈 페이지로 리다이렉트합니다.
			String redirectScript = "location.href = '" + cpath + "/';";

			// 스크립트를 응답에 작성하고 추가 처리를 중단하려면 false를 반환합니다.
			response.setContentType("text/html");
			response.getWriter().write("<script>" + redirectScript + "</script>");
			return false;
		}

		return true;
	}
}
