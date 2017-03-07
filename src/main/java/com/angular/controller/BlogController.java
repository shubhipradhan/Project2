
package com.angular.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.angular.dao.BlogDAO;
import com.angular.model.Blog;

@RestController
public class BlogController {

	@Autowired
	private Blog blog;
	
	@Autowired
	private BlogDAO blogDAO;
	
	@Autowired
	private HttpSession session;
	
	// POST --- > Create Object
	// PUT --> Update Object
	// GET --> Get Object
	// Delete --> Delete Object
	
	// @Request Mapping is will also work because default it is GETMAPPING 
	//but for POST mapping we have to give
	// Get Mapping automatically convert into JSON objects
	
	@GetMapping("/blogs")
	public List<Blog> getBlogs() {
		//logger.debug("calling method getBlogs");
		
		List blogs =   blogDAO.getAllBlogs();
		
		if(blogs==null)
			
		{
			blog = new Blog();
			
			blog.setErrorCode("404");
			blog.setErrorMessage("No blogs are available");
			blogs.add(blog);
			
		}
		return blogs;
		
		
		//How it is returning JSONArray without proper return type i.e., ResponseEntity<List<Blog>>
	}

	@GetMapping("/blog/{id}")
	public Blog getBlog(@PathVariable("id") int id) {
		//logger.debug("**************calling method getBlogs with the id " + id);
		Blog blog = blogDAO.getBlog(id);
		
		//logger.debug("date of creation of the blog from DB : " + blog.getDateTime());
		if(blog==null)
		{
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog not found with the id:" + id);
		}
		
		return blog;
		/*if (blog == null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Blog>(blog, HttpStatus.OK);*/
	}

	@PostMapping(value = "/blog")
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog, HttpSession session) {
		//logger.debug("calling method createBlog with id " +blog.getId());
		
		String loggedInUserID = (String) session.getAttribute("loggedInUserID");
		//logger.debug(" Blog is creating by the blog :"+loggedInUserID);
		blog.setUserId(loggedInUserID);
		blog.setStatus('N');// A->Accepted,  R->Rejected
		blog.setDate_time(new Date(System.currentTimeMillis()));
		
		//logger.debug("the current time is setting :" + blog.getDateTime());
		blogDAO.save(blog);

		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	

	@PutMapping("/blog/{id}")
	public ResponseEntity<Blog> updateBlog(@PathVariable int id, @RequestBody Blog blog) {
		//logger.debug("calling method updateBlog with the id " + id);
		
		if (blogDAO.getBlog(id)==null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		blogDAO.update(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}

	

	@RequestMapping(value = "/accept_blog/{id}", method = RequestMethod.GET)
	public ResponseEntity<Blog> accept(@PathVariable("id") int id) {
		
		blog = updateStatus(id, 'A', "");
		//logger.debug("Ending of the method accept");
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}

	@RequestMapping(value = "/reject_blog/{id}/{reason}", method = RequestMethod.GET)
	public ResponseEntity<Blog> reject(@PathVariable("id") int id, @PathVariable("reason") String reason) {
		
		blog = updateStatus(id, 'R', reason);
		
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);

	}

	private Blog updateStatus(int id, char status, String reason) {
		
		blog = blogDAO.getBlog(id);

		if (blog == null) {
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Could not update the status");
		} else {

			blog.setStatus(status);
			blog.setReason(reason);
			
			blogDAO.update(blog);
			
			blog.setErrorCode("200");
			blog.setErrorMessage("Updated the status successfully");
		}
		
		return blog;

	
	}

	
/*	@GetMapping("/blogs")
	public List<Blog> getAllBlogs()
	{
		
		
		return blogDAO.getAllBlogs();
	}
	
	@PutMapping("/approve/{blogId}")
	public Blog approveBlog(@PathVariable("blogId") Integer blogId){
		
		// get the blog based on blogId
		// If the Blog does not exist --> cannnot approve
		// If the Blog is already approve
		//set the status as "A"
		//call the update method
		
		// Check whether the user is logged or not
		
		// Check the user is admin or not
		if (session.getAttribute("loggedInUserID") == null)
		{
			blog.setErrorMessage("Please login to approve the blog");
			blog.setErrorCode("404");
			return blog;
		}
		
		if (! session.getAttribute("loggedInUserRole").equals("ROLE_ADMIN"))
		{
			blog.setErrorMessage("You are not authorized to approve the blog");
			blog.setErrorCode("404");
			return blog;
		}
		
	
		
		if(blog==null){
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog does not Exist");
		}
		if(blog.getStatus()==('A')){
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog is Already Approved");
		}
		
		blog.setStatus('A');
		
		if(blogDAO.update(blog)){
			blog.setErrorCode("200");
			blog.setErrorMessage("Successfully Approved");
		}
		else{
			blog.setErrorCode("404");
			blog.setErrorMessage("Not able to Approved the Blog");
		}
		
		
		return blog;
	}
	
	@PostMapping("/createBlog/")
	public Blog createBlog(@RequestBody Blog blog){
		
		// First User should login to create a Blog
		
		String loggedInUserId=(String)session.getAttribute("loggedInUserId");
		
		if(loggedInUserId==null){
			blog.setErrorCode("404");
			blog.setErrorMessage("You have to Login");
			return blog;
		}
System.out.println("value of 		blog.setUserId(loggedInUserId)"+loggedInUserId);

		blog.setStatus('N');
		blog.setUserId(loggedInUserId);
		
		blog.setDate_time(new Date());
		
		// If you have not given default sysdate then you have to write this
		
		if(blogDAO.save(blog)){
			blog.setErrorCode("200");
			blog.setErrorMessage("Successfully Created the blog");
			return blog;
		}else{
			blog.setErrorCode("404");
			blog.setErrorMessage("Not able to create a Blog");
			return blog;
		}
		
		
	}
	
	@PutMapping("/RejectBlog/{blogId}/{reason}")
	public Blog rejectBlog(@PathVariable("blogId") Integer blogId,@PathVariable("reason") String reason){
		
        String loggedInUserId=(String)session.getAttribute("loggedInUserId");
		
		if(loggedInUserId==null){
			blog.setErrorCode("404");
			blog.setErrorMessage("You have to Login");
			return blog;
		}
		
		if (! session.getAttribute("loggedInUserRole").equals("ROLE_ADMIN"))
		{
			blog.setErrorMessage("You are not authorized to approve the blog");
			blog.setErrorCode("404");
			return blog;
		}
		
		if(blog==null){
			blog = new Blog();
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog does not Exist");
		}
		if(blog.getStatus()=='A'){
			blog.setErrorCode("404");
			blog.setErrorMessage("Blog is Already Approved");
		}
		
		blog.setStatus('R');
		
		if(blogDAO.update(blog)){
			blog.setErrorCode("200");
			blog.setErrorMessage("Successfully Rejected");
		}
		else{
			blog.setErrorCode("404");
			blog.setErrorMessage("Not able to Approved the Blog");
		}
		
		
		return blog;
	
	}*/
	
}
