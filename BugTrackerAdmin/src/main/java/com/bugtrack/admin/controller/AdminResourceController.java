/**
 * 
 */
package com.bugtrack.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.bugtrack.common.CommonController;
import com.bugtrack.model.ResourceModel;
import com.bugtrack.util.PageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Admin/Auth")
public class AdminResourceController extends CommonController {
	@RequestMapping(value = "resources", method = RequestMethod.GET)
	public ModelAndView roleIndex() throws Exception {
		ModelAndView mv = new ModelAndView("Admin/Auth/resources");
		if(isLogin()){
			Integer num = this.getCountTask();
			if (num.intValue() > 0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		return mv;
	}

	// ?? for roles?
	@RequestMapping(value = "resources/list", method = RequestMethod.GET)
	public String resList(HttpServletRequest request, PageContent page) throws Exception {
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
		List<ResourceModel> ress = resRepo.findAll();
		if (ress != null) {
			map.put("total", ress.size());
			ress = resRepo.findAll(pageable).getContent();
			map.put("rows", ress);
		} else {
			map.put("total", 0);
			map.put("rows", ress);
		}
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		return jsonString;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "resources/save", method = RequestMethod.POST)
	public String addResources(HttpServletRequest request, ResourceModel res,
			@RequestParam(value = "parentid", required = true) Integer pid) throws Exception {
		if(res.getResource()==null || res.getResource().trim().equals("")){
			throw new Exception("the resource must have a value");
		}
		if(pid==null || pid.equals(-1)){
			res.setParent(null);
		}else{
			res.setParent(resRepo.findOne(pid));
		}
		
		if (res.getId()==null) {
			// new res
			res.setId(null);
			res.setRoles(null);
		}
		res.setResource(res.getResource().trim());
		if(res.getParent().getTp()!=null)
			res.setTp(res.getParent().getTp()+1);
		else
			res.setTp(1);
		res = resRepo.saveAndFlush(res);
		return ajaxReturn(true, Integer.toString(res.getId()), "OK");
	}

	@Transactional(readOnly = false)
	@RequestMapping(value = "resources/status", method = RequestMethod.PUT)
	public String resourcesSwitchStatus(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) throws Exception {
		ResourceModel res = resRepo.findById(id);
		if (res == null)
			return ajaxReturn(false, "", "can not find the resources!");
		if (res.getStatus() == 1)
			res.setStatus(0);
		else
			res.setStatus(1);
		res = resRepo.saveAndFlush(res);
		return ajaxReturn(true, Integer.toString(res.getStatus()), "OK");
	}
}
