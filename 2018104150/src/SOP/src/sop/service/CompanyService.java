package sop.service;

import java.util.ArrayList;

import sop.dao.domain.Bid;
import sop.dao.domain.Company;
import sop.dao.domain.Project;

public interface CompanyService {
	//公司用户登陆
	Company loginCompany(String cid,String password);
	//注册公司用户
	void registerCompany(Company company);
	//发布项目
	void issueProject(Project project,String companyId);
	//删除项目
	void deleteProject(String proId);
	//确定招标
	void affirmBid(String proId,String bidId);
	//查看标
	ArrayList<Bid> getAllMyBid(String proId);
	//修改公司信息
	void modifyCompanyInfo(Company c);
	//查看历史项目
	ArrayList<Project> getAllMyProject(String companyId);
}
