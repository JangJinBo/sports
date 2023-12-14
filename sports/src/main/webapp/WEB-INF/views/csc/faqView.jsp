<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/csc.css">

<div class="frame listHead" id="faqView">
	<div>
		<h2>자주하는 질문</h2>
		<a href="${cpath }/csc/faq"><button>≡ 목록으로</button></a>
	</div>
	<div class="viewHeader">
		<h3>${dto.title }</h3>
	</div>
	<div>
		<pre>${dto.content }</pre>
	</div>
	<div id="fine">
	<c:if test="${login.memberType == 'admin' }">
		<a href="${cpath }/csc/faqmod/${dto.idx}">수정</a>|<a href="${cpath }/csc/faqdel/${dto.idx}">삭제</a>
	</c:if>
	</div>
</div>

<%@ include file="../footer.jsp"%>