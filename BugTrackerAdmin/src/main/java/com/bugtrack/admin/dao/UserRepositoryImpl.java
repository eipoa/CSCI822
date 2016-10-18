/**
 * 
 */
package com.bugtrack.admin.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Pageable;

import com.bugtrack.admin.model.UserModel;

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
		String role = "", username = "";
		String tmpSql = "";
		boolean isrole = false, isname = false;
		StringBuffer sql = new StringBuffer("");
		sql.append("select u.* from auth_user u where u.status<>100 ");
		if (keywords.containsKey("role")) {
			role = keywords.get("role");
			isrole = true;
			tmpSql = "  and u.id in (select ur.userid from auth_roleuser ur where ur.roleid=:role) ";
		}
		if (keywords.containsKey("username")) {
			username = keywords.get("username");
			isname = true;
			tmpSql = tmpSql + " and  u.username like :username ";
		}
		if(!tmpSql.equals("")){
			sql.append(tmpSql);
		}
		Query query = em.createNativeQuery(sql.toString(), UserModel.class);
		if (isrole) {
			query.setParameter("role", role);
		}
		if (isname) {
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
