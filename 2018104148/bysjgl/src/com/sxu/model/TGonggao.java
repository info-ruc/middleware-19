package com.sxu.model;

public class TGonggao implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	//���
	private Integer ggId;
	//����
	private String ggTitle;
	//����
	private String ggContent;
	//����ʱ��
	private String ggTime;
	//�Ƿ�ɾ��
	private String ggDel;
	
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
	public String getGgDel() {
		return ggDel;
	}
	public void setGgDel(String ggDel) {
		this.ggDel = ggDel;
	}
	
	@Override
	public String toString() {
		return "TGonggao [ggId=" + ggId + ", ggTitle=" + ggTitle + ", ggContent=" + ggContent + ", ggTime=" + ggTime
				+ ", ggDel=" + ggDel + "]";
	}
	
	
}
