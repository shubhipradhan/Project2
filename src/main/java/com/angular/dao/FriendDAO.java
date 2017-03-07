package com.angular.dao;

import java.util.List;

import com.angular.model.Friend;

public interface FriendDAO {

	// select * from Friend where userId =? and status ='A'
	public List<Friend> getMyFriends(String userId);
	
	public Friend get(String userId,String friendId);
	
	// if u want to get all the details of your friend
	// you can get(userId) of the userDAO interface
	
	public boolean save(Friend friend);
	
	public boolean update(Friend friend);
	
	public void delete(String userId,String friendId);
	
	public Friend get(String friendId);
	// select * from Friend where friendId=? and status='N'
	
	public List<Friend> getNewFriendRequest(String friendId);
	
	public void setOnline(String friendId);
	
	public void setOffline(String friendId);
	
	// select * from Friend where userId=? status ='N'
	public List<Friend> getRequestsSendByMe(String userId);
	
}
