package com.mics.httpRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mics.conf.BaseConf;
import com.mics.httpInterface.DoctorRequest;
import com.mics.utils.Util;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * @author 72338
 *
 */
public class HttpRequest extends BaseClass {

	private DoctorRequest doctorRequest;
	private static GsonBuilder gb = new GsonBuilder();
	private Gson g;

	public HttpRequest() {
		this.doctorRequest = BaseClass.getRetrofit().create(DoctorRequest.class);
		this.g = gb.create();
	}

	
	/**
	 * 医生登陆
	 * 
	 * @return
	 * @throws IOException
	 * 
	 */
	public Boolean DoctorLogin() throws IOException {
		Call<ResponseBody> call = doctorRequest.doctorLogin(BaseConf.username, BaseConf.password,
				Util.getToken(BaseConf.username, BaseConf.password));
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		Map<String, Object> user = Util.String2Map(g.toJson(map.get("User")));
		if ((Double) map.get("errorCode") == 0.0) {
			double userUID = (double)user.get("userUID");
			double hospitalUID = (double)user.get("hospitalUID");
			int doctorUID = (int)userUID;
			int hospitalNo = (int)hospitalUID;
			
			BaseConf.setDoctorUID(doctorUID);
			BaseConf.setHospitalNo(hospitalNo);
			return true;
		}
		return false;
	}

	// public void getPatientList() throws IOException{
	// Call<ResponseBody> call = doctorRequest.getPatientList(5, 0, 0, "ALL");
	// Response<ResponseBody> response = call.execute();
	// System.out.println(response.body().string());
	// }

	/**
	 * 查询记录
	 * 
	 * @param studyInstanceUID
	 * @param seriesInstanceUID
	 * @param sopInstanceUID
	 * @return
	 * @throws IOException
	 * 
	 */
	public Map<String, Object> queryRecord(String studyInstanceUID, String seriesInstanceUID, String sopInstanceUID)
			throws IOException {
		Call<ResponseBody> call = doctorRequest.queryRecord(BaseConf.doctorUID, studyInstanceUID, seriesInstanceUID,
				sopInstanceUID);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
//		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}

	/**
	 * 创建病人
	 * 
	 * @param name
	 * @param sex
	 * @param i
	 * @param patientID
	 * @param username
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> creatPatientWithNo(String name, String sex, String i, String patientID, String username)
			throws IOException {
		Call<ResponseBody> call = doctorRequest.creatPatientWithNo(BaseConf.doctorUID, name, sex, i, patientID,
				username);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}

	/**
	 * 新增study
	 * 
	 * @param UserUID
	 * @param studyInstanceUID
	 * @param patientName
	 * @param patientUID
	 * @param StudyDate
	 * @param StudyTime
	 * @param ModalitiesInStudy
	 * @param InstitutionName
	 * @param StudyDescription
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> addStudy(Long UserUID, String studyInstanceUID, String patientName, String patientUID,
			String StudyDate, String StudyTime, String ModalitiesInStudy, String InstitutionName,
			String StudyDescription) throws IOException {
		Call<ResponseBody> call = doctorRequest.addPatientStudy(BaseConf.doctorUID, UserUID, studyInstanceUID,
				patientName, patientUID, StudyDate, StudyTime, ModalitiesInStudy, InstitutionName, StudyDescription);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}

	
	
	/**
	 * 新增series
	 * 
	 * @param UserUID
	 * @param SeriesInstanceUID
	 * @param StudyInstanceUID
	 * @param SeriesNumber
	 * @param SeriesDate
	 * @param SeriesTime
	 * @param SeriesDescription
	 * @param Modality
	 * @param BodyPartExamined
	 * @param AcquisitionNumber
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> addSeries(Long UserUID, String SeriesInstanceUID, String StudyInstanceUID,
			String SeriesNumber, String SeriesDate, String SeriesTime, String SeriesDescription, String Modality,
			String BodyPartExamined, String AcquisitionNumber) throws IOException {
		Call<ResponseBody> call = doctorRequest.addPatientSeries(BaseConf.doctorUID, UserUID, SeriesInstanceUID,
				StudyInstanceUID, SeriesNumber, SeriesDate, SeriesTime, SeriesDescription, Modality, BodyPartExamined,
				AcquisitionNumber);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}

	/**
	 * 关联病历号
	 * 
	 * @param userUID
	 * @param serialNumber
	 * @param hospitalNo
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> addClinicalRecord(Long userUID, String serialNumber, Integer hospitalNo)
			throws IOException {
		Call<ResponseBody> call = doctorRequest.addClinicalRecord(BaseConf.doctorUID, userUID, serialNumber,
				hospitalNo);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}

	/**
	 * 获取病历号
	 * 
	 * @param serialNumber
	 * @param institutionId
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> getClinicalRecord(String serialNumber, String institutionId) throws IOException {
		Call<ResponseBody> call = doctorRequest.getClinicalRecord(BaseConf.doctorUID, serialNumber, institutionId);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}

	
	
	/**
	 * 新增Image
	 * 
	 * @param sopInstanceUID
	 * @param filePath
	 * @param seriesInstanceUID
	 * @param serialNumber
	 * @param spaceLocation
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> addPatientImage(String sopInstanceUID, String filePath, String seriesInstanceUID,
			String serialNumber, String spaceLocation) throws IOException {
		Call<ResponseBody> call = doctorRequest.addPatientImage(BaseConf.doctorUID, sopInstanceUID, filePath,
				seriesInstanceUID, serialNumber, spaceLocation);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}

	
	
	/**
	 * 获取Image上传路径
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> getImageStorePath(String filePath) throws IOException {
		Call<ResponseBody> call = doctorRequest.getImageStorePath(BaseConf.doctorUID, filePath);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	/**
	 * 创建报告订单
	 * 
	 * @param hospitalNo
	 * @param studyInstanceUID
	 * @param long1
	 * @return
	 * @throws IOException
	 */
	public Map<String, Object> createOrderByDoctor(Integer hospitalNo, String studyInstanceUID, Long long1) throws IOException {
		Call<ResponseBody> call = doctorRequest.createOrderByDoctor(BaseConf.doctorUID, hospitalNo, studyInstanceUID, long1);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}

	
	
	/**
	 * 上传DCM文件
	 * 
	 * @param strUrl
	 * @param file
	 * @throws IOException
	 */
	public void uploadDcm(String strUrl, File file) throws IOException {
		
		URL url = new URL(strUrl);
		
		RequestBody fileBody = RequestBody.create(MediaType.parse("application/form-data"), file);
		RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
				.addFormDataPart("file", file.getName(), fileBody)
				.build();
		Request request = new Request.Builder().addHeader("Accept", "*/*").url(strUrl).put(requestBody).build();

		BaseClass.getNewClient().newCall(request).enqueue(new Callback() {

			@Override
			public void onFailure(okhttp3.Call arg0, IOException arg1) {
				// TODO Auto-generated method stub
				System.out.println(arg1.getMessage());
				doctorRequest.uploadNotify(BaseConf.doctorUID, url.getPath(), 0);
			}

			@Override
			public void onResponse(okhttp3.Call arg0, okhttp3.Response arg1) throws IOException {
				// TODO Auto-generated method stub
				System.out.println(arg1.body().string());
				doctorRequest.uploadNotify(BaseConf.doctorUID, url.getPath(), 1);
			}
		});
	}
}
