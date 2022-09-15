package com.digitalbooks.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.Payment;
import com.digitalbooks.demo.payload.response.MessageResponse;
import com.digitalbooks.demo.repository.BookRepository;
import com.digitalbooks.demo.repository.PaymentRepository;

@Service
public class ReaderService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	public List<Books> search(Long authorID, String category,Double price, String publisher) {
		return bookRepository.findByUserUserIdAndCategoryAndPriceAndPublisher(authorID, category, price, publisher);	
	}
	
	public ResponseEntity<MessageResponse> createPayment(Payment payment) {
		try {
			paymentRepository.save(payment);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Payment Successfully"));
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Please try after some time.."));
		}
		
	}
	
	public List<Payment> listPurchasedBooks(Long readerId) {
		return paymentRepository.findByUserUserId(readerId);
	}
	
	public Payment listBookByPaymentID(Long readerId, Long pid) {
		return paymentRepository.findByUserUserIdAndId(readerId, pid);
	}

}
