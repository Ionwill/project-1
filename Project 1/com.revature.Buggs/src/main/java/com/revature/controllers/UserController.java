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

import com.revature.dtos.UserDTO;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService us;
	private AuthService as;
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	public UserController(UserService us, AuthService as) {
		super();
		this.us = us;
		this.as = as;
	}

//SELLER AND BASIC USER METHODS FOR FINDING USERS INFO

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(
			@RequestHeader(value = "Authorization", required = true) String token) {
		if (token == null) {
			LOG.warn("Not authorized to search users.");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		MDC.put("requestId", UUID.randomUUID().toString());
		as.allVerify(token);
		LOG.info("users retrieved");
		return new ResponseEntity<>(us.getUsers(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {

		// this just checks if the token is null, not if it has the right value
		if (token == null) {
			LOG.warn("Not authorized to search users.");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

		MDC.put("userToken", token);
		as.allVerify(token);
		UserDTO u = us.getUserById(id);
		MDC.clear();
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@PostMapping
	public String createUser() {
		return ("Unautherized to create new user");
	}

	@PutMapping("/{id}")
	public String updateUser(){
		return("Unauthorized to update users.");
	}
	
	@DeleteMapping
		public String DeleteById() {
			return("Unauthorized to delete users");
	}
}


