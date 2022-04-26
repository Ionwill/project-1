package com.revature.controllers;

import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthService as;
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	
	@Autowired
	public AuthController(AuthService as) {
		super();
		this.as = as;
	}
	
	@PostMapping
	public ResponseEntity<String> login(@RequestParam(name="username")String username, @RequestParam(name="password")String password){

		
		MDC.put("requestId", UUID.randomUUID().toString());
		LOG.debug("starting login");

		String token = as.login(username, password);


		HttpHeaders hh = new HttpHeaders();
		
		hh.set("Authorization", token);
		
		LOG.debug("Login occurred successfully");
		LOG.info("Login successful");

		return new ResponseEntity<>("Login successful.", hh, HttpStatus.OK);
	}
	
}
