<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp" %>
<link type="text/css" rel="stylesheet" 
					  href="${cpath }/resources/style/space.css">
<style>
	.scrap{
		color: red;
		font-weight: bold;
	}
</style>

<div class="frame listHead">

	<div>
		<h2>대여서비스</h2>
		<c:if test="${login.memberType == 'supp' }">
		<a href="${cpath }/space/register"><button>공간 등록</button></a>
		</c:if>
	</div>

<!-- 검색을 위한 form 시작 -->

	<form id="searchForm" action="${cpath }/space/list">
		<table>
			<tr>
				<td>카테고리</td>
				<td>
					<input type="text" name="category" placeholder="ex)축구" list="categoryList" autocomplete="off">
					<datalist id="categoryList">
					    <c:forEach var="spaceCategory" items="${spaceCategoryList}">
					        <option value="${spaceCategory}"></option>
					    </c:forEach>
					</datalist>
				</td>
			</tr>
			<tr>
				<td>지역</td>
				<td>
					<input type="text" name="region" placeholder="지역 입력">
				</td>
			</tr>
			<tr>
				<td>가격(시간당)</td>
				<td>
					<input type="number" name="minPrice" placeholder="최소값"> ~ 
					<input type="number" name="maxPrice" placeholder="최대값">
				</td>
			</tr>
			<tr>
				<td>별점</td>
				<td>
					<select name="starRating">
						<option value="0.0">선택 없음</option>
						<option value="5.0">5점 이상</option>
						<option value="4.0">4점 이상</option>
						<option value="3.0">3점 이상</option>
						<option value="2.0">2점 이상</option>
						<option value="1.0">1점 이상</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>시설명</td>
				<td>
					<input type="search" name="spaceName" placeholder="검색어 입력">
				</td>
			</tr>
		</table>
		<button type="submit">검색하기</button>
	</form>

<!-- 검색을 위한 form 끝 -->


<!-- spaceList 시작 -->

	<table id="spaceTable">
		<thead>
			<tr>
				<th>즐겨찾기</th>
				<th>시설명</th>
				<th>지역</th>
				<th>카테고리</th>
				<th>가격(시간당)</th>
				<th>별점</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="spaceDTO" items="${spaceList }">
				<tr>
					<td>
						<a href="#" class="toggle-scrap" data-space-idx="${spaceDTO.idx}">
						    <span class="heart ${spaceDTO.favorites != 0 ? 'scrap' : 'unscrap'}">♥</span>
						</a>
					</td>
					<td class="toSpaceView" onclick="tosv('${spaceDTO.idx}')"><a href="${cpath }/space/view/${spaceDTO.idx}">${spaceDTO.spaceName }</a></td>
					<td class="toSpaceView" onclick="tosv('${spaceDTO.idx}')">${spaceDTO.region }</td>
					<td class="toSpaceView" onclick="tosv('${spaceDTO.idx}')">${spaceDTO.spaceCategory }</td>
					<td class="toSpaceView" onclick="tosv('${spaceDTO.idx}')">${spaceDTO.price }</td>
					<td class="toSpaceView" onclick="tosv('${spaceDTO.idx}')"><fmt:formatNumber value="${spaceDTO.rate}" type="number" pattern="#.#" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${empty spaceList }">
		<div style="text-align: center; margin-top: 20px;">등록된 게시글이 없습니다</div>
	</c:if>
	

<!-- spaceList 끝 -->

</div>	<!-- end of div.frame -->

<%@ include file="../footer.jsp" %>

<script>	// 즐겨찾기 구현
// 페이지 로딩 시 실행되도록 이벤트 리스너를 추가
document.addEventListener('DOMContentLoaded', function () {
    // 모든 toggle-scrap 클래스를 가진 요소에 클릭 이벤트 리스너 등록
    const toggleScrapElements = document.querySelectorAll('.toggle-scrap');
    toggleScrapElements.forEach(function (element) {
        element.addEventListener('click', function (event) {
        	event.preventDefault()
            const space_idx = this.getAttribute('data-space-idx');
            toggleScrap(space_idx);
        });
    });
});
//스크랩 토글을 처리하는 함수
async function toggleScrap(space_idx) {
    const url = cpath + '/space/toggleScrap'
    const member_idx = '${login.idx}'
    
    const ob = {
   		space_idx: space_idx,
        member_idx: member_idx
    };

    const opt = {
        method: 'POST',
        body: JSON.stringify(ob),
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
            'isFetch' : true	// 요청할때 인터셉터에서 구분할수 있도록 헤더 추가
        }
    }

    const response = await fetch(url, opt);
    const isRedirect = response.headers.get('isRedirect');
    // 응답받을때 인터셉터가 추가한 헤더에 따라서 리다이렉트인지 아닌지 판별
    
    if(isRedirect) {	// 리다이렉트이면
    	// 응답을 텍스트로 변환해서 주소 이동
    	location.href = await response.text();
    	return
    }
    // 리다이렉트가 아니면 응답을 json으로 변환해서 이후 처리
    const result = await response.json();

    const heartIcon = document.querySelector('.toggle-scrap[data-space-idx="' + space_idx + '"] .heart'); // 문자열 연결로 요소 선택
    if (result.isScrapped) {
        heartIcon.classList.remove('unscrap');
        heartIcon.classList.add('scrap');
    } else {
        heartIcon.classList.remove('scrap');
        heartIcon.classList.add('unscrap');
    }
}
</script>



</body>
</html>