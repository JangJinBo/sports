<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<div class="frame">
    <div id="center">
       <div>
         <div class="centerBtn"
              style="background-image: url('${cpath}/resources/image/001.jpg'); height: 100%;">
            <a href="${cpath}/space/list">Space Rentals</a>
         </div>
         <div class="centerBtn" style="background-image: url('${cpath}/resources/image/002.jpg');height: 100%;">
            <a href="${cpath}/juBoard/substiBoard">Substitute</a>
         </div>
       </div>
       <div>
         <div class="centerBtn" style="background-image: url('${cpath}/resources/image/003.jpg') ;height: 100%;">
            <a  href="${cpath}/juBoard/teamBoard" >
            TeamMatching</a>
         </div>
         <div class="centerBtn"  
             style="background-image: url('${cpath}/resources/image/004.jpg'); height: 100%;">
            <a href="${cpath}/csc/notice">
            Notice</a>
         </div>
       </div>
   </div>
   
   
</div>

<script>
   const login = '${loginForm}'
   if(login == 'true') {
      document.getElementById('login').dispatchEvent(new Event('click'))
   }
   
// 마우스 진입시 이미지 변경
   function changeImage(btnId, newImageNumber) {
     const btn = document.getElementById(btnId);
     btn.style.backgroundImage = `url(cpath +'/resources/image/${newImageNumber}.jpg')`;
   }

   // 마우스 이탈시 이미지 원래대로 복원
   function restoreImage(btnId, originalImageNumber) {
     const btn = document.getElementById(btnId);
     btn.style.backgroundImage = `url(cpath +'/resources/image/${originalImageNumber}.jpg')`;
   }
</script>

<%@ include file="footer.jsp" %>

