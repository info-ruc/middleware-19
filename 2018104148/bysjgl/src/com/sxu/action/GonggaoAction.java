package com.sxu.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sxu.dao.TGonggaoDAO;
import com.sxu.model.TGonggao;
import com.sxu.model.TStu;
import com.sxu.model.Xuanti;

public class GonggaoAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	private Integer ggId;
	private String ggTitle;
	private String ggContent;
	private String ggTime;
	private String message;
	private String path;
	private TGonggaoDAO gonggaoDAO;
	
	
	public String gonggaoAdd()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		TGonggao gonggao=new TGonggao();
		gonggao.setGgId(ggId);
		gonggao.setGgTitle(ggTitle);
		gonggao.setGgContent(ggContent);
		gonggao.setGgTime(Trans_Date());
		gonggao.setGgDel("no");
		gonggaoDAO.save(gonggao);
		
		this.setMessage("操作成功");
		this.setPath("gonggaoMana.action");
		return "succeed";
	}
	
	
	
	public String gonggaoMana()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		String sql = "from TGonggao where ggDel='no'";
		Map request=(Map)ServletActionContext.getContext().get("request");
		List gonggaoList=gonggaoDAO.getHibernateTemplate().find(sql);
		request.put("gonggaoList", gonggaoList);
		return ActionSupport.SUCCESS;
	}
	
	
	public String gonggaoDel()
	{
		TGonggao gonggao=gonggaoDAO.findById(ggId);
		gonggao.setGgDel("yes");
		gonggaoDAO.attachDirty(gonggao);
		this.setMessage("删除成功");
		this.setPath("gonggaoMana.action");
		return "succeed";
	}
		
	
	public String gonggaoEdit()
	{
		TGonggao gonggao=gonggaoDAO.findById(ggId);
		
		gonggao.setGgId(ggId);
		gonggao.setGgTitle(ggTitle);
		gonggao.setGgContent(ggContent);
		gonggao.setGgTime(ggTime);
		gonggaoDAO.attachDirty(gonggao);
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		int userType = session.getAttribute("userType")==null?0:Integer.parseInt(session.getAttribute("userType")+"");
		if(1==userType){
			session.setAttribute("gonggao", gonggao);
		}
	
		
		
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("msg", "修改成功");
		return "msg";
	}

	public String gonggaoAll()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
  
		TStu stu=(TStu)session.getAttribute("stu");
		
			String sql= "from TGonggao where ggDel='no'";
			List gonggaoList=gonggaoDAO.getHibernateTemplate().find(sql);
			Map request=(Map)ServletActionContext.getContext().get("request");
			request.put("gonggaoList", gonggaoList);
		
		
		return ActionSupport.SUCCESS;
	}

	public Integer getGgId() {
		return ggId;
	}



	public void setGgId(Integer ggId) {
		this.ggId = ggId;
	}



	public String getGgTitle() {
		return ggTitle;
	}



	public void setGgTitle(String ggTitle) {
		this.ggTitle = ggTitle;
	}



	public String getGgContent() {
		return ggContent;
	}



	public void setGgContent(String ggContent) {
		this.ggContent = ggContent;
	}



	public String getGgTime() {
		return ggTime;
	}



	public void setGgTime(String ggTime) {
		this.ggTime = ggTime;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public TGonggaoDAO getGonggaoDAO() {
		return gonggaoDAO;
	}



	public void setGonggaoDAO(TGonggaoDAO gonggaoDAO) {
		this.gonggaoDAO = gonggaoDAO;
	}
	
	//日期格式转换
	public String Trans_Date(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = sdf.format(date);
		return dateString;
	}
}
