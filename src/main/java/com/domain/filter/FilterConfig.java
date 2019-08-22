package com.domain.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Configuration
public class FilterConfig {

    @Resource
    private RedisTemplate redisTemplate;

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new PreventDupFilter(redisTemplate));
        registration.addUrlPatterns("/*");
        registration.setName("preventDupFilter");
        registration.setOrder(1);
        return registration;
    }



}