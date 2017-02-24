package com.angular.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="C_Friend")
@Component
public class Friend extends BaseDomain  {
	
	@Id
	private int id;
	
	private String userId;
	
	private String friendId;
	
	private char status;
	
	private char is_Online;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public char getIs_Online() {
		return is_Online;
	}

	public void setIs_Online(char is_Online) {
		this.is_Online = is_Online;
	}
	
	
	
	

}
