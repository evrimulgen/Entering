package com.mics.httpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import com.mics.conf.BaseConf;
import com.mics.httpInterface.IReportRequest;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ReportRequest extends BaseClass {

	private IReportRequest reportRequest;

	private InputStream is = null;

	public ReportRequest() {
		this.reportRequest = BaseClass.getRetrofit().create(IReportRequest.class);
	}

	/*
	 * 
	 * 
	 */
	public String getReportListAfterTime(Date time) throws IOException {
		// TODO Auto-generated method stub
		Call<ResponseBody> call = reportRequest.getReportListAfterTime(BaseConf.doctorUID, time);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		return result;
	}

	/*
	 * 下载报告JSON文件
	 * 
	 */
	public String dowloadReport(String fileUrl) throws IOException {

		Request request = new Request.Builder().url(fileUrl).build();
		okhttp3.Response response = BaseClass.getNewClient().newCall(request).execute();
		is = response.body().byteStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));  
		StringBuffer result = new StringBuffer();
		String line = null;
		while((line = reader.readLine()) != null){
			result.append(line);
		}
		
		return result.toString().replaceAll(" ", "");
	}

}
