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
	
//	@Transactional(readOnly = false)
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
