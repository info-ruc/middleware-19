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
			<form action="<%=path %>/tketiAdd.action" name="formAdd" method="post">
				     <table width="98%" align="center" border="0" cellpadding="4" cellspacing="1" bgcolor="#CBD8AC" style="margin-bottom:8px">
						<tr bgcolor="#EEF4EA">
					        <td colspan="3" background="<%=path %>/images/wbg.gif" class='title'><span>添加课题</span></td>
					    </tr>
						
										  
						
					   <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       课题名称：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
				 			  <input type="text" id="name" name="tKeti.name" style="width:350px;" maxlength="300"   />
					  					    
					      </td>
						</tr>
				 				  
						 <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       课题难度：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
						      <select name="tKeti.ktnd" id="ktnd">
						         <option value="容易">容易</option>
						         <option value="一般">一般</option>
						         <option value="中等">中等</option>
						         <option value="较难">较难</option>
						         <option value="很难">很难</option>
						      </select>
				 			 	    
					      </td>
						</tr>
					  
				 		<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       课题方向：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
						      <select  name="tKeti.ktfx" id="ktfx">
						         <option value="工程实践">工程实践</option>
						         <option value="研究综述">研究综述</option>
						         <option value="教师课堂">教师课堂</option>
						         <option value="理论研究">理论研究</option>
						         <option value="专业实践">专业实践</option>
						         <option value="调查报告">调查报告</option>
						         <option value="设计作品">设计作品</option>
						      </select>
				 			 	    
					      </td>
						</tr>	  
					 
					   
				 		<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='red';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						    <td width="25%" bgcolor="#FFFFFF" align="right">
						       课题要求：
						    </td>
						    <td width="75%" bgcolor="#FFFFFF" align="left">	
						    
						      <input type="hidden" id="xxrs" name="tKeti.xxrs" value="10000" maxlength="300"   />
						       <textarea rows="15" cols="80" name="tKeti.ktyq" id="ktyq"></textarea>
				 			 	    
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