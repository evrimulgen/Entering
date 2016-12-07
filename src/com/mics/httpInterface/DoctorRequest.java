package com.mics.httpInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DoctorRequest {

	@GET("api/doctor/login")
	Call<ResponseBody> doctorLogin(@Query("username") String username, @Query("password") String password, @Query("token") String token);
	
	
	@GET("api/doctor/{doctorUID}/getPatientList")
	Call<ResponseBody> getPatientList(@Path("doctorUID") String doctorUID, @Query("limit") Integer limit, @Query("offset") Integer offset, @Query("fetchPatients") String fetchPatients);
	
	@GET("api/doctor/{doctorUID}/queryRecord")
	Call<ResponseBody> queryRecord(@Path("doctorUID") String doctorUID, @Query("studyInstanceUID") String studyInstanceUID, @Query("seriesInstanceUID") String seriesInstanceUID, @Query("sopInstanceUID") String sopInstanceUID);

	@GET("api/doctor/{doctorUID}/creatPatientWithNo")
	Call<ResponseBody> creatPatientWithNo(@Path("doctorUID") String doctorUID, @Query("name") String name, @Query("sex") String sex, @Query("age") String patientAge, @Query("patientID") String patientID, @Query("username") String username);

	@GET("api/doctor/{doctorUID}/addPatientStudy")
	Call<ResponseBody> addPatientStudy(@Path("doctorUID") String doctorUID, @Query("userUID") Long userUID, 
			@Query("studyInstanceUID") String studyInstanceUID, @Query("patientName") String patientName,
			@Query("patientUID") String patientUID, @Query("StudyDate") String StudyDate,
			@Query("StudyTime") String StudyTime, @Query("ModalitiesInStudy") String ModalitiesInStudy,
			@Query("InstitutionName") String InstitutionName, @Query("StudyDescription") String StudyDescription);
	
	@GET("api/doctor/{doctorUID}/addPatientSeries")
	Call<ResponseBody> addPatientSeries(@Path("doctorUID") String doctorUID,@Query("userUID") Long userUID, 
			@Query("SeriesInstanceUID") String SeriesInstanceUID, @Query("StudyInstanceUID") String StudyInstanceUID,
			@Query("SeriesNumber") String SeriesNumber, @Query("SeriesDate") String SeriesDate,
			@Query("SeriesTime") String SeriesTime, @Query("SeriesDescription") String SeriesDescription,
			@Query("Modality") String Modality, @Query("BodyPartExamined") String BodyPartExamined,
			@Query("AcquisitionNumber") String AcquisitionNumber);
	
	@GET("api/doctor/{doctorUID}/addPatientImage")
	Call<ResponseBody> addPatientImage(@Path("doctorUID") String doctorUID,@Query("sopInstanceUID") String sopInstanceUID,
			@Query("filePath") String filePath, @Query("seriesInstanceUID") String seriesInstanceUID,
			@Query("InsertTimestamp") String serialNumber, @Query("SpaceLocation") Double spaceLocation);
	
	@GET("api/doctor/{doctorUID}/addClinicalRecord")
	Call<ResponseBody> addClinicalRecord(@Path("doctorUID") String doctorUID,@Query("userUID") Long userUID,
			@Query("serialNumber") String serialNumber, @Query("institutionId") String institutionId);
	
	@GET("api/doctor/{doctorUID}/getClinicalRecord")
	Call<ResponseBody> getClinicalRecord(@Path("doctorUID") String doctorUID, @Query("serialNumber") String serialNumber,
			@Query("institutionId") String institutionId);
}
