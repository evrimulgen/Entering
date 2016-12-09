package com.mics.interceptors;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import com.mics.httpRequest.HttpRequest;
import com.mics.utils.Util;

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
		
		
		if (request.method().equals("GET")) {
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
			Map<String, Object> map = Util.String2Map(bodyString);
			if ((double) map.get("errorCode") == 2.0) {
				System.out.println("未登录，正在尝试登陆。。。");
				HttpRequest httpRequest = new HttpRequest();
				httpRequest.DoctorLogin();
				response = chain.proceed(request);
			}
		}
		return response;
	}
}
