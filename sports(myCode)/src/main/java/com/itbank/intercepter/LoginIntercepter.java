package com.itbank.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class LoginIntercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) // 요청을 보내는 중간에 처리 함
			throws Exception {

		Object login = request.getSession().getAttribute("login"); // 세션에 저장되어있는 login을 불러온다.
		String requestURL = request.getRequestURL().toString(); // 내가 보낼 URL을 문자열 형식으로 불러온다.

		String cpath = request.getContextPath();

		if (login == null) { // login이 없으면

			String isFetch = request.getHeader("isFetch"); // 요청이 fetch인지 판별
			if ("true".equals(isFetch)) {
				response.setHeader("isRedirect", "true"); // 응답에 리다이렉트 표기
				response.getWriter().append(cpath + "/member/login?url=" + request.getHeader("referer"));
				response.getWriter().flush();
				response.getWriter().close();
				response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
			} else {
				response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
				response.sendRedirect(cpath + "/member/login?url=" + requestURL); // 함수 실행 후에 요청한 주소로 보내준다.
			}
			return false;
		}

		return true;
	}

}
