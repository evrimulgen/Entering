package com.mics.upload;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import com.mics.conf.BaseConf;
import com.mics.httpRequest.BaseClass;
import com.mics.httpRequest.HttpRequest;

public class UploadDcm implements Runnable {
	private Attributes attributes = null;
	private File dcmFile = null;
	private HttpRequest httpRequest;

	public UploadDcm(Attributes attributes, File dcmFile) {
		this.attributes = attributes;
		this.dcmFile = dcmFile;
		this.httpRequest = new HttpRequest();
	}

	@Override
	public void run() {
		try {
			Map<String, Object> result = httpRequest.queryRecord(attributes.getString(Tag.StudyInstanceUID), 
					attributes.getString(Tag.SeriesInstanceUID), 
					attributes.getString(Tag.SOPInstanceUID));
			
			System.out.println(attributes.getString(Tag.SeriesInstanceUID));
			
			if((boolean)result.get("ObjectExist") != false)
				return;
			
			if(BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)) == null){
				creatPatient();
			}
				
			if(Double.valueOf((Double)result.get("studyInstanceUID")) != 1.0){
				addStudy();
			}
			System.out.println(Double.valueOf((Double)result.get("seriesInstanceUID")));
			if(Double.valueOf((Double)result.get("seriesInstanceUID")) != 1.0){
				addSeries();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void creatPatient() throws NumberFormatException, IOException{
		Calendar calendar = Calendar.getInstance();
		String patientAge = attributes.getString(Tag.PatientAge);
		patientAge = patientAge.substring(0, patientAge.length()-1);
//		String reg = "^(?<year>\\d{4}).?(?<month>\\d{2}).?(?<day>\\d{2})$";
//		Pattern p = Pattern.compile(reg);
//		Matcher m = p.matcher(PatientBirthDate);
		
		Map<String, Object> map = httpRequest.creatPatientWithNo(attributes.getString(Tag.PatientName),
				attributes.getString(Tag.PatientSex, "F").equals("F") ? "0" : "1", 
				patientAge, 
				attributes.getString(Tag.PatientID), 
				BaseConf.hospitalNo + "_" + attributes.getString(Tag.PatientID));
		BaseClass.addStudyCache(attributes.getString(Tag.StudyInstanceUID), map.get("userUID"));
	}	
	
	private void addStudy() throws IOException{
		Map<String, Object> map = httpRequest.addStudy(BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)), attributes.getString(Tag.StudyInstanceUID),
				attributes.getString(Tag.PatientName), attributes.getString(Tag.PatientID),
				attributes.getString(Tag.StudyDate), attributes.getString(Tag.StudyTime),
				attributes.getString(Tag.ModalitiesInStudy), attributes.getString(Tag.InstitutionName),
				attributes.getString(Tag.StudyDescription));
	}
	
	private void addSeries() throws IOException{
		Map<String, Object> map = httpRequest.addSeries(BaseClass.getStudyCache().get(attributes.getString(Tag.StudyInstanceUID)), attributes.getString(Tag.SeriesInstanceUID),
				attributes.getString(Tag.StudyInstanceUID), attributes.getString(Tag.SeriesNumber),
				attributes.getString(Tag.SeriesDate), attributes.getString(Tag.SeriesTime),
				attributes.getString(Tag.SeriesDescription), attributes.getString(Tag.Modality),
				attributes.getString(Tag.BodyPartExamined), attributes.getString(Tag.AcquisitionNumber));
	}

	private void addClinicalRecord(){
		
	}
	
	private void getClinicalRecord(){
		
	}
	
	
}
