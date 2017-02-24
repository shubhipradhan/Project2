package com.angular.dao;

import java.util.List;

import com.angular.model.User;

public interface UserDAO {

	public List<User> list();

	public User get(String userId);

	public User validate(String id, String password);

	public boolean save(User user);

	public boolean update(User user);
	
}
