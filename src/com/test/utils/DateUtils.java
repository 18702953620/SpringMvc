package com.test.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author ch
 *
 */
public class DateUtils {
	/**
	 * 返回标准时间
	 * 
	 * @param time
	 * @return
	 */
	public static String getDate(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}

	/**
	 * 返回标准时间
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * 返回时间戳
	 * 
	 * @return
	 */
	public static String getTemple() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date());
	}

}
