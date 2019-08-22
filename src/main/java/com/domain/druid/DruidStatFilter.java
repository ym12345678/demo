package com.domain.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
* 配置监控拦截器
* druid监控拦截器
* @ClassName: DruidStatFilter
*/ 
@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*", initParams={
		@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"),
		@WebInitParam(name="sessionStatEnable",value="false")//去掉对session的监控
})
 public class DruidStatFilter extends WebStatFilter { }