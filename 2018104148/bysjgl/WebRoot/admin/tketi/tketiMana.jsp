<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	int usertype = request.getSession().getAttribute("userType") == null ? 0
			: Integer.parseInt(request.getSession().getAttribute(
					"userType")
					+ "");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />
		<script type="text/javascript" src="<%=path%>/js/popup.js"></script>
		<script language="javascript">
           function tketiDel(id)
           {
               if(confirm('您确定删除吗？'))
               {
                   window.location.href="<%=path%>/tketiDel.action?id="+id;
               }
           }
           
           function tketiAdd()
           {
                 var url="<%=path%>/admin/tketi/tketiAdd.jsp";
                 //var n="";
                 //var w="480px";
                 //var h="500px";
                 //var s="resizable:no;help:no;status:no;scroll:yes";
				 //openWin(url,n,w,h,s);
				 window.location.href=url;
           }		
       </script>
	</head>

	<body leftmargin="2" topmargin="2"
		background='<%=path%>/images/allbg.gif'>
		<table width="98%" border="0" cellpadding="2" cellspacing="1"
			bgcolor="#D1DDAA" align="center" style="margin-top: 8px">
			<tr bgcolor="#E7E7E7">
				<td height="14" colspan="8" background="<%=path%>/images/tbg.gif">
					&nbsp;课题管理&nbsp;
				</td>
			</tr>
			<tr align="center" bgcolor="#FAFAF1" height="22">
				<td>
					课题名称
				</td>
				<td>
					指导老师
				</td>
				<td>
					课题难度
				</td>
				<td>
					课题方向
				</td>
				 
				<td width="12%">
					操作
				</td>
			</tr>
			<s:iterator value="#request.tKetiList" id="tKeti">
				<tr align='center' bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='red';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">


					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#tKeti.name" />
					</td>

					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#tKeti.teaname" />
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#tKeti.ktnd" />
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#tKeti.ktfx" />
					</td>
				 

					<td bgcolor="#FFFFFF" align="center">
						<%
							if (usertype != 0) {
						%>

						<a
							href="<%=path%>/totketiEdit.action?id=<s:property value="#tKeti.id" />"
							class="pn-loperator">编辑</a> &nbsp;

						<%
							}
						%>

						<a href="#" onclick="tketiDel('<s:property value="#tKeti.id" />')"
							class="pn-loperator">删除</a>
					</td>
				</tr>
			</s:iterator>
		</table>

		<%
			if (usertype != 0) {
		%>
		<table width='98%' border='0'
			style="margin-top: 8px; margin-left: 5px;">
			<tr>
				<td>
					<input type="button" value="添加" style="width: 80px;"
						onclick="tketiAdd()" />
				</td>
			</tr>
		</table>
		<%
			}
		%>
		<div id="tip"
			style="position: absolute; display: none; border: 0px; width: 80px; height: 80px;">
			<TABLE id="tipTable" border="0" bgcolor="#ffffee">
				<TR align="center">
					<TD>
						<img id="photo" src="" height="80" width="80">
					</TD>
				</TR>
			</TABLE>
		</div>
	</body>
</html>