package com.mics.http.inter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DoctorRequest {

	@GET("api/doctor/login")
	Call<ResponseBody> doctorLogin(@Path("username") String username, @Path("password") String password, @Path("token") String token);
	
}
