package com.mics.entity;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class ReportContentBack {
	public static final String TABLE_NAME = "ReportContent";
	
	private String Object_id;
	
	private String id;
	
	private Long patientUID;
	private String studyInstanceUID;
	private Date modifiedTime;
	private Date reviewedTime;
	private Long lockedTime;
	private Integer status;
	
	private String representation;
	private String reviewedResult;
	private String reportWriter;
	private String reportWriterUID;
	private String hospital;
	private String reviewer;
	private String examResult;
	private String partsResult;
	private String settingResult;
	
	public ReportContentBack() {
	}
	
	public ReportContentBack(String representation, String reviewedResult, String reportWriter, String reportWriterUID,
			String hospital, String reviewer) {
		this.representation = representation;
		this.reviewedResult = reviewedResult;
		this.reportWriter = reportWriter;
		this.reportWriterUID = reportWriterUID;
		this.hospital = hospital;
		this.reviewer = reviewer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Long getPatientUID() {
		return patientUID;
	}

	public void setPatientUID(Long patientUID) {
		patientUID = patientUID;
	}

	public String getStudyInstanceUID() {
		return studyInstanceUID;
	}

	public void setStudyInstanceUID(String studyInstanceUID) {
		this.studyInstanceUID = studyInstanceUID;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Date getReviewedTime() {
		return reviewedTime;
	}

	public void setReviewedTime(Date reviewedTime) {
		this.reviewedTime = reviewedTime;
	}

	public Long getLockedTime() {
		return lockedTime;
	}

	public void setLockedTime(Long lockedTime) {
		this.lockedTime = lockedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRepresentation() {
		return representation;
	}

	public void setRepresentation(String representation) {
		this.representation = representation;
	}

	public String getReviewedResult() {
		return reviewedResult;
	}

	public void setReviewedResult(String reviewedResult) {
		this.reviewedResult = reviewedResult;
	}

	public String getReportWriter() {
		return reportWriter;
	}

	public void setReportWriter(String reportWriter) {
		this.reportWriter = reportWriter;
	}

	public String getReportWriterUID() {
		return reportWriterUID;
	}

	public void setReportWriterUID(String reportWriterUID) {
		this.reportWriterUID = reportWriterUID;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getExamResult() {
		return examResult;
	}

	public void setExamResult(String examResult) {
		this.examResult = examResult;
	}

	public String getPartsResult() {
		return partsResult;
	}

	public void setPartsResult(String partsResult) {
		this.partsResult = partsResult;
	}

	public String getSettingResult() {
		return settingResult;
	}

	public void setSettingResult(String settingResult) {
		this.settingResult = settingResult;
	}

	public String getObject_id() {
		return Object_id;
	}

	public void setObject_id(String object_id) {
		Object_id = object_id;
	}

	@Override
	public String toString() {
		return "ReportContentBack [Object_id=" + Object_id + ", id=" + id + ", patientUID=" + patientUID
				+ ", studyInstanceUID=" + studyInstanceUID + ", modifiedTime=" + modifiedTime + ", reviewedTime="
				+ reviewedTime + ", lockedTime=" + lockedTime + ", status=" + status + ", representation="
				+ representation + ", reviewedResult=" + reviewedResult + ", reportWriter=" + reportWriter
				+ ", reportWriterUID=" + reportWriterUID + ", hospital=" + hospital + ", reviewer=" + reviewer
				+ ", examResult=" + examResult + ", partsResult=" + partsResult + ", settingResult=" + settingResult
				+ "]";
	}
	
}
