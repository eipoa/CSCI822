/**
 * 
 */
package com.springboot.demo.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Pageable;

import com.springboot.demo.model.UserModel;

/**
 * @author Administrator
 *
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.springboot.demo.dao.UserRepositoryCustom#findAll(java.util.Map,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Map<String, Object> findAll(Map<String, String> keywords, Pageable pageable) {
		// TODO Auto-generated method stub
		// http://www.christophbrill.de/de_DE/how-to-use-the-jpa-criteria-api-in-a-subquery-on-an-elementcollection/
		String role, username;
		StringBuffer sql = new StringBuffer("");
		sql.append("select u.* from user u ");
		if (keywords.containsKey("role")) {
			role = keywords.get("role");
		} else {
			role = null;
		}
		if (keywords.containsKey("username")) {
			username = keywords.get("username");
		} else {
			username = null;
		}
		if (role != null && !("").equals(role.trim()) && !role.trim().equals("-1")) {
			sql.append(" where u.id in (select au.userid from authorise au where au.roleid=:role) ");
			if (username != null && !("").equals(username.trim())) {
				sql.append(" and u.username like :username");
			}
		}else{
			if (username != null && !("").equals(username.trim())) {
				sql.append("  where u.username like :username");
			}
		}

		Query query = em.createNativeQuery(sql.toString(), UserModel.class);
		if (role != null && !("").equals(role.trim()) && !role.trim().equals("-1")) {
			query.setParameter("role", role);
		}
		if (username != null && !("").equals(username.trim())) {
			query.setParameter("username", "%"+username+"%");
		}
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("total", query.getResultList().size());
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		map.put("rows", query.getResultList());

		return map;
	}

}
