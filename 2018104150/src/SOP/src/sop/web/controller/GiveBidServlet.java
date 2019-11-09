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

import sop.dao.domain.Bid;
import sop.dao.domain.Programmer;
import sop.service.TeamService;
import sop.service.impl.TeamServiceImpl;

/**
 * Servlet implementation class GiveBidServlet
 */
@WebServlet("/GiveBidServlet")
public class GiveBidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GiveBidServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Programmer p=(Programmer)request.getSession().getAttribute("user");
		String teamId=request.getParameter("tid");
		String proId=request.getParameter("proId");
		String programmerId=p.getPid();
		
		Bid b=new Bid();
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		
		b.setBidId(programmerId+time);
		b.setBudget(request.getParameter("budget"));
		b.setComments(request.getParameter("comments"));
		b.setConfirmed("0");
		TeamService ts=new TeamServiceImpl();
		ts.applyForBid(proId, teamId, b);
		
		
		request.setAttribute("message", "¾º±ê³É¹¦");
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
