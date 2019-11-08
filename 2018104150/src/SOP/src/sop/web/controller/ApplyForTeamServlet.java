package sop.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sop.service.ProgrammerService;
import sop.service.impl.ProgrammerServiceImpl;

/**
 * Servlet implementation class ApplyForTeamServlet
 */
@WebServlet("/ApplyForTeamServlet")
public class ApplyForTeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplyForTeamServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String programmerId=request.getParameter("pid");
		String teamId=request.getParameter("tid");
		ProgrammerService ps=new ProgrammerServiceImpl();
		ps.applyForTeam(programmerId, teamId);
		request.setAttribute("message", "…Í«Î∑¢ÀÕ≥…π¶");
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
