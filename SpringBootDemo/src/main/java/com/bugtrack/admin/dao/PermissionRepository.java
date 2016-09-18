package com.bugtrack.admin.dao;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bugtrack.admin.model.PermissionModel;

public interface PermissionRepository extends JpaRepository<PermissionModel, Integer> {

	@Query(value="SELECT r.rolename FROM permission p, resource res, role r "
			+ "WHERE p.roleid=r.id and p.resid=res.id and res.resource=?1", nativeQuery=true)
	ArrayList<String> findAllRolename(String url);

}
