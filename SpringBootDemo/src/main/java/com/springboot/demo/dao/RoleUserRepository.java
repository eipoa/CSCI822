package com.springboot.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.model.RoleUserModel;

public interface RoleUserRepository extends JpaRepository<RoleUserModel, Integer> {

}
