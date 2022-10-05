package com.digitalbboks.test.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.digitalbooks.demo.controllers.AuthController;
import com.digitalbooks.demo.entity.ERole;
import com.digitalbooks.demo.entity.Role;
import com.digitalbooks.demo.model.LoginRequest;
import com.digitalbooks.demo.model.SignupRequest;
import com.digitalbooks.demo.repository.RoleRepository;
import com.digitalbooks.demo.repository.UserRepository;
import com.digitalbooks.demo.security.jwt.JwtUtils;
import com.digitalbooks.demo.security.services.UserDetailsImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TestAuthController {

    @InjectMocks
    AuthController authController;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepository roleRepository;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    Authentication authentication;

    @Test
    public void authenticateUserTest() {
        LoginRequest request = new LoginRequest();
        request.setUsername("sravani");
        request.setPassword("pass1234");
        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority grantedAuthority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_AUTHOR";
            }
        };
        authorities.add(grantedAuthority);
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "sravani", "sravani@g.com", "schjdcnc", authorities);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
        Mockito.when(jwtUtils.generateJwtToken(Mockito.any())).thenReturn("sfhecndfheu765");
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        ResponseEntity<?> responseEntity = authController.authenticateUser(request);
        Assert.assertNotNull(responseEntity);
    }

    @Test(expected = RuntimeException.class)
    public void registerReaderTest() {
        SignupRequest signupRequest = new SignupRequest();
        Set<String> strRoles = new HashSet<>();
        strRoles.add("user");
        signupRequest.setEmail("sravani@gmail.com");
        signupRequest.setUsername("sravani");
        signupRequest.setPassword("pass1234");
        signupRequest.setRole(strRoles);
        Role role = new Role();
        role.setName(ERole.ROLE_READER);
        role.setId(1);
        Optional<Role> readerRole = Optional.of(role);
        Mockito.when(userRepository.existsByEmail("sravani@gmail.com")).thenReturn(false);
        Mockito.when(userRepository.existsByUsername("sravani")).thenReturn(false);
        Mockito.when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("nsbcuyscsjchs");
        Mockito.when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
        Assert.assertNotNull(responseEntity);
    }

    @Test(expected = RuntimeException.class)
    public void registerUserExceptionTest() {
        SignupRequest signupRequest = new SignupRequest();
        Set<String> strRoles = new HashSet<>();
        strRoles.add("ROLE_AUTHOR");
        signupRequest.setEmail("sravani@gmail.com");
        signupRequest.setUsername("sravani");
        signupRequest.setPassword("pass123");
        signupRequest.setRole(strRoles);
        Mockito.when(userRepository.existsByEmail("sravani@gmail.com")).thenReturn(false);
        Mockito.when(userRepository.existsByUsername("sravani")).thenReturn(false);
        Mockito.when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("sfhjhfscjhfec");
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
        Assert.assertNotNull(responseEntity);
    }

    @Test(expected = NullPointerException.class)
    public void registerAuthorTest() {
        SignupRequest signupRequest = new SignupRequest();
        Set<String> strRoles = new HashSet<>();
        strRoles.add("ROLE_AUTHOR");
        signupRequest.setEmail("sravani@gmail.com");
        signupRequest.setUsername("sravani");
        signupRequest.setPassword("pass123");
        signupRequest.setRole(strRoles);
        Role role = new Role();
        role.setName(ERole.ROLE_READER);
        role.setId(1);
        Optional<Role> authorRole = Optional.of(role);
        Mockito.when(userRepository.existsByEmail("sravani@gmail.com")).thenReturn(false);
        Mockito.when(userRepository.existsByUsername("sravani")).thenReturn(false);
        Mockito.when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("sdhsfsnfsjhc");
        Mockito.when(roleRepository.findByName(ERole.ROLE_AUTHOR)).thenReturn(authorRole);
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
        Assert.assertNotNull(responseEntity);
    }

    @Test(expected = NullPointerException.class)
    public void registerU5ser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("sravani@gmail.com");
        signupRequest.setUsername("sravani");
        signupRequest.setPassword("pass1234");
        Role role = new Role();
        role.setName(ERole.ROLE_READER);
        role.setId(1);
        Optional<Role> readerRole = Optional.of(role);
        Mockito.when(userRepository.existsByEmail("sravani@gmail.com")).thenReturn(false);
        Mockito.when(userRepository.existsByUsername("sravani")).thenReturn(false);
        Mockito.when(passwordEncoder.encode(signupRequest.getPassword())).thenReturn("shjsdscnscns");
        Mockito.when(roleRepository.findByName(ERole.ROLE_READER)).thenReturn(readerRole);
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
        Assert.assertNotNull(responseEntity);
    }

    @Test
    public void regiUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("sravani@gmail.com");
        signupRequest.setUsername("sravani");
        signupRequest.setPassword("pass123");
        Mockito.when(userRepository.existsByUsername("sravani")).thenReturn(true);
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
        Assert.assertNotNull(responseEntity);
    }

    @Test
    public void regifUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("sravani@gmail.com");
        signupRequest.setUsername("sravani");
        signupRequest.setPassword("pass123");
        Mockito.when(userRepository.existsByEmail("sravani@gmail.com")).thenReturn(true);
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
        Assert.assertNotNull(responseEntity);
    }
}