<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
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
           function shitiAdd()
           {
              var url="<%=path %>/atea/shiti/shitiAdd.jsp";
              window.location.href=url;
           }
           function down1(fujianPath,fujianYuashiMing)
           {
               var url="<%=path %>/updown/updown.jsp?fujianPath="+fujianPath+"&fujianYuashiMing="+fujianYuashiMing;
		       url=encodeURI(url); 
               url=encodeURI(url); 
               window.open(url,"_self");
           }
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/images/allbg.gif'>
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr bgcolor="#E7E7E7">
					<td height="14" colspan="13" background="<%=path %>/images/tbg.gif">&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FAFAF1" height="22">
					<td width="4%">序号</td>
					<td width="10%">学生姓名</td>
					<td width="15%">文档类型</td>
					<td width="25%">附件下载</td>
					 
					<td width="10%">操作</td>
		        </tr>	
				<s:iterator value="#request.shitiList" id="shiti" status="ss">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#ss.index+1"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#shiti.stuname"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#shiti.mingcheng"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#shiti.fujianYuanshiming"/>
						&nbsp;&nbsp;&nbsp;
						<a href="#" onclick="down1('<s:property value="#shiti.fujian"/>','<s:property value="#shiti.fujianYuanshiming"/>')" style="font-size: 10px;color: red">下载</a>
					</td>
					 
					<td bgcolor="#FFFFFF" align="center">
 
						<a class="pn-loperator" href="<%=path %>/shitiDel.action?id=<s:property value="#shiti.id"/>">删除</a>
					</td>
				</tr>
				</s:iterator>
			</table>
			
			 
	</body>
</html>
