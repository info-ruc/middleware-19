package sop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sop.dao.ProgrammerDao;
import sop.dao.domain.Programmer;

public class ProgrammerDaoImpl implements ProgrammerDao{

	@Override
	public boolean addProgrammer(Programmer programmer) {
		String sql="insert into programmer values(?,?,?,?,?,?,?,?,?,?,?,?)";
		String[] parameters=new String[12];
		parameters[0]=programmer.getPid();
		parameters[1]=programmer.getRealName();
		parameters[2]=programmer.getIdNum();
		parameters[3]=programmer.getOverview();
		parameters[4]=programmer.getGraduation();
		parameters[5]=programmer.getEducationDegree();
		parameters[6]=programmer.getCertificate();
		parameters[7]=programmer.getExperience();
		parameters[8]=programmer.getAccount();
		parameters[9]=programmer.getPassword();
		parameters[10]=programmer.getType();
		parameters[11]=programmer.getMail();
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean deleteProgrammer(String programmerId) {
		String sql="delete from programmer where pid=?";
		String[] parameters=new String[1];
		parameters[0]=programmerId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean modifyProgrammerBase(Programmer programmer) {
		String sql="delete from programmer where pid=?";
		String[] parameters=new String[1];
		parameters[0]=programmer.getPid();
		SQLHelper.executeUpdate(sql, parameters);
		sql="insert into programmer values(?,?,?,?,?,?,?,?,?,?,?,?)";
		parameters=new String[12];
		parameters[0]=programmer.getPid();
		parameters[1]=programmer.getRealName();
		parameters[2]=programmer.getIdNum();
		parameters[3]=programmer.getOverview();
		parameters[4]=programmer.getGraduation();
		parameters[5]=programmer.getEducationDegree();
		parameters[6]=programmer.getCertificate();
		parameters[7]=programmer.getExperience();
		parameters[8]=programmer.getAccount();
		parameters[9]=programmer.getPassword();
		parameters[10]=programmer.getType();
		parameters[11]=programmer.getMail();
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public Programmer getProgrammerById(String programmerId) {
		Programmer p =new Programmer();
		String sql="select * from programmer where pid=?";
		String[] parameters=new String[1];
		parameters[0]=programmerId;
		SQLHelper.executeQuery(sql, parameters);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
			p.setPid(rs.getString("pid"));
			p.setRealName(rs.getString("realname"));
			p.setIdNum(rs.getString("idnum"));
			p.setOverview(rs.getString("overview"));
			p.setGraduation(rs.getString("graduation"));
			p.setCertificate(rs.getString("certificate"));
			p.setExperience(rs.getString("experience"));
			p.setAccount(rs.getString("account"));
			p.setPassword(rs.getString("password"));
			p.setType(rs.getString("type"));
			p.setMail(rs.getString("mail"));
}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return p;
	}

	@Override
	public ArrayList<String> getAllProgrammers() {
		ArrayList<String> programmerIds=new ArrayList<String>();
		String sql="{call outAllProgrammers(?)}";
		SQLHelper.executeListAllProcedure2(sql);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				programmerIds.add(rs.getString("pid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return programmerIds;
	}

	@Override
	public ArrayList<String> getAllMyTeamsLeader(String programmerId) {
		ArrayList<String> teamIds=new ArrayList<String>();
		String sql="{call outallteams_lid(?,?)}";
		SQLHelper.executeListAllProcedure(sql,programmerId);
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

	@Override
	public ArrayList<String> getAllMyTeams(String programmerId) {
		ArrayList<String> teamIds=new ArrayList<String>();
		String sql="{call outallteams_pid(?,?)}";
		SQLHelper.executeListAllProcedure(sql,programmerId);
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
