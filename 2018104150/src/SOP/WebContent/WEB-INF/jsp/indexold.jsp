<%@ page language="java" import="java.util.*,java.sql.*,
sop.dao.domain.*,sop.service.*,sop.service.impl.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>主页</title>

<style type="text/css">
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>
</head>
<body>
<%
GeneralService  gs=new GeneralServiceImpl();
ArrayList<Project> projects=gs.getAllProjects();
ArrayList<Company> companys=gs.getAllCompanys();
ArrayList<Programmer> programmers=gs.getAllProgrammers();
ArrayList<Team> teams=gs.getAllTeams();
%>

<c:if test="${user.getType()=='0' }">
<a href="${pageContext.request.contextPath }/EstablishTeamUIServlet">成立团队</a>
</c:if>

<c:if test="${user.getType()=='1' }">
<a href="${pageContext.request.contextPath }/IssueProjectUIServlet">发布项目</a>
</c:if>

  <br>
  <b>项目列表</b>
  <br>
  <c:forEach items="<%=projects %>" var="keyword" varStatus="id">
	 <table class="gridtable" border='1px'>
	 <tr>
		
	    <td><a href="${pageContext.request.contextPath }/ProjectDetailUIServlet?proId=${keyword.getProId() }">名称${keyword.getProName() }</a></td>
	    <td>proId${keyword.getProId() }</td>
	    <td>描述${keyword.getDescribtion() }</td>
	    <td>类型${keyword.getType() }</td>
	    <td>预算${keyword.getMaxBudget() }</td>
	    <td>是否选择团队${keyword.isHasTeam() }</td>
	    <td>选的标${keyword.getBId() }</td>
	 <tr>
	 </table>
</c:forEach>
				
  <b>公司列表</b>
  <br>
  <c:forEach items="<%=companys %>" var="keyword" varStatus="id">
	 <table class="gridtable" border='1px'>
	 <tr>
	 
	    <td>公司id${keyword.getCid() }</td>
	     <td><a href="${pageContext.request.contextPath }/CompanyDetailUIServlet?cid=${keyword.getCid() }">名称${keyword.getCompanyName() }</a></td>
	    <td>电话${keyword.getTelephoneNum() }</td>
	    <td>地址${keyword.getLocation() }</td>
	 <tr>
	 </table>
</c:forEach>

 <b>程序员列表</b>
  <br>
  <c:forEach items="<%=programmers %>" var="keyword" varStatus="id">
	 <table class="gridtable" border='1px'>
	 <tr>
	    <td>程序员id${keyword.getPid() }</td>
	    <td><a href="${pageContext.request.contextPath }/ProgrammerDetailUIServlet?pid=${keyword.getPid() }">真实姓名${keyword.getRealName() }</a></td>
	    <td>真实姓名${keyword.getRealName() }</td>
	    <td>身份证号${keyword.getIdNum() }</td>
	    <td>简历概述${keyword.getOverview() }</td>
	    <td>毕业学校${keyword.getGraduation() }</td>
	    <td>学历${keyword.getEducationDegree() }</td>
	    <td>能力证书${keyword.getCertificate() }</td>
	    <td>工作经历${keyword.getExperience() }</td>
	 <tr>
	 </table>
</c:forEach>

 <b>团队列表</b>
  <br>
  <c:forEach items="<%=teams %>" var="keyword" varStatus="id">
	 <table class="gridtable" border='1px'>
	 <tr>
	     <td>团队id${keyword.getTeamId() }</td>
		<td><a href="${pageContext.request.contextPath }/TeamDetailUIServlet?teamId=${keyword.getTeamId() }">名称${keyword.getTeamName() }</a></td>
	    <td>团队介绍${keyword.getProfile() }</td>
	    <td>队长名称${keyword.getLeaderId() }</td>	    
	     <c:if test="${user.getType()=='0' }">
		<td><a href="${pageContext.request.contextPath }/ApplyForTeamServlet?tid=${keyword.getTeamId() }&pid=${user.getPid()}">申请入队</a></td>
		</c:if>
	 <tr>
	 </table>
</c:forEach>
</body>
</html>