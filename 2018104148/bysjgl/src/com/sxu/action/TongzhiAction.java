package com.sxu.action;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sxu.dao.TongzhiDAO;
import com.sxu.dao.XuantiDAO;
import com.sxu.model.TStu;
import com.sxu.model.TTea;
import com.sxu.model.Tongzhi;
import com.sxu.model.Xuanti;
public class TongzhiAction extends ActionSupport
{
	private Long id;
	private String message;
	private String path;
	private XuantiDAO xuantiDAO;
	private TongzhiDAO tongzhiDAO;
	
	private Tongzhi tongzhi = new Tongzhi();	
	
	public String tongzhiMana()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		Integer usertype = (Integer)session.getAttribute("userType");
		String sql="from Tongzhi where 1=1 ";
		if(1==usertype){
			TTea tea = (TTea)session.getAttribute("tea");
			sql = sql + " and uid = " + tea.getTeaId();
		}
		
		List tongzhiList=tongzhiDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("tongzhiList", tongzhiList);
		return ActionSupport.SUCCESS;
	}
	
	
	
	public String tongzhiAll()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
  
		TStu stu=(TStu)session.getAttribute("stu");
		String sql="from Xuanti where sid =  " + stu.getStuId();
		List<Xuanti> xuantiList=xuantiDAO.getHibernateTemplate().find(sql);
		
		if(xuantiList!=null && xuantiList.size()>0){
			sql="from Tongzhi where 1=1 ";
			sql = sql + " and uid = " + xuantiList.get(0).getTid();
			List tongzhiList=tongzhiDAO.getHibernateTemplate().find(sql);
			Map request=(Map)ServletActionContext.getContext().get("request");
			request.put("tongzhiList", tongzhiList);
		}
		
		
		return ActionSupport.SUCCESS;
	}
	
	
	public String tongzhiDetail()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		Tongzhi tongzhi=tongzhiDAO.findById(id);
		request.put("tongzhi", tongzhi);
		return ActionSupport.SUCCESS;
	}
	
	public String tongzhiAdd()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		Integer usertype = (Integer)session.getAttribute("userType");
		if(1==usertype){
			TTea tea = (TTea)session.getAttribute("tea");
			tongzhi.setUid(tea.getTeaId());
			tongzhi.setUname(tea.getTeaRealname());
		}
		tongzhiDAO.save(tongzhi);
		this.setMessage("操作成功");
		this.setPath("tongzhiMana.action");
		return "succeed";
		
	}
	
	public String tongzhiDel()
	{
		Tongzhi tongzhi=tongzhiDAO.findById(id);
		tongzhiDAO.delete(tongzhi);
		this.setMessage("操作成功");
		this.setPath("tongzhiMana.action");
		return "succeed";
	}
	
	public String totongzhiEdit()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		Tongzhi tongzhi=tongzhiDAO.findById(id);
		request.put("tongzhi", tongzhi);
		return ActionSupport.SUCCESS;
	}
	
	public String tongzhiEdit()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		tongzhiDAO.attachDirty(tongzhi);
		this.setMessage("操作成功");
		this.setPath("tongzhiMana.action");
		return "succeed";
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
	public void setTongzhiDAO(TongzhiDAO tongzhiDAO) {
		this.tongzhiDAO = tongzhiDAO;
	}
    public Tongzhi getTongzhi() {
		return tongzhi;
	}
	public void setTongzhi(Tongzhi tongzhi) {
		this.tongzhi = tongzhi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public XuantiDAO getXuantiDAO() {
		return xuantiDAO;
	}



	public void setXuantiDAO(XuantiDAO xuantiDAO) {
		this.xuantiDAO = xuantiDAO;
	}



	public TongzhiDAO getTongzhiDAO() {
		return tongzhiDAO;
	}
    
}
