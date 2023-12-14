<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/csc.css">

<div class="frame listHead" id="noticeView">
	<div>
		<h2>공지사항</h2>
		<a href="${cpath }/csc/notice?pageNum=${pageNum}&keyword=${keyword}&searchType=${searchType}"><button>≡ 목록으로</button></a>
	</div>
	<div class="viewHeader">
		<h3>공지 | ${dto.title }</h3>
		<p><fmt:formatDate value="${dto.wdate }" pattern="yyyy.MM.dd HH:mm"/></p>
	</div>
	<div>
		<pre>${dto.content }</pre>
	</div>
	<div id="fine">
	<c:if test="${login.memberType == 'admin' }">
		<a href="${cpath }/csc/noticemod/${dto.idx}">수정</a>|<a href="${cpath }/csc/noticedel/${dto.idx}">삭제</a>
	</c:if>
	</div>
</div>

<%@ include file="../footer.jsp"%>