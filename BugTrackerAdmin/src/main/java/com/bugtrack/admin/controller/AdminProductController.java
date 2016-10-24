/**
 * 
 */
package com.bugtrack.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bugtrack.common.CommonController;
import com.bugtrack.model.ProductModel;
import com.bugtrack.model.ProductNameModel;
import com.bugtrack.model.ProductOsModel;

/**
 * @author Administrator
 *
 */
@RequestMapping("/Admin/Product")
@RestController
public class AdminProductController extends CommonController {
	// maintain
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String pro_os_save(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "txt", required = false) String txt,
			@RequestParam(value = "nid", required = false) Integer pid,
			@RequestParam(value = "oid", required = false) Integer oid,
			@RequestParam(value = "tp", required = false) String tp) throws Exception {
		if(tp.trim().equals("1")){
			ProductModel product = new ProductModel();
			product.setName(productNameRepo.findOne(pid));
			product.setOs(productOsRepo.findOne(oid));
			product.setVersion(txt);
			product = productRepo.saveAndFlush(product);
		}else if(tp.trim().equals("2")){
			ProductNameModel prodname = new ProductNameModel();
			prodname.setName(txt);
			prodname = productNameRepo.saveAndFlush(prodname);
		}else if(tp.trim().equals("3")){
			ProductOsModel oname = new ProductOsModel();
			oname.setOsname(txt);
			oname = productOsRepo.saveAndFlush(oname);
		}else
			throw new Exception("invalid parameters");
		return ajaxReturn(true, "", "OK");
	}
	
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String pro_os_Modify(HttpServletRequest request,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "txt", required = false) String txt,
			@RequestParam(value = "nid", required = false) Integer pid,
			@RequestParam(value = "oid", required = false) Integer oid,
			@RequestParam(value = "tp", required = false) String tp) throws Exception {
		if(tp.trim().equals("1")){
			ProductModel product = productRepo.findOne(id);
			product.setName(productNameRepo.findOne(pid));
			product.setOs(productOsRepo.findOne(oid));
			product.setVersion(txt);
			product = productRepo.saveAndFlush(product);
		}else if(tp.trim().equals("2")){
			ProductNameModel prodname = productNameRepo.findOne(id);
			prodname.setName(txt);
			prodname = productNameRepo.saveAndFlush(prodname);
		}else if(tp.trim().equals("3")){
			ProductOsModel oname = productOsRepo.findOne(id);
			oname.setOsname(txt);
			oname = productOsRepo.saveAndFlush(oname);
		}else
			throw new Exception("invalid parameters");
		return ajaxReturn(true, "", "OK");
	}
	
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
	
}
