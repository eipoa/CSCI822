/**
 * 
 */
package com.springboot.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.dao.UserRepository;
import com.springboot.demo.model.UserModel;

/**
 * @author Administrator
 *
 */
@RestController
public class CommonController {
	@Autowired
	UserRepository userRepo;

	@Autowired
	HttpServletRequest request;

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
}
