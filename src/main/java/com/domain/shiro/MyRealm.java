package com.domain.shiro;


import com.dao.UserDao;
import com.domain.User;
import com.domain.exception.MyException;
import com.domain.redis.RedisService;
import com.domain.shiro.token.JWTToken;
import com.domain.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 认证与鉴权
 * @author	LiangJ
 * @date	2019年4月24日
 */
@Service
@SuppressWarnings("unchecked")
public class MyRealm extends AuthorizingRealm {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		return null;
	}

/*	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserDao userDao;
	@Resource
	public RedisTemplate<Serializable,Serializable> redisTemplate;
	@Autowired
	private UserOnlineStateService userOnlineStateService;

    *//**
     * JWTToken替换Shiro自带token，必须重写此方法，不然Shiro会报错
     *//*
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    *//**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     *//*
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	//从token解析出用户名
    	Claims claims = JwtHelper.authToken(principals.toString());
        String username = claims.getSubject();

        List<String> permissionStrs = new ArrayList<>();
        User user = null;
        if(username.startsWith("试玩")) {
        	*//**
        	 * Redis中不存在游客账户的SELF_USER，直接判断用户名即可
        	 *//*
        	permissionStrs = (List<String>) redisTemplate.opsForHash().get(RedisPrefixConstants.AUTHCODE, RoleEnum.ROLE_FIVE.getCode());
        } else {
        	//获取Redis中的用户
        	user = (User) redisService.get(RedisPrefixConstants.SELF_USER + username);
        	if(user == null) {
        		//从数据库中查找
        		user = userDao.getByAccount(username);
        		if(user == null) {
        			throw new MyException(LoginTipsEnum.ACCOUNT_NOT_LOGIN.getDesc());//	账号未登录
        		}
        		redisService.set(RedisPrefixConstants.SELF_USER + username, user);
        	}
        	*//**
        	 * 判断是不是会员的试玩账号
        	 *//*
        	if(user.getAccountType()!=null && user.getAccountType()==0) {
        		permissionStrs = (List<String>) redisTemplate.opsForHash().get(RedisPrefixConstants.AUTHCODE, RoleEnum.ROLE_FIVE.getCode());
        	} else {
        		permissionStrs = (List<String>) redisTemplate.opsForHash().get(RedisPrefixConstants.AUTHCODE, user.getRoleId());
        	}
        }

        //将权限放入shiro中.
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(permissionStrs!=null && permissionStrs.size()>0) {
        	simpleAuthorizationInfo.addStringPermissions(permissionStrs);
        }
        return simpleAuthorizationInfo;
    }

    *//**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     *//*
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
    	String authToken = (String) auth.getCredentials();
    	if(authToken == null || !authToken.startsWith("Bearer ")) {
    		throw new AuthenticationException("token invalid");
    	}
        String token = authToken.substring(7);
        // 获得username
        Claims claims = JwtHelper.authToken(token);
        String username = claims.getSubject();

        //获取请求对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 查询Redis中token是否存在
        String redisToken = userOnlineStateService.getTokenByUserAccount(request,username);

        // 判断两个token是否相等
        if(redisToken == null || !token.equals(redisToken)) {
        	throw new AuthenticationException("token invalid");
        }

        return new SimpleAuthenticationInfo(token, token, "myRealm");
    }*/

}
