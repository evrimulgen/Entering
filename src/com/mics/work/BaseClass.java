package com.mics.work;

import com.mics.conf.BaseConf;
import com.mics.utils.GsonConverterFactory;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class BaseClass {
	protected Retrofit retrofit;
	private OkHttpClient client;
	
	public BaseClass() {
		
		retrofit = new Retrofit.Builder().baseUrl(HttpUrl.parse(BaseConf.baseURL))
				.addConverterFactory(GsonConverterFactory.create())
				.client(client)
				.build();
	}

}
