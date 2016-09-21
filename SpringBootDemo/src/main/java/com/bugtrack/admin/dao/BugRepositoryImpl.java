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

/**
 * @author Administrator
 *
 */
public class BugRepositoryImpl implements BugRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.springboot.demo.dao.BugRepositoryCustom#findAll(java.util.Map,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Map<String, Object> findAll(Map<String, String> keywords, Pageable pageable) {
		// TODO Auto-generated method stub
		// http://www.christophbrill.de/de_DE/how-to-use-the-jpa-criteria-api-in-a-subquery-on-an-elementcollection/
		String cid = "", pid = "", bstatus = "";
		String ver = "", intitle = "", tempSql = "";
		boolean iscid = false, ispid = false, isbstatus = false, isver = false, isintitle = false;

		StringBuffer sql = new StringBuffer("");
		sql.append("select {b.*}, {p.*}, {o.*}, {v.*} from bug b, os o, product p, version v where ");
		sql.append("b.product_id=p.id and b.os_id=o.id and b.version_id=v.id ");
		//sql.append("select {b.*}, p.productName, o.osname, v.versiond from bug b, os o where ");
		sql.append("  b.os_id=o.id  ");
		if (keywords.containsKey("classification_id")) {
			cid = keywords.get("classification_id");
			if (!cid.trim().equals("-1")) {
				iscid = true;
				tempSql = " b.classification_id=:cid ";
			}
		}
		if (keywords.containsKey("product_id")) {
			pid = keywords.get("product_id");
			if (!pid.trim().equals("-1")) {
				ispid = true;
				if (tempSql.equals("")) {
					tempSql = " b.product_id=:pid ";
				} else {
					tempSql = tempSql + " and b.product_id=:pid ";
				}

			}
		}
		if (keywords.containsKey("version")) {
			ver = keywords.get("version");
			isver = true;
			if (tempSql.equals("")) {
				tempSql = " b.version=:ver ";
			} else {
				tempSql = tempSql + " and b.version=:ver ";
			}
		}
		if (keywords.containsKey("bug_status")) {
			bstatus = keywords.get("bug_status");
			isbstatus = true;
			if (tempSql.equals("")) {
				tempSql = " b.bug_status=:bstatus ";
			} else {
				tempSql = tempSql + " and b.bug_status=:bstatus ";
			}
		}
		if (keywords.containsKey("title")) {
			isintitle = true;
			intitle = keywords.get("title");
			if (tempSql.equals("")) {
				tempSql = " b.title like :title ";
			} else {
				tempSql = tempSql + " and b.title like :title ";
			}
		}

		if (!tempSql.equals("")) {
			sql.append(" and ");
			sql = sql.append(tempSql);
		}

		

		// Query query = em.createNativeQuery(sql.toString(), BugsModel.class);
		Query query = em.createNativeQuery(sql.toString(),"BugsMapping");
		if (iscid) {
			query.setParameter("cid", cid);
		}
		if (ispid) {
			query.setParameter("pid", pid);
		}
		if (isver) {
			query.setParameter("ver", ver);
		}
		if (isbstatus) {
			query.setParameter("bstatus", bstatus);
		}
		if (isintitle) {
			query.setParameter("intitle", "%" + intitle + "%");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", query.getResultList().size());
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		map.put("rows", query.getResultList());
		return map;
	}

}
