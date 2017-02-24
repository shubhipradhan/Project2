package com.angular.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="C_Forum")
@Component
public class ChatForum  extends BaseDomain {
	
	@Id
	private int forumId;
	
	private String userId;
	
	private String forum_topic;
	
	private Timestamp created_date;

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getForum_topic() {
		return forum_topic;
	}

	public void setForum_topic(String forum_topic) {
		this.forum_topic = forum_topic;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
	
	

}

