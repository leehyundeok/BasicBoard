<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="color.jspf" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<style type="text/css">



*        { margin:0; padding: 0; }



body{

         font-family: "맑은 고딕";

         font-size: 0.75em;

         color: #333;

}

#login-form{

         width:200px;

         margin:100px auto;

         border: 1px solid gray;

         border-radius: 5px;

         padding: 15px;

}

#login-form input{

         display:block;

}


#login-form label{

         margin-top: 10px;

}



#login-form input{

         margin-top: 5px;

}

#login-form input[type='image']{

         margin: 10px auto;

}

</style>
</head>
<body body bgcolor="<%=bodyback_c %>">
<%
String id = (String)session.getAttribute("id");
if(request.getAttribute("checkId") !=null){
int checkId = 0;

}
%>
<form id= "login-form" action="loginPro.do" method="post">
활동 닉네임    <input type="text" name="id" id="id" placeholder="ID"><br>
이메일    <input type="text" name="email" id="email" placeholder="EMAIL"><br>
비밀번호 <input type="password" name="password" id="password" placeholder="PASSWORD"><br>
<input type="submit" value="login">
<input type="button" onclick="location.href='insertTestForm.jsp'" value="회원가입">
<input type="button" onclick="location.href='memberDelete.jsp'" value="회원탈퇴">
</form>
</body>
</html>