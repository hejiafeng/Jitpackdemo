package com.example.mylibrary;


import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtils {

	private static final String FORMAT_0 = "yyyy";
	public static final String FORMAT_1 = "yyyy-MM-dd";
	public static final String FORMAT_2 = "yyyy-MM-dd HH:mm";
	private static final String FORMAT_3 = "HH:mm";
	private static final String FORMAT_4 = "yyyyMMddHHmmss";
	public static final String FORMAT_5 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_6 = "yyyy-MM";
	public static final String FORMAT_7 = "MM-dd";
	public static final String FORMAT_8 = "MM月dd日";


	public static String convertSecond2Minute(int second) {
		int minute = second / 60;
		int secondWithMinute = second % 60;

		if (minute == 0) {
			return second + "秒";
		}
		else if (secondWithMinute == 0) {
			return minute + "分钟";
		}
		else {
			return minute + "分" + secondWithMinute + "秒";
		}
	}

	public static Girl getGirl(){
		Girl girl = new Girl();
		girl.setAge(18);
		girl.setName("秀儿");
		return girl;
	}


	/**
	 * 获取当前月份的前几个月
	 */
	public static List<String> getPrevMonth(int count) {
		List<String> monthArray = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_6);
		Calendar now = Calendar.getInstance();
		for (int i = count; i > 0; i--) {
			monthArray.add(sdf.format(now.getTime()));
			now.add(Calendar.MONTH, -1);
		}
		return monthArray;
	}

	public static long getTimestampWithoutSecond() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return now.getTime().getTime();
	}

	public static String convertFormDateFormat(String formFormat) {
		//DYYYY-MM-DD HH:NN:SS  --->  yyyy-MM-dd HH:mm:ss



		if (!formFormat.startsWith("D")) {
			return formFormat;
		}

		StringBuffer dateFormat = new StringBuffer();
		if (formFormat.contains("YYYY")) {
			dateFormat.append("yyyy");
		}
		if (formFormat.contains("MM")) {
			dateFormat.append("-MM");
		}
		if (formFormat.contains("DD")) {
			dateFormat.append("-dd");
		}
		if (formFormat.contains("HH")) {
			dateFormat.append(" HH");
		}
		if (formFormat.contains("NN")) {
			dateFormat.append(":mm");
		}
		if (formFormat.contains("SS")) {
			dateFormat.append(":ss");
		}

		System.out.println("dateFormat==" + dateFormat);
		return dateFormat.toString();
	}



	/**
	 * 将dateTime转换化为format格式
	 */
	public static String reFormatDateTime(String dateTime, String format) {
		Log.e("Test", "dateTime=" + dateTime + " format===" + format);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date d = sdf.parse(dateTime);
			return sdf.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
			Log.e("Test", "dateTime=" + dateTime + " format===" + format);
		}
		return "";
	}

	public static List<String> getLastYear(int duration) {
		List<String> yearList = new ArrayList<>();
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_0);
		for (int i = 0; i < duration; i++) {
			now.add(Calendar.YEAR, -1);
			yearList.add(0, sdf.format(now.getTime()));
		}
		yearList.add(getYear());
		return yearList;
	}

	/****
	 * 传入当前日期 ，返回具体日期增加一个月。
	 * @param date 日期(2017-04-13)
	 * @return 2017-05-13
	 * @throws ParseException
	 */
	public static String subMonth(int sum) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date dt = getNowDate();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);
		rightNow.add(Calendar.MONTH, sum);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	/**
	 * Return the current date.
	 *
	 * @return the current date
	 */
	public static Date getNowDate() {
		return new Date();
	}



	public static long getTimestamp(String dateStr, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return getTimestamp();
		}
	}

	public static long getTimestamp() {
		return new Date().getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 */
	public static int daysBetween(String from, String to) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_1);
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(from));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(to));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Integer.parseInt(String.valueOf(between_days));
		} catch (ParseException e) {
			e.printStackTrace();
			return 1;
		}
	}

	public static Date getData(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_5);
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	public static String getData(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_5);
		return sdf.format(time);
	}

	//计算两个日期间隔
	public static String getTime(Date currentTime, Date firstTime) {
		Log.i("dataTime", "当前时间：" + getData(currentTime));
		Log.i("dataTime", "之前时间：" + getData(firstTime));
		long diff = currentTime.getTime() - firstTime.getTime();//这样得到的差值是微秒级别
		Calendar currentTimes = dataToCalendar(currentTime);//当前系统时间转Calendar类型
		Calendar firstTimes = dataToCalendar(firstTime);//查询的数据时间转Calendar类型
		long year = currentTimes.get(Calendar.YEAR) - firstTimes.get(Calendar.YEAR);//获取年
		long month = currentTimes.get(Calendar.MONTH) - firstTimes.get(Calendar.MONTH);
		if (year != 0) {//跨年显示月
			month = (month + (year * 12));
			return Math.abs(month) + "月" + getTimeText(month);
		}

		long days = diff / (1000 * 60 * 60 * 24);

		if (days >= 30 || days <= -30) {//超过30天显示月
			month = days / 30;//获取月
			return Math.abs(month) + "月" + getTimeText(month);
		}

		long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); //获取时
		long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60); //获取分钟
		long s = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);//获取秒

		String CountTime = "" + year + "年" + month + "月" + days + "天" + hours + "小时" + minutes + "分" + s + "秒";

		Log.i("dataTime", CountTime);

		if (days != 0) {
			return Math.abs(days) + "天" + getTimeText(days);
		}
		else if (hours != 0) {
			return Math.abs(hours) + "小时" + getTimeText(hours);
		}
		else if (minutes != 0) {
			return Math.abs(minutes) + "分" + getTimeText(minutes);
		}
		else if (s != 0) {
			return Math.abs(s) + "秒" + getTimeText(s);
		}
		return "";
	}

	private static String getTimeText(long time) {
		return time < 0 ? "后" : "前";
	}

	private static Calendar dataToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static String getNow() {
		return new SimpleDateFormat(FORMAT_2).format(new Date());
	}

	public static String getNowYearMonth() {
		return new SimpleDateFormat(FORMAT_6).format(new Date());
	}


	public static String getYear() {
		return new SimpleDateFormat(FORMAT_0).format(new Date());
	}

	public static String getNow2() {
		return new SimpleDateFormat(FORMAT_3).format(new Date());
	}

	public static String getNow3() {
		return new SimpleDateFormat(FORMAT_4).format(new Date());
	}

	public static String getToday() {
		return new SimpleDateFormat(FORMAT_1).format(new Date());
	}

	public static String getToday(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}


	public static String getTodayByFormate7() {
		return new SimpleDateFormat(FORMAT_7).format(new Date());
	}

	public static String getTomorrowByFormate7() {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(FORMAT_7).format(tomorrow.getTime());
	}


	public static String getTomorrow() {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(FORMAT_1).format(tomorrow.getTime());
	}


	public static String getTomorrow(String format) {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(format).format(tomorrow.getTime());
	}

	public static int getAfterDate(int count) {
		Calendar after = Calendar.getInstance();
		after.add(Calendar.DAY_OF_MONTH, count);
		return after.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * From的时间小于To的时间
	 */
	public static boolean isFromBeforeTo(String from, String to, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date fromDate = sdf.parse(from);
			Date toDate = sdf.parse(to);
			return fromDate.before(toDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return true;
		}
	}

	public static String getDateStr(int index) {
		String str;
		if (index < 10) {
			str = "0" + index;
		}
		else {
			str = "" + index;
		}
		return str;
	}

	public static String getWeek(Date date) {
		String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	public static String getWeek(String day) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_1);
			return getWeek(sdf.parse(day));
		} catch (ParseException e) {
			e.printStackTrace();
			return "周一";
		}
	}

	public static final int MORNING = 0;
	public static final int AFTERNOON = 1;
	public static final int NIGHT = 2;

	public static int getTimeQuantum() {
		String hour_s = new SimpleDateFormat("HH").format(new Date());
		int hour = Integer.parseInt(hour_s);
		if (0 < hour && hour < 12) {
			return MORNING;
		}
		else if (hour < 18) {
			return AFTERNOON;
		}
		else if (hour < 24) {
			return NIGHT;
		}
		else {
			return MORNING;
		}

	}

	//获取当前日期连续一周的日期
	public static List<String> getDateToWeek(Date date) {
		List<String> dateWeekList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		String time;
		int flag; //flag用来存取与当天日期的相差数
		Calendar cal;
		for (int i = 1; i < 8; i++) {
			cal = Calendar.getInstance();
			cal.setTime(date);//在日历中找到当前日期
			flag = -cal.get(Calendar.DAY_OF_WEEK);//当前日期时本周第几天，默认按照西方惯例上周星期天为第一天
			cal.add(Calendar.DATE, flag + i);//根据循环。当天与上周星期天和本周一到周五相差的天数
			time = sdf.format(cal.getTime());//转化格式
			dateWeekList.add(time);//存入list
		}
		return dateWeekList;
	}

	//获取当前日期
	public static String getDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		return sdf.format(date.getTime());//转化格式;
	}

	public static String getFormatDate(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_1);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat(FORMAT_7);
		try {
			Date date = dateFormat.parse(dateStr);
			return dateFormat1.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getNowSec() {
		return new SimpleDateFormat(FORMAT_4).format(new Date());
	}
}
