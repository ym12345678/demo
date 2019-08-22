package com.domain.utils;


/**
 * 用户请求来源分析
 * @author lph
 * @Date 2018年10月30日
 */
public class UserAgentHelper {

	/**
	 * 用户请求来源分析
	 * @param userAgent
	 * @return 1-PC 2-安卓  3-苹果
	 */
	public static int judge(String userAgent) {
		if(userAgent.contains("Android")) {
			return 2;
		}else if(userAgent.contains("iPhone") || userAgent.contains("ipad") || userAgent.contains ("ipod")) {
			return 3;
		}else if(userAgent.contains("twwinapp") || userAgent.contains("TWWINAPP")) {
			return 4;
		}else {
			return 1;
		}
	}

}
