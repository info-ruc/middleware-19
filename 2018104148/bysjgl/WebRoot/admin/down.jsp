<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
			        <td id="dd" width="200" height="30">&nbsp;管理员：[${admin.userName}]
			        </td>
			        <td><div align="center" class="STYLE1">&nbsp;</div></td>
			        <td width="200"><div align="right" class="STYLE1"> 
			        	<fmt:formatDate value="<%=new Date() %>" pattern="yyyy年MM月dd日  "/> 
			       		<!-- 今天是：<span id="timeShow"></span> -->
			        </div></td>
			      </tr>
		       </table>
		    </td>
		    <td width="28"><img src="<%=path %>/images/main_43.gif" width="28" height="30" /></td>
		  </tr>
</table>
</body>
<script type="text/javascript">
	function currentdate() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
	    $("#timeShow").html(currentdate);
	} 
	setInterval("currentdate()",1000);//1000为1秒钟
</script>

</html>
