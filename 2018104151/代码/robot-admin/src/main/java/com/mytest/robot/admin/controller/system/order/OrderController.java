package com.zoneyet.robot.admin.controller.system.order;



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
import com.zoneyet.robot.admin.service.system.order.OrderManager;
import com.zoneyet.robot.admin.util.AppUtil;
import com.zoneyet.robot.admin.util.Const;
import com.zoneyet.robot.admin.util.Jurisdiction;
import com.zoneyet.robot.admin.util.MyDateUtils;
import com.zoneyet.robot.admin.util.ObjectExcelView;
import com.zoneyet.robot.admin.util.PageData;
import com.zoneyet.robot.admin.util.Tools;

/** 
 * 说明：订单表
 * 创建人：FH Q313596790
 * 创建时间：2018-03-14
 */
@Controller
@RequestMapping(value="/order")
public class OrderController extends BaseController {
	
	String menuUrl = "order/list.do"; //菜单地址(权限用)
	@Resource(name="orderService")
	private OrderManager orderService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROBOTID", "0");	//机器人id
		pd.put("THIRDNO", "");	//第三方序列号
		pd.put("OURNO", "");	//序列号
		pd.put("STARTTIME", Tools.date2Str(new Date()));	//开始时间
		pd.put("GRADEFROM", "0");	//升级前等级
		pd.put("LASTORDERID", "0");	//上一个订单id
		pd.put("PAYMETHOD", "");	//支付方式
		pd.put("STRATEGYID", "0");	//支付策略id
		pd.put("STATUS", "0");	//订单完成状态
		pd.put("REMAIN1", "");	//备注17
		pd.put("REMAIN2", "");	//备注18
		pd.put("REMAIN3", "0");	//备注19
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		try {
			orderService.save(pd);
			FHLOG.save(user.getNAME(), "保存记录"+pd.getString("NAME")+"成功", super.getIP());
		} catch (Exception e) {
			FHLOG.save(user.getNAME(), "保存记录"+pd.getString("NAME")+"失败", super.getIP());
		}
		
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/refund")
	public ModelAndView refund() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		try {
		orderService.refund(pd);
			FHLOG.save(user.getNAME(), "保存订单号为"+pd.getString("ORDERID")+"的退款记录成功", super.getIP());
		} catch (Exception e) {
			FHLOG.save(user.getNAME(), "保存订单号为"+pd.getString("ORDERID")+"的退款记录成功", super.getIP());
		}
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		orderService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		orderService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Order");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String createStart = pd.getString("STARTTIME");	//开始时间
		String createEnd = pd.getString("ENDTIME");		//结束时间
		if(createStart != null && !"".equals(createStart)){
			pd.put("createStart", createStart+" 00:00:00");
		}
		if(createEnd != null && !"".equals(createEnd)){
			pd.put("createEnd", createEnd+" 23:59:59");
		}
		page.setPd(pd);
		List<PageData>	varList = orderService.list(page);	//列出Order列表
		mv.setViewName("system/order/order_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去退款页
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = orderService.findById(pd);	//根据ID读取
		pd.put("THIRDNO", null);
		if(orderService.findRefundById(pd)!=null){
			pd = orderService.findRefundById(pd);	//根据ID读取
		}
		mv.setViewName("system/order/order_refund");
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
		pd = orderService.findById(pd);	//根据ID读取
		mv.setViewName("system/order/order_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Order");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			orderService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Order到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("订单号");	//1
		titles.add("用户ID");	//2
		titles.add("机器人id");	//3
		titles.add("购买产品名称");	//4
		titles.add("第三方序列号");	//5
		titles.add("序列号");	//6
		titles.add("下单时间");	//7
		titles.add("开始时间");	//8
		titles.add("到期时间");	//9
		titles.add("升级前等级");	//10
		titles.add("上一个订单id");	//11
		titles.add("订单价格");	//12
		titles.add("支付方式");	//13
		titles.add("支付策略id");	//14
		titles.add("订单完成状态");	//15
		titles.add("订单状态");	//16
		titles.add("备注17");	//17
		titles.add("备注18");	//18
		titles.add("备注19");	//19
		dataMap.put("titles", titles);
		List<PageData> varOList = orderService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ORDERID").toString());	//1
			vpd.put("var2", varOList.get(i).get("USERID").toString());	//2
			vpd.put("var3", varOList.get(i).get("ROBOTID").toString());	//3
			vpd.put("var4", varOList.get(i).getString("CONTENT"));	    //4
			vpd.put("var5", varOList.get(i).getString("THIRDNO"));	    //5
			vpd.put("var6", varOList.get(i).getString("OURNO"));	    //6
			vpd.put("var7", varOList.get(i).getString("CREATETIME"));	    //7
			vpd.put("var8", varOList.get(i).getString("STARTTIME"));	    //8
			vpd.put("var9", varOList.get(i).getString("ENDTTIME"));	    //9
			vpd.put("var10", varOList.get(i).get("GRADEFROM").toString());	//10
			vpd.put("var11", varOList.get(i).get("LASTORDERID").toString());	//11
			vpd.put("var12", varOList.get(i).getString("COST"));	    //12
			vpd.put("var13", varOList.get(i).getString("PAYMETHOD"));	    //13
			vpd.put("var14", varOList.get(i).get("STRATEGYID").toString());	//14
			vpd.put("var15", varOList.get(i).get("STATUS").toString());	//15
			vpd.put("var16", varOList.get(i).get("ISPAY").toString());	//16
			vpd.put("var17", varOList.get(i).getString("REMAIN1"));	    //17
			vpd.put("var18", varOList.get(i).getString("REMAIN2"));	    //18
			vpd.put("var19", varOList.get(i).get("REMAIN3").toString());	//19
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
