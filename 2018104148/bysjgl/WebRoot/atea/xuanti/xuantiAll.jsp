<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
int usertype = request.getSession().getAttribute("userType")==null?0:Integer.parseInt(request.getSession().getAttribute("userType")+"");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />

		<link rel="stylesheet" type="text/css" href="<%=path %>/css/base.css" />
		
		<script language="JavaScript" src="<%=path %>/js/public.js" type="text/javascript"></script>
		
        <script language="javascript">
           function queren(id)
           {
               if(confirm('您确定要确认吗？'))
               {
                   window.location.href="<%=path %>/xuantiQueren.action?id="+id;
               }
           }
            function quxiao(id)
           {
               if(confirm('您确定要取消吗？'))
               {
                   window.location.href="<%=path %>/xuantiQuxiao.action?id="+id;
               }
           }
           
           
           
            function tketiDel(id)
           {
               if(confirm('您确定删除吗？'))
               {
                   window.location.href="<%=path%>/xuantiDel.action?id="+id;
               }
           }
           
           
        </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/img/allbg.gif'>
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr bgcolor="#E7E7E7">
					<td height="14" colspan="14" background="<%=path %>/img/tbg.gif">&nbsp;&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FAFAF1" height="22">
					<td width="4%">序号</td>
					<td width="15%">学生</td>
					<td width="40%">课题名称</td>
					<td width="10%">状态</td>
					<td width="20%">操作</td>
		        </tr>	
				<s:iterator value="#request.xuantiList" id="xuanti" status="ss">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						 <s:property value="#ss.index+1"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						 <s:property value="#xuanti.sname"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#xuanti.ktname"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
					    <s:property value="#xuanti.shzt"/>
					</td>
					 
					<td bgcolor="#FFFFFF" align="center">
					  <% if(usertype!=0){  %>
					   
					   <input type="button" value="确认学生选题" onclick="queren(<s:property value="#xuanti.id"/>)"/>
					    &nbsp; 
					  
					   <input type="button" value="取消学生选题" onclick="quxiao(<s:property value="#xuanti.id"/>)"/>
					    &nbsp; 
					    
					    <% }%>
					  
					   <% if(usertype==0){  %>
						<a href="#" onclick="tketiDel('<s:property value="#xuanti.id" />')" class="pn-loperator">删除</a>
					    <% }%>
						
					</td>
				</tr>
				</s:iterator>
			</table>
	</body>
</html>
