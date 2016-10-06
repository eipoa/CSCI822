package com.bugtrack.admin.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bugtrack")  
public class BugTrackerProperty {
	public static class Ueditor {  
	    private String rootpath;
		public String getRootpath() {
			return rootpath;
		}
		public void setRootpath(String rootpath) {
			this.rootpath = rootpath;
		}  

	}
	private Ueditor ueditor;
	public Ueditor getUeditor() {
		return ueditor;
	}
	public void setUeditor(Ueditor ueditor) {
		this.ueditor = ueditor;
	}
	
}
