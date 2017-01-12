package com.mics.httpRequest;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.mics.conf.BaseConf;
import com.mics.interceptors.NetLoginInterceptor;
import com.mics.interceptors.checkLoginInterceptor;
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
	private static BaseConf baseConf = BaseConf.getInstance();

	public static Retrofit getRetrofit() {
		X509TrustManager xtm = new X509TrustManager() {

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
			}
            
        };
        
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
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
					.addInterceptor(new NetLoginInterceptor())
//					.addNetworkInterceptor(new checkLoginInterceptor())
					.sslSocketFactory(sslContext.getSocketFactory())
					.hostnameVerifier(DO_NOT_VERIFY)
					.build();

			retrofit = new Retrofit.Builder().baseUrl(HttpUrl.parse(baseConf.getBaseURL()))
					.addConverterFactory(GsonConverterFactory.create()).client(client).build();
		}
		return retrofit;
	}

	public static Map<String, Long> getStudyCache() {
		return studyCache;
	}

	public static void addStudyCache(String key, Object value) {
		if (studyCache.size() >= baseConf.getStudyCache_Max()) {
			studyCache.remove(studyCache.keySet().iterator().next());
		}
		Long userUID = new Double((Double) value).longValue();
		studyCache.put(key, userUID);
	}

	public static OkHttpClient getNewClient() {
		return new OkHttpClient.Builder().build();
	}

}