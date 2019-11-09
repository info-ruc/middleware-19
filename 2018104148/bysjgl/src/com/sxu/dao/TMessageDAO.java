package com.sxu.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sxu.model.TMessage;

/**
 * Data access object (DAO) for domain model class TTea.
 * 
 * @see com.sxu.model.TMessage
 * @author MyEclipse Persistence Tools
 */

public class TMessageDAO extends HibernateDaoSupport
{
	private static final Log log = LogFactory.getLog(TMessageDAO.class);

 

	protected void initDao()
	{
		// do nothing
	}

	public void save(TMessage transientInstance)
	{
		log.debug("saving TMessage instance");
		try
		{
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re)
		{
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TMessage persistentInstance)
	{
		log.debug("deleting TMessage instance");
		try
		{
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re)
		{
			log.error("delete failed", re);
			throw re;
		}
	}

	public TMessage findById(java.lang.Integer id)
	{
		log.debug("getting TMessage instance with id: " + id);
		try
		{
			TMessage instance = (TMessage) getHibernateTemplate().get("com.sxu.model.TMessage",
					id);
			return instance;
		} catch (RuntimeException re)
		{
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TMessage instance)
	{
		log.debug("finding TMessage instance by example");
		try
		{
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re)
		{
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value)
	{
		log.debug("finding TMessage instance with property: " + propertyName
				+ ", value: " + value);
		try
		{
			String queryString = "from TMessage as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re)
		{
			log.error("find by property name failed", re);
			throw re;
		}
	}

	 
	public List findAll(int tid)
	{
		log.debug("finding all TMessage instances");
		try
		{
			String queryString = "from TMessage where tid = " + tid;
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re)
		{
			log.error("find all failed", re);
			throw re;
		}
	}

	public TMessage merge(TMessage detachedInstance)
	{
		log.debug("merging TMessage instance");
		try
		{
			TMessage result = (TMessage) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re)
		{
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TMessage instance)
	{
		log.debug("attaching dirty TMessage instance");
		try
		{
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re)
		{
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TMessage instance)
	{
		log.debug("attaching clean TMessage instance");
		try
		{
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re)
		{
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TMessageDAO getFromApplicationContext(ApplicationContext ctx)
	{
		return (TMessageDAO) ctx.getBean("TMessageDAO");
	}
}