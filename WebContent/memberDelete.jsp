<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="color.jspf" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호를 입력하세요</title>
</head>
<body body bgcolor="<%=bodyback_c %>">

<form name="deletform" action="memberDeletePro.jsp" method="post" onSubmit="retuen check()">
<h4>아이디 <input type="text" name="id" /></h4>
<h4>비밀번호 <input type="password" name="password" /></h4>
<input type="submit" value="delete"onclick="window.location='login.jsp'"/>
<input type="button" value="로그인 화면" onclick="window.location='login.jsp'"/>
</form>
</body>
</html>