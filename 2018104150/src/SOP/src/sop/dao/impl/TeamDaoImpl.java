package sop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sop.dao.TeamDao;
import sop.dao.domain.Team;

public class TeamDaoImpl implements TeamDao{

	@Override
	public boolean addTeam(Team team) {
		String sql;
		sql="insert into team values(?,?,?,?)";
		String[] parameters;
		parameters=new String[4];
		parameters[0]=team.getTeamId();
		parameters[1]=team.getTeamName();
		parameters[2]=team.getProfile();
		parameters[3]=team.getLeaderId();
		SQLHelper.executeUpdate(sql, parameters);
		sql="insert into programmer_team_unite values(?,?)";
		parameters=new String[2];
		parameters[0]=team.getLeaderId();
		parameters[1]=team.getTeamId();		
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean deleteTeam(String teamId) {
		String sql;
		String[] parameters;
		sql="delete from programmer_team_apply where tid=?";
		parameters=new String[1];
		parameters[0]=teamId;
		SQLHelper.executeUpdate(sql, parameters);
		sql="delete from programmer_team_unite where tid=?";
		parameters=new String[1];
		parameters[0]=teamId;
		SQLHelper.executeUpdate(sql, parameters);
		sql="delete from team where tid=?";
		parameters=new String[1];
		parameters[0]=teamId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean modifyTeamBase(Team team) {
		String sql="delete from team where tid=?";
		String[] parameters=new String[1];
		parameters[0]=team.getTeamId();
		SQLHelper.executeUpdate(sql, parameters);
		sql="insert into team values(?,?,?,?)";
		parameters=new String[4];
		parameters[0]=team.getTeamId();
		parameters[1]=team.getTeamName();
		parameters[2]=team.getProfile();
		parameters[3]=team.getLeaderId();
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public Team getByTeamId(String teamId) {
		Team t=new Team();
		//向一个Team对象中装属性
		String sql="select * from team where tid=?";
		String[] parameters=new String[1];
		parameters[0]=teamId;
		SQLHelper.executeQuery(sql, parameters);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				t.setTeamId(rs.getString("tid"));
				t.setProfile(rs.getString("profile"));
				t.setTeamName(rs.getString("teamname"));
				t.setLeaderId(rs.getString("leaderid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SQLHelper.close();
		return t;
	}

	@Override
	public boolean addTeamApply(String programmerId,String teamId) {
		String sql="insert into programmer_team_apply values(?,?)";
		String[] parameters=new String[2];
		parameters[0]=programmerId;
		parameters[1]=teamId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean addTeamMember(String programmerId,String teamId) {
		String sql="insert into programmer_team_unite values(?,?)";
		String[] parameters=new String[2];
		parameters[0]=programmerId;
		parameters[1]=teamId;
		SQLHelper.executeUpdate(sql, parameters);
		sql="delete from programmer_team_apply where pid=? and tid=?";
		parameters=new String[2];
		parameters[0]=programmerId;
		parameters[1]=teamId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean deleteTeamMember(String programmerId) {
		String sql="delete from programmer_team_unite where pid=?";
		String[] parameters=new String[1];
		parameters[0]=programmerId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean deleteTeamApply(String programmerId, String teamId) {
		String sql="delete from programmer_team_apply where pid=? and tid=?";
		String[] parameters=new String[2];
		parameters[0]=programmerId;
		parameters[1]=teamId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public ArrayList<String> getMyBids(String teamId) {
		ArrayList<String> bids=new ArrayList<String>();
		String sql="{call outAllBids_tid(?,?)}";
		SQLHelper.executeListAllProcedure(sql, teamId);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				bids.add(rs.getString("bid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return bids;
	}

	@Override
	public ArrayList<String> getMyMembers(String teamId) {
		ArrayList<String> members=new ArrayList<String>();
		String sql="{call outAllMembers_tid(?,?)}";
		SQLHelper.executeListAllProcedure(sql, teamId);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				members.add(rs.getString("pid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return members;
	}

	@Override
	public ArrayList<String> getMyApply(String teamId) {
		ArrayList<String> candidates=new ArrayList<String>();
		String sql="{call outAllApply_tid(?,?)}";
		SQLHelper.executeListAllProcedure(sql, teamId);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				candidates.add(rs.getString("pid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return candidates;
	}

	@Override
	public ArrayList<String> getMyProjects(String teamId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAllTeams() {
		ArrayList<String> teamIds=new ArrayList<String>();
		String sql="{call outAllTeams(?)}";
		SQLHelper.executeListAllProcedure2(sql);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				teamIds.add(rs.getString("tid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return teamIds;
	}

}
