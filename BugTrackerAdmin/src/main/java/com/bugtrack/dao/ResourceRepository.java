package com.bugtrack.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.model.ResourceModel;

public interface ResourceRepository extends JpaRepository<ResourceModel, Integer> {
	ResourceModel findByResource(String url);
	ResourceModel findByResourceAndStatus(String url, int i);
	ResourceModel findById(Integer id);
	List<ResourceModel> findAllByStatus(int i, Sort sort);
	List<ResourceModel> findAllByParentIsNullAndStatus(int i);
	List<ResourceModel> findAllByParentIsNullAndStatus(Integer i, Sort s);
	List<ResourceModel> findAllByParentAndStatus(int parent, int status, Sort sort);
	Collection<ResourceModel> findAllByParentIsNull();
}
