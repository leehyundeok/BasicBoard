<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% //request.setCharacterEncoding("utf-8"); 

String id = (String)request.getAttribute("id");
String email = (String)request.getAttribute("email");
String password = (String)request.getAttribute("password");
String name = (String)request.getAttribute("name");
String str = (String)request.getAttribute("str");
int result = (int)request.getAttribute("result");
if(result == 1){
	str= id + "님 회원가입 완료 되었습니다.";

%>
<input type="button" onclick="location.href='login.jsp'" value="로그인"/>

<%
} else {
	str= id + "님 회원가입에 실패하였습니다. 다시 가입해 주세요.";
}
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>