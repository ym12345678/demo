package com.domain.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;


/**
 * @author: LJ
 * @create: 2019-04-10
 **/
public class LocationUtil {
    public static final String APPKEY = "3cf29771e55aba7a";
    public static final String URL = "http://ipapi.163220.com/ip/";
    // public static final String URL = "http://ipapi.twwin.tw/ip/";   //正式

    public static final String TOKEN = "8cce56ef61cb103f33971e749f2a2b91263f56e2";
    //    public static final String URL = "http://ipapi.ipip.net/find";
    public static final String URL2 = "http://ipapi.163220.com/find";
    private static Logger log = LogManager.getLogger(LocationUtil.class);

    public static String getLocation(String ip) {
        String result = null;
        String params = "appkey=" + APPKEY + "&ip=" + ip;
        String location = "运营商级NAT";
        try {
            result = SendUrlRequestUtil.sendGetIp(URL, params);
            JSONObject json = JSONObject.fromObject(result);
            if (json.getInt("status") != 0) {
                log.error("无法获取IP归属地");
            } else {
                JSONObject resultarr = json.optJSONObject("result");
                String area = resultarr.getString("area");
                String type = resultarr.getString("type");
                location = area + " " + type;
            }
        } catch (Exception e) {
            log.error("获取IP归属地异常");
        }
        return location;
    }

    public static String getLocationByIpIp(String ip) {
        String params = "?addr=" + ip;
        String location = null;
        String doGet = HttpClientUtil.doGet(URL2 + params, TOKEN);
        if (doGet != null) {
            com.alibaba.fastjson.JSONObject parse = (com.alibaba.fastjson.JSONObject) JSON.parse(doGet);
            JSONArray data = (JSONArray) parse.get("data");
            if (data != null) {
                List<String> strings = data.toJavaList(String.class);
                if (strings.size() > 2) {
                    location = strings.get(0) + " " + strings.get(1) + " " + strings.get(2);
                }
            }

        }
        if (location == null) location = "运营商级NAT";
        return location;
    }

    public static String getIPByJuHe(String ip) {
        String location = null;
//        String url = "http://ipapi.163220.com";
      /*  String url = "http://ipapi.twwin.tw";
        String key = "ef855871cfdec7d0877fde45d0207d2a";*/
        String url = PropertiesUtil.getProperty("ju.he.url");
        String key = PropertiesUtil.getProperty("ju.he.key");
        String locationUrl = url + "/ip/ipNew?ip=" + ip + "&key=" + key;
        String doGet = HttpClientUtil.doGet(locationUrl, null);
        if (doGet != null) {
            com.alibaba.fastjson.JSONObject parse = (com.alibaba.fastjson.JSONObject) JSON.parse(doGet);
            String resultcode = (String) parse.get("resultcode");
            if (resultcode.equals("200")) {
                com.alibaba.fastjson.JSONObject data = (com.alibaba.fastjson.JSONObject) parse.get("result");
                if (data != null) {
                    Map<String, Object> innerMap = data.getInnerMap();
                    String country = (String)innerMap.get("Country");
                    String province = (String)innerMap.get("Province");
                    String city = (String)innerMap.get("City");
                    String isp = (String)innerMap.get("Isp");
                    if (city.equals(isp)) { isp = "";}
                    location=country + " " + province + " " + city + " " + isp;
                }
            }
        }
        if (location == null) {
            log.error(locationUrl+"返回信息："+doGet);
            location = "运营商级NAT";
        }
        return location;
    }

   /* public static void main(String[] args) {
        String location = getIPByJuHe("167.99.85.71");
        //String location = getLocation("167.99.85.71");
        System.out.println(location);
    }*/



    /**
     * 异步获取ip归属地
     *
     * @param ip
     * @auther: LJ
     * @return:
     */
    @Async
    public Future<String> asyncGetLocation(String ip) {
//        聚合API
        String location = LocationUtil.getIPByJuHe(ip);
        //极速API
//        String location = LocationUtil.getLocation(ip);
        //IPIP.net
//      String location = LocationUtil.getLocationByIpIp(ip);
        return new AsyncResult<>(location);
    }

}
