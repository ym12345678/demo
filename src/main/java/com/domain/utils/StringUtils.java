package com.domain.utils;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 字符串工具类，对字符串进行常规的处理
 * 
 * @Author:chenssy
 * @date:2014年8月5日
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**
	 * 将半角的符号转换成全角符号.(即英文字符转中文字符)
	 * 
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param str
	 *            要转换的字符
	 * @return
	 */
	public static String changeToFull(String str) {
		String source = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
		String[] decode = { "１", "２", "３", "４", "５", "６", "７", "８", "９", "０", "！", "＠", "＃", "＄", "％", "︿", "＆", "＊",
				"（", "）", "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ",
				"ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ", "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ",
				"Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ", "Ｙ", "Ｚ", "－", "＿", "＝", "＋", "＼", "｜", "【", "】", "；",
				"：", "'", "\"", "，", "〈", "。", "〉", "／", "？" };
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int pos = source.indexOf(str.charAt(i));
			if (pos != -1) {
				result += decode[pos];
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 将字符转换为编码为Unicode，格式 为'\u0020'<br>
	 * unicodeEscaped(' ') = "\u0020"<br>
	 * unicodeEscaped('A') = "\u0041"
	 * 
	 * @autor:chenssy
	 * @date:2014年8月7日
	 *
	 * @param ch
	 *            待转换的char 字符
	 * @return
	 */
	public static String unicodeEscaped(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		} else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		} else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}

	/**
	 * 进行toString操作，若为空，返回默认值
	 * 
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param object
	 *            要进行toString操作的对象
	 * @param nullStr
	 *            返回的默认值
	 * @return
	 */
	public static String toString(Object object, String nullStr) {
		return object == null ? nullStr : object.toString();
	}

	/**
	 * 将字符串重复N次，null、""不在循环次数里面 <br>
	 * 当value == null || value == "" return value;<br>
	 * 当count <= 1 返回 value
	 *
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param value
	 *            需要循环的字符串
	 * @param count
	 *            循环的次数
	 * @return
	 */
	public static String repeatString(String value, int count) {
		if (value == null || "".equals(value) || count <= 1) {
			return value;
		}

		int length = value.length();
		if (length == 1) { // 长度为1，存在字符
			return repeatChar(value.charAt(0), count);
		}

		int outputLength = length * count;
		switch (length) {
		case 1:
			return repeatChar(value.charAt(0), count);
		case 2:
			char ch0 = value.charAt(0);
			char ch1 = value.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = count * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default:
			StringBuilder buf = new StringBuilder(outputLength);
			for (int i = 0; i < count; i++) {
				buf.append(value);
			}
			return buf.toString();
		}

	}

	/**
	 * 将某个字符重复N次
	 *
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param ch
	 *            需要循环的字符
	 * @param count
	 *            循环的次数
	 * @return
	 */
	public static String repeatChar(char ch, int count) {
		char[] buf = new char[count];
		for (int i = count - 1; i >= 0; i--) {
			buf[i] = ch;
		}
		return new String(buf);
	}

	/**
	 * 判断字符串是否全部都为小写
	 *
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param value
	 *            待判断的字符串
	 * @return
	 */
	public static boolean isAllLowerCase(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		for (int i = 0; i < value.length(); i++) {
			if (Character.isLowerCase(value.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否全部大写
	 *
	 * @autor:chenssy
	 * @date:2014年8月9日
	 *
	 * @param value
	 *            待判断的字符串
	 * @return
	 */
	public static boolean isAllUpperCase(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		for (int i = 0; i < value.length(); i++) {
			if (Character.isUpperCase(value.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 反转字符串
	 *
	 * @autor:chenssy
	 * @date:2014年8月9日
	 * @param value
	 *            待反转的字符串
	 * @return
	 */
	public static String reverse(String value) {
		if (value == null) {
			return null;
		}
		return new StringBuffer(value).reverse().toString();
	}

	/**
	 * @desc:截取字符串，支持中英文混乱，其中中文当做两位处理
	 * @autor:chenssy
	 * @date:2014年8月10日
	 *
	 * @param resourceString
	 * @param length
	 * @return
	 */
	public static String subString(String resourceString, int length) {
		String resultString = "";
		if (resourceString == null || "".equals(resourceString) || length < 1) {
			return resourceString;
		}

		if (resourceString.length() < length) {
			return resourceString;
		}

		char[] chr = resourceString.toCharArray();
		int strNum = 0;
		int strGBKNum = 0;
		boolean isHaveDot = false;

		for (int i = 0; i < resourceString.length(); i++) {
			if (chr[i] >= 0xa1) {// 0xa1汉字最小位开始
				strNum = strNum + 2;
				strGBKNum++;
			} else {
				strNum++;
			}

			if (strNum == length || strNum == length + 1) {
				if (i + 1 < resourceString.length()) {
					isHaveDot = true;
				}
				break;
			}
		}
		resultString = resourceString.substring(0, strNum - strGBKNum);
		if (isHaveDot) {
			resultString = resultString + "...";
		}

		return resultString;
	}

	/**
	 *
	 * @autor:chenssy
	 * @date:2014年8月10日
	 *
	 * @param htmlString
	 * @param length
	 * @return
	 */
	public static String subHTMLString(String htmlString, int length) {
		return subString(delHTMLTag(htmlString), length);
	}

	/**
	 * 过滤html标签，包括script、style、html、空格、回车标签
	 *
	 * @autor:chenssy
	 * @date:2014年8月10日
	 *
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签

		return htmlStr.trim(); // 返回文本字符串
	}

	public static int StringToInt(String s) {
		if (s == null)
			return 0;
		s = s.replaceAll("[\\D]+", "");
		return Integer.parseInt(s);
	}

	public static Integer[] toIntegerArray(String str, String regex) {
		List<Integer> list = toIntegerList(str, regex);
		Integer[] arr = new Integer[list.size()];
		return list.toArray(arr);
	}

	public static List<Integer> toIntegerList(String str, String regex) {
		StringTokenizer t = new StringTokenizer(str, regex);
		List<Integer> l = new ArrayList<Integer>();
		while (t.hasMoreElements()) {
			String s = t.nextToken();
			if (isNotBlank(s)) {
				l.add(Integer.parseInt(s));
			}
		}
		return l;
	}

	/**
	 * 检测是否为空格串、空字符串或null
	 *
	 * @param str
	 *            待检测的字符串
	 * @return 若str为null或空字符串则或空格串返回false; 否则返加true
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 检测是否为空格串、空字符串或null
	 *
	 * @param str
	 *            待检测的字符串
	 * @return 若str为null或空字符串则或空格串返回true; 否则返加false
	 */
	public static boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		}

		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 检测是否为空字符串或为null
	 *
	 * @param str
	 *            待检测的字符串
	 * @return 若str为null或空字符串则返回true; 否则返加false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() <= 0;
	}

	/**
	 * 随机生成一个汉字
	 *
	 * @return
	 */
	public static char getRandomChar() {
		String str = "";
		int hightPos; //
		int lowPos;

		Random random = new Random();

		hightPos = (176 + Math.abs(random.nextInt(39)));
		lowPos = (161 + Math.abs(random.nextInt(93)));

		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(hightPos)).byteValue();
		b[1] = (Integer.valueOf(lowPos)).byteValue();

		try {
			str = new String(b, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("错误");
		}

		return str.charAt(0);
	}

	/**
	 * 将容器的字符串/数组连接起来
	 *
	 *            容器
	 * @param collection
	 *            分割符
	 * @param mark
	 *            分割字符串的前后增加mark标签符
	 * @return 字符串
	 */
	public static String join(Collection<?> collection, String sep, char mark) {
		StringBuilder outStr = new StringBuilder();
		for (Object obj : collection) {
			if (obj == null) {
				continue;
			}
			if (outStr.length() > 0) {
				outStr.append(sep);
			}
			outStr.append(mark).append(obj).append(mark);
		}
		return outStr.toString();
	}

	/**
	 * 用StringTokenizer分割字符串到数组
	 *
	 * @param str
	 *            待分割字符串
	 * @param sep
	 *            分割符
	 * @return 分割后的字符串数组
	 */
	public static String[] splitTokens(String str, String sep) {
		if (isEmpty(str)) {
			return null;
		}

		StringTokenizer token = new StringTokenizer(str, sep);
		int count = token.countTokens();
		if (count < 1) {
			return null;
		}

		int i = 0;
		String[] output = new String[count];
		while (token.hasMoreTokens()) {
			output[i] = token.nextToken();
			++i;
		}
		return output;
	}

	/**
	 * 将json字符串Unicode编码转换成中文
	 *
	 * @param utfString
	 * @return
	 */
	public static String convert(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

	public static List<String> partition(String str, int partition) {
		List<String> parts = new ArrayList<String>();
		int len = str.length();
		for (int i = 0; i < len; i += partition) {
			parts.add(str.substring(i, Math.min(len, i + partition)));
		}
		return parts;
	}

	public static int getTimes(String src, String dest) {
		int times = 0, index = 0;
		do {
			index = src.indexOf(dest);
			if (index >= 0) {
				times++;
				src = src.substring(index + dest.length());
			}
		} while (index != -1);

		return times;
	}
	/**
	 * double转字符串保留3位小数
	 * @param money
	 * @return
	 */
	public static String double2String(Double money){
		DecimalFormat df = new DecimalFormat(".###");
		return df.format(money);
	}

	/**
	 * Double保留三位小数
	 * @param money 数值
	 * @return
	 */
	public static Double doubleToThree(Double money){
		DecimalFormat df = new DecimalFormat(".###");
		return Double.valueOf(df.format(money));
	}

	/**
	 * 去掉字符串中前后的空格/回车/TAB
	 *
	 * @param str
	 *            输入的字符串
	 * @return 去掉前后空格/回车/TAB的字符串
	 */
	public static String trimMore(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);

		// 去掉头部的空格
		int index = 0;
		while (buf.length() > index) {
			char c = buf.charAt(index);
			if (Character.isWhitespace(c) || c == '\t' || c == '\r'
					|| c == '\n') {
				buf.deleteCharAt(index);
			} else {
				break;
			}
		}

		// 去掉尾部的空格
		while (buf.length() > 0) {
			int len = buf.length() - 1;
			char c = buf.charAt(len);
			if (Character.isWhitespace(c) || c == '\t' || c == '\r'
					|| c == '\n') {
				buf.deleteCharAt(len);
			} else {
				break;
			}
		}

		return buf.toString();
	}

	public static String toStrings(Object obj, String defStr) {
		if (obj == null) {
			return defStr;
		}
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj.toString();
	}

	/**
	 * 转换为星号内容1
	 *
	 * @param str
	 * @param startShowLen 前置显示位数
	 * @param endShowLen   后置显示位数
	 * @return
	 */
	public static String getStarString(String str, int startShowLen, int endShowLen) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		int len = str.length() - endShowLen;
		if (len > 1) {
			return str.substring(0, startShowLen) + StringUtils.repeat("*", len - startShowLen) + str.substring(len);
		} else {
			return str.substring(0, 1) + "**";
		}
	}

	/**
	 * 转换为星号内容2
	 *
	 * @param str
	 * @return
	 */
	public static String getStarString(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		int len = str.length();
		return (len > 3) ? str.substring(0, 3) + "***" + str.substring(len > 8 ? (len - 4) : len > 5 ? (len - 3) : (len - 1)) : str + "***";
	}
	
	/**
	 * 
	 * <p>描述: 判断是否为整数</p>  
	 * @author cty  
	 * @date  新增日期：2019年5月22日  
	 * @date  修改日期：2019年5月22日  
	 * @param str
	 * @return 是整数返回true,否则返回false 
	 */
	public static boolean isInteger(String str) {  
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
   }

	/**处理前端经过编码的字符串
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
   public  static  String encodingStrForWeb(String content)  {
	   try {
		   return new String (content.getBytes("iso-8859-1"),"UTF-8");
	   } catch (Exception e) {
		   return content;
	   }
   }
	
}
