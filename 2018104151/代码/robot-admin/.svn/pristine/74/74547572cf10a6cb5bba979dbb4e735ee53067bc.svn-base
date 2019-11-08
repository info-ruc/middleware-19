package com.zoneyet.robot.admin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MyDateUtils {
	
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private static final Calendar calendar = new GregorianCalendar();
	
	/**
	 * 获取当前日期 （2018-01-18）
	 * @return
	 */
	public static String getTodayDateString() {
		Date now = new Date(); 
	    calendar.setTime(now);
	    return formatter.format(now);
	}
	
	//public static String getDateString()
	
	/**
	 * 获取与当前日期间隔 interval (可正、0、负）天数的日期
	 * 格式：yyyy-mm-dd
	 * @param interval
	 * @return
	 */
	public static String getDateString(int interval) {
		Date now = new Date(); 
	    calendar.setTime(now);
	    calendar.add(calendar.DATE, interval);
	    Date intervalDay=calendar.getTime(); //这个时间就是日期往前/后推 interval 天的结果
	    return formatter.format(intervalDay);
	}
	
	/**
	 * 获取与某个日期间隔 interval (可正、0、负）天数的日期
	 * 格式：yyyy-mm-dd
	 * @param interval
	 * @return
	 * @throws ParseException 
	 */
	public static String getDateString(String dateString,int interval) throws ParseException {
		Date date = formatter.parse(dateString); 
	    calendar.setTime(date);
	    calendar.add(calendar.DATE, interval);
	    Date intervalDay=calendar.getTime(); //这个时间就是date往前/后推 interval 天的结果
	    return formatter.format(intervalDay);
	}
	
	
	/**
	 * 获取与当前日期往前推 interval (只能为负)天数的日期字符串的集合
	 * 格式：yyyy-dd-mm
	 * @param interval
	 * @return
	 */
	public static List<String> getDaysString(int interval){
		List<String> resultList = new ArrayList<>();
		for(int i=interval; i<=0;i++) {
			resultList.add(getDateString(i));
		}
		return resultList;
	}
	
	/**
	 * 获取startDate 到月底的日期字符串集合
	 * @param startDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getStartDay2EndString(String startDate) throws ParseException{
		int dayOfMonth = getDayOfMonth(startDate);
		int sumDays = getDaysOfMonth(startDate);
		
		//startDate 距月底间隔 interval 天
		int interval = sumDays - dayOfMonth;//startDate 
		List<String> resultList = new ArrayList<>();
		for(int i=0;i<=interval; i++) {
			resultList.add(getDateString(startDate, i));
		}
		return resultList;
	}
	
	
	/**
	 * 获取月初到 endDate 的日期字符串集合
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static List<String> getStart2EndDayString(String endDate) throws ParseException{
		int dayOfMonth = getDayOfMonth(endDate);
		
		//endDate 距月初间隔 interval 天
		int interval = dayOfMonth -1;//startDate 
		List<String> resultList = new ArrayList<>();
		for(int i=interval ;i>=0; i--) {
			resultList.add(getDateString(endDate, -i));
		}
		return resultList;
	}
	
	
	/**
	 * 获取 date 为第几个月
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthOfYear(String date) throws ParseException {
		Date date1 = formatter.parse(date);
        calendar.setTime(date1);  
        return calendar.get(Calendar.MONTH);
	}
	
	/**
	 * 获取开始和结束日期之间的日期字符串(开始可以大于结束日期)
	 * 注意：格式为 yyyy--MM-dd,且开始和结束日期可以跨月
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	/*public static List<String> getStartAndEndDaysString(String startDate, String endDate) throws ParseException{
		int interval = MyDateUtils.getDaysBetweenStartAndEnd(startDate, endDate);
		System.out.println("@"+interval);
		List<String> resultList = new ArrayList<>();
		for(int i=0 ;i<=interval; i++) {
			resultList.add(getDateString(startDate, i));
		}
		return resultList;
	}*/
	
	/**
	 * 获取 startDate 和 endDate 间隔的天数
	 * 例如 ："2018-01-01", "2018-01-22" 返回21
	 * 或 ："2018-01-22", "2018-01-01" 返回21
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	/*public static int getDaysBetweenStartAndEnd(String startDate, String endDate) throws ParseException {
		if(startDate.compareTo(endDate)>0) {
			String temp = startDate;
			startDate = endDate;
			endDate = temp;
		}
		Calendar start = new GregorianCalendar();
		Date date1 = formatter.parse(startDate);
		start.setTime(date1); 
		
		Calendar end = new GregorianCalendar();
		Date date2 = formatter.parse(endDate);
		end.setTime(date2); 
		return DateUtils.getDaysBetween(start, end)-1;
	}*/
	
	/**
	 * 获取当前日期为该月的第几天
	 * @return
	 */
	public static int getDayOfMonth() {
		Date date = new Date();
        calendar.setTime(date);  
        return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前日期为该月的第几天
	 * @return
	 * @throws ParseException 
	 */
	public static int getDayOfMonth(String date) throws ParseException {
		Date date1 = formatter.parse(date);
        calendar.setTime(date1);  
        return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取据当前日期interval天数的日期为该月的第几天
	 * @param interval
	 * @return
	 */
	public static int getDayOfMonth(int interval) {
		Date date = new Date();
        calendar.setTime(date); 
        calendar.add(calendar.DATE, interval);
        return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前日期月份的天数
	 * @param date
	 * @return
	 */
	public static int getDaysOfMonth() {  
		Date date = new Date();
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    } 
	
	/**
	 * 获取某个日期所在月份的天数
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static int getDaysOfMonth(String dateString) throws ParseException {  
		Date date = formatter.parse(dateString);
        calendar.setTime(date);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    } 
	
	/**
	 * 获取当前日期往前推 interval （为负）天所在月份的天数
	 * @param interval
	 * @return
	 */
	public static int getDaysOfMonth(int interval) {  
		Date now = new Date(); 
	    calendar.setTime(now);
	    calendar.add(calendar.DATE, interval);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
    } 
	
	public static void main(String[] args) throws ParseException {
		System.out.println(MyDateUtils.getTodayDateString());
		System.out.println(MyDateUtils.getDateString(-3));
		System.out.println(MyDateUtils.getDaysString(-30).toString());
		
        System.out.println(getDaysOfMonth());
        System.out.println(getDaysOfMonth(-60));
        System.out.println(getDaysOfMonth(30));
        System.out.println(getDayOfMonth());
        System.out.println(getDayOfMonth(-30));
        System.out.println(getStartDay2EndString("2018-02-01").toString());
        System.out.println(getStart2EndDayString("2018-02-13").toString());
        /*System.out.println(getStartAndEndDaysString("2017-12-01", "2018-01-05").toString());*/
       /* System.out.println(getDaysBetweenStartAndEnd("2018-01-22", "2018-01-01"));*/
	}
}

