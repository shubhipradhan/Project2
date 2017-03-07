package com.angular.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.angular.dao.FriendDAO;
import com.angular.model.Friend;

@Repository("friendDAO")
@Transactional
public class FriendDAOImpl implements FriendDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public FriendDAOImpl(SessionFactory sessionFactory) {
		try {
			this.sessionFactory = sessionFactory;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	private Integer getMaxId() {
		

		String hql = "select max(id) from Friend";
		Query query = sessionFactory.openSession().createQuery(hql);
		Integer maxID;
		try {
			maxID = (Integer) query.uniqueResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 100;
		}
		
		return maxID;

	}
	
	public List<Friend> getMyFriends(String userId) {
		String hql1="select friendId from Friend where userId='"+userId+"' and status='A'";
		String hql2="select userId from Friend where friendId='"+userId+"' and status='A'";
		//Query query =sessionFactory.openSession().createQuery(hql1).list();
		//Query query
		// return sessionFactory.openSession().createQuery(hql1).list().addAll(sessionFactory.openSession().createQuery(hql2).list());
	List<Friend> list1=sessionFactory.openSession().createQuery(hql1).list();
	List<Friend> list2=sessionFactory.openSession().createQuery(hql2).list();
	
	list1.addAll(list2);
	return list1;
	//	return null;
	}

	

	@Transactional
	public boolean save(Friend friend) {

		try {
			
			friend.setId(getMaxId() + 1);
			
			sessionFactory.getCurrentSession().save(friend);
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	@Transactional
	public boolean update(Friend friend) {

		try {
			
			sessionFactory.getCurrentSession().update(friend);
			
			return true;
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		}

	}
	public void delete(String userId, String friendId) {
		Friend friend = new Friend();
		friend.setFriendId(friendId);
		friend.setUserId(userId);
		sessionFactory.openSession().delete(friend);
	}

	public List<Friend> getNewFriendRequest(String friendId) {
	String hql="select userId from Friend where friendId="+"'"+friendId+"' and status='"+"N'";
	return sessionFactory.openSession().createQuery(hql).list();
		//return null;
	}

	public void setOnline(String friendId) {
		
		String hql = " UPDATE Friend	SET isOnline = 'Y' where friendID='" + friendId + "'";
	
		Query query = sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();
		
	}

	public void setOffline(String friendId) {

		String hql = " UPDATE Friend	SET isOnline = 'N' where friendID='" + friendId + "'";

		Query query = sessionFactory.openSession().createQuery(hql);
		query.executeUpdate();

	}
	@Transactional
	public Friend get(String userId, String friendId) {
		String hql = "from Friend where userID=" + "'" + userId + "' and friendID= '" + friendId + "'";

		Query query = sessionFactory.openSession().createQuery(hql);

		return (Friend) query.uniqueResult();

	}
	public List<Friend> getRequestsSendByMe(String userId) {
		String hql="from Friend where userId="+"'"+userId+"' and status='N'";
		
		return sessionFactory.openSession().createQuery(hql).list();
		//return null;
	}

	public Friend get(String friendId){
		String hql="from Friend userId="+"'"+friendId+"'and friendId='"+friendId+"'";
		return (Friend)sessionFactory.openSession().createQuery(hql).list();
	}
}
