package com.mics.conf;

public class BaseConf {
	
	public static String baseURL = "http://192.168.200.116";
	public static String username = "meinian"; 
	public static String password = "123456";
	
	//SCP Config
	public static int Scp_port = 11112;
	public static String Scp_AETitle = "STORESCP";
	public static int Scp_CStore_Status = 0;
	public static String Scp_StorageDirectory = "E:\\save";
	
	//上传线程数MAX
	public static int Upload_Thread_MAX = 10;
}
