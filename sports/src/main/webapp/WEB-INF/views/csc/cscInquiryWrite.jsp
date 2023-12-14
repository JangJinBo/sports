<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/csc.css">
<div class="frame" id="cscInquiryWrite">
	<div id="cscInquiryWriteMain">
		<div>
			<h2>문의하기</h2>
		</div>
		<form method="POST">
		<div id="inquiryRequest">
			<input type="hidden" name="sendId" value="${login.userid }">
			<div id="inquiryTitle">제목</div>
			<div class="inquiryInput"><input type="text" name="title" placeholder="제목을 입력해주세요" autofocus required autocomplete="off"></div>
			<div id="inquiryContent">내용</div>
			<div class="inquiryInput"><textarea name="content" placeholder="내용을 입력해주세요" required></textarea></div>
			<p><button type="submit">작성완료</button></p>
		</div>
		</form>
	</div>
</div>

<%@ include file="../footer.jsp" %>