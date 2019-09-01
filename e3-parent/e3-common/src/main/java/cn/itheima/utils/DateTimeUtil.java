package cn.itheima.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateTimeUtil {
	public static final String _DDMMYY = "ddMMyy";
	public static final String _MMDDYY = "MMddyy";
	public static final String _YYMMDD = "yyMMdd";
	public static final String _YY_MM_DD = "yy-MM-dd";
	public static final String _YYYYMMDD = "yyyyMMdd";
	public static final String _YYYY_MM_DD = "yyyy-MM-dd";
	public static final String _YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String _YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String _YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String _YYYYMMDDHHMM = "yyyyMMddHHmm";
	public static final String _YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	public static final String _HHMM = "HHmm";
	public static final String _HHMMSS = "HHmmss";
	public static final String _HH_MM_SS = "HH:mm:ss";
	public static final String _HH_MM_SS_SSS = "HH:mm:ss.SSS";

	public static int getInteger(Date date, int calendarDateType) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(calendarDateType);
	}

	public static String parse2String(Date date) {
		return parse2String(date, "yyyy-MM-dd");
	}

	public static String parse2String(Date date, String format) {
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd";
		}
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return date != null ? formater.format(date) : "";
	}

	public static String parse2String(String dateStr, String dateFormat, String timeStr, String timeFormat,
			String format) {
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = "yyyy-MM-dd";
		}
		if (StringUtils.isBlank(timeFormat)) {
			format = "HH:mm:ss";
		}
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		if ((StringUtils.isBlank(dateStr)) || (StringUtils.isBlank(timeStr))) {
			return "";
		}
		Date date = parse2Date(dateStr, dateFormat);
		Date time = parse2Date(timeStr, timeFormat);

		Calendar timeal = Calendar.getInstance();
		timeal.setTime(time);

		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		dateCal.set(11, timeal.get(11));
		dateCal.set(12, timeal.get(12));
		dateCal.set(13, timeal.get(13));

		return parse2String(dateCal.getTime(), format);
	}

	public static Date parse2Date(Date date) {
		return parse2Date(date, "yyyy-MM-dd");
	}

	public static Date parse2Date(String date) {
		return parse2Date(date, "yyyy-MM-dd");
	}

	public static Date parse2DateByFormat(String format) {
		return parse2Date(new Date(), format);
	}

	public static Date parse2Date(Date date, String format) {
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd";
		}
		if (date == null) {
			return null;
		}
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			date = formater.parse(formater.format(date));
		} catch (ParseException localParseException) {
		}
		return date;
	}

	public static Date parse2Date(String dateStr, String format) {
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd";
		}
		if (StringUtils.isBlank(dateStr)) {
			dateStr = getDateTimeStr(format);
		}
		SimpleDateFormat formater = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = formater.parse(dateStr);
		} catch (ParseException localParseException) {
		}
		return date;
	}

	public static Date parse2Date(String dateStr, String timeStr, String format) {
		if (StringUtils.isBlank(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		if ((StringUtils.isBlank(dateStr)) || (StringUtils.isBlank(timeStr))) {
			return null;
		}
		Date date = parse2Date(dateStr, "ddMMyy");
		Date time = parse2Date(timeStr, "HHmmss");

		Calendar timeal = Calendar.getInstance();
		timeal.setTime(time);

		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		dateCal.set(11, timeal.get(11));
		dateCal.set(12, timeal.get(12));
		dateCal.set(13, timeal.get(13));

		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			date = formater.parse(parse2String(dateCal.getTime(), format));
		} catch (ParseException localParseException) {
		}
		return date;
	}

	public static Date getDate_YYYYMMDD() {
		return parse2DateByFormat("yyyyMMdd");
	}

	public static String getDateStr_YYYYMMDD() {
		return getDateTimeStr("yyyyMMdd");
	}

	public static Date getDate_YYYY_MM_DD() {
		return parse2DateByFormat("yyyy-MM-dd");
	}

	public static String getDateStr_YYYY_MM_DD() {
		return getDateTimeStr("yyyy-MM-dd");
	}

	public static String getTimeStr_HHMM() {
		return getDateTimeStr("HHmm");
	}

	public static String getTimeStr_HHMMSS() {
		return getDateTimeStr("HHmmss");
	}

	public static String getTimeStr_HH_MM_SS() {
		return getDateTimeStr("HH:mm:ss");
	}

	public static String getTimeStr_HH_MM_SS_SSS() {
		return getDateTimeStr("HH:mm:ss.SSS");
	}

	public static Date getDateTime_YYYYMMDDHHMMSS() {
		return parse2DateByFormat("yyyyMMddHHmmss");
	}

	public static String getDateTimeStr_YYYYMMDDHHMMSS() {
		return getDateTimeStr("yyyyMMddHHmmss");
	}

	public static Date getDateTime_YYYYMMDDHHMMSSSSS() {
		return parse2DateByFormat("yyyyMMddHHmmssSSS");
	}

	public static String getDateTimeStr_YYYYMMDDHHMMSSSSS() {
		return getDateTimeStr("yyyyMMddHHmmssSSS");
	}

	public static Date getDateTime_YYYY_MM_DD_HH_MM_SS() {
		return parse2DateByFormat("yyyy-MM-dd HH:mm:ss");
	}

	public static String getDateTimeStr_YYYY_MM_DD_HH_MM_SS() {
		return getDateTimeStr("yyyy-MM-dd HH:mm:ss");
	}

	public static Date getDateTime_YYYY_MM_DD_HH_MM_SS_SSS() {
		return parse2DateByFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static String getDateTimeStr_YYYY_MM_DD_HH_MM_SS_SSS() {
		return getDateTimeStr("yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static String getDateTimeStr(String format) {
		return parse2String(new Date(), format);
	}

	public static String getFirstDayByCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(5, 1);
		return parse2String(calendar.getTime(), "yyyy-MM-dd");
	}

	public static String getLastDayByCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(5, 1);
		calendar.roll(5, -1);
		return parse2String(calendar.getTime(), "yyyy-MM-dd");
	}

	public static int getYear() {
		return getYear(new Date());
	}

	public static int getYear(Date date) {
		return getInteger(date, 1);
	}

	public static int getMonth() {
		return getMonth(new Date());
	}

	public static int getMonth(Date date) {
		return getInteger(date, 2);
	}

	public static int getIntervalDays(Date smallDate, Date bigDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		smallDate = simpleDateFormat.parse(simpleDateFormat.format(smallDate));
		bigDate = simpleDateFormat.parse(simpleDateFormat.format(bigDate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(smallDate);
		long time1 = calendar.getTimeInMillis();
		calendar.setTime(bigDate);
		long time2 = calendar.getTimeInMillis();
		long between_days = Long.parseLong("0");
		if (time2 > time1) {
			between_days = (time2 - time1) / 86400000L;
		} else {
			between_days = (time1 - time2) / 86400000L;
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static int getDaysBetween(Date smallDate, Date bigDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		smallDate = simpleDateFormat.parse(simpleDateFormat.format(smallDate));
		bigDate = simpleDateFormat.parse(simpleDateFormat.format(bigDate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(smallDate);
		long time1 = calendar.getTimeInMillis();
		calendar.setTime(bigDate);
		long time2 = calendar.getTimeInMillis();
		long between_days = Long.parseLong("0");

		between_days = (time2 - time1) / 86400000L;

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static int getMonthDiff(Date startDate, Date endDate) throws ParseException {
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(startDate);
		int sYear = starCal.get(1);
		int sMonth = starCal.get(2) + 1;
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		int eYear = endCal.get(1);
		int eMonth = endCal.get(2) + 1;
		int monthday = (eYear - sYear) * 12 + eMonth - sMonth;

		starCal.add(2, monthday);
		if (starCal.compareTo(endCal) > 0) {
			monthday -= 1;
		}
		return monthday;
	}

	public static Date addDateTime(int value, int calendarDateType) {
		return addDateTime(new Date(), value, calendarDateType);
	}

	public static Date addDateTime(Date date, int value, int calendarDateType) {
		if (date == null) {
			return new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendarDateType, value);
		return calendar.getTime();
	}

	public static Date getDateInSomeMonths(Date date, int months) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar nextDate = Calendar.getInstance();
		nextDate.setTime(simpleDateFormat.parse(simpleDateFormat.format(date)));
		nextDate.add(2, months);
		return nextDate.getTime();
	}

	public static List<Date> getPaymentDateList(Date successDate, int period) throws ParseException {
		successDate = parse2Date(successDate);
		List<Date> paymentDateList = new ArrayList<Date>();
		for (int month = 1; month <= period; month++) {
			paymentDateList.add(getDateInSomeMonths(successDate, month));
		}
		return paymentDateList;
	}

	public static boolean checkTwoDateIsEqual(Date startDate, Date endDate) throws ParseException {
		startDate = parse2Date(startDate);
		endDate = parse2Date(endDate);
		int monthDistance = getMonthDiff(startDate, endDate);
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(startDate);
		starCal.add(2, monthDistance);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		return starCal.compareTo(endCal) == 0;
	}

	public static List<Date> getAllTheDateOftheMonth(Date start, Date end) {
		List<Date> list = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		while (start.before(end)) {
			list.add(start);
			cal.add(5, 1);
			start = cal.getTime();
		}
		return list;
	}

	public static boolean isSameMonth(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(1) == cal2.get(1);
		boolean isSameMonth = (isSameYear) && (cal1.get(2) == cal2.get(2));

		return isSameMonth;
	}

	public static boolean isSameday(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(1) == cal2.get(1);
		boolean isSameMonth = (isSameYear) && (cal1.get(2) == cal2.get(2));
		boolean isSameDate = (isSameMonth) && (cal1.get(5) == cal2.get(5));
		return isSameDate;
	}

	public static Date getDateAfter(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(5, now.get(5) + day);
		return now.getTime();
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(1), calendar.get(2), 1);
		calendar.roll(5, -1);
		return calendar.getTime();
	}
	
	/**
     * 获取某月的最后一天
     *
     */
    public static String getLastDayOfMonth(Date date, String FMT)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置当前日期
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(FMT);
        String lastDayOfMonth = sdf.format(cal.getTime());
          
        return lastDayOfMonth;
    }

	public static Date getDateBefore(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(5, now.get(5) - day);
		return now.getTime();
	}

	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(5, calendar.get(5) + 1);
		if (calendar.get(5) == 1) {
			return true;
		}
		return false;
	}

	public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(1), min.get(2), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(1), max.get(2), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(2, 1);
		}
		return result;
	}

	public static String getMonthBeforThisMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();

		c.setTime(date);
		c.add(2, -1);
		Date m = c.getTime();
		String mon = format.format(m);
		return mon;
	}

	public static boolean isFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int nowDay = calendar.get(5);
		if (nowDay == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Description 为指定日期追加时分秒：23:59:59
	 *
	 * @author 刘训鹏
	 *  
	 * @return
	 *
	 * @CreateDate 2019-04-24 15:28:23
	 * @Copyright by 天津津湖数据有限公司
	 */
	public static Date append235959ToDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int nowDay = calendar.get(5);
		if (nowDay == 1) {
			System.out.println("本月第一天");
		} else {
			System.out.println("不是");
		}
	}
}
