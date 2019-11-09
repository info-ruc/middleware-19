package sop.dao;

import java.util.ArrayList;

import sop.dao.domain.Project;

public interface ProjectDao {
	public boolean addProject(Project project,String companyId);
	public boolean deleteProject(String proId);
	public boolean modifyProjectBase(String proId,String bidId);
	public Project getProjectById(String proId);
	public ArrayList<String> getAllProjects();
}
