/**
 * 
 */
package com.bugtrack.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrack.admin.dao.ProductNameRepository;
import com.bugtrack.admin.dao.ProductOsRepository;
import com.bugtrack.admin.dao.ProductRepository;
import com.bugtrack.admin.model.ProductOsModel;
import com.bugtrack.admin.model.ProductModel;
import com.bugtrack.admin.model.ProductNameModel;

/**
 * @author Administrator
 *
 */
@RequestMapping("/Product")
@RestController
public class ProductController extends CommonController {
	@Autowired
	ProductRepository productRepo;
	@RequestMapping(value = "proc", method = RequestMethod.GET)
	public ProductModel getProductById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return productRepo.findOne(id);
	}

	@RequestMapping(value = "verlist", method = RequestMethod.GET)
	public List<Map<String, String> > getVerList(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		List<ProductModel> product = productRepo.findByName(this.getNameById(request, id));
		Map<String, String> x= new HashMap<String, String>();
		List<Map<String, String> > ret = new ArrayList<Map<String, String> >();
		for(ProductModel tmp:product) {
			if(!x.containsKey(tmp.getVersion())){
				x.put(tmp.getVersion(), tmp.getVersion());
				Map<String, String> y= new HashMap<String, String>();
				y.put("text", tmp.getVersion());
				y.put("value", tmp.getVersion());
				ret.add(y);
			}
        }
		return ret;
	}
	@RequestMapping(value = "oslist001", method = RequestMethod.GET)
	public List<ProductOsModel> getOsList001(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "ver", required = true) String ver) {
		List<ProductModel> product = productRepo.findByNameAndVersion(this.getNameById(request, id), ver);
		Map<Integer, String> x= new HashMap<Integer, String>();
		List<ProductOsModel> ret = new ArrayList<ProductOsModel>();
		for(ProductModel tmp:product) {
			ProductOsModel os = tmp.getOs();
			if(!x.containsKey(os.getOsname())){
				x.put(os.getId(), os.getOsname());
				ret.add(os);
			}
        }
		return ret;
	}
	

	@Autowired
	ProductOsRepository osRepo;
	@RequestMapping(value = "os", method = RequestMethod.GET)
	public ProductOsModel getOsById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return osRepo.findOne(id);
	}
	
	@RequestMapping(value = "oslist", method = RequestMethod.GET)
	public List<ProductOsModel> getOsList() {
		return osRepo.findAll();
	}
	
	@Autowired
	ProductNameRepository nameRepo;
	@RequestMapping(value = "name", method = RequestMethod.GET)
	public ProductNameModel getNameById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return nameRepo.findOne(id);
	}
	@RequestMapping(value = "namelist", method = RequestMethod.GET)
	public List<ProductNameModel> getNameList() {
		// maybe some names aren't released
		// it is in name table, but not in product table
		return nameRepo.findAll();
	}

	@Autowired
	HttpServletRequest request;

}
