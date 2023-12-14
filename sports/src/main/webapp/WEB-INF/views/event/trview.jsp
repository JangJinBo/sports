<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/event.css">

<div class="frame listHead eventView">
	<div>
		<h2>강습 안내</h2>
		<a href="${cpath }/event/training?pageNum=${pageNum}&searchType=${searchType}&keyword=${keyword}"><button>≡ 목록으로</button></a>
	</div>

	<div class="viewHeader">
		<h3>강습 안내 | ${eventDTO.title }</h3>
		<div class="relbox" onContextMenu="right('userid')">${eventDTO.username }
			<div class="memberbox mb${eventDTO.userid } disnone mb">
				<div class="mb"><a href="${cpath }/message/sendMessage?receiverId=${eventDTO.userid}" class="mb">✉ 쪽지보내기</a></div>
			</div>
		</div>
		<p><fmt:formatDate value="${eventDTO.wdate }" pattern="yyyy.MM.dd HH:mm"/></p>
	</div>
	<c:if test="${not empty fileList }">
         <c:forEach var="fileDTO" items="${fileList }">
            <img src="${cpath }/upload/${fileDTO.fileName }" style="width: 500px;">
         </c:forEach>
   </c:if>
	<div>
		<pre>${eventDTO.content }</pre>
	</div>
	<div class="fine">
		<c:if test="${login != null && login.username == eventDTO.username || login.memberType == 'admin'}">
		<p class="eventviewMenu">
			<c:if test="${login != null && login.username == eventDTO.username}">
			<a href="${cpath }/event/modify/${eventDTO.idx}"><button>수정</button></a>
			|
			</c:if>
			<a href="${cpath }/event/trDelete/${eventDTO.idx}"><button id="deleteBtn">삭제</button></a>	
		</p>
		</c:if>
	</div>
</div>

<script>
	const deleteBtn = document.getElementById('deleteBtn')
	deleteBtn.onclick = function deleteBtnHandler(event) {
		event.preventDefault()
		const idx = '${eventDTO.idx}'
		if ('${login.userid}' == '${eventDTO.userid }' || ${ login.memberType == 'admin'}) {
			if (confirm('삭제하시겠습니까?') == true) {
				window.location.href = cpath + '/event/trDelete/' + idx
			}
		} else {
			history.go(-1);
		}
	}
</script>
<%@ include file="../footer.jsp" %>