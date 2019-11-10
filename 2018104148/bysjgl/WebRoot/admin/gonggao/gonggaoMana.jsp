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
		
        <script language="javascript">
           function gonggaoDel(ggId)
           {
               if(confirm('您确定删除吗？'))
               {
                   window.location.href="<%=path %>/gonggaoDel.action?ggId="+ggId;
               }
           }
           
           function gonggaoAdd()
           {
                 var url="<%=path %>/admin/gonggao/gonggaoAdd.jsp";
				 window.location.href=url;
           }
       </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/images/allbg.gif'>
			<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
				<tr bgcolor="#E7E7E7">
					<td height="14" colspan="11" background="<%=path %>/images/tbg.gif">&nbsp;&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FAFAF1" height="22">
					<td width="4%">序号</td>
					<td width="8%">编号</td>
					<td width="11%">标题</td>
					<td width="11%">内容</td>
					<td width="11%">时间</td>
					<td width="11%">操作</td>
		        </tr>	
				<s:iterator value="#request.gonggaoList" id="gonggao" status="ss">
				<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#ss.index+1"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#gonggao.ggId"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#gonggao.ggTitle"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
					    <s:property value="#gonggao.ggContent"/>
					</td>
					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#gonggao.ggTime"/>
					</td>
					
					<td bgcolor="#FFFFFF" align="center">
						<a href="#" onclick="gonggaoDel(<s:property value="#gonggao.ggId"/>)" class="pn-loperator">删除</a>
					</td>
				</tr>
				</s:iterator>
			</table>
			
			
			<table width='98%'  border='0'style="margin-top:8px;margin-left: 8px;">
			  <tr>
			    <td>
			      <input type="button" value="添加公告" style="width: 120px;" onclick="gonggaoAdd();" />
			    </td>
			  </tr>
		    </table>
	</body>
</html>
