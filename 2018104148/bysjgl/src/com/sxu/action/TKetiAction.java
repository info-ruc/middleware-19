package com.sxu.action;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sxu.dao.TDocDAO;
import com.sxu.dao.TKetiDAO;
import com.sxu.model.TDoc;
import com.sxu.model.TKeti;
import com.sxu.model.TTea;
public class TKetiAction extends ActionSupport
{
	private int id;
	private String message;
	private String path;
 
	private TKetiDAO tketiDAO;
	private TDocDAO docDAO;
	private TKeti tKeti = new TKeti();	
	private String tname;
	private String kname;
	public String tketiMana()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		Integer usertype = (Integer)session.getAttribute("userType");
		
		
		String sql="from TKeti where 1=1 ";
		if(1==usertype){
			TTea tea = (TTea)session.getAttribute("tea");
			sql = sql + " and teaId = " + tea.getTeaId();
		}
		List tKetiList=tketiDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("tKetiList", tKetiList);
		return ActionSupport.SUCCESS;
	}
	
	public String tketiDetail()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		TKeti tKeti=tketiDAO.findById(id);
		request.put("tKeti", tKeti);
		return ActionSupport.SUCCESS;
	}
	
	public String tketiAdd()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		Integer usertype = (Integer)session.getAttribute("userType");
		if(1==usertype){
			TTea tea = (TTea)session.getAttribute("tea");
			tKeti.setTeaId(tea.getTeaId());
			tKeti.setTeaname(tea.getTeaRealname());
		}
		
		tketiDAO.save(tKeti);
		this.setMessage("操作成功");
		this.setPath("tketiMana.action");
		return "succeed";
		
	}
	
	public String tketiDel()
	{
		TKeti tKeti=tketiDAO.findById(id);
		tketiDAO.delete(tKeti);
		this.setMessage("操作成功");
		this.setPath("tketiMana.action");
		return "succeed";
	}
	
	public String totketiEdit()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		TKeti tKeti=tketiDAO.findById(id);
		request.put("tKeti", tKeti);
		return ActionSupport.SUCCESS;
	}
	
	public String tketiEdit()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		Integer usertype = (Integer)session.getAttribute("userType");
		if(1==usertype){
			TTea tea = (TTea)session.getAttribute("tea");
			tKeti.setTeaId(tea.getTeaId());
			tKeti.setTeaname(tea.getTeaRealname());
		}
		tketiDAO.attachDirty(tKeti);
		this.setMessage("操作成功");
		this.setPath("tketiMana.action");
		return "succeed";
	}
	
	
	public String tketiAll()
	{
		
		String flag = "N";
		try {
			TDoc doc = docDAO.findById(1);
			String sdate = doc.getSdate() + " 00:00:00";
			String edate = doc.getEdate() + " 23:59:59";
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = format.parse(sdate);
			Date d2 = format.parse(edate);
			
			Date date = new Date();
			
			if(date.after(d1) && date.before(d2)){
				flag = "Y";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		Integer usertype = (Integer)session.getAttribute("userType");
		
		
		String sql="from TKeti where 1=1 ";
		
		if(tname!=null && !"".equals("tname")){
			sql = sql + " and teaname like '%"+tname+"%'";
		}
		if(kname!=null && !"".equals("kname")){
			sql = sql + " and name like '%"+kname+"%'";
		}
		List tKetiList=tketiDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("tKetiList", tKetiList);
		request.put("flag", flag);
		request.put("kname", kname);
		request.put("tname", tname);
		return ActionSupport.SUCCESS;
	}
	
	
	 
	
	public String tketiDetailQian()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		TKeti tKeti=tketiDAO.findById(id);
		request.put("tKeti", tKeti);
		return ActionSupport.SUCCESS;
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
   
    public void setTketiDAO(TKetiDAO tketiDAO) {
		this.tketiDAO = tketiDAO;
	}

	public TKeti getTKeti() {
		return tKeti;
	}
	public void setTKeti(TKeti tKeti) {
		this.tKeti = tKeti;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public TDocDAO getDocDAO() {
		return docDAO;
	}

	public void setDocDAO(TDocDAO docDAO) {
		this.docDAO = docDAO;
	}

	public TKetiDAO getTketiDAO() {
		return tketiDAO;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getKname() {
		return kname;
	}

	public void setKname(String kname) {
		this.kname = kname;
	}
	
	
}
