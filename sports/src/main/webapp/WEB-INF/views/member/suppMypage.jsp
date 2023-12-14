<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
                 href="${cpath }/resources/style/member.css">
<div class="frame" id="mypage">
<div id="mypageMain" class="sb">
   <div id="profile">
      <div>이름</div>
      <p>${dto.username }</p>
      <div>아이디</div>
      <p>${dto.userid }</p>
      <div>닉네임</div>
      <p>${dto.nickname}</p>
      <div>번호</div>
      <p>${dto.pnum}</p>
      <div>이메일</div>
      <p>${dto.email}</p>
      <div>주소</div>
      <p>${dto.addressLocation}</p>
      
      <a href="${cpath }/member/userModify/${login.idx}">회원 정보 수정</a>
   </div>
   <!--     공급자가 등록 한 공간 리스트 -->
   <div id="mypageSpaceList">
      <span class="mlHead">나의 시설</span>
    
       <table class="sptb">
          <tr>
             <th>공간</th>
             <th>지역</th>
             <th>카테고리</th>
          </tr>
           <c:if test="${empty spaceList }">
              <tr>
                 <td colspan="3">스크랩 목록이 없습니다.</td>
              </tr>
           </c:if>
           <c:if test="${not empty spaceList }">
          <c:forEach var="spaceDTO" items="${spaceList }">
             <tr>
               <td><a href="${cpath }/space/view/${spaceDTO.idx}">${spaceDTO.spaceName }</a></td>
               <td>${spaceDTO.region}</td>
               <td>${spaceDTO.spaceCategory   }</td>
             </tr>
          </c:forEach>
          </c:if>
       </table>
   </div> 
</div>
 
 <div id="adminMypageCenter" class="sb">
    <div id="adminMypageReserve">
      <span class="mlHead">예약 현황</span>
      <table class="suprstb">
          <tr>
             <th>이용 시간</th>
             <th>시설명</th>
             <th>예약자명</th>
             <th>연락처</th>
          </tr>
           <c:if test="${empty reserveList }">
              <tr>
                 <td colspan="4">예약 정보가 없습니다.</td>
              </tr>
           </c:if>
           <c:if test="${not empty reserveList }">
          <c:forEach var="reserveDTO" items="${reserveList }">
             <tr class="status${reserveDTO.status } stat">
                <td>${fn:split(reserveDTO.startTime, ':')[0] }시<br>
               ${fn:split(reserveDTO.endTime, ':')[0] }시</td>
                <td><a href="${cpath }/space/view/${reserveDTO.space_idx}">${reserveDTO.spaceName }</a></td>
                <td>${reserveDTO.reserverName }</td>
                <td>${reserveDTO.reserverNumber }</td>
             </tr>
          </c:forEach>
          </c:if>
       </table>
    </div>

 </div>

</div>

<script>
window.onload = function() {
	var reslist = document.querySelectorAll('.stat');
	reslist.forEach(e => {
		if(e.classList[0] == 'status2') {
			e.className = 'status2 stat past';
		}
	})
}
	
</script>

<%@ include file="../footer.jsp" %>