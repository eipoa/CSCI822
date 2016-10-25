/**
 * 
 */
package com.bugtrack.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bugtrack.model.BugsModel;
import com.bugtrack.model.ProductModel;
import com.bugtrack.model.UserModel;

/**
 * @author Administrator
 * @reference http://docs.spring.io/spring-data/jpa/docs/1.11.0.M1/reference/html/#repositories
 */

public interface BugRepository extends JpaRepository<BugsModel, Integer>, BugRepositoryCustom {
	public Page<BugsModel> findAll(Pageable pageable);

	public BugsModel findById(Integer id);

	public List<BugsModel> findTop50ByOrderByCreationtsDesc();

	public Page<BugsModel> findAllByOrderByCreationtsDesc(Pageable pageable);

	public Page<BugsModel> findAllByProductIn(List<ProductModel> findAllByName, Pageable pageable);

	@Query(value = "select pn.name, count(b.id) "
			+ " from bug b join product p on b.product_id=p.id join product_name pn on p.name_id=pn.id "
			+ " group by pn.name ", nativeQuery = true)//right
	List<Object[]> findReport01();
	
	@Query(value = "select pn.name, count(b.id) "
			+ " from bug b join product p on b.product_id=p.id right join "
			+ " (select n.id, n.name from product_name n where n.id in (select p.name_id from product p)) pn on p.name_id=pn.id "
			+ " and b.status_id>3 group by pn.name ",  nativeQuery = true)
	List<Object[]> findReport04();
	
	@Query(value = "select ps.desc, count(b.id)  " +
			" from bug b " +
			" right join bug_status ps on b.status_id=ps.id  " +
			" group by ps.desc order by ps.id", nativeQuery = true)	
	List<Object[]> findReport02();
	
	@Query(value="select substr(b.creation_ts,1,7) as ts, count(b.id)  " +
			" from bug b  " +
			" group by ts ", nativeQuery = true)
	List<Object[]> findReport03();
	
	@Query(value="select substr(b.creation_ts,1,7) as ts, count(b.id)  " +
			" from bug b  " +
			" group by ts ", nativeQuery = true)
	List<Object[]> findReportNewTask();
	
	@Query(value="select count(1)  " +
			" from bug b  where " +
			" date_format(b.creation_ts,'%Y-%m')=date_format(now(),'%Y-%m') ", nativeQuery = true)
	Integer findReportAllTask();
	
	@Query(value="select count(1)  " +
			" from bug b  where " +
			" date_format(b.creation_ts,'%Y-%m')=date_format(now(),'%Y-%m') and (b.status_id=4 or b.status_id=5)", nativeQuery = true)
	Integer findReportAllSTask();
	
	@Query(value="select avg(a.num) " +
			" from " +
			" (select substr(b.creation_ts,1,7) as ts, count(b.id) as num " +
			" from bug b " +
			" group by ts) a", nativeQuery = true)
	Integer findMonthAvgTask();

	public Page<BugsModel> findAllByTitleContainingIgnoreCaseOrTitleContainingIgnoreCase(String string,
			String string2, Pageable pageable);

	public Page<BugsModel> findAllByTitleContainingIgnoreCase(String string, Pageable pageable);

	public Page<BugsModel> findAllByShortdescContainingIgnoreCase(String string, Pageable pageable);

	public Page<BugsModel> findAllByShortdescContainingIgnoreCaseOrShortdescContainingIgnoreCase(String string,
			String string2, Pageable pageable);

	public Page<BugsModel> findAllByReporter(UserModel user, Pageable pageable);

	
//	@Query(value="select count(1)  " +
//			" from bug b  where b.triager_id=?1 " +
//			" and date_format(b.creation_ts,'%Y-%m')=date_format(now(),'%Y-%m')) and b.status_id=?2", nativeQuery = true)
//	Integer findReportTTask(Integer id, Integer st);
//	
//	@Query(value="select count(1)  " +
//			" from bug b  where b.developer_id=?1 " +
//			" and date_format(b.creation_ts,'%Y-%m')=date_format(now(),'%Y-%m')) and b.status_id=?2", nativeQuery = true)
//	Integer findReportDTask(Integer id, Integer st);
//	
//	@Query(value="select count(1)  " +
//			" from bug b  where b.reviewer_id=?1 " +
//			" and date_format(b.creation_ts,'%Y-%m')=date_format(now(),'%Y-%m')) and b.status_id=?2", nativeQuery = true)
//	Integer findReportRTask(Integer id, Integer st);
}


















