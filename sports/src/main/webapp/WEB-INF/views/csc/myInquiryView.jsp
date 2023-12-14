<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/csc.css">

<div class="frame listHead" id="myInquiryView">
	<div>
		<h2>내 문의</h2>
		<a href="${cpath }/csc/inquiry"><button>≡ 목록으로</button></a>
	</div>
	<div class="viewHeader" id="myInquiryViewMain">
		<h3>${dto.title }</h3>
		<p><fmt:formatDate value="${dto.wdate }" pattern="yyyy.MM.dd HH:mm"/></p>
	</div>
	<div>
		<pre>${dto.content }</pre>
	</div>
	<c:if test="${dto.response == 1 }">
	<div class="resp">
		<div class="viewHead">
			<h3>[Sports pair] 스포츠페어 운영자의 답변입니다.</h3>
		</div>
		<div>
			안녕하십니까, 고객님.<br>
			함께하는 스포츠 취미생활 서비스 스포츠페어입니다.<br><br>
			${dto.answer }
			<br><br>
			향후 다른 문의 사항이 있으시면 언제든지 고객센터로 말씀해주시기 바랍니다.<br>
			더 나은 서비스 제공을 위해 최선을 다하는 스포츠페어가 되겠습니다.<br><br>
			감사합니다.
		</div>
	</div>
	</c:if>
	<div id="fine"></div>
</div>

<%@ include file="../footer.jsp"%>