<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/space.css">
<div class="frame listHead">
	
	<div>
		<h2>공간 정보</h2>
		<a href="${cpath }/space/list?pageNum=${pageNum }&category=${map.category }&region=${map.region }&minPrice=${map.minPrice }
				&maxPrice=${map.maxPrice }&starRating=${map.starRating }&spaceName=${map.spaceName }"><button>≡ 목록으로</button></a>
	</div>
	
	
	<c:if test="${boardDTO.member_idx == login.idx  || login.memberType == 'admin'}">
		<div id="supNav">
			<c:if test="${boardDTO.member_idx == login.idx }">
				<a href="${cpath }/space/holiday/${spaceDTO.idx}"><button>휴일 추가</button></a>
			</c:if>
			<div id="supud">
				<c:if test="${boardDTO.member_idx == login.idx }">
					<a href="${cpath }/space/modify/${spaceDTO.idx}"><button>수정</button></a>
				</c:if>
				<button id="spaceDeleteBtn">삭제</button>
			</div>
		</div>
	</c:if>

<!-- fileList가 있으면 띄울 이미지 -->
	<c:if test="${not empty fileList }">
		<div class="img-container">
			<button class="prev" onclick="showImage(-1)">←</button>
			<c:forEach var="fileDTO" items="${fileList }">
				<div class="imgFile" style="display: none;">
					<img src="${cpath}/upload/${fileDTO.fileName }">
				</div>
			</c:forEach>
			<button class="next" onclick="showImage(1)">→</button>
		</div>
	</c:if>

<!-- 지도 등 space의 정보 -->
	<div style="display: flex;" id="spaceBody">
      
      <div id="spaceInfo">
         <table id="sitab">
            <tr>
               <th>주소</th>
               <td>${addressDTO.location}</td>
            </tr>
            <tr>
               <th>공간이름</th>
               <td>${spaceDTO.spaceName }</td>
            </tr>
            <tr>
               <th>면적</th>
               <c:if test="${not empty spaceDTO.area }">
             	  <td><fmt:formatNumber value="${spaceDTO.area }" pattern="#,###" />m²</td>
               </c:if>
               <c:if test="${empty spaceDTO.area }">
				  <td>-</td>
               </c:if>
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
            <tr>
               <th>주차</th>
               <td>${spaceDTO.park }</td>
            </tr>
         </table>
      </div>
      <div id="smap">
	      <div id="map" style="width: 100%; height: 380px;"></div>
			<a href="${cpath }/space/reserve/${spaceDTO.idx}"><button>예약하기</button></a>
      </div>
   </div>



<!-- 안내사항, 후기 -->
	<div class="tabs">
		<div class="tab" onclick="showTab('tab1', this)">안내사항</div>
		<div class="tab" onclick="showTab('tab2', this)">공지사항</div>
		<div class="tab" onclick="showTab('tab3', this)">후기</div>
		<div class="tab" onclick="showTab('tab4', this)">문의사항</div>
	</div>

	<div class="tab-content" id="tab1">
			<h2>안내사항</h2>
		<div class="tabHead">
			<p><pre>${spaceDTO.guide }</pre></p>
		</div>
	</div>

	<div class="tab-content" id="tab2">
		<div style="display: flex; justify-content: space-between;">
		<h2>공지사항</h2>
		
		<c:if test="${boardDTO.member_idx == login.idx }">
			<a href="${cpath }/space/facility/${spaceDTO.idx}">
				<button style="padding: 5px 10px;margin-top: 30px;
							background-color: #50a0f0; border: none;color: white;border-radius: 5px;margin-bottom: 20px;">
					공지 작성
				</button>
			</a>
		</c:if>
		</div>
		    	<c:if test="${empty facilityList }">
		    		<tr>
		    			<td>공지가 없습니다.</td>
		    		</tr>
		    	</c:if>
		    	<c:if test="${not empty facilityList }">
		<div class="tabHead">
		<table id="nottb">
		    <thead>
		        <tr>
		            <td>제목</td>
		            <td>작성일자</td>
		            <c:if test="${boardDTO.member_idx == login.idx }">
		            <td></td>
		            </c:if>
		        </tr>
		    </thead>
		    <tbody>
		        <c:forEach var="facility" items="${facilityList}">
		        <tr>
		            <td class="facilityTitle">${facility.title}</td>
		            <td>${facility.wdate}</td>
			<c:if test="${boardDTO.member_idx == login.idx }">
			            <td>
			            	<a href="${cpath }/space/deleteFacility/${spaceDTO.idx }/${facility.idx}">
			            		<button style="border: 2px solid #dadada;">삭제</button>
			            	</a>
						</td>
			        </c:if>
		        </tr>
		        <tr class="content-row">
		            <c:choose>
		        	<c:when test="${boardDTO.member_idx == login.idx }">
			            <td colspan="3" class="content"><pre>${facility.content}</pre></td>
		        	</c:when>
		        	<c:otherwise>
			            <td colspan="2" class="content"><pre>${facility.content}</pre></td>
		        	</c:otherwise>
		        </c:choose>
		        </tr>
		        </c:forEach>
		    </tbody>
		</table>
		</div>
		    </c:if>
	</div>

	<div class="tab-content" id="tab3">
		<div style="display: flex; justify-content: space-between;">
		<h2>후기</h2>
		<a href="${cpath }/space/review/${boardDTO.idx}">
			<button style="padding: 5px 10px; margin-top: 30px;
							background-color: #50a0f0; border: none;color: white;border-radius: 5px;">
				후기 작성
			</button>
		</a>
		</div>
		<c:if test="${not empty reviewList }">
		<div class="tabHead">
			<span style="background-color: #a0c0f0;border-radius: 20px; color: white;padding: 5px 10px;margin-bottom: 20px;">
			<span style="color: yellow; font-size: 1.2rem;">★</span><fmt:formatNumber value="${spaceDTO.rate}" type="number" pattern="#.0" />
			</span>
			<div id="revdiv">
					<c:forEach var="reviewDTO" items="${reviewList }">
						<div class="spacerev">
							<div>${reviewDTO.nickName } | 
								<span>
									${reviewDTO.score < 1 ? '☆' : '★'}
									${reviewDTO.score < 2 ? '☆' : '★'}
									${reviewDTO.score < 3 ? '☆' : '★'}
									${reviewDTO.score < 4 ? '☆' : '★'}
									${reviewDTO.score < 5 ? '☆' : '★'}
								</span>
							</div>
							<div style="display:flex; justify-content: space-between;">
	    						<fmt:formatDate value="${reviewDTO.wdate}" pattern="yyyy-MM-dd HH:mm"/>
								<c:if test="${reviewDTO.member_idx == login.idx || login.memberType == 'admin'}">
									<span id="revdel" onclick="todelrev('${spaceDTO.idx }', '${reviewDTO.idx}')">❌</span>
								</c:if>
							</div>
							<div>${reviewDTO.content }</div>
						</div>
					</c:forEach>
			</div>
		</div>
		</c:if>
		<c:if test="${empty reviewList }">
			후기가 없습니다.
		</c:if>
	</div>

	<div class="tab-content" id="tab4">
		<h2>문의사항</h2>
		<c:if test="${not empty login }">
			<form method="POST" id="sendMessageMain" action="${cpath }/message/sendNotice?idx=${spaceDTO.idx}">
			<input type="hidden" name="receiverId" value="${memberDTO.userid }">
			<table>
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
		</c:if>
		<c:if test="${empty login }">
		<div style="text-align: center; margin-top: 20px;">로그인 후 이용 가능합니다.</div>
		</c:if>
	</div>


</div>	<!-- end of div.frame -->

<%@ include file="../footer.jsp"%>

<!-- 지도 표시 시작 -->
<script>
	function mapLoad(){
	   var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	   		mapOption = {
	           center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	           level: 3 // 지도의 확대 레벨
	       }; 
	   		
	   // 지도를 생성
	   var map = new kakao.maps.Map(mapContainer, mapOption); 
	   
	   // 주소-좌표 변환 객체를 생성
	   var geocoder = new kakao.maps.services.Geocoder();
	   
	   const location = '${addressDTO.location}'
	   // 주소로 좌표를 검색
	   geocoder.addressSearch(location, function(result, status) {
	   
	       // 정상적으로 검색이 완료됐으면 
	        if (status === kakao.maps.services.Status.OK) {
	   
	           var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
	   
	           // 결과값으로 받은 위치를 마커로 표시
	           var marker = new kakao.maps.Marker({
	               map: map,
	               position: coords
	           });
	   
	           // 인포윈도우로 장소에 대한 설명을 표시
	           var infowindow = new kakao.maps.InfoWindow({
	               content: '<div style="width:150px;text-align:center;padding:6px 0;">${spaceDTO.spaceName}</div>'
	           });
	           infowindow.open(map, marker);
	   
	           // 지도의 중심을 결과값으로 받은 위치로 이동
	           map.setCenter(coords);
	       }
	   });
	}
	window.addEventListener('DOMContentLoaded', mapLoad)
</script>
<!-- 지도 표시 끝 -->

<!-- 이미지 표시 이동 시작 -->
<script>
	let currentImage = 0;
	const images = document.querySelectorAll('.imgFile');
	let intervalId;
	
	function showImage(step) {
	    images[currentImage].style.display = 'none';
	    currentImage += step;
	
	    if (currentImage < 0) {
	        currentImage = images.length - 1;
	    } else if (currentImage >= images.length) {
	        currentImage = 0;
	    }
	
	    images[currentImage].style.display = 'block';
	}
	
	function startAutoChange() {
	    showImage(0); // 초기 이미지 표시
	
	    intervalId = setInterval(() => {
	        showImage(1); // 2초마다 다음 이미지로 전환
	    }, 2000);
	}
	
	function stopAutoChange() {
	    clearInterval(intervalId);
	}
	
	startAutoChange();
	
	const imgContainer = document.querySelector('.img-container');
	imgContainer.addEventListener('mouseover', stopAutoChange);
	imgContainer.addEventListener('mouseout', startAutoChange);
</script>
<!-- 이미지 표시 이동 끝 -->

<!-- 탭 시작 -->
<script>
    // 탭을 표시하거나 숨기는 함수
    function showTab(tabId, tab) {
      // 모든 탭을 숨김
      const tabs = document.querySelectorAll('.tab-content');
      tabs.forEach(tab => tab.classList.remove('active'));

      // 모든 탭 버튼 스타일 초기화
      const tabButtons = document.querySelectorAll('.tab');
      tabButtons.forEach(btn => btn.classList.remove('active'));

      // 지정한 탭을 표시하고 활성 탭 버튼 스타일을 적용
      document.getElementById(tabId).classList.add('active');
      tab.classList.add('active');
    }

    // 페이지 로드시 첫 번째 탭을 표시
    showTab('tab1', document.querySelector('.tabs .tab'));
  </script>
<!-- 탭 끝 -->

<!-- 공지사항 시작 -->
<script>
    // JavaScript 코드
    const titleElements = document.querySelectorAll('.facilityTitle');
    const contentRows = document.querySelectorAll('.content-row');

    titleElements.forEach((titleElement, index) => {
        titleElement.addEventListener('click', () => {
            contentRows[index].classList.toggle('show-content');
        });
    });
</script>
<!-- 공지사항 끝 -->

<script>
// todelrev('${spaceDTO.idx }', '${reviewDTO.idx}')
	function todelrev(space, review){
		location.href = cpath + '/space/deleteReview/' + space + '/' + review
	}
</script>

<script>
	const spaceDeleteBtn = document.getElementById('spaceDeleteBtn')
	spaceDeleteBtn.onclick = function(){
		if(confirm('정말 삭제하시겠습니까?')){
			location.href= cpath + '/space/delete/${spaceDTO.idx}'
		}
	}
	
</script>

</body>
</html>