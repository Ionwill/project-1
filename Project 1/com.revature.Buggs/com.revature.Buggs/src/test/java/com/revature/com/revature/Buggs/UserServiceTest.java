package com.revature.com.revature.Buggs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.exceptions.BuggNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.models.UserRole;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	static UserRepository ur;
	
	static UserService us;
	static UserRole urole;
	static List<User> users = new ArrayList<>();
	static User user;
	
	@BeforeAll
	public static void setup() {
		ur = mock(UserRepository.class);
		us = new UserService(ur);
		user = new User(1, "x", "x", urole);
		users.add(user);
	}

	@Test
	public static void getUsersTest() {
		when(ur.findAll()).thenReturn(users);
		assertEquals(users, us.getUsers());
	}
	
	@Test
	public void getUserByIdTest() {
		when(ur.findById(1)).thenReturn(Optional.of(user));
		assertEquals(user, us.getAllById(1));	
	}
	
	@Test
	public void createUserTest() {
		when(ur.save(user)).thenReturn(user);
		assertEquals(user, us.createUser(user));
	}
	
	@Test
	public void updateUserTest() throws UserNotFoundException{
		when(ur.findById(1)).thenReturn(Optional.of(user));
		when(ur.save(user)).thenReturn(user);
		assertEquals(user, us.updateUser(1, user));
	}
	
	@Test
	public void deleteUserByIdTest() {
		when(ur.getById(1)).thenReturn(user);
		assertEquals(us.deleteUserById(1),true);
	}

	
}

