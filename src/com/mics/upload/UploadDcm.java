package com.mics.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.dcm4che3.io.DicomInputStream.IncludeBulkData;
import org.dcm4che3.util.SafeClose;

import com.mics.conf.BaseConf;
import com.mics.httpRequest.BaseClass;
import com.mics.httpRequest.HttpRequest;

public class UploadDcm implements Runnable {
	private Attributes attributes = null;
	private File dcmFile = null;
	private HttpRequest httpRequest;
	private String filePath;
	private Logger LOG = Logger.getLogger(com.mics.upload.UploadDcm.class);
	private BaseConf baseConf = BaseConf.getInstance();
	public UploadDcm(Attributes attributes, File file) {
		this.attributes = attributes;
		this.dcmFile = file;
		this.httpRequest = new HttpRequest();
	}

	@Override
	public void run() {
		try {
			Map<String, Object> result = httpRequest.queryRecord(attributes.getString(Tag.StudyInstanceUID),
					attributes.getString(Tag.SeriesInstanceUID), attributes.getString(Tag.SOPInstanceUID));

			/*
			 * 若文件存在，则不上传
			 */
			if ((boolean) result.get("ObjectExist") == true)
				return;

			if (BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)) == null) {
				creatPatient();// 通过创建病人，获取userUID
			}

			if (Double.valueOf((Double) result.get("studyInstanceUID")) != 1.0) {
				addStudy();
				addClinicalRecord();
				createOrderByDoctor();
			}

			if (Double.valueOf((Double) result.get("seriesInstanceUID")) != 1.0) {
				addSeries();
			}

			addPatientImage();

			uploadDcm();

			// System.gc();
		} catch (Exception e) {
			e.printStackTrace();
			/*
			 * 错误处理.....
			 * 
			 */
			LOG.error("File : " + dcmFile.getPath());
			
			System.out.println(attributes.getString(Tag.StudyInstanceUID));
		}
	}

	private void creatPatient() throws Exception {
		Calendar calendar = Calendar.getInstance();
		String patientAge = null;
		if (null != attributes.getString(Tag.PatientAge)) {
			patientAge = attributes.getString(Tag.PatientAge);
			patientAge = attributes.getString(Tag.PatientAge).substring(0, patientAge.length() - 1);
		}
		// String reg = "^(?<year>\\d{4}).?(?<month>\\d{2}).?(?<day>\\d{2})$";
		// Pattern p = Pattern.compile(reg);
		// Matcher m = p.matcher(PatientBirthDate);

		Map<String, Object> map = httpRequest.creatPatientWithNo(attributes.getString(Tag.PatientName),
				attributes.getString(Tag.PatientSex, "F").equals("F") ? "0" : "1", patientAge,
				attributes.getString(Tag.PatientID), baseConf.getHospitalNo() + "_" + attributes.getString(Tag.PatientID));

		// 将userUID插入study缓存中
		BaseClass.addStudyCache(attributes.getString(Tag.StudyInstanceUID), map.get("userUID"));

		if ((Double) map.get("errorCode") != 0.0 & (Double) map.get("errorCode") != 4.0) {
			throw new Exception("creatPatient Error!");
		}
	}

	private void addStudy() throws Exception {
		Map<String, Object> map = httpRequest.addStudy(
				BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)),
				attributes.getString(Tag.StudyInstanceUID), attributes.getString(Tag.PatientName),
				attributes.getString(Tag.PatientID), attributes.getString(Tag.StudyDate),
				attributes.getString(Tag.StudyTime), attributes.getString(Tag.ModalitiesInStudy),
				attributes.getString(Tag.InstitutionName), attributes.getString(Tag.StudyDescription));

		if ((Double) map.get("errorCode") != 0.0) {
			throw new Exception("addStudy Error!");
		}
	}

	private void addSeries() throws Exception {
		Map<String, Object> map = httpRequest.addSeries(
				BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)),
				attributes.getString(Tag.SeriesInstanceUID), attributes.getString(Tag.StudyInstanceUID),
				attributes.getString(Tag.SeriesNumber), attributes.getString(Tag.SeriesDate),
				attributes.getString(Tag.SeriesTime), attributes.getString(Tag.SeriesDescription),
				attributes.getString(Tag.Modality), attributes.getString(Tag.BodyPartExamined),
				attributes.getString(Tag.AcquisitionNumber));

		if ((Double) map.get("errorCode") != 0.0) {
			throw new Exception("addSeries Error!");
		}
	}

	private void addPatientImage() throws Exception {
		filePath = baseConf.getDcm_PreFilePath() + BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID))
				+ "/" + attributes.getString(Tag.StudyInstanceUID) + "/" + attributes.getString(Tag.SeriesInstanceUID)
				+ "/" + attributes.getString(Tag.SOPInstanceUID);

		String spaceLocation = getSpaceLocation();
		Map<String, Object> map = httpRequest.addPatientImage(attributes.getString(Tag.SOPInstanceUID), filePath,
				attributes.getString(Tag.SeriesInstanceUID), attributes.getString(Tag.SeriesNumber), spaceLocation);

		if ((Double) map.get("errorCode") != 0.0 & (Double) map.get("errorCode") != 14.0) {
			throw new Exception("addPatientImage Error!");
		}
	}

	private void addClinicalRecord() throws Exception {
		Map<String, Object> map = httpRequest.addClinicalRecord(
				BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)),
				attributes.getString(Tag.SeriesNumber), baseConf.getHospitalNo());
		if ((Double) map.get("errorCode") != 0.0) {
			throw new Exception("addClinicalRecord Error!");
		}
	}

	private void createOrderByDoctor() throws Exception {
		Map<String, Object> map = httpRequest.createOrderByDoctor(baseConf.getHospitalNo(),
				attributes.getString(Tag.StudyInstanceUID),
				BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)));
		if ((Double) map.get("errorCode") != 0.0) {
			throw new Exception("createOrderByDoctor Error!");
		}
	}

	private void uploadDcm() throws IOException {
		filePath = BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)) + "/"
				+ attributes.getString(Tag.StudyInstanceUID) + "/" + attributes.getString(Tag.SeriesInstanceUID) + "/"
				+ attributes.getString(Tag.SOPInstanceUID);
		Map<String, Object> map = httpRequest.getImageStorePath(filePath);
		ArrayList imageStorePath = (ArrayList) map.get("ImageStorePath");
		httpRequest.uploadDcm((String) imageStorePath.get(0), dcmFile);
	}

	/*
	 * 计算DCM文件spacelocation
	 * 
	 */
	private String getSpaceLocation() {
		try {
			// 计算病人向量上的投影
			double[] positionStrings = attributes.getDoubles(Tag.ImagePositionPatient);
			double[] orientationStrings = attributes.getDoubles(Tag.ImageOrientationPatient);
			double[] positionMatrix = { 0, 0, 0 };
			double[] orientationMatrix = { 1, 0, 0, 0, 1, 0, 0, 0, 1 };

			for (int i = 0; i < positionStrings.length; ++i) {
				positionMatrix[i] = Double.valueOf(positionStrings[i]);
			}
			for (int i = 0; i < orientationStrings.length; ++i) {
				orientationMatrix[i] = Double.valueOf(orientationStrings[i]);
			}
			orientationMatrix[6] = orientationMatrix[1] * orientationMatrix[5]
					- orientationMatrix[2] * orientationMatrix[4];
			orientationMatrix[7] = orientationMatrix[2] * orientationMatrix[3]
					- orientationMatrix[0] * orientationMatrix[5];
			orientationMatrix[8] = orientationMatrix[0] * orientationMatrix[4]
					- orientationMatrix[1] * orientationMatrix[0];

			for (int i = 0; i < 3; ++i) {
				int x = 0 + 3 * i, y = 1 + 3 * i, z = 2 + 3 * i;
				double value = Math.sqrt(orientationMatrix[x] * orientationMatrix[x]
						+ orientationMatrix[y] * orientationMatrix[y] + orientationMatrix[z] * orientationMatrix[z]);
				if (value == 0) {
					value = 1.0f;
				}
				orientationMatrix[x] = orientationMatrix[x] / value;
				orientationMatrix[y] = orientationMatrix[y] / value;
				orientationMatrix[z] = orientationMatrix[z] / value;
			}

			double value = positionMatrix[0] * orientationMatrix[6] + positionMatrix[1] * orientationMatrix[7]
					+ positionMatrix[2] * orientationMatrix[8];
			return String.valueOf(value);
		} catch (Exception e) {
			return "0.0"; // set default value
		}
	}
}
