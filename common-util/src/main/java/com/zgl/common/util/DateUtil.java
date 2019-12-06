package com.zgl.common.util;

import com.google.common.base.Preconditions;
import org.apache.tomcat.jni.Local;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Assert;

import java.time.*;
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

	/**
	 * String类型转13位Long时间戳
	 * @param time
	 * @return
	 */
	public static Long convertTimeToLong(String time) {
		Assert.notNull(time, "time is null");
		java.time.format.DateTimeFormatter ftf = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate parse = LocalDate.parse(time, ftf);
		return LocalDate.from(parse).atStartOfDay().toEpochSecond(ZoneOffset.of("+8")) * 1000;
	}

	/**
	 * ﻿LocalDate转Date
	 * @param localDate
	 * @return
	 */
	public static Date convertLocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant());
	}

	/**
	 * ﻿LocalDateTime转Date
	 * @param localDateTime
	 * @return
	 */
	public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
	}

	/**
	 * ﻿Date转LocalDateTime
	 * @param date
	 * @return
	 */
	public static LocalDateTime convertDateToLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
	}

	/**
	 * ﻿Date转LocalDate
	 * @param date
	 * @return
	 */
	public static LocalDate convertDateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDate();
	}

	/**
	 *﻿ LocalDate转时间戳
	 * @param localDate
	 * @return
	 */
	public static Long convertLocalDateToLong(LocalDate localDate) {
		return LocalDate.from(localDate).atStartOfDay().toEpochSecond(ZoneOffset.of("+8")) * 1000;
	}

	/**
	 * LocalDateTime转时间戳
	 * @param localDateTime
	 * @return
	 */
	public static Long convertLocalDateTimeToLong(LocalDateTime localDateTime) {
		return LocalDateTime.from(localDateTime).toEpochSecond(ZoneOffset.of("+8")) * 1000;
	}

	/**
	 * 时间戳转LocalDate
	 * @param timeStamp
	 * @return
	 */
	public static LocalDate convertLongToLocalDate(Long timeStamp) {
		return Instant.ofEpochMilli(timeStamp).atZone(ZoneOffset.ofHours(8)).toLocalDate();
	}

	/**
	 * LocalDateTime转时间戳
	 * @param timeStamp
	 * @return
	 */
	public static LocalDateTime convertLongToLocalDateTime(Long timeStamp) {
		return Instant.ofEpochMilli(timeStamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
	}

	public static LocalDate convertLocalDateTimeToLocalDate(LocalDateTime localDateTime) {
		return localDateTime.toLocalDate();
	}
}