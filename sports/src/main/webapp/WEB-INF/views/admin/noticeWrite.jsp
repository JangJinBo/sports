<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/admin.css">
	<div class="frame">
		<div>
			<h2>공지사항 작성</h2>
		</div>
		<form method="POST" class="adform">
		<div id="inquiryRequest">
			<input type="hidden" name="type" value="notice" required>
			<div id="inquiryTitle">제목</div>
			<div class="inquiryInput"><input type="text" name="title" placeholder="제목을 입력해주세요" autofocus required autocomplete="off"></div>
			<div id="inquiryContent">내용</div>
			<div class="inquiryInput"><textarea name="content" placeholder="내용을 입력해주세요" required></textarea></div>
			<p><button type="submit" formaction="${cpath}/admin/adminWrite">작성완료</button></p>
		</div>
		</form>
	</div>


<%@ include file="../footer.jsp"%>
