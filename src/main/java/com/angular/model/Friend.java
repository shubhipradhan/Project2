package com.angular.model;

import javax.persistence.Column;
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
	@Column(name="user_id")
	private String userId;
	@Column(name="friend_id")
	private String friendId;
	
	private String status;
	
	private char is_Online;

	//@Column(name="LAST_SEEN_TIME")
	//private 
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public char getIs_Online() {
		return is_Online;
	}

	public void setIs_Online(char is_Online) {
		this.is_Online = is_Online;
	}
	
	
	
	

}
