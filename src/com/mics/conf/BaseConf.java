package com.mics.conf;

public class BaseConf {
	
	public static String baseURL = "http://192.168.200.186:8080/mics/";
	public static String username = "meinian"; 
	public static String password = "123456";
	public static String doctorUID = null;
	public static int studyCache_Max = 10;
	public static String hospitalNo = "5";
	
	
	//SCP Config
	public static int Scp_port = 11112;
	public static String Scp_AETitle = "STORESCP";
	public static int Scp_CStore_Status = 0;
	public static String Scp_StorageDirectory = "E:\\save";
	
	public static String Dcm_PreFilePath = "ImageStore/dicom/";
	
	//上传线程数MAX
	public static int Upload_Thread_MAX = 10;

	public static String getDoctorUID() {
		return doctorUID;
	}

	public static void setDoctorUID(String doctorUID) {
		BaseConf.doctorUID = doctorUID;
	}

	public static String getHospitalNo() {
		return hospitalNo;
	}

	public static void setHospitalNo(String hospitalNo) {
		BaseConf.hospitalNo = hospitalNo;
	}
}