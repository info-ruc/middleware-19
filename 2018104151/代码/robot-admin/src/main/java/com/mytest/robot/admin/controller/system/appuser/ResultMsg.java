package com.zoneyet.robot.admin.controller.system.appuser;

import java.io.Serializable;

public class ResultMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private String data;
	private String dataId;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	
}
