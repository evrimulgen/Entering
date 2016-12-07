package com.mics.httpRequest;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mics.conf.BaseConf;
import com.mics.httpInterface.DoctorRequest;
import com.mics.utils.Util;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpRequest extends BaseClass {
	
	private DoctorRequest doctorRequest;
	private static GsonBuilder gb = new GsonBuilder();
	private Gson g;
	
	public HttpRequest() {
		this.doctorRequest = BaseClass.getRetrofit().create(DoctorRequest.class);
		this.g = gb.create();
	}
	
	
	public Boolean DoctorLogin() throws IOException{
		Call<ResponseBody> call = doctorRequest.doctorLogin(BaseConf.username, BaseConf.password, Util.getToken(BaseConf.username, BaseConf.password));
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		Map<String, Object> map = Util.String2Map(result);
		Map<String, Object> user = Util.String2Map(g.toJson(map.get("User")));
		if((Double)map.get("errorCode") == 0.0){
			BaseConf.setDoctorUID(user.get("userUID").toString());
			System.out.println(user.get("userUID"));
			return true;
		}
		return false;
	}
	
	
//	public void getPatientList() throws IOException{
//		Call<ResponseBody> call = doctorRequest.getPatientList(5, 0, 0, "ALL");
//		Response<ResponseBody> response = call.execute();
//		System.out.println(response.body().string());
//	}
	
	public Map<String, Object> queryRecord(String studyInstanceUID, String seriesInstanceUID, String sopInstanceUID) throws IOException{
		Call<ResponseBody> call = doctorRequest.queryRecord(BaseConf.doctorUID, studyInstanceUID, seriesInstanceUID, sopInstanceUID);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	public Map<String, Object> creatPatientWithNo(String name, String sex, String i, String patientID, String username) throws IOException{
		Call<ResponseBody> call = doctorRequest.creatPatientWithNo(BaseConf.doctorUID, name, sex, i, patientID, username);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	public Map<String, Object> addStudy(Long UserUID, String studyInstanceUID, String patientName, String patientUID, String StudyDate, String StudyTime, String ModalitiesInStudy, String InstitutionName, String StudyDescription) throws IOException{
		Call<ResponseBody> call = doctorRequest.addPatientStudy(BaseConf.doctorUID, UserUID, studyInstanceUID, patientName, patientUID, StudyDate, StudyTime, ModalitiesInStudy, InstitutionName, StudyDescription);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	public Map<String, Object> addSeries(Long UserUID, String SeriesInstanceUID, String StudyInstanceUID, String SeriesNumber, String SeriesDate, String SeriesTime, String SeriesDescription, String Modality, String BodyPartExamined, String AcquisitionNumber) throws IOException{
		Call<ResponseBody> call = doctorRequest.addPatientSeries(BaseConf.doctorUID, UserUID, SeriesInstanceUID, StudyInstanceUID, SeriesNumber, SeriesDate, SeriesTime, SeriesDescription, Modality, BodyPartExamined, AcquisitionNumber);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	public Map<String, Object> addClinicalRecord(Long userUID, String serialNumber, String institutionId) throws IOException{
		Call<ResponseBody> call = doctorRequest.addClinicalRecord(BaseConf.doctorUID, userUID, serialNumber, institutionId);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	public Map<String, Object> getClinicalRecord(String serialNumber, String institutionId) throws IOException{
		Call<ResponseBody> call = doctorRequest.getClinicalRecord(BaseConf.doctorUID, serialNumber, institutionId);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	public Map<String, Object> addPatientImage(String sopInstanceUID, String filePath, String seriesInstanceUID, String serialNumber, String spaceLocation) throws IOException{
		Call<ResponseBody> call = doctorRequest.addPatientImage(BaseConf.doctorUID, sopInstanceUID,
				filePath, seriesInstanceUID,
				serialNumber, spaceLocation);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	public Map<String, Object> getImageStorePath(String filePath) throws IOException{
		Call<ResponseBody> call = doctorRequest.getImageStorePath(BaseConf.doctorUID, filePath);
		Response<ResponseBody> response = call.execute();
		String result = new String(response.body().bytes());
		System.out.println(result);
		Map<String, Object> map = Util.String2Map(result);
		return map;
	}
	
	public void uploadDcm(String url, File file) throws IOException{
		
		RequestBody requestFile =
	            RequestBody.create(MediaType.parse("multipart/form-data"), file);

		MultipartBody.Part body =
	            MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
		
		String descriptionString = "hello";
		
		RequestBody description =
	            RequestBody.create(
	                    MediaType.parse("multipart/form-data"), descriptionString);

		Call<ResponseBody> call = doctorRequest.uploadDcm(url, description, body);
		call.enqueue(new Callback<ResponseBody>() {
	        @Override
	        public void onResponse(Call<ResponseBody> call,
	                               Response<ResponseBody> response) {
	            System.out.println("Upload : success");
	        }

	        @Override
	        public void onFailure(Call<ResponseBody> call, Throwable t) {
	            System.out.println("Upload error:" + t.getMessage());
	        }
		});
	}
}
