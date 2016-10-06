package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.ResourceModel;

public interface ResourceRepository extends JpaRepository<ResourceModel, Integer> {

}
