/**
 * 
 */
package com.bugtrack.admin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Pageable;

import com.bugtrack.admin.model.BugStatusModel;
import com.bugtrack.admin.model.BugsModel;

/**
 * @author Baoxing Li
 * @version 1.0.0 A implements of BugRepositoryCustom interface
 */
public class BugRepositoryImpl implements BugRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * Retrieve bugs according to keywords and page
	 * 
	 * @param keywords
	 *            in query list
	 * @param pageable
	 *            in page object
	 * @return out json object for JqeasyUI datagrid {total: 10, rows : { bugs
	 *         object list } }
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
			// productSql = productSql.equals("")?"":productSql + " and ";
			productSql = productSql + " and version=:pver ";
		}
		// os
		if (keywords.containsKey("pos")) {
			pos = keywords.get("pos");
			ispos = true;
			// productSql = productSql.equals("")?"":productSql + " and ";
			productSql = productSql + " and os_id=:pos ";
		}
		productSql = productSql + ") ";
		sql.append(productSql);

		// category
		if (keywords.containsKey("bcategory")) {
			cid = keywords.get("bcategory");
			isbcid = true;
			tempSql = tempSql + " and b.classification_id=:cid ";
		}
		// priority
		if (keywords.containsKey("bpriority")) {
			pid = keywords.get("bpriority");
			isbpri = true;
			tempSql = tempSql + " and b.priority_id=:pid ";
		}
		// bug id
		if (keywords.containsKey("bid")) {
			bid = keywords.get("bid");
			isbid = true;
			// tempSql = tempSql.equals("")?"":tempSql + " and ";
			tempSql = tempSql + " and b.id=:bid ";
		}

		// like title
		if (keywords.containsKey("btitle")) {
			isbtitle = true;
			btitle = keywords.get("btitle");
			tempSql = tempSql + " and b.title like :btitle ";
		}
		// triager
		boolean istriager = false;
		String btriager = "";
		if (keywords.containsKey("triager")) {
			istriager = true;
			btriager = keywords.get("triager");
			tempSql = tempSql + " and b.triager_id=:btriager ";
		}
		// developer
		boolean isdeve = false;
		String bdeve = "";
		if (keywords.containsKey("developer")) {
			isdeve = true;
			bdeve = keywords.get("developer");
			tempSql = tempSql + " and b.developer_id=:bdeve and (b.status_id=2 or b.status_id=3 or b.status_id=4) ";
		}
		// reviewer
		boolean isreview = false;
		String breview = "";
		if (keywords.containsKey("reviewer")) {
			isreview = true;
			breview = keywords.get("reviewer");
			tempSql = tempSql + " and b.reviewer_id=:breview  and (b.status_id=3 or b.status_id=4) ";
		}

		// creation time
		boolean iscts1 = false, iscts2 = false;
		String cts1 = "", cts2 = "";
		if (keywords.containsKey("cts1")) {
			iscts1 = true;
			cts1 = keywords.get("cts1");
			cts1 = cts1.substring(0, 10);
		}
		if (keywords.containsKey("cts2")) {
			iscts2 = true;
			cts2 = keywords.get("cts2");
			cts2 = cts2.substring(0, 10);
		}
		if (iscts1 && iscts2) {
			tempSql = tempSql + " and b.creation_ts between :cts1 and :cts2 ";
		} else if (iscts1) {
			tempSql = tempSql + " and left(b.creation_ts,10) = :cts1 ";
		} else if (iscts2) {
			tempSql = tempSql + " and left(b.creation_ts,10) = :cts2 ";
		}
		
System.out.println(cts1);
System.out.println(cts2);
		// bug status
		if (keywords.containsKey("bstatus")) {
			bstatus = keywords.get("bstatus");
			isbstatus = true;
			tempSql = tempSql + " and b.status_id=:bstatus ";
		}
		// start/end???

		if (!tempSql.equals("")) {
			sql = sql.append(tempSql);
		}

		Query query = em.createNativeQuery(sql.toString(), BugsModel.class);// "BugsMapping");

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
		if (istriager) {
			query.setParameter("btriager", btriager);
		}
		if (isdeve) {
			query.setParameter("bdeve", bdeve);
		}
		if (isreview) {
			query.setParameter("breview", breview);
		}
		if (iscts1) {
			query.setParameter("cts1", cts1);
		}
		if (iscts2) {
			query.setParameter("cts2", cts2);
		}
		//System.out.println(query.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", query.getResultList().size());
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
//		Collection<BugsModel> bugs = query.getResultList();
//		for (BugsModel b : bugs) {
//			b.setCurentSolution();
//		}
		map.put("rows", query.getResultList());
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BugStatusModel> findStatusList(String typeid, String sm) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("");
		// products are on sell
		if (typeid.trim().equals("1")) {
			if (sm.trim().equals("0"))
				sql.append("select b.* from bug_status b ");
			else
				sql.append("select b.* from bug_status b where b.id=1 or b.id=2 or b.id=5");
		} else if (typeid.trim().equals("2")) {
			if (sm.trim().equals("0"))
				sql.append("select b.* from bug_status b where b.id=2 or b.id=3 or b.id=4");
			else
				sql.append("select b.* from bug_status b where b.id=2 or b.id=3");
		} else if (typeid.trim().equals("3")) {
			if (sm.trim().equals("0"))
				sql.append("select b.* from bug_status b where b.id=3 or b.id=4");
			else
				sql.append("select b.* from bug_status b where b.id=2 or b.id=3 or b.id=4");
		}
		Query query = em.createNativeQuery(sql.toString(), BugStatusModel.class);
		return query.getResultList();
	}

}
