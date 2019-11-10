<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 
    <script src="<%=path %>/js/menu.js"></script>

	<style type="text/css">
		body {
			margin-left: 0px;
			margin-top: 0px;
			margin-right: 0px;
			margin-bottom: 0px;
		}
		.STYLE1 {
			color: #FFFFFF;
			font-weight: bold;
			font-size: 12px;
		}
		.STYLE2 {
			font-size: 12px;
			color: #03515d;
		}
		a:link {font-size:12px; text-decoration:none; color:#03515d;}
		a:visited{font-size:12px; text-decoration:none; color:#03515d;}
		.STYLE3 {font-size: 12px}
	</style>
  </head>

  <body>
     <c:if test="${sessionScope.userType==0 }">
     <table width="156"  border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center" valign="top">
	            <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
				      <tr>
				        <td height="33" background="<%=path %>/images/main_21.gif">&nbsp;</td>
				      </tr>
				      <!-- caidan -->
				      <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td1" onClick="show(1)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">修改密码</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show1" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					          <tr>
					            <td><a href="<%=path %>/admin/userinfo/userPw.jsp" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;修改登录密码</a>
					            </td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <!-- caidan -->
				      
				       <!-- caidan -->
				      <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td2" onClick="show(21)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">账号管理</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show21" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					          <tr>
					            <td><a href="<%=path %>/adminManage.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;账号管理</a>
					            </td>
					          </tr>
					        
					        </table>
					    </td>
				      </tr>
				      <!-- caidan -->
				      
				      
				      <!-- caidan -->
				      <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td2" onClick="show(2)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">教师管理</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show2" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					          <tr>
					            <td><a href="<%=path %>/teaMana.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;教师管理</a>
					            </td>
					          </tr>
					        
					        </table>
					    </td>
				      </tr>
				      <!-- caidan -->
				      <!-- caidan -->
				      <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td3" onClick="show(3)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">学生管理</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show3" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					          <tr>
					            <td><a href="<%=path %>/stuMana.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;学生管理</a>
					            </td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <!-- caidan -->
				      
				      
				      
				      
				      <!-- caidan -->
				      
				       <!-- caidan -->
				      <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td12" onClick="show(12)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">课题管理</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show12" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					          <tr>
					            <td><a href="<%=path %>/tketiMana.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;课题管理</a>
					            </td>
					          </tr>
					          
					        </table>
					    </td>
				      </tr>
				      <!-- caidan -->
				      
				          <!-- caidan -->
				      <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td121" onClick="show(121)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">系统管理</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show121" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					          <tr>
					            <td><a href="<%=path %>/docDetailQian.action?id=1" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;选题开放时间设置</a>
					               &nbsp;
					               <a href="<%=path %>/gonggaoMana.action?id=1" target="I2" style="margin-left: 10px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;公告管理</a>
					            </td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				       
				      
				   
				      
				      
				      <tr>
				        <td height="10" background="<%=path %>/images/main_25.gif">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"></div></td>
					                  <td valign="middle">
					                      
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <!-- meiyongde -->
	            </table>
            </td>
        </tr>
     </table>
     </c:if>
     
     
     
     
     
     
     
     
     
     
     
     
     
     
  </body>
</html>

