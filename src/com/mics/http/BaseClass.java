package com.mics.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mics.conf.BaseConf;
import com.mics.utils.GsonConverterFactory;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class BaseClass {
	protected Retrofit retrofit;
	private OkHttpClient client;
	private Map<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();

	public BaseClass() {
		
		client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
			@Override
			public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {
				// TODO Auto-generated method stub
				System.out.println("^^^^^^^^^^^^" + arg0);
				cookieStore.put(arg0.host(), arg1);
			}
			
			@Override
			public List<Cookie> loadForRequest(HttpUrl arg0) {
				System.out.println("^^^^^^^^^^^^" + arg0);
				System.out.println(arg0.host());
				List<Cookie> cookies = cookieStore.get(arg0.host());
	            return cookies != null ? cookies : new ArrayList<Cookie>();
			}
		}).build();
		
		retrofit = new Retrofit.Builder().baseUrl(HttpUrl.parse(BaseConf.baseURL))
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();
	}

}
