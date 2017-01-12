package com.mics.httpInterface;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IReportRequest {
	
	@GET("api/doctor/{userUID}/getReportListAfterTime")
	Call<ResponseBody> getReportListAfterTime(@Path("userUID") Integer userUID, @Query("time") Date time);
	
	@GET("api/doctor/{userUID}/uploadStatistics")
	Call<ResponseBody> uploadStatistics(@Path("userUID") Integer userUID, @Query("registerCount") int registerCount, @Query("collection") int collection);
}
