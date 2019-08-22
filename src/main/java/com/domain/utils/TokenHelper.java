package com.domain.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


/**
 * Token 工具类
 * 
 * @author lph
 * @Date 2019年1月10日
 */
public class TokenHelper {

	private TokenHelper() {
	};

	private static final TokenHelper instance = new TokenHelper();

	public static TokenHelper getInstance() {
		return instance;
	}

	
	/**
	 * 生成Token
	 * 
	 * @return
	 */
	@SuppressWarnings("restriction")
	public String makeToken() {
		String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte md5[] = md.digest(token.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace()();
		}
		return null;
	}

}
