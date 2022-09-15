
package com.digitalbooks.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.User;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.services.AuthorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/digitalbooks/author")
public class AuthorController extends BaseController {
	
	@Autowired
	AuthorService authorService;
	
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PostMapping("/{authorId}/books")
	public ResponseEntity<?> createBook(@PathVariable("authorId") Long authorId, @Valid @RequestBody BookModel createBookRequest) {
		User user = new User();
		user.setUserId(authorId);
		Books book = new Books(createBookRequest.getTitle(), createBookRequest.getCategory(), 
					createBookRequest.getPublisher(), createBookRequest.getContent(), createBookRequest.getLogo(), createBookRequest.getPrice(), createBookRequest.getStatus(),user);
			return authorService.createBook(book);
	}
	
	@PreAuthorize("hasRole('AUTHOR')")
	@PutMapping("{authorId}/books/{bookId}")
	public ResponseEntity<?>  updateBook(@PathVariable("authorId") Long authorId, @PathVariable("bookId") String bookId, @Valid @RequestBody BookModel createBookRequest) {
		User user = new User();
		user.setUserId(authorId);
		Books book = new Books(createBookRequest.getTitle(), createBookRequest.getCategory(), 
					createBookRequest.getPublisher(), createBookRequest.getContent(), createBookRequest.getLogo(), createBookRequest.getPrice(), createBookRequest.getStatus(),user);
		return authorService.updateBook(authorId, book);
	}		
}
