package com.zoneyet.robot.admin.controller.system.help;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.zoneyet.robot.admin.controller.base.BaseController;
import com.zoneyet.robot.admin.controller.system.appuser.ResultMsg;
import com.zoneyet.robot.admin.entity.Page;
import com.zoneyet.robot.admin.entity.system.Role;
import com.zoneyet.robot.admin.entity.system.User;
import com.zoneyet.robot.admin.service.system.appuser.AppuserManager;
import com.zoneyet.robot.admin.service.system.fhlog.FHlogManager;
import com.zoneyet.robot.admin.service.system.role.RoleManager;
import com.zoneyet.robot.admin.util.AppUtil;
import com.zoneyet.robot.admin.util.Const;
import com.zoneyet.robot.admin.util.Jurisdiction;
import com.zoneyet.robot.admin.util.MyDateUtils;
import com.zoneyet.robot.admin.util.PageData;

/**
 * 帮助中心管理
 * @author yanglan
 *
 */
@Controller
@RequestMapping(value="/help")
public class HelpController extends BaseController {

	
	String menuUrl = "happuser/listUsers.do"; //菜单地址(权限用)
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/**显示用户列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/helpManager")
	public ModelAndView listUsers(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String createStart = pd.getString("createStart");	//开始时间
			String createEnd = pd.getString("createEnd");		//结束时间
			String today = MyDateUtils.getTodayDateString();
			if(createStart != null && !"".equals(createStart)){
				pd.put("createStart", createStart+" 00:00:00");
			}else{
				pd.put("createStart", MyDateUtils.getDateString(today, -30)+" 00:00:00");
			}
			if(createEnd != null && !"".equals(createEnd)){
				pd.put("createEnd", createEnd+" 23:59:59");
			}else{
				pd.put("createEnd", today+" 23:59:59");
			}
			String titleType = pd.getString("titletype");
			if(!("-1".equals(titleType))){
				pd.put("titleType", titleType);
			}
			page.setPd(pd);
			List<PageData>	noticeList = appuserService.listPdPageNotice(page);		//列出会员列表
			/*pd.put("ROLE_ID", "2");
			List<Role> roleList = roleService.listAllRolesByPId(pd);			//列出会员组角色
*/			mv.setViewName("system/help/help_list");
			mv.addObject("noticeList", noticeList);
			mv.addObject("pd", pd);
			mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 批量删除
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteAllU")
	@ResponseBody
	public Object deleteAllU() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	/*获取当前登陆用户*/
		String USERNAME = user.getUSERNAME();
		FHLOG.save(USERNAME, "批量删除消息",super.getIP());
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"批量删除消息");
		/*FHLOG.save(Jurisdiction.getUsername(), "批量删除user",super.getIP());*/
		Map<String,Object> map = new HashMap<String,Object>();
		List<PageData> pdList = new ArrayList<PageData>();
		String notice_IDS = pd.getString("USER_IDS");
		if(null != notice_IDS && !"".equals(notice_IDS)){
			String ArrayUSER_IDS[] = notice_IDS.split(",");
			/*userService.deleteAllU(ArrayUSER_IDS);*/
			appuserService.deleteAllNotice(ArrayUSER_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	/**消息上线
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/setNoticeStatus")
	@ResponseBody
	public String setNoticeStatus() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	/*获取当前登陆用户*/
		String USERNAME = user.getUSERNAME();
		FHLOG.save(USERNAME, "消息"+pd.getString("noticeid")+"上线",super.getIP());
		logBefore(logger, Jurisdiction.getUsername()+"消息上线");
		ResultMsg r=new ResultMsg();
		ModelAndView mv = this.getModelAndView();
		if(null == appuserService.findByUsername(pd)){//判断新增权限
			appuserService.setNoticeStatus(pd);	
			PageData notice = appuserService.findOnlineTime(pd);
			/*System.out.println(notice.get("onlinetime"));*/
			r.setMsg("success");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式
			String time = df.format(notice.get("onlinetime"));
			r.setData(time);
			r.setDataId((String)notice.get("noticeid"));
		}else{
			r.setMsg("failed");
		}
		return JSON.toJSONString(r);
	}
	
	/**消息下线
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/setNoticeStatus2")
	@ResponseBody
	public String setNoticeStatus2() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	/*获取当前登陆用户*/
		String USERNAME = user.getUSERNAME();
		FHLOG.save(USERNAME, "消息"+pd.getString("noticeid")+"下线",super.getIP());
		logBefore(logger, Jurisdiction.getUsername()+"消息下线");
		ResultMsg r=new ResultMsg();
		ModelAndView mv = this.getModelAndView();
		if(null == appuserService.findByUsername(pd)){//判断新增权限
			appuserService.setNoticeStatus2(pd);	
			PageData notice = appuserService.findOnlineTime(pd);
			/*System.out.println(notice.get("onlinetime"));*/
			r.setMsg("success");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式
			String time = df.format(notice.get("offlinetime"));
			r.setData(time);
			r.setDataId((String)notice.get("noticeid"));
		}else{
			r.setMsg("failed");
		}
		return JSON.toJSONString(r);
	}
	
	/**去新增消息页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROLE_ID", "2");
		pd.put("titletype", "新手攻略");
		pd.put("remain2", 0);
		List<PageData>	noticetopList = appuserService.helpNoticeList(pd);	//列出新手攻略类型的父类型
		List<Role> roleList = roleService.listAllRolesByPId(pd);			//列出会员组角色
		mv.setViewName("system/help/help_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("roleList", roleList);
		mv.addObject("noticetopList", noticetopList);
		return mv;
	}
	
	/**新增系统消息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	/*获取当前登陆用户*/
		String USERNAME = user.getUSERNAME();
		FHLOG.save(USERNAME, "新增系统消息",super.getIP());
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"新增系统消息");
		ModelAndView mv = this.getModelAndView();
		pd.put("managerid", user.getUSER_ID());				//最后登录时间
		String publishtime = pd.getString("publishtime");/*为1立即发布*/
		String ueditorContext = pd.getString("ueditorContext");
		String titleType = pd.getString("titleType");
		String subNewType = pd.getString("subNewType");//新手攻略类型
		String subHelpType = pd.getString("subHelpType");//帮助中心类型
		String topHelpType = pd.getString("topHelpType");//新手攻略最高级id
		String isHelpToplevel = pd.getString("isHelpToplevel");//是否为新手攻略最高级（0否，1是）
		
		
		String form_field = pd.getString("form-field");/*1为置顶*/
		if("帮助".equals(titleType)){
			pd.put("remain2", null);
			pd.put("remain3", Integer.parseInt(subHelpType));
		}
		if("新手攻略".equals(titleType)){
			pd.put("remain3", Integer.parseInt(subNewType));
		}
		if("系统公告".equals(titleType)){
			pd.put("remain2", null);
			pd.put("remain3", null);
		}
		if("1".equals(isHelpToplevel)){ //是新手攻略最高级
			pd.put("remain2", 0);//对应remain2
			pd.put("remain3", null);
		}else if("0".equals(isHelpToplevel)){ //不是新手攻略最高级
			pd.put("remain2",Integer.parseInt(topHelpType) );//对应remain2（外键）
		}
		if(null == appuserService.findByUsername(pd)){
			appuserService.insertNotice(pd);			//判断新增权限
			mv.addObject("msg","success");
		}else{
			mv.addObject("msg","failed");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除消息
	 * @param out
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);	/*获取当前登陆用户*/
		String USERNAME = user.getUSERNAME();
		FHLOG.save(USERNAME, "删除消息"+pd.getString("noticeid"),super.getIP());
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		logBefore(logger, Jurisdiction.getUsername()+"删除消息");
		appuserService.deleteNotice(pd);
		out.write("success");
		out.close();
	}
}

