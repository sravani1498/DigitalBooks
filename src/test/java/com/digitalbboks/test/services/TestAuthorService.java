package com.digitalbboks.test.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.User;
import com.digitalbooks.demo.exception.InternalServerErrorException;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.model.MessageResponse;
import com.digitalbooks.demo.repository.BookRepository;
import com.digitalbooks.demo.services.AuthorService;

@RunWith(MockitoJUnitRunner.class)
public class TestAuthorService {
	
	@Mock
	BookRepository bookRepository;
	
	@InjectMocks
	AuthorService authorService;
	
	@Test
    public void createBook() throws Exception {
        User user = new User();
        user.setUserId(123L);
        Books actual = new Books();
        actual.setCategory("comedy");
		actual.setTitle("book1");
		actual.setPrice(42.98);
		
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(actual);
        BookModel bookRequest = new BookModel();
        bookRequest.setId(123L);
		BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
        Books b = authorService.createBook(123L, bookRequest);
        Assert.assertNotNull(b);
    }
	
	@Test(expected = InternalServerErrorException.class)
    public void createBookException() throws InternalServerErrorException, SQLException{
        User user = new User();
        user.setUserId(123L);
        Books actual = new Books();
        actual.setCategory("comedy");
		actual.setTitle("book1");
		actual.setPrice(42.98);
		
        BookModel bookRequest = new BookModel();
        bookRequest.setId(123L);
		BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
        Mockito.doThrow(InternalServerErrorException.class).when(authorService.createBook(123L, bookRequest));
    }

	@Test(expected = InternalServerErrorException.class)
    public void createBookExceptionTest() throws Exception {
        User user = new User();
        user.setUserId(123L);
        BookModel bookRequest = new BookModel();
        bookRequest.setId(123L);
        Mockito.doThrow(InternalServerErrorException.class).when(authorService.createBook(123L, bookRequest));
    }
    
    @Test
    public void updateBookDetails() throws Exception {
        User user = new User();
        user.setUserId(123L);
        Books actual = new Books();
        actual.setCategory("comedy");
		actual.setTitle("book1");
		actual.setPrice(42.98);
		
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(actual);
        BookModel bookRequest = new BookModel();
        bookRequest.setId(123L);
		BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
        ResponseEntity<MessageResponse> bookResponse1 = authorService.updateBook(123L, 123L, bookRequest);
        Assert.assertNotNull(bookResponse1);
    }

    @Test
    public void updateBookExceptionTest() throws Exception {
        User user = new User();
        user.setUserId(123L);
        BookModel bookRequest = new BookModel();
        bookRequest.setId(123L);
        Assert.assertNotNull(authorService.updateBook(123L,123L,bookRequest));
    }
    
    @Test(expected = AssertionError.class)
    public void updateBookExceptionTest1() throws Exception {
        User user = new User();
        user.setUserId(123L);
        BookModel bookRequest = new BookModel();
        bookRequest.setId(123L);
        Assert.assertNull(authorService.updateBook(123L,123L,bookRequest));
    }
    
    @Test
    public void testListOfBooks() {
		List<BookModel> bookModelList = new ArrayList<BookModel>();
		List<Books> booksList = new ArrayList<Books>();
		Books book = new Books();
		book.setBookId(123L);
		booksList.add(book);
		when(bookRepository.findByUserUserId(123L)).thenReturn(booksList);
		BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
		bookModelList.add(bookResponse);
		List<BookModel> expected = authorService.listOfBooks(123L);
		assertNotNull(expected);
		
    }


}
