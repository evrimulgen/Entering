package com.mics.main;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.mics.conf.BaseConf;
import com.mics.httpRequest.HttpRequest;
import com.mics.httpRequest.ReportRequest;

public class Main {

	public static void main(String[] args) throws IOException, ParseException {
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.DoctorLogin();
		ReportRequest reportRequest = new ReportRequest();
		BaseConf baseConf = BaseConf.getInstance();
		
		Timer timer = new Timer(true);
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				try {
					reportRequest.uploadStatistics();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		
		timer.schedule(timerTask, new Date(), baseConf.getTimerTaskPoint());
		while(true){
		}
	}
}
