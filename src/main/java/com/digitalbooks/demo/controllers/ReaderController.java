package com.digitalbooks.demo.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.demo.entity.Payment;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.model.PaymentModel;
import com.digitalbooks.demo.model.PaymentRequest;
import com.digitalbooks.demo.services.ReaderService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/digitalbooks/reader")
public class ReaderController {
	
	@Autowired
	ReaderService readerService;
	
	@GetMapping("books/search")
	public List<BookModel> search(@RequestParam("authorId") Long authorID, @RequestParam("category") String category, @RequestParam("price") Double price,@RequestParam("publisher") String publisher){
		return readerService.search(authorID, category, price, publisher);
	}
	
	@PreAuthorize("hasRole('ROLE_READER')")
	@PostMapping("books/buy")
	public ResponseEntity<?> buy(@Valid @RequestBody PaymentRequest payment) {
		Payment paymentResponse = readerService.createPayment(payment);
		PaymentModel paymentModel = new PaymentModel();
		if(paymentResponse != null) {
			paymentModel.setPaymentId(paymentResponse.getId());
			paymentModel.setMessage("Payment Successfull");
			return ResponseEntity.status(HttpStatus.CREATED).body(paymentModel);
		} else {
			paymentModel.setMessage("Payment unsuccessfull.Please try after sometime");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(paymentModel);
		}

	}
	
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("{readerId}/allBooks")
	public List<BookModel> listPurchasedBooks(@PathVariable("readerId") Long readerId) {
		return readerService.listPurchasedBooks(readerId);
	}
	
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("{readerId}/books")
	public BookModel listBookByPaymentID(@PathVariable("readerId") Long readerId, @RequestParam("pid") Long pid) {
		return readerService.listBookByPaymentID(readerId,pid);
	}
	
	@PreAuthorize("hasRole('ROLE_READER')")
	@GetMapping("{readerId}/books/{bookId}")
	public BookModel readBook(@PathVariable("readerId") Long readerId, @PathVariable("bookId") Long bookId) {
		return readerService.readBook(readerId,bookId);
	}
}
