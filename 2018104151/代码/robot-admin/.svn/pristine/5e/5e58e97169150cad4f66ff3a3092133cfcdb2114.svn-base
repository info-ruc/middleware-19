package com.zoneyet.robot.admin.service.system.rkeybook;

import java.util.List;

import com.zoneyet.robot.admin.entity.Page;
import com.zoneyet.robot.admin.util.PageData;


/** 
 * 说明： 系统内容设置接口
 * 创建人：FH Q313596790
 * 创建时间：2018-03-09
 * @version
 */
public interface RKeyBookManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**更新redis中数据字典（基本设置）的数据
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public Integer updateRedisKeyBook()throws Exception;
}

