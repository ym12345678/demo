package com.domain.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *  获取配置文件信息
 * */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    public static Properties props;

    static {
        try {
            props = new Properties();
            props= PropertiesLoaderUtils.loadAllProperties("application.properties");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 根据key获取对应的配置文件值
     * */
    public static String getProperty(String key){
        String value = props.getProperty(key);
        return value;
    }

    /**
     * 根据key获取对应的配置文件值
     * */
    public static String getValue(String key){
        Properties properties = new Properties();
        String value = null;
        try {
            properties.load(new InputStreamReader(PropertiesUtil.class.getResourceAsStream("/application.properties"), "UTF-8"));
            value = properties.getProperty(key);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

}
