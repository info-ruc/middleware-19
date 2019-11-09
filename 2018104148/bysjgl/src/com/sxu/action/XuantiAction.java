package com.sxu.action;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sxu.dao.TKetiDAO;
import com.sxu.dao.XuantiDAO;
import com.sxu.model.TKeti;
import com.sxu.model.TStu;
import com.sxu.model.TTea;
import com.sxu.model.Xuanti;
public class XuantiAction extends ActionSupport
{
	private Long id;
	private String message;
	private String path;
 
	private XuantiDAO xuantiDAO;
	private TKetiDAO TKetiDAO;
	private Xuanti xuanti = new Xuanti();	
	private String fenshu;
	private String jieguo;
	public String xuantiMana()
	{
		String sql="from Xuanti ";
		List xuantiList=xuantiDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("xuantiList", xuantiList);
		return ActionSupport.SUCCESS;
	}
	
	public String xuantiDetail()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		Xuanti xuanti=xuantiDAO.findById(id);
		request.put("xuanti", xuanti);
		return ActionSupport.SUCCESS;
	}
	
	public String xuantiAdd()
	{
		xuantiDAO.save(xuanti);
		this.setMessage("操作成功");
		this.setPath("xuantiMana.action");
		return "succeed";
		
	}
	
	
	
	
	
	public String shejiDel()
	{
		Xuanti xuanti=xuantiDAO.findById(id);
		xuantiDAO.delete(xuanti);
		
		TKeti keti = TKetiDAO.findById(xuanti.getKtid());
		if(keti.getYxrs()!=null && keti.getYxrs()!=0){
			keti.setYxrs(keti.getYxrs()-1);
		}
		
		return shejiAll();
	}
	
	
	public String xuantiDel()
	{
		Xuanti xuanti=xuantiDAO.findById(id);
		xuantiDAO.delete(xuanti);
		
		TKeti keti = TKetiDAO.findById(xuanti.getKtid());
		if(keti.getYxrs()!=null && keti.getYxrs()!=0){
			keti.setYxrs(keti.getYxrs()-1);
		}
		TKetiDAO.attachDirty(keti);
		
		this.setMessage("操作成功");
		this.setPath("xuantiTeaList.action");
		return "succeed";
	}
	
	
	/**
	 * 取消选题
	 * @return
	 */
	public String xuantiCancel()
	{
		Xuanti xuanti=xuantiDAO.findById(id);
		xuantiDAO.delete(xuanti);
		
		TKeti keti = TKetiDAO.findById(xuanti.getKtid());
		if(keti.getYxrs()!=null && keti.getYxrs()!=0){
			keti.setYxrs(keti.getYxrs()-1);
		}
		TKetiDAO.attachDirty(keti);
		
		this.setMessage("操作成功");
		this.setPath("xuantiMyList.action");
		return "succeed";
	}
	
	
	public String toxuantiEdit()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		Xuanti xuanti=xuantiDAO.findById(id);
		request.put("xuanti", xuanti);
		return ActionSupport.SUCCESS;
	}
	
	public String xuantiEdit()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		xuantiDAO.attachDirty(xuanti);
		this.setMessage("操作成功");
		this.setPath("xuantiMana.action");
		return "succeed";
	}
	
	
	
	public String xuantiAll()
	{
		String sql="from Xuanti";
		List xuantiList=xuantiDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("xuantiList", xuantiList);
		return ActionSupport.SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	public String xuantiSave()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		TStu stu=(TStu)session.getAttribute("stu");
		xuanti.setShzt("未确认");
		xuanti.setSid(stu.getStuId());
		xuanti.setSname(stu.getStuRealname());
		xuantiDAO.save(xuanti);
		
		
//		TKeti keti = TKetiDAO.findById(xuanti.getKtid());
//		keti.setYxrs(keti.getYxrs()+1);
//		TKetiDAO.attachDirty(keti);
		
		this.setMessage("操作成功");
		this.setPath("tketiAll.action");
		return "succeed";
	}
	
	

	public String xuantiMyList()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		TStu stu=(TStu)session.getAttribute("stu");
		String sql="from Xuanti where sid =  " + stu.getStuId();
		List xuantiList=xuantiDAO.getHibernateTemplate().find(sql);
		request.setAttribute("xuantiList", xuantiList);
		return ActionSupport.SUCCESS;
	}
	
	public String shejiList()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		TStu stu=(TStu)session.getAttribute("stu");
		int userType = session.getAttribute("userType")==null?0:Integer.parseInt(session.getAttribute("userType")+"");
		String sql="from Xuanti where 1=1 ";
		if(2==userType){
			sql = sql + " and sid = " + stu.getStuId();
		}
		List xuantiList=xuantiDAO.getHibernateTemplate().find(sql);
		request.setAttribute("xuantiList", xuantiList);
		return ActionSupport.SUCCESS;
	}
	 
	
	
	
	public String xuantiTeaList()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		int userType = session.getAttribute("userType")==null?0:Integer.parseInt(session.getAttribute("userType")+"");
		String sql="from Xuanti where 1=1 ";
		if(1==userType){
			TTea tea=(TTea)session.getAttribute("tea");
			sql = sql + " and tid = " + tea.getTeaId();
		}
		List xuantiList=xuantiDAO.getHibernateTemplate().find(sql);
		request.setAttribute("xuantiList", xuantiList);
		return ActionSupport.SUCCESS;
	}
	
	
	public String xuantiQueren()
	{
		xuanti = xuantiDAO.findById(id);
		xuanti.setShzt("已确认");
		xuantiDAO.attachDirty(xuanti);
		this.setMessage("操作成功");
		this.setPath("xuantiTeaList.action");
		return "succeed";
	}
	
	public String xuantiQuxiao()
	{
		xuanti = xuantiDAO.findById(id);
		xuanti.setShzt("未确认");
		xuantiDAO.attachDirty(xuanti);
		this.setMessage("操作成功");
		this.setPath("xuantiTeaList.action");
		return "succeed";
	}
	
	
	
	
	public String toxuantiView()
	{
		Map request=(Map)ServletActionContext.getContext().get("request");
		Xuanti xuanti=xuantiDAO.findById(id);
		request.put("xuanti", xuanti);
		return ActionSupport.SUCCESS;
	}
	
	public String shejiAll()
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session=request.getSession();
		
		
		int userType = session.getAttribute("userType")==null?0:Integer.parseInt(session.getAttribute("userType")+"");
		String sql="from Xuanti where shzt='已确认' ";
		if(1==userType){
			TTea tea=(TTea)session.getAttribute("tea");
			sql = sql + " and tid = " + tea.getTeaId();
		}
		sql = sql + " order by fenshu desc ";
		List xuantiList=xuantiDAO.getHibernateTemplate().find(sql);
		request.setAttribute("xuantiList", xuantiList);
		return ActionSupport.SUCCESS;
	}
	
	
	public String xuantiDafen()
	{
		xuanti = xuantiDAO.findById(id);
		xuanti.setFenshu(fenshu);
		xuanti.setJieguo(jieguo);
		xuantiDAO.attachDirty(xuanti);
		this.setMessage("操作成功");
		this.setPath("shejiAll.action");
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
	public void setXuantiDAO(XuantiDAO xuantiDAO) {
		this.xuantiDAO = xuantiDAO;
	}
    public Xuanti getXuanti() {
		return xuanti;
	}
	public void setXuanti(Xuanti xuanti) {
		this.xuanti = xuanti;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFenshu() {
		return fenshu;
	}

	public void setFenshu(String fenshu) {
		this.fenshu = fenshu;
	}

	public String getJieguo() {
		return jieguo;
	}

	public void setJieguo(String jieguo) {
		this.jieguo = jieguo;
	}

	public void setTKetiDAO(TKetiDAO ketiDAO) {
		TKetiDAO = ketiDAO;
	}
    
}
