/**
 * 
 */
package com.bugtrack.app.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrack.app.dao.BugClassRepository;
import com.bugtrack.app.dao.BugPatchRepository;
import com.bugtrack.app.dao.BugPriorityRepository;
import com.bugtrack.app.dao.BugRepository;
import com.bugtrack.app.dao.BugStatusRepository;
import com.bugtrack.app.dao.MessageClassRepository;
import com.bugtrack.app.dao.ProductNameRepository;
import com.bugtrack.app.dao.ProductOsRepository;
import com.bugtrack.app.dao.ProductRepository;
import com.bugtrack.app.dao.ResourceRepository;
import com.bugtrack.app.dao.RoleRepository;
import com.bugtrack.app.dao.UserRepository;
import com.bugtrack.app.model.UserModel;
import com.bugtrack.app.util.BugTrackerProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@RestController
public class CommonController{
	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	ResourceRepository resRepo;
	
	@Autowired
	BugRepository bugRepo;

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

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

	@Autowired
	BugPatchRepository patchRepo;

	@Autowired
	BugTrackerProperty btProperty;

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

	public Integer getUserId() {
		UserDetails userDetails = (UserDetails) request.getSession(false).getAttribute("USER_STATE");
		String username = userDetails.getUsername();
		UserModel user = userRepo.findByUsername(username);
		return user.getId();
	}

	public UserModel getUser() {
		UserDetails userDetails = (UserDetails) request.getSession(false).getAttribute("USER_STATE");
		String username = userDetails.getUsername();
		UserModel user = userRepo.findByUsername(username);
		return user;
	}

	public String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return df.format(now);
	}

	public Integer getCountTask() {
		// return msgRepo.findAllByReceiverAndReadtsIsNull(getUser()).size();
		return msgRepo.findAllByReceiverAndStatus(getUser(), 0).size();
	}

	public String getRandom(int num) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append(String.valueOf(random.nextInt(10)));
		}
		return sb.toString();
	}

	public String getExt(String name) {
		if (name == null || "".equals(name) || !name.contains("."))
			return "";
		return name.substring(name.lastIndexOf(".") + 1);
	}
	
	@Autowired
	private HttpSession session;

	/**
	 * check ajax or normal
	 * 
	 * @param request
	 * @return
	 */
	public boolean isAjax() {
		Map<String, String> headermap = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			headermap.put(key, value);
		}
		if ((request.getParameter("isAjax") != null && request.getParameter("isAjax").equals("1"))
				|| (request.getHeader("x-requested-with") != null
						&& request.getHeader("x-requested-with").toLowerCase().equals("xmlhttprequest"))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLogin() {
		if (session.getAttribute("USER_STATE") != null) {
			UserDetails userDetails = (UserDetails) session.getAttribute("USER_STATE");
			if (userDetails != null) {
				return true;
			}else{
				return false;
			}
		} else {
			return false;
		}
	}
	public Collection<String> getMenus(){
		// get menu based on role
		return null;
	}
}
