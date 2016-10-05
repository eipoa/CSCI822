/**
 * 
 */
package com.bugtrack.admin.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrack.admin.dao.BugClassRepository;
import com.bugtrack.admin.dao.BugPriorityRepository;
import com.bugtrack.admin.dao.BugRepository;
import com.bugtrack.admin.dao.BugStatusRepository;
import com.bugtrack.admin.dao.MessageClassRepository;
import com.bugtrack.admin.dao.ProductNameRepository;
import com.bugtrack.admin.dao.ProductOsRepository;
import com.bugtrack.admin.dao.ProductRepository;
import com.bugtrack.admin.dao.RoleRepository;
import com.bugtrack.admin.dao.UserRepository;
import com.bugtrack.admin.model.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@RestController
public class CommonController {
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	BugRepository bugRepo;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	BugStatusRepository bugstatusRepo;
	
	@Autowired
	BugClassRepository bugclassRepo;
	
	@Autowired
	BugPriorityRepository bugpriorityRepo;	
	
	@Autowired
	ProductRepository productRepo;	
	
	@Autowired
	ProductNameRepository productNameRepo;	
	
	@Autowired
	ProductOsRepository productOsRepo;
	
	@Autowired
	MessageClassRepository msgRepo;
	
	public String ajaxReturn(boolean statue, String data, String info) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (statue) {
			if (info == null || info.equals(""))
				info = "modift successfully.";
			if (data == null)
				data = "";
			map.put("status", true);
			map.put("info", info);
			map.put("data", data);
		} else {
			if (info == null || info.equals(""))
				info = "modift unsuccessfully.";
			if (data == null)
				data = "";
			map.put("status", false);
			map.put("info", info);
			map.put("data", data);
		}

		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		return jsonString;
	}

	public String getFullname() {
		UserDetails userDetails = (UserDetails) request.getSession(false).getAttribute("USER_STATE");
		String username = userDetails.getUsername();
		UserModel user = userRepo.findByUsername(username);
		return user.getFirst_name() + " " + user.getLast_name();
	}
	
	public Integer getUserId(){
		UserDetails userDetails = (UserDetails) request.getSession(false).getAttribute("USER_STATE");
		String username = userDetails.getUsername();
		UserModel user = userRepo.findByUsername(username);
		return user.getId();
	}
	
	public UserModel getUser(){
		UserDetails userDetails = (UserDetails) request.getSession(false).getAttribute("USER_STATE");
		String username = userDetails.getUsername();
		UserModel user = userRepo.findByUsername(username);
		return user;
	}
	
	public String getCurrentTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return df.format(now);
	}
	
	public Integer getCountTask(){
		//return msgRepo.findAllByReceiverAndReadtsIsNull(getUser()).size();
		return msgRepo.findAllByReceiverAndStatus(getUser(), 0).size();
	}
}
