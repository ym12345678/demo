package com.domain.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.xsoup.Xsoup;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


public class SendUrlRequestUtil {

	
	private static Logger logger = LoggerFactory.getLogger(SendUrlRequestUtil.class);


	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		String location = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
//				// System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			
			//截取读到的页面
			//将请求回来的页面转成org.jsoup.nodes.Document 类型
			Document document = Jsoup.parse(result);
			//调用截取方法（参数为需要截取的内容标签具体位置）
			location = Xsoup.compile("//div[@class='well']/p[2]/code/text()").evaluate(document).get();
		} catch (Exception e) {
//			// System.out.println("发送GET请求出现异常！" + e);
			//// e.printStackTrace()();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return location;
	}
	
	
	
	/**
	 * 根据请求对象获取用户ip
	 * @param request 请求对象
	 * @return 返回用户ip
	 */
	public static String getIpByRequest(HttpServletRequest request) {
		String originIps = request.getHeader("X-Forwarded-For");
		logger.info("【X-Forwarded-For】 获取到的IP集：" + originIps);
		String ip = null;
		if(StringUtils.isNotBlank(originIps)) {
			ip = originIps.split(",")[0];
			// 如果nginx反向代理返回的第一个ip为127.0.0.1，则取第二个ip值
			if("127.0.0.1".equals(ip)){
			    ip = originIps.split(",")[1];
            }
		}
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
		logger.info("最终获取的IP：" + ip);
	    return ip;
	}

	public static String sendGetIp(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			/*for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}*/
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
//			System.out.println("发送GET请求出现异常！" + e);
//			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;

	}


}
