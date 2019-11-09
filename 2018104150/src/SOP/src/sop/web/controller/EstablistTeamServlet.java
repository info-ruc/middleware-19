package sop.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sop.dao.domain.Programmer;
import sop.dao.domain.Team;
import sop.service.ProgrammerService;
import sop.service.impl.ProgrammerServiceImpl;

/**
 * Servlet implementation class EstablistTeamServlet
 */
@WebServlet("/EstablistTeamServlet")
public class EstablistTeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EstablistTeamServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		Programmer p=(Programmer)request.getSession().getAttribute("user");
		String programmerId=p.getPid();
		Team t=new Team();
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		
		t.setTeamId(programmerId+time);
		t.setLeaderId(programmerId);
		t.setTeamName(request.getParameter("teamName"));
		t.setProfile(request.getParameter("profile"));
		ProgrammerService ps= new ProgrammerServiceImpl();
		ps.establishTeam(t);
		request.setAttribute("message", "创建团队成功");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
