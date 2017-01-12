package com.mics.conf;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.mics.utils.Util;

public class BaseConf {

	private String mysqlUrl = "jdbc:mysql:///dcm4chee";
	private String mysqlUserName = "pacs";
	private String mysqlPass = "pacs";
	private String oracleUrl = "jdbc:oracle:thin:@127.0.0.1:1521:TYYL";
	private String oracleUserName = "tyyl";
	private String oraclePass = "tyylmn150910";
	private int TimerTaskPoint = 300000;

	private String baseURL = "http://192.168.1.107:8080/mics/";
	private String username = "meinian";
	private String password = "123456";
	private Integer doctorUID = null;
	// study缓存
	private int studyCache_Max = 10;
	private Integer hospitalNo = 444444;

	// SCP Config
	private int Scp_port = 11112;
	private String Scp_AETitle = "STORESCP";
	private int Scp_CStore_Status = 0;
	private String Scp_StorageDirectory = "E://save";

	private String Dcm_PreFilePath = "ImageStore/dicom/";

	// 上传线程数MAX
	// private static int Upload_Thread_MAX = 10;

	// RabbitMQ配置
	private String RM_username = "";
	private String RM_password = "";
	private String RM_host = "hahateam.tpddns.cn";
	private int RM_port = 5672;

	private static BaseConf baseConf = null;

	public static BaseConf getInstance() {
		if (null == baseConf) {
			baseConf = createInstance();
		}
		return baseConf;
	}

	private static BaseConf createInstance() {
		try {
			File file = new File(System.getProperty("user.dir") + File.separator + "mics.conf");
			String content = FileUtils.readFileToString(file);
			return Util.fromJsonString(content, BaseConf.class);
		} catch (Exception e) {
			System.out.println("load mics.conf failed");
		}

		return new BaseConf();
	}

	public String getMysqlUrl() {
		return mysqlUrl;
	}

	public void setMysqlUrl(String mysqlUrl) {
		this.mysqlUrl = mysqlUrl;
	}

	public String getMysqlUserName() {
		return mysqlUserName;
	}

	public void setMysqlUserName(String mysqlUserName) {
		this.mysqlUserName = mysqlUserName;
	}

	public String getMysqlPass() {
		return mysqlPass;
	}

	public void setMysqlPass(String mysqlPass) {
		this.mysqlPass = mysqlPass;
	}

	public String getOracleUrl() {
		return oracleUrl;
	}

	public void setOracleUrl(String oracleUrl) {
		this.oracleUrl = oracleUrl;
	}

	public String getOracleUserName() {
		return oracleUserName;
	}

	public void setOracleUserName(String oracleUserName) {
		this.oracleUserName = oracleUserName;
	}

	public String getOraclePass() {
		return oraclePass;
	}

	public void setOraclePass(String oraclePass) {
		this.oraclePass = oraclePass;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDoctorUID() {
		return doctorUID;
	}

	public void setDoctorUID(Integer doctorUID) {
		this.doctorUID = doctorUID;
	}

	public int getStudyCache_Max() {
		return studyCache_Max;
	}

	public void setStudyCache_Max(int studyCache_Max) {
		this.studyCache_Max = studyCache_Max;
	}

	public Integer getHospitalNo() {
		return hospitalNo;
	}

	public void setHospitalNo(Integer hospitalNo) {
		this.hospitalNo = hospitalNo;
	}

	public int getScp_port() {
		return Scp_port;
	}

	public void setScp_port(int scp_port) {
		Scp_port = scp_port;
	}

	public String getScp_AETitle() {
		return Scp_AETitle;
	}

	public void setScp_AETitle(String scp_AETitle) {
		Scp_AETitle = scp_AETitle;
	}

	public int getScp_CStore_Status() {
		return Scp_CStore_Status;
	}

	public void setScp_CStore_Status(int scp_CStore_Status) {
		Scp_CStore_Status = scp_CStore_Status;
	}

	public String getScp_StorageDirectory() {
		return Scp_StorageDirectory;
	}

	public void setScp_StorageDirectory(String scp_StorageDirectory) {
		Scp_StorageDirectory = scp_StorageDirectory;
	}

	public String getDcm_PreFilePath() {
		return Dcm_PreFilePath;
	}

	public void setDcm_PreFilePath(String dcm_PreFilePath) {
		Dcm_PreFilePath = dcm_PreFilePath;
	}

	public String getRM_username() {
		return RM_username;
	}

	public void setRM_username(String rM_username) {
		RM_username = rM_username;
	}

	public String getRM_password() {
		return RM_password;
	}

	public void setRM_password(String rM_password) {
		RM_password = rM_password;
	}

	public String getRM_host() {
		return RM_host;
	}

	public void setRM_host(String rM_host) {
		RM_host = rM_host;
	}

	public int getRM_port() {
		return RM_port;
	}

	public void setRM_port(int rM_port) {
		RM_port = rM_port;
	}

	public static BaseConf getBaseConf() {
		return baseConf;
	}

	public static void setBaseConf(BaseConf baseConf) {
		BaseConf.baseConf = baseConf;
	}

	public int getTimerTaskPoint() {
		return TimerTaskPoint;
	}

	public void setTimerTaskPoint(int timerTaskPoint) {
		TimerTaskPoint = timerTaskPoint;
	}
	
}