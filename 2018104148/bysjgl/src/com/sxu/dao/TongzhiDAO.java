package com.sxu.dao;
import java.util.List;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sxu.model.Tongzhi;

public class TongzhiDAO extends HibernateDaoSupport
{


    private static final Log log = LogFactory.getLog(TongzhiDAO.class);

 
	protected void initDao() {
		// do nothing
	}

	public void save(Tongzhi transientInstance) {
		 
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Tongzhi persistentInstance) {
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Tongzhi findById(java.lang.Long id) {
		log.debug("getting Tongzhi instance with id: " + id);
		try {
			Tongzhi instance = (Tongzhi) getHibernateTemplate().get(
					"com.sxu.model.Tongzhi", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Tongzhi instance) {
		log.debug("finding Tongzhi instance by example");
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
		log.debug("finding Tongzhi instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Tongzhi as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

 

	public List findAll() {
		log.debug("finding all Tongzhi instances");
		try {
			String queryString = "from Tongzhi";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Tongzhi merge(Tongzhi detachedInstance) {
		log.debug("merging TAdmin instance");
		try {
			Tongzhi result = (Tongzhi) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Tongzhi instance) {
		log.debug("attaching dirty Tongzhi instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Tongzhi instance) {
		log.debug("attaching clean TAdmin instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TongzhiDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TongzhiDAO) ctx.getBean("TongzhiDAO");
	}
}
