<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/space.css">


<div class="frame listHead">
	<div>
		<h2>공간 수정</h2>
	</div>


<form class="regForm" method="POST" enctype="multipart/form-data">
	<table id="regtb">
		<tr>
			<td>카테고리 : </td>
			<td>
				<input type="text" name="spaceCategory" placeholder="ex)축구" required autofocus value="${spaceDTO.spaceCategory }">
			</td>
		</tr>
		<tr>
			<td>장소명 : </td>
			<td><input type="text" name="spaceName" placeholder="장소 이름을 입력하세요" required autofocus value="${spaceDTO.spaceName }"></td>
		</tr>
		<tr>
			<td>기존 주소 : </td>
			<td>${addressDTO.location }</td>
		</tr>
		<tr>
			<td style="display: flex; align-items: flex-start;">주소 : </td>
			<td>
				<input type="text" id="postcode" placeholder="우편번호">
				<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
				<input type="text" id="addr" placeholder="주소"><br>
				<input type="text" id="detailAddr" placeholder="상세주소">
			</td>
		</tr>
		<tr>
			<td>면적 : </td>
			<td><input type="text" name="area" placeholder="m2" value="${spaceDTO.area }"></td>
		</tr>
		<tr>
            <td>여는 시간 : </td>
            <td>
				<select name="openTime">
				    <c:forEach var="i" begin="0" end="23">
				        <fmt:formatNumber var="startHH" value="${i }" pattern="00" />
				        <option ${fn:startsWith(spaceDTO.openTime, startHH) ? 'selected' : '' }
				            value="${startHH }">${startHH }:00</option>
				    </c:forEach>
				</select>
            </td>
        </tr>
        <tr>
            <td>닫는 시간 : </td>
            <td>
				 <select name="closeTime">
				    <c:forEach var="i" begin="0" end="23">
				        <fmt:formatNumber var="endHH" value="${i }" pattern="00" />
				        <option ${fn:startsWith(spaceDTO.closeTime, endHH) ? 'selected' : '' }
				            value="${endHH }">${endHH }:00</option>
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
			<td><input type="text" name="price" placeholder="가격(시간당)" required value="${spaceDTO.price }"></td>
		</tr>
		<tr>
			<td style="display: flex; align-items: flex-start;">안내 사항 : </td>
			<td><textarea cols="30" rows="10" name="guide" placeholder="안내사항 및 연락처 등 공간을 소개해 주세요">${spaceDTO.guide }</textarea></td>
		</tr>
	</table>
	<c:if test="${not empty fileList }">
	<fieldset>
        <legend>기존 파일</legend>
        <div style="display: flex; flex-wrap: wrap;">
            <table>
                <tr>
                    <td><label><input type="checkbox" id="allCheck">전체 선택</label></td>
                    <td colspan="2" align="right">
                        <input type="button" id="downloadButton" value="다운로드">
                        <input type="button" id="deleteButton" value="삭제">
                    </td>
					</tr>
					<c:forEach var="fileDTO" items="${fileList }">
						<tr>
							<td><input type="checkbox" value="${fileDTO.fileName }"></td>
							<td><img src="${cpath}/upload/${fileDTO.fileName }" height="100px"></td>
							<td>${fileDTO.fileName }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
	</fieldset>
	</c:if>
	<fieldset>
		<legend>파일 추가</legend>
		<p><input type="file" name="upload" onchange="addFile(this);" multiple></p>
		<div class="fileList"></div>
	</fieldset>
 	<p>
 		<input type="submit" value="수정 완료">
 		<input type="button" value="취소" id="sbCancel">
 	</p>
 	
 	<div id="hiddenInputStorage"></div>
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
                // 커서를 주소 필드로 이동한다.
                document.getElementById("addr").focus();
//                 document.getElementById("detailAddr").focus();
            }
        }).open();
    }
</script>



<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=770cdde62b82ec60509855005f656150&libraries=services"></script>
<script>
const addrInput = document.getElementById('addr');
const hiddenInputStorage = document.getElementById('hiddenInputStorage');

addrInput.onblur = function(){
	
	hiddenInputStorage.innerHTML = '';
	
	// 상세 주소
	const location = document.getElementById('addr').value + ' ' +document.getElementById('detailAddr').value
	

	// 주소-좌표 변환 객체를 생성
	var geocoder = new kakao.maps.services.Geocoder();

	// 주소로 좌표를 검색
	geocoder.addressSearch(location, function(result, status) {
	
	    // 정상적으로 검색이 완료됐으면 
	     if (status === kakao.maps.services.Status.OK) {

	        const region = result[0].address.region_1depth_name + ' ' + result[0].address.region_2depth_name
	        const sendPost = {
	        		'location' : location,
	        		'addX' : result[0].x,
	        		'addY' : result[0].y,
	        		'region' : region,
	        }	// input type=hidden으로 post에 보낼 값들 세팅

	        for(let key in sendPost){
	        	const hiddenInput = document.createElement('input')
				hiddenInput.setAttribute('type', 'hidden')
				hiddenInput.setAttribute('name', key)
				hiddenInput.setAttribute('value', sendPost[key])
				hiddenInputStorage.appendChild(hiddenInput)
			}	// input type=hidden으로 form에 추가
	    }
	     else{
	    	 alert('주소를 다시 확인 해 주세요')
	     }
	});
	// 커서를 상세주소 필드로 이동한다.
// 	document.getElementById("detailAddr").focus();
}
</script>


<script>	// select에서 각각의 값과 같은 거 선택
    // spaceDTO.park의 값을 가져온다
    var selectedPark = "${spaceDTO.park}";

    // select 요소를 찾는다
    var selectElementPark = document.getElementsByName("park")[0];

	 // 모든 option 요소를 반복하며 선택된 것을 찾는다
    for (var i = 0; i < selectElementPark.options.length; i++) {
        var option = selectElementPark.options[i];
        if (option.value === selectedPark) {
        	// spaceDTO.spaceCategory와 일치하는 값인 경우 선택
            option.selected = true;
            break; // 일치하는 항목을 찾았으므로 반복을 종료
        }
    }
</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const allCheck = document.getElementById("allCheck");
        allCheck.addEventListener("click", function() {
            const checkboxes = document.querySelectorAll("input[type='checkbox'][value]");

            if (allCheck.checked) {
                // 모든 체크박스 선택
                for (let i = 0; i < checkboxes.length; i++) {
                    checkboxes[i].checked = true;
                }
            } else {
                // 모든 체크박스 해제
                for (let i = 0; i < checkboxes.length; i++) {
                    checkboxes[i].checked = false;
                }
            }
        });
        
        // 하위 체크박스 이벤트 리스너
        const checkboxes = document.querySelectorAll("input[type='checkbox'][value]");
        for (let i = 0; i < checkboxes.length; i++) {
            checkboxes[i].addEventListener("click", function() {
                updateAllCheck();
            });
        }

        const downloadButton = document.getElementById("downloadButton");
        downloadButton.addEventListener("click", downloadSelected);

        const deleteButton = document.getElementById("deleteButton");
        deleteButton.addEventListener("click", deleteSelected);

        function updateAllCheck() {
            const checkboxes = document.querySelectorAll("input[type='checkbox'][value]");
            let allChecked = true;
            for (let i = 0; i < checkboxes.length; i++) {
                if (!checkboxes[i].checked) {
                    allChecked = false;
                    break;
                }
            }
            allCheck.checked = allChecked;
        }
    });

    function downloadSelected() {
        const checkboxes = document.querySelectorAll("input[type='checkbox'][value]");

        const selectedFiles = [];
        for (let i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                selectedFiles.push(checkboxes[i].value);
            }
        }

        if (selectedFiles.length > 0) {
            // 선택된 파일에 대한 다운로드 URL을 동적으로 생성하여 다운로드
            for (let i = 0; i < selectedFiles.length; i++) {
            	const downloadLink = document.createElement("a");
                downloadLink.href = cpath + "/upload/" + selectedFiles[i];
                downloadLink.download = selectedFiles[i];
                downloadLink.click();
            }
        } else {
            alert("선택된 파일이 없습니다.");
        }
    }


    function deleteSelected() {
        const checkboxes = document.querySelectorAll("input[type='checkbox'][value]");
        const board_idx = '${spaceDTO.board_idx}';
        let selectedFiles = [];
        
        for (let i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                selectedFiles.push(checkboxes[i].value);
            }
        }

        if (selectedFiles.length > 0) {
            // 사용자에게 확인 메시지를 표시
            if (confirm("정말 삭제하시겠습니까?\n삭제 하시면 복구 하실 수 없습니다")) {
                // 사용자가 확인을 클릭한 경우 선택된 파일을 삭제
                const deletePromises = selectedFiles.map((file) => {
                    return fetch(cpath + '/space/deleteFile/' + board_idx + '/' + file, {
                        method: 'DELETE'
                    });
                });

                Promise.all(deletePromises)
                    .then((responses) => {
                        const successfulDeletes = responses.filter((response) => response.ok);
                        if (successfulDeletes.length > 0) {
                            alert('선택된 파일이 성공적으로 삭제되었습니다.');
                            location.reload();
                        } else {
                            alert('파일 삭제 중 오류가 발생했습니다.');
                        }
                    });
            }
        } else {
            alert("선택된 파일이 없습니다.");
        }
    }

</script>






</body>
</html>