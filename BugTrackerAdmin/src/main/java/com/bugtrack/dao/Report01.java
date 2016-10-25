package com.bugtrack.dao;

public class Report01 {
	private String pname;
	private Integer  count;

	public Report01(String pname, Integer  cnt) {
		this.pname = pname;
		this.count = cnt;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer  getCount() {
		return count;
	}

	public void setCount(Integer  count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Report01 [pname=" + pname + ", count=" + count + "]";
	}
	
	
}
