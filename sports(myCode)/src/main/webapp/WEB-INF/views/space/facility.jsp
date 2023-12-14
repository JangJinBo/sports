<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/space.css">

<div class="frame listHead">
<div>
<h2>공지사항 작성</h2>
</div>

	<form method="POST" id="revForm">
		<table id="revtb">
			<tr>
				<td>제목 : </td>
				<td><input type="text" name="title" placeholder="제목을 입력해 주세요" required autofocus></td>
			</tr>
			
			<tr>
				<td style="display: flex; align-items: flex-start;">내용 : </td>
				<td>
					<textarea rows="10" cols="30" name="content" required placeholder="내용을 입력해 주세요"></textarea>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="작성 완료">
				</td>
			</tr>
		</table>
	</form>

</div>	<!-- end of div.frame -->

<%@ include file="../footer.jsp"%>

</body>
</html>