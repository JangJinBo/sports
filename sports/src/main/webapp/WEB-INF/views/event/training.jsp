<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" href="${cpath }/resources/style/event.css">

<div class="frame event" id="competition">
   
   <div>
      <div class="miniNav">
         <h2 class="listselected"><a href="${cpath }/event/training">강습 안내</a></h2>
         <h2 class="listnother"><a href="${cpath }/event/competition">대회 공지 안내</a></h2>
      </div>
      <div id="trainingSearch" class="eventSearch">
         <form method="GET" action="${cpath }/event/training">
            <select name="searchType">
               <option value="title">제목</option>
               <option value="content">내용</option>
               <option value="all">제목 + 내용</option>
            </select>
            <input type="text" name="keyword" autocomplete="off">
            <input type="submit" value="검색">
         </form>
      </div>
      <a href="${cpath }/event/write/${boardType}"><button>🖊 글쓰기</button></a>
   </div>
   
   <table>
      <thead>
         <tr>
            <th scope="col" class="td_idx">번호</th>
            <th scope="col" class="td_title">제목</th>
            <th scope="col" class="td_writer">작성자</th>
            <th scope="col" class="td_date">작성일</th>
         </tr>
      </thead>
      <tbody>
         <c:forEach var="dto" items="${trList }" >
			<tr class="toEventView" onclick="select('${dto.idx }')">
				<td class="cen">${dto.idx}</td>
				<td>${dto.title }</td>
				<td class="cen userclick relbox" onContextMenu="right('userid')">${dto.username}
				<div class="memberbox mb${dto.userid } disnone mb">
					<div class="mb"><a href="${cpath }/message/sendMessage?receiverId=${dto.userid}" class="mb">✉ 쪽지보내기</a></div>
				</div>
				</td>
				<td class="cen">${dto.wdate }</td>
			</tr>
			</c:forEach>
      </tbody>
   </table>
   
   <!-- 페이징 버튼 시작 -->
   <c:if test="${pageMake.realEnd != 0 }">
   <div id="pageDiv">
      <ul class="pagination">
         <li class="pagination_button pageMover">
            <a href="?pageNum=1&searchType=${searchType }&keyword=${keyword}">&lt&lt</a>
         </li>
      
         <c:if test="${pageMake.prev }">
         <li class="pagination_button pageMover">
            <a href="?pageNum=${pageMake.startPage - 1 }&searchType=${searchType }&keyword=${keyword}">&lt</a>
         </li>
         </c:if>
         <c:if test="${pageMake.prev == false }">
         <li class="pagination_button pageMover">
            <a href="?pageNum=1&searchType=${searchType }&keyword=${keyword}">&lt</a>
         </li>
         </c:if>
   
         <c:forEach var="num" begin="${pageMake.startPage }" end="${pageMake.endPage }">
         <li class="pagination_button" id="${num }">
            <a href="?pageNum=${num }&searchType=${searchType }&keyword=${keyword}">${num }</a>
         </li>
         </c:forEach>
      
         <c:if test="${pageMake.next }">
         <li class="pagination_button pageMover">
             <a href="?pageNum=${pageMake.endPage + 1 }&searchType=${searchType }&keyword=${keyword}">&gt</a>
         </li>
         </c:if>
         <c:if test="${pageMake.next == false }">
         <li class="pagination_button pageMover">
            <a href="?pageNum=${pageMake.realEnd }&searchType=${searchType }&keyword=${keyword}">&gt</a>
         </li>
         </c:if>
         
         <li class="pagination_button pageMover">
             <a href="?pageNum=${pageMake.realEnd }&searchType=${searchType }&keyword=${keyword}">&gt&gt</a>
         </li>
      </ul>
   </div>
   </c:if>
   <!-- 페이징 버튼 끝 -->

</div>

<script>
   function select(sel) {
      location.href = cpath + '/event/trview/' + sel + '?pageNum=${pageMake.pageNum}&searchType=${searchType }&keyword=${keyword}'
   }
   
   // 페이징
   const pageNum = '${pageMake.pageNum}'
   const otherPage = document.querySelectorAll('li')
   otherPage.className = 'pagination_button'
   
   const thisPage = document.getElementById(pageNum)
   thisPage.className = 'pagination_button thisPage'
</script>

<%@ include file="../footer.jsp" %>