<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="cpath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sports Pair</title>
<link type="text/css" rel="stylesheet"
   href="${cpath }/resources/style/style.css">
<link rel="stylesheet"
   href="https://fonts.googleapis.com/css?family=Noto+Sans+KR">
<script>
   const cpath = '${cpath}'
</script>

</head>
<body>
   <header>
      <div id="mainHeader">
         <div id="headerTop">
            <!-- Sports Pair -->
            <a href="${cpath }/"> <img class="homeBtn"
               src="${cpath }/resources/image/LogoNew.png" width="400px"
               style="margin: 10px 50px;">
            </a>
         </div>
      </div>
   </header>

   <div id="fixedMain">
      <div id="headerStatus" class="sb">
         <div id="clock" style="margin: 15px; text-align: center;"></div>
         <div id="mainLogo" class="hidden">
            <a href="${cpath }/" style="background-color: #EEEEEE;"> <img
               src="${cpath }/resources/image/LogoNew.png" height="80px"></a>
         </div>
         <div id="memberStatus" class="sb" style="margin: 20px 0;">
            <c:if test="${not empty login }">
               <div style="margin-right: 15px;">
                  <a href="${cpath }/member/logout">로그아웃</a>
               </div>
               <c:if test="${login.memberType eq 'supp' }">
                  <div>
                     <a href="${cpath }/member/suppMypage/${login.idx}">${login.username}님[${login.userid }]</a>
                  </div>
               </c:if>
               <c:if test="${login.memberType eq 'user' }">
                  <div>
                     <a href="${cpath }/member/mypage/${login.idx}">${login.username}님[${login.userid }]</a>
                  </div>
               </c:if>
               <c:if test="${login.memberType eq 'admin' }">
                  <div>관리자[${login.userid }]</div>
               </c:if>
               <div id="tomsg">
                  <a href="${cpath }/message/list"> <span id="msgicon">✉</span>
                     <span id="countmsg" class="${login.userid }"></span>
                  </a>
               </div>
            </c:if>
            <c:if test="${empty login }">
               <!--     ================ 모달창============ -->
               <button id="login" class="modalBtn" href="#myModal1">로그인</button>
               <!-- The Modal -->
               <div id="myModal1" class="modal">

                  <!-- Modal content -->
                  <div class="modalContent">
                     <div class="modalHeader">
                        <span class="closeModal">×</span> <img
                           src="${cpath }/resources/image/LogoNew.png">
                     </div>
                     <div class="modalBody">
                        <div id="loginForm">
                           <div>Log In</div>
                           <hr>
                           <form method="POST" action="${cpath }/member/login">
                              <p>
                                 <input type="text" name="userid" placeholder="ID" required
                                    autofocus autocomplete="off">
                              </p>
                              <p>
                                 <input type="password" name="userpw" placeholder="Password"
                                    required>
                              </p>
                              <p>
                                 <input type="submit" value="로그인">
                              </p>
                              <input type="hidden" name="url" value="${param.url }">
                           </form>
                        </div>
                        <button id="reissuePW" class="modalBtn" href="#myModal2">임시
                           비밀번호 재발급</button>

                        <!-- The Modal -->
                        <div id="myModal2" class="modal" style="padding-top: 15%;">

                           <!-- Modal content -->
                           <div class="modalContent">
                              <div class="modalHeader">
                                 <span id="reissuePwClose" class="closeModal">×</span> <img
                                    src="${cpath }/resources/image/LogoNew.png">
                              </div>
                              <div class="modalBody">
                                 <div id="reissuePW">
                                    <div>임시 비밀번호 발급</div>
                                    <hr>
                                    <form id="reissuePWForm" action="${cpath}/member/reissuePW">
                                       <p>
                                          <input id="useridPw" type="text" name="userid"
                                             placeholder="ID" required autocomplete="off" autofocus>
                                       </p>
                                       <p>
                                          <input type="email" name="email" placeholder="Email"
                                             required>
                                       </p>
                                       <p>
                                          <input type="text" name="authNumber" placeholder="인증번호 입력"
                                             required> <input type="button"
                                             class="sendAuthNumber" value="인증번호 발송">
                                       </p>
                                       <p>
                                          <input id="reissueBtn" type="submit" value="임시비밀번호 발급">
                                       </p>
                                    </form>
                                 </div>
                              </div>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>

               <button class="modalBtn" href="#myModal3">회원가입</button>
               <!-- The Modal -->
               <div id="myModal3" class="modal">

                  <!-- Modal content -->
                  <div class="modalContent">
                     <div class="modalHeader">
                        <span id="joinClose" class="closeModal">×</span> <img
                           src="${cpath }/resources/image/LogoNew.png">
                     </div>
                     <div class="modalBody">
                        <div id="joinFormStyle">
                           <div>Join Us</div>
                           <hr>
                           <form id="joinForm" method="POST"
                              action="${cpath }/member/join">
                              <p>
                                 <select name="memberType">
                                    <option value="user">일반회원</option>
                                    <option value="supp">공급자</option>
<!--                                     <option value="admin">관리자</option> -->
                                 </select>
                              </p>
                              <p>
                                 <input id="joinUserId" type="text" name="userid"
                                    placeholder="ID" required autofocus autocomplete="off"
                                    style="margin-left: 15px;"> <span
                                    style="font-weight: bold; color: lightgrey;" id="span">✓</span>
                              </p>
                              <p>
                                 <input type="password" name="userpw" placeholder="Password"
                                    required>
                              </p>
                              <p>
                                 <input type="text" name="nickname" placeholder="Nick Name"
                                    required style="margin-left: 15px;"> <span
                                    style="font-weight: bold; color: lightgrey;" id="span2">✓</span>
                              </p>
                              <p>
                                 <input type="text" name="username" placeholder="Name"
                                    required>
                              </p>
                              <p>
                                 <input type="text" name="pnum" placeholder="Phone Number"
                                    required>
                              </p>
                              <p>
                                 <input id="email" type="email" name="email"
                                    placeholder="Email" required>
                              </p>
                              <p>
                                 <input type="text" id="postcode" placeholder="우편번호" required
                                    readonly style="margin-left: 104px;"> <input
                                    type="button" id="execDaum" value="우편번호 찾기"><br>
                                 <input type="text" id="addr" placeholder="주소" required><br>
                                 <input type="text" id="detailAddr" placeholder="상세주소">
                              </p>
                              <p>
                                 <input type="text" name="authNumber" placeholder="인증번호 입력"
                                    required style="margin-left: 104px;"> <input
                                    type="button" class="sendAuthNumber" value="인증번호 발송">
                              </p>
                              <p>
                                 <input type="submit" value="회원가입">
                              </p>
                           </form>
                        </div>
                     </div>
                  </div>
               </div>
            </c:if>
         </div>
      </div>
      <ul id="menu" class="sb">
         <!-- 대여게시판(rentBoard) -->
        <li class="navBtn">
        	<a href="${cpath }/space/list">대여서비스</a>
        </li>
		<li class="navBtn"><a href="${cpath }/juBoard/substiBoard">팀업</a>
			<!-- 함께해요 게시판(with Us Board) -->
			<ul class="drop">
				<!-- 함께해요 > 용병모집(substituteBoard) -->
				<li class="navBtn"><a href="${cpath }/juBoard/substiBoard">용병모집</a></li>
				<!-- 함께해요 > 동아리모집(teamBoard) -->
				<li class="navBtn"><a href="${cpath }/juBoard/teamBoard">팀원모집</a></li>
				<!-- 함께해요 > 파티원 모집(partyBoard) -->
				<li class="navBtn"><a href="${cpath }/juBoard/partyBoard">파티원모집</a></li>
				<!-- 함께해요 > 팀매칭(MatchingBoard) -->
				<li class="navBtn"><a href="${cpath }/juBoard/matchBoard">팀매칭</a></li>
			</ul>
		</li>
		<li class="navBtn">
			<a href="${cpath }/event/training">이벤트</a>
            <ul class="drop">
				<li class="navBtn"><a href="${cpath }/event/training">강습안내</a></li>
				<li class="navBtn"><a href="${cpath }/event/competition">대회공지</a></li>
            </ul>
		</li>
		<li class="navBtn"><a href="${cpath }/csc/notice">고객센터</a>
			<ul class="drop">
				<li class="navBtn"><a href="${cpath }/csc/notice">공지사항</a></li>
				<li class="navBtn"><a href="${cpath }/csc/faq">FAQ</a></li>
				<li class="navBtn"><a href="${cpath }/csc/inquiry">기타문의사항</a></li>
			</ul>
		</li>
      </ul>


   </div>

   <c:if test="${login.memberType == 'admin' }">
      <link type="text/css" rel="stylesheet"
         href="${cpath }/resources/style/admin.css">

      <div id="adminHeader" style="width: fit-content;">
         ≡ 관리자 메뉴
         <div>
            <h3>
               <a href="${cpath }/admin/admin">홈으로</a>
            </h3>
            <a href="${cpath }/admin/noticeWrite">공지작성</a> 
            <a href="${cpath }/admin/faqWrite">FAQ작성</a>
         </div>
      </div>
   </c:if>

	<script src="${cpath }/resources/js/script.js"></script>
	<script
		src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=770cdde62b82ec60509855005f656150&libraries=services"></script>

	<script>
		// 관리자 계정으로 로그인하면 관리자 메뉴바 설정
		const loginType = '${login.memberType}'
		if(loginType == 'admin'){
		   const admin = document.getElementById('adminHeader')
		   
		   admin.addEventListener('mouseover', function() {
		      admin.style.width = '300px';
		      
		   });
		   admin.addEventListener('mouseout', function() {
		        admin.style.width = 'fit-content';
		   });
		};
		
		
		
		const fixedMain = document.getElementById('fixedMain');
		const mainLogo = document.getElementById('mainLogo');
		const menuLiElements = document.querySelectorAll('#menu > li');
		
		window.addEventListener('scroll', () => {
		  if (window.pageYOffset > 90) {
		    mainLogo.classList.remove('hidden');
		    menuLiElements.forEach((menuLi) => {
		      
		      // 여기에 메뉴 사이즈도 줄여야 한다.
		    });
		  } else {
		    mainLogo.classList.add('hidden');
		    menuLiElements.forEach((menuLi) => {
		      menuLi.style.fontSize = '1.5rem'; // 본래 크기로 돌아가게 하려면 빈 문자열로 설정
		    });
		  }
		});

	</script>

	<script>
		const inputNickname = document.querySelector('input[name="nickname"]');
		const checkPoint = {
		   dupId: false,
		   dupNick: false,
		   validAddr: false,
		   validAuth: false,
		}
		
		
		
		
		
		async function getAddressLocation() {}
		
		// ===== 회원가입 스크립트=========
		const joinForm = document.getElementById("joinForm");
		if(joinForm != null) {
		   
		   joinForm.onsubmit = joinHandler
		      
		   // 아이디 중복 체크
		   const userid = document.getElementById("joinUserId"); // 아이디 입력 요소 가져오기
		   userid.focus(); // 아이디 입력 요소에 포커스 설정
		   userid.addEventListener("input", () => checkDuplicate(userid, "userid", userid.value));
		   // 아이디 중복 체크 함수 호출
		   
		   // 닉네임 중복 체크
		   const nickname = document.querySelector('input[name="nickname"]'); // 닉네임 입력 요소
		   nickname.addEventListener("input", () => checkDuplicate(nickname, "nickname", nickname.value));
		   // 닉네임 중복 체크 함수 호출
		   
		   
		   const execDaum = document.getElementById('execDaum') 
		   execDaum.addEventListener('click', execDaumPostcode)
		
		}   
		
		   
		const reissuePWForm = document.querySelector('form#reissuePWForm')
		if(reissuePWForm != null) {
		   reissuePWForm.onsubmit = async function(event) {
		      event.preventDefault()
		      const authNumber = event.target.querySelector('input[name="authNumber"]')
		      const flag = await authNumberCheckFetch(authNumber)
		      if(flag == false) {
		         alert('인증 번호가 일치하지 않습니다')
		         return
		      }
		      
		      let url = cpath  + '/member/reissuePW'
		      const data = {
		         userid: event.target.querySelector('input[name="userid"]').value,   
		         email: event.target.querySelector('input[name="email"]').value,   
		         authNumber: event.target.querySelector('input[name="authNumber"]').value,   
		      }
		      url += '?'
		      for(let key in data) {
		         url += key + '=' + data[key] + '&'
		      }
		      const pw = await fetch(url).then(resp => resp.text())
		      alert('변경된 임시 비밀번호 : [' + pw + ']')
		   
		   }
		}
		
		//쪽지 알림 함수
		async function msgCounter() {
		   if(${empty login }){
		      return
		   }
		   // 헤더에 메세지 아이콘
		   const countmsg = document.getElementById('countmsg')
		   // 로그인 세션의 userid
		   var receiverId = countmsg.classList[0]
		   var countUrl = cpath + '/message/countingmsg?receiverId=' + receiverId
		   if(receiverId == ''){
		      return
		   }
		   const msgresult = await fetch(countUrl).then((resp) => resp.json()); 
		   // 안읽은 쪽지 개수 출력                                       
		   countmsg.innerText = msgresult.counter; 
		   // 안읽은 쪽지가 없다면 알림 투명도 100%
		   if(msgresult.color == 'none'){
		      countmsg.style.opacity = 0;
		      return
		   }
		   // 배경색 orangered
		   countmsg.style.backgroundColor = msgresult.color; 
		}
		// 1초마다 msgCounter함수 실행
		setInterval(msgCounter, 1000);
		// 페이지 읽고나면 한번 실행
		if(${not empty login }){
		   window.onload = msgCounter()
		}
	</script>