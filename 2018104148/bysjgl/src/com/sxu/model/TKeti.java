package com.sxu.model;

public class TKeti implements java.io.Serializable {
	protected static final long serialVersionUID = -1L;

	public TKeti() {
	}

	private Integer id;

	private String name;

	private Integer teaId;

	private String teaname;

	private String ktnd;
	private String ktfx;
	private Integer xxrs=0;
	private String ktyq;
	private Integer yxrs=0;
	public String getKtnd() {
		return ktnd;
	}

	public void setKtnd(String ktnd) {
		this.ktnd = ktnd;
	}

	public String getKtfx() {
		return ktfx;
	}

	public void setKtfx(String ktfx) {
		this.ktfx = ktfx;
	}

	public int getXxrs() {
		return xxrs;
	}

	public void setXxrs(int xxrs) {
		this.xxrs = xxrs;
	}

	public String getKtyq() {
		return ktyq;
	}

	public void setKtyq(String ktyq) {
		this.ktyq = ktyq;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTeaId() {
		return this.teaId;
	}

	public void setTeaId(Integer teaId) {
		this.teaId = teaId;
	}

	public String getTeaname() {
		return this.teaname;
	}

	public void setTeaname(String teaname) {
		this.teaname = teaname;
	}

	public Integer getYxrs() {
		return yxrs;
	}

	public void setYxrs(Integer yxrs) {
		this.yxrs = yxrs;
	}

	public void setXxrs(Integer xxrs) {
		this.xxrs = xxrs;
	}

}
