package sop.service.impl;

import java.util.ArrayList;
import java.util.Iterator;

import sop.dao.BidDao;
import sop.dao.CompanyDao;
import sop.dao.ProgrammerDao;
import sop.dao.ProjectDao;
import sop.dao.TeamDao;
import sop.dao.domain.Bid;
import sop.dao.domain.Company;
import sop.dao.domain.Programmer;
import sop.dao.domain.Project;
import sop.dao.domain.Team;
import sop.dao.impl.BidDaoImpl;
import sop.dao.impl.CompanyDaoImpl;
import sop.dao.impl.ProgrammerDaoImpl;
import sop.dao.impl.ProjectDaoImpl;
import sop.dao.impl.TeamDaoImpl;
import sop.service.GeneralService;

public class GeneralServiceImpl implements GeneralService {

	private static ProjectDao prod=new ProjectDaoImpl();
	private static ProgrammerDao pd=new ProgrammerDaoImpl();
	private static CompanyDao cd=new CompanyDaoImpl();
	private static TeamDao td=new TeamDaoImpl();
	private static BidDao bd=new BidDaoImpl();
	
	@Override
	public ArrayList<Project> getAllProjects() {
		ArrayList<Project> projects=new ArrayList<Project>();
		ArrayList<String> projectIds=prod.getAllProjects();
		Iterator<String> it=projectIds.iterator();
		while(it.hasNext()){
			projects.add(prod.getProjectById(it.next()));
		}
		return projects;
	}

	@Override
	public ArrayList<Company> getAllCompanys() {
		ArrayList<Company> companys=new ArrayList<Company>();
		ArrayList<String> companyIds=cd.getAllCompanys();
		Iterator<String> it=companyIds.iterator();
		while(it.hasNext()){
			companys.add(cd.getCompanyById(it.next()));
		}
		return companys;
	}

	@Override
	public ArrayList<Programmer> getAllProgrammers() {
		ArrayList<Programmer> programmers=new ArrayList<Programmer>();
		ArrayList<String> programmerIds=pd.getAllProgrammers();
		Iterator<String> it=programmerIds.iterator();
		while(it.hasNext()){
			programmers.add(pd.getProgrammerById(it.next()));
		}
		return programmers;
	}

	@Override
	public ArrayList<Team> getAllTeams() {
		ArrayList<Team> teams=new ArrayList<Team>();
		ArrayList<String> teamIds=td.getAllTeams();
		Iterator<String> it=teamIds.iterator();
		while(it.hasNext()){
			teams.add(td.getByTeamId(it.next()));
		}
		return teams;
	}

	@Override
	public Project getProjectInfo(String projectId) {
		Project pro=prod.getProjectById(projectId);
		return pro;
	}

	@Override
	public Company getCompanyInfo(String companyId) {
		Company c=cd.getCompanyById(companyId);
		return c;
	}

	@Override
	public Programmer getProgrammerInfo(String programmerId) {
		Programmer p=pd.getProgrammerById(programmerId);
		return p;
	}

	@Override
	public Team getTeamInfo(String teamId) {
		Team t=td.getByTeamId(teamId);
		return t;
	}

	@Override
	public Bid getBidInfo(String bidId) {
		Bid b=bd.getBidById(bidId);
		return b;
	}

}
