<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>   
<div class="frame" id="admin">
   <div class="dash">
      <a class="dashHead" href="${cpath }/message/list">
         <span>밴 해제/공급자 승인 요청</span>
         <span>+</span>
      </a>
      <div id="admemscroll">
         <div class="bannedcont">
            <c:forEach var="bannedDTO" items="${bannedMemberList}">
               <div class="adsel${bannedDTO.idx }  adr${bannedDTO.counter } bannedcon adTitle">
                  <div>
                     <span>${bannedDTO.username}</span> 
                     <span>${bannedDTO.title}</span>
                  </div>
                  <span style="width: 100px;">${bannedDTO.wdate}</span>
               </div>   
                  <div class="content content-row">
                     <pre>${bannedDTO.title }<br><br>${bannedDTO.content}</pre>
                  </div>
            </c:forEach>
         </div>
      </div>
   </div>
   <div class="dash">
      <a class="dashHead">
         <span>문의/답변</span>
         <span>+</span>
      </a>
      <div id="admemscroll">
         <div class="bannedcont">
            <c:forEach var="inquiryDTO" items="${inquiryList }">
            <div class="bannedcon adTitlei" onclick="res('${inquiryDTO.idx}')">
               <div>
                  <span>${inquiryDTO.sendId }</span>
                  <span>${inquiryDTO.title }</span>
               </div>
               <span style="width: 100px;">${inquiryDTO.wdate }</span>
            </div>
            </c:forEach>
         </div>
      </div>
   </div>
   <div class="dash">
      <a class="dashHead" href="${cpath }/message/list">
         <span>신고</span>
         <span>+</span>
      </a>
      <div id="admemscroll">
         <div class="bannedcont">
            <c:forEach var="messDTO" items="${messageDeclar}">
            <div class="adsel${messDTO.idx } adr${messDTO.counter } bannedcon adTitles">
               <div>
                  <span>${messDTO.username }</span>
                  <span>${messDTO.title }</span>
               </div>
               
               <span style="width: 100px;">${messDTO.wdate }</span>   
            </div>
            <div class="content content-rows">
                  <pre>${messDTO.title }<br><br>${messDTO.content}</pre>
               </div>
            </c:forEach>
         </div>
      </div>
   </div>
   <div class="dash">
      <a class="dashHead">
         <span>회원관리</span>
         <span>+</span>
      </a>
      <div id="admemscroll">
         <div>
            <div id="admemlist">
               <form method="GET" action="${cpath }/admin/admin">
                  <select name="searchType">
                     <option value="wholeKeyword">전체</option>
                     <option value="userid">아이디</option>
                     <option value="username">유저명</option>
                     <option value="nickname">닉네임</option>
                  </select>
                     <input type="text" name="keyword" placeholder="검색해주세요" autocomplete="off">
                     <input type="submit" value="검색">
               </form>
               <div id="admembutton">
                  <a href="${cpath }/admin/admin?type=ban"><button>밴</button></a>
                  <a href="${cpath }/admin/admin?type=sign"><button>승인</button></a>
               </div>
            </div>         
                  <c:if test="${not empty searchMemberList }">
            <table id="admemtb">
               <thead>
                  <tr>
                     <th>아이디</th>
                     <th>유저명</th>
                     <th>닉네임</th>
                     <th>밴여부</th>
                  </tr>
               </thead>
               <tbody>
               
                  <!-- 검색을 하지 않았을 시 멤버 정보를 띄우지 않는다 -->
                     <c:forEach var="memDTO" items="${searchMemberList }">
                        <tr>
                           <td>${memDTO.userid }</td>
                           <td>${memDTO.username }</td>
                           <td>${memDTO.nickname }</td>

                           <c:if test="${memDTO.block == '0'}">                     
                              <td><span class="bspan" onclick="doBlock('${memDTO.idx}')">❌</span></td>
                           </c:if>
                           
                           <c:if test="${memDTO.block == '1' }">                     
                              <td><span class="uspan" onclick="unDoBlock('${memDTO.idx}')">해제</span></td>
                           </c:if>
                           
                           <c:if test="${memDTO.block == '2' }">                     
                              <td><span class="sspan" onclick="unDoBlockSupp('${memDTO.idx}')">승인✔</span></td>
                           </c:if>
                        </tr>
                     </c:forEach>                  
               </tbody>
            </table>
                  </c:if>      
            
         </div>
      </div>
   </div>
</div>

<script>
   function doBlock(seq){
      $.ajax ({
         type : 'post',
         url : cpath + '/admin/doBlock/'+seq,
         data : {idx : seq
            },
         success : function(result) {
            if(result == 'success') {
               alert("블락 성공하였습니다");
               location.reload();
            }else {
               alert("블락 실패!")
            }
         }
      })
   }
</script>

<script>
   function unDoBlock(seq) {
      $.ajax ({
         type : 'post',
         url : cpath + '/admin/unDoBlock/'+seq,
         data : {idx : seq
            },
         success : function(result) {
            if(result == 'success') {
               alert("블락 해제하였습니다");
               location.reload();
            }else {
               alert("블락 해제실패!")
            }
         }
      })      
   }
</script>

<script>
   function unDoBlockSupp(seq) {
      $.ajax ({
         type : 'post',
         url : cpath + '/admin/unDoBlockSupp/'+seq,
         data : {idx : seq
            },
         success : function(result) {
            if(result == 'success') {
               alert("등록을 승인하였습니다");
               location.reload();
            }else {
               alert("등록 승인 실패!")
            }
         }
      })      
   }
</script>


<!-- 공지사항 시작 -->
<script>
    // JavaScript 코드
    const titleElements = document.querySelectorAll('.adTitle');
    const contentRows = document.querySelectorAll('.content-row');

    titleElements.forEach((titleElement, index) => {
        titleElement.addEventListener('click', () => {
            contentRows[index].classList.toggle('show-content');
            titleElement.classList.toggle('sel-title');
            titleElement.classList.add('adselected')
            var idx = titleElement.classList[0].substring(5)
            adseler()
            async function adseler() {
               const url = cpath + '/message/msgChecker?idx=' + idx
               const result = await fetch(url).then((resp) => resp.json())
            }
        });
    });
    
    // JavaScript 코드
    const titleElementss = document.querySelectorAll('.adTitles');
    const contentRowss = document.querySelectorAll('.content-rows');

    titleElementss.forEach((titleElement, index) => {
        titleElement.addEventListener('click', () => {
            contentRowss[index].classList.toggle('show-content');
            titleElement.classList.toggle('sel-title');
            titleElement.classList.add('adselected')
            var idx = titleElement.classList[0].substring(5)
            adseler()
            async function adseler() {
               const url = cpath + '/message/msgChecker?idx=' + idx
               const result = await fetch(url).then((resp) => resp.json())
            }
        });
    });
    window.onload = function() {
       titleElements.forEach(e => {
          if(e.classList[1] == 'adrread'){
              e.style.backgroundColor = '#aaa';
          }
       })
       titleElementss.forEach(e => {
          if(e.classList[1] == 'adrread'){
              e.style.backgroundColor = '#aaa';
          }
       })
       
       
       var adselected = document.querySelectorAll('.adselected')
       adselected.forEach(e => {
          e.style.backgroundColor = '#aaa';
       })
       
    }
</script>
<!-- 공지사항 끝 -->

<script>
   function res(idx) {
      location.href = '${cpath }/admin/adminInquiryView/' + idx
   }
</script>


<%@ include file="../footer.jsp" %>