package com.test.utils;

public class TextUtils {
	/**
	 * Returns true if the string is null or 0-length.
	 * 
	 * @param str the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 获取文件类型
	 * 
	 * @param name
	 * @return
	 */
	public static String subType(String name) {
		if (isEmpty(name)) {
			return "";
		}
		int index = name.lastIndexOf(".");
		if (index >= 0) {
			return name.substring(index);
		}

		return "";
	}

	/**
	 * 获取临时文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getTemFileName(String fileName) {
		return DateUtils.getTemple() + subType(fileName);
	}

}
