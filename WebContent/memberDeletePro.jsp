<%@page import="ch13.model.BoardDBBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String id =(String)session.getAttribute("id"); //세션이 저장되어있기 때문에 로그인세션이 있는 상태에서만 회원탈퇴 가능하다.
if(id == null){
%>
	<script>
	alert("로그인 하셔야 합니다.");
	</script>
	<input type="button" onclick="location.href='login.jsp'" value="로그인"/>
<%	
}
String password =request.getParameter("password");

BoardDBBean dbPro = BoardDBBean.getInstance();
boolean result = dbPro.deleteMember(id, password);

if(result) {
	session.invalidate();
%>
<center>
	<h4>회원탈퇴가 완료 되었습니다.</h4>
</center>
<%}else{%>
<center>
	<h4>비밀번호를 다시 확인해주세요</h4>
</center>
<%}%>