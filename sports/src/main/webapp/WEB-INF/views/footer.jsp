<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#footerLogo {
	padding: 50px 0;
	width: fit-content;
}

#footerMain {
	color: white;
	margin: 50px;
}
</style>

<footer>
	<div style="padding: 0 200px; display: flex;">
		<div id="footerLogo">
			<a href="${cpath }"><img
				src="${cpath }/resources/image/Logo_footer.jpg" width="300px">
			</a>
		</div>
		<div id="footerMain">
			Email : KGitbank@KGitbank.com<br> 부산광역시 해운대구 우동 센텀2로 25 센텀드림월드
			11층

		</div>
	</div>

</footer>

<script>
   function right(userid) {
        event.preventDefault()
        var mbox = event.target.childNodes[1]
        mbox.classList.add('disblock')
        mbox.classList.remove('disnone')
   }
   
   document.querySelector("body").addEventListener("click", function(e) {
       if(e.target.classList.contains('mb') == false) {
           var tboxes = document.querySelectorAll('.memberbox')
           tboxes.forEach(e => {
              e.classList.add('disnone')
              e.classList.remove('disblock')
           })
       } 
   })
</script>

</body>
</html>