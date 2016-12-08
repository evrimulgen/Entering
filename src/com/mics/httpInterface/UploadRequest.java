package com.mics.httpInterface;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UploadRequest {

	
//	@Headers({
////		"processData: false",
////		"contentType: false",
////		"Cookie: false"
//		"Host: {host}"
//	})
	@Multipart
	@PUT("{url}")
	Call<ResponseBody> uploadDcm(@Path("url") String url,
//			@Query("Expires") String Expires,
//			@Query("OSSAccessKeyId") String OSSAccessKeyId,
//			@Query("Signature") String Signature,
			@Part MultipartBody.Part file);
}
