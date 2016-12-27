package com.mics.conf;

public class BaseConf {

	public static String baseURL = "http://localhost:8080/mics/";
	public static String username = "meinian";
	public static String password = "123456";
	public static Integer doctorUID = null;
	//study缓存
	public static int studyCache_Max = 10;
	public static Integer hospitalNo = 444444;

	// SCP Config
	public static int Scp_port = 11112;
	public static String Scp_AETitle = "STORESCP";
	public static int Scp_CStore_Status = 0;
	public static String Scp_StorageDirectory = "E://save";

	public static String Dcm_PreFilePath = "ImageStore/dicom/";

	// 上传线程数MAX
//	public static int Upload_Thread_MAX = 10;
	
	//RabbitMQ配置
	public static String RM_username = "raymed";
	public static String RM_password = "R_aymed89909091";
	public static String RM_host = "hahateam.tpddns.cn";
	public static int RM_port = 5672; 

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

	public static String getRM_password() {
		return RM_password;
	}

	public static void setRM_password(String rM_password) {
		RM_password = rM_password;
	}

	public static String getRM_host() {
		return RM_host;
	}

	public static void setRM_host(String rM_host) {
		RM_host = rM_host;
	}

	public static int getRM_port() {
		return RM_port;
	}

	public static void setRM_port(int rM_port) {
		RM_port = rM_port;
	}
	
}