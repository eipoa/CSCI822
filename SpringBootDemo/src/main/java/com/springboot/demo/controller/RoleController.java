/**
 * 
 */
package com.springboot.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.demo.dao.RoleRepository;
import com.springboot.demo.model.RoleModel;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Auth")
public class RoleController {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RoleRepository repo;
	/**
	 * the main view of users
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "roles", method = RequestMethod.GET)
	public ModelAndView roleIndex() throws Exception {
		ModelAndView mv = new ModelAndView("Auth/roles");
		return mv;
	}
	
	/**
	 * 
	 * @return json data of role name list for listbox or combox
	 */
	@RequestMapping(value = "listrolename", method = RequestMethod.GET)
	public List<RoleModel> listRoleName() {
		List<RoleModel> result= repo.findAll();
		result.add(0, new RoleModel(-1, "All", ""));
		return result;
	}
	
	
}
