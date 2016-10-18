/**
 * 
 */
package com.bugtrack.admin.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.admin.dao.RoleRepository;
import com.bugtrack.admin.model.ResourceModel;
import com.bugtrack.admin.model.RoleModel;
import com.bugtrack.admin.util.PageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Auth")
public class RoleController extends CommonController {

	@Autowired
	RoleRepository repo;

	/**
	 * the main view of users
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "roles", method = RequestMethod.GET)
	public ModelAndView roleIndex() throws Exception {
		ModelAndView mv = new ModelAndView("Auth/roles");
		Integer num = this.getCountTask();
		if (num.intValue() > 0)
			mv.addObject("tasks", this.getCountTask());
		mv.addObject("fullname", this.getFullname());
		return mv;
	}

	/**
	 * 
	 * @return json data of role name list for listbox or combox
	 */
	@RequestMapping(value = "roles/listname", method = RequestMethod.GET)
	public List<RoleModel> listRoleName() {
		List<RoleModel> result = repo.findAll();
		return result;
	}

	@RequestMapping(value = "roles/list", method = RequestMethod.GET)
	public String roleList(HttpServletRequest request, PageContent page) throws Exception {
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

		Map<String, Object> map = new HashMap<String, Object>();
		List<RoleModel> roles = roleRepo.findAll();
		if (roles != null) {
			map.put("total", roles.size());
			roles = roleRepo.findAll(pageable).getContent();
			// for (RoleModel r : roles) {
			// Collection<ResourceModel> res = r.getResources();
			// r.setResources(res);
			// }
			map.put("rows", roles);
		} else {
			map.put("total", 0);
			map.put("rows", roles);
		}
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		return jsonString;
	}

	/* for user admin */
	@RequestMapping(value = "roles/list01", method = RequestMethod.GET)
	public String roleList01(HttpServletRequest request, PageContent page) throws Exception {
		Sort sort = null;
		if (page.getOrder().equals("asc")) {
			sort = new Sort(Direction.ASC, page.getSort());
		} else if (page.getOrder().equals("desc")) {
			sort = new Sort(Direction.DESC, page.getSort());
		}
		List<RoleModel> ress = roleRepo.findAllByStatus(1, sort);
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(ress);
		return jsonString;
	}

	@RequestMapping(value = "roles/save", method = RequestMethod.POST)
	public String roleModify(HttpServletRequest request, RoleModel role,
			@RequestParam(value = "resids", required = false) String resids) throws Exception {
		String[] ids = null;
		if (resids != null && !resids.equals(""))
			ids = resids.split(",");
		Set<ResourceModel> res = new HashSet<ResourceModel>();
		for (int i = 0; i < ids.length; ++i) {
			res.add(resRepo.findOne(Integer.valueOf(ids[i])));
		}
		// some resource mist be added
		res.add(resRepo.findByResource("/"));
		role.setResources(res);
		role = roleRepo.saveAndFlush(role);
		return ajaxReturn(true, Integer.toString(role.getId()), "OK");
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "roles/status", method = RequestMethod.PUT)
	public String roleSwitchStatus(HttpServletRequest request, @RequestParam(value = "id", required = true) Integer id)
			throws Exception {
		try {
			RoleModel role = roleRepo.findOne(id);
			if (role == null)
				return ajaxReturn(false, "", "can not find the role!");
			if (role.getStatus() == 1)
				role.setStatus(0);
			else
				role.setStatus(1);
			role = roleRepo.saveAndFlush(role);
			return ajaxReturn(true, Integer.toString(role.getStatus()), "OK");
		} catch (Exception e) {
			return ajaxReturn(false, "", e.getMessage());
		}
	}
}
