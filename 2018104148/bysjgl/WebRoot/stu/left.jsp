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
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">个人信息</div>
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
					            <td><a href="<%=path %>/stu/userinfo/userPw.jsp" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;修改密码</a>
					            </td>
					          </tr>
					          <tr>
					            <td><a href="<%=path %>/stu/userinfo/userinfo.jsp" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;修改个人信息</a>
					            </td>
					          </tr>
					           
					        </table>
					    </td>
				      </tr>
				      
			 
				        
				      
				      
				      
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
					            <td><a href="<%=path %>/tketiAll.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;选题管理</a>
					            </td>
					          </tr>
					          
					          <tr>
					            <td><a href="<%=path %>/xuantiMyList.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;我的选题</a>
					            </td>
					          </tr>
					        
					        </table>
					    </td>
				      </tr>
				       <!-- caidan -->
				      
				        <!-- caidan -->
				      <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td6" onClick="show(6)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">论文上交</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show6" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					            <tr>
					            <td><a href="<%=path %>/shitiAll.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;论文上交</a>
					            </td>
					          </tr>
					          <tr>
					            <td><a href="<%=path %>/shejiList.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;论文成绩查看</a>
					            </td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <!-- caidan -->
				      
				      
				        <!-- caidan -->
				      <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td61" onClick="show(61)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">答疑时间地点</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show61" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					            <tr>
					            <td><a href="<%=path %>/tongzhiAll.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;答疑时间地点</a>
					            </td>
					          </tr>
					          
					        </table>
					    </td>
				      </tr>
				      <!-- caidan -->
				     
				      <!-- 通知公告 -->
				       <tr>
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td7" onClick="show(7)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">通知公告</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show7" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					            <tr>
					            <td><a href="<%=path %>/gonggaoAll.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;通知公告</a>
					            </td>
					          </tr>
					          
					        </table>
					    </td>
				      </tr>
				      
				      
				      
				      
				      <!-- caidan -->
				      <tr style="display: none">
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td6" onClick="show(6)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">33</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show6" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					          <tr>
					            <td><a href="<%=path %>/shetuanMana.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;66</a>
					            </td>
					          </tr>
					          <tr>
					            <td><a href="<%=path %>/admin/shetuan/shetuanAdd.jsp" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;66</a>
					            </td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <!-- caidan -->
				      
				      
				      
				      <!-- caidan -->
				      <tr style="display: none">
				        <td height="30" background="<%=path %>/images/main_25.jpg" id="td7" onClick="show(7)">
					        <table width="100%" border="0" cellspacing="0" cellpadding="0">
					          <tr>
					            <td width="13%">&nbsp;</td>
					            <td width="72%" height="20"><div align="center">
					              <table width="100%" height="21" border="0" cellpadding="0" cellspacing="0">
					                <tr>
					                  <td><div align="center"><img src="<%=path %>/images/top_8.gif" width="16" height="16"></div></td>
					                  <td valign="middle">
					                      <div align="center" class="STYLE1" style="font-family: 微软雅黑;">33</div>
					                  </td>
					                </tr>
					              </table>
					            </div></td>
					            <td width="15%">&nbsp;</td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      <tr id="show7" style="display:none">
				        <td align="center" valign="top">
					        <table border="0" align="center" cellpadding="5" cellspacing="5">
					          <tr>
					            <td><a href="<%=path %>/shetuanMana.action" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;77</a>
					            </td>
					          </tr>
					          <tr>
					            <td><a href="<%=path %>/admin/shetuan/shetuanAdd.jsp" target="I2" style="margin-left: 20px;">
					               <img src="<%=path %>/images/arr3.gif" border="0">&nbsp;&nbsp;77</a>
					            </td>
					          </tr>
					        </table>
					    </td>
				      </tr>
				      
				      <!-- meiyongde -->
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
     
  </body>
</html>

