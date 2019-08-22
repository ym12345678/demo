package com.domain.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 * @ClassName: ApplicationStartup
 * @Description: 系统初始化
 * @Author: linjinhuang
 * @Date: 2019/5/11 15:24
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());





    /**
     * 复写系统初始化方法,需要初始化的操作可以在此进行
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //将所有的彩种基础规则缓存到Map中
        initLotteryBaseInfo();
    }


    private void initLotteryBaseInfo() {

    }
}
