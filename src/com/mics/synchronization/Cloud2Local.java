package com.mics.synchronization;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mics.entity.ReportContentBack;
import com.mics.httpRequest.ReportRequest;
import com.mics.utils.TimeAdapter;
import com.mics.utils.Util;

public class Cloud2Local {
	
	private ReportRequest reportRequest;
	private JsonParser parser;
	
	public Cloud2Local() {
		this.reportRequest = new ReportRequest();
		this.parser=new JsonParser();
	}
	
	public void start() throws ParseException, JsonSyntaxException, IOException {
		// TODO Auto-generated method stub
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = simpleDateFormat.parse("2016-11-29");
		
		GsonBuilder gb = new GsonBuilder();
		Gson g = gb.registerTypeAdapter(Date.class, new TimeAdapter()).create();
		
		JsonObject obj=parser.parse(reportRequest.getReportListAfterTime(date)).getAsJsonObject();
		JsonArray array=obj.getAsJsonArray("reportList");
		for(int i = 0; i < array.size(); i++){
			ReportContentBack reportContentBack = (ReportContentBack)g.fromJson(array.get(i), ReportContentBack.class);
			System.out.println("---------------->"+reportContentBack);
			String json = reportRequest.dowloadReport(Util.String2Map(array.get(i).toString()).get("reportPath").toString());
			reportContentBack = JsonToObject(json, reportContentBack);
			System.out.println(reportContentBack);
		}
	}
	
	
	private ReportContentBack JsonToObject(String json, ReportContentBack reportContentBack){
		
		Map<String, Object> map = Util.String2Map(json);
		
		reportContentBack.setReviewedResult(map.get("reviewedResult") == null ? "无" : map.get("reviewedResult").toString());
		reportContentBack.setRepresentation(map.get("representation") == null ? "无" : map.get("representation").toString());
		reportContentBack.setPartsResult(map.get("partsResult") == null ? "无" : map.get("partsResult").toString());
		reportContentBack.setExamResult(map.get("examResult") == null ? "无" : map.get("examResult").toString());
		reportContentBack.setSettingResult(map.get("settingResult") == null ? "无" : map.get("settingResult").toString());
		return reportContentBack;
	}

}
