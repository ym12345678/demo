package com.domain.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * 利用HttpClient进行post请求的工具类v
 *
 * @author: LJ
 * @create: 2018-12-26
 **/
public class HttpClientUtil {
     private static Logger log = LogManager.getLogger(HttpClientUtil.class);
    @SuppressWarnings("resource")
    public static String doPost(String url, String jsonstr, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            StringEntity se = new StringEntity(jsonstr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            log.error("请求apiUrl:【{}】 - 异常信息：{}", url, ex.getMessage());
        }
        return result;
    }

    /**
     * 模拟get请求工具方法
     *
     * @param url
     * @param timeoutSecond
     * @param charset
     * @auther: LJ
     * @return:
     */
    public static String doGet(String url, Integer timeoutSecond, String charset) {
        timeoutSecond = timeoutSecond * 1000;
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if (timeoutSecond != null) {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(timeoutSecond).setConnectionRequestTimeout(timeoutSecond)
                    .setSocketTimeout(timeoutSecond).build();
            httpGet.setConfig(requestConfig);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
//            System.out.println("得到的结果:" + response.getStatusLine());//得到请求结果
            HttpEntity entity = response.getEntity();//得到请求回来的数据
            result = EntityUtils.toString(entity, charset);
        } catch (IOException e) {
            log.error("请求apiUrl:【{}】 - 异常信息：{}", url, e.getMessage());
        }
        return result;
    }

    public static String doGet(String url, String token) {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if (token != null) httpGet.addHeader("Token", token);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
//            System.out.println("得到的结果:" + response.getStatusLine());//得到请求结果
            HttpEntity entity = response.getEntity();//得到请求回来的数据
            result = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            log.error("请求apiUrl:【{}】 - 异常信息：{}", url, e.getMessage());
        }
        return result;
    }
}
