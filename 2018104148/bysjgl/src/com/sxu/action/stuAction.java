package com.sxu.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sxu.dao.TStuDAO;
import com.sxu.model.TStu;
import com.sxu.model.TTea;

public class stuAction extends ActionSupport
{
	private int stuId;
	private String stuXuehao;
	private String stuRealname;
	private String stuSex;
	private String stuAge;
	private String loginPw;
	private String zhuanye;
	private String nianji;
	private String telphone;
	private String message;
	private String path;
	private TStuDAO stuDAO;
	
	
	public String stuAdd()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		TTea tea = (TTea)session.getAttribute("tea");
		TStu stu=new TStu();
		stu.setStuXuehao(stuXuehao);
		stu.setStuRealname(stuRealname);
		stu.setStuSex(stuSex);
		stu.setStuAge(stuAge);
		stu.setLoginPw(loginPw);
		stu.setZhuanye(zhuanye);
		stu.setNianji(nianji);
		stu.setTelphone(telphone);
		stu.setZhuangtai("a");
		stu.setDel("no");
//		stu.setTeaid(tea.getTeaId());
		stuDAO.save(stu);
		
		this.setMessage("操作成功");
		this.setPath("stuMana.action");
		return "succeed";
	}
	
	
	
	public String stuMana()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		TTea tea = (TTea)session.getAttribute("tea");
		String sql = "from TStu where del='no'";
		Map request=(Map)ServletActionContext.getContext().get("request");
		Integer usertype = (Integer)session.getAttribute("userType");
		if(1 == usertype){
			sql = sql + " and teaid = " + tea.getTeaId();
		}
		List stuList=stuDAO.getHibernateTemplate().find(sql);
		request.put("stuList", stuList);
		return ActionSupport.SUCCESS;
	}
	
	
	public String stuDel()
	{
		TStu stu=stuDAO.findById(stuId);
		stu.setDel("yes");
		stuDAO.attachDirty(stu);
		this.setMessage("删除成功");
		this.setPath("stuMana.action");
		return "succeed";
	}
		
	
	public String stuEdit()
	{
		TStu stu=stuDAO.findById(stuId);
		
		stu.setStuXuehao(stuXuehao);
		stu.setStuRealname(stuRealname);
		stu.setStuSex(stuSex);
		stu.setStuAge(stuAge);
		stu.setZhuanye(zhuanye);
		stu.setNianji(nianji);
		stu.setTelphone(telphone);
		stu.setLoginPw(loginPw);
		stuDAO.attachDirty(stu);
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		int userType = session.getAttribute("userType")==null?0:Integer.parseInt(session.getAttribute("userType")+"");
		if(2==userType){
			session.setAttribute("stu", stu);
		}
	
		
		
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("msg", "修改成功");
		return "msg";
	}
	
	
	
	
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public String getPath()
	{
		return path;
	}
	public TStuDAO getStuDAO()
	{
		return stuDAO;
	}
	public void setStuDAO(TStuDAO stuDAO)
	{
		this.stuDAO = stuDAO;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
	public String getStuAge()
	{
		return stuAge;
	}
	
	public String getLoginPw()
	{
		return loginPw;
	}
	public void setLoginPw(String loginPw)
	{
		this.loginPw = loginPw;
	}
	public void setStuAge(String stuAge)
	{
		this.stuAge = stuAge;
	}
	public int getStuId()
	{
		return stuId;
	}
	public void setStuId(int stuId)
	{
		this.stuId = stuId;
	}
	public String getStuRealname()
	{
		return stuRealname;
	}
	public void setStuRealname(String stuRealname)
	{
		this.stuRealname = stuRealname;
	}
	public String getStuSex()
	{
		return stuSex;
	}
	public void setStuSex(String stuSex)
	{
		this.stuSex = stuSex;
	}
	public String getStuXuehao()
	{
		return stuXuehao;
	}
	public void setStuXuehao(String stuXuehao)
	{
		this.stuXuehao = stuXuehao;
	}



	public String getZhuanye() {
		return zhuanye;
	}



	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}



	public String getNianji() {
		return nianji;
	}



	public void setNianji(String nianji) {
		this.nianji = nianji;
	}



	public String getTelphone() {
		return telphone;
	}



	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
}
