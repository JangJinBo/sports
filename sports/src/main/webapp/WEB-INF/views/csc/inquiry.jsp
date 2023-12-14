<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/csc.css">



<div class="frame listHead" id="inquiry">
	<div>
		<h2>내 문의</h2><span id="comment">※ 최근 1년동안의 문의만 기록됩니다.</span>
		<div>
			<a href="${cpath }/csc/cscInquiryWrite"><button>문의하기</button></a>
			<a href="${cpath }/message/declare"><button>신고하기</button></a>
		</div>
	</div>
	<div id="myInquiry">
		<c:forEach var="dto" items="${inquiryList }">
		<div class="${dto.displayBlock ? 'block' : 'none' }" onclick="location.href='${cpath }/csc/myInquiryView/${dto.idx}';">
			<a>
				${dto.title }
				<c:if test="${dto.response == 1 }">
				<span>[1]</span>
				</c:if>
			</a>
		</div>
		
		</c:forEach>
	</div>
</div>


<%@ include file="../footer.jsp"%>