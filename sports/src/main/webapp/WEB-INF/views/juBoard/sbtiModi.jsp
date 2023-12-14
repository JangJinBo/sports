<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/juBoard.css">
	
	<div class="frame listHead ">
		<div style="border-bottom: 2px solid black;">
			<h2>게시글 수정</h2>
		</div>
	
		<form method="POST" enctype="multipart/form-data" id="regForm">
		<input type="hidden" name="username" value="${sbtidto.username }" required>
				<input type="hidden" name="juBoard_idx" value="${sbtidto.juBoard_idx }" required>
				<input type="hidden" name="board_idx" value="${sbtidto.board_idx }" required>
		<table id="regtb">
		<tr>
			<td>제목</td>
			<td>
				<input type="text" name="title"
					placeholder="제목 입력" value="${sbtidto.title }" required autofocus>
			</td>
		</tr>
		<tr>
			<td>나이</td>
			<td>
				<input type="text" name="age"
					placeholder="나이 입력" value="${sbtidto.age }" required autofocus>
			</td>
		</tr>
		<tr>
			<td>성별</td>
			<td>
				<label>
					<input type="radio" name="gender"
					value="남성" checked="checked" required>남자
				</label> 
				<label>
					<input type="radio" name="gender" 
					value="여성" required>여자
				</label>
			</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>
				<textarea name="content" placeholder="팀 소개"
					style="resize: none; width: 500px; height: 200px">${sbtidto.content }</textarea>
			</td>
		</tr>
		<tr>
			<td>종목</td>
			<td>
			<input type="text"
				class="teamBoard matchBoard substiBoard partyBoard" name="category"
				placeholder="종목 입력" value="${sbtidto.category }" required autofocus>
			</td>
		</tr>
		<tr>
			<td>지역명</td>
			<td>
			<input type="text"
				class="teamBoard matchBoard substiBoard partyBoard" name="mainarea"
				placeholder="간단한 지역명 입력" value="${sbtidto.mainarea }" required autofocus>
			</td>
		</tr>
		<tr>
			<td>정확한 장소 입력</td>
			<td>
				<input type="text" name="place" placeholder="정확한 장소 입력" value="${sbtidto.place }">
			</td>
		</tr>
		<tr>
			<td>일정 입력</td>
			<td>
				<input type="date" name="schedule" value="${sbtidto.schedule }">
			</td>
		</tr>
		<tr>
			<td>참가비</td>
			<td>
				<input type="number" name="joinPrice" value="${sbtidto.joinPrice }" placeholder="참가비 입력">
			</td>
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

			<input type="submit" value="수정">
			<button id="sbCancel">취소</button>
		 </form>
		 
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
			const sbCancel = document.getElementById('sbCancel')
			sbCancel.onclick = function sbCancelHandler(event) {
				window.history.go(-1)
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
	                downloadLink.href = "${cpath}/upload/" + selectedFiles[i];
	                downloadLink.download = selectedFiles[i];
	                downloadLink.click();
	            }
	        } else {
	            alert("선택된 파일이 없습니다.");
	        }
	    }
	
	
	    function deleteSelected() {
	        const checkboxes = document.querySelectorAll("input[type='checkbox'][value]");
	        const board_idx = '${sbtidto.board_idx}';
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
	                    return fetch(cpath + '/juBoard/deleteFile/' + board_idx + '/' + file, {
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
	</div>
					  
<%@ include file="../footer.jsp" %>					  
</body>
</html>