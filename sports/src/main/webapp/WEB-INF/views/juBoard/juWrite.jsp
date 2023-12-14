<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${cpath }/resources/style/juBoard.css">
 
<div class="frame listHead">
	<div>
		<h2>플레이어 찾기</h2>
	</div>
	<form method="POST" enctype="multipart/form-data"  id="juWrite">
		<select name="boardType" id="juBType" required>
			<option value="typeNull">=== 양식 선택 ===</option>
			<option value="substiBoard">용병 모집</option>
			<option value="teamBoard">팀원 모집</option>
			<option value="partyBoard">파티 모집</option>
			<option value="matchBoard">팀 매칭</option>
		</select>

		<p>
			<input type="text"
				class="teamBoard matchBoard substiBoard partyBoard" name="title"
				placeholder="제목 입력" required autofocus>
		</p>
		<p>
			<input type="text"
				class="teamBoard matchBoard substiBoard partyBoard" name="age"
				placeholder="나이 입력" required autofocus>
		</p>
		<label>
			<input type="radio" class="teamBoard matchBoard substiBoard partyBoard" name="gender"
			value="남성" checked="checked" required>남자
		</label> 
		<label>
			<input type="radio" class="teamBoard matchBoard substiBoard partyBoard" name="gender" 
			value="여성" required>여자
		</label>
		<p>
			<input type="hidden"
				class="teamBoard matchBoard substiBoard partyBoard" name="writer"
				value="${login.username }" required>
		</p>
		<p>
			
			<textarea name="content" placeholder="팀 소개" class="teamBoard matchBoard substiBoard partyBoard"
				style="resize: none; width: 500px; height: 200px" required></textarea>
		</p>

		<p>
			<input type="text"
				class="teamBoard matchBoard substiBoard partyBoard" name="category"
				placeholder="종목 입력" required autofocus>
		</p>
		<p>
			<input type="text"
				class="teamBoard matchBoard substiBoard partyBoard" name="mainarea"
				placeholder="간단한 지역명 입력" required autofocus>
		</p>

		<p>
			<input type="file"
				class="teamBoard matchBoard substiBoard partyBoard" name="upload"
				onchange="addFile(this);" multiple>
		</p>
		<div class="fileList"></div>

		<p>
			<input type="text" class="matchBoard substiBoard partyBoard"
				name="place" placeholder="정확한 장소 입력" required>
		</p>
		<p>
			일정 입력
			<input type="date" style="width: 120px;" class="matchBoard substiBoard partyBoard"
				name="schedule">
		</p>

		<p>
			<input type="number" class="substiBoard" name="joinPrice"
				placeholder="참가비 입력">
		</p>
		<p>
			<input type="number" class="partyBoard" name="maxCapacity"
				placeholder="인원수 입력">
		</p>

		<p>
			<input type="submit" class="teamBoard" value="제출"
				formaction="${cpath}/juBoard/juWrite">
		</p>
		<p>
			<input type="submit" class="matchBoard" value="제출"
				formaction="${cpath}/juBoard/mtchWrite">
		</p>
		<p>
			<input type="submit" class="substiBoard" value="제출"
				formaction="${cpath}/juBoard/sbtiWrite">
		</p>
		<p>
			<input type="submit" class="partyBoard" value="제출"
				formaction="${cpath}/juBoard/ptyWrite">
		</p>
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
		const juBType = document.getElementById('juBType')
		var boardType = '${boardType}'
		
		var selectJuBtype = document.getElementsByName("boardType")[0];
		
		for (var i = 0; i < selectJuBtype.options.length; i++) {
	        var option = selectJuBtype.options[i];
	        if (option.value === boardType) {
	            
	            option.selected = true;
	            break; 
	        }
	    }
		
		juBType.onchange = function(event) {
			const value = event.target.value
			var juBSelList = document.querySelectorAll("form input")
			juBSelList.forEach(e => {
				if(e.classList.contains(value)) {
					e.parentNode.classList.remove('hidden')
				}
				else {
					e.parentNode.classList.add('hidden')
				}
			})
			const onOffText = document.querySelectorAll("textarea")
			onOffText.forEach(e => {
				if(e.classList.contains(value)) {
					e.parentNode.classList.remove('hidden')
				}
				else {
					e.parentNode.classList.add('hidden')
				}
			})
		}
		
		
		juBType.dispatchEvent(new Event('change'))
	</script>

</div>
<%@ include file="../footer.jsp"%>
</body>
</html>