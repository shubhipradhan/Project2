package com.angular.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.angular.dao.JobDAO;
import com.angular.model.Job;
import com.angular.model.JobApplication;

@Repository("jobDAO")
@Transactional
public class JobDAOImpl implements JobDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public JobDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	
	public List<Job> getAllOpendJobs() {
		String hql = "from Job";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		return query.list();
	}

	public boolean saveJob(Job job) {
		try {
			sessionFactory.getCurrentSession().save(job);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Transactional
	public Job getJobDetails(Long id) {
		return  (Job)sessionFactory.getCurrentSession().get(Job.class, id);
	
	}
	
	@Transactional
	public JobApplication getJobApplication(String userID, Long jobID) {
		
		
		String hql = "from JobApplication where userID ='"+ userID + "' and jobID='"+jobID + "'";
		
		return (JobApplication) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
		
	
	}
	
	@Transactional
	public JobApplication getJobApplication(Long jobID) {
		return (JobApplication) sessionFactory.getCurrentSession().get(JobApplication.class, jobID);
	
	}

	@Transactional
	public boolean updateJob(Job job) {
		try {
			sessionFactory.getCurrentSession().update(job);
			return true;
		} catch (HibernateException e) {
			
			e.printStackTrace();
			return false;
		}
		}
	
	@Transactional
	public boolean updateJob(JobApplication jobApplication) {
		try {
			sessionFactory.getCurrentSession().update(jobApplication);
			return true;
		} catch (HibernateException e) {
			
			e.printStackTrace();
			return false;
		}
		}
	
	private Long getMaxId()
	{
		
		Long maxID = 100L;
		try {
			String hql = "select max(id) from JobApplication";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			maxID =  (Long) query.uniqueResult();
		} catch (HibernateException e) {
			
			maxID = 100L;
			e.printStackTrace();
		}
		
		return maxID+1;

	}
	
	@Transactional
	public boolean save(Job job) {
		
		try {
			sessionFactory.getCurrentSession().save(job);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Transactional
	public List<Job> getMyAppliedJobs(String userID) {
		
		String hql = "from JobApplied where userID ='"+ userID +"'";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		
		return query.list();
		
	}

	
}
