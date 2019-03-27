package com.zgl.common.util;

import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 时间戳转换工具类
 * @author zgl
 * @date 2019/3/27 上午11:36
 */
public class DateUtil {
	public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

	public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String DATE_TIME_FORMAT_MM_DD = "MMdd";

	public static final String DATE_TIME_FORMAT_YYMM = "YYMM";

	public static final String DATE_TIME_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	private DateUtil() {
		throw new IllegalStateException("DateUtil class");
	}

	public static String parseDateToString(Date date, String pattern) {
		if (date != null) {
			DateTime dateTime = new DateTime(date);
			return dateTime.toString(pattern);
		}
		return null;
	}

	public static Date parseStringToDate(String time, String pattern) {
		Preconditions.checkNotNull(time);
		Preconditions.checkNotNull(pattern);
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		DateTime dateTime = DateTime.parse(time, formatter);
		return dateTime.toDate();
	}

	public static String getSuffix() {
		return parseDateToString(DateTime.now().toDate(), DateUtil.DATE_TIME_FORMAT_YYMM);
	}

	public static String getNextSuffix() {
		return parseDateToString(DateTime.now().plusMonths(1).toDate(), DateUtil.DATE_TIME_FORMAT_YYMM);
	}
}