package com.domain.filter;

import com.domain.filter.constant.PreventDupURI;
import com.domain.utils.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 防重复拦截,以及解决跨域
 */
public class PreventDupFilter implements Filter {

    public RedisTemplate redisTemplate;

    private static Logger logger = LogManager.getLogger(PreventDupFilter.class);


    public PreventDupFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**防止重复提交x_token 请求Header的key*/
    public static final String PREVENT_DUP_TOKEN_HEADER_KEY = "x_token";


    //分布式锁过期时间,60s
    private static final long LOCK_TIME_OUT = 60;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //避免跨域
        setCrossOrigin(request,response);
        //获取访问端
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isNotBlank(userAgent)) {
            userAgent = userAgent.toUpperCase();
        }else {
            filterChain.doFilter(request, response);
            return;
        }
        String twwinWap = request.getHeader("TWWINWAP");
        String twwinApp = request.getHeader("TWWINAPP");
        if (StringUtils.isNotBlank(twwinWap)){
            twwinWap = twwinWap.toUpperCase();
        }else {
            twwinWap = "";
        }

        if (StringUtils.isNotBlank(twwinApp)){
            twwinApp = twwinApp.toUpperCase();
        }else {
            twwinApp = "";
        }


        //过滤指定请求方法,指定的URI,指定访问端
        if (HttpMethod.POST.matches(request.getMethod()) && !isMobile(userAgent,twwinWap,twwinApp) && PreventDupURI.PREVENT_DUP_URI.contains(request.getRequestURI())) {
            //获取token
            String xToken = request.getHeader(PREVENT_DUP_TOKEN_HEADER_KEY);

            //校验Token
            if (StringUtils.isBlank(xToken) || !checkXToken(xToken)) {
                //校验失败,驳回
                logger.info("重复请求:" + request.getRequestURI());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write(JsonResult.fillResultString(0, "重复请求", null));
                return;
            }
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 解决跨域问题
     * @param request
     * @param response
     */
    private void setCrossOrigin(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("Origin");
        if (origin == null) {
            response.setHeader("Access-Control-Allow-Origin", "*");
        } else {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "60");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, COOKIES, Authorization, xtoken, x_token,TWWINWAP");
        response.setHeader("Access-Control-Allow-Credentials", "true" ); // 控制是否开启与Ajax的Cookie提交方式
        response.setHeader("XDomainRequestAllowed","1");
    }


    @Override
    public void destroy() {

    }

    /**
     * 判断是否是手机端,通过user-agent转大写后进行关键字判断,是返回true,否返回false
     *
     * @param userAgent
     * @return
     */
    private boolean isMobile(String userAgent,String twwinWap,String twwinApp) {
        if (userAgent.indexOf("TWWINAPP") != -1) {
            if (twwinApp.indexOf("TWWINAPP") != -1) {
                return false;
            }
            return true;
        }else if (userAgent.indexOf("ANDROID") != -1 || userAgent.indexOf("IPHONE") != -1 || userAgent.indexOf("IPAD") != -1
                || userAgent.indexOf("IPOD") != -1 || userAgent.indexOf("MOBILE") != -1) {
            if (twwinWap.indexOf("TWWINWAP") != -1) {
                return false;
            }
            return true;
        }
        return false;

    }

    /**
     * 校验token
     *
     * @param xToken
     * @return
     */
    private boolean checkXToken(String xToken) {
        String value = System.currentTimeMillis() + xToken;

        try {
            //获取锁
            if (lock("redisLock"+xToken, value)) {
                //校验redis中的xtoken
                return checkPreventDupToken(xToken);
            }
        } catch (Exception e) {

        } finally {
            unlock("redisLock"+xToken, value);
        }
        return false;
    }


    /**
     * 判断存在以及删除redis中对应的preventDupToken
     *
     * @param preventDupToken
     */
    private boolean checkPreventDupToken(String preventDupToken) {
        if (redisTemplate.hasKey("TOKEN"+ preventDupToken)) {
            redisTemplate.delete("TOKEN" + preventDupToken);
            return true;
        }
        return false;
    }


    /**
     * 获取redis分布式锁
     *
     * @param key
     * @param value
     * @return
     */
    private Boolean lock(String key, String value) {
        SessionCallback<Boolean> sessionCallback = new SessionCallback<Boolean>() {
            List<Object> exec = null;
            @Override
            @SuppressWarnings("unchecked")
            public Boolean execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                redisTemplate.opsForValue().setIfAbsent(key, value);
                //设置锁过期时间
                redisTemplate.expire(key, LOCK_TIME_OUT, TimeUnit.SECONDS);
                exec = operations.exec();
                if (exec.size() > 0) {
                    return (Boolean) exec.get(0);
                }
                return false;
            }
        };
        return (boolean)redisTemplate.execute(sessionCallback);
    }


    /**
     * 解锁
     *
     * @param key
     * @param value
     */
    private void unlock(String key, String value) {
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
            redisTemplate.opsForValue().getOperations().delete(key);
        }
    }



}
