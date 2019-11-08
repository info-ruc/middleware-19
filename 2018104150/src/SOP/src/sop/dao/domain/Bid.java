package sop.dao.domain;

public class Bid {
	private String bidId;
	private String comments;	
	private String budget;
	private String isConfirmed;
	public String getBidId() {
		return bidId;
	}
	public void setBidId(String bidId) {
		this.bidId = bidId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String isConfirmed() {
		return isConfirmed;
	}
	public void setConfirmed(String isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
}
