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

		<link rel="stylesheet" type="text/css" href="<%=path%>/css/base.css" />
		<script type="text/javascript" src="<%=path%>/js/popup.js"></script>
		<script language="javascript">
         
       </script>
	</head>

	<body leftmargin="2" topmargin="2"
		background='<%=path%>/images/allbg.gif'>
		<table width="98%" border="0" cellpadding="2" cellspacing="1"
			bgcolor="#D1DDAA" align="center" style="margin-top: 8px">
			<tr bgcolor="#E7E7E7">
				<td height="14" colspan="8" background="<%=path%>/images/tbg.gif">
					&nbsp;通知公告&nbsp;
				</td>
			</tr>
			<tr align="center" bgcolor="#FAFAF1" height="22">
				<td>
					标题
				</td>
				<td>
					内容
				</td>
			</tr>
			<s:iterator value="#request.gonggaoList" id="tongzhi">
				<tr align='center' bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='red';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">


					 
					<td bgcolor="#FFFFFF" align="center" width="30px">
						<s:property value="ggTitle" />
					</td>
					<td bgcolor="#FFFFFF" align="center" width="80px">
						<s:property value="ggContent" />
					</td>

				 
				</tr>
			</s:iterator>
		</table>
 
		 
	</body>
</html>