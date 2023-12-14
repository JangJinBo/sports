<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
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
         <span class="mlHead">나의 즐겨찾기</span>

         <table class="sptb">
            <tr>
               <th>공간</th>
               <th>지역</th>
               <th>카테고리</th>
            </tr>
            <c:if test="${empty scrapList }">
               <tr>
                  <td colspan="3">즐겨찾기 목록이 없습니다.</td>
               </tr>
            </c:if>
            <c:if test="${not empty scrapList }">
               <c:forEach var="scrapDTO" items="${scrapList }">
                  <tr>
                     <td><a href="${cpath }/space/view/${scrapDTO.space_idx}">${scrapDTO.spaceName }</a></td>
                     <td>${scrapDTO.region }</td>
                     <td>${scrapDTO.spaceCategory }</td>
                  </tr>
               </c:forEach>
            </c:if>
         </table>
      </div>
   </div>

   <div id="adminMypageCenter">
      <div id="adminMypageReserve">
         <span class="mlHead">나의 예약</span>
         <table class="suprstb">
            <tr>
               <th>이용 시간</th>
               <th>시설명</th>
               <th>예약 취소</th>
            </tr>
            <c:if test="${empty reserveList }">
               <tr>
                  <td colspan="3">예약 정보가 없습니다.</td>
               </tr>
            </c:if>
            <c:if test="${not empty reserveList }">
               <c:forEach var="reserveDTO" items="${reserveList }">
                  <tr>
                     <td>
                     	${fn:split(reserveDTO.startTime, ':')[0] }시부터<br>
                        ${fn:split(reserveDTO.endTime, ':')[0] }시 까지
                     </td>
                     <td>
                     	<a href="${cpath }/space/view/${reserveDTO.space_idx}">${reserveDTO.spaceName }</a>
                     </td>
                     <td>
                   		<button class="cancelBtn" data-space-idx="${reserveDTO.idx}">예약 취소</button>
                     </td>
                  </tr>
               </c:forEach>
            </c:if>
         </table>
      </div>
      <!-- 지난 예약  -->
      <div id="adminPastMypageReserve">
         <span class="mlHead">지난 예약</span>
         <table class="suprstb">
            <tr>
               <th>이용 시간</th>
               <th>시설명</th>
            </tr>
            <c:if test="${empty pastReserveList }">
               <tr>
                  <td colspan="2">예약 이력이 없습니다.</td>
               </tr>
            </c:if>
            <c:if test="${not empty pastReserveList }">
               <c:forEach var="pastReserveDTO" items="${pastReserveList }">
                  <tr>
                     <td>
                     	${fn:split(pastReserveDTO.startTime, ':')[0] }시 부터<br>
                        ${fn:split(pastReserveDTO.endTime, ':')[0] }시 까지
                     </td>
                     <td>
                     	<a href="${cpath }/space/view/${pastReserveDTO.space_idx}">${pastReserveDTO.spaceName }</a>
                     </td>
                  </tr>
               </c:forEach>
            </c:if>
         </table>
      </div>
     
   </div>
</div>
<script>
   //클래스가 'cancelBtn'인 모든 버튼을 선택
   const cancelBtns = document.querySelectorAll('.cancelBtn');
   
   // 각 버튼에 대해 반복문 실행
   cancelBtns.forEach(function(cancelBtn) {
     cancelBtn.addEventListener('click', function(event) {
       let space_idx = event.target.getAttribute('data-space-idx');
       const result = confirm('예약을 취소 하시겠습니까?');
       if (result) {
         location.href = cpath + '/space/reserveCancel/' + space_idx;
       }
     });
   });
</script>
<%@ include file="../footer.jsp"%>