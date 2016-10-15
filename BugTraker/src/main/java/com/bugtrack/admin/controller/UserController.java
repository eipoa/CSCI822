/**
 * 
 */
package com.bugtrack.admin.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.admin.model.RoleModel;
import com.bugtrack.admin.model.UserModel;
import com.bugtrack.admin.util.PageContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Auth")
public class UserController extends CommonController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * the main view of users
	 * 
	 * @return view of all user
	 */
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public ModelAndView userIndex() {
		ModelAndView mv = new ModelAndView("Auth/users");
		mv.addObject("fullname", this.getFullname());
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
//		double x = 100.1/0;
//		x = x + 100;
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
		Map<String, Object> map = userRepo.findAll(keywords, pageable);

		// debug
		logger.debug("------------------ /Auth/user/list");
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		logger.debug(jsonString);

		return map;
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "user/save", method = RequestMethod.POST)
	public String addUser(HttpServletRequest request, UserModel user,
			@RequestParam(value = "roleids", required = false) String roleids) throws Exception {
//		try {
			UserModel tmpUser = userRepo.findByUsername(user.getUsername());
			String[] ids=null;
			if(roleids!=null && !roleids.equals(""))
				ids = roleids.split(",");
			Set<RoleModel> roles01= new HashSet<RoleModel>();
			if (tmpUser == null) {
				// new user / set password=1
				user.setPassword(new Md5PasswordEncoder().encodePassword("1", null));
				user.setCreate_ts(getCurrentTime());
				user.setLogin_ts(null);
			} else {
				user.setPassword(tmpUser.getPassword());
				user.setCreate_ts(tmpUser.getCreate_ts());
				user.setLogin_ts(tmpUser.getLogin_ts());
				Collection<RoleModel> roles = tmpUser.getRoles();
				if(roles!=null){
					for (RoleModel r : roles) {
						if(r.getRolename().equals("ROLE_ADMIN")){
							roles01.add(r);
						}
					}
				}
			}
			
			//roles.clear();
			if(ids!=null)
				for(int i=0; i<ids.length;++i){
					roles01.add(roleRepo.findOne(Integer.valueOf(ids[i])));
				}
			user.setRoles(roles01);
			user = userRepo.saveAndFlush(user);
			return ajaxReturn(true, Integer.toString(user.getId()), "OK");
//		} catch (Exception e) {
//			return ajaxReturn(false, "0", e.getMessage());
//		}
	}

	@Transactional(readOnly=false)
	@RequestMapping(value = "user/delete", method = RequestMethod.DELETE)
	public String delUser(HttpServletRequest request, @RequestParam(value = "id", required = true) Integer id)
			throws Exception {
		try {
			UserModel user = userRepo.findById(id);
			if (user == null)
				return ajaxReturn(false, "", "can not find the user!");
			if (user.isAdmin())
				return ajaxReturn(false, "", "can not remove an administrator!");
			logger.debug("----------------------- delete " + user.toString());
			//user.setRole(null);
			//userRepo.delete(user);
			user.setStatus(100);
			userRepo.saveAndFlush(user);
			return ajaxReturn(true, "", "OK");
		} catch (Exception e) {
			return ajaxReturn(false, "", e.getMessage());
		}
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "user/status", method = RequestMethod.PUT)
	public String userSwitchStatus(HttpServletRequest request, @RequestParam(value = "id", required = true) Integer id)
			throws Exception {
		try {
			UserModel user = userRepo.findById(id);
			if (user == null)
				return ajaxReturn(false, "", "can not find the user!");
			if (user.isAdmin())
				return ajaxReturn(false, "", "can not freeze an administrator!");
			if (user.getStatus() == 1)
				user.setStatus(0);
			else
				user.setStatus(1);
			user = userRepo.saveAndFlush(user);
//			logger.info("-----------------------status " + Integer.toString(user.getStatus()));
			return ajaxReturn(true, Integer.toString(user.getStatus()), "OK");
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
		return userRepo.findOne(id);
	}
}
