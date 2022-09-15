package com.digitalbooks.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.Payment;
import com.digitalbooks.demo.entity.User;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.model.PaymentRequest;
import com.digitalbooks.demo.payload.response.MessageResponse;
import com.digitalbooks.demo.repository.BookRepository;
import com.digitalbooks.demo.repository.PaymentRepository;

@Service
public class ReaderService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	public List<BookModel> search(Long authorID, String category,Double price, String publisher) {
		List<BookModel> booksList = new ArrayList<BookModel>();
		
		List<Books> bookList = bookRepository.findByUserUserIdAndCategoryAndPriceAndPublisher(authorID, category, price, publisher);	

		bookList.forEach(books -> {
			BookModel bookModel = new BookModel(books.getBookId(),books.getTitle(), books.getCategory(), 
					books.getPublisher(), books.getContent(), books.getLogo(), books.getPrice(), books.getStatus());
//			bookModel.setTitle(books.getTitle());
//			bookModel.setCategory(books.getCategory());
//			bookModel.setContent(books.getContent());
//			bookModel.setLogo(books.getLogo());
//			bookModel.setPrice(books.getPrice());
//			bookModel.setPublisher(books.getPublisher());
//			bookModel.setStatus(books.getStatus());
			booksList.add(bookModel);
		});
		return booksList;
	}
	
	public ResponseEntity<MessageResponse> createPayment(PaymentRequest payment) {
		User user = new User();
		user.setUserId(payment.getReaderId());
		Books book = new Books();
		book.setBookId(payment.getBookId());
		Payment paymentModel = new Payment(new Date(),user, book);
		try {
			paymentRepository.save(paymentModel);
			return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Payment Successfully"));
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Please try after some time.."));
		}
		
	}
	
	public List<BookModel> listPurchasedBooks(Long readerId) {
		List<Payment> paymentList = paymentRepository.findByUserUserId(readerId);
		List<BookModel> booksList = new ArrayList<BookModel>();
		paymentList.forEach(payment -> {
			Books books = payment.getBook();
			BookModel bookModel = new BookModel(books.getBookId(),books.getTitle(), books.getCategory(), 
					books.getPublisher(), books.getContent(), books.getLogo(), books.getPrice(), books.getStatus());
			booksList.add(bookModel);
		});
		return booksList;
	}
	
	public BookModel listBookByPaymentID(Long readerId, Long pid) {
		Payment payment = paymentRepository.findByUserUserIdAndId(readerId, pid);
		Books books = payment.getBook();
		BookModel bookModel = new BookModel(books.getBookId(),books.getTitle(), books.getCategory(), 
				books.getPublisher(), books.getContent(), books.getLogo(), books.getPrice(), books.getStatus());
		return bookModel;
	}
	
	public BookModel readBook(Long readerId, Long bookId) {
		Payment payment = paymentRepository.findByUserUserIdAndBookBookId(readerId, bookId);
		if(payment != null){
			Books book = payment.getBook();
			BookModel bookModel = new BookModel(book.getContent());
			return bookModel;
		}
		return null;
	}

}
