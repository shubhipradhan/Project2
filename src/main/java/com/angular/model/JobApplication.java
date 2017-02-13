package com.angular.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="C_Job_Applied")
@Component
public class JobApplication extends BaseDomain  {

	@Id
	private int jobAppliedId;
	
	private String userId;
	
	private String jobId;
	
	private Date date_time;
	
	private char status;
	
	private String remarks;

	public int getJobAppliedId() {
		return jobAppliedId;
	}

	public void setJobAppliedId(int jobAppliedId) {
		this.jobAppliedId = jobAppliedId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
