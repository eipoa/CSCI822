/**
 * 
 */
package com.bugtrack.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrack.model.ProductModel;
import com.bugtrack.model.ProductNameModel;
import com.bugtrack.model.ProductOsModel;

/**
 * @author Administrator
 *
 */
@RequestMapping("/Admin/Product")
@RestController
public class AdminProductController extends AdminCommonController {
	@RequestMapping(value = "proc", method = RequestMethod.GET)
	public ProductModel getProductById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return productRepo.findOne(id);
	}

	@RequestMapping(value = "verlist", method = RequestMethod.GET)
	public List<Map<String, String>> getVerList(HttpServletRequest request,
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

	@RequestMapping(value = "oslist001", method = RequestMethod.GET)
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

	@RequestMapping(value = "os", method = RequestMethod.GET)
	public ProductOsModel getOsById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return productOsRepo.findOne(id);
	}

	@RequestMapping(value = "oslist", method = RequestMethod.GET)
	public List<ProductOsModel> getOsList() {
		return productOsRepo.findAll();
	}

	@RequestMapping(value = "name", method = RequestMethod.GET)
	public ProductNameModel getNameById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return productNameRepo.findOne(id);
	}

	@RequestMapping(value = "namelist", method = RequestMethod.GET)
	public List<ProductNameModel> getNameList() {
		// maybe some names aren't released
		// it is in name table, but not in product table
		return productNameRepo.findAll();
	}

	@RequestMapping(value = "plist", method = RequestMethod.GET)
	public List<ProductModel> getProductList() {
		return productRepo.findAll();
	}

	// maintain
	@Transactional(readOnly = false)
	@RequestMapping(value = "status", method = RequestMethod.PUT)
	public String roleSwitchStatus(HttpServletRequest request, @RequestParam(value = "id", required = true) Integer id)
			throws Exception {
		ProductModel prod = productRepo.findOne(id);
		if (prod == null)
			return ajaxReturn(false, "", "can not find the product!");
		if (prod.getStatus() == 1)
			prod.setStatus(0);
		else
			prod.setStatus(1);
		prod = productRepo.saveAndFlush(prod);
		return ajaxReturn(true, Integer.toString(prod.getStatus()), "OK");
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String pro_os_Modify(HttpServletRequest request, ProductModel product,
			@RequestParam(value = "pn", required = false) Integer pid,
			@RequestParam(value = "pname", required = false) String pn,
			@RequestParam(value = "on", required = false) Integer oid,
			@RequestParam(value = "oname", required = false) String on,
			@RequestParam(value = "tp", required = false) String tp) throws Exception {
		if(tp.trim().equals("1")){
			product.setName(productNameRepo.findOne(pid));
			product.setOs(productOsRepo.findOne(oid));
			product = productRepo.saveAndFlush(product);
		}else if(tp.trim().equals("2")){
			ProductNameModel prodname = null;
			if(pid!=null)
				prodname = productNameRepo.findOne(pid);
			else
				prodname = new ProductNameModel();
			prodname.setName(pn);
			prodname = productNameRepo.saveAndFlush(prodname);
		}else if(tp.trim().equals("3")){
			ProductOsModel oname = null;
			if(oid!=null)
				oname = productOsRepo.findOne(oid);
			else
				oname = new ProductOsModel();
			oname.setOsname(on);
			oname = productOsRepo.saveAndFlush(oname);
		}else
			throw new Exception("invalid parameters");
		return ajaxReturn(true, "", "OK");
	}
}
