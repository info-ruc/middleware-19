package sop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import sop.dao.BidDao;
import sop.dao.domain.Bid;

public class BidDaoImpl implements BidDao {

	@Override
	public boolean addBid(String proId, String teamId,Bid bid) {
		String sql="insert into bid values(?,?,?,?)";
		String[] parameters=new String[4];
		parameters[0]=bid.getBidId();
		parameters[1]=bid.getComments();
		parameters[2]=bid.getBudget();
		parameters[3]=bid.isConfirmed();
		SQLHelper.executeUpdate(sql, parameters);
		sql="insert into team_bid_give values(?,?)";
		parameters=new String[2];
		parameters[0]=teamId;
		parameters[1]=bid.getBidId();
		SQLHelper.executeUpdate(sql, parameters);
		sql="insert into project_bid values(?,?)";
		parameters=new String[2];
		parameters[0]=bid.getBidId();
		parameters[1]=proId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean deleteBid(String bidId) {
		String sql;
		String[] parameters;
		sql="delete from project_bid where bid=?";
		parameters=new String[1];
		parameters[0]=bidId;
		SQLHelper.executeUpdate(sql, parameters);
		sql="delete from team_bid_give where bid=?";
		parameters=new String[1];
		parameters[0]=bidId;
		SQLHelper.executeUpdate(sql, parameters);
		sql="delete from bid where bid=?";
		parameters=new String[1];
		parameters[0]=bidId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean modifyBid(String bidId) {
		String sql;
		String [] parameters;
//		sql="delete from bid where bid=?";
//		parameters=new String[1];
//		parameters[0]=bid.getBidId();
		sql="update bid set isConfirmed='1' where bid=?";
		parameters=new String[1];
		parameters[0]=bidId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}
	
	@Override
	public Bid getBidById(String bid) {
		Bid b=new Bid();
		String sql="select * from bid where bid=?";
		String[] parameters=new String[1];
		parameters[0]=bid;
		SQLHelper.executeQuery(sql, parameters);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				b.setBidId(rs.getString("bid"));
				b.setBudget(rs.getString("budget"));
				b.setComments(rs.getString("comments"));
				b.setConfirmed(rs.getString("isconfirmed"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SQLHelper.close();
		return b;
	}
//
//	@Override
//	public ArrayList<Bid> getBidsByProId(String proId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ArrayList<Bid> getBidsByTeamId(String teamId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
