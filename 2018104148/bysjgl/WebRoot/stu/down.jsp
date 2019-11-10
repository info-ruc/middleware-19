<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.Date"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		<!--
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		.STYLE1 {
			color: #767a7a;
			font-size: 12px;
		}
		-->
		#dd{
			color:rgb(55, 82, 127);
		    font-family:宋体;
		    line-height:20px;
			font-size: 12px;
		}
	</style>
	
  </head>
  
  <body>
     <table width="100%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="22" height="30"><img src="<%=path %>/images/main_38.gif" width="22" height="30" /></td>
		    <td background="<%=path %>/images/main_40.gif">
		       <table width="100%" border="0" cellspacing="0" cellpadding="0">
			      <tr>
			        <td id="dd" width="200" height="30">&nbsp;学生:[${sessionScope.stu.stuXuehao }]${sessionScope.stu.stuRealname}</td>
			        <td><div align="center" class="STYLE1">&nbsp;</div></td>
			        <td width="200" ><div align="right" class="STYLE1">      
			        	<fmt:formatDate value="<%=new Date() %>" pattern="yyyy年MM月dd日   "/> 
			        	<%-- <iframe src="<%=path %>/time/time.jsp" width="220" height="220" frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” ></iframe>  --%>
			        </div></td>
			      </tr>
		       </table>
		    </td>
		    <td width="28"><img src="<%=path %>/images/main_43.gif" width="28" height="30" /></td>
		  </tr>
	</table>
  </body>
</html>
