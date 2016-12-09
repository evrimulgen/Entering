package com.mics.conf;

public class BaseConf {
	
	public static String baseURL = "http://192.168.200.116/";
	public static String username = "meinian"; 
	public static String password = "123456";
	public static Integer doctorUID = null;
	public static int studyCache_Max = 10;
	public static Integer hospitalNo = 444444;
	
	
	//SCP Config
	public static int Scp_port = 11112;
	public static String Scp_AETitle = "STORESCP";
	public static int Scp_CStore_Status = 0;
	public static String Scp_StorageDirectory = "E:\\save";
	
	public static String Dcm_PreFilePath = "ImageStore/dicom/";
	
	//上传线程数MAX
	public static int Upload_Thread_MAX = 10;

	public static Integer getDoctorUID() {
		return doctorUID;
	}

	public static void setDoctorUID(Integer doctorUID) {
		BaseConf.doctorUID = doctorUID;
	}

	public static Integer getHospitalNo() {
		return hospitalNo;
	}

	public static void setHospitalNo(Integer hospitalNo) {
		BaseConf.hospitalNo = hospitalNo;
	}
}