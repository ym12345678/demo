package com.domain.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Json Web Token 工具类
 * 
 * @author lph
 * @Date 2018年9月13日
 */
public class JwtHelper {
	
	/** Json Web Token 秘钥 **/
	public final static String PRIVATE_KEY = "lottery";
	
	/** Token前缀 **/
    static final String TOKEN_PREFIX = "Bearer";     
    
    /** 存放Token的Header Key**/
    static final String HEADER_STRING = "Authorization";
    
    /**Token 过期时间 5天 **/
	public static final long EXPIRATIONTIME = 432_000_000;     
	

	
	/**
	 * 生成 Token
	 * Jwt token 分为三部分，第一部分为 头部信息Header, 第二部分为信息载荷payload, 第三部分为签证signature
	 * 
	 * @param account	登录账号
	 * @return	令牌token
	 */
	public static String createToken(String account) {
		 JwtBuilder jwtBuilder = Jwts.builder()
	                // 用户名写入标题
	                .setSubject(account)
	                // 有效期设置
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
	                // 签名设置
                	.signWith(SignatureAlgorithm.HS512, PRIVATE_KEY);
		return jwtBuilder.compact();
	}
	
	
	
	
	
	/**
	 * 校验token
	 * @param token	令牌
	 * @return	
	 */
	public static Claims authToken(String token) {
		 Claims claims = Jwts.parser()
		 		.setSigningKey(PRIVATE_KEY)
		 		.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
		 		.getBody();
		 return claims;
	}
	
	
}
