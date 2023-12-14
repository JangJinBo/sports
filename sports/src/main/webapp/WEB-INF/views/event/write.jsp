<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/event.css">

<div class="frame listHead">
<div>
	<h2>이벤트 공지/안내</h2>
</div>
<form method="POST" enctype="multipart/form-data" id="eventWrite">
	<div>
	<select name="boardType" id="eventType" required autofocus>
		<option value="">=== 양식을 선택하세요 ===</option>
		<option value="training">강습 안내</option>
		<option value="competition">대회 공지 안내</option>
	</select>
	<c:if test="${not empty fileList }">
      <c:forEach var="fileDTO" items="${fileList }">
         <img src="${cpath }/upload/${fileDTO.fileName }">
      </c:forEach>
   </c:if>
	</div>
	<span>제목</span>
	<p><input type="text" name="title" placeholder="제목을 입력하세요" required></p>
	<span>내용</span>
	<p><textarea name="content" placeholder="내용을 입력하세요" required></textarea></p>
	<input type="hidden" name="writer" value="${login.username }" required>
 	<p><input type="file" name="upload" onchange="addFile(this);" multiple></p>
    <div class="fileList"></div>
    
	<p class="none" id="subp"><input type="submit" id="eventSub" value="작성완료"></p>
</form>
</div>


<script>
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

const eventType = document.getElementById('eventType')
const eventSub = document.getElementById('eventSub')
const subp = document.getElementById('subp')
eventType.onchange = function(event) {
	const value = event.target.value
	if(value == 'training') {
		subp.classList.remove('none')
		eventSub.setAttribute('formaction',cpath + '/event/trWrite')
	}
	else if(value == 'competition'){
		subp.classList.remove('none')
		eventSub.setAttribute('formaction',cpath + '/event/comWrite')
	}
	else{
		subp.classList.add('none')
	}
}

var boardType = '${boardType}'
	var selectEventType = document.getElementsByName("boardType")[0];
	for (var i = 0; i < selectEventType.options.length; i++) {
	   var option = selectEventType.options[i];
	    if (option.value === boardType) {
	        
	        option.selected = true;
	        break; 
	    }
	}
	eventType.dispatchEvent(new Event('change'))	
</script>

<%@ include file="../footer.jsp" %>
