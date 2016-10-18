package com.bugtrack.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {
	@Autowired
	private Environment env;
	@Autowired
	BugTrackerProperty btProperty;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("---------------------------------------- bugtrack.ueditor.rootpath = " + env.getProperty("bugtrack.ueditor.rootpath"));
		logger.info("---------------------------------------- bugtrack.ueditor.configure = " + env.getProperty("bugtrack.ueditor.configure"));
		logger.info("================================= get base path ========================================");
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		String rootPath = null;
		URL[] urls = ((URLClassLoader) cl).getURLs();
		if (urls.length > 0) {
			File file = new File(urls[0].getFile());
			rootPath = file.getParent();
			rootPath = rootPath.replace("\\", "/");
			logger.info("================================= " + rootPath);
			btProperty.getUeditor().setRootpath(rootPath);
		}
		logger.info("================================= get base path end ====================================");
		if (rootPath != null) {
			registry.addResourceHandler("/Upload/**").addResourceLocations("file:///" + rootPath + "/Upload/");
		}
		super.addResourceHandlers(registry);
	}
}
