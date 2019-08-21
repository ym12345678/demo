package com.domain.utils;


import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 *  MD5 加解密工具类
 * @author lph
 * @date 2018年8月28日
 */
public class MD5Helper {
	//16进制下数字到字符的映射数组   
    private static String[] hexDigits = new String[]{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};  
  
    //将inputstr加密   
    public static String createMD5String(String inputstr) {  
        return encodeByMD5(inputstr);  
    }  
  
    //验证文件名是否存在是否正确   
    public static boolean authenticateFile(String pass , String inputstr) {  
        if(pass.equals((encodeByMD5(inputstr))))  {  
            return true;  
        }else {  
             return false;  
        }  
    } 
    
	//对字符串进行MD5编码   
	private static String encodeByMD5(String originstr) {  
	     if(originstr !=null) {  
	         try{  
	             //创建具有指定算法名称的信息摘要   
	         MessageDigest md = MessageDigest.getInstance("MD5");  
	         //使用指定的字节数组对摘要进行最后的更新，然后完成摘要计算   
	         byte[] results = md.digest(originstr.getBytes());  
	          //将得到的字节数组编程字符串返回   
	             String resultString = byteArrayToHexString(results);  
	             return resultString.toUpperCase();  
	         }catch(Exception ex){  
	             ex.printStackTrace();  
	         }  
	     }  
	    return null;  
	}  
	
    //转换字节数组为十六进制字符串   
    private static String byteArrayToHexString(byte[] b) {  
        StringBuffer resultsb = new StringBuffer();  
        int i=0;  
        for(i=0;i<b.length;i++)  
        {  
            resultsb.append(byteToHexString(b[i]));  
        }  
        return resultsb.toString();  
    }  
    
    //将字节转化成十六进制的字符串   
    private static String byteToHexString(byte b) {  
	   int n=b;  
	   if(n<0){  
	        n = 256 + n;  
	   }  
	   int d1 = n / 16;  
	   int d2 = n /16;  
	   return hexDigits[d1]+hexDigits[d2];  
    }
    
	
	
	/**
	 * 加密,相同的密码加密后得到不同的密文
	 * @param originstr 待加密原文
	 * @return ciphertext 加密后密文
	 */
	public static String encrypt(String originstr) {
		String ciphertext = "";
		try {
			//生成一个随机数;
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[12];
			random.nextBytes(salt);
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt);
			md.update(originstr.getBytes("UTF8"));
			//随机数与明文捆绑在一起同时加密;
			byte[] digest = md.digest();
			ciphertext = new Base64().encodeToString(salt);
			//将随机数与密文捆绑在一起(字段串前十16位为随机数);
			ciphertext = ciphertext + new Base64().encodeToString(digest);
			return ciphertext;
		} catch (Exception e) {
			//// e.printStackTrace()();
		}
		return ciphertext;
	}
	
	/**
	 * 加密,相同的密码加密后得到相同的密文
	 * @param password
	 * @return
	 */
	public static String encryptForRemainderSign(String password){
		String strResult = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes("UTF8"));
			strResult = new Base64().encodeToString(md.digest());
		}catch (Exception e) {
			//// e.printStackTrace()();
		}
		return strResult;
	}
	

	/**
	 * 认证,校验
	 * @param originstr 待验证明文
	 * @param ciphertext 待对比密文
	 * @return
	 */
	public static boolean authenticate(String originstr, String ciphertext) {
		boolean result = false;
		try {
			//截取前16位随机数转成byte类型;
			String saltString = ciphertext.substring(0, 16);
			byte[] salt = new Base64().decode(saltString);
			String digest1 = ciphertext.substring(16);
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(salt);
			md.update(originstr.getBytes("UTF8"));
			//随机数与明文捆绑在一起同时加密码;
			byte[] digest = md.digest();
			String digest2 = new Base64().encodeToString(digest);
			//比较加密码后的密文与参数列表提供字符串是否相同;
			result = digest1.equals(digest2);
		} catch (Exception e) {
			//// e.printStackTrace()();
		}
		return result;
	}
  
}
