package sop.service;

import java.util.ArrayList;

import sop.dao.domain.Bid;
import sop.dao.domain.Company;
import sop.dao.domain.Programmer;
import sop.dao.domain.Project;
import sop.dao.domain.Team;

public interface GeneralService {
	//展示所有项目
	ArrayList<Project> getAllProjects();
	//展示所有接包方用户
	ArrayList<Company> getAllCompanys();
	//展示所有接包方用户
	ArrayList<Programmer> getAllProgrammers();
	//展示所有团队
	ArrayList<Team> getAllTeams();
	//查看项目信息
	Project getProjectInfo(String projectId);
	//查看公司信息
	Company getCompanyInfo(String companyId);
	//查看个人信息
	Programmer getProgrammerInfo(String programmerId);
	//查看团队信息
	Team getTeamInfo(String teamId);
	//查看标信息
	Bid getBidInfo(String bidId);
}
