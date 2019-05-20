package com.reeyou.imall.utils;

import org.joda.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * @author Reeyou
 * @data 2019/5/20 15:53
 */
public class DateTimeUtil {

	private static final String FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

	//	str -> date
	public static Date strToDate(String dateTimeStr, String formatStr) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
		DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
		return dateTime.toDate();
	}

	//	date -> str
	public static String dateToStr(Date date, String formatStr) {
		if(date == null) {
			return StringUtils.EMPTY;
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(formatStr);
	}

	public static Date strToDate(String dateTimeStr) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(FORMAT_STR);
		DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
		return dateTime.toDate();
	}

	public static String dateToStr(Date date) {
		if(date == null) {
			return StringUtils.EMPTY;
		}
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(FORMAT_STR);
	}


}
