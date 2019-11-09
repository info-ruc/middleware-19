package junit.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import sop.dao.domain.Programmer;
import sop.dao.domain.Team;
import sop.service.ProgrammerService;
import sop.service.impl.ProgrammerServiceImpl;

public class ProgrammerServiceTest {

	@Test
	public void testRegisterProgammer(){
		Programmer p=new Programmer();
		p.setAccount("1");
		p.setPassword("1");
		p.setType("0");
		p.setRealName("4rn");
		p.setPid("4");
		ProgrammerService ps=new ProgrammerServiceImpl();
		ps.registerProgammer(p);
	}
//	
//	@Test
//	public void testGetMyInfo(){
//		ProgrammerService ps=new ProgrammerServiceImpl();
//		Programmer p=ps.getMyInfo("1");
//		System.out.println(p.getAccount()+p.getRealName());
//	}
	
	@Test
	public void testModifyMyInfo(){
		Programmer p=new Programmer();
		p.setAccount("1");
		p.setPassword("1");
		p.setType("0");
		p.setRealName("nrn");
		p.setPid("1");
		ProgrammerService ps=new ProgrammerServiceImpl();
		ps.modifyMyInfo(p);
	}
	
	@Test
	public void testEstablishTeam(){
		Team t=new Team();
		t.setProfile("p");
		t.setTeamId("2");
		t.setTeamName("tn");
		t.setLeaderId("1");
		ProgrammerService ps=new ProgrammerServiceImpl();
		ps.establishTeam(t);
	}
	
	@Test
	public void testApplyForTeam(){
		ProgrammerService ps=new ProgrammerServiceImpl();
		ps.applyForTeam("4", "1");
	}

	@Test
	public void testLoginProgammer(){
		ProgrammerService ps=new ProgrammerServiceImpl();
		Programmer p=ps.loginProgrammer("1", "1");
		System.out.println(p.getPassword());
	}

	@Test
	public void testGetAllMyTeamsLeader(){
		ProgrammerService ps=new ProgrammerServiceImpl();
		ArrayList<Team> ts=ps.getAllMyTeamsLeader("p1");
		Iterator<Team> it=ts.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getLeaderId());
		}
	}
	
}
