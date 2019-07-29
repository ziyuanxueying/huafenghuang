package com.primecloud.huafenghuang.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * TODO 日期处理集
 *
 **/
@SuppressLint("SimpleDateFormat")
public class DatesUtils {
	
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	
	private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy年MM月");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater4 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy年MM月dd日");
		}
	};


	private static String dateStr;

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		return toDate(sdate, dateFormater.get());
	}

	public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getDateString(Date date) {
		return dateFormater.get().format(date);
	}
	
	public static String change(int second) {
		int d = 0;
		int s = 0;
		d = second / 60;
		if (second % 60 != 0) {
			s = second % 60;
		}
		if (s < 10) {
			return d + ":0" + s;
		} else {
			return d + ":" + s;
		}
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = null;
		if (TimeZoneUtil.isInEasternEightZones())
		{
			time = toDate(sdate,dateFormater.get());
		}
		else
		{
			time = TimeZoneUtil.transformTime(toDate(sdate,dateFormater.get()), TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
		}


		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			int points=(int) ((cal.getTimeInMillis() - time.getTime()) / 60000);
			if (hour == 0)
			{
				if (points<1) {
					ftime ="刚刚";
				}
				else
				{
					ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";

				}
			}
			else
			{
				ftime = hour + "小时前";
			}
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			int points=(int) ((cal.getTimeInMillis() - time.getTime()) / 60000);
			if (hour == 0)
			if (points<1) {
				ftime ="刚刚";
			}
			else
			{
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";

			}
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天 ";
		} else if (days > 2 && days < 31) {
			ftime = days + "天前";
		} else if (days >= 31 && days/31/12<1) {
			ftime = (days/31)+"个月前";
		} else if (days>=31*12){
			ftime = (days/31/12)+"年前";
		} else {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}
	


	public static int  showDay(String sdate)
	{
		Date time = null;

		if (TimeZoneUtil.isInEasternEightZones())
			time = toDate(sdate);
		else
			time = TimeZoneUtil.transformTime(toDate(sdate), TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());

		if (time == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();
		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		return days;
	}



	
	/**
	 * 以年的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String year_time(String sdate) {
		String time = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		
		String   date   =   df.format(new   Date());
		try
		{
		   Date d1 = df.parse(date);
		   
		   long diff = d1.getTime() - Long.parseLong(sdate)*1000;//这样得到的差值是微秒级别


		   long days = diff /(1000 * 60 * 60 * 24);
		   long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
		   long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
		   
		   if (days>365) {
			  int num=(int) (days/365);
			  time=num+"年前";	 
		   }
		   else
		   {
			   Date longdate = new Date(Long.parseLong(sdate)*1000);
			   time=date4String(longdate);
		   }
		}
		catch (Exception e)
		{
		}
		return time;
		
	}

	@SuppressWarnings("deprecation")
	public static String friendly_time2(String sdate) {
		String res = "";
		if (StringUtils.isEmpty(sdate))
			return "";

		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		String currentData = getDataTime("MM-dd");
		int currentDay = ConvertUtils.toInt(currentData.substring(3));
		int currentMoth = ConvertUtils.toInt(currentData.substring(0, 2));

		int sMoth = ConvertUtils.toInt(sdate.substring(5, 7));
		int sDay = ConvertUtils.toInt(sdate.substring(8, 10));
		int sYear = ConvertUtils.toInt(sdate.substring(0, 4));
		Date dt = new Date(sYear, sMoth - 1, sDay - 1);

		if (sDay == currentDay && sMoth == currentMoth) {
			res = "今天 / " + weekDays[getWeekOfDate(new Date())];
		} else if (sDay == currentDay + 1 && sMoth == currentMoth) {
			res = "昨天 / " + weekDays[(getWeekOfDate(new Date()) + 6) % 7];
		} else {
			if (sMoth < 10) {
				res = "0";
			}
			res += sMoth + "/";
			if (sDay < 10) {
				res += "0";
			}
			res += sDay + " / " + weekDays[getWeekOfDate(dt)];
		}

		return res;
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static int getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return w;
	}
	
	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int getYearOfDate(){
		Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        System.out.println(c.get(Calendar.YEAR));
        return c.get(Calendar.YEAR);
	}
	
	public static String date2String(Date date){
		return dateFormater3.get().format(date);
	}

	public static Date String2Date(String date){
		return toDate(date, dateFormater3.get());
	}

	public static String date3String(Date date){
		return dateFormater2.get().format(date);
	}

	public static Date String3Date(String date){
		return toDate(date, dateFormater2.get());
	}

	public static Date String4Date(String date){
		return toDate(date, dateFormater4.get());
	}

	public static String date4String(Date date){
		return dateFormater4.get().format(date);
	}
	
	/**
	 * 返回当前系统时间
	 */
	public static String getDataTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 返回long类型的今天的日期
	 * 
	 * @return
	 */
	public static long getToday() {
		Calendar cal = Calendar.getInstance();
		String curDate = dateFormater2.get().format(cal.getTime());
		curDate = curDate.replace("-", "");
		return Long.parseLong(curDate);
	}
	
	/**
	 * 获取当前时间字符串
	 * 
	 * @return
	 */
	public static String getCurTimeStr() {
		Calendar cal = Calendar.getInstance();
		String curDate = dateFormater.get().format(cal.getTime());
		return curDate;
	}

	/***
	 * 计算两个时间差，返回的是的秒s
	 * 
	 * @author 火蚁 2015-2-9 下午4:50:06
	 * 
	 * @return long
	 * @param dete1
	 * @param date2
	 * @return
	 */
	public static long calDateDifferent(String dete1, String date2) {

		long diff = 0;

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = dateFormater.get().parse(dete1);
			d2 = dateFormater.get().parse(date2);
			// 毫秒ms
			diff = d2.getTime() - d1.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return diff / 1000;
	}
	
	/**
	 * 时间格式化
	 * 
	 * @param dateStr
	 * @return
	 */
	public static String parseMMddHHmm(String dateStr){
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			return new SimpleDateFormat("MM-dd HH:mm").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr; 
		
	}	
}
