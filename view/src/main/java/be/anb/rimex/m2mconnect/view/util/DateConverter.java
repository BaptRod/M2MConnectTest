package be.anb.rimex.m2mconnect.view.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
	
	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
	
}
