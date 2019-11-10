package com.sxu.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sxu.model.TGonggao;

/**
 * 
 * @author ZYR
 *
 */

public class TGonggaoDAO extends HibernateDaoSupport
{
	private static final Log log = LogFactory.getLog(TGonggaoDAO.class);

	// property constants
	public static final String GG_ID = "ggId";

	public static final String GG_TITLE = "ggTitle";

	public static final String GG_CONTENT = "ggContent";

	public static final String GG_TIME = "ggTime";

	public static final String GG_DEL = "ggDel";

	protected void initDao()
	{
		// do nothing
	}

	public void save(TGonggao transientInstance)
	{
		log.debug("saving TGonggao instance");
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

	public void delete(TGonggao persistentInstance)
	{
		log.debug("deleting TGonggao instance");
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

	public TGonggao findById(java.lang.Integer id)
	{
		log.debug("getting TGonggao instance with id: " + id);
		try
		{
			TGonggao instance = (TGonggao) getHibernateTemplate().get("com.sxu.model.TGonggao",id);
			return instance;
		} catch (RuntimeException re)
		{
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TGonggao instance)
	{
		log.debug("finding TGonggao instance by example");
		try
		{
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "+ results.size());
			return results;
		} catch (RuntimeException re)
		{
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value)
	{
		log.debug("finding TGonggao instance with property: " + propertyName + ", value: " + value);
		try
		{
			String queryString = "from TGonggao as model where model." + propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re)
		{
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByGGId(Object ggId)
	{
		return findByProperty(GG_ID, ggId);
	}

	public List findByGGTitle(Object ggTitle)
	{
		return findByProperty(GG_TITLE, ggTitle);
	}

	public List findByGGContent(Object ggContent)
	{
		return findByProperty(GG_CONTENT, ggContent);
	}

	public List findByGGTime(Object ggTime)
	{
		return findByProperty(GG_TIME, ggTime);
	}

	public List findByGGDel(Object ggDel)
	{
		return findByProperty(GG_DEL, ggDel);
	}

	public List findAll()
	{
		log.debug("finding all TGonggao instances");
		try
		{
			String queryString = "from TGonggao";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re)
		{
			log.error("find all failed", re);
			throw re;
		}
	}

	public TGonggao merge(TGonggao detachedInstance)
	{
		log.debug("merging TGonggao instance");
		try
		{
			TGonggao result = (TGonggao) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re)
		{
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TGonggao instance)
	{
		log.debug("attaching dirty TGonggao instance");
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

	public void attachClean(TGonggao instance)
	{
		log.debug("attaching clean TGonggao instance");
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

	public static TGonggaoDAO getFromApplicationContext(ApplicationContext ctx)
	{
		return (TGonggaoDAO) ctx.getBean("TGonggaoDAO");
	}
}