package com.mics.upload;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import com.mics.conf.BaseConf;
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
			System.out.println(result.isEmpty());
			System.out.println(result.size());
			System.out.println(result.get("errorCode"));
			if(result.get("StudyInstanceUID") == null){
				creatPatient();
			}else if(result.get("seriesInstanceUID") == null){
				
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
		
		httpRequest.creatPatientWithNo(attributes.getString(Tag.PatientName),
				attributes.getString(Tag.PatientSex, "F").equals("F") ? "0" : "1", 
				patientAge, 
				attributes.getString(Tag.PatientID), 
				BaseConf.hospitalNo + "_" + attributes.getString(Tag.PatientID));
	}	
	
	private void addStudy(){
		
	}

}
