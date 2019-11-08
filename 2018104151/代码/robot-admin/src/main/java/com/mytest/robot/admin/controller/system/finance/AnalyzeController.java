package com.zoneyet.robot.admin.controller.system.finance;

import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zoneyet.robot.admin.controller.base.BaseController;
import com.zoneyet.robot.admin.util.Jurisdiction;

/**
 * 财务分析
* @ClassName: AnalyzeController  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author zhangHengxing 
* @date 2018年1月31日 下午4:47:02
*
 */
@Controller
@RequestMapping(value="/analyze")
public class AnalyzeController  extends BaseController {

	String menuUrl = "analyze/list.do"; //菜单地址(权限用)
	
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Object save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"发送站内信");
		System.out.println("进来啊");
		
		
		return menuUrl;
		
		
	}
	
	@RequestMapping(value="/del")
	@ResponseBody
	public void del(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Fhsms");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){System.out.println("进来啊22");} //校验权限
		System.out.println("进来啊");
		
		out.write("success");
		out.close();
		
		
	}
		
}
