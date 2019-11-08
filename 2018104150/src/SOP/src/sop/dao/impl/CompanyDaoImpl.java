package sop.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sop.dao.CompanyDao;
import sop.dao.domain.Company;

public class CompanyDaoImpl implements CompanyDao{

	@Override
	public boolean addCompany(Company company) {
		String sql="insert into company values(?,?,?,?,?,?,?,?)";
		String [] parameters=new String[8];
		parameters[0]=company.getCid();
		parameters[1]=company.getCompanyName();
		parameters[2]=company.getTelephoneNum();
		parameters[3]=company.getLocation();
		parameters[4]=company.getAccount();
		parameters[5]=company.getPassword();
		parameters[6]=company.getType();
		parameters[7]=company.getMail();
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean deleteCompany(String companyId) {
		String sql="delete from company where cid=?";
		String[] parameters=new String[1];
		parameters[0]=companyId;
		SQLHelper.executeUpdate(sql, parameters);
		sql="delete from company_project where cid=?";
		parameters=new String[1];
		parameters[0]=companyId;
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public boolean modifyCompanyBase(Company company) {
		String sql="delete from company where cid=?";
		String[] parameters=new String[1];
		parameters[0]=company.getCid();
		SQLHelper.executeUpdate(sql, parameters);
		sql="insert into company values(?,?,?,?,?,?,?,?)";
		parameters=new String[8];
		parameters[0]=company.getCid();
		parameters[1]=company.getCompanyName();
		parameters[2]=company.getTelephoneNum();
		parameters[3]=company.getLocation();
		parameters[4]=company.getAccount();
		parameters[5]=company.getPassword();
		parameters[6]=company.getType();
		parameters[7]=company.getMail();
		SQLHelper.executeUpdate(sql, parameters);
		SQLHelper.close();
		return true;
	}

	@Override
	public Company getCompanyById(String companyId) {
		Company c=new Company();
		String sql="select * from company where cid=?";
		String[] parameters=new String[1];
		parameters[0]=companyId;
		SQLHelper.executeQuery(sql, parameters);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
			c.setCid(rs.getString("cid"));
			c.setCompanyName(rs.getString("companyname"));
			c.setTelephoneNum(rs.getString("telephonenum"));
			c.setLocation(rs.getString("location"));
			c.setAccount(rs.getString("account"));
			c.setPassword(rs.getString("password"));
			c.setType(rs.getString("type"));
			c.setMail(rs.getString("mail"));
}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return c;
	}

	@Override
	public ArrayList<String> getMyProjects(String companyId) {
		ArrayList<String> projectIds=new ArrayList<String>();
		String sql="{call outAllProjects_cid(?,?)}";
		//int id=Integer.valueOf(companyId);
		SQLHelper.executeListAllProcedure(sql, companyId);
		ResultSet rs=SQLHelper.getRs();
		try {
			rs = (ResultSet)SQLHelper.getCs().getObject(2);
			//循环取出
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

	@Override
	public ArrayList<String> getMyProjectBids(String projectId) {
		ArrayList<String> bidIds=new ArrayList<String>();
		String sql="{call outAllBids_proid(?,?)}";
		SQLHelper.executeListAllProcedure(sql, projectId);
		ResultSet rs=SQLHelper.getRs();
		try {
			rs = (ResultSet)SQLHelper.getCs().getObject(2);
			//循环取出
			while(rs.next()){
				bidIds.add(rs.getString("bid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return bidIds;
	}

	@Override
	public ArrayList<String> getAllCompanys() {
		ArrayList<String> companyIds=new ArrayList<String>();
		String sql="{call outAllCompanys(?)}";
		SQLHelper.executeListAllProcedure2(sql);
		ResultSet rs=SQLHelper.getRs();
		try {
			while(rs.next()){
				companyIds.add(rs.getString("cid"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			SQLHelper.close();
		}
		return companyIds;
	}

}
