package com.mics.upload;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;

import com.mics.http.imp.HttpRequest;

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
			
			if((double)result.get("StudyInstanceUID") == 0.0){
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
