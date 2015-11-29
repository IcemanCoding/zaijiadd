package com.zaijiadd.app.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	private static final String CHARSET_NAME = "UTF-8";

	public static String splitString(String str, String splitStr, Integer index) {

		String[] strArr = str.split(splitStr);
		String resStr = "";

		if (index == -1 || index > strArr.length - 1) {
			resStr = strArr[strArr.length - 1];
		} else {
			resStr = strArr[index];
		}

		return resStr;

	}

	public static String buildOrderId(String key) {

		// orderId format: 'DDHOME' + yyyyMMddHHmmss + random(10)
		String orderId = "DDHOME";

		orderId += DateUtils.getSysDate(DateUtils.FULL_DATE_TIME2);

		orderId += RandomUtils.getRandomNumber(10);

		if (key.length() >= 9) {
			key.substring(0, 9);
		}

		orderId += key;

		return orderId;

	}

	public static String buildJdTradeOrderId(String key) {

		// orderId format: 'DDHOME' + yyyyMMddHHmmss + random(10)
		String orderId = "JD&DD";

		orderId += DateUtils.getSysDate(DateUtils.FULL_DATE_TIME2);

		orderId += RandomUtils.getRandomNumber(6);

		if (key.length() >= 9) {
			key.substring(0, 9);
		}

		orderId += key;

		return orderId;

	}

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 字节数组转换为字符串。以UTF-8字符集
	 * 
	 * @param bytes
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str
	 *            验证字符串
	 * @param strs
	 *            字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (str != null) {
			for (String s : strs) {
				if (str.equals(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)) {
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt) {
		if (txt == null) {
			return "";
		}
		return replace(replace(EncryptUtil.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * 
	 * @param str
	 *            目标字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		return toDouble(val).longValue();
	}

	public static void main(String[] args) {

		String t = buildOrderId("7");
		System.out.println(t);

		String s = splitString(
				"64C8B923-8ABE-42CE-ABF5-AEBACDA09DF3-75022-00009F62C13819AC`AATmZNU1hZX/rUCJTc2o4Z5bMbj9Ab7I`1", "`",
				3);
		System.out.println(s);

	}

}
