package com.mics.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util {
	
	private static GsonBuilder gb = new GsonBuilder();
	
	public static String getToken(String username,String password){
		String plainText = username + password + "RaymedTECH";
		try {
	        //生成实现指定摘要算法的 MessageDigest 对象
	        MessageDigest md = MessageDigest.getInstance("MD5");  
	        //使用指定的字节数组更新摘要
	        md.update(plainText.getBytes());
	        //通过执行诸如填充之类的最终操作完成哈希计算
	        byte b[] = md.digest();
	        //生成具体的md5密码到buf数组
	        int i;
	        StringBuffer buf = new StringBuffer("");
	        for (int offset = 0; offset < b.length; offset++) {
	          i = b[offset];
	          if (i < 0)
	            i += 256;
	          if (i < 16)
	            buf.append("0");
	          buf.append(Integer.toHexString(i));
	        }
	        return buf.toString();
	     } 
	     catch (Exception e) {
	       e.printStackTrace();
	     }
		return null;
	}
	
	public static synchronized Map<String, Object> String2Map(String object){
		Gson g = gb.create();
//		return g.fromJson(object, new TypeToken<Map<String, Object>>() {}.getType());
		return g.fromJson(object, HashMap.class);
	}
	
	public static <T> T fromJsonString(String str, Class<?> T){
		Gson gson = new Gson();
		try{
			return (T) gson.fromJson(str, T);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
