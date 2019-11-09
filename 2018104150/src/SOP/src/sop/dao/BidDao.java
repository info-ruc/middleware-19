package sop.dao;

import sop.dao.domain.Bid;

public interface BidDao {
	public boolean addBid(String proId, String teamId,Bid bid);
	public boolean deleteBid(String bidId);
	public boolean modifyBid(String bidId);
	public Bid getBidById(String bid);
}
