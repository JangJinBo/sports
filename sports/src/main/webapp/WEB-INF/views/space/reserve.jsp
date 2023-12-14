<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/space.css">

<div class="frame listHead">
	<div>
		<h2>예약 페이지</h2>
	</div>
	<table id="sitab" style="margin: 20px auto;">
		<tr>
			<th>공간이름</th>
			<td>${spaceDTO.spaceName }</td>
		</tr>
		<tr>
			<th>면적</th>
			<td><fmt:formatNumber value="${spaceDTO.area }" pattern="#,###" />m²</td>
		</tr>
		<tr>
			<th>여는 시간</th>
			<td>${spaceDTO.openTime }</td>
		</tr>
		<tr>
			<th>닫는 시간</th>
			<td>${spaceDTO.closeTime }</td>
		</tr>
		<tr>
			<th>가격(시간당)</th>
			<td><fmt:formatNumber value="${spaceDTO.price }" pattern="#,###" />원</td>
		</tr>
	</table>

	
	<form id="reserveForm" class="regForm" method="POST">
		<input type="hidden" name="member_idx"value="${login.idx}">
		<table id="restb">
			<tr>
				<td>이용 일자</td>
				<td>
					<input type="date" name="startDay" min="${today}" value="${empty param.startTime ? today : param.startTime}">
					<select name="startTime">
						<c:forEach var="i" begin="${fn:split(spaceDTO.openTime, ':')[0] }" end="${fn:split(spaceDTO.closeTime, ':')[0]}">
							<fmt:formatNumber var="startHH" value="${i }" pattern="00" />
							<option ${param.startTime == startHH ? 'selected' : '' } value="${startHH }">${startHH }:00</option>
						</c:forEach>
					</select>
					 &nbsp &nbsp ~ &nbsp &nbsp  
					<input type="date" name="endDay" min="${today}" value="${empty param.endTime ? today : param.endTime }">
					<select name="endTime">
						<c:forEach var="i" begin="${fn:split(spaceDTO.openTime, ':')[0] }" end="${fn:split(spaceDTO.closeTime, ':')[0]}">
							<fmt:formatNumber var="endHH" value="${i }" pattern="00" />
							<option ${param.endTime == endHH ? 'selected' : '' } value="${endHH }">${endHH }:00</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>	
				<td>예약자 이름 </td>
				<td>
					<input type="text" name="reserverName" required>
				</td>
			</tr>
			<tr>
				<td>예약자 전화 번호</td>
				<td>
					<input type="text" name="reserverNumber" required>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input id="reserveCheckBtn" type="submit" value="예약 가능 여부 확인">
				</td>
			</tr>
		</table>
	</form>
	<div id="reserveCh" class="sb">
		<div>
			<h5 class="hidden" style="font-size: 1.2rem; margin: 20px 0;">예약된 시간</h5>
			<table id="reservationTable" class="hidden holtb" border="1">
				<thead>
					<tr>
						<th>시작 시간</th>
						<th>종료 시간</th>
					</tr>
				</thead>
				<tbody>
					<!-- 여기에 예약 정보를 추가할 것입니다. -->
				</tbody>
			</table>
		</div>
		<div>
			<h5 id="message" style="width: 300px; font-size: 1rem;"></h5><br>
			<span id="total" class="hidden"></span> 
			<span id="totalValue" class="hidden"></span><br>
			<button id="pay" class="hidden">결제</button>
		</div>
	</div>
</div>
<%@ include file="../footer.jsp"%>

<script>

	// 예약 중복 확인 스크립트
	const reserveForm = document.getElementById('reserveForm')
	let reserveDTO;
	let reserveDayList;
	
	reserveForm.onsubmit = async function(event) {
		event.preventDefault()
		const message = document.getElementById('message')
		// 예약 가능 여부 확인 버튼을 누르면 현재 위치에서200px아래로 스크롤이 내려감
		window.scrollBy(0, 200);
		
		const spaceIdx = '${spaceDTO.idx}'
		const formData = new FormData(reserveForm)
		const ob = {}
		
		for (let element of formData.entries()){
			ob[element[0]] = element[1];
		}
		
		
		const url = cpath + "/space/reserveCheck/" + spaceIdx;
		const opt = {
				method : "POST",
				body : JSON.stringify(ob),
			    headers: {
				      "Content-Type": "application/json;charset=utf-8"
			    }
		}
		

		let result = null; 
			await fetch(url, opt).then(resp => resp.json())
			.then(data => {
				reserveDTO = data.ReserveDTO 
				reserveDayList = data.reserveDayList 
				result = data;
			})
		
		
		
		if(result.color == 'blue') {
			document.getElementById('pay').classList.remove('hidden')
			document.getElementById('total').classList.remove('hidden')
			document.getElementById('total').innerText = result.total
			message.innerText = result.message
			message.style.color = result.color
		}else{
			document.getElementById('pay').classList.add('hidden')
			document.getElementById('total').classList.add('hidden')
			message.innerText = result.message
			message.style.color = result.color
			
			const h5 = document.querySelector('h5');
			const reservationTable = document.getElementById('reservationTable');
			const tbody = reservationTable.querySelector('tbody');
			tbody.innerHTML = '';
			reservationTable.classList.remove('hidden');
			h5.classList.remove('hidden');
			
			reserveDayList.forEach(list => {
				  // 각 예약 정보에 대한 테이블 행(<tr>)을 생성합니다.
				  const row = document.createElement('tr');

				  // 1. startTime 열(<td>)을 생성하고 예약 정보의 startTime을 추가합니다.
				  const startTimeCell = document.createElement('td');
				  startTimeCell.textContent = list.startTime;

				  // 2. endTime 열(<td>)을 생성하고 예약 정보의 endTime을 추가합니다.
				  const endTimeCell = document.createElement('td');
				  endTimeCell.textContent = list.endTime;

				  // 3. 각 <td> 열을 <tr> 행에 추가합니다.
				  row.appendChild(startTimeCell);
				  row.appendChild(endTimeCell);

				  // 4. 생성한 <tr> 행을 HTML 테이블의 <tbody> 내부에 추가합니다.
				  tbody.appendChild(row);
				});
		}
			document.getElementById('totalValue').innerText = result.totalValue
	}
	</script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	<script src="https://cdn.iamport.kr/v1/iamport.js"></script>
	<script>
	const payBtn = document.getElementById('pay')
	 
	
	const spaceName = '${spaceDTO.spaceName}'
	const spaceIdx = '${spaceDTO.idx}'
	

			
	payBtn.onclick =  function(){
		const price = +document.getElementById('totalValue').innerText;
		
		IMP.init('imp08380387')
        IMP.request_pay({
        	  pg: "kakaopay",
              pay_method: "card",
              name: spaceName,
              amount: +price,
              merchant_uid: "ORD20231030-"+ new Date().getTime()
          } 
          ,function(rsp){
        	  	if(rsp.success){
        		 	axios({
	    	        	url: cpath + "/space/pay/"+ spaceIdx,
	    	        	method: "POST",
	    	        	headers: { "Content-Type": "application/json" },
    	        		data: 	{
			    		          imp_uid: rsp.imp_uid,
			    		          merchant_uid: rsp.merchant_uid,
			    		          paid_amount : rsp.paid_amount
			    		        }
	    	      }).then((payResponse) => {
	    	        // 서버 결제 API 성공시 로직
    	     	    const payIdx = payResponse.data.payIdx;
    	     	    reserveDTO.pay_payIdx = payIdx;
    	     	   
	    	        const reserveUrl = cpath + "/space/reserve";
	    			const reserveOpt = {
						method : "POST",
						body : JSON.stringify(reserveDTO),
						headers: {
								"Content-Type": "application/json;charset=utf-8"
								}
						}
	    			
	    				fetch(reserveUrl, reserveOpt).then(resp => resp.text())
			    			.then(text => {
			    				alert('예약이 완료 되었습니다.')
			    				location.href = cpath + '/member/mypage/'+reserveDTO.member_idx;
		    				})
		    		})
			  }else{
				   alert('결제에 실패하였습니다. 에러 내용 \n' + rsp.error_msg);
			  }
    	   }
          )
		}
</script>
</body>
</html>