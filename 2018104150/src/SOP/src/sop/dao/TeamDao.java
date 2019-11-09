package sop.dao;

import java.util.ArrayList;

import sop.dao.domain.Team;

public interface TeamDao {
	public boolean addTeam(Team team);
	public boolean deleteTeam(String teamId);
	public boolean modifyTeamBase(Team team);
	public Team getByTeamId(String teamId);
//	public boolean modifyTeamBids(String teamId,ArrayList<String> bids);
//	public boolean modifyTeamCandidates(String teamId,ArrayList<String> candidates);
//	public boolean modifyTeamMembers(String teamId,ArrayList<String> members);
	public boolean addTeamApply(String programmerId,String teamId);
	public boolean deleteTeamApply(String programmerId,String teamId);
	public boolean addTeamMember(String programmerId,String teamId);
	public boolean deleteTeamMember(String programmerId);
	public ArrayList<String> getMyBids(String teamId);
	public ArrayList<String> getMyMembers(String teamId);
	public ArrayList<String> getMyApply(String teamId);
	public ArrayList<String> getMyProjects(String teamId);
	public ArrayList<String> getAllTeams();
}
