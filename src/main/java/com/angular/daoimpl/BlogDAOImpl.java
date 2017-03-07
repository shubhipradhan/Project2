package com.angular.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.angular.dao.BlogDAO;
import com.angular.model.Blog;

@Repository("blogDAO")
@Transactional
public class BlogDAOImpl implements BlogDAO {

	@Autowired
	SessionFactory sessionFactory;

	public BlogDAOImpl(){
	
    }
	
	public BlogDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// sessionFactory.getCurrentSession - calling in each method
	// can write a private method which return the currentSession
	// i will call this private method instead of calling
	// sessionFactory.getCurrentSession

	public Blog getBlog(Integer id) {
		
		// sessionFactory.getCurrentSession().get(Blog.class,blogId);
		
		return (Blog) getSession().get(Blog.class, id);

	}

	public boolean save(Blog blog) {

		try {
			getSession().save(blog);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
/*
 * (non-Javadoc)
 * @see com.angular.dao.BlogDAO#getAllBlogs()
 * 
 * It will return all the approved Blogs
 */
	
	public List<Blog> getAllBlogs() {
		//String hql = "from Blog where status= 'A'";
		String hql = "from Blog ";

		Query query = getSession().createQuery(hql);
		return query.list();
	}
/*
 * (non-Javadoc)
 * @see com.angular.dao.BlogDAO#getAllBlogs(java.lang.String)
 * 
 * It will return all the Blogs written by a particular user
 * 
 */
	public List<Blog> getAllBlogs(String userId) {

		// select * from Blog where userId = 'shubhi'

		String hql = "from Blog where userId='" + userId + "'";

		Query query = getSession().createQuery(hql);

		return query.list();
	}

	public boolean update(Blog blog) {
		try {
			getSession().save(blog);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	/*
	 * public BlogDAOImpl(SessionFactory sessionFactory) { super();
	 * this.sessionFactory = sessionFactory; }
	 * 
	 * public List<Blog> list() {
	 * 
	 * String hql="from Blog";
	 * 
	 * Query query = sessionFactory.getCurrentSession().createQuery(hql);
	 * 
	 * return query.list(); }
	 * 
	 * public Blog get(int blogId) { return
	 * (Blog)sessionFactory.getCurrentSession().get(Blog.class, blogId); }
	 * 
	 * public boolean save(Blog blog) { try {
	 * sessionFactory.getCurrentSession().save(blog); } catch (Exception e) {
	 * e.printStackTrace(); return false; }
	 * 
	 * return true; }
	 * 
	 * public boolean update(Blog blog) { try {
	 * sessionFactory.getCurrentSession().update(blog); } catch (Exception e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); return false; }
	 * return true; }
	 */

}
