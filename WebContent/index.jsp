<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="color.jspf" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>
<style type="text/css">

.a { 
	width: 500px;
	margin:0 auto;
	background-color: dimgrey;
	text-align: center;
	}
.a_{
	width:100px;
	height:100px;
	display: inline-block;
	vertical-align: top;
	}
.a_1 {background-color: yellow;}
.a_2 {background-color: royalblue;}
.a_3 {background-color: green;}



</style>
</head>
<body  body bgcolor="<%=bodyback_c %>" >
<div class="a">
		<div class="a_1"><a href="list.do">글목록</a><div>
		<div class="a_2"><a href="login.jsp">로그인</a><div>
		<div class="a_3"><a href="insertTestForm.do">회원가입</a><div>
</div>
		

	

</body>
</html>