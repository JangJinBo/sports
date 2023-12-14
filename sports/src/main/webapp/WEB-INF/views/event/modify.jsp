<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/event.css">


<div class="frame listHead" id="eventmod">

<div style="border-bottom: 2px solid black;">
	<h2>게시글 수정</h2>
</div>

<form method="POST" enctype="multipart/form-data">
   <p><input type="hidden" name="boardType" value=${eventDTO.boardType }></p>
   <p><input type="hidden" name="idx" value=${eventDTO.idx }></p>
	<span>제목</span>
    <p><input type="text" name="title" value="${eventDTO.title }" style="width: 1200px; height: 30px;" required autofocus></p>
    <span>내용</span>
    <p><input type="hidden" name="board_idx" value="${eventDTO.board_idx }"></p>
   <p><textarea name="content" style="width: 1200px; height: 500px;" required>${eventDTO.content }</textarea></p>
   <p><input type="hidden" name="writer" value="${login.username }" required></p>
	<!-- <p><input type="file" name="upload" onchange="addFile(this);" multiple></p>
    <div class="fileList"></div> -->
    <input type="hidden" name="pageNum" value="${pageNum }">
<!--     <p><input type="submit" value="수정 완료"></p> -->


	<c:if test="${not empty fileList }">
	<fieldset>
        <legend>기존 파일</legend>
        <div style="display: flex; flex-wrap: wrap;">
            <table>
                <tr>
                    <td>
                       <label><input type="checkbox" id="allCheck">전체 선택</label>
                    </td>
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
    <button id="Cancel">취소</button>
    </p>
</form>
</div>
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
        const board_idx = '${eventDTO.board_idx}';
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
                    return fetch(cpath + '/event/deleteFile/' + board_idx + '/' + file, {
                        method: 'DELETE'
                    });
                });

                Promise.all(deletePromises)
                    .then((responses) => {
                        const successfulDeletes = responses.filter((response) => response.ok);
                        if (successfulDeletes.length > 0) {
                            alert('선택된 파일이 성공적으로 삭제되었습니다.');
                            window.location.reload();
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

<script>
		const Cancel = document.getElementById('Cancel')
		Cancel.onclick = function CancelHandler(event) {
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

</body>
</html>