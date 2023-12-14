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
	<c:if test="${dto.response != 1 }">
	
	<div class="resp">
		<form method="POST">
			<pre>안녕하십니까, 고객님.
함께하는 스포츠 취미생활 서비스 스포츠페어입니다.

</pre>
			<p><input type="hidden" name="idx" value="${dto.idx }"></p>
			<p>
				<textarea name="answer" placeholder="답변내용"
					style="resize: none; width: 700px; height: 300px; outline: none;
					border: 2px solid #f08080; padding: 10px;font-family: inherit;font-size: inherit;"></textarea>
			</p>
			<pre>
			
향후 다른 문의 사항이 있으시면 언제든지 고객센터로 말씀해주시기 바랍니다.
더 나은 서비스 제공을 위해 최선을 다하는 스포츠페어가 되겠습니다.

감사합니다.
			</pre>
			<p><input type="submit" value="작성 완료" style="width: 300px; padding: 10px; outline:none;
			background-color: #f08080;border: 3px solid #f0e0e0;color: white;" id="resbut"></p>
		</form>
	</div>
	</c:if>
	<div id="fine"></div>
</div>

<%@ include file="../footer.jsp"%>