package com.digitalbboks.test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.digitalbooks.demo.controllers.ReaderController;
import com.digitalbooks.demo.entity.Payment;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.model.PaymentRequest;
import com.digitalbooks.demo.services.ReaderService;

@RunWith(MockitoJUnitRunner.class)
public class ReaderControllerTest {
	
	@Mock
	ReaderService service;
	@InjectMocks
	ReaderController controller;
	
	@Test
	public void searchTest() {
		long authorId = 1;
		String category = "comedy";
		double price = 12.81;
		String publisher = "xyz publications";
		
		List<BookModel> actual = new ArrayList<>();
		
		when(service.search(authorId, category, price, publisher)).thenReturn(actual);
		
		List<BookModel> expected = controller.search(authorId, category, price, publisher);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void buyTest() {
		PaymentRequest payment = new PaymentRequest();
		
		Payment actual = new Payment();
		when(service.createPayment(payment)).thenReturn(actual);
		ResponseEntity<?> expected = controller.buy(payment);
		assertNotNull(expected);
	}
	
	@Test
	public void listPurchasedBooksTest() {
		long readerId = 4;
				
		List<BookModel> actual = new ArrayList<>();
		
		when(service.listPurchasedBooks(readerId)).thenReturn(actual);
		
		List<BookModel> expected = controller.listPurchasedBooks(readerId);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void listBookByPaymentIDTest() {
		long readerId = 4;
		long pid = 5;
				
		BookModel actual = new BookModel();
		
		when(service.listBookByPaymentID(readerId, pid)).thenReturn(actual);
		
		BookModel expected = controller.listBookByPaymentID(readerId, pid);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void readBookTest() {
		long readerId = 4;
		long bookId = 1;
				
		BookModel actual = new BookModel();
		
		when(service.readBook(readerId, bookId)).thenReturn(actual);
		
		BookModel expected = controller.readBook(readerId, bookId);
		
		assertEquals(expected, actual);
	}

}
