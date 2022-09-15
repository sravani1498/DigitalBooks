package com.digitalbooks.demo.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.Payment;
import com.digitalbooks.demo.entity.User;
import com.digitalbooks.demo.model.PaymentRequest;
import com.digitalbooks.demo.services.ReaderService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/digitalbooks/reader")
public class ReaderContoller {
	
	@Autowired
	ReaderService readerService;
	
	@GetMapping("books/search")
	public List<Books> search(@RequestParam("authorId") Long authorID, @RequestParam("category") String category, @RequestParam("price") Double price,@RequestParam("publisher") String publisher){
		return readerService.search(authorID, category, price, publisher);
	}
	
	@PostMapping("books/buy")
	public ResponseEntity<?> buy(@Valid @RequestBody PaymentRequest payment) {
		User user = new User();
		user.setUserId(payment.getReaderId());
		Books book = new Books();
		book.setBookId(payment.getBookId());
		Payment paymentModel = new Payment(new Date(),user, book);
		return readerService.createPayment(paymentModel);
	}
	
	@GetMapping("{readerId}/allBooks")
	public List<Payment> listPurchasedBooks(@PathVariable("readerId") Long readerId) {
		return readerService.listPurchasedBooks(readerId);
	}
	
	@GetMapping("{readerId}/books")
	public Payment listBookByPaymentID(@PathVariable("readerId") Long readerId, @RequestParam("pid") Long pid) {
		return readerService.listBookByPaymentID(readerId,pid);
	}
}
