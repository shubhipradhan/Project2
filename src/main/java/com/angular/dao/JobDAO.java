package com.angular.dao;

import java.util.List;

import com.angular.model.Job;

public interface JobDAO {

	public boolean saveJob(Job job);
	public List<Job> getAllOpendJobs();
}
