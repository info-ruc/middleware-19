<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<STYLE type=text/css>
		BODY{TEXT-ALIGN: center; PADDING-BOTTOM: 0px;  BACKGROUND-COLOR: #235789;  MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px}
		A:link {
			COLOR: #000000; TEXT-DECORATION: none
		}
		A:visited {
			COLOR: #000000; TEXT-DECORATION: none
		}
		A:hover {
			COLOR: #ff0000; TEXT-DECORATION: underline
		}
		A:active {
			TEXT-DECORATION: none
		}
		.input {
			BORDER-BOTTOM: #ccc 1px solid; BORDER-LEFT: #ccc 1px solid; LINE-HEIGHT: 20px; WIDTH: 182px; HEIGHT: 20px; BORDER-TOP: #ccc 1px solid; BORDER-RIGHT: #ccc 1px solid
		}
		.input1 {
			BORDER-BOTTOM: #ccc 1px solid; BORDER-LEFT: #ccc 1px solid; LINE-HEIGHT: 20px; WIDTH: 120px; HEIGHT: 20px; BORDER-TOP: #ccc 1px solid; BORDER-RIGHT: #ccc 1px solid
		}
		#dd{
			COLOR : #FFFFFF;
		}
		.ss{
			margin-top: 100px;
		}
		
	</STYLE>
	
	<script type='text/javascript' src='<%=path %>/dwr/interface/loginService.js'></script>
    <script type='text/javascript' src='<%=path %>/dwr/engine.js'></script>
    <script type='text/javascript' src='<%=path %>/dwr/util.js'></script>
       
	<script language="javascript">
		function check1()
		{                                                                                         
		     if(document.ThisForm.userName.value=="")
			 {
			 	alert("请输入用户名");
				document.ThisForm.userName.focus();
				return false;
			 }
			 if(document.ThisForm.userPw.value=="")
			 {
			 	alert("请输入密码");
				document.ThisForm.userPw.focus();
				return false;
			 }
			 if(document.ThisForm.userType.value=="-1")
			 {
			 	alert("请选择登陆身份");
				document.ThisForm.userType.focus();
				return false;
			 }
			 document.getElementById("indicator").style.display="block";
			 loginService.login(document.ThisForm.userName.value,document.ThisForm.userPw.value,document.ThisForm.userType.value,callback);
		}
		
		function callback(data)
		{
		    document.getElementById("indicator").style.display="none";
		    if(data=="no")
		    {
		        alert("用户名或密码错误");
		    }
		    if(data=="yes")
		    {
		        alert("通过验证,系统登录成功");
		        window.location.href="<%=path %>/loginSuccess.jsp";
		    }
		    
		}
    </script>
  </head>
  
  <BODY>
      <DIV id="dd" style="MARGIN: -90px auto; WIDTH: 936px;margin-top: 180px;font-family: 微软雅黑;font-size:30px;">
                         计算机与信息技术学院&nbsp;&nbsp;&nbsp; 毕业设计管理系统
      </DIV>
      <DIV style=" BACKGROUND-COLOR: #235789; margin-top: 0px;">
	      <DIV style="MARGIN: 0px auto; WIDTH: 800px">
		      <DIV  style="BACKGROUND: url(<%=path %>/images/body_06.jpg) no-repeat; HEIGHT: 384px ; WIDTH: 817px" >
			      <DIV style="TEXT-ALIGN: down; WIDTH: 360px; FLOAT: left; HEIGHT: 200px; _height: 95px;">
				      <form action="<%=path %>/admin/index.jsp" name="ThisForm" method="post">
				      <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%" style="margin-top:130px;margin-left:40px">
				          <TR>
					          <TD style="HEIGHT: 18px">
					              &nbsp;
					          </TD>
				          </TR>
				          <TR> 
					          <TD style="HEIGHT: 30px">
					              <span class="ss" style="font-family: 微软雅黑;font-size: 13px;"> 用&nbsp;&nbsp;户&nbsp;&nbsp;名：</span>
					              <INPUT type=text name=userName style="width: 130px;">
					          </TD>
				          </TR>
				          <TR>
					          <TD style="HEIGHT: 30px">
					              <span class="ss" style="font-family: 微软雅黑;font-size: 13px;"> 密 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</span>
					              <INPUT type=password name=userPw style="width: 130px;">
					          </TD>
				          </TR>
				          <TR>
					          <TD style="HEIGHT: 50px">
					              <span class="ss" style="font-family: 微软雅黑;font-size: 13px;"> 用户类型：</span>
					              <select class="INPUT_text" name="userType" style="width:130px;">
									<option value="0">管理员</option>
						            <option value="1">老师</option>	
						            <option value="2">学生</option>	
								  </select>
					            
					          </TD>
				          </TR>   
				           <TR>
					          <TD style="HEIGHT: 30px;"> 
					               <input type="button" value="登录" style="width: 60px;margin-left:60px" onclick='check1()'>
					               <input type="reset" value="重置" style="width: 60px;" >
					              <img id="indicator" src="<%=path %>/images/loading.gif" style="display:none"/>
					          </TD>
				          </TR>
					  </TABLE>
					  </form>
			      </DIV>
		      </DIV>
	      </DIV>
      </DIV>
      
      <div align="center" style="color:#fff">山西大学 &nbsp; 计算机与信息技术学院</div>
  </BODY>
</html>
