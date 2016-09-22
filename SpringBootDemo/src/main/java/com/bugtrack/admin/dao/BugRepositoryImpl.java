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

import com.bugtrack.admin.model.BugsModel;

/**
 * @author Baoxing Li
 * @version 1.0.0
 * A implements of BugRepositoryCustom interface
 */
public class BugRepositoryImpl implements BugRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}


	/**
	 * Retrieve bugs according to keywords and page
	 * @param keywords in query list
	 * @param pageable in page object
	 * @return out json object for JqeasyUI datagrid
	 * 		{total: 10,
	 * 		 rows : {
	 * 			bugs object list
	 * 		 }
	 *      }
	 * @see Pageable
	 */
	@Override
	public Map<String, Object> findAll(Map<String, String> keywords, Pageable pageable) {
		// TODO Auto-generated method stub
		// http://www.christophbrill.de/de_DE/how-to-use-the-jpa-criteria-api-in-a-subquery-on-an-elementcollection/
		String cid = "", pid = "", bid = "", bstatus = "", btitle = "";
		String pname = "", pver = "", pos = "";
		String tempSql = "", productSql = "";
		boolean ispname = false, ispver = false, ispos = false;
		boolean isbcid = false, isbid = false, isbstatus = false, isbtitle = false, isbpri = false;

		StringBuffer sql = new StringBuffer("");
		// products are on sell
		sql.append("select b.* from bug b where b.product_id in ( ");

		// for product
		productSql = " select id from product where status=1 ";
		// pname
		if (keywords.containsKey("pname")) {
			pname = keywords.get("pname");
			ispname = true;
			productSql = productSql + " and name_id=:pname ";
		}
		// ver
		if (keywords.containsKey("pver")) {
			pver = keywords.get("pver");
			ispver = true;
			//productSql = productSql.equals("")?"":productSql + " and ";
			productSql = productSql + " and version=:pver ";
		}
		// os
		if (keywords.containsKey("pos")) {
			pos = keywords.get("pos");
			ispos = true;
			//productSql = productSql.equals("")?"":productSql + " and ";
			productSql = productSql + " and os_id=:pos ";
		}
		productSql = productSql + ") ";
		sql.append(productSql);
		
		
		// category
		if (keywords.containsKey("bcategory")) {
			cid = keywords.get("bcategory");
			isbcid = true;
			tempSql = " and b.classification_id=:cid ";
		}
		// priority
		if (keywords.containsKey("bpriority")) {
			pid = keywords.get("bpriority");
			isbpri = true;
			tempSql = " and b.priority_id=:pid ";
		}
		// bug id
		if (keywords.containsKey("bid")) {
			bid = keywords.get("bid");
			isbid = true;
			//tempSql = tempSql.equals("")?"":tempSql + " and ";
			tempSql = " and b.id=:bid ";
		}
		// bug status
		if (keywords.containsKey("bstatus")) {
			bstatus = keywords.get("bstatus");
			isbstatus = true;
			tempSql = tempSql + " and b.status_id=:bstatus ";
		}
		// like title
		if (keywords.containsKey("btitle")) {
			isbtitle = true;
			btitle = keywords.get("btitle");
			tempSql = tempSql + " and b.title like :btitle ";
		}
		// start/end

		if (!tempSql.equals("")) {
			sql = sql.append(tempSql);
		}

		
		Query query = em.createNativeQuery(sql.toString(),BugsModel.class);//"BugsMapping");
		
		// product
		if (ispver) {
			query.setParameter("pver", pver);
		}
		if (ispos) {
			query.setParameter("pos", pos);
		}
		if (ispname) {
			query.setParameter("pname", pname);
		}
		// bug
		if (isbid) {
			query.setParameter("bid", bid);
		}
		if (isbcid) {
			query.setParameter("cid", cid);
		}
		if (isbpri) {
			query.setParameter("pid", pid);
		}
		if (isbstatus) {
			query.setParameter("bstatus", bstatus);
		}
		if (isbtitle) {
			query.setParameter("btitle", "%" + btitle + "%");
		}
		System.out.println(query.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", query.getResultList().size());
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		map.put("rows", query.getResultList());
		return map;
	}

}
