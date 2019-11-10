package com.sxu.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sxu.dao.TDocDAO;
import com.sxu.model.TDoc;
import com.sxu.model.TTea;

public class docAction extends ActionSupport
{
	private int id;
	private String sdate;
	private String edate;
 
	private String path;
	
	private TDocDAO docDAO;
	
	 
	
 
	
	public String docDetailQian()
	{
		TDoc doc=docDAO.findById(id);
		Map request=(Map)ServletActionContext.getContext().get("request");
		request.put("doc", doc);
		return ActionSupport.SUCCESS;
	}
	
	public String docEdit()
	{
		TDoc doc=docDAO.findById(id);
		doc.setSdate(sdate);
		doc.setEdate(edate);
		docDAO.attachDirty(doc);
		HttpServletRequest request=ServletActionContext.getRequest();
		request.setAttribute("msg", "²Ù×÷³É¹¦");
		return "msg";
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getSdate() {
		return sdate;
	}



	public void setSdate(String sdate) {
		this.sdate = sdate;
	}



	public String getEdate() {
		return edate;
	}



	public void setEdate(String edate) {
		this.edate = edate;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public TDocDAO getDocDAO() {
		return docDAO;
	}



	public void setDocDAO(TDocDAO docDAO) {
		this.docDAO = docDAO;
	}
	

	 
	
}
