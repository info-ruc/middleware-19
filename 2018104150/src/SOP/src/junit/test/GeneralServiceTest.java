package junit.test;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import sop.dao.domain.Company;
import sop.dao.domain.Programmer;
import sop.dao.domain.Project;
import sop.dao.domain.Team;
import sop.service.impl.GeneralServiceImpl;

public class GeneralServiceTest {

	@Test
	public void testGetAllProjects(){
		GeneralServiceImpl gs=new GeneralServiceImpl();
		ArrayList<Project> pros=gs.getAllProjects();
		Iterator<Project> it=pros.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getProId());
		}
	}
	
	@Test
	public void testGetAllProgrammers(){
		GeneralServiceImpl gs=new GeneralServiceImpl();
		ArrayList<Programmer> ps=gs.getAllProgrammers();
		Iterator<Programmer> it=ps.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getRealName());
		}
	}
	
	@Test
	public void testGetAllTeams(){
		GeneralServiceImpl gs=new GeneralServiceImpl();
		ArrayList<Team> ts=gs.getAllTeams();
		Iterator<Team> it=ts.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getLeaderId());
		}
	}
	
	@Test
	public void testGetAllCompanys(){
		GeneralServiceImpl gs=new GeneralServiceImpl();
		ArrayList<Company> cs=gs.getAllCompanys();
		Iterator<Company> it=cs.iterator();
		while(it.hasNext()){
			System.out.println(it.next().getCompanyName());
		}
	}
	
}
