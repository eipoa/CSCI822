/**
 * 
 */
package com.springboot.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Administrator
 *
 */
@RestController
public class CommonController {

	public String ajaxReturn(boolean statue, String data, String info) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (statue) {
			if(info==null || info.equals("")) info="modift successfully.";
			if(data==null) data="";
			map.put("status", true);
			map.put("info", info);
			map.put("data", data);
		} else {
			if(info==null || info.equals("")) info="modift unsuccessfully.";
			if(data==null) data="";
			map.put("status", false);
			map.put("info", info);
			map.put("data", data);
		}
		
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		return jsonString;
	}
}
