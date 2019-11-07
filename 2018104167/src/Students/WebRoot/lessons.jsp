<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="entity.LessonRecord" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'lessons.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <table border="1" charset="UTF-8">
    	<tr>
    		<td>ID</td>
    		<td>NAME</td>
    		<td>LESSON</td>
    		<td>GRADE</td>
    	</tr>
    	
    	<%
    	List<LessonRecord> LR = (List<LessonRecord>)request.getAttribute("LessonList");
    	if(LR != null){
   			for(LessonRecord lr: LR){
    	 %>
    	 <tr>
    	 	<td><%=lr.getId() %></td>
    	 	<td><%=lr.getName() %></td>
    	 	<td><%=lr.getLess_name() %></td>
    	 	<td><%=lr.getGrade() %></td>
    	 </tr>
    	 <%
    	 }
    	 }
    	  %>
    	 
    	 
    	 
    	
    </table>
    
    <br>
    <br>
    
    <form name="form0" action="/Students/addServlet" method="post">
    	<input type="submit" value="ADD" style="width: 100px; color: Black"/>
		ID:<input type="text" name="id" style="width: 106px; "/>
		NAME:<input type="text" name="name" style="width: 106px; "/>
		LESSON:<input type="text" name="less_name" style="width: 106px; "/>
		Grade:<input type="text" name="grade" style="width: 106px; "/><br>
    </form>
    <form name="form1" action="/Students/modifyServlet" method="post">
    	<input type="submit" value="MODIFY" style="width: 100px; color: Black"/>
		ID:<input type="text" name="id" style="width: 106px; "/>
		Grade:<input type="text" name="grade" style="width: 106px; "/><br>
    </form>	
    <form name="form2" action="/Students/deleteServlet" method="post">
    	<input type="submit" value="DELETE" style="width: 100px; color: Black"/>
		ID:<input type="text" name="id" style="width: 106px; "/>
    </form>
    <form name="form3" action="/Students/searchServlet" method="post">
    	<input type="submit" value="SEARCH" style="width: 100px; color: Black"/>
		LESSON:<input type="text" name="less_name" style="width: 106px; "/>
    </form>			
    
    
    
  </body>
</html>
