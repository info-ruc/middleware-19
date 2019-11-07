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

public class deleteServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		String sql = "delete from lesson where id = " + id;
		System.out.println(sql);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?characterEncoding=gbk", "root", "root");
			System.out.println("yes");
			
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery("select * from lesson");
			
			List<LessonRecord> LessonList = new ArrayList<LessonRecord>();
			
			while(rs1.next()){
				String id1 = rs1.getString(1);
				String name1 = rs1.getString(2);
				String less_name1 = rs1.getString(3);
				String grade1 = rs1.getString(4);
				
				LessonRecord lr = new LessonRecord();
				lr.setId(id1);
				lr.setName(name1);
				lr.setLess_name(less_name1);
				lr.setGrade(grade1);
				
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
	}

	

}
