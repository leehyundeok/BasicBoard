<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="color.jspf" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body body bgcolor="<%=bodyback_c %>">
<h2> 회원가입</h2>

<form action ="insertTestPro.do" method="post">
아이디:<input type ="text" name="id" id="id" maxlength="20"><br>
이메일:<input type = "text" name="email" id="email" maxlength="50"><br>
패스워드:<input type = "password" name="password" id="password" maxlength="16"><br>
이름:<input type = "text" name="name" id="name" maxlength="10"><br>
<input type = "submit" value="입력완료">
</form>
</body>
</html>