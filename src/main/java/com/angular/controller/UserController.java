package com.angular.controller;


import java.util.List;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.angular.dao.UserDAO;
import com.angular.model.User;




@RestController
public class UserController {



	@Autowired
	UserDAO userDAO;
	
	@Autowired
	private User user;
	
	@Autowired
	private HttpSession session;
	
	public UserController(){
		System.out.println("<-------  USER CONTROLLER ------->");
	}
	
	@GetMapping("/home")
	public String homePage(){
		return "Hello Page ";
	}
	
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers(){

		List users = userDAO.list();
		if(users.isEmpty()){
			user.setErrorCode("100");
			user.setErrorMessage("Not User Available");
			users.add(user);
			
			return new ResponseEntity<List<User>>(users,HttpStatus.OK);
			
		}
		user.setErrorCode("200");
		user.setErrorMessage("Successfully Fetched User");
		
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@PostMapping(value="/createUser/")
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		if(userDAO.get(user.getUserId())==null){
			userDAO.save(user);
			user.setErrorCode("200");
			user.setErrorMessage("Successfully Registered");
		}else{
			user.setErrorCode("404");
			user.setErrorMessage("User exist with this Id --->"+user.getUserId());
		}
		
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("/validate/{id}/{password}")
	public ResponseEntity<User> validateCredentials(@PathVariable("id") String id , @PathVariable("password") String password){
		
		user = userDAO.validate(id, password);
		
	if(user==null){
		user = new User();
		user.setErrorCode("404");
		user.setErrorMessage("Invalid ");
	}else
	{
		user.setErrorCode("200");
		user.setErrorMessage("Login successfuly");
		
	}
	
	return new ResponseEntity<User>(user,HttpStatus.OK);
	
	}
	
	@PostMapping("/login/")
	public ResponseEntity<User> login(@RequestBody User user){
		user=userDAO.validate(user.getUserId(), user.getPassword());
		
		if(user==null){
			user=new User();
			user.setErrorCode("404");
			user.setErrorMessage("Please Login");
		}else{
			user.setErrorCode("200");
			user.setErrorMessage("Successfully Logged In");
			
			session.setAttribute("loggedInUserId", user.getUserId());
			session.setAttribute("loggedInUserRole", user.getRole());
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	
	@GetMapping("/logout")
	public User logout(){
		session.invalidate();
		
		user.setErrorCode("200");
		user.setErrorMessage("SuccessFully Logout");
		
		return user;
	}
	/*@GetMapping("/validate/{id}/{password}")
	public ResponseEntity<User> validateCredentials(@PathVariable("id") String id , @PathVariable("password") String password){
		
		user = userDAO.validate(id, password);
		
	if(user==null){
		user = new User();
		user.setErrorCode("404");
		user.setErrorMessage("Invalid ");
	}else
	{
		user.setErrorCode("200");
		user.setErrorMessage("Login successfuly");
		
	}
	
	return new ResponseEntity<User>(user,HttpStatus.OK);
	
	}*/
	
	// ye Sir wla hai
	
	/*@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		
		if (userDAO.get(user.getUserId()) == null) {
			
			user.setIs_Online('N');
			user.setStatus('N');
			  if (userDAO.save(user) ==true)
			  {
				  user.setErrorCode("200");
					user.setErrorMessage("Thank you  for registration. You have successfully registered as " + user.getRole());
			  }
			  else
			  {
				  user.setErrorCode("404");
					user.setErrorMessage("Could not complete the operatin please contact Admin");
		
				  
			  }
			
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		user.setErrorCode("404");
		user.setErrorMessage("User already exist with id : " + user.getUserId());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		
		if (userDAO.get(user.getUserId()) == null) {
			
			user = new User(); // ?
			user.setErrorCode("404");
			user.setErrorMessage("User does not exist with id " + user.getUserId());
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

		userDAO.update(user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	*/
	/*@PostMapping(value="/createUser/")
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		if(userDAO.get(user.getUserId())==null){
			userDAO.save(user);
			user.setErrorCode("200");
			user.setErrorMessage("Successfully Registered");
		}else{
			user.setErrorCode("404");
			user.setErrorMessage("User exist with this Id --->"+user.getUserId());
		}
		
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}*/
}
