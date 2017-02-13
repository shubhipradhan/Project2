package com.angular.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.angular.dao.UserDAO;
import com.angular.model.User;

@RestController
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	private User user;
	

	@GetMapping("/home")
	public String homePage(){
		return "Hello Page ";
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
	
	@PostMapping(value="/createUser/",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		if(userDAO.get(user.getUserId())== null)
		{
			userDAO.save(user);
			user.setErrorCode("200");
			user.setErrorMessage("Successfully Registered");
		}else
		
		{
			user.setErrorCode("404");
			user.setErrorMessage("User exist with this Id"+user.getUserId());
		}
		/*if(userDAO.get(user.getUserId()==null){
			userDAO.save(user);
			user.setErrorCode("200");
			user.setErrorMessage("Successfully Registered");
		}
		else{
			
			user.setErrorCode("404");
			user.setErrorMessage("User exist with this Id"+user.getUserId());
			
		}*/
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}
