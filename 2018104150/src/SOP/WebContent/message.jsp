<%@ page language="java" import="java.util.*,java.sql.*,
sop.dao.domain.*,sop.service.*,sop.service.impl.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>消息提示</title>
</head>
<body>

 <b>消息</b>
<h2> ${message }</h2>
  <br>

<c:if test="${user==null }">
<a href="${pageContext.request.contextPath }/login.jsp">登陆</a>
</c:if>

<c:if test="${user!=null }">
<a href="${pageContext.request.contextPath }/IndexUIServlet">回到主页</a>
</c:if>

</body>
</html>