package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.LessonRecord;

import utils.DBConnection;

public class loginServlet extends HttpServlet {

		public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			
			doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		String usr = request.getParameter("user");
		String pw = request.getParameter("passwd");
		
		
		System.out.println("user: " + usr);
		System.out.println("passwd: " + pw);
		
		if(usr.equals("123") && pw.equals("123")){
			try {
				
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?characterEncoding=gbk", "root", "root");
				System.out.println("yes");
				
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
					
					System.out.println("ok");
				}
				
				conn.close();
				
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				
				request.setAttribute("LessonList", LessonList);
				
		        request.getRequestDispatcher("/lessons.jsp").forward(request,response);
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("Wrong User !");
			request.getRequestDispatcher("/login.jsp").forward(request,response);
		}
		
		
		
		
		
//		if(usr == "123" && pw == "123"){
//			try {
//				Class.forName("com.mysql.jdbc.Driver");
//				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?characterEncoding=gbk", "root", "root");
//				System.out.println("yes");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}else{
//			System.out.println("Wrong User !");
//		}
		
		
		
		
		
	}

	

}
