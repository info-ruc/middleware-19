package entity;

public class LessonRecord {
	private String id;
	private String name;
	private String less_name;
	private String grade;
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getLess_name(){
		return less_name;
	}
	
	public String getGrade(){
		return grade;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setLess_name(String less_name){
		this.less_name = less_name;
	}
	
	public void setGrade(String grade){
		this.grade = grade;
	}
	
}
