package com.bugtrack.admin.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.ResourceModel;

public interface ResourceRepository extends JpaRepository<ResourceModel, Integer> {
	ResourceModel findByResource(String url);
	ResourceModel findByResourceAndStatus(String url, int i);
	ResourceModel findById(Integer id);
	List<ResourceModel> findAllByStatus(int i, Sort sort);

}
