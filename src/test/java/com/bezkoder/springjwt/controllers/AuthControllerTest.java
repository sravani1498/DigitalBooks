package com.bezkoder.springjwt.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.digitalbooks.demo.controllers.AuthorController;
import com.digitalbooks.demo.entity.Role;
import com.digitalbooks.demo.model.SignupRequest;
import com.digitalbooks.demo.repository.UserRepository;
import com.digitalbooks.demo.repository.RoleRepository;


@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {
	
	@Mock
	UserRepository userRepository;
	@Mock
	PasswordEncoder encoder;
	@Mock
	RoleRepository roleRepository;
	@Mock
	Role role;
	@InjectMocks
	AuthorController authController;
	
//	@Test
//	public void testRegisterUser() {
//		SignupRequest signupRequest = new SignupRequest();
//		
//		signupRequest.setEmail("gewdn@gmail.com");
//		signupRequest.setPassword("pass@word1");
//		signupRequest.setUsername("fyuefencbuy");
//		
//		when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(true);
//		when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);
//
//		when(encoder.encode(signupRequest.getPassword())).thenReturn("dhwsxscsc");
//		
//		Set<String> strRoles = new HashSet<String>();
//		strRoles.add("ROLE_USER");
//		signupRequest.setRole(strRoles);
//		
//;
//		
//		 
//		assertEquals(responseEntity, authController.registerUser(signupRequest));		
//	}
	
//	@Test
//	public void testRegisterUser1() {
//		SignupRequest signupRequest = new SignupRequest();
//		
//		signupRequest.setEmail("gewdn@gmail.com");
//		signupRequest.setPassword("pass@word1");
//		signupRequest.setUsername("fyuefencbuy");
//		
//		when(userRepository.existsByUsername(signupRequest.getUsername())).thenReturn(false);
//		when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
//		
//		when(encoder.encode(signupRequest.getPassword())).thenReturn("dhwsxscsc");
//		
//		Set<String> strRoles = new HashSet<String>();
//		strRoles.add("ROLE_USER");
//		signupRequest.setRole(strRoles);
//		
//		ResponseEntity<?> r = new ResponseEntity("User registered successfully!" , HttpStatus.OK);
//		 
//		assertEquals(r, authController.registerUser(signupRequest));		
//	}


}
