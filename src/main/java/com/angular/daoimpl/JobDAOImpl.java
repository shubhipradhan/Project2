package com.angular.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.angular.dao.JobDAO;
import com.angular.model.Job;

@Repository("jobDAO")
@Transactional
public class JobDAOImpl implements JobDAO {

	@Autowired
	SessionFactory sessionFactory;
	
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

}
