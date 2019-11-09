package com.sxu.dao;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sxu.model.Xuanti;

public class XuantiDAO extends HibernateDaoSupport
{


    private static final Log log = LogFactory.getLog(XuantiDAO.class);

 
	protected void initDao() {
		// do nothing
	}

	public void save(Xuanti transientInstance) {
		 
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Xuanti persistentInstance) {
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Xuanti findById(Long id) {
		log.debug("getting Xuanti instance with id: " + id);
		try {
			Xuanti instance = (Xuanti) getHibernateTemplate().get("com.sxu.model.Xuanti", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Xuanti instance) {
		log.debug("finding Xuanti instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Xuanti instance with property: " + propertyName+ ", value: " + value);
		try {
			String queryString = "from Xuanti as model where model."+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

 

	public List findAll() {
		log.debug("finding all Xuanti instances");
		try {
			String queryString = "from Xuanti";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Xuanti merge(Xuanti detachedInstance) {
		log.debug("merging TAdmin instance");
		try {
			Xuanti result = (Xuanti) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Xuanti instance) {
		log.debug("attaching dirty Xuanti instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Xuanti instance) {
		log.debug("attaching clean TAdmin instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
	public List report(){
		String sql = " select sum(case when fenshu>=90 and fenshu<=100 then 1 ELSE 0 end) yx, ";
		sql = sql + " sum(case when fenshu>=80 and fenshu<=89 then 1 ELSE 0 end) lh,";
		sql = sql + " sum(case when fenshu>=70 and fenshu<=79 then 1 ELSE 0 end)  hg, ";
		sql = sql + " sum(case when  fenshu<70 then 1 ELSE 0 end) bhg  ";
		sql = sql + " from xuanti where fenshu is not null  ";
		Session session = null;
		List<Object[]> list = null;
		try {
			session = getSession();
			Query query = session.createSQLQuery(sql);
			list = query.list();
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return list;
	}
	

	public static XuantiDAO getFromApplicationContext(ApplicationContext ctx) {
		return (XuantiDAO) ctx.getBean("XuantiDAO");
	}
}
