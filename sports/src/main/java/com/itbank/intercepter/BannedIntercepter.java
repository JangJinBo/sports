package com.itbank.intercepter;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.itbank.model.MemberDTO;

public class BannedIntercepter implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) // 요청을 보내는 중간에 처리함
			throws Exception {

		MemberDTO login = (MemberDTO) request.getSession().getAttribute("login"); // 세션에 저장되어있는 login을 불러온다.
		String cpath = request.getContextPath();

		if (login != null) {
			if ("0".equals(login.getBlock()) == false) { // "0"이 아닌 경우 차단 상태라면
				// 사용자가 밴 처리된 경우, 경고 메시지를 표시하고 홈 페이지로 리다이렉트합니다.
				String alertMessage = "1. 밴 당한 계정일 수 있습니다.\n2. 공급자의 경우 공급자 등록 승인이 필요합니다.\n\n관리자에게 문의하세요\n쪽지함에서 메시지(밴 해제 요청)을 보내주세요";
				String encodedMessage = URLEncoder.encode(alertMessage, "UTF-8").replace("+", "%20");
				String alertScript = "alert(decodeURIComponent('" + encodedMessage + "'));";
				String redirectScript = "location.href = '" + cpath + "/message/list';";

				// 경고 및 리다이렉트 스크립트를 결합합니다.
				String fullScript = alertScript + redirectScript;

				// 스크립트를 응답에 작성하고 추가 처리를 중단하려면 false를 반환합니다.
				response.setContentType("text/html");
				response.getWriter().write("<script>" + fullScript + "</script>");
				return false;
			}
		}

		return true;
	}
}
