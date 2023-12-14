<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/space.css">

<div class="frame listHead">
	<div>
		<h2>후기 작성</h2>
	</div>

	<form method="POST" id="revForm">
	<input type="hidden" name="member_idx" value="${login.idx }">
	<input type="hidden" name="space_idx" value="${spaceDTO.idx }">
		<table id="revtb">
			<tr>
				<td></td>
				<td>
				<h3>[${spaceDTO.spaceName }]의 후기를 작성해 주세요</h3>
				</td>
			</tr>
			<tr>
				<td>별점 : </td>
				<td>
					<input type="radio" name="score" value="5" checked>5
					<input type="radio" name="score" value="4">4
					<input type="radio" name="score" value="3">3
					<input type="radio" name="score" value="2">2
					<input type="radio" name="score" value="1">1
				</td>
			</tr>
			
			<tr>
				<td style="display: flex; align-items: flex-start;">내용 : </td>
				<td>
					<textarea rows="10" cols="30" name="content"></textarea>
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