package com.zoneyet.robot.admin.controller.system.robot;



import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zoneyet.robot.admin.controller.base.BaseController;
import com.zoneyet.robot.admin.entity.Page;
import com.zoneyet.robot.admin.entity.system.User;
import com.zoneyet.robot.admin.service.system.fhlog.FHlogManager;
import com.zoneyet.robot.admin.service.system.robot.RobotVerManager;
import com.zoneyet.robot.admin.util.AppUtil;
import com.zoneyet.robot.admin.util.Const;
import com.zoneyet.robot.admin.util.Jurisdiction;
import com.zoneyet.robot.admin.util.ObjectExcelView;
import com.zoneyet.robot.admin.util.PageData;


/** 
 * 说明：机器人版本
 * 创建人：FH Q313596790
 * 创建时间：2018-03-12
 */
@Controller
@RequestMapping(value="/robotversion")
public class RobotVerController extends BaseController {
	
	String menuUrl = "robotver/list.do"; //菜单地址(权限用)
	@Resource(name="robotverService")
	private RobotVerManager robotverService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增RobotVer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROBOTVER_ID", this.get32UUID());	//主键
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		try {
			robotverService.save(pd);
			FHLOG.save(user.getNAME(), "保存版本"+pd.getString("NAME")+"成功", super.getIP());
		} catch (Exception e) {
			FHLOG.save(user.getNAME(), "保存版本"+pd.getString("NAME")+"失败", super.getIP());
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	 
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		try {
			if(robotverService.delete(pd)){
				FHLOG.save(user.getNAME(), "删除版本id为"+pd.getString("VERSIONID")+"的版本成功",super.getIP());
				out.write("success");
			}else{
				FHLOG.save(user.getNAME(), "删除版本id为"+pd.getString("VERSIONID")+"的版本失败",super.getIP());
				out.write("fail");
			}
		} catch (Exception e) {
			FHLOG.save(user.getNAME(), "删除版本id为"+pd.getString("VERSIONID")+"的版本失败",super.getIP());
			out.write("fail");
		}
		out.close();
	
		
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改RobotVer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		robotverService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表RobotVer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = robotverService.list(page);	//列出RobotVer列表
		mv.setViewName("system/robot/robotver_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/robot/robotver_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = robotverService.findById(pd);	//根据ID读取
		mv.setViewName("system/robot/robotver_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除RobotVer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			robotverService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出RobotVer到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("备注1");	//1
		titles.add("备注2");	//2
		titles.add("备注3");	//3
		titles.add("备注4");	//4
		titles.add("备注5");	//5
		titles.add("备注6");	//6
		titles.add("备注7");	//7
		titles.add("备注8");	//8
		titles.add("备注9");	//9
		titles.add("备注10");	//10
		titles.add("备注11");	//11
		titles.add("备注12");	//12
		titles.add("备注13");	//13
		titles.add("备注14");	//14
		titles.add("备注15");	//15
		titles.add("备注16");	//16
		titles.add("备注17");	//17
		titles.add("备注18");	//18
		dataMap.put("titles", titles);
		List<PageData> varOList = robotverService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("VERSIONID").toString());	//1
			vpd.put("var2", varOList.get(i).get("GRADE").toString());	//2
			vpd.put("var3", varOList.get(i).getString("NAME"));	    //3
			vpd.put("var4", varOList.get(i).getString("CONTENT"));	    //4
			vpd.put("var5", varOList.get(i).getString("PRICE"));	    //5
			vpd.put("var6", varOList.get(i).get("STREAM").toString());	//6
			vpd.put("var7", varOList.get(i).get("STORAGE").toString());	//7
			vpd.put("var8", varOList.get(i).get("USENUM").toString());	//8
			vpd.put("var9", varOList.get(i).getString("PURE"));	    //9
			vpd.put("var10", varOList.get(i).getString("CREATETIME"));	    //10
			vpd.put("var11", varOList.get(i).getString("TAIL"));	    //11
			vpd.put("var12", varOList.get(i).getString("FILTER"));	    //12
			vpd.put("var13", varOList.get(i).getString("REMAIN1"));	    //13
			vpd.put("var14", varOList.get(i).getString("REMAIN2"));	    //14
			vpd.put("var15", varOList.get(i).getString("REMAIN3"));	    //15
			vpd.put("var16", varOList.get(i).getString("REMAIN4"));	    //16
			vpd.put("var17", varOList.get(i).getString("ICON"));	    //17
			vpd.put("var18", varOList.get(i).getString("MONTHPRICE"));	    //18
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
