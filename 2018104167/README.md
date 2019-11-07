# 学生课程成绩管理系统——中间件实现技术课程项目报告
学号：2018104167 姓名：曲芳

## 项目内容
        开发一个简易的学生课程成绩管理系统，能够查看学生对应的课程成绩，实现增删改查功能
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

