<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/message.css">
					  
<div class="frame listHead">
	<div>
		<h2>쪽지 쓰기</h2>
		<a href="${cpath }/message/list"><button>≡ 내 우편함</button></a>
	</div>
	<form method="POST" id="sendMessageMain" action="${cpath }/message/sendMessage">
		<table>
			<tr>
				<td class="tname">받는사람 ID</td>
				<td class="tval"><input type="text" name="receiverId" id="receiverInput" required autocomplete="off">
				<span style="font-weight: bold; color:lightgrey; display: none;" id="rspan">✓</span></td>
			</tr>
			<tr>
				<td class="tname">제목</td>
				<td class="tval"><input type="text" name="title" id="titleInput" required></td>
			</tr>
			<tr>
				<td class="tname">내용</td>
				<td class="tval"><textarea name="content" required></textarea></td>
			</tr>
		</table>		
		<button type="submit">보내기</button>
	</form>	
</div>

<script>

	//============ 중복 체크 함수 ((입력란),(식별자),(입력값))
	async function checkDuplicate(element, type, value) {
	  const checkSpan = element.nextElementSibling; // 입력란 다음 span을 가져옴
	
	  if (value == "") {
	    // 입력 값이 없으면
	    checkSpan.style.color = "lightgrey"; // 스팬 텍스트 색상을 회색으로 설정
	    return; // 함수 종료
	  }
	
	  const url = cpath + "/member/checkDuplicate?type=" + type + "&value=" + value; // 중복
																						// 체크를
																						// 위한
																						// URL
																						// 생성
	  const result = await fetch(url).then((resp) => resp.json()); // 서버에 중복 체크 요청
																	// 후 결과를 가져옴
	  checkSpan.innerText = result.msg; // 스팬에 텍스트를 결과 메시지띄움
	  checkSpan.style.color = result.color; // 스팬의 텍스트 색상을 결과에 맞게 설정
	}
	
	

	const receiverInput = document.getElementById('receiverInput')
	const titleInput = document.getElementById('titleInput')
	if(${receiverId == ""}){
		receiverInput.focus()
	}
	else{
		titleInput.focus()
		receiverInput.setAttribute('value', '${receiverId}')
	}
	
	// 아이디 중복 체크
	const receiverId = document.getElementById("receiverInput"); // 아이디 입력 요소 가져오기
		receiverId.addEventListener("input", () => {
 		checkDuplicate(receiverId, "userid", receiverId.value); // 아이디 중복 체크 함수 호출
	});
		

		
	const messageForm = document.getElementById("sendMessageMain");
	messageForm.onsubmit = async function (event) {
		  event.preventDefault();

		  // 중복체크 조건(파란색이 2개가 아니면 함수 종료)
		  if (Array.from(event.target.querySelectorAll("span"))
		      .map((e) => e.style.color)
		      .filter((e) => e == "blue").length == 1
		  ) {
		    alert("존재하지 않는 ID입니다");
		    return;
		  }
		  else{
			  messageForm.submit()
		  }
	}
</script>

<%@ include file="../footer.jsp" %>