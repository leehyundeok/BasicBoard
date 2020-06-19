<%@page import="java.util.Enumeration"%>
<%@page import="java.io.IOException"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="ch13.*"%>
 <%@ page import="com.oreilly.servlet.MultipartRequest" %>
    <%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
	<%@ page import="java.util.Enumeration" %>
	<%@ page import="java.io.*" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<% request.setCharacterEncoding("UTF-8"); %>
<%
	response.sendRedirect("list.jsp");
%>



