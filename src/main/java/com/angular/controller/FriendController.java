package com.angular.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.angular.dao.FriendDAO;
import com.angular.dao.UserDAO;
import com.angular.model.Friend;

@RestController
public class FriendController {

	@Autowired
	FriendDAO friendDAO;

	@Autowired
	Friend friend;
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value = "/myFriends", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriends() {
		
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Friend> myFriends = new ArrayList<Friend>();
		if(loggedInUserID == null)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("Please login to know your friends");
			myFriends.add(friend);
			return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
			
		}
       
		
		 myFriends = friendDAO.getMyFriends(loggedInUserID);

		if (myFriends.isEmpty()) {
			
			friend.setErrorCode("404");
			friend.setErrorMessage("You does not have any friends");
			myFriends.add(friend);
		}
		
		return new ResponseEntity<List<Friend>>(myFriends, HttpStatus.OK);
	}

	@RequestMapping(value = "/addFriend/{friendId}", method = RequestMethod.GET)
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendId") String friendId) {
		
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		friend.setUserId(loggedInUserID);
		friend.setFriendId(friendId);
		friend.setStatus("N"); // N - New, R->Rejected, A->Accepted
		friend.setIs_Online('N');
		// Is the user already sent the request previous?
		
		//check whether the friend exist in user table or not
		if(isUserExist(friendId)==false)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("No user exist with the id :" + friendId);
		}
		
		else

		if (friendDAO.get(loggedInUserID, friendId) != null) {
			friend.setErrorCode("404");
			friend.setErrorMessage("You already sent the friend request to " + friendId);

		} else {
			friendDAO.save(friend);

			friend.setErrorCode("200");
			friend.setErrorMessage("Friend request successfull.." + friendId);
		}

		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}
	
	
	
	private boolean isUserExist(String id)
	{
		if(userDAO.get(id)==null)
			return false;
		else
			return true;
	}
	
	
	private boolean isFriendRequestAvailabe(String friendId)
	{
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		
		if(friendDAO.get(loggedInUserID,friendId)==null)
			return false;
		else
			return true;
	}

	@RequestMapping(value = "/unFriend/{friendId}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> unFriend(@PathVariable("friendId") String friendId) {
		
		updateRequest(friendId, "U");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	@RequestMapping(value = "/rejectFriend/{friendId}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> rejectFriendFriendRequest(@PathVariable("friendId") String friendId) {
		

		updateRequest(friendId, "R");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	@RequestMapping(value = "/accepttFriend/{friendId}", method = RequestMethod.PUT)
	public ResponseEntity<Friend> acceptFriendFriendRequest(@PathVariable("friendId") String friendId) {
		
        
		friend = updateRequest(friendId, "A");
		return new ResponseEntity<Friend>(friend, HttpStatus.OK);

	}

	private Friend updateRequest(String friendId, String status) {
		
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		
		
		if(isFriendRequestAvailabe(friendId)==false)
		{
			friend.setErrorCode("404");
			friend.setErrorMessage("The request does not exist.  So you can not update to "+status);
		}
		
		if (status.equals("A") || status.equals("R"))
			friend = friendDAO.get(friendId, loggedInUserID);
		else
			friend = friendDAO.get(loggedInUserID, friendId);
		friend.setStatus(status);// N - New, R->Rejected, A->Accepted

		friendDAO.update(friend);

		friend.setErrorCode("200");
		friend.setErrorMessage(
				"Request from   " + friend.getUserId() + " To " + friend.getFriendId() + " has updated to :" + status);
	
		return friend;

	}

	@RequestMapping(value = "/getMyFriendRequests/", method = RequestMethod.GET)
	public ResponseEntity<List<Friend>> getMyFriendRequests() {
		
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Friend> myFriendRequests = friendDAO.getNewFriendRequest(loggedInUserID);
		return new ResponseEntity<List<Friend>>(myFriendRequests, HttpStatus.OK);

	}
	
	
	@RequestMapping("/getRequestsSendByMe")
	public ResponseEntity<List<Friend>>  getRequestsSendByMe()
	{
		
		
		String loggedInUserID = (String) httpSession.getAttribute("loggedInUserID");
		List<Friend> requestSendByMe = friendDAO.getRequestsSendByMe(loggedInUserID);
		
		
		
		return new ResponseEntity<List<Friend>>(requestSendByMe, HttpStatus.OK);
		
	}
	
	
}
