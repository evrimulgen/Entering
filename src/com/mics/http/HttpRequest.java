package com.mics.http;

import com.mics.conf.BaseConf;
import com.mics.http.inter.DoctorRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class HttpRequest extends BaseClass {
	
	private DoctorRequest doctorRequest;
	
	public HttpRequest() {
		super();
		this.doctorRequest = retrofit.create(DoctorRequest.class);
	}
	
	
	public boolean DoctorLogin(){
//		Call<ResponseBody> call = doctorRequest.doctorLogin(BaseConf., password, token)
		return false;
	}
	
}
