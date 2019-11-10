package com.sxu.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import com.sxu.dao.TAdminDAO;
import com.sxu.dao.TStuDAO;
import com.sxu.dao.TTeaDAO;
import com.sxu.model.TAdmin;
import com.sxu.model.TStu;
import com.sxu.model.TTea;

public class loginService
{
	private TAdminDAO adminDAO;
	private TStuDAO stuDAO;
	private TTeaDAO teaDAO;
	private Logger log = Logger.getLogger("console");
	
	public TAdminDAO getAdminDAO()
	{
		return adminDAO;
	}
	public void setAdminDAO(TAdminDAO adminDAO)
	{
		this.adminDAO = adminDAO;
	}
	public TStuDAO getStuDAO()
	{
		return stuDAO;
	}
	public void setStuDAO(TStuDAO stuDAO)
	{
		this.stuDAO = stuDAO;
	}
	public TTeaDAO getTeaDAO()
	{
		return teaDAO;
	}
	public void setTeaDAO(TTeaDAO teaDAO)
	{
		this.teaDAO = teaDAO;
	}
	
	
	public String login(String userName,String userPw,int userType)
	{
		log.info("userType"+userType);
		//System.out.println("userType"+userType);
		try
		{
			Thread.sleep(700);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result="no";
		
		if(userType==0)//系统管理员登陆
		{
			String sql="from TAdmin where userName=? and userPw=?";
			Object[] con={userName.trim(),userPw.trim()};
			List adminList=adminDAO.getHibernateTemplate().find(sql,con);
			if(adminList.size()==0)
			{
				 result="no";
			}
			else
			{
				 WebContext ctx = WebContextFactory.get(); 
				 HttpSession session=ctx.getSession(); 
				 TAdmin admin=(TAdmin)adminList.get(0);
				 session.setAttribute("userType", 0);
	             session.setAttribute("admin", admin);
	             result="yes";
			}
		}
		if(userType==1)//老师登陆
		{
			String sql="from TTea where loginName=? and loginPw=? and del='no'";
			Object[] con={userName.trim(),userPw.trim()};
			List teaList=teaDAO.getHibernateTemplate().find(sql,con);
			if(teaList.size()==0)
			{
				 result="no";
			}
			else
			{
				 WebContext ctx = WebContextFactory.get(); 
				 HttpSession session=ctx.getSession(); 
				 TTea tea=(TTea) teaList.get(0);
				 session.setAttribute("userType", 1);
	             session.setAttribute("tea", tea);
	             result="yes";
			}
		}
		if(userType==2)//学生登陆
		{
			String sql="from TStu where stuXuehao=? and loginPw=? and del='no' ";
			Object[] con={userName.trim(),userPw.trim()};
			List stuList=stuDAO.getHibernateTemplate().find(sql,con);
			if(stuList.size()==0)
			{
				 result="no";
			}
			else
			{
				 WebContext ctx = WebContextFactory.get(); 
				 HttpSession session=ctx.getSession(); 
				 TStu stu=(TStu) stuList.get(0);
				 session.setAttribute("userType", 2);
	             session.setAttribute("stu", stu);
	             result="yes";
			}
		}
		return result;
	}

    public String adminPwEdit(String userPwNew)
    {
		System.out.println("DDDD");
    	try 
		{
			Thread.sleep(700);
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebContext ctx = WebContextFactory.get(); 
		HttpSession session=ctx.getSession(); 
		 
		TAdmin admin=(TAdmin)session.getAttribute("admin");
		admin.setUserPw(userPwNew);
		
		adminDAO.getHibernateTemplate().update(admin);
		session.setAttribute("admin", admin);
		
		return "yes";
    }
    
    
    public String teaPwEdit(String userPwNew)
    {
		System.out.println("DDDD");
    	try 
		{
			Thread.sleep(700);
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebContext ctx = WebContextFactory.get(); 
		HttpSession session=ctx.getSession(); 
		 
		TTea tea=(TTea)session.getAttribute("tea");
		tea.setLoginPw(userPwNew);
		
		teaDAO.getHibernateTemplate().update(tea);
		session.setAttribute("tea", tea);
		
		return "yes";
    }
    
    
    public String stuPwEdit(String userPwNew)
    {
		System.out.println("DDDD");
    	try 
		{
			Thread.sleep(700);
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebContext ctx = WebContextFactory.get(); 
		HttpSession session=ctx.getSession(); 
		 
		TStu stu=(TStu)session.getAttribute("stu");
		stu.setLoginPw(userPwNew);
		
		stuDAO.getHibernateTemplate().update(stu);
		session.setAttribute("stu", stu);
		
		return "yes";
    }
    
    public void logout()
    {
    	WebContext ctx = WebContextFactory.get(); 
		HttpSession session=ctx.getSession(); 
		session.setAttribute("userType", null);
    }
}
