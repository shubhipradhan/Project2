package com.angular.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;



@Entity
@Table(name="C_Blog")
@Component
public class Blog extends BaseDomain {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@GeneratedValue-17/2/17
	@Column(name="ID")
	private Integer id;
	
	private String title;
	
	private String userId;
	
	
	//default sysdate in DB or in java new Date()
	
	private Date date_time;
	
	
	// A--> Approved
	// R--> Reject
	// N--> New
	// By default it should be new
	
	private char status;
	
	private String reason;
	
	//Character Oriented Large Object
	private String description;




	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	
	
}
