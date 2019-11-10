package com.sxu.dao;
import java.util.List;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sxu.model.TKeti;

public class TKetiDAO extends HibernateDaoSupport
{


    private static final Log log = LogFactory.getLog(TKetiDAO.class);

 
	protected void initDao() {
		// do nothing
	}

	public void save(TKeti transientInstance) {
		 
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TKeti persistentInstance) {
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TKeti findById(java.lang.Integer id) {
		log.debug("getting TKeti instance with id: " + id);
		try {
			TKeti instance = (TKeti) getHibernateTemplate().get(
					"com.sxu.model.TKeti", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TKeti instance) {
		log.debug("finding TKeti instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TKeti instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TKeti as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

 

	public List findAll() {
		log.debug("finding all TKeti instances");
		try {
			String queryString = "from TKeti";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TKeti merge(TKeti detachedInstance) {
		log.debug("merging TAdmin instance");
		try {
			TKeti result = (TKeti) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TKeti instance) {
		log.debug("attaching dirty TKeti instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TKeti instance) {
		log.debug("attaching clean TAdmin instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TKetiDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TKetiDAO) ctx.getBean("TKetiDAO");
	}
}
