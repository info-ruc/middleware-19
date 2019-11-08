package sop.dao;

import java.util.ArrayList;

import sop.dao.domain.Programmer;

public interface ProgrammerDao {
	public boolean addProgrammer(Programmer programmer);
	public boolean deleteProgrammer(String programmerId);
	public boolean modifyProgrammerBase(Programmer programmer);
	public Programmer getProgrammerById(String programmerId);
	public ArrayList<String> getAllProgrammers();
	public ArrayList<String> getAllMyTeamsLeader(String programmerId);
	public ArrayList<String> getAllMyTeams(String programmerId);
}
