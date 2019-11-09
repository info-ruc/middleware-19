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

import sop.dao.domain.Company;
import sop.dao.domain.Project;
import sop.service.CompanyService;
import sop.service.impl.CompanyServiceImpl;

/**
 * Servlet implementation class IssueProjectServlet
 */
@WebServlet("/IssueProjectServlet")
public class IssueProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IssueProjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Company c=(Company)request.getSession().getAttribute("user");
		String companyId=c.getCid();
		Project pro=new Project();
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		
		pro.setProId(companyId+time);
		pro.setDescribtion(request.getParameter("Describtion"));
		pro.setHasTeam("0");
		pro.setMaxBudget(request.getParameter("Budget"));
		pro.setType(request.getParameter("Type"));
		pro.setProName(request.getParameter("name"));
		pro.setTerminated("0");
		CompanyService cs=new CompanyServiceImpl();
		cs.issueProject(pro, companyId);
		request.setAttribute("message", "发布成功");
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
