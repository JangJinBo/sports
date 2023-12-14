<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/csc.css">

<div class="frame listHead" id="faq">
	<div>
		<h2>자주하는 질문</h2>
	</div>
	<div id="faqMain">
		<c:forEach var="dto" items="${faqList }" varStatus="num" step="1">
		<div onclick="location.href='${cpath }/csc/faqView/${dto.idx}';">
			<div>
				<span><fmt:formatNumber value="${num.count }" pattern="00" /></span>
				<div>${dto.title }</div>
			</div>
		</div>
		</c:forEach>
	</div>
</div>

<%@ include file="../footer.jsp"%>