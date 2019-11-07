# 学生课程成绩管理系统——中间件实现技术课程项目报告
学号：2018104167 姓名：曲芳

## 项目内容
        开发一个简易的学生课程成绩管理系统，能够查看学生对应的课程成绩，实现增删改查功能
	使用课上讲过的Servlet,jsp等方法，实现数据交互
## 实验平台
        Java Web项目使用MyEclipse 10 + mysql 5
        使用Servlet的doPost()方法实现数据库的增删改查过程
        使用jsp展示管理系统的页面
## 源代码分析
        项目名为Student，简化了MVC的开发框架，在utils包中定义了数据库连接方法，在entity包中定义了课程记录的属性，controller中的Servlet执行对数据库的增删改查以及与jsp的数据传输
![代码框架](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/%E4%BB%A3%E7%A0%81myeclipse.jpg)
#### DBConnection.java 数据库连接
```
try{
	Class.forName("com.mysql.jdbc.Driver");
	this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?characterEncoding=gbk", "root", "root");
}catch(Exception e){
	throw e;
}
```
        本项目使用低版本的mysql驱动 com.mysql.jdbc.Driver
        数据来自于mysql的student库中的lesson表，用户名和密码都是root，注意增加gbk的编码格式
#### LessonRecord.java 课程记录
```
public class LessonRecord {
	private String id;
	private String name;
	private String less_name;
	private String grade;
	
	public String getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public String getLess_name(){
		return less_name;
	}
	public String getGrade(){
		return grade;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setLess_name(String less_name){
		this.less_name = less_name;
	}
	public void setGrade(String grade){
		this.grade = grade;
	}
}
```
- 定义了ID，学生姓名，课程名，课程成绩这4个属性，以及对应的set()和get()方法，对应数据库中的4列：
![数据库列名](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%97%E5%90%8D.jpg)
#### login.jsp 登录页面
```
<form name="form0" action="/Students/loginServlet" method="post">
	账号：<input type="text" name="user" style="width: 106px; "><br>
    	密码：<input type="password" name="passwd" style="width: 106px; "/><br>
    		 <input type="submit" value="登录并查看课程成绩" style="width: 140px; color: Black"/>
</form>		
```
- 使用表单，参数action定义了提交表单后的跳转地址，将user和passwd的值传递到loginServlet中
#### loginServlet.java 核对用户名和密码，查找并返回全部记录
```
String usr = request.getParameter("user");
String pw = request.getParameter("passwd");
if(usr.equals("123") && pw.equals("123")){
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?characterEncoding=gbk", "root", "root");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from lesson");

		List<LessonRecord> LessonList = new ArrayList<LessonRecord>();
		while(rs.next()){
			String id = rs.getString(1);
			String name = rs.getString(2);
			String less_name = rs.getString(3);
			String grade = rs.getString(4);

			LessonRecord lr = new LessonRecord();
			lr.setId(id);
			lr.setName(name);
			lr.setLess_name(less_name);
			lr.setGrade(grade);
			LessonList.add(lr);
		}
		conn.close();

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		request.setAttribute("LessonList", LessonList);
		request.getRequestDispatcher("/lessons.jsp").forward(request,response);

	} catch (Exception e) {
		e.printStackTrace();
	}
}else{
	System.out.println("Wrong User !");
	request.getRequestDispatcher("/login.jsp").forward(request,response);
}
```
- 接收login.jsp传来的user和passwd，当用户名和密码都为123（这里只是简单的字符串匹配）时，连接数据库，执行select * from lesson查询操作
- 将查询结果以list的形式返回给lessons.jsp
- 否则跳转回login.jsp页面，重新输入用户名和密码
#### lessons.jsp 课程成绩展示与操作
```
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
```
- 以table的形式展示所有课程成绩记录和增删改查操作
![lesson.jsp页面](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/lesson0.jpg)
	
- 在table中接收loginServlet传来的LessonList参数，在for循环中获取id/name/less_name/grade并展示出来。
- 在table下方，增加了ADD/MODIFY/DELETE/SEARCH增删改查4个表单，对应了addServlet/modifyServlet/deleteServlet/searchServlet操作。
#### addServlet/modifyServlet/deleteServlet/searchServlet.java 增删改查
	接收参数，转换成sql
		add: insert into lesson values(?,?,?,?)
		modify: update lesson set grade=? where id=?
		delete: delete from lesson where id=?
		search: select * from lesson where lesson=?
	连接数据库
	执行sql
	返回结果给lessons.jsp
##### addServlet.java
```
int id = Integer.parseInt(request.getParameter("id"));
String name = request.getParameter("name");
String less_name = request.getParameter("less_name");
float grade = Float.parseFloat(request.getParameter("grade"));
String sql = "insert into lesson values("+id+",'"+name+"','"+less_name+"',"+grade+")";
```
##### modifyServlet.java
```
int id = Integer.parseInt(request.getParameter("id"));
float grade = Float.parseFloat(request.getParameter("grade"));
String sql = "update lesson set grade = " + grade + " where id = " + id;
```
##### deleteServlet.java
```
int id = Integer.parseInt(request.getParameter("id"));
String sql = "delete from lesson where id = " + id;
```
##### searchServlet.java
```
String less_name = request.getParameter("less_name");
String sql = "select * from lesson where less_name = '" + less_name + "'";
```
##### web.xml 记录了各Servlet的路径
```
<servlet-mapping>
    <servlet-name>loginServlet</servlet-name>
    <url-pattern>/loginServlet</url-pattern>
  </servlet-mapping>
```
## 实验结果
#### 原有的数据库记录
![数据库](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/%E6%95%B0%E6%8D%AE%E5%BA%93.jpg)
- 在浏览器中登录 **http://qufang-pc:8080/Students/login.jsp** 用户名和密码为*123*<br>
![登录](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/%E7%99%BB%E5%BD%95.jpg)
#### 课程成绩记录与操作页面
![lessons.jsp](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/lesson0.jpg)
#### 增加
![add0](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/add0.jpg)
- 结果<br>
![add1](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/add1.jpg)
#### 修改
![modify0](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/modify0.jpg)
- 结果<br>
![modify1](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/modify1.jpg)
#### 删除
![delete0](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/delete0.jpg)
- 结果<br>
![delete1](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/delete1.jpg)
#### 查询
![search0](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/search0.jpg)
- 结果<br>
![search1](https://github.com/ElsaQf/middleware-19/blob/master/2018104167/pic/search1.jpg)
- Trick：在查找时，若LESSON中不输入任何值，即不指定LESSON，可以查找全部记录
