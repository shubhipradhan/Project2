package com.angular.dao;

import java.util.List;

import com.angular.model.Blog;
import com.angular.model.User;


public interface BlogDAO {

	public List<Blog> list();

	public Blog get(int blogId);
	
	public boolean save(Blog blog);

	public boolean update(Blog blog);
	
}
