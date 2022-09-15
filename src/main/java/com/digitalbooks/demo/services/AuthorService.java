package com.digitalbooks.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.User;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.payload.response.MessageResponse;
import com.digitalbooks.demo.repository.BookRepository;

@Service
public class AuthorService {
	
	@Autowired
	BookRepository bookRepository;
	
	public ResponseEntity<MessageResponse> createBook(Long authorId, BookModel bookModel) {
		User user = new User();
		user.setUserId(authorId);
		Books book = new Books(bookModel.getTitle(), bookModel.getCategory(), 
				bookModel.getPublisher(), bookModel.getContent(), bookModel.getLogo(), bookModel.getPrice(), bookModel.getStatus(),user);
		try {
			bookRepository.save(book);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Book Created Successfully"));
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Please try after some time.."));
		}
		
	}
	
	public ResponseEntity<MessageResponse> updateBook(Long authorId, Long bookId,BookModel bookModel) {
		User user = new User();
		user.setUserId(authorId);
		Books book = new Books(bookModel.getTitle(), bookModel.getCategory(), 
				bookModel.getPublisher(), bookModel.getContent(), bookModel.getLogo(), bookModel.getPrice(), bookModel.getStatus(),user);
		try {
			book.setBookId(bookId);
			bookRepository.save(book);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Book Updated Successfully"));
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Please try after some time.."));
		}
		
	}

}
