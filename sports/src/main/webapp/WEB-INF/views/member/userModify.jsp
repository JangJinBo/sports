<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/member.css">
<div class="frame">

	<form method="POST" id="update" class="regForm">
				<input type="hidden" name="memberType" value="${user.memberType }">
		<table id="regtb">
			<tr>
				<td>이름</td>
				<td><input type="text" style="border:none;" name="username" value="${user.username }" readonly></td>
			</tr>
			<tr>
				<td>아이디</td>
				<td><input type="text" style="border:none;" name="userid" value="${user.userid }" readonly></td>
			</tr>
			<tr>
				<td>닉네임</td>
				<td>
					<input type="text" style="border:none;" name="nickname" value="${user.nickname}" readonly>
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="userpw"  id="newPassword"  placeholder="PW확인" required></td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type="text" name="pnum" value="${user.pnum}"></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="email" name="email" value="${user.email}"></td>
			</tr>
			  <tr >
				<td>기존주소</td>
				<td>${user.addressLocation }</td>
			</tr>
			  <tr >
				<td>주소</td>
				<td>
                   	<input type="text" id="postcode" placeholder="우편번호" >
                </td>
                <td>
					<input type="button" id="execDaum"  value="우편번호 찾기">
				</td>
              </tr>
              <tr>
              	<td></td>
              	<td>
              		<input type="text" id="addr" placeholder="주소" >
              	</td>
              </tr>
              <tr>
              	<td></td>
              	<td>
              		<input type="text" id="detailAddr" placeholder="상세주소">
              	</td>
              </tr>
              <tr>
              	<td></td>
              	<td>
					<input type="submit" value="수정완료">
              	</td>
              </tr>
		</table>
	</form>
				<button id="deleteMember">회원탈퇴</button>


</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
// 회원 탈퇴 스크립트
    const deleteMemberButton = document.getElementById('deleteMember');
	//이거 클릭하면 팝업창으로 삭제를 한번 더 확인 후 삭제 하는 스크립트 만들기 
    
    deleteMemberButton.addEventListener('click', function() {
        const confirmDelete = confirm('회원을 탈퇴하시겠습니까?');
                // 사용자가 확인을 클릭한 경우, 비밀번호와 함께 삭제 요청을 서버로 보냅니다.
        
        if (confirmDelete) {
	        const password = prompt('비밀번호를 입력하세요:', '');
	        
	        if (password !== null) {
	            // 사용자가 비밀번호를 입력한 경우
	        	  $.ajax({
	                    type: 'POST',
	                    url: cpath + '/member/deleteMember', // 서버의 삭제 요청 URL로 변경
	                    data: { userpw: password, 
	                    		userid : '${user.userid}'},
	                    success: function(response) {
	                        // 서버로부터의 응답을 처리
	                        if (response == 'success') {
	                            alert('회원 탈퇴가 완료되었습니다.');
	                            window.location.href = cpath;
	                        } else {
	                            alert('비밀번호가 틀렸습니다.');
	                        }
	                    },
	                    error: function() {
	                        alert('서버와의 통신 중 오류가 발생했습니다.');
	                    }
	                });
            } else {
	            alert('비밀번호 입력이 취소되었습니다.');
            }
        } else {
            alert('회원 탈퇴가 취소되었습니다.');
        }
    });
    
    
// 회원 정보 수정 주소 스크립트
const form = document.getElementById("update");
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
				  form.appendChild(input1)
				  
				  const input2 = document.createElement('input')
				  input2.type = 'hidden'
				  input2.name = 'addX'
				  input2.value = result[0].x
				  form.appendChild(input2)
				  
				  const input3 = document.createElement('input')
				  input3.type = 'hidden'
				  input3.name = 'addY'
				  input3.value = result[0].y
				  form.appendChild(input3)
			
			  } 
		  });
	}

	execDaum.addEventListener('click', execDaumPostcode)
	
</script>
<%@ include file="../footer.jsp" %>