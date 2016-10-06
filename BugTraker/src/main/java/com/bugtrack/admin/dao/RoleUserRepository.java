package com.bugtrack.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.admin.model.RoleUserModel;

public interface RoleUserRepository extends JpaRepository<RoleUserModel, Integer> {

}
