package com.mics.test;

import org.junit.Before;
import org.junit.Test;

import com.mics.utils.JDBCUtil;

public class JDBCTest {
	private JDBCUtil jdbcUtil = null;
	String filename;
	String filepath;
	
	@Before
	public void init(){
		filename = "abcdef";
		filepath = "D:/abcdef";
		jdbcUtil = new JDBCUtil();
	}
	
	@Test
	public void test(){
		jdbcUtil.executeUpdate(filename,filepath);
	}
}
