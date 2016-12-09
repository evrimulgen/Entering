package com.mics.httpInterface;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoctorRequest {

	@GET("api/doctor/login")
	Call<ResponseBody> doctorLogin(@Query("username") String username, @Query("password") String password,
			@Query("token") String token);

	@GET("api/doctor/{doctorUID}/getPatientList")
	Call<ResponseBody> getPatientList(@Path("doctorUID") Integer doctorUID, @Query("limit") Integer limit,
			@Query("offset") Integer offset, @Query("fetchPatients") String fetchPatients);

	@GET("api/doctor/{doctorUID}/queryRecord")
	Call<ResponseBody> queryRecord(@Path("doctorUID") Integer doctorUID,
			@Query("studyInstanceUID") String studyInstanceUID, @Query("seriesInstanceUID") String seriesInstanceUID,
			@Query("sopInstanceUID") String sopInstanceUID);

	@GET("api/doctor/{doctorUID}/creatPatientWithNo")
	Call<ResponseBody> creatPatientWithNo(@Path("doctorUID") Integer doctorUID, @Query("name") String name,
			@Query("sex") String sex, @Query("age") String patientAge, @Query("patientID") String patientID,
			@Query("username") String username);

	@GET("api/doctor/{doctorUID}/addPatientStudy")
	Call<ResponseBody> addPatientStudy(@Path("doctorUID") Integer doctorUID, @Query("userUID") Long userUID,
			@Query("studyInstanceUID") String studyInstanceUID, @Query("patientName") String patientName,
			@Query("patientUID") String patientUID, @Query("StudyDate") String StudyDate,
			@Query("StudyTime") String StudyTime, @Query("ModalitiesInStudy") String ModalitiesInStudy,
			@Query("InstitutionName") String InstitutionName, @Query("StudyDescription") String StudyDescription);

	@GET("api/doctor/{doctorUID}/addPatientSeries")
	Call<ResponseBody> addPatientSeries(@Path("doctorUID") Integer doctorUID, @Query("userUID") Long userUID,
			@Query("SeriesInstanceUID") String SeriesInstanceUID, @Query("StudyInstanceUID") String StudyInstanceUID,
			@Query("SeriesNumber") String SeriesNumber, @Query("SeriesDate") String SeriesDate,
			@Query("SeriesTime") String SeriesTime, @Query("SeriesDescription") String SeriesDescription,
			@Query("Modality") String Modality, @Query("BodyPartExamined") String BodyPartExamined,
			@Query("AcquisitionNumber") String AcquisitionNumber);

	@GET("api/doctor/{doctorUID}/addPatientImage")
	Call<ResponseBody> addPatientImage(@Path("doctorUID") Integer doctorUID,
			@Query("sopInstanceUID") String sopInstanceUID, @Query("filePath") String filePath,
			@Query("seriesInstanceUID") String seriesInstanceUID, @Query("InsertTimestamp") String serialNumber,
			@Query("SpaceLocation") String spaceLocation);

	@GET("api/doctor/{doctorUID}/addClinicalRecord")
	Call<ResponseBody> addClinicalRecord(@Path("doctorUID") Integer doctorUID, @Query("userUID") Long userUID,
			@Query("serialNumber") String serialNumber, @Query("institutionId") Integer hospitalNo);

	@GET("api/doctor/{doctorUID}/getClinicalRecord")
	Call<ResponseBody> getClinicalRecord(@Path("doctorUID") Integer doctorUID,
			@Query("serialNumber") String serialNumber, @Query("institutionId") String institutionId);

	@GET("api/doctor/{doctorUID}/getImageStorePath")
	Call<ResponseBody> getImageStorePath(@Path("doctorUID") Integer doctorUID, @Query("filePath") String filePath);

	@GET("api/doctor/{doctorUID}/uploadNotify")
	Call<ResponseBody> uploadNotify(@Path("doctorUID") Integer doctorUID, @Query("filepath") String filepath,
			@Query("type") Integer type);

	@GET("api/doctor/{doctorUID}/createOrderByDoctor")
	Call<ResponseBody> createOrderByDoctor(@Path("doctorUID") Integer doctorUID,
			@Query("hospitalUID") Integer hospitalNo, @Query("studyInstanceUID") String studyInstanceUID,
			@Query("patientUID") Long patientUID);
}
