package com.angular.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.angular.dao.UserDAO;
import com.angular.model.User;

@Repository("userDAO")
@Transactional
@EnableTransactionManagement
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	// How get initialize
	// At the time of creation of instance , you have pass sessionFactory

	public UserDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public List<User> list() {

		String hql = "from User";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		return query.list();

	}
	//If the valid id -> will return user domain object
	//If the invalid id -> will return null
	@Transactional
	public User get(String userId) {
		
		return (User) sessionFactory.getCurrentSession().get(User.class,userId);

	}

	// select * from user where id ='niit' and password='niit'
	//If the credentials are valid  -> will return user domain object
		//If the invalid credentials -> will return null
	@Transactional
	public User validate(String userId, String password) {
		String hql = "from User where userId ='" + userId + "'  and password='" + password + "'";

		Query query = sessionFactory.getCurrentSession().createQuery(hql);

		return (User) query.uniqueResult();

	}
	
	@Transactional
	public boolean save(User user) {

		try {
			sessionFactory.getCurrentSession().save(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		
	}
	@Transactional
	public boolean update(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	
	}

}
