package com.sxu.model;

public class TShiti implements java.io.Serializable
{

	private Integer id;
	private String mingcheng;
	private String fujian;
	private String fujianYuanshiming;

	private String shijian;
	private String del;
	private Integer teaid;
	private Integer stuid;
	private String replay;
	private String replaydate;
	private String state;
	private String stuname;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getTeaid() {
		return teaid;
	}
	public void setTeaid(Integer teaid) {
		this.teaid = teaid;
	}
	public String getReplay() {
		return replay;
	}
	public void setReplay(String replay) {
		this.replay = replay;
	}
	public String getReplaydate() {
		return replaydate;
	}
	public void setReplaydate(String replaydate) {
		this.replaydate = replaydate;
	}
	public String getDel()
	{
		return del;
	}
	public void setDel(String del)
	{
		this.del = del;
	}
	public String getFujian()
	{
		return fujian;
	}
	public void setFujian(String fujian)
	{
		this.fujian = fujian;
	}
	public String getFujianYuanshiming()
	{
		return fujianYuanshiming;
	}
	public void setFujianYuanshiming(String fujianYuanshiming)
	{
		this.fujianYuanshiming = fujianYuanshiming;
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getMingcheng()
	{
		return mingcheng;
	}
	public void setMingcheng(String mingcheng)
	{
		this.mingcheng = mingcheng;
	}
	public String getShijian()
	{
		return shijian;
	}
	public void setShijian(String shijian)
	{
		this.shijian = shijian;
	}
	public Integer getStuid() {
		return stuid;
	}
	public void setStuid(Integer stuid) {
		this.stuid = stuid;
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

}