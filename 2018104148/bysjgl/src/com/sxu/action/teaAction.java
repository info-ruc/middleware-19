package com.sxu.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sxu.dao.TMessageDAO;
import com.sxu.dao.TTeaDAO;
import com.sxu.model.TMessage;
import com.sxu.model.TTea;

public class teaAction extends ActionSupport
{
	private int teaId;
	private String teaBianhao;
	private String teaRealname;
	private String teaSex;
	private String teaAge;
	private String loginName;
	private String loginPw;
	private String jszc;
	private String telphone;
	private int id;
	private String message;
	private String path;
	private TTeaDAO teaDAO;
	private TMessageDAO tmessageDAO;
	
	public String teaAdd()
	{
		TTea tea=new TTea();
		tea.setTeaBianhao(teaBianhao);
		tea.setLoginName(loginName);
		tea.setLoginPw(loginPw);
		tea.setTeaRealname(teaRealname);
		tea.setTeaSex(teaSex);
		tea.setTeaAge(teaAge);
		tea.setJszc(jszc);
		tea.setTelphone(telphone);
		tea.setDel("no");
		teaDAO.save(tea);
		this.setMessage("操作成功");
		this.setPath("teaMana.action");
		return "succeed";
	}
	
	public String teaMana()
	{
		String sql="from TTea where del='no'";
		List teaList=teaDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("teaList", teaList);
		return ActionSupport.SUCCESS;
	}
	
	public String teaDel()
	{
		TTea tea=teaDAO.findById(teaId);
		tea.setDel("yes");
		teaDAO.attachDirty(tea);
		this.setMessage("操作成功");
		this.setPath("teaMana.action");
		return "succeed";
	}
	
	public String teaMessage()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TMessage mm = new TMessage();
		mm.setTid(teaId);
		mm.setState(0);
		mm.setMdate(format.format(new Date()));
		mm.setMinfo("请及时答题");
		tmessageDAO.save(mm);
		this.setMessage("发送提醒成功");
		this.setPath("teaMana.action");
		return "succeed";
	}
	
	
	public String messageMana()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		TTea tea = (TTea)session.getAttribute("tea");
		String sql="from TMessage where state = 0 and tid = " + tea.getTeaId();
		List messageList=tmessageDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("messageList", messageList);
		return ActionSupport.SUCCESS;
	}
	
	public String stateUpdate()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		TTea tea = (TTea)session.getAttribute("tea");
		TMessage message=tmessageDAO.findById(id);
		message.setState(1);
		tmessageDAO.attachDirty(message);
		String sql="from TMessage where state = 0 and tid = " + tea.getTeaId();
		List messageList=tmessageDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("messageList", messageList);
		return ActionSupport.SUCCESS;
	}
	
	
	public String teaEdit()
	{
		TTea tea=teaDAO.findById(teaId);
		tea.setTeaBianhao(teaBianhao);
		tea.setLoginName(loginName);
		tea.setLoginPw(loginPw);
		tea.setTeaRealname(teaRealname);
		tea.setTeaSex(teaSex);
		tea.setTeaAge(teaAge);
		tea.setJszc(jszc);
		tea.setTelphone(telphone);
		tea.setDel("no");
		teaDAO.attachDirty(tea);
		Map request=(Map)ServletActionContext.getContext().get("request");
 		request.put("msg", "修改成功，重新登录后生效");
		return "msg";
	}
	
	
	public String getTeaBianhao()
	{
		return teaBianhao;
	}

	public void setTeaBianhao(String teaBianhao)
	{
		this.teaBianhao = teaBianhao;
	}

	public String getLoginName()
	{
		return loginName;
	}
	public void setLoginName(String loginName)
	{
		this.loginName = loginName;
	}
	public String getLoginPw()
	{
		return loginPw;
	}
	public void setLoginPw(String loginPw)
	{
		this.loginPw = loginPw;
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
	public void setPath(String path)
	{
		this.path = path;
	}
	public String getTeaAge()
	{
		return teaAge;
	}
	public void setTeaAge(String teaAge)
	{
		this.teaAge = teaAge;
	}
	public TTeaDAO getTeaDAO()
	{
		return teaDAO;
	}
	public void setTeaDAO(TTeaDAO teaDAO)
	{
		this.teaDAO = teaDAO;
	}
	public int getTeaId()
	{
		return teaId;
	}
	public void setTeaId(int teaId)
	{
		this.teaId = teaId;
	}
	public String getTeaRealname()
	{
		return teaRealname;
	}
	public void setTeaRealname(String teaRealname)
	{
		this.teaRealname = teaRealname;
	}
	public String getTeaSex()
	{
		return teaSex;
	}
	public void setTeaSex(String teaSex)
	{
		this.teaSex = teaSex;
	}

	public TMessageDAO getTmessageDAO() {
		return tmessageDAO;
	}

	public void setTmessageDAO(TMessageDAO tmessageDAO) {
		this.tmessageDAO = tmessageDAO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJszc() {
		return jszc;
	}

	public void setJszc(String jszc) {
		this.jszc = jszc;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	
	
}
