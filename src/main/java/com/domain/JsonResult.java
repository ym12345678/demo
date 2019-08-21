package com.domain;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口统一返回响应信息 JSON
 * @author lph
 * @Date 2018年9月13日
 */
public class JsonResult {
	
	
	/**
	 * 填充jsonResult
	 * @param status	响应状态码  1-成功    0-失败
	 * @param message	响应信息
	 * @param result	响应结果集
	 * @return
	 */
	public static String fillResultString(Integer status, String message, Object result){
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("status", status);
		jsonMap.put("message", message);
		jsonMap.put("result", result);
        return JSONObject.toJSONString(jsonMap, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);
    }

}