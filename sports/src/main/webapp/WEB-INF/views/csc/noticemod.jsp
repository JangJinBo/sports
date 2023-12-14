<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/admin.css">
	<div class="frame">
		<div>
			<h2>공지사항 수정</h2>
		</div>
		<form method="POST" class="adform" action="${cpath }/csc/noticemod">
		<div id="inquiryRequest">
			<input type="hidden" name="idx" value="${dto.idx }" required>
			<div id="inquiryTitle">제목</div>
			<div class="inquiryInput"><input type="text" name="title" value="${dto.title }" autofocus required autocomplete="off"></div>
			<div id="inquiryContent">내용</div>
			<div class="inquiryInput"><textarea name="content" placeholder="내용을 입력해주세요" required>${dto.content }</textarea></div>
			<p><button type="submit">수정완료</button></p>
		</div>
		</form>
	</div>


<%@ include file="../footer.jsp"%>
