<%@ page language="java" import="java.util.*,java.sql.*,
sop.dao.domain.*,sop.service.*,sop.service.impl.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注册</title>
<style type="text/css">
.smart-green {
margin-left:auto;
margin-right:auto;
max-width: 1000px;
background: #F8F8F8;
padding: 30px 30px 20px 30px;
font: 12px Arial, Helvetica, sans-serif;
color:#8F44AD;
border-radius: 5px;
-webkit-border-radius: 5px;
-moz-border-radius: 5px;
}
.smart-green placeholder{
color:#090;
text-decoration:overline;
}
.smart-green h1 {
font: 24px "Trebuchet MS", Arial, Helvetica, sans-serif;
padding: 20px 0px 20px 40px;
display: block;
margin: -30px -30px 10px -30px;
color: #FFF;
background: #8F44AD;
text-shadow: 1px 1px 1px #949494;
border-radius: 5px 5px 0px 0px;
-webkit-border-radius: 5px 5px 0px 0px;
-moz-border-radius: 5px 5px 0px 0px;
border-bottom:1px solid #89AF4C;
}
.smart-green label {
font: 24px "Trebuchet MS", Arial, Helvetica, sans-serif;
padding: 20px 0px 20px 40px;
display: block;
margin: -30px -30px 10px -30px;
text-shadow: 1px 1px 1px #949494;
border-radius: 5px 5px 0px 0px;
-webkit-border-radius: 5px 5px 0px 0px;
-moz-border-radius: 5px 5px 0px 0px;
border-bottom:1px solid #89AF4C;
}
.smart-green label>span {
display: block;
font-size: 20px;
margin-top: 10px;
color: #5E5E5E;
}
.smart-green input[type="text"], .smart-green input[type="email"], .smart-green textarea, .smart-green select {
color: #555;
height: 30px;
line-height:15px;
width: 100%;
padding: 0px 0px 0px 10px;
margin-top: 2px;
border: 1px solid #E5E5E5;
background: #FBFBFB;
outline: 0;
-webkit-box-shadow: inset 1px 1px 2px rgba(238, 238, 238, 0.2);
box-shadow: inset 1px 1px 2px rgba(238, 238, 238, 0.2);
font: normal 14px/14px Arial, Helvetica, sans-serif;
}
.smart-green textarea{
height:400px;
padding-top: 10px;
}
.smart-green select {
background: url('down-arrow.png') no-repeat right, -moz-linear-gradient(top, #FBFBFB 0%, #E9E9E9 100%);
background: url('down-arrow.png') no-repeat right, -webkit-gradient(linear, left top, left bottom, color-stop(0%,#FBFBFB), color-stop(100%,#E9E9E9));
appearance:none;
-webkit-appearance:none;
-moz-appearance: none;
text-indent: 0.01px;
text-overflow: '';
width:100%;
height:30px;
}
.smart-green .button {
background-color: #8F44AD;
border-radius: 5px;
-webkit-border-radius: 5px;
-moz-border-border-radius: 5px;
border: none;
padding: 10px 25px 10px 25px;
color: #FFF;
text-shadow: 1px 1px 1px #949494;
}
.smart-green .button:hover {
background-color:#80A24A;
}
</style>
</head>
<body>
<%String type=(String)request.getAttribute("type");
%>
				<c:if test="${type=='0' }">
				<form class="smart-green" 
		method="post"
			action="${pageContext.request.contextPath }/RegisterProgrammerServlet"
			name="reg" >
				<b>注册接包方</b>
  				<br>
				<input type="text" name="id" placeholder="账号" />
				<input type="text" name="password" placeholder="密码" />
				<input type="text" name="mail" placeholder="邮箱" />
				<input type="text" name="Name" placeholder="真实姓名" />
				<input type="text" name="IdNum" placeholder="身份证号" />
				<input type="text" name="Overview" placeholder="简历概述" />
				<input type="text" name="Graduation" placeholder="毕业学校" />
				<input type="text" name="EducationDegree" placeholder="学历" />
				<input type="text" name="Certificate" placeholder="能力证书" />
				<input type="text" name="Experience" placeholder="工作经历" />
				<input type="submit" class="button" value="提交" name="submit">
</form>
				</c:if>
				
				<c:if test="${type=='1' }">
				<form class="smart-green" 
		method="post"
			action="${pageContext.request.contextPath }/RegisterCompanyServlet"
			name="reg" >
				<b>注册发包方</b>
  				<br>
				<input type="text" name="cid" placeholder="账号" />
				<input type="text" name="password" placeholder="密码" />
				<input type="text" name="mail" placeholder="邮箱" />
				<input type="text" name="companyName" placeholder="公司名称" />
				<input type="text" name="telephoneNum" placeholder="联系电话" />
				<input type="text" name="location" placeholder="公司位置" />
				<input type="submit" class="button" value="提交" name="submit">
</form>
				</c:if>

</body>
</html>