package com.mics.test;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import com.mics.httpRequest.HttpRequest;

public class HttpTest {

	private HttpRequest httpRequest;

	@Before
	public void init() {
		httpRequest = new HttpRequest();
	}

	@Test
	public void test() throws InterruptedException, FileNotFoundException {
	}
	
}
