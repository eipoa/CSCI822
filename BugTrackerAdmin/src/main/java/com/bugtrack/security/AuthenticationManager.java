package com.bugtrack.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bugtrack.dao.ResourceRepository;
import com.bugtrack.model.ResourceModel;

@Component
public class AuthenticationManager {
	@Autowired
	private ResourceRepository resRepo;
	
	public static Collection<ResourceModel> resources;
	
	public void setResources(Collection<ResourceModel> res){
		resources = res;
	}
	
	public Collection<ResourceModel> getResources(){
		return resources;
	}
	
	public void updateResources(){
		resources = resRepo.findAllByParentIsNull();
	}
}
