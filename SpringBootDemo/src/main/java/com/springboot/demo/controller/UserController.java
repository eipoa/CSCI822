/**
 * 
 */
package com.springboot.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.demo.dao.UserRepository;
import com.springboot.demo.model.UserModel;
import com.springboot.demo.util.PageContent;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Auth")
public class UserController extends CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository repo;

	/**
	 * the main view of users
	 * 
	 * @return view of all user
	 */
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public ModelAndView userIndex() {
		ModelAndView mv = new ModelAndView("Auth/users");
		return mv;
	}

	/**
	 * datagrid of users
	 * 
	 * @param request
	 * @param page
	 * @return
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "user/list", method = RequestMethod.GET)
	public Map<String, Object> userList(HttpServletRequest request, PageContent page,
			@RequestParam(value = "role", required = false) String role,
			@RequestParam(value = "username", required = false) String username) throws JsonProcessingException {
		// keyword
		Map<String, String> keywords = new HashMap<String, String>();
		if (role != null && !role.trim().equals(""))
			keywords.put("role", role);
		if (username != null && !username.trim().equals(""))
			keywords.put("username", username);

		// page
		Sort sort = null;
		if (page.getOrder().equals("asc")) {
			sort = new Sort(Direction.ASC, page.getSort());
		} else if (page.getOrder().equals("desc")) {
			sort = new Sort(Direction.DESC, page.getSort());
		}
		int pageNum = Integer.parseInt(page.getPage()) - 1;
		int rows = Integer.parseInt(page.getRows());
		Pageable pageable = new PageRequest(pageNum, rows, sort);

		// result for easyui
		/*
		 * // query List<UserModel> list= null; Page<UserModel> list =
		 * repo.findAll(pageable); Map<String, Object> map = new HashMap<String,
		 * Object>(); map.put("total", list.getTotalElements());// count of data
		 * map.put("rows", list.getContent());// data of this page
		 */
		Map<String, Object> map = repo.findAll(keywords, pageable);

		// debug
		logger.debug("------------------ /Auth/user/list");
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		logger.debug(jsonString);

		return map;
	}

	@Transactional
	@RequestMapping(value = "user/status", method = RequestMethod.PUT)
	public String userSwitchStatus(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
			throws JsonProcessingException {
		try {
			UserModel user = repo.findById(id);
			if (user == null)
				return ajaxReturn(false, "", "can not find the user!");
			if (user.getStatus() == 1)
				user.setStatus(0);
			else
				user.setStatus(1);
			user= repo.saveAndFlush(user);
			logger.info("-----------------------"+Integer.toString(user.getStatus()));
			return ajaxReturn(true, Integer.toString(user.getStatus()), "");
		} catch (Exception e) {
			return ajaxReturn(false, "", e.getMessage());
		}
	}

	/**
	 * 
	 * @param id
	 * @return json data of one user
	 */
	@RequestMapping(value = "user/{id}", method = RequestMethod.GET)
	public UserModel getByID(@PathVariable int id) {
		return repo.findOne(id);
	}
}
