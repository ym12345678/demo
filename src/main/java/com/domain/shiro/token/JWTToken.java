package com.domain.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义JwtToken继承Shiro的AuthenticationToken
 * @author	LiangJ
 * @date	2019年4月22日
 */
public class JWTToken implements AuthenticationToken {

	// 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
	
}
