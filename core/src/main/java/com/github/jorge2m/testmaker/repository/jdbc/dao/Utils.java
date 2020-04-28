package com.github.jorge2m.testmaker.repository.jdbc.dao;

import java.text.SimpleDateFormat;

public class Utils {

	public enum DateFormat {ToSeconds, ToMillis}
	
	public static SimpleDateFormat getDateFormat(DateFormat format) {
		switch (format) {
		case ToMillis:
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		case ToSeconds:
		default:
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	}
}
