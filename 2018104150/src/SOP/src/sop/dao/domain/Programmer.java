package sop.dao.domain;

public class Programmer extends User{
	private String pid;
	private String realName;
	private String idNum;		//身份证号	
	private String overview; 	//简历概述
	private String graduation; //毕业学校
	private String educationDegree;//学历
	private String certificate;  //能力证书
	private String experience;//工作经历
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public String getGraduation() {
		return graduation;
	}
	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}
	public String getEducationDegree() {
		return educationDegree;
	}
	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
}
