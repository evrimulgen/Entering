package com.mics.main;

import java.io.IOException;

import com.mics.httpRequest.HttpRequest;
import com.mics.rabbitmq.WorkService;
import com.mics.scp.StoreSCP;

public class Main {

	public static void main(String[] args) throws IOException {
//		HttpRequest httpRequest = new HttpRequest();
//		httpRequest.DoctorLogin();
//		StoreSCP storeSCP = new StoreSCP();
//		storeSCP.startSCPServer();
		
		WorkService workService = new WorkService();
		try {
			workService.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
