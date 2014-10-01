package com.ibm.bluemix.demo.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
	public static Date addMonth(Date date, int month) {
		Calendar calendar = new GregorianCalendar(); 
	    calendar.setTime(date); 
	    calendar.add(Calendar.MONTH, month);
	    return calendar.getTime();
	}
	
	public static boolean isWorkDay(String dateStr) {
		try {
			Date date = StringUtils.FORMAT_INPUT.parse(dateStr);
			Calendar calendar = new GregorianCalendar(); 
		    calendar.setTime(date);
		    int day = calendar.get(Calendar.DAY_OF_WEEK);
		    return day != 1 && day != 7;
		} catch (ParseException e) {
			//NOP
		}
		return true;
	}
	
	public static void main(String[] args) {
		java.util.List<String> list = new java.util.ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		
		java.util.List<String> list0 = list.subList(3, 4);
		for(String s : list0) {
			System.out.println(s);
		}
	}
}
