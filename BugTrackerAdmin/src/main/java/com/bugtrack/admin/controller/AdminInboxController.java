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

import com.bugtrack.model.MessageModel;
import com.bugtrack.util.PageContent;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/Admin/Message")
public class AdminInboxController  extends AdminCommonController {
	
	/**
	 * the main view of assign bugs
	 * @return template of /Message/inbox
	 */
	@RequestMapping(value="inbox", method=RequestMethod.GET)
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("Admin/Message/inbox");
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
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<MessageModel> msgs = msgRepo.findAllByReceiverAndStatusLessThan(this.getUser(), 2);
		if(msgs!=null){
			map.put("total", msgs.size());
			msgs = msgRepo.findAllByReceiverAndStatusLessThan(this.getUser(), 2, pageable);
			map.put("rows", msgs);
		}else{
			map.put("total", 0);
			map.put("rows", msgs);
		}
		ObjectMapper om = new ObjectMapper();
		String jsonString = om.writeValueAsString(map);
		return jsonString;
	}
	
	@Transactional(readOnly = false)
	@RequestMapping(value = "status", method = RequestMethod.PUT)
	public String msgSwitchStatus(HttpServletRequest request, 
			@RequestParam(value = "ids", required = false) String mids,
			@RequestParam(value = "st", required = true) Integer st)
			throws Exception {
		String[] ids = null;
		if (mids != null && !mids.equals(""))
			ids = mids.split(",");
		for (int i = 0; i < ids.length; ++i) {
			MessageModel msg = msgRepo.findOne(Integer.valueOf(ids[i]));
			if (msg != null){
				msg.setStatus(st);
				msg.setReadts(this.getCurrentTime());
				msg = msgRepo.saveAndFlush(msg);
			}
		}
		return ajaxReturn(true, "", "Ok");
	}
}
