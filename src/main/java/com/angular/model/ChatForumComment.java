package com.angular.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="C_Forum_Comment")
public class ChatForumComment extends BaseDomain {

	@Id
	private int forumCommentId;
	
	private String forumId;
	
	private String userId;
	
	private String forum_comment;
	
	private Date comment_date;

	public int getForumCommentId() {
		return forumCommentId;
	}

	public void setForumCommentId(int forumCommentId) {
		this.forumCommentId = forumCommentId;
	}

	public String getForumId() {
		return forumId;
	}

	public void setForumId(String forumId) {
		this.forumId = forumId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getForum_comment() {
		return forum_comment;
	}

	public void setForum_comment(String forum_comment) {
		this.forum_comment = forum_comment;
	}

	public Date getComment_date() {
		return comment_date;
	}

	public void setComment_date(Date comment_date) {
		this.comment_date = comment_date;
	}
	
	
}
