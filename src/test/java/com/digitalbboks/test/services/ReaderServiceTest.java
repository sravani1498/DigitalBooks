package com.digitalbboks.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.Payment;
import com.digitalbooks.demo.entity.User;
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
		long authorID = 1;
		String category = "comedy";
		double price = 230;
		String publisher = "xyz publications";
		
		Optional<List<Books>> booksList = Optional.ofNullable(new ArrayList<Books>());
		List<BookModel> bookModelList = new ArrayList<BookModel>();
		bookModelList.add(new BookModel(""));

        when(bookRepo.findByUserUserIdAndCategoryAndPriceAndPublisher(authorID, category, price, publisher)).thenReturn(booksList);
        
		List<BookModel> expected = readerService.search(authorID, category, price, publisher);

		assertEquals(expected, booksList.get());
		verify(bookRepo).findByUserUserIdAndCategoryAndPriceAndPublisher(authorID, category, price, publisher);
 
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
	public void listPurchasedBooksTest() {
		long readerId = 4;
	
		Optional<List<Payment>> payment = Optional.ofNullable(new ArrayList<Payment>());
		when(paymentRepo.findByUserUserId(readerId)).thenReturn(payment);

		List<BookModel> expected = readerService.listPurchasedBooks(readerId);

		assertEquals(expected, payment.get());
	    verify(paymentRepo).findByUserUserId(readerId);

	}
	
	@Test (expected = NullPointerException.class)
	public void listBookByPaymentIDTest() {
		long readerId = 4;
		long pid = 5;
		Optional<Payment> actual = Optional.ofNullable(new Payment());
		when(paymentRepo.findByUserUserIdAndId(readerId, pid)).thenReturn(actual);
		BookModel expected = readerService.listBookByPaymentID(readerId, pid);
		
		assertEquals(expected, actual.get());
	    verify(paymentRepo).findByUserUserIdAndId(readerId, pid);
		
	}
	 
}
