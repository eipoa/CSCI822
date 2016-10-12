package com.bugtrack.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;
import com.baidu.ueditor.ActionEnter;

/** 
 * upload file controller for baidu Ueditor
 * @author Baoxing Li
 */  
@RestController  
@RequestMapping(value="/Upload")  
public class UploadController extends CommonController{ 
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @RequestMapping(value="init") 
    public void config(HttpServletRequest request,  HttpServletResponse response, String action) {
        response.setContentType("application/json");   
        // read properties
        String rootPath = btProperty.getUeditor().getRootpath();
        String confPath = btProperty.getUeditor().getConfigpath();
        logger.info("--------------------------------------- rootPath = " + rootPath);
        logger.info("--------------------------------------- confPath = " + confPath);
        //ServletContext.getResource();
        try {
                String exec = new ActionEnter(request, rootPath, confPath).exec();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
    }
}