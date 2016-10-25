package com.bugtrack.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bugtrack.common.CommonController;
import com.bugtrack.model.BugClassModel;
import com.bugtrack.model.BugPriorityModel;
import com.bugtrack.model.BugSeverityModel;
import com.bugtrack.model.BugStatusModel;
import com.bugtrack.model.BugsModel;
import com.bugtrack.model.ProductModel;
import com.bugtrack.model.ProductNameModel;
import com.bugtrack.model.ProductOsModel;
import com.bugtrack.model.ResourceModel;
import com.bugtrack.model.RoleModel;
import com.bugtrack.model.SysNoteModel;
import com.bugtrack.model.SysReputationModel;
import com.bugtrack.model.UserModel;
import com.bugtrack.util.PageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/")
public class WebRestController extends CommonController {
	// the first level index!!!!!!!!!!!
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index(@RequestParam(value = "page", required = true, defaultValue = "0") Integer page,
			@RequestParam(value = "rows", required = true, defaultValue = "50") Integer rows,
			@RequestParam(value = "idx", required = true, defaultValue = "1") Integer idx,
			@RequestParam(value = "pn", required = false) Integer pn) {
		ModelAndView mv = new ModelAndView("index");
		if (this.isLogin()) {
			Integer num = this.getCountTask();
			if (num.intValue() > 0)
				mv.addObject("tasks", this.getCountTask());
			mv.addObject("fullname", this.getFullname());
		}
		mv.addObject("productList", this.productNameRepo.findProductList());
		List<UserModel> topuse = userRepo.findTop10ByOrderByReputationDesc();
		mv.addObject("topusers", topuse);
		List<SysNoteModel> note = sysnRepo.findTop3ByExpireGreaterThan(getCurrentTime());
		if (note != null)
			mv.addObject("news", note);
		List<Order> orders = new ArrayList<Order>();
		if (idx == 1 || idx == 3) {
			orders.add(new Order(Direction.DESC, "creationts"));
		} else if (idx == 2) {
			orders.add(new Order(Direction.DESC, "vote"));
		}
		Sort sort = new Sort(orders);
		page = page - 1 < 0 ? 0 : page - 1;
		Pageable pageable = new PageRequest(page, rows, sort);
		Page<BugsModel> bugList01 = null;

		if (idx == 1 || idx == 2)
			bugList01 = bugRepo.findAll(pageable);
		else
			bugList01 = bugRepo.findAllByProductIn(productRepo.findAllByName(productNameRepo.findOne(pn)), pageable);

		if (bugList01 != null && bugList01.getTotalPages() > 0)
			mv.addObject("pages", bugList01);
		mv.addObject("idx", idx);
		mv.addObject("pn", pn);
		return mv;
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
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "ver", required = false) String ver) {
		List<ProductOsModel> ret = this.productOsRepo.findAll();
		return ret;
	}

	@RequestMapping(value = "Rest/oslistbyproduct01", method = RequestMethod.GET)
	public List<ProductOsModel> getOsList002(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "ver", required = false) String ver) {
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
		return productNameRepo.findProductList();
	}

	@RequestMapping(value = "Rest/productnamelistFull", method = RequestMethod.GET)
	public List<ProductNameModel> getNameListFull() {
		return productNameRepo.findAll();
	}

	@RequestMapping(value = "Rest/productlist", method = RequestMethod.GET)
	public List<ProductModel> getProductList() {
		return productRepo.findAll();
	}

	@RequestMapping(value = "Rest/bugseveritylist", method = RequestMethod.GET)
	public List<BugSeverityModel> getSeverityList() {
		return sevRepo.findAll();
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

	@RequestMapping(value = "Rest/sysnotelist", method = RequestMethod.GET)
	public List<SysNoteModel> getSysNoteList(HttpServletRequest request) {
		return sysnRepo.findAllByOrderByExpireDesc();
	}
	
	@RequestMapping(value = "Rest/sysrepulist", method = RequestMethod.GET)
	public List<SysReputationModel> getSysRepuList(HttpServletRequest request) {
		return sysrRepo.findAll();
	}

	//
	// @RequestMapping(value = "Public/regist", method =
	// {RequestMethod.POST})//, method = RequestMethod.GET
	// public String regist(HttpServletRequest req, HttpServletResponse rep,
	// UserModel user) throws Exception {
	// user.setLast_name("");
	// user.setRoles(this.roleRepo.findAllById(6));
	// user.setPassword(new
	// Md5PasswordEncoder().encodePassword(user.getPassword(), null));
	// user.setUsername(user.getEmail());
	// user.setCreate_ts(this.getCurrentTime());
	// user = userRepo.saveAndFlush(user);
	// return ajaxReturn(true, user.getId().toString(), "Sign up successfully");
	// }
	//
	// @RequestMapping(value = "login", method = RequestMethod.GET)
	// public ModelAndView login(HttpServletRequest req, HttpServletResponse
	// rep) throws IOException {
	// if (!isLogin()) {
	// ModelAndView mv = new ModelAndView("Public/login");
	// return mv;
	// } else {
	// rep.sendRedirect("/");
	// return null;
	// }
	// }
	//
	// @RequestMapping(value = "signup", method = RequestMethod.GET)
	// public ModelAndView signup(HttpServletRequest req, HttpServletResponse
	// rep) throws IOException {
	// if (!isLogin()) {
	// ModelAndView mv = new ModelAndView("Public/signup");
	// return mv;
	// } else {
	// rep.sendRedirect("/");
	// return null;
	// }
	// }
	//
	// @Transactional(readOnly = false)
	// @RequestMapping(value = "chpwd", method = RequestMethod.PUT)
	// public String changePassword(
	// @RequestParam(value = "oldpw", required = true) String oldpwd,
	// @RequestParam(value = "newpw", required = true) String newpwd) throws
	// Exception{
	// try {
	// UserModel user = userRepo.findById(this.getUserId());
	// oldpwd = new Md5PasswordEncoder().encodePassword(oldpwd, null);
	// if(!user.getPassword().equals(oldpwd)){
	// return ajaxReturn(false, "1", "the old password is not correct");
	// }
	// newpwd = new Md5PasswordEncoder().encodePassword(newpwd, null);
	// user.setPassword(newpwd);
	// userRepo.saveAndFlush(user);
	// return ajaxReturn(true, "1", "OK");
	// }catch (Exception e){
	// return ajaxReturn(false, "1", "//////////////");//e.getMessage());
	// }
	// }
}
