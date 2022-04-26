package com.revature.controllers;

import java.util.List;
import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private UserService us;
	private AuthService as;
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public AdminController(UserService us, AuthService as) {
		super();
		this.us = us;
		this.as = as;
	}
	
	///ADMIN METHODS
	
		@GetMapping("/users")
		public ResponseEntity<List<User>> getAllUsers(@RequestHeader(value = "Authorization", required = true) String token){
			
			MDC.put("requestId", UUID.randomUUID().toString());
			as.adVerify(token);
			LOG.debug("Admin successfully retrieved users");
			LOG.info("Admin successfully retrieved users");
			return new ResponseEntity<>(us.getAllUsers(), HttpStatus.OK);
		}
		
		
		@GetMapping("/users/{id}")
		public ResponseEntity<User> getAllById(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {

			// this just checks if the token is null, not if it has the right value
			if (token == null) {
				LOG.debug("Unauthorized user tried to access users by id");
				LOG.warn("Unauthorized user tried to access users by id");
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			LOG.debug("Admin successfully retrieved users");
			LOG.info("Admin successfully retrieved users");
			MDC.put("userToken", token);
			as.adVerify(token);
			MDC.clear();
			return new ResponseEntity<>(us.getAllById(id), HttpStatus.OK);
		}
		
		
		@PostMapping("/users")
		public ResponseEntity<String> createUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
			if (token == null) {
				LOG.debug("Unauthorized user attempted to search all users");
				LOG.warn("Token is null");
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			LOG.debug("Admin successfully created a user");
			LOG.info("Admin successfully created a user");
			MDC.put("userToken", token);
			as.adVerify(token);
			User u = us.createUser(user);
			MDC.clear();
			return new ResponseEntity<>("User " + u.getUsername() + " has been created.", HttpStatus.CREATED);
		}
		
		
		@PutMapping("/users/{id}")
		public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") int id, @RequestHeader("Authorization") String token){
			if (token == null) {
				LOG.debug("Unauthorized user attempted to search a user by Id");
				LOG.warn("Token is null");
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			LOG.debug("Admin successfully updated a user by Id");
			LOG.info("Admin successfully updated user");
			MDC.put("userToken", token);
			as.adVerify(token);
			us.getUserById(id);
			MDC.clear();
			return new ResponseEntity<>(us.updateUser(id, user), HttpStatus.CREATED);
		}
		
		@DeleteMapping("/users/{id}")
		public ResponseEntity<String> DeleteById(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
			if (token == null) {
				LOG.debug("Unauthroized usser attempted to delete a user");
				LOG.warn("Token is null");
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
			LOG.debug("Admin successfully deleted user");
			LOG.info("Admin successfully deleted user");
			MDC.put("userToken", token);
			as.adVerify(token);
			us.deleteUserById(id);
			MDC.clear();
			return new ResponseEntity<>("User was deleted", HttpStatus.OK);
		}
}
