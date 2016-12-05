package com.mics.test;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.mics.http.imp.HttpRequest;

public class HttpTest {

	private HttpRequest httpRequest;

	@Before
	public void init() {
		httpRequest = new HttpRequest();
	}

	@Test
	public void test() throws InterruptedException {
		try {
			httpRequest.getPatientList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
