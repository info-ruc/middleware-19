package sop.service.impl;

import java.util.ArrayList;
import java.util.Iterator;

import sop.dao.BidDao;
import sop.dao.CompanyDao;
import sop.dao.ProjectDao;
import sop.dao.domain.Bid;
import sop.dao.domain.Company;
import sop.dao.domain.Project;
import sop.dao.impl.BidDaoImpl;
import sop.dao.impl.CompanyDaoImpl;
import sop.dao.impl.ProjectDaoImpl;
import sop.service.CompanyService;

public class CompanyServiceImpl implements CompanyService{
	
	private static CompanyDao cd=new CompanyDaoImpl();
	private static BidDao bd=new BidDaoImpl();
	private static ProjectDao prod=new ProjectDaoImpl();


	@Override
	public Company loginCompany(String cid, String password) {
		Company c=cd.getCompanyById(cid);
		if(c.getPassword().equals(password))
			return c;
		else
			return null;
	}
	
	@Override
	public void registerCompany(Company company) {
		cd.addCompany(company);
	}
	
	@Override
	public void issueProject(Project project,String companyId) {
		prod.addProject(project,companyId);
	}

	@Override
	public void deleteProject(String proId) {
		prod.deleteProject(proId);
	}

	@Override
	public void affirmBid(String proId,String bidId) {
//		Project pro=prod.getProjectById(proId);
//		pro.setBId(bidId);
		prod.modifyProjectBase(proId,bidId);
//		Bid b=bd.getBidById(bidId);
//		b.setConfirmed("1");
		bd.modifyBid(bidId);
	}

	@Override
	public ArrayList<Bid> getAllMyBid(String proId) {
		ArrayList<Bid> bids=new ArrayList<Bid>();
		ArrayList<String> bidIds=cd.getMyProjectBids(proId);
		Iterator<String> it=bidIds.iterator();
		while(it.hasNext()){
			bids.add(bd.getBidById(it.next()));
		}
		return bids;
	}

	@Override
	public void modifyCompanyInfo(Company c) {
		cd.modifyCompanyBase(c);
	}

	@Override
	public ArrayList<Project> getAllMyProject(String companyId) {
		ArrayList<Project> projects=new ArrayList<Project>();
		ArrayList<String> projectIds=cd.getMyProjects(companyId);
		Iterator<String> it=projectIds.iterator();
		while(it.hasNext()){
			projects.add(prod.getProjectById(it.next()));
		}
		return projects;
	}

}
