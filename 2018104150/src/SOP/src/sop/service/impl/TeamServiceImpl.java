package sop.service.impl;

import java.util.ArrayList;
import java.util.Iterator;

import sop.dao.BidDao;
import sop.dao.ProgrammerDao;
import sop.dao.TeamDao;
import sop.dao.domain.Bid;
import sop.dao.domain.Programmer;
import sop.dao.impl.BidDaoImpl;
import sop.dao.impl.ProgrammerDaoImpl;
import sop.dao.impl.TeamDaoImpl;
import sop.service.TeamService;

public class TeamServiceImpl implements TeamService{

	private static TeamDao td=new TeamDaoImpl();
	private static BidDao bd=new BidDaoImpl();
	private static ProgrammerDao pd=new ProgrammerDaoImpl();
	
	@Override
	public ArrayList<Programmer> getTeamApplyInfo(String teamId) {
		ArrayList<Programmer> programmers=new ArrayList<Programmer>();
		ArrayList<String> programmerIds=td.getMyApply(teamId);
		Iterator<String> it=programmerIds.iterator();
		while(it.hasNext()){
			programmers.add(pd.getProgrammerById(it.next()));
		}
		return programmers;
	}

	@Override
	public ArrayList<Programmer> getTeamMemberInfo(String teamId) {
		ArrayList<Programmer> programmers=new ArrayList<Programmer>();
		ArrayList<String> programmerIds=td.getMyMembers(teamId);
		Iterator<String> it=programmerIds.iterator();
		while(it.hasNext()){
			programmers.add(pd.getProgrammerById(it.next()));
		}
		return programmers;
	}

	@Override
	public ArrayList<Bid> getTeamBidInfo(String teamId) {
		ArrayList<Bid> bids=new ArrayList<Bid>();
		ArrayList<String> bidIds=td.getMyBids(teamId);
		Iterator<String> it=bidIds.iterator();
		while(it.hasNext()){
			bids.add(bd.getBidById(it.next()));
		}
		return bids;
	}

	@Override
	public void applyForBid(String proId, String teamId,Bid bid) {
		bd.addBid(proId,teamId,bid);
	}

	@Override
	public void deleteTeammate(String programmerId,String teamId) {
		td.deleteTeamMember(programmerId);
	}

	@Override
	public void acceptRequest(String programmerId,String teamId) {
		td.addTeamMember(programmerId, teamId);
	}

	@Override
	public void refuseRequest(String programmerId, String teamId) {
		td.deleteTeamApply(programmerId, teamId);
	}

	@Override
	public void deleteTeam(String teamId) {
		ArrayList<String> bids=td.getMyBids(teamId);
		Iterator<String> it=bids.iterator();
		while(it.hasNext()){
			bd.deleteBid(it.next());
		}
		td.deleteTeam(teamId);
	}
}
