package com.domain.utils;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域请求拦截器处理类
 * @author lph
 * @date 2018年8月23日
 */
public class CrossOriginIntercept implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String origin = request.getHeader("Origin");  
        if (origin == null) {  
        	response.setHeader("Access-Control-Allow-Origin", "*");  
        } else {  
        	response.setHeader("Access-Control-Allow-Origin", origin);  
        }  
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "60");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, COOKIES, Authorization, xtoken, x_token");
        response.setHeader("Access-Control-Allow-Credentials", "true" ); // 控制是否开启与Ajax的Cookie提交方式
        response.setHeader("XDomainRequestAllowed","1");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
