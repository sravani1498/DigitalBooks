
package com.digitalbooks.demo.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.demo.models.Author;
import com.digitalbooks.demo.models.Books;
import com.digitalbooks.demo.models.ERole;
import com.digitalbooks.demo.models.Role;
import com.digitalbooks.demo.payload.request.CreateBookRequest;
import com.digitalbooks.demo.payload.request.LoginRequest;
import com.digitalbooks.demo.payload.request.SignupRequest;
import com.digitalbooks.demo.payload.response.JwtResponse;
import com.digitalbooks.demo.payload.response.MessageResponse;
import com.digitalbooks.demo.repository.AuthorRepository;
import com.digitalbooks.demo.repository.BookRepository;
import com.digitalbooks.demo.repository.RoleRepository;
import com.digitalbooks.demo.security.jwt.JwtUtils;
import com.digitalbooks.demo.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthorController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BookRepository bookRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (authorRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (authorRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		Author user = new Author(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 signUpRequest.getName(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_AUTHOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		authorRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
	@PostMapping("/createBook")
	public ResponseEntity<?> createBook(@RequestParam String token, @Valid @RequestBody CreateBookRequest createBookRequest) {
		System.out.println(token);
		if(jwtUtils.validateJwtToken(token)) {
			String authorUserName = jwtUtils.getUserNameFromJwtToken(token);
			Books book = new Books(createBookRequest.getTitle(), createBookRequest.getCategory(), authorUserName,
					createBookRequest.getPublisher(), createBookRequest.getContent(), createBookRequest.getLogo(), createBookRequest.getPrice(), createBookRequest.getStatus());
			bookRepository.save(book);
			return ResponseEntity.ok(new MessageResponse("Book published successfully!"));
		} else {
			 return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		
	}
	
}
