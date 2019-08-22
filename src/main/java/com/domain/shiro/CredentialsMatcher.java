package com.domain.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * 密码比较器
 * @author	LiangJ
 * @date	2019年4月24日
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
	
	//校验
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    	String inToken = ((String) token.getCredentials()).substring(7);
        String dbToken = (String) info.getCredentials();
        //进行密码的比对
        return this.equals(inToken, dbToken);
    }
	
}
