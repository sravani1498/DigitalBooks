package com.digitalbboks.test.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.digitalbooks.demo.controllers.AuthorController;
import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.model.MessageResponse;
import com.digitalbooks.demo.services.AuthorService;

@RunWith(MockitoJUnitRunner.class)
public class AuthorControllerTest {
	
	@Mock
	AuthorService authorService;
	
	@InjectMocks
	AuthorController authorController;
	
	 @Test
	    public void createBookTest() throws Exception{
		 BookModel bookRequest = new BookModel();
	        bookRequest.setId(123L);
	        Books actual = new Books();
			actual.setCategory("comedy");
			actual.setTitle("book1");
			actual.setPrice(42.98);
			
			BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
	        Mockito.when(authorService.createBook(123L, bookRequest)).thenReturn(actual);
	        Assert.assertNotNull(authorController.createBook(123L, bookRequest));
	    }

	 @Test(expected = AssertionError.class)
	 public void createBook() {
		BookModel book = new BookModel();
		book.setCategory("comedy");
		book.setTitle("book1");
		book.setPrice(42.98);
		Long authorId = 1L;
		
		Books actual = new Books();
		actual.setCategory("comedy");
		actual.setTitle("book1");
		actual.setPrice(42.98);
		
		when(authorService.createBook(authorId, book)).thenReturn(actual);
		
		ResponseEntity<?> r = authorController.createBook(authorId, book);
		Assert.assertNull(r);
	}
	
	@Test
    public void UpdateBookDetailsTest() throws Exception{
		BookModel bookRequest = new BookModel();
	    bookRequest.setId(123L);
	    Books actual = new Books();
		actual.setCategory("comedy");
		actual.setTitle("book1");
		actual.setPrice(42.98);
		ResponseEntity<?> r =  ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Book Updated Successfully"));
        Mockito.when(authorService.updateBook(123L,123L,bookRequest)).thenReturn((ResponseEntity<MessageResponse>) r);
        Assert.assertNotNull(authorController.updateBook(123L,123L,bookRequest));
    }
}

