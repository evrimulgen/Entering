package com.cc.interceptors;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mics.http.HttpRequest;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class NetLoginInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		Response response = chain.proceed(request);
		
		ResponseBody body = response.body();

        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.defaultCharset();
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, Object> map = g.fromJson(bodyString, new TypeToken<Map<String, Object>>() {}.getType());
        if((double)map.get("errorCode") == 2.0){
        	System.out.println("未登录，正在尝试登陆。。。");
//        	HttpRequest httpRequest = new HttpRequest();
//        	
//        	System.out.println(oldRequest.headers().size());
//        	
//        	String JSESSIONID = httpRequest.DoctorLogin().split(";")[0];
//        	
//        	Builder builder = new Builder();
//        	for(int i = 0; i < request.headers().size(); i++){
//        		builder.add(request.headers().name(i),request.headers().value(i));
//        		System.out.println(request.headers().name(i) +" "+ request.headers().value(i));
//        	}
//        	Headers header = builder.add("Cookie", JSESSIONID).build();
//        	
//        	System.out.println(header.toString());
////        	oldHeader.newBuilder().add("Cookie", JSESSIONID).build();
//        	request.newBuilder()
////        	.addHeader("Cookie", JSESSIONID)
//        	.headers(header)
//        	.method(request.method(), request.body())
//        	.build();
//        	System.out.println("1");
//        	System.out.println(request.headers().toString());
//        	response = chain.proceed(request);
//        	System.out.println("---->");
        	HttpRequest httpRequest = new HttpRequest();
        	httpRequest.DoctorLogin();
        	response = chain.proceed(request);
        };
		return response;
	}

}
