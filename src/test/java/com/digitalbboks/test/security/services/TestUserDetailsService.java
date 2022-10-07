package com.digitalbboks.test.security.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.digitalbooks.demo.entity.User;
import com.digitalbooks.demo.repository.UserRepository;
import com.digitalbooks.demo.security.services.UserDetailsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TestUserDetailsService {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserDetailsServiceImpl service;
	
	@Test
	public void testloadUserByUsername() throws UsernameNotFoundException{
		Optional<User> user = Optional.ofNullable(new User());
		user.get().setUserId(123L);
		
		when(userRepository.findByUsername("sravani")).thenReturn(user);
		
		Assert.assertNotNull(service.loadUserByUsername("sravani"));
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testloadUserByUsernameNotFound() throws UsernameNotFoundException{
		Optional<User> user = Optional.ofNullable(new User());
		user.get().setUserId(123L);
		
		Assert.assertNull(service.loadUserByUsername(null));
	}


}
