package sop.service;

import java.util.ArrayList;

import sop.dao.domain.Programmer;
import sop.dao.domain.Team;

public interface ProgrammerService {
	//登陆
	Programmer loginProgrammer(String pid,String password);
	//注册
	void registerProgammer(Programmer programmer);
	//修改个人信息
	void modifyMyInfo(Programmer p);
	//创立团队
	void establishTeam(Team team);
	//申请加入团队
	void applyForTeam(String programmerId, String teamId);
	//获得我的所有团队
	ArrayList<Team> getAllMyTeams(String programmerId);
	//获得以我为队长的所有团队
	ArrayList<Team> getAllMyTeamsLeader(String programmerId);
}
