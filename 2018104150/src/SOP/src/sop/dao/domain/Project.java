package sop.dao.domain;

public class Project {
	private String proId;		//项目id
	private String isTerminated;	
	private String hasTeam;		//是否有队伍中标
	private String bId;		
	private String describtion;	//项目描述
	private String proName;		//项目名称
	private String type;		//项目类型（Web、移动、微信等等）
	private String maxBudget;		//最大项目预算
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String isTerminated() {
		return isTerminated;
	}
	public void setTerminated(String isTerminated) {
		this.isTerminated = isTerminated;
	}
	public String isHasTeam() {
		return hasTeam;
	}
	public void setHasTeam(String hasTeam) {
		this.hasTeam = hasTeam;
	}
	public String getBId() {
		return bId;
	}
	public void setBId(String bId) {
		this.bId = bId;
	}
	public String getDescribtion() {
		return describtion;
	}
	public void setDescribtion(String describtion) {
		this.describtion = describtion;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMaxBudget() {
		return maxBudget;
	}
	public void setMaxBudget(String maxBudget) {
		this.maxBudget = maxBudget;
	}
}
