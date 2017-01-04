package com.mics.main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mics.entity.ReportContentBack;
import com.mics.httpRequest.HttpRequest;
import com.mics.httpRequest.ReportRequest;
import com.mics.synchronization.Cloud2Local;
import com.mics.utils.TimeAdapter;
import com.mics.utils.Util;
import com.mysql.jdbc.StandardSocketFactory;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.DoctorLogin();
		
		Cloud2Local cloud2Local = new Cloud2Local();
//		StoreSCP storeSCP = new StoreSCP();
//		storeSCP.startSCPServer();
		
//		WorkService workService = new WorkService();
//		try {
//			workService.start();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		GsonBuilder gb = new GsonBuilder();
//		Gson g = gb.registerTypeAdapter(Date.class, new TimeAdapter()).create();
//		
//		ReportRequest reportRequest = new ReportRequest();
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = simpleDateFormat.parse("2016-11-29");
//		
//		JsonParser parser=new JsonParser();
//		JsonObject obj=parser.parse(reportRequest.getReportListAfterTime(date)).getAsJsonObject();
//		JsonArray array=obj.getAsJsonArray("reportList");
//		System.out.println(array.get(0));
//		ReportContentBack report = (ReportContentBack)g.fromJson(array.get(0), ReportContentBack.class);
//		System.out.println(report.getModifiedTime());
//		String reportPath = Util.String2Map(array.get(0).toString()).get("reportPath").toString();
//		String result = reportRequest.dowloadReport(reportPath);
//		System.out.println(result);
		cloud2Local.start();
	}
}
