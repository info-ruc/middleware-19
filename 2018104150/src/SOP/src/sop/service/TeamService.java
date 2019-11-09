package sop.service;

import java.util.ArrayList;

import sop.dao.domain.Bid;
import sop.dao.domain.Programmer;

public interface TeamService {
	//查看入团申请列表
	ArrayList<Programmer> getTeamApplyInfo(String teamId);
	//查看团队成员列表
	ArrayList<Programmer> getTeamMemberInfo(String teamId);
	//查看招标列表
	ArrayList<Bid> getTeamBidInfo(String teamId);
	//参与招标
	void applyForBid(String proId, String teamId,Bid bid);
	//删除成员
	void deleteTeammate(String programmerId,String teamId);
	//确认申请
	void acceptRequest(String programmerId,String teamId);
	//拒绝申请
	void refuseRequest(String programmerId,String teamId);
	//解散团队
	void deleteTeam(String teamId);
}
