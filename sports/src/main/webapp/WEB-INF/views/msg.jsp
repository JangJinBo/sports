<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<div class="frame">
<script>
 const msg = '${msg}'
 alert(msg)
 window.location.href = cpath+"/";
</script>
</div>
<%@ include file="footer.jsp" %>
