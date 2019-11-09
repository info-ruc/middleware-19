package junit.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import sop.dao.domain.Bid;
import sop.dao.domain.Programmer;
import sop.service.TeamService;
import sop.service.impl.TeamServiceImpl;

public class TeamServiceTest {

	@Test
	public void testApplyForBid(){
		Bid b=new Bid();
		b.setBidId("3");
		b.setBudget("700");
		b.setComments("bc");
		b.setConfirmed("0");
		TeamService ts=new TeamServiceImpl();
		ts.applyForBid("1", "2", b);
	}
	
	@Test
	public void testGetTeamBidInfo(){
		TeamService ts=new TeamServiceImpl();
		ArrayList<Bid>bids=ts.getTeamBidInfo("1");
		Iterator<Bid> it=bids.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getBidId());
		}
	}
	
	@Test
	public void testAcceptRequest(){
		TeamService ts=new TeamServiceImpl();
		ts.acceptRequest("2", "1");
	}
	
	@Test
	public void testRefuseRequest(){
		TeamService ts=new TeamServiceImpl();
		ts.refuseRequest("2", "2");
	}
	
	@Test
	public void testGetTeamMemberInfo(){
		TeamService ts=new TeamServiceImpl();
		ArrayList<Programmer> ps=ts.getTeamMemberInfo("1");
		Iterator<Programmer> it= ps.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getRealName());
		}
	}
	
	@Test
	public void testGetTeamApplyInfo(){
		TeamService ts=new TeamServiceImpl();
		ArrayList<Programmer> ps=ts.getTeamApplyInfo("1");
		Iterator<Programmer> it= ps.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getRealName());
		}
	}
	
}
