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

import com.bugtrack.admin.model.MessageModel;
import com.bugtrack.admin.util.PageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Message")
public class InboxController  extends CommonController {
	
	/**
	 * the main view of assign bugs
	 * @return template of /Message/inbox
	 */
	@RequestMapping(value="inbox", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("/Message/inbox");
		Integer num = this.getCountTask();
		if(num.intValue()>0)
			mv.addObject("tasks", this.getCountTask());
		mv.addObject("fullname", this.getFullname());
		return mv;
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String msgList(HttpServletRequest request, PageContent page) throws Exception {
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
		
		List<MessageModel> msgs = msgRepo.findAllByReceiver(this.getUser(), pageable);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", msgs.size());
		map.put("rows", msgs);
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		return jsonString;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "status", method = RequestMethod.PUT)
	public String msgSwitchStatus(HttpServletRequest request, @RequestParam(value = "id", required = true) Integer id)
			throws Exception {
		try {
			MessageModel msg = msgRepo.findOne(id);
			if (msg == null)
				return ajaxReturn(false, "", "can not find the message!");
			if (msg.getStatus().equals(1))
				msg.setStatus(0);
			else
				msg.setStatus(1);
			msg.setReadts(this.getCurrentTime());
			msg = msgRepo.saveAndFlush(msg);
			return ajaxReturn(true, Integer.toString(msg.getStatus()), "");
		} catch (Exception e) {
			return ajaxReturn(false, "", e.getMessage());
		}
	}
}
