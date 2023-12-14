<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/csc.css">

<div class="frame" id="notice">
	<!-- ModelAndView list -->
	<div>
		<h2>공지사항</h2>
		<div id="noticeSearch">
			<form>
				<select name="searchType">
					<option value="title">제목</option>
					<option value="content">내용</option>
					<option value="all">제목 + 내용</option>
				</select>
				<input type="text" name="keyword" autocomplete="off">
				<input type="submit" value="검색">
			</form>
		</div>
	</div>
	<div id="noticeMain">
	<c:forEach var="dto" items="${noticeList }">
	<div class="noticeMain" onclick="location.href='${cpath }/csc/noticeView/${dto.idx }?pageNum=${pageMake.pageNum}&keyword=${keyword }&searchType=${searchType }';">
		<div><span>공지</span>${dto.title }</div>
		<div>${dto.wdate }</div>
	</div>
	</c:forEach>
	</div>
	
	<c:if test="${pageMake.realEnd != 0 }">
	<div id="pageDiv">
		<ul class="pagination">
			<li class="pagination_button pageMover">
				<a href="?pageNum=1">&lt&lt</a>
			</li>
		
			<c:if test="${pageMake.prev }">
			<li class="pagination_button pageMover">
				<a href="?pageNum=${pageMake.startPage - 1 }">&lt</a>
			</li>
			</c:if>
			<c:if test="${pageMake.prev == false }">
			<li class="pagination_button pageMover">
				<a href="?pageNum=1">&lt</a>
			</li>
			</c:if>
	
			<c:forEach var="num" begin="${pageMake.startPage }" end="${pageMake.endPage }">
			<li class="pagination_button" id="${num }">
				<a href="?pageNum=${num }">${num }</a>
			</li>
			</c:forEach>
		
			<c:if test="${pageMake.next }">
			<li class="pagination_button pageMover">
			 	<a href="?pageNum=${pageMake.endPage + 1 }">&gt</a>
			</li>
			</c:if>
			<c:if test="${pageMake.next == false }">
			<li class="pagination_button pageMover">
				<a href="?pageNum=${pageMake.realEnd }">&gt</a>
			</li>
			</c:if>
			
			<li class="pagination_button pageMover">
			 	<a href="?pageNum=${pageMake.realEnd }">&gt&gt</a>
			</li>
		</ul>
	</div>
	</c:if>
</div>

<script>
	const pageNum = '${pageMake.pageNum}'
	const otherPage = document.querySelectorAll('li')
	otherPage.className = 'pagination_button'
	
	const thisPage = document.getElementById(pageNum)
	thisPage.className = 'pagination_button thisPage'
	
</script>

<%@ include file="../footer.jsp" %>