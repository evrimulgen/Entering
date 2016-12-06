package com.mics.http.imp;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
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
		System.out.println(response.body().string());
	}
	
	public Map<String, Object> queryRecord(String studyInstanceUID, String seriesInstanceUID, String sopInstanceUID) throws IOException{
		Call<ResponseBody> call = doctorRequest.queryRecord(5, studyInstanceUID, seriesInstanceUID, sopInstanceUID);
		Response<ResponseBody> response = call.execute();
		Map<String, Object> map = Util.String2Map(response.body().string());
		return map;
	}
}
