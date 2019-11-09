package sop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sop.dao.ProjectDao;
import sop.dao.domain.Project;

public class ProjectDaoImpl implements ProjectDao{

	@Override
	public boolean addProject(Project project,String companyId) {
		String sql="insert into project values(?,?,?,?,?,?,?,?)";
		String [] parameters=new String[8];
		parameters[0]=project.getProId();
		parameters[1]=project.getDescribtion();
		parameters[2]=project.getProName();
		parameters[3]=project.getType();
		parameters[4]=project.getMaxBudget();
		parameters[5]=project.isTerminated();
		parameters[6]=project.isHasTeam();
		parameters[7]=project.getBId();
		SQLHelper.executeUpdate(sql, parameters);
		sql="insert into company_project values(?,?)";
		parameters=new String[2];
		parameters[0]=project.getProId();
		parameters[1]=companyId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean deleteProject(String proId) {
		String sql;
		String[] parameters;
		sql="delete from company_project where proid=?";
		parameters=new String[1];
		parameters[0]=proId;
		SQLHelper.executeUpdate(sql, parameters);
		sql="delete from project where proid=?";
		parameters=new String[1];
		parameters[0]=proId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean modifyProjectBase(String proId,String bidId) {
//		String sql="delete from project where proid=?";
//		String[] parameters=new String[1];
//		parameters[0]=project.getProId();
//		SQLHelper.executeUpdate(sql, parameters);
//		sql="insert into project values(?,?,?,?,?,?,?,?)";
//		parameters=new String[8];
//		parameters[0]=project.getProId();
//		parameters[1]=project.getDescribtion();
//		parameters[2]=project.getProName();
//		parameters[3]=project.getType();
//		parameters[4]=project.getMaxBudget();
//		parameters[5]=project.isTerminated();
//		parameters[6]=project.isHasTeam();
//		parameters[7]=project.getBId();
//		SQLHelper.executeUpdate(sql, parameters);
		String sql="update project set bidId=? where proId=?";
		String[] parameters=new String[2];
		parameters[0]=bidId;
		parameters[1]=proId;
		SQLHelper.executeUpdate(sql, parameters);
		sql="update project set hasTeam='1' where proId=?";
		parameters=new String[1];
		parameters[0]=proId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public Project getProjectById(String proId) {
		Project p=new Project();
		String sql="select * from project where proid=?";
		String[] parameters=new String[1];
		parameters[0]=proId;
		SQLHelper.executeQuery(sql, parameters);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				p.setProId(rs.getString("proid"));
				p.setDescribtion(rs.getString("describtion"));
				p.setProName(rs.getString("proname"));
				p.setType(rs.getString("type"));
				p.setMaxBudget(rs.getString("maxbudget"));
				p.setTerminated(rs.getString("isterminated"));
				p.setHasTeam(rs.getString("hasteam"));
				p.setBId(rs.getString("bidid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SQLHelper.close();
		return p;
	}

	@Override
	public ArrayList<String> getAllProjects() {
		ArrayList<String> projectIds=new ArrayList<String>();
		String sql="{call outAllProjects(?)}";
		SQLHelper.executeListAllProcedure2(sql);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				projectIds.add(rs.getString("proid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return projectIds;
	}

}
