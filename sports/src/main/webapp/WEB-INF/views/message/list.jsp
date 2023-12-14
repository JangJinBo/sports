<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/message.css">

<div class="frame listHead">
	<c:if test="${not empty login && login.block == '1' }">
	<div>
		<h2>쪽지 보관함</h2>
		<a href="${cpath }/message/sendBannedMessage"><button>밴 해제 요청하기</button></a>
	</div>
	</c:if>
	<c:if test="${not empty login && login.block == '0' }">
	<div>
		<h2>쪽지 보관함</h2>
		<a href="${cpath }/message/sendMessage"><button>쪽지 쓰기</button></a>
	</div>
	</c:if>
	<c:if test="${not empty login && login.block == '2' }">
	<div>
		<h2>쪽지 보관함</h2>
		<a href="${cpath }/message/sendSuppMessage"><button>공급자 등록 신청</button></a>
	</div>
	</c:if>
	<div id="messageListMain">
		<div id="messageSelector" class="parent">
			<c:forEach var="dto" items="${list }" varStatus="status">
			<div class="selector ${dto.idx }" id="selector${status.index }" onclick="select('${status.index }')">
				<p>
					<span>${dto.sendId }</span>${dto.cut }
					<c:if test="${dto.counter == 'unread' }">
					<span id="checker${status.index }" class="checker">●</span>
					</c:if>
				</p>
				<p>${dto.wdate }</p>
			</div>
			
			<div class="messageView none" id="messageView${status.index }">
				<h4>${dto.sendId } | ${dto.title }</h4>
				<div>${dto.wdate }<span id="trashCan${status.index }" class="trashcan" onclick="del('${dto.idx }')">🗑️</span>
					<a href="${cpath }/message/sendMessage?receiverId=${dto.sendId}">
						<button id="resbtn">
							답장하기
						</button>
					</a>
				</div>
				<div class="messageContent" style="background-color: white;"><pre>${dto.content }</pre></div>
			</div>
			</c:forEach>
		</div>
	</div>
</div>

<script>
	async function select(sel) {
		var datas = document.querySelectorAll('div .selector')
		var msgs = document.querySelectorAll('div .messageView')
		datas.forEach(function (data) {
		 data.classList.remove('selected');
		})
		msgs.forEach(function (msg) {
		 msg.classList.remove('block')
		 msg.classList.add('none')
		})
		document.querySelector('div #selector' + sel).classList.add('selected')
		document.querySelector('div #messageView' + sel).classList.remove('none')
		document.querySelector('div #messageView' + sel).classList.add('block')
		
		var idx = document.querySelector('div #selector' + sel).classList[1]
		const url = cpath + '/message/msgChecker?idx=' + idx
		
		const result = await fetch(url).then((resp) => resp.json())
		
		const checker = document.getElementById('checker' + sel)
		if(result.ment == 'operate'){
			checker.classList.add('none')
		}
	}
	
	function del(idx) {
		if(confirm('쪽지를 삭제하시겠습니까?') == true) {
			location.href = cpath + '/message/delete/' + idx
		}
	}
</script>

<%@ include file="../footer.jsp" %>