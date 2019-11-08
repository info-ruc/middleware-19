package com.zoneyet.robot.admin.service.system.rkeybook.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zoneyet.robot.admin.dao.DaoSupport;
import com.zoneyet.robot.admin.dao.redis.RedisDao;
import com.zoneyet.robot.admin.entity.Page;
import com.zoneyet.robot.admin.service.system.rkeybook.RKeyBookManager;
import com.zoneyet.robot.admin.util.PageData;

/** 
 * 说明： 系统内容设置
 * 创建人：zhx
 * 创建时间：2018-03-09
 * @version
 */
@Service("rkeybookService")
public class RKeyBookService implements RKeyBookManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource(name = "redisDaoImpl")
	private RedisDao redisDaoImpl;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("RKeyBookMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("RKeyBookMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("RKeyBookMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RKeyBookMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RKeyBookMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RKeyBookMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RKeyBookMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public Integer updateRedisKeyBook() throws Exception {
		//先获取数据库数据
		Page page=new Page();
		PageData pd=new PageData();
		pd.put("STATUS", 1);//有效数据存入redis
		page.setPd(pd);
		List<PageData> list=this.list(page);
		Map<String, String> jmap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			jmap.put(list.get(i).getString("NAME"), list.get(i).getString("VALUE"));
		}
		//存入redis
		System.out.println(redisDaoImpl.addMap("sys_keybook", jmap));
		System.out.println("获取Map:"+redisDaoImpl.getMap("sys_keybook"));			//获取Map
		return null;
	}
	
}

