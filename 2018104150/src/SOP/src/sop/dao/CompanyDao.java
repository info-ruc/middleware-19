package sop.dao;

import java.util.ArrayList;

import sop.dao.domain.Company;

public interface CompanyDao {
	public boolean addCompany(Company company);
	public boolean deleteCompany(String companyId);
	public boolean modifyCompanyBase(Company company);
	public Company getCompanyById(String companyId);
	public ArrayList<String> getMyProjects(String companyId);
	public ArrayList<String> getMyProjectBids(String projectId);
	public ArrayList<String> getAllCompanys();
}
