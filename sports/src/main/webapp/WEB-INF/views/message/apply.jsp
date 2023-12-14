<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/message.css">
					  
<div class="frame listHead">
	<div>
		<h2>신청하기</h2>
	</div>
	<form method="POST" id="sendMessageMain">
				<input type="hidden" name="title" id="titleInput" value="${title }" required>
		<table>
			<tr>
				<td class="tname">신청 내용</td>
				<td class="tval"><textarea name="content" placeholder="성별, 연령, 활동 지역, 활동 경력 등                   간단하게 소개해주세요." required autofocus></textarea></td>
			</tr>
		</table>		
		<button type="submit">보내기</button>
	</form>	
</div>

<%@ include file="../footer.jsp" %>