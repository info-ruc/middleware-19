package com.zoneyet.robot.admin.service.system.appuser.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zoneyet.robot.admin.dao.DaoSupport;
import com.zoneyet.robot.admin.entity.Page;
import com.zoneyet.robot.admin.service.system.appuser.AppuserManager;
import com.zoneyet.robot.admin.util.PageData;


/**类名称：AppuserService
 * @author FH Q313596790
 * 修改时间：2015年11月6日
 */
@Service("appuserService")
public class AppuserService implements AppuserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**列出某角色下的所有会员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllAppuserByRorlid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AppuserMapper.listAllAppuserByRorlid", pd);
	}
	
	/**会员列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPdPageUser(Page page)throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.userlistPage", page);
	}
	
	/**通过用户名获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByUsername", pd);
	}
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByEmail(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByEmail", pd);
	}
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByNumber(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByNumber", pd);
	}
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception{
		dao.save("AppuserMapper.saveU", pd);
	}
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception{
		dao.delete("AppuserMapper.deleteU", pd);
	}
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception{
		dao.update("AppuserMapper.editU", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUiId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.findByUiId", pd);
	}
	
	/**全部会员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAllUser(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("AppuserMapper.listAllUser", pd);
	}
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS)throws Exception{
		dao.delete("AppuserMapper.deleteAllU", USER_IDS);
	}
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getAppUserCount(String value)throws Exception{
		return (PageData)dao.findForObject("AppuserMapper.getAppUserCount", value);
	}

	@Override
	public PageData findByUserNickname(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AppuserMapper.findByNickname", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listOrdersByUserNickname(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppuserMapper.listOrdersByUserNickname", pd);
	}

	@Override
	public void sendNotice(PageData pd) throws Exception {
		dao.save("AppuserMapper.saveNotice", pd);
		
	}

	@Override
	public void changeStatus(PageData pd) throws Exception {
		dao.update("AppuserMapper.changeStatus", pd);
		
	}

	
	/*@SuppressWarnings("unchecked")
	public List<PageData> listPdPageNotice(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AppuserMapper.listNotice", pd);
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listPdPageNotice(Page pd) throws Exception {
		return (List<PageData>)dao.findForList("AppuserMapper.noticelistPage", pd);
	}

	@Override
	public void insertNotice(PageData pd) throws Exception {
		dao.save("AppuserMapper.insertNotice", pd);
		
	}

	@Override
	public void deleteNotice(PageData pd) throws Exception {
		dao.delete("AppuserMapper.deleteNotice", pd);
		
	}

	@Override
	public void setNoticeStatus(PageData pd) throws Exception {
		dao.update("AppuserMapper.setNoticeStatus", pd);
		
	}

	@Override
	public PageData findOnlineTime(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AppuserMapper.findOnlineTime", pd);
	}
	
	@Override
	public void setNoticeStatus2(PageData pd) throws Exception {
		dao.update("AppuserMapper.setNoticeStatus2", pd);
		
	}

	@Override
	public void deleteAllNotice(String[] arrayUSER_IDS) throws Exception {
		dao.delete("AppuserMapper.deleteAllNotice", arrayUSER_IDS);
		
	}
	
	@Override
	public List<PageData> helpNoticeList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("AppuserMapper.helpNoticeList", pd);
	}
}

