<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/juBoard.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>		
	<div class="frame listHead juView">
		<div>
			<h2>팀원 모집</h2>
			<a href="${cpath }/juBoard/teamBoard?pageNum=${pageNum}&searchType=${searchType}&keyword=${keyword}"><button>≡ 목록으로</button></a>
		</div>
		
		<div class="viewHeader">
			<h3>${tmdto.category} | ${tmdto.title }</h3>
			<div class="relbox" onContextMenu="right('userid')">${tmdto.username }
				<div class="memberbox mb${tmdto.userid } disnone mb">
					<div class="mb"><a href="${cpath }/message/sendMessage?receiverId=${tmdto.userid}" class="mb">✉ 쪽지보내기</a></div>
				</div>
				</div>
			<p><fmt:formatDate value="${tmdto.wdate }" pattern="yyyy.MM.dd HH:mm"/></p>
		</div>
		
		<table id="jutab">
            <tr>
               <th>지역</th>
               <td>${tmdto.mainarea}</td>
            </tr>
            <tr>
               <th>모집성별</th>
               <td>${tmdto.gender }</td>
            </tr>
            <tr>
               <th>모집연령대</th>
               <td>${tmdto.age }</td>
            </tr>
         </table>
		
		<c:if test="${not empty fileList }">
			<td class="img-container">
				<c:forEach var="fileDTO" items="${fileList }">
					<div class="imgFile">
						<img src="${cpath }/upload/${fileDTO.fileName}" style="width: 500px;">
					</div>
				</c:forEach>
			</td>
		</c:if>			
		<div class="jucon">
			<pre>${tmdto.content }</pre>
		</div>
		<div class="fine">
			<c:if test="${login.username == tmdto.username || login.memberType == 'admin'}">
			<p class="juviewMenu">
				<c:if test="${login.username == tmdto.username}">
				<button id="tmModiBtn">수정</button>
				|
				</c:if>
				<button id="tmDelBtn">삭제</button>
			</p>
			</c:if>
			<c:if test="${login != null && login.username != tmdto.username}">
			<p class="juviewMes">
				<a href="${cpath }/message/apply?receiverId=${tmdto.userid}&boardType=${tmdto.boardType}"><button onclick="toApply()">신청하기</button></a>
			</p>
			</c:if>
		</div>
<!-- 		<button id="tmBack">뒤로가기</button> -->
	
	<!-- 댓글 작성 -->
		<div>		
			<c:if test="${empty login}">
						<p><textarea name="content" id="getAlert" placeholder="로그인을 하신 후에 이용가능합니다"
				style="resize: none; width: 500px; height: 100px" readonly></textarea></p>
			</c:if>
			<c:if test="${not empty login}">
				<form method="POST" action="${cpath }/juBoard/mkReview?pageNum=${pageNum }&searchType=${searchType }&keyword=${keyword }&boardType=tmView">
					<p>작성자 : ${login.username }</p>
					<p><input type="hidden" name="userid" value="${login.userid}"></p>
					<p><input type="hidden" name="board_idx" value="${tmdto.board_idx}"></p>
					<p><textarea name="content" placeholder="댓글 입력"
					style="resize: none; width: 1194px; height: 70px"></textarea></p>
					<p><input type="submit" value="등록" style="padding: 5px 10px; background-color: white;width:60px;text-align: center;border:1px solid #dadada;"></p>
				</form>
			</c:if>
			
 			<c:forEach var="reviewDTO" items="${reviewList}">
 				<section id="reV">
 					<div class="relbox" onContextMenu="right('userid')">
 					${reviewDTO.username }
				<div class="memberbox mb${reviewDTO.userid } disnone mb">
					<div class="mb"><a href="${cpath }/message/sendMessage?receiverId=${reviewDTO.userid}" class="mb">✉ 쪽지보내기</a></div>
				</div>
				
						<c:if test="${login.userid eq reviewDTO.userid || login.memberType eq 'admin' }">
							<button onclick="reviewDel('${reviewDTO.idx }')">❌</button>
						</c:if>
 					</div>
						<pre style="width: 1180px; height: 200px; background-color: #eee; padding: 0 10px;">${reviewDTO.content }</pre>
					
 				</section>
			</c:forEach>  
		</div>
		
	</div>
	<script>
			const tmDelBtn = document.getElementById('tmDelBtn')
		tmDelBtn.onclick = function tmDelHandler(event) {
			event.preventDefault() 
			const idx = '${tmdto.idx}'
			if('${login.userid}' == '${tmdto.userid }' || '${login.userid}' == 'admin') {
				if(confirm('삭제하시겠습니까?') == true) {						
					window.location.href = cpath + '/juBoard/tmDel/' + idx
				}
			}
			else {
				alert('작성자만 게시글을 삭제할 수 있습니다')
			}
		}
	</script>	
	<script>
		const tmModiBtn = document.getElementById('tmModiBtn')
		tmModiBtn.onclick = function tmModiHandler(event) {
			event.preventDefault() 
			const idx = '${tmdto.idx}'
			if('${login.userid}' == '${tmdto.userid }') {
				window.location.href = cpath + '/juBoard/tmModi/' + idx 
			}
			else {
				alert('작성자만 게시글을 수정할 수 있습니다')
			}
		}
	</script>
	<script>
		function toApply() {
			location.href = '${cpath }/message/apply?receiverId=${tmdto.userid}&boardType=${tmdto.boardType}'
		}
	</script>
	<script>
	// 댓글 삭제 스크립트
		function reviewDel(seq){
				$.ajax({
					type : 'post',
					url : cpath + '/juBoard/delRev/'+seq,
					data : {idx : seq
						},
					success : function(result) {
						if(result == 'success'){
							alert("댓글이 삭제되었습니다.");
							location.reload();
						}else{
							alert("삭제에 실패하셨습니다. \n 계속적으로 발생시 관리자에게 문의해주세요")
						}
					}
				})
			}
	</script>

<%@ include file="../footer.jsp" %>
</body>
</html>