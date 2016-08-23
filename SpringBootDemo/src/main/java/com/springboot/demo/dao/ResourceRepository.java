package com.springboot.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.model.ResourceModel;

public interface ResourceRepository extends JpaRepository<ResourceModel, Integer> {

}
