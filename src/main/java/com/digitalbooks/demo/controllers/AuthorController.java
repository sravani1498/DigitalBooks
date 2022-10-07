
package com.digitalbooks.demo.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.exception.InternalServerErrorException;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.model.BookResponseModel;
import com.digitalbooks.demo.model.MessageResponse;
import com.digitalbooks.demo.services.AuthorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/digitalbooks/author")
public class AuthorController{
	
	@Autowired
	AuthorService authorService;
	
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PostMapping("/{authorId}/books")
	public ResponseEntity<?> createBook(@PathVariable("authorId") Long authorId, @Valid @RequestBody BookModel bookModel) throws InternalServerErrorException, SQLException {
			BookResponseModel bookResponseModel = new BookResponseModel();
			Books book = authorService.createBook(authorId, bookModel);
			if(book != null) {
				bookResponseModel.setBookId(book.getBookId());
				bookResponseModel.setMessage("Book Created Successfully");
				return ResponseEntity.ok(bookResponseModel);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Book Creation failed"));
			}
	}
	
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@PutMapping("{authorId}/books/{bookId}")
	public ResponseEntity<?>  updateBook(@PathVariable("authorId") Long authorId, @PathVariable("bookId") Long bookId, @Valid @RequestBody BookModel bookModel) throws InternalServerErrorException, SQLException {
		return authorService.updateBook(authorId,bookId, bookModel);
	}	
	
	@PreAuthorize("hasRole('ROLE_AUTHOR')")
	@GetMapping("{authorId}/books")
	public List<BookModel>  listOfBooks(@PathVariable("authorId") Long authorId) {
		return authorService.listOfBooks(authorId);
	}
}
