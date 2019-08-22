package com.domain.exum;

import com.alibaba.druid.util.StringUtils;

/**
 * 用户请求来源枚举类
 * @author lph
 * @Date 2019年1月22日
 */
public enum UserAgentEnum {
	
	PC("1", "web端"),
	ANDROID("2", "安卓端"),
	IOS("3", "苹果端"),
	APP("4","APP"),
	;
	
	private UserAgentEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
    private String desc;
    
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	/**
	 * 根据枚举编码获取请求来源枚举
	 * 
	 * @param code 编码
	 * @return
	 */
	public static UserAgentEnum findByCode(String code) {
		for(UserAgentEnum agent : values()) {
			if(StringUtils.equals(agent.getCode(), code)) {
				return agent;
			}
		}
		return null;
	}
	
	
	
	/**
	 * 根据枚举编码获取来源描述
	 * 
	 * @param code 编码
	 * @return
	 */
	public static String getDescByCode(String code) {
		for(UserAgentEnum agent : values()) {
			if(StringUtils.equals(agent.getCode(), code)) {
				return agent.getDesc();
			}
		}
		return null;
	}

}
