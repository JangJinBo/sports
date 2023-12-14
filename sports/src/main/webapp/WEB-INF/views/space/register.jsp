<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/space.css">



<div class="frame listHead">
<div>
	<h2>공간 등록</h2>
</div>

<form method="POST" enctype="multipart/form-data" class="regForm" id="formRegister">
	<table class="regtb">
		<tr>
			<td>카테고리 : </td>
			<td>
				<input type="text" name="spaceCategory" placeholder="ex)축구" required autofocus>
			</td>
		</tr>
		<tr>
			<td>장소명 : </td>
			<td><input type="text" name="spaceName" placeholder="장소 이름을 입력하세요" required></td>
		</tr>
		<tr>
			<td style="display: flex; align-items: flex-start;">주소 : </td>
			<td>
				<input type="text" id="postcode" placeholder="우편번호" required>
				<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
				<input type="text" id="addr" placeholder="주소" required><br>
				<input type="text" id="detailAddr" placeholder="상세주소">
			</td>
		</tr>
		<tr>
			<td>면적 : </td>
			<td><input type="text" name="area" placeholder="m2"></td>
		</tr>
		<tr>
            <td>여는 시간 : </td>
            <td>
				<select name="openTime" id="openTimeSelect">
					<c:forEach var="i" begin="0" end="23">
						<fmt:formatNumber var="startHH" value="${i }" pattern="00" />
						<option value="${startHH }">${startHH }:00</option>
					</c:forEach>
				</select>
            </td>
        </tr>
        <tr>
            <td>닫는 시간 : </td>
            <td>
                <select name="closeTime" id="closeTimeSelect">
					<c:forEach var="i" begin="0" end="23">
						<fmt:formatNumber var="endHH" value="${i }" pattern="00" />
						<option value="${endHH }">${endHH }:00</option>
					</c:forEach>
				</select>
            </td>
        </tr>
		<tr>
			<td>주차 공간 여부 : </td>
			<td>
				<select name="park">
					<option value="없음">없음</option>
					<option value="있음">있음</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>가격 : </td>
			<td><input type="number" name="price" placeholder="가격(시간당)" required></td>
		</tr>
		<tr>
			<td style="display: flex; align-items: flex-start;">안내 사항 : </td>
			<td><textarea cols="30" rows="10" name="guide" placeholder="안내사항 및 연락처 등 공간을 소개해 주세요"></textarea></td>
		</tr>
		<tr>
			<td style="display: flex; align-items: flex-start;">파일 : </td>
			<td>
				<p><input type="file" name="upload" onchange="addFile(this);" multiple></p>
				<div class="fileList"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			 	<p>
			 		<input type="submit" value="등록">
			 		<input type="button" value="취소" id="sbCancel">
 				</p>
			</td>
		</tr>
	</table>
</form>

</div>	<!-- end of div.frame -->

<%@ include file="../footer.jsp" %>

<script>
	const sbCancel = document.getElementById('sbCancel')
	sbCancel.onclick = function sbCancelHandler(event) {
		window.history.go(-1)
	}
</script>

<script>
/* 첨부파일 리스트 */
function addFile(obj){
	const fileList = document.querySelector('.fileList')
	fileList.innerText = ''
	for (let i = 0; i < obj.files.length; i++) {
		let fileName = ''
		if(i != 0){
			fileName += ', '
		}
		fileName += obj.files[i].name
		fileList.append(fileName);
	}
}
</script>



<script>
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
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
            }
        }).open();
    }
</script>



<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=770cdde62b82ec60509855005f656150&libraries=services"></script>
<script>
const formRegister = document.getElementById('formRegister');

function areOpeningAndClosingTimesEqual() {
    const openTime = document.getElementById('openTimeSelect').value;
    const closeTime = document.getElementById('closeTimeSelect').value;

    return openTime === closeTime;
}

formRegister.onsubmit = function (event) {
    event.preventDefault();

    if (areOpeningAndClosingTimesEqual()) {
        alert('여는 시간과 닫는 시간은 동일할 수 없습니다.');
    } else {
        // 양식 제출을 계속합니다
        var geocoder = new kakao.maps.services.Geocoder();
        const location = document.getElementById('addr').value + ' ' + document.getElementById('detailAddr').value;

        geocoder.addressSearch(location, function (result, status) {
            if (status === kakao.maps.services.Status.OK) {
                const region = result[0].address.region_1depth_name + ' ' + result[0].address.region_2depth_name;
                const sendPost = {
                    'location': location,
                    'addX': result[0].x,
                    'addY': result[0].y,
                    'region': region,
                };

                for (let key in sendPost) {
                    const hiddenInput = document.createElement('input');
                    hiddenInput.setAttribute('type', 'hidden');
                    hiddenInput.setAttribute('name', key);
                    hiddenInput.setAttribute('value', sendPost[key]);
                    formRegister.appendChild(hiddenInput);
                }

                event.target.submit();
            } else {
                alert('주소를 다시 확인해 주세요.');
            }
        });
    }
};
</script>

</body>
</html>
