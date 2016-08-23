package com.springboot.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.model.PermissionModel;

public interface PermissionRepository extends JpaRepository<PermissionModel, Integer> {

}
