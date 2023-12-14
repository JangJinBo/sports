<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/space.css">

<div class="frame listHead">
	<div>
		<h2>휴일 추가</h2>
	</div>
	<form class="regForm" method="POST">
		<input type="hidden" name="member_idx"value="${login.idx}">
		<p>
			<input type="date" name="startDay" min="${today}" 
					value="${empty param.startTime ? today : param.startTime}">
			<select name="startTime">
				<c:forEach var="i" begin="0" end="23">
					<fmt:formatNumber var="startHH" value="${i }" pattern="00" />
					<option value="${startHH }">${startHH }:00</option>
				</c:forEach>
			</select>
			~ 
			<input type="date" name="endDay" min="${today}" 
					value="${empty param.endTime ? today : param.endTime }">
			<select name="endTime">
				<c:forEach var="i" begin="0" end="23">
					<fmt:formatNumber var="endHH" value="${i }" pattern="00" />
					<option value="${endHH }">${endHH }:00</option>
				</c:forEach>
			</select>
		</p>
		<p>
			<input type="text" name="reserverName" placeholder="ex)정기휴일" required>
			<input type="submit" value="휴일 등록">
		</p>
	</form>
	
	<table id="holtb">
		<thead>
			<tr>
				<th>휴일 날짜</th>
				<th>휴일 설명</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="holiday" items="${holidayList }">
				<tr>
					<td>${holiday.startTime } ~ ${holiday.endTime }</td>
					<td>${holiday.reserverName }</td>
					<td><a href="${cpath }/space/holidayDelete/${spaceDTO.idx }/${holiday.idx}"><span>🗑</span></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
				
	
</div>	<!-- end of div.frame -->

<%@ include file="../footer.jsp"%>



</body>
</html>