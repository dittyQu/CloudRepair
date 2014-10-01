package com.ibm.bluemix.demo.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	public static final SimpleDateFormat FORMAT_INPUT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	public static final SimpleDateFormat FORMAT_DISPLAY = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat FORMAT_DISPLAY_TIME = new SimpleDateFormat("yyyy年MM月dd日 hh時mm分");
	public static final SimpleDateFormat FORMAT_DB = new SimpleDateFormat("yyyyMMdd");
	public static final DecimalFormat FORMAT_MONEY = new DecimalFormat("#,##0"); 
	public static String getValue(String value) {
		if (value == null) {
			return Constants.VAL_BLANK;
		}
		
		return value.trim();
	}
	
	public static boolean isEmpty(String value) {
		if (value == null) {
			return true;
		}
		
		return value.trim().isEmpty();
	}
	
	public static Date parseDate(String dateStr, String pattern) {
		if (dateStr == null) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatBirth(String birth) {
		if (birth == null) {
			return Constants.VAL_BLANK;
		}
		if (birth.trim().length() != 8) {
			return birth.trim();
		} else {
			try {
				Date birthDate = FORMAT_DB.parse(birth);
				return FORMAT_DISPLAY.format(birthDate);
			} catch (ParseException e) {
				return birth.trim();
			}
		}
	}
	
	public static String formatMoney(int money) {
		return FORMAT_MONEY.format(money);
	}
}
