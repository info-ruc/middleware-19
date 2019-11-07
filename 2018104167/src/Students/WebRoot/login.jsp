<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    学生课程成绩查看——管理员登录<br>
    <form name="form0" action="/Students/loginServlet" method="post">
		账号：<input type="text" name="user" style="width: 106px; "><br>
    	密码：<input type="password" name="passwd" style="width: 106px; "/><br>
    		 <input type="submit" value="登录并查看课程成绩" style="width: 140px; color: Black"/>
    </form>		
  </body>
</html>
