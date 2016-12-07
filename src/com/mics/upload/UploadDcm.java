package com.mics.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import com.mics.conf.BaseConf;
import com.mics.httpRequest.BaseClass;
import com.mics.httpRequest.HttpRequest;

public class UploadDcm implements Runnable {
	private Attributes attributes = null;
	private File dcmFile = null;
	private HttpRequest httpRequest;
	private String filePath;

	public UploadDcm(Attributes attributes, File dcmFile) {
		this.attributes = attributes;
		this.dcmFile = dcmFile;
		this.httpRequest = new HttpRequest();
	}

	@Override
	public void run() {
		try {
			Map<String, Object> result = httpRequest.queryRecord(attributes.getString(Tag.StudyInstanceUID),
					attributes.getString(Tag.SeriesInstanceUID), attributes.getString(Tag.SOPInstanceUID));

			if ((boolean) result.get("ObjectExist") == true)
				return;

			if (BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)) == null) {
				creatPatient();
			}

			if (Double.valueOf((Double) result.get("studyInstanceUID")) != 1.0) {
				addStudy();
			}

			if (Double.valueOf((Double) result.get("seriesInstanceUID")) != 1.0) {
				addSeries();
			}

			addPatientImage();
			
			uploadDcm();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void creatPatient() throws NumberFormatException, IOException {
		Calendar calendar = Calendar.getInstance();
		String patientAge = attributes.getString(Tag.PatientAge);
		patientAge = patientAge.substring(0, patientAge.length() - 1);
		// String reg = "^(?<year>\\d{4}).?(?<month>\\d{2}).?(?<day>\\d{2})$";
		// Pattern p = Pattern.compile(reg);
		// Matcher m = p.matcher(PatientBirthDate);

		Map<String, Object> map = httpRequest.creatPatientWithNo(attributes.getString(Tag.PatientName),
				attributes.getString(Tag.PatientSex, "F").equals("F") ? "0" : "1", patientAge,
				attributes.getString(Tag.PatientID), BaseConf.hospitalNo + "_" + attributes.getString(Tag.PatientID));
		
		BaseClass.addStudyCache(attributes.getString(Tag.StudyInstanceUID), map.get("userUID"));
	}

	private void addStudy() throws IOException {
		Map<String, Object> map = httpRequest.addStudy(
				BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)),
				attributes.getString(Tag.StudyInstanceUID), attributes.getString(Tag.PatientName),
				attributes.getString(Tag.PatientID), attributes.getString(Tag.StudyDate),
				attributes.getString(Tag.StudyTime), attributes.getString(Tag.ModalitiesInStudy),
				attributes.getString(Tag.InstitutionName), attributes.getString(Tag.StudyDescription));
		addClinicalRecord();
	}

	private void addSeries() throws IOException {
		Map<String, Object> map = httpRequest.addSeries(
				BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)),
				attributes.getString(Tag.SeriesInstanceUID), attributes.getString(Tag.StudyInstanceUID),
				attributes.getString(Tag.SeriesNumber), attributes.getString(Tag.SeriesDate),
				attributes.getString(Tag.SeriesTime), attributes.getString(Tag.SeriesDescription),
				attributes.getString(Tag.Modality), attributes.getString(Tag.BodyPartExamined),
				attributes.getString(Tag.AcquisitionNumber));
	}

	private void addPatientImage() throws IOException {
		filePath = BaseConf.Dcm_PreFilePath
				+ BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)) + "/"
				+ attributes.getString(Tag.StudyInstanceUID) + "/" + attributes.getString(Tag.SeriesInstanceUID) + "/"
				+ attributes.getString(Tag.SOPInstanceUID);
		String spaceLocation = getSpaceLocation();
		Map<String, Object> map = httpRequest.addPatientImage(attributes.getString(Tag.SOPInstanceUID), filePath,
				attributes.getString(Tag.SeriesInstanceUID), attributes.getString(Tag.SeriesNumber), spaceLocation);
	}

	private void addClinicalRecord() throws IOException {
		Map<String, Object> map = httpRequest.addClinicalRecord(BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)),
				attributes.getString(Tag.SeriesNumber), BaseConf.hospitalNo);
	}
	
	private void uploadDcm() throws IOException{
		Map<String, Object> map = httpRequest.getImageStorePath(filePath);
		ArrayList imageStorePath = (ArrayList) map.get("ImageStorePath");
		System.out.println(imageStorePath.get(0));
		httpRequest.uploadDcm((String) imageStorePath.get(0), dcmFile);
	}


	private String getSpaceLocation() {
		try {
			// 计算病人向量上的投影
			String[] positionStrings = attributes.getString(Tag.ImagePositionPatient).split(",");
			String[] orientationStrings = attributes.getString(Tag.ImageOrientationPatient).split(",");
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
