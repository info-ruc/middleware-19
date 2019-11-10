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
         　
           
            function selectCourse(id,name,tid,tname){
                               
					              
					              
                if(confirm("确定要选此课题吗？")){
                     document.getElementById("ktid").value = id;
                     document.getElementById("ktname").value = name;
                     document.getElementById("tid").value = tid;
                     document.getElementById("tname").value = tname;
                     document.forms[0].submit();
                 }				     
 
            }
     
           function cancelCourse(id){
               if(confirm("确定要取消所选课题吗？")){
                     window.location.href = "<%=path%>/xuantiCancel.action?id=" + id;
                 }		
           }
        </script>
	</head>

	<body leftmargin="2" topmargin="2" background='<%=path %>/img/allbg.gif'>
	
	
	 <form action="<%=path%>/xuantiSave.action" method="post">
	                            <input type="hidden" id="ktid" name="xuanti.ktid"  />
					            <input type="hidden" id="ktname" name="xuanti.ktname"   />
					             <input type="hidden" id="tid" name="xuanti.tid"   />
					              <input type="hidden" id="tname" name="xuanti.tname"  　/>
	
	</form>
			<table width="98%" border="0" cellpadding="2" cellspacing="1"
			bgcolor="#D1DDAA" align="center" style="margin-top: 8px">
			<tr bgcolor="#E7E7E7">
				<td height="14" colspan="8" background="<%=path%>/images/tbg.gif">
					&nbsp;我的选题&nbsp;
				</td>
			</tr>
			<tr align="center" bgcolor="#FAFAF1" height="22">
				<td>
					课题名称
				</td>
				<td>
					指导老师
				</td>
			 

				<td width="12%">
					操作
				</td>
			</tr>
			<s:iterator value="#request.xuantiList" id="xuanti">
				<tr align='center' bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='red';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">


					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#xuanti.ktname" />
					</td>

					<td bgcolor="#FFFFFF" align="center">
						<s:property value="#xuanti.tname" />
					</td>
				 

					<td bgcolor="#FFFFFF" align="center">
						 
                       <input type="button" value="取消选择" onclick="cancelCourse('<s:property value="#xuanti.id" />')"/>
				 
					</td>
				</tr>
			</s:iterator>
		</table>
	</body>
</html>
