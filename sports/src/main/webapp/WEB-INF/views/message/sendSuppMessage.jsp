<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/message.css">
					  
<div class="frame listHead">
	<div>
		<h2>가입 승인 신청</h2>
	</div>
	<input type="hidden" name="receiverId" value="admin" id="receiverInput">
	<form method="POST" id="sendMessageMain">
		<table>
			<tr>
				<td class="tname">제목</td>
				<td class="tval"><input type="text" name="title" id="titleInput" required></td>
			</tr>
			<tr>
				<td class="tname">내용</td>
				<td class="tval"><textarea name="content" required></textarea></td>
			</tr>
		</table>		
		<button type="submit">보내기</button>
	</form>	
</div>


<%@ include file="../footer.jsp" %>