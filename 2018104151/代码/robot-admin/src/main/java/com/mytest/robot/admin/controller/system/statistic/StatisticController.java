package com.zoneyet.robot.admin.controller.system.statistic;



import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zoneyet.robot.admin.controller.base.BaseController;
import com.zoneyet.robot.admin.entity.Page;
import com.zoneyet.robot.admin.service.system.robot.RobotVerManager;
import com.zoneyet.robot.admin.service.system.statistic.StatisticManager;
import com.zoneyet.robot.admin.util.Jurisdiction;
import com.zoneyet.robot.admin.util.PageData;

/** 
 * 说明：订单表
 * 创建人：FH Q313596790
 * 创建时间：2018-03-14
 */
@Controller
@RequestMapping(value="/statistic")
public class StatisticController extends BaseController {
	
	String menuUrl = "statistic/list.do"; //菜单地址(权限用)
	@Resource(name="statisticService")
	private StatisticManager statisticService;
	@Resource(name="robotverService")
	private RobotVerManager robotverService;
	private Integer temp = 0;
	
	
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Order");
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		
		//4个统计数据
		BigDecimal daysum;
		BigDecimal monthsum;
		if(statisticService.daysum(pd)!=null){
			daysum = (BigDecimal) (statisticService.daysum(pd).get("daysum"));
		}else{
			daysum=new BigDecimal(0);
		}
		if(statisticService.monthsum(pd)!=null){
			monthsum = (BigDecimal) (statisticService.monthsum(pd).get("monthsum"));
		}else{
			monthsum=new BigDecimal(0);
		}
		Long daycount = (Long) statisticService.daycount(pd).get("daycount");
		Long monthcount = (Long) statisticService.monthcount(pd).get("monthcount");
		
		//获取默认时间范围
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	    String nowDate=format.format(new Date());  
	    Calendar c = Calendar.getInstance();  
	    c.add(Calendar.MONTH, -1);
	    String postDate=format.format(c.getTime());
	    SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd");
	    
		//处理表格1
		if(pd==null||pd.get("STARTTIME")==null||pd.get("ENDTIME")==null||"".equals(pd.get("STARTTIME"))||"".equals(pd.get("ENDTIME"))){
			pd.put("STARTTIME", postDate);
			pd.put("ENDTIME", nowDate);
		}
		if(pd!=null&&pd.get("STARTTIME")!=null&&pd.get("ENDTIME")!=null&&!"".equals(pd.get("STARTTIME"))&&!"".equals(pd.get("ENDTIME"))){
			//获取starttime
			Date date=formatter.parse((String)pd.get("STARTTIME"));
			c.setTime(date);
			
			List<PageData> pd2=null;//x轴和y轴免费用户
			List<PageData> pd3=null;//y轴付费用户
			List<String> xval = new ArrayList<String>();
			List<Integer> ymemfree = new ArrayList<Integer>();
			List<Integer> ymemfees = new ArrayList<Integer>();	
			pd2 =statisticService.memberstatic(pd);
			pd3=statisticService.memberstatic2(pd);
			PageData pdtime=statisticService.getstartend(pd);
			Integer start=null;
			Integer end=null;
			try {
				start = ((Long)pdtime.get("start")).intValue();
				end = ((Long)pdtime.get("end")).intValue();
				int j=0;
				int k=0; 
				for (int i = start; i <= end; i++) {
					String now=format.format(c.getTime());
					if(j<pd2.size()&&k<pd3.size()&&i==(int)pd2.get(j).get("count")&&i==(int)pd3.get(k).get("count")){//既有免费又有付费
						xval.add("'"+pd2.get(j).getString("time")+"'");
						ymemfree.add(((Long)pd2.get(j).get("num")).intValue());
						ymemfees.add(((Long)pd3.get(k).get("num2")).intValue());
						j++;
						k++;
					}else if(j<pd2.size()&&i==(int)pd2.get(j).get("count")){		//只有免费
						xval.add("'"+pd2.get(j).getString("time")+"'");
						ymemfree.add(((Long)pd2.get(j).get("num")).intValue());
						ymemfees.add(0);
						j++;
					}else if(k<pd3.size()&&i==(int)pd3.get(k).get("count")){		//只有付费
						xval.add("'"+pd3.get(k).getString("time")+"'");
						ymemfree.add(0);
						ymemfees.add(((Long)pd3.get(k).get("num2")).intValue());
						k++;
					}else{															//没有用户
						xval.add("'"+now+"'");
						ymemfree.add(0);
						ymemfees.add(0);
					}	
					c.add(Calendar.DATE, 1);
				}
				String s= strlist2string(xval);
				mv.addObject("xval", strlist2string(xval));
				mv.addObject("ymemfree", ymemfree);
				mv.addObject("ymemfees", ymemfees);
				mv.addObject("tag2", 0);
			} catch (Exception e) {
				mv.addObject("tag2", 1);
				mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
				mv.setViewName("system/statistic/statistic");
			}			
		}		
		
		
		//处理表格2
		List<PageData> listAll = robotverService.listAll(pd);
		List<Integer> lines=new ArrayList<>();
		List<String> lines2=new ArrayList<>();
		List<String> lines3=new ArrayList<>();
		for (int i = 0; i < listAll.size(); i++) {
			lines.add((Integer)listAll.get(i).get("GRADE"));
			lines2.add("'"+listAll.get(i).getString("NAME")+"'");
			lines3.add(listAll.get(i).getString("NAME"));
		}
		pd.put("lines", lines);
		
		
		if(pd==null||pd.get("STARTTIME2")==null||pd.get("ENDTIME2")==null||"".equals(pd.get("STARTTIME2"))||"".equals(pd.get("ENDTIME2"))){		
			pd.put("STARTTIME2", postDate);
			pd.put("ENDTIME2", nowDate);
		}
		if(pd!=null&&pd.get("STARTTIME2")!=null&&pd.get("ENDTIME2")!=null&&!"".equals(pd.get("STARTTIME2"))&&!"".equals(pd.get("ENDTIME2"))){		
			//获取starttime2
			Date date=formatter.parse((String)pd.get("STARTTIME2"));
			c.setTime(date);
			List<PageData> pd4=statisticService.incomestatic(pd);			//访问数据库
			Integer start=null;
			Integer end=null;
			PageData pdtime=statisticService.getstartend2(pd);
			try {
				start = ((Long)pdtime.get("start")).intValue();
				end = ((Long)pdtime.get("end")).intValue();
				mv.addObject("tag3",0);	
			} catch (Exception e) {
				mv.setViewName("system/statistic/statistic");
				mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
				mv.addObject("tag3",1);	
				return mv;
			}
			List<String> xval2 = new ArrayList<String>();
			List<List<BigDecimal>> list= new ArrayList<>();
			for (int i = 0; i < lines.size(); i++) {
				list.add(new ArrayList<BigDecimal>());
			}
			int j=0;
			for (int i = start; i <= end; i++) {
				String now=format.format(c.getTime());
				if(j<pd4.size()&&i==(int)pd4.get(j).get("count")){
					xval2.add("'"+pd4.get(j).getString("time")+"'");
					for (int k = 0; k < lines.size(); k++) {
						list.get(k).add((BigDecimal)pd4.get(j).get("num"+k));
					}
					j++;
				}else{
					xval2.add("'"+now+"'");
					for (int k = 0; k < lines.size(); k++) {
						list.get(k).add(new BigDecimal(0));
					}
				}
				c.add(Calendar.DATE, 1);
				
			}
			String s = process(list,lines3);
			mv.addObject("xval2", strlist2string(xval2));
			mv.addObject("lines2",lines2);
			mv.addObject("list",list);
			mv.addObject("data2",s);
			System.out.println();
		}
		
		pd.put("daysum", daysum);
		pd.put("daycount", daycount);
		pd.put("monthcount", monthcount);
		pd.put("monthsum", monthsum);	
		page.setPd(pd);
		mv.setViewName("system/statistic/statistic");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	
	
	
	
	
	
	
	/**表格
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/table")
	@ResponseBody
	public Map<String,Object> table(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Order");
		Map<String,Object> mv = new HashMap<String, Object>();
		PageData pd=this.getPageData();
		String key=pd.getString("key");
		//获取默认时间范围
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	    String nowDate=format.format(new Date());  
	    Calendar c = Calendar.getInstance();  
	    c.add(Calendar.MONTH, -1);
	    String postDate=format.format(c.getTime());
	    
	    
	    if("1".equals(key)){
		//处理表格1
		if(pd==null||pd.get("STARTTIME")==null||pd.get("ENDTIME")==null||"".equals(pd.get("STARTTIME"))||"".equals(pd.get("ENDTIME"))){
			pd.put("STARTTIME", postDate);
			pd.put("ENDTIME", nowDate);
		}
		if(pd!=null&&pd.get("STARTTIME")!=null&&pd.get("ENDTIME")!=null&&!"".equals(pd.get("STARTTIME"))&&!"".equals(pd.get("ENDTIME"))){
			//获取starttime
			Date date=format.parse((String)pd.get("STARTTIME"));
			c.setTime(date);
			
			List<PageData> pd2=null;//x轴和y轴免费用户
			List<PageData> pd3=null;//y轴付费用户
			PageData pdtime=null;//y轴付费用户
			List<String> xval = new ArrayList<String>();
			List<Integer> ymemfree = new ArrayList<Integer>();
			List<Integer> ymemfees = new ArrayList<Integer>();	
			pd2 =statisticService.memberstatic(pd);
			pd3 =statisticService.memberstatic2(pd);
			pdtime=statisticService.getstartend(pd);
			int start=((Long)pdtime.get("start")).intValue();
			int end=((Long)pdtime.get("end")).intValue();
			int j=0;
			int k=0;
			for (int i = start; i <= end; i++) {
				String now=format.format(c.getTime());
				if(j<pd2.size()&&k<pd3.size()&&i==(int)pd2.get(j).get("count")&&i==(int)pd3.get(k).get("count")){//既有免费又有付费
					xval.add(""+pd2.get(j).getString("time")+"");
					ymemfree.add(((Long)pd2.get(j).get("num")).intValue());
					ymemfees.add(((Long)pd3.get(k).get("num2")).intValue());
					j++;
					k++;
				}else if(j<pd2.size()&&i==(int)pd2.get(j).get("count")){		//只有免费
					xval.add(""+pd2.get(j).getString("time")+"");
					ymemfree.add(((Long)pd2.get(j).get("num")).intValue());
					ymemfees.add(0);
					j++;
				}else if(k<pd3.size()&&i==(int)pd3.get(k).get("count")){		//只有付费
					xval.add(""+pd3.get(k).getString("time")+"");
					ymemfree.add(0);
					ymemfees.add(((Long)pd3.get(k).get("num2")).intValue());
					k++;
				}else{															//没有用户
					xval.add(now);
					ymemfree.add(0);
					ymemfees.add(0);
				}	
				c.add(Calendar.DATE, 1);
			}
			String s= strlist2string(xval);
			mv.put("xval", xval);
			mv.put("ymemfree", ymemfree);
			mv.put("ymemfees", ymemfees);
		}
		
	    }
		
	    if("2".equals(key)){
		//处理表格2
		List<PageData> listAll = robotverService.listAll(pd);
		List<Integer> lines=new ArrayList<>();
		List<String> lines2=new ArrayList<>();
		List<String> lines3=new ArrayList<>();
		for (int i = 0; i < listAll.size(); i++) {
			lines.add((Integer)listAll.get(i).get("GRADE"));
			lines2.add("'"+listAll.get(i).getString("NAME")+"'");
			lines3.add(listAll.get(i).getString("NAME"));
		}
		pd.put("lines", lines);
		
		
		if(pd==null||pd.get("STARTTIME2")==null||pd.get("ENDTIME2")==null||"".equals(pd.get("STARTTIME2"))||"".equals(pd.get("ENDTIME2"))){		
			pd.put("STARTTIME2", postDate);
			pd.put("ENDTIME2", nowDate);
		}
		if(pd!=null&&pd.get("STARTTIME2")!=null&&pd.get("ENDTIME2")!=null&&!"".equals(pd.get("STARTTIME2"))&&!"".equals(pd.get("ENDTIME2"))){		
			//获取starttime2
			Date date=format.parse((String)pd.get("STARTTIME2"));
			c.setTime(date);
			
			List<PageData> pd4=statisticService.incomestatic(pd);			//访问数据库
			PageData pdtime=statisticService.getstartend2(pd);
			int start=((Long)pdtime.get("start")).intValue();
			int end=((Long)pdtime.get("end")).intValue();
			List<String> xval2 = new ArrayList<String>();
			List<List<BigDecimal>> list= new ArrayList<>();
			for (int i = 0; i < lines.size(); i++) {
				list.add(new ArrayList<BigDecimal>());
			}
			int j=0;
			for (int i = start; i <= end; i++) {
				String now=format.format(c.getTime());
				if(j<pd4.size()&&i==(int)pd4.get(j).get("count")){
					xval2.add(""+pd4.get(j).getString("time")+"");
					for (int k = 0; k < lines.size(); k++) {
						list.get(k).add((BigDecimal)pd4.get(j).get("num"+k));
					}
					j++;
				}else{
					xval2.add(now);
					for (int k = 0; k < lines.size(); k++) {
						list.get(k).add(new BigDecimal(0));
					}
				}
				c.add(Calendar.DATE, 1);
			}
			
			String s = process(list,lines3);
			mv.put("xval2", xval2);
			mv.put("lines2",lines2);
			mv.put("list",list);
			mv.put("data2",s);
			System.out.println();
		}
	    }
		
		mv.put("pd", pd);
		//mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	
	
	
	/**首页
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list2")
	public ModelAndView list2(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Order");
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		
		//5个统计数据
		BigDecimal todaysum;
		if(statisticService.todaysum(pd)!=null){
			todaysum = (BigDecimal) (statisticService.todaysum(pd).get("todaysum"));
		}else{
			todaysum=new BigDecimal(0);
		}
		
		Long todayordercount = (Long) statisticService.todayordercount(pd).get("todayordercount");
		Long todaynewmemcount = (Long) statisticService.todaynewmemcount(pd).get("todaynewmemcount");
		Long todaycostmemcount = (Long) statisticService.todaycostmemcount(pd).get("todaycostmemcount");
		Long todayusememcount = (Long) statisticService.todayusememcount(pd).get("todayusememcount");
	
		//获取默认时间范围
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	    String nowDate=format.format(new Date());  
	    Calendar c = Calendar.getInstance();  
	    c.add(Calendar.MONTH, -1);
	    String postDate=format.format(c.getTime());
	    
		//处理表格1
		if(pd==null||pd.get("STARTTIME")==null||pd.get("ENDTIME")==null||"".equals(pd.get("STARTTIME"))||"".equals(pd.get("ENDTIME"))){
			pd.put("STARTTIME", postDate);
			pd.put("ENDTIME", nowDate);
		}
		if(pd!=null&&pd.get("STARTTIME")!=null&&pd.get("ENDTIME")!=null&&!"".equals(pd.get("STARTTIME"))&&!"".equals(pd.get("ENDTIME"))){
			//获取starttime
			Date date=format.parse((String)pd.get("STARTTIME"));
			c.setTime(date);
			
			List<PageData> pd2=null;//x轴和y轴免费用户
			List<PageData> pd3=null;//y轴付费用户
			List<String> xval = new ArrayList<String>();
			List<Integer> ymemfree = new ArrayList<Integer>();
			List<Integer> ymemfees = new ArrayList<Integer>();	
			pd2 =statisticService.memberstatic(pd);
			pd3=statisticService.memberstatic2(pd);
			Integer start=null;
			Integer end=null;	
			PageData pdtime=statisticService.getstartend(pd);
			try {
				start=((Long)pdtime.get("start")).intValue();
				end=((Long)pdtime.get("end")).intValue();	
				mv.addObject("tag", 0);
			} catch (Exception e) {
				mv.addObject("tag", 1);
				mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
				mv.setViewName("system/statistic/index");
				return mv;
			}
			
			int j=0;
			int k=0;
			for (int i = start; i <= end; i++) {
				String now=format.format(c.getTime());
				if(j<pd2.size()&&k<pd3.size()&&i==(int)pd2.get(j).get("count")&&i==(int)pd3.get(k).get("count")){//既有免费又有付费
					xval.add("'"+pd2.get(j).getString("time")+"'");
					ymemfree.add(((Long)pd2.get(j).get("num")).intValue());
					ymemfees.add(((Long)pd3.get(k).get("num2")).intValue());
					j++;
					k++;
				}else if(j<pd2.size()&&i==(int)pd2.get(j).get("count")){		//只有免费
					xval.add("'"+pd2.get(j).getString("time")+"'");
					ymemfree.add(((Long)pd2.get(j).get("num")).intValue());
					ymemfees.add(0);
					j++;
				}else if(k<pd3.size()&&i==(int)pd3.get(k).get("count")){		//只有付费
					xval.add("'"+pd3.get(k).getString("time")+"'");
					ymemfree.add(0);
					ymemfees.add(((Long)pd3.get(k).get("num2")).intValue());
					k++;
				}else{															//没有用户
					xval.add("'"+now+"'");
					ymemfree.add(0);
					ymemfees.add(0);
				}	
				c.add(Calendar.DATE, 1);
			}
			String s= strlist2string(xval);
			mv.addObject("xval", strlist2string(xval));
			mv.addObject("ymemfree", ymemfree);
			mv.addObject("ymemfees", ymemfees);
		}
		pd.put("todaysum", todaysum);
		
		pd.put("todayordercount", todayordercount);
		pd.put("todaynewmemcount", todaynewmemcount);
		pd.put("todaycostmemcount", todaycostmemcount);	
		pd.put("todayusememcount", todayusememcount);	
		mv.setViewName("system/statistic/index");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	private Object intlist2string(List<Integer> ymemfees) {
		String s="[";
		int i;
		for ( i = 0; i < ymemfees.size()-1; i++) {
			//s =s+"'"+xval.get(i)+"'"+",";
			s =s+ymemfees.get(i)+",";
		}
		//s=s+"'"+xval.get(xval.size()-1)+"'"+"]";
		s=s+ymemfees.get(ymemfees.size()-1)+"]";
		return s;
	}

	private String process(List<List<BigDecimal>> list, List<String> lines) {
		String s="";
		for (int i = 0; i < list.size(); i++) {
			s=s+"{\n";
			s=s+"name:'"+lines.get(i)+"',\n";
			s=s+"type:'line',\ndata:";
			s=s+declist2string(list.get(i))+"\n},";
		}
		return s;
		
	}
	
	

	
	
	
	

	
	private String strlist2string(List<String> xval) {
		String s="[";
		int i;
		for ( i = 0; i < xval.size()-1; i++) {
			//s =s+"'"+xval.get(i)+"'"+",";
			s =s+xval.get(i)+",";
		}
		//s=s+"'"+xval.get(xval.size()-1)+"'"+"]";
		s=s+xval.get(xval.size()-1)+"]";
		return s;
	}
	
	private String declist2string(List<BigDecimal> xval) {
		String s="[";
		int i;
		for ( i = 0; i < xval.size()-1; i++) {
			//s =s+"'"+xval.get(i)+"'"+",";
			s =s+xval.get(i)+",";
		}
		//s=s+"'"+xval.get(xval.size()-1)+"'"+"]";
		s=s+xval.get(xval.size()-1)+"]";
		return s;
	}




	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	
	
	
}
