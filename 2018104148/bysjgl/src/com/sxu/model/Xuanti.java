package com.sxu.model;

public class Xuanti implements java.io.Serializable {
	protected static final long serialVersionUID = -1L;

	public Xuanti() {
	}

	private Long id;

	private Integer sid;

	private String sname;

	private Integer ktid;

	private String ktname;

	private Integer tid;

	private String tname;

	private String shzt;
	
	private String ktbg;
	private String lunwen;
	private String fenshu;
	private String jieguo;
    private String zqhb;
	
	
    
	public String getZqhb() {
		return zqhb;
	}

	public void setZqhb(String zqhb) {
		this.zqhb = zqhb;
	}

	public String getKtbg() {
		return ktbg;
	}

	public void setKtbg(String ktbg) {
		this.ktbg = ktbg;
	}

	public String getLunwen() {
		return lunwen;
	}

	public void setLunwen(String lunwen) {
		this.lunwen = lunwen;
	}

	public String getFenshu() {
		return fenshu;
	}

	public void setFenshu(String fenshu) {
		this.fenshu = fenshu;
	}

	public String getJieguo() {
		return jieguo;
	}

	public void setJieguo(String jieguo) {
		this.jieguo = jieguo;
	}

	public String getShzt() {
		return shzt;
	}

	public void setShzt(String shzt) {
		this.shzt = shzt;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSid() {
		return this.sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getSname() {
		return this.sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Integer getKtid() {
		return this.ktid;
	}

	public void setKtid(Integer ktid) {
		this.ktid = ktid;
	}

	public String getKtname() {
		return this.ktname;
	}

	public void setKtname(String ktname) {
		this.ktname = ktname;
	}

	public Integer getTid() {
		return this.tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getTname() {
		return this.tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	
}
