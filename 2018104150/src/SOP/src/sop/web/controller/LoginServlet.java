package sop.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sop.dao.domain.Company;
import sop.dao.domain.Programmer;
import sop.service.CompanyService;
import sop.service.ProgrammerService;
import sop.service.impl.CompanyServiceImpl;
import sop.service.impl.ProgrammerServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String identity = request.getParameter("identity");
		if (identity.equals("0")) {
			ProgrammerService ps = new ProgrammerServiceImpl();
			Programmer user = ps.loginProgrammer(id, password);
			if (user != null) {
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("/IndexUIServlet").forward(request, response);
				return;
			}
			request.setAttribute("message", "用户名或密码错误");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
		else if (identity.equals("1")) {
			CompanyService cs = new CompanyServiceImpl();
			Company user = cs.loginCompany(id, password);
			if (user != null) {
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("/IndexUIServlet").forward(request, response);
				return;
			}
			request.setAttribute("message", "用户名或密码错误");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		} else {
			request.setAttribute("message", "未知的错误");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
