package com.digitalbboks.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.Payment;
import com.digitalbooks.demo.entity.User;
import com.digitalbooks.demo.exception.ResourceNotFoundException;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.model.MessageResponse;
import com.digitalbooks.demo.model.PaymentRequest;
import com.digitalbooks.demo.repository.BookRepository;
import com.digitalbooks.demo.repository.PaymentRepository;
import com.digitalbooks.demo.services.ReaderService;

@RunWith(MockitoJUnitRunner.class)
public class ReaderServiceTest {
	@Mock
	BookRepository bookRepo;
	
	@Mock
	PaymentRepository paymentRepo;
	
	@Mock
	MessageResponse messageResponse;
	
	@InjectMocks
	ReaderService readerService;
	
	@Test
	public void search() {
		String title = "book1";
		String category = "comedy";
		double price = 230;
		String publisher = "xyz publications";
		User user = new User();
		user.setUserId(123L);
		Books book = new Books("title","category","publisher","content","logo",120.0,true,user);
		book.setBookId(123L);
		
		List<Books> bookList = new ArrayList<Books>();
		bookList.add(book);
		Optional<List<Books>> booksList = Optional.ofNullable(bookList);
		
		List<BookModel> bookModelList = new ArrayList<BookModel>();
		Books books = new Books();
		books.setBookId(123L);
		BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
		bookModelList.add(bookResponse);
        when(bookRepo.findByTitleAndCategoryAndPublisher(title, category,  publisher)).thenReturn(booksList);
        
		List<BookModel> expected = readerService.search(title, category,  publisher);

		assertNotNull(expected); 
	}
	
	@Test
	public void createPaymentTest() {
		long readerId=4;
		long bookId=1;
		PaymentRequest payment = new PaymentRequest();
		payment.setBookId(bookId);
		payment.setReaderId(readerId);
		User user = new User();
		user.setUserId(payment.getReaderId());
		Books book = new Books();
		book.setBookId(payment.getBookId());
		Payment actual = new Payment();
		actual.setId((long) 1);
		when(paymentRepo.save(ArgumentMatchers.any(Payment.class))).thenReturn(actual);
		
		Payment expected = readerService.createPayment(payment);
		
		assertThat(expected.getId()).isSameAs(actual.getId());		
	}
	
	@Test
	public void listPurchasedBooksTest() throws Exception {
		long readerId = 4;
		Payment payment = new Payment();
		payment.setId(123L);
		User user = new User();
		user.setUserId(123L);
		Books book = new Books("title","category","publisher","content","logo",120.0,true,user);
		book.setBookId(123L);
		
		payment.setBook(book);
		
		List<Payment> pList = new ArrayList<Payment>();
		pList.add(payment);
	
		Optional<List<Payment>> paymentList = Optional.ofNullable(pList);
		when(paymentRepo.findByUserUserId(readerId)).thenReturn(paymentList);
		Books books = new Books();
		books.setBookId(123L);
		List<BookModel> bookModelList = new ArrayList<BookModel>();
		BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
		bookModelList.add(bookResponse);

		List<BookModel> expected = readerService.listPurchasedBooks(readerId);

		assertNotNull(expected);
	}
	
	@Test
	public void listBookByPaymentIDTest() {
		long readerId = 4;
		long pid = 5;
		Payment payment = new Payment();
		payment.setId(123L);
		User user = new User();
		user.setUserId(123L);
		Books book = new Books("title","category","publisher","content","logo",120.0,true,user);
		book.setBookId(123L);
		
		payment.setBook(book);
		Optional<Payment> actual = Optional.ofNullable(payment);
		when(paymentRepo.findByUserUserIdAndId(readerId, pid)).thenReturn(actual);
		BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
		
		BookModel expected = readerService.listBookByPaymentID(readerId, pid);
		Assert.assertNotNull(expected);		
	}
	
	@Test
	public void testReadBook() {
		long readerId = 4;
		long bookId = 5;
		Payment payment = new Payment();
		payment.setId(123L);
		User user = new User();
		user.setUserId(123L);
		Books book = new Books("title","category","publisher","content","logo",120.0,true,user);
		book.setBookId(123L);
		
		payment.setBook(book);
		Optional<Payment> actual = Optional.ofNullable(payment);
		when(paymentRepo.findByUserUserIdAndBookBookId(readerId, bookId)).thenReturn(actual);
		BookModel bookResponse = new BookModel(123L,"book1", "comedy","xyz", "sncdhcdhdvdnhdhv", "image1.png", 120.0, true);
		
		BookModel expected = readerService.readBook(readerId, bookId);
		Assert.assertNotNull(expected);
	}
	 
}
