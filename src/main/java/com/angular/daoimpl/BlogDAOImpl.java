package com.angular.daoimpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.angular.dao.BlogDAO;
import com.angular.model.Blog;

@Repository("blogDAO")
public class BlogDAOImpl implements BlogDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public BlogDAOImpl(){
		
	}
	
	public BlogDAOImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	public List<Blog> list() {

        String hql="from Blog";
        
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        
		return query.list();
	}

	public Blog get(int blogId) {
		return (Blog)sessionFactory.getCurrentSession().get(Blog.class, blogId);
	}

	public boolean save(Blog blog) {
		try {
			sessionFactory.getCurrentSession().save(blog);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean update(Blog blog) {
		try {
			sessionFactory.getCurrentSession().update(blog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

}
