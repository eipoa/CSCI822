/**
 * 
 */
package com.springboot.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.demo.dao.UserRepository;
import com.springboot.demo.model.UserModel;
/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/Auth")
public class UserController {
	@Autowired UserRepository repo;
	
	/**
	 * 
	 * @return view of all user
	 */
	@RequestMapping(value="users", method=RequestMethod.GET)
	public ModelAndView userIndex(){
		ModelAndView mv = new ModelAndView("Auth/users");    
		return mv;
	}

	@RequestMapping(value="rights", method=RequestMethod.GET)
	public ModelAndView rightIndex(){
		ModelAndView mv = new ModelAndView("Auth/rights");    
		return mv;
	}
	/**
	 * 
	 * @return json data of user list
	 */
	@RequestMapping(value="user", method=RequestMethod.GET)
	public List<UserModel> listUsers(){
		return repo.findAll();
	}
	
	/**
	 * 
	 * @param id
	 * @return	json data of one user
	 */
	@RequestMapping(value="user/{id}", method=RequestMethod.GET)
	public UserModel getByID(@PathVariable int id){
		return repo.findOne(id);
	}
}
