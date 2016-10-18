package com.bugtrack.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;
import com.bugtrack.model.BugClassModel;
import com.bugtrack.model.BugPriorityModel;
import com.bugtrack.model.BugStatusModel;
import com.bugtrack.model.ProductModel;
import com.bugtrack.model.ProductNameModel;
import com.bugtrack.model.ProductOsModel;
import com.bugtrack.model.ResourceModel;
import com.bugtrack.model.RoleModel;
import com.bugtrack.util.PageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/")
public class WebRestController extends CommonController {
	@RequestMapping(value="", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("index");
		if(this.isLogin()){
			Integer num = this.getCountTask();
			if(num.intValue()>0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		return mv;
	}
	
	@RequestMapping(value="Public/login", method=RequestMethod.GET)
	public void defaultLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		System.out.println(request.getHeader("method"));  
		Enumeration e = request.getHeaderNames();  
		while(e.hasMoreElements()){  
		    String name = (String)e.nextElement();  
		    String value = request.getHeader(name);  
		    System.out.println(name+":"+value);  
		}
		
		String path = "App/login";
		String ref = request.getHeader("referer").trim().toLowerCase();
		if(ref.indexOf("/admin")!=-1){
			path = "Admin/login";
		}else if(ref.indexOf("/app")!=-11){
			path = "App/login";
		}
		response.sendRedirect(path);
	}
	
	// all rest services
	/* for user admin */
	@RequestMapping(value = "Rest/roleslist", method = RequestMethod.GET)
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
	/**
	 * 
	 * @return json data of role name list for listbox or combox
	 */
	@RequestMapping(value = "Rest/rolesnamelist", method = RequestMethod.GET)
	public List<RoleModel> listRoleName() {
		List<RoleModel> result = roleRepo.findAll();
		return result;
	}
	
	// datagrid
	@RequestMapping(value = "Rest/reslist01", method = RequestMethod.GET)
	public String resList01(HttpServletRequest request, PageContent page) throws Exception {
		Sort sort = null;
		if (page.getOrder().equals("asc")) {
			sort = new Sort(Direction.ASC, page.getSort());
		} else if (page.getOrder().equals("desc")) {
			sort = new Sort(Direction.DESC, page.getSort());
		}
		List<ResourceModel> ress = resRepo.findAllByStatus(1, sort);
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(ress);
		return jsonString;
	}

	// treegrid
	@RequestMapping(value = "Rest/reslist", method = RequestMethod.GET)
	public String resList02(HttpServletRequest request) throws Exception {
		List<ResourceModel> ress = resRepo.findAllByParentIsNullAndStatus(1, new Sort(Direction.ASC, "resource"));
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(ress);
		return jsonString;
	}
	@RequestMapping(value = "Rest/product", method = RequestMethod.GET)
	public ProductModel getProductById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return productRepo.findOne(id);
	}

	@RequestMapping(value = "Rest/verlist", method = RequestMethod.GET)
	public List<Map<String, String>> getProductVerList(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		List<ProductModel> product = productRepo.findByName(this.getNameById(request, id));
		Map<String, String> x = new HashMap<String, String>();
		List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
		for (ProductModel tmp : product) {
			if (!x.containsKey(tmp.getVersion())) {
				x.put(tmp.getVersion(), tmp.getVersion());
				Map<String, String> y = new HashMap<String, String>();
				y.put("text", tmp.getVersion());
				y.put("value", tmp.getVersion());
				ret.add(y);
			}
		}
		return ret;
	}

	@RequestMapping(value = "Rest/os", method = RequestMethod.GET)
	public ProductOsModel getOsById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return productOsRepo.findOne(id);
	}

	@RequestMapping(value = "Rest/oslist", method = RequestMethod.GET)
	public List<ProductOsModel> getOsList() {
		return productOsRepo.findAll();
	}
	
	@RequestMapping(value = "Rest/oslistbyproduct", method = RequestMethod.GET)
	public List<ProductOsModel> getOsList001(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "ver", required = true) String ver) {
		List<ProductModel> product = productRepo.findByNameAndVersion(this.getNameById(request, id), ver);
		Map<Integer, String> x = new HashMap<Integer, String>();
		List<ProductOsModel> ret = new ArrayList<ProductOsModel>();
		for (ProductModel tmp : product) {
			ProductOsModel os = tmp.getOs();
			if (!x.containsKey(os.getOsname())) {
				x.put(os.getId(), os.getOsname());
				ret.add(os);
			}
		}
		return ret;
	}

	@RequestMapping(value = "Rest/productname", method = RequestMethod.GET)
	public ProductNameModel getNameById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return productNameRepo.findOne(id);
	}

	@RequestMapping(value = "Rest/productnamelist", method = RequestMethod.GET)
	public List<ProductNameModel> getNameList() {
		return productNameRepo.findAll();
	}

	@RequestMapping(value = "Rest/productlist", method = RequestMethod.GET)
	public List<ProductModel> getProductList() {
		return productRepo.findAll();
	}
	
	@RequestMapping(value = "Rest/bugstatus", method = RequestMethod.GET)
	public BugStatusModel getStatusById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return bugstatusRepo.findOne(id);
	}

	@RequestMapping(value = "Rest/bugstatuslist", method = RequestMethod.GET)
	public List<BugStatusModel> getBugStatusList(HttpServletRequest request,
			@RequestParam(value = "typeid", required = false) String typeid,
			@RequestParam(value = "sm", required = false) String sm) {
		if (typeid != null && !typeid.trim().equals("")) {
			return bugRepo.findStatusList(typeid, sm);
		} else
			return bugstatusRepo.findAll();
	}

	@RequestMapping(value = "Rest/bugclasslist", method = RequestMethod.GET)
	public List<BugClassModel> getBugClassList(HttpServletRequest request) {
		return bugclassRepo.findAllByOrderByIdAsc();
	}

	@RequestMapping(value = "Rest/bugprioritylist", method = RequestMethod.GET)
	public List<BugPriorityModel> getBugPriorityList(HttpServletRequest request) {
		return bugpriorityRepo.findAllByOrderByIdAsc();
	}
	
}
