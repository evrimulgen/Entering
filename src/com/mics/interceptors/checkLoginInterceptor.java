package com.mics.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class checkLoginInterceptor implements Interceptor{
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
//		System.out.println("----------------------------------------------------------------------------");
//		System.out.println(request.headers().toString());
		Response response = chain.proceed(request);
//		System.out.println(response.headers().toString());
//		System.out.println("----------------------------------------------------------------------------");
		return response;
	}

}
