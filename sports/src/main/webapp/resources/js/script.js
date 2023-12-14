//=================== 함수 정의 ========================
const clock = document.querySelector("div#clock");
function getClock() {
  const date = new Date();

  const year = date.getFullYear();
  const mon = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const week = date.getDay();
  const WEEKDAY = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];

  const hours = String(date.getHours()).padStart(2, "0");
  const min = String(date.getMinutes()).padStart(2, "0");
  const sec = String(date.getSeconds()).padStart(2, "0");
  clock.innerText = `${year}년 ${mon}월 ${day}일 [${WEEKDAY[week]}]\n${hours}:${min}:${sec}`;
}

getClock();
setInterval(getClock, 1000);



// === ==============모달 ===
// 버튼, 모달 및 닫기 버튼 요소 가져오기
const btn = document.querySelectorAll("button.modalBtn");
const modals = document.querySelectorAll(".modal");
const spans = document.getElementsByClassName("closeModal");

// 각 버튼에 클릭 이벤트 리스너 추가
for (let i = 0; i < btn.length; i++) {
  btn[i].onclick = function (e) {
    e.preventDefault(); // 기본 동작 막기 (페이지 새로고침 방지)
    let modal = document.querySelector(e.target.getAttribute("href"));
    modal.style.display = "block"; // 모달 표시

//모달이 열릴 때 첫 번째 input 요소에 자동 포커스 설정
  let firstInput = modal.querySelector("input");
  if (firstInput) {
    firstInput.focus();
  }
};
  
  // 닫기 버튼을 클릭하면 해당 모달만 숨김
  spans[i].onclick = function () {
    let modal = modals[i];
    modal.style.display = "none"; // 해당 모달만 숨기기
  };
}

// 모달 외부를 클릭하면 해당 모달만 숨김
window.onclick = function (event) {
  for (let i = 0; i < modals.length; i++) {
    if (event.target === modals[i]) {
      modals[i].style.display = "none"; // 해당 모달만 숨기기
    }
  }
};


// ============= 주소 입력 스크립트 함수
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postcode').value = data.zonecode;
                document.getElementById("addr").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detailAddr").focus();
                checkPoint.validAddr = true;
                
                addAddressLocation()
            }
        	
        }).open();
        
    }
    
//execDaum.addEventListener('click', execDaumPostcode)



////====== 인증번호 발송 함수

const sendAuthNumberButtons = document.querySelectorAll(".sendAuthNumber"); 
const emailInputs = document.querySelectorAll('form input[type="email"]');
const url = cpath + "/member/sendAuthNumber"; // URL 생성
sendAuthNumberButtons.forEach((button, index) => {
 // 'sendAuthNumberButtons' 배열 내의 각 요소에 대한 반복
 button.addEventListener("click", function (event) {
   // 클릭 이벤트에 대한 리스너 추가
   const emailInput = emailInputs[index]; // 'emailInputs' 배열에서 현재 인덱스의
											// 'emailInput' 요소 가져옴
   const emailValue = emailInput.value; // 'emailInput'의 값 가져옴
   if (emailValue === "") {
     // 이메일 값이 비어있는지 확인
     alert("인증번호를 받을 이메일을 정확하게 입력해주세요");
     emailInput.focus(); // 이메일 입력란으로 포커스 이동
     return; // 함수 실행 중지
   }
   fetch(url + "?email=" + emailValue) // 서버에 이메일 값으로 fetch 요청 보냄
     .then((resp) => resp.text()) // 서버 응답을 텍스트로 변환
     .then((text) => {
       alert(text); // 서버 응답 내용을 알림으로 표시
     });
 });
});


// 인증번호를 보내고 일치여부를  1/0으로 반환하는 함수
async function authNumberCheckFetch(authNumber) {
	const urlCheck = cpath + "/member/checkAuthNumber/" + authNumber.value; // URL 생성
	const authResult = await fetch(urlCheck).then((resp) => resp.text()); // 서버로부터 'authResult'를 비동기로 가져오기
	return +authResult === 1
}
 
function addAddressLocation() {
    var geocoder = new kakao.maps.services.Geocoder();
    const location = document.getElementById('addr').value + ' ' +document.getElementById('detailAddr').value
    geocoder.addressSearch(location, function(result, status) {
       if (status === kakao.maps.services.Status.OK) {
          const region = result[0].address.region_1depth_name + ' ' + result[0].address.region_2depth_name

          
          const input1 = document.createElement('input')
          input1.type = 'hidden'
          input1.name = 'location'
          input1.value = location
          joinForm.appendChild(input1)
          
          const input2 = document.createElement('input')
          input2.type = 'hidden'
          input2.name = 'addX'
          input2.value = result[0].x
          joinForm.appendChild(input2)
          
          const input3 = document.createElement('input')
          input3.type = 'hidden'
          input3.name = 'addY'
          input3.value = result[0].y
          joinForm.appendChild(input3)
       
       } 
    });
 }
 
 
//============ 중복 체크 함수 ((입력란),(식별자),(입력값))
async function checkDuplicate(element, type, value) {
  const checkSpan = element.nextElementSibling; // 입력란 다음 span을 가져옴
	 
  if (value == "") {
    // 입력 값이 없으면
    checkSpan.style.color = "lightgrey"; // 스팬 텍스트 색상을 회색으로 설정
    return; // 함수 종료
  }

  const url = cpath + "/member/checkDuplicate?type=" + type + "&value=" + value; // 중복 체크를 위한 URL생성
  const result = await fetch(url).then((resp) => resp.json()); // 서버에 중복 체크 요청 후 결과를 가져옴
  checkSpan.innerText = result.msg; // 스팬에 텍스트를 결과 메시지띄움
  checkSpan.style.color = result.color; // 스팬의 텍스트 색상을 결과에 맞게 설정
  
  if(result.color == 'blue') {
	     switch(type) {
	     case 'userid': 	checkPoint.dupId = true; break;
	     case 'nickname':	checkPoint.dupNick = true; break;
	     }
  }
  
}	// end of checkDuplicate
 
 
 
 
 
 
async function joinHandler(event) {
    event.preventDefault();
    const joinData = {}; // 빈 JS객체를 준비한다.

	const authNumber = joinForm.querySelector('input[name="authNumber"]');
	checkPoint.validAuth = await authNumberCheckFetch(authNumber)

 	//    if(Object.values(checkPoint).filter(e => e == false).length != 0) {
//        alert('모든 항목이 체크되지 않았습니다')
//        return
//    }
    const checkValues = Object.values(checkPoint);

   if (checkValues[0] === false) {
      alert('아이디가 중복 됩니다.');
      return
   } else if (checkValues[1] === false) {
      alert('닉네임이 중복 됩니다.');
      return
   } else if (checkValues[2] === false) {
      alert('정확한 주소가 아닙니다.');
      return
   } else if (checkValues[3] === false) {
      alert('인증번호가 일치하지 않습니다.');
      return
   }
       
    
    const formData = new FormData(joinForm); // joinForm 안에 있는 input을 다 불러온다.
    for (let element of formData.entries()) {
       // 내부에는 [key, value] 형식으로 되어있다.
       // input태그에 [name, value]
          joinData[element[0]] = element[1]; // 0번째는 key, 1번째는 value
    }
    
    const joinUrl = cpath + "/member/join";
    const opt = {
        method: "POST",
        headers: {
          "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(joinData),
    };
   
    const result = await fetch(joinUrl, opt).then((resp) => resp.json());
    alert(result.status);
   
    const login = document.getElementById("login");
    const joinClose = document.getElementById("joinClose");
    if (result.status == "가입성공") {      // 회원가입 창 닫고, 로그인창 띄우기
        joinClose.click();
        login.click();
        joinForm.reset();
    }
} 

 
 
 
 
 
 
 
 

