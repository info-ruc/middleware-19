package sop.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sop.dao.domain.Programmer;
import sop.service.ProgrammerService;
import sop.service.impl.ProgrammerServiceImpl;

/**
 * Servlet implementation class RegisterProgrammerServlet
 */
@WebServlet("/RegisterProgrammerServlet")
public class RegisterProgrammerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterProgrammerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Programmer p=new Programmer();
		p.setPid(request.getParameter("id"));
		p.setPassword(request.getParameter("password"));
		p.setMail(request.getParameter("mail"));
		p.setRealName(request.getParameter("Name"));
		p.setIdNum(request.getParameter("IdNum"));
		p.setOverview(request.getParameter("Overview"));
		p.setGraduation(request.getParameter("Graduation"));
		p.setEducationDegree(request.getParameter("EducationDegree"));
		p.setCertificate(request.getParameter("Certificate"));
		p.setExperience(request.getParameter("Experience"));
		p.setType("0");
		ProgrammerService ps=new ProgrammerServiceImpl();
		ps.registerProgammer(p);
		request.setAttribute("message", "×¢²á³É¹¦");
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
