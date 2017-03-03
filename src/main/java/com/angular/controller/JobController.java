package com.angular.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.angular.dao.JobDAO;
import com.angular.model.Job;
import com.angular.model.JobApplication;

@RestController
public class JobController {

	@Autowired
	Job job;
	
	@Autowired
	JobDAO jobDAO;
	
	@Autowired 
	HttpSession httpSession;
	
	@Autowired
	JobApplication jobApplication;
	
	@RequestMapping(value="/getAllJobs/")
	public ResponseEntity<List<Job>> getAllOpendJobs(){
		
		List<Job> jobs=jobDAO.getAllOpendJobs();
		
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}
	
	@RequestMapping(value="/postAJob",method=RequestMethod.POST)
	public ResponseEntity<Job> postaJob(@RequestBody Job job){
		job.setStatus('V');
		Date date=new Date();
		job.setDate_time(date);
		
		if(jobDAO.saveJob(job)==false){
			job.setErrorCode("404");
			job.setErrorMessage("Job cannot be save");
			
		}else
		{
			job.setErrorCode("200");
			job.setErrorMessage("JOb post save successfully ");
			
		}
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
	
}
