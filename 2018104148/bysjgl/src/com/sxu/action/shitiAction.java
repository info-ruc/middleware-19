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
import com.sxu.dao.TShitiDAO;
import com.sxu.dao.XuantiDAO;
import com.sxu.model.TDoc;
import com.sxu.model.TShiti;
import com.sxu.model.TStu;
import com.sxu.model.TTea;
import com.sxu.model.Xuanti;

public class shitiAction extends ActionSupport
{
	private int id;
	private String mingcheng;
	private String fujian;
	private String fujianYuanshiming;
	private String replay;
	private String message;
	private String path;
	
	private TShitiDAO shitiDAO;
	private XuantiDAO xuantiDAO;
	
	
	/**
     * 提交论文列表
     * @return
     */
	public String shitiAll()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		Integer usertype = (Integer)session.getAttribute("userType");
		String sql="from TShiti where del='no'";
		if(1==usertype){
			TTea tea = (TTea)session.getAttribute("tea");
			sql = sql + " and teaid = " + tea.getTeaId();
		}
		if(2==usertype){
			TStu stu = (TStu)session.getAttribute("stu");
			sql = sql + " and stuid = " + stu.getStuId();
		}
		List shitiList=shitiDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("shitiList", shitiList);
		return ActionSupport.SUCCESS;
	}
	
	
	public String shitiAdd()
	{
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		TShiti shiti=new TShiti();
		shiti.setMingcheng(mingcheng);
		shiti.setFujian(fujian);
		shiti.setFujianYuanshiming(fujianYuanshiming);
		shiti.setShijian(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		shiti.setDel("no");
		TStu stu = (TStu)session.getAttribute("stu");
		List<Xuanti> list = xuantiDAO.findByProperty("sid",stu.getStuId());
		if(list!=null && list.size()>0){
			Xuanti xuanti = list.get(0);
			shiti.setTeaid(xuanti.getTid());
		}
		Integer usertype = (Integer)session.getAttribute("userType");
		 
		if(2==usertype){
		 
			 
			shiti.setStuid(stu.getStuId());
			shiti.setStuname(stu.getStuRealname());
		}
		shiti.setState("未批复");
		shitiDAO.save(shiti);
		
	    
		
	    if(list!=null && list.size()>0){
	    	Xuanti xuanti = list.get(0);
	    	if("开题报告".equals(mingcheng)){
				xuanti.setKtbg("是");
			}
			if("论文".equals(mingcheng)){
				xuanti.setLunwen("是");
			}
			if("中期汇报".equals(mingcheng)){
				xuanti.setZqhb("是");
			}
			xuantiDAO.attachDirty(xuanti);
			
	    }
		
	    return shitiAll();
	}
	
	
	public String shitiEdit()
	{
		TShiti shiti=shitiDAO.findById(id);
		shiti.setState("已批复");
		shiti.setReplay(replay);
		shiti.setReplaydate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		shitiDAO.attachDirty(shiti);
		Map request=(Map)ServletActionContext.getContext().get("request");
		this.setMessage("批复成功");
		this.setPath("shitiMana.action");
		return "succeed";
	}
	
	public String shitiDel()
	{
		TShiti shiti=shitiDAO.findById(id);
		shiti.setDel("yes");
		shitiDAO.attachDirty(shiti);
		this.setMessage("删除成功");
		this.setPath("shitiMana.action");
		return "succeed";
	}
	
	public String shitiMana()
	{
		String sql="from TShiti where del='no'";
		
		HttpServletRequest request1=ServletActionContext.getRequest();
		HttpSession session=request1.getSession();
		Integer usertype = (Integer)session.getAttribute("userType");
		if(1==usertype){
			TTea tea = (TTea)session.getAttribute("tea");
			sql = sql + " and teaid = " + tea.getTeaId();
		}
		List shitiList=shitiDAO.getHibernateTemplate().find(sql);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("shitiList", shitiList);
		return ActionSupport.SUCCESS;
	}
	
	
    
	
	public String shitiDetailQian()
	{
		TShiti shiti=shitiDAO.findById(id);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("shiti", shiti);
		return ActionSupport.SUCCESS;
	}

	public String getFujian()
	{
		return fujian;
	}

	public void setFujian(String fujian)
	{
		this.fujian = fujian;
	}

	public String getFujianYuanshiming()
	{
		return fujianYuanshiming;
	}

	public TShitiDAO getshitiDAO()
	{
		return shitiDAO;
	}

	public void setshitiDAO(TShitiDAO shitiDAO)
	{
		this.shitiDAO = shitiDAO;
	}

	public void setFujianYuanshiming(String fujianYuanshiming)
	{
		this.fujianYuanshiming = fujianYuanshiming;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public TShitiDAO getShitiDAO()
	{
		return shitiDAO;
	}

	public void setShitiDAO(TShitiDAO shitiDAO)
	{
		this.shitiDAO = shitiDAO;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMingcheng()
	{
		return mingcheng;
	}

	public void setMingcheng(String mingcheng)
	{
		this.mingcheng = mingcheng;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}


	public String getReplay() {
		return replay;
	}


	public void setReplay(String replay) {
		this.replay = replay;
	}


	public void setXuantiDAO(XuantiDAO xuantiDAO) {
		this.xuantiDAO = xuantiDAO;
	}
	
	
}
