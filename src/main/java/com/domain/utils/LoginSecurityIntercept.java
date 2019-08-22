package com.domain.utils;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 登录拦截器
 * @author lph
 * @Date 2018年9月13日
 */
public class LoginSecurityIntercept implements HandlerInterceptor {
	
	@Resource
	public RedisTemplate<Serializable,Serializable> redisTemplate;
	
	/*@Autowired
	public UserDao userDao;

	@Autowired
	private UserOnlineStateService userOnlineStateService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authHeader = request.getHeader("Authorization");
		if(!request.getMethod().equals("OPTIONS")) {
            // 放开的URI
            List<String> uncheckUri = Arrays.asList("/api/lottery/list");
            if(uncheckUri.contains(request.getRequestURI())){
                return true;
            }

			if(authHeader == null || !authHeader.startsWith("Bearer ")) {
				response.setStatus(401);
				return false;
			}
			 //取得token
            String token = authHeader.substring(7);
            try {
                Claims claims = JwtHelper.authToken(token);
                String redisToken = userOnlineStateService.getTokenByUserAccount(request,claims.getSubject());
                if(!token.equals(redisToken)) {
                	response.setStatus(401);
                	return false;
                }
                //校验token是否过期
                long expireTime = claims.getExpiration().getTime();
                long currentTime = System.currentTimeMillis();
                if(currentTime >= expireTime) {
                	response.setStatus(401);
                	return false;
                }
                request.setAttribute("claims", claims);
                
                //把用户信息存入session
                User user = (User) request.getSession().getAttribute("reqLoginUser_" + claims.getSubject());
                if(null == user) {
                	user = userDao.getByAccount(claims.getSubject());
                	request.getSession().setAttribute("reqLoginUser_" + claims.getSubject(), user);
                }
                request.setAttribute("loginUser", user);
				//如果不是游客,设置在线状态
				if (user.getAccountType()!=2){
					userOnlineStateService.processUserOnlineStateLogin(request,user.getAccount());
				}
				return true;
            } catch (Exception e) {
            	response.setStatus(401);
				return false;
            }
		}else return true;
	}
*/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	
}
