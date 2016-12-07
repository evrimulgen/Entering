package com.mics.httpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mics.conf.BaseConf;
import com.mics.interceptors.NetLoginInterceptor;
import com.mics.utils.GsonConverterFactory;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class BaseClass {
	private static Retrofit retrofit;
	private static OkHttpClient client;
	private static Map<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();
	private static Map<String, Long> studyCache = new HashMap<>();
	
	public static Retrofit getRetrofit() {
		if (retrofit == null) {
			client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
				@Override
				public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {
					// TODO Auto-generated method stub
					cookieStore.put(arg0.host(), arg1);
				}

				@Override
				public List<Cookie> loadForRequest(HttpUrl arg0) {
					List<Cookie> cookies = cookieStore.get(arg0.host());
					return cookies != null ? cookies : new ArrayList<Cookie>();
				}
			})
					// .authenticator(new Authenticator() {
					// @Override
					// public Request authenticate(Route route, Response
					// response) throws IOException {
					// System.out.println("Authenticating for response: " +
					// response);
					// System.out.println("Challenges: " +
					// response.challenges());
					// String credential = Credentials.basic("jesse",
					// "password1");
					// return
					// response.request().newBuilder().header("Authorization",
					// credential).build();
					// }
					//
					// @Override
					// public Request authenticate(Route arg0, okhttp3.Response
					// arg1) throws IOException {
					// // TODO Auto-generated method stub
					// return null;
					// }
					// })
					.addInterceptor(new NetLoginInterceptor())
//					.addNetworkInterceptor(new checkLoginInterceptor())
					.build();

			retrofit = new Retrofit.Builder().baseUrl(HttpUrl.parse(BaseConf.baseURL))
					.addConverterFactory(GsonConverterFactory.create()).client(client).build();
		}
		return retrofit;
	}

	public static Map<String, Long> getStudyCache() {
		return studyCache;
	}

	public static void addStudyCache(String key, Object value) {
		if(studyCache.size() >= BaseConf.studyCache_Max){
			studyCache.remove(studyCache.keySet().iterator().next());
		}
		Long userUID = new Double((Double)value).longValue();
		studyCache.put(key, userUID);
	}
}
