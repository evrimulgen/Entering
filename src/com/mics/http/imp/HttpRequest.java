package com.mics.http.imp;

import java.io.IOException;

import com.mics.conf.BaseConf;
import com.mics.http.DoctorRequest;
import com.mics.utils.Util;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class HttpRequest extends BaseClass {
	
	private DoctorRequest doctorRequest;
	
	public HttpRequest() {
		this.doctorRequest = BaseClass.getRetrofit().create(DoctorRequest.class);
	}
	
	
	public Boolean DoctorLogin() throws IOException{
		Call<ResponseBody> call = doctorRequest.doctorLogin(BaseConf.username, BaseConf.password, Util.getToken(BaseConf.username, BaseConf.password));
		Response<ResponseBody> response = call.execute();
		
		return false;
	}
	
	
	public void getPatientList() throws IOException{
		Call<ResponseBody> call = doctorRequest.getPatientList(5, 0, 0, "ALL");
		Response<ResponseBody> response = call.execute();
//		System.out.println(response.headers().toString());
		System.out.println(response.body().string());
	}
}
