<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored="false" %> 
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>

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
        <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
        <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
        <script type="text/javascript" src="<%=path %>/js/popup.js"></script>
        
        <script language="javascript">
		    function check()
		    {
		        document.formAdd.submit();
		    }
        </script>
	</head>

	<body leftmargin="2" topmargin="9" background='<%=path %>/images/allbg.gif'>
			<form action="<%=path %>/xuantiAdd.action" name="formAdd" method="post">
				     <table width="98%" align="center" border="0" cellpadding="4" cellspacing="1" bgcolor="#CBD8AC" style="margin-bottom:8px">
						<tr bgcolor="#EEF4EA">
					        <td colspan="3" background="<%=path %>/images/wbg.gif" class='title'><span>添加</span></td>
					    </tr>
						
										  
						
					   <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       学生ID：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
				 				      				      <input type="text" id="sid" name="xuanti.sid"   maxlength="11"   />
					  					    
					      </td>
						</tr>
				 				  
						
					   <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       姓名：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
				 				      				      <input type="text" id="sname" name="xuanti.sname"   maxlength="50"   />
					  					    
					      </td>
						</tr>
				 				  
						
					   <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       课题ID：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
				 				      				      <input type="text" id="ktid" name="xuanti.ktid"   maxlength="11"   />
					  					    
					      </td>
						</tr>
				 				  
						
					   <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       课题名称：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
				 				      				      <input type="text" id="ktname" name="xuanti.ktname"   maxlength="150"   />
					  					    
					      </td>
						</tr>
				 				  
						
					   <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       老师ID：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
				 				      				      <input type="text" id="tid" name="xuanti.tid"   maxlength="11"   />
					  					    
					      </td>
						</tr>
				 				  
						
					   <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       老师姓名：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
				 				      				      <input type="text" id="tname" name="xuanti.tname"   maxlength="50"   />
					  					    
					      </td>
						</tr>
				 				  						  
							
					 
						<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						        &nbsp;
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">
						       <input type="button" value="提交" onclick="check()"/>&nbsp; 
						       <input type="reset" value="重置"/>&nbsp;
						    </td>
						</tr>
					 </table>
			</form>
   </body>
</html>