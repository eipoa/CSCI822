/**
 * 
 */
package com.bugtrack.common;

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

import com.bugtrack.dao.BugClassRepository;
import com.bugtrack.dao.BugCommentRepository;
import com.bugtrack.dao.BugPatchRepository;
import com.bugtrack.dao.BugPriorityRepository;
import com.bugtrack.dao.BugRepository;
import com.bugtrack.dao.BugSeverityRepository;
import com.bugtrack.dao.BugStatusRepository;
import com.bugtrack.dao.MessageClassRepository;
import com.bugtrack.dao.ProductNameRepository;
import com.bugtrack.dao.ProductOsRepository;
import com.bugtrack.dao.ProductRepository;
import com.bugtrack.dao.ResourceRepository;
import com.bugtrack.dao.RoleRepository;
import com.bugtrack.dao.SysNoteRepository;
import com.bugtrack.dao.SysReputationRepository;
import com.bugtrack.dao.UserRepository;
import com.bugtrack.model.UserModel;
import com.bugtrack.util.BugTrackerProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@RestController
public class CommonController{
	@Autowired
	protected
	UserRepository userRepo;

	@Autowired
	protected
	RoleRepository roleRepo;
	
	@Autowired
	protected
	ResourceRepository resRepo;
	
	@Autowired
	protected
	BugRepository bugRepo;

	@Autowired
	protected
	HttpServletRequest request;

	@Autowired
	protected
	HttpServletResponse response;

	@Autowired
	protected
	BugStatusRepository bugstatusRepo;

	@Autowired
	protected
	BugClassRepository bugclassRepo;

	@Autowired
	protected
	BugPriorityRepository bugpriorityRepo;

	@Autowired
	protected
	ProductRepository productRepo;

	@Autowired
	protected
	ProductNameRepository productNameRepo;

	@Autowired
	protected
	ProductOsRepository productOsRepo;

	@Autowired
	protected
	MessageClassRepository msgRepo;

	@Autowired
	protected
	BugPatchRepository patchRepo;

	@Autowired
	protected
	BugTrackerProperty btProperty;
	
	@Autowired
	protected
	BugCommentRepository bugcommentRepo;
	
	@Autowired
	protected
	BugSeverityRepository sevRepo;

	@Autowired
	protected
	SysNoteRepository sysnRepo;
	
	@Autowired
	protected
	SysReputationRepository sysrRepo;
	
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
