package com.mics.http.inter;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoctorRequest {

	@GET("api/doctor/login")
	Call<ResponseBody> doctorLogin(@Query("username") String username, @Query("password") String password, @Query("token") String token);
	
	
	@GET("api/doctor/{doctorUID}/getPatientList")
	Call<ResponseBody> getPatientList(@Path("doctorUID") Integer doctorUID, @Query("limit") Integer limit, @Query("offset") Integer offset, @Query("fetchPatients") String fetchPatients);
}
