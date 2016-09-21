/**
 * 
 */
package com.bugtrack.admin.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bugtrack.admin.dao.OsRepository;
import com.bugtrack.admin.dao.ProductRepository;
import com.bugtrack.admin.dao.VersionRepository;
import com.bugtrack.admin.model.OsModel;
import com.bugtrack.admin.model.ProductModel;
import com.bugtrack.admin.model.VersionModel;

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

	@Autowired
	OsRepository osRepo;
	@RequestMapping(value = "os", method = RequestMethod.GET)
	public OsModel getOsById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return osRepo.findOne(id);
	}
	
	@Autowired
	VersionRepository versionRepo;
	@RequestMapping(value = "ver", method = RequestMethod.GET)
	public VersionModel getVersionById(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		return versionRepo.findOne(id);
	}

	@Autowired
	HttpServletRequest request;

}
