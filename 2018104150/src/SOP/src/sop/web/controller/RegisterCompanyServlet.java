package sop.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sop.dao.domain.Company;
import sop.service.CompanyService;
import sop.service.impl.CompanyServiceImpl;

/**
 * Servlet implementation class RegisterCompanyServlet
 */
@WebServlet("/RegisterCompanyServlet")
public class RegisterCompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterCompanyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Company c=new Company();
		c.setCid(request.getParameter("cid"));
		c.setPassword(request.getParameter("password"));
		c.setMail(request.getParameter("mail"));
		c.setCompanyName(request.getParameter("companyName"));
		c.setTelephoneNum(request.getParameter("telephoneNum"));
		c.setLocation(request.getParameter("location"));
		c.setType("1");
		CompanyService cs=new CompanyServiceImpl();
		cs.registerCompany(c);
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
