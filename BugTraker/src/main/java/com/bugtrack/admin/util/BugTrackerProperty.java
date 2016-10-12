package com.bugtrack.admin.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bugtrack") 
public class BugTrackerProperty {
	public static class Ueditor {  
	    private String rootpath;
	    private String configpath;
		public String getRootpath() {
			return rootpath;
		}
		public void setRootpath(String rootpath) {
			this.rootpath = rootpath;
		}
		public String getConfigpath() {
			return configpath;
		}
		public void setConfigpath(String configpath) {
			this.configpath = configpath;
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
