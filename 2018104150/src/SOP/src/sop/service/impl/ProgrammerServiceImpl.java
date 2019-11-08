package sop.service.impl;

import java.util.ArrayList;
import java.util.Iterator;

import sop.dao.ProgrammerDao;
import sop.dao.TeamDao;
import sop.dao.domain.Programmer;
import sop.dao.domain.Team;
import sop.dao.impl.ProgrammerDaoImpl;
import sop.dao.impl.TeamDaoImpl;
import sop.service.ProgrammerService;

public class ProgrammerServiceImpl implements ProgrammerService{

	private static ProgrammerDao pd=new ProgrammerDaoImpl();
	private static TeamDao td=new TeamDaoImpl();
	
	@Override
	public void registerProgammer(Programmer programmer) {
		pd.addProgrammer(programmer);
	}

	@Override
	public void modifyMyInfo(Programmer p) {
		pd.modifyProgrammerBase(p);
	}

	@Override
	public void establishTeam(Team team) {
		td.addTeam(team);
	}

	@Override
	public void applyForTeam(String programmerId, String teamId) {
		td.addTeamApply(programmerId, teamId);
	}

	@Override
	public ArrayList<Team> getAllMyTeams(String programmerId) {
		ArrayList<Team> ts=new ArrayList<Team>();
		ArrayList<String> tids=pd.getAllMyTeams(programmerId);
		Iterator<String> it =tids.iterator();
		while(it.hasNext()){
			ts.add(td.getByTeamId(it.next()));
		}
		return ts;
	}

	@Override
	public ArrayList<Team> getAllMyTeamsLeader(String programmerId) {
		ArrayList<Team> ts=new ArrayList<Team>();
		ArrayList<String> tids=pd.getAllMyTeamsLeader(programmerId);
		Iterator<String> it =tids.iterator();
		while(it.hasNext()){
			ts.add(td.getByTeamId(it.next()));
		}
		return ts;
	}

	@Override
	public Programmer loginProgrammer(String pid, String password) {
		Programmer p=pd.getProgrammerById(pid);
		if(p.getPassword().equals(password))
			return p;
		else
			return null;
	}

}
