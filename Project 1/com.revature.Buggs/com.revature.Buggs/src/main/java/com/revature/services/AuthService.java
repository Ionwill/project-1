package com.revature.services;

import org.jboss.logging.MDC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.UserDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.GlobalExceptionHandler;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class AuthService {
	
	private UserRepository ur;
	private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);
	private static final User user = new User();
	
	@Autowired
	public AuthService(UserRepository ur) {
		super();
		this.ur = ur;
	}
	
	public String login(String username, String password) throws AuthenticationException{
		User user = ur.findUserByUsername(username);
		if(user == null || !user.getPassword().equals(password)) {
			LOG.warn("Unknown attempted login");
			throw new AuthenticationException("Attempted to login with username: " + username);
		}
		LOG.info("User " + user.getUsername() + "'s credentials validated.");
		return user.getId()+":"+user.getRole().toString();
	}
	
	
	public void adVerify(String token)  throws AuthenticationException{

		if(token == null) {
			throw new AuthorizationException("null token");
		}
		String[] splitToken = token.split(":");
		User principal = ur.findById(Integer.valueOf(splitToken[0])).orElse(null);
		// Authentication
		if(principal == null || !principal.getRole().toString().equals(splitToken[1]) || !principal.getRole().toString().equals("ADMIN")) {
			throw new AuthorizationException("Unable to verify token of value: " + splitToken[0] + ", " + splitToken[1]);
		} 
		LOG.info("token verified successfully");
		// could log a user id
		MDC.put("userId", principal.getId());
	}

	
	
	public void allVerify(String token) throws AuthenticationException{

		if(token == null) {
			throw new AuthorizationException("null token");
		}
		String[] splitToken = token.split(":");
		
		User principal = ur.findById(Integer.valueOf(splitToken[0])).orElse(null);
		// Authentication
		if(principal == null ) {
			throw new AuthorizationException("Unable to verify token of value: " + splitToken[0] + ", " + splitToken[1]);
		} 
		LOG.info("token verified successfully");
		// could log a user id
		MDC.put("userId", principal.getId());
	}

	
	

//	public String generateToken(UserDTO principal) {
//		
//		return principal.getId()+":"+principal.getUsername();
//	}
//
}
