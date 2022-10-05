package com.digitalbooks.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.Payment;
import com.digitalbooks.demo.entity.User;
import com.digitalbooks.demo.exception.InternalServerErrorException;
import com.digitalbooks.demo.exception.ResourceNotFoundException;
import com.digitalbooks.demo.model.BookModel;
import com.digitalbooks.demo.model.PaymentRequest;
import com.digitalbooks.demo.repository.BookRepository;
import com.digitalbooks.demo.repository.PaymentRepository;

@Service
public class ReaderService {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	PaymentRepository paymentRepository;
	
	public List<BookModel> search(String title, String category, String publisher) {
		List<BookModel> booksList = new ArrayList<BookModel>();
		
		Optional<List<Books>> bookList = Optional.ofNullable(bookRepository.findByTitleAndCategoryAndPublisher(title,category, publisher).orElseThrow(()->new ResourceNotFoundException("There are no books found.Please try with different search!!!")));	
		if(bookList.isPresent()) {
			bookList.get().forEach(books -> {
				BookModel bookModel = new BookModel(books.getBookId(),books.getTitle(), books.getCategory(), 
						books.getPublisher(), books.getContent(), books.getLogo(), books.getPrice(), books.getStatus());
				booksList.add(bookModel);
			});
		}
		return booksList;
	}
	
	public Payment createPayment(PaymentRequest payment) {
		User user = new User();
		user.setUserId(payment.getReaderId());
		Books book = new Books();
		book.setBookId(payment.getBookId());
		Payment paymentModel = new Payment(new Date(),user, book);
		try {
			return paymentRepository.save(paymentModel);
		}catch (Exception e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}
	
	public List<BookModel> listPurchasedBooks(Long readerId) {
		List<BookModel> booksList = new ArrayList<BookModel>();
		Optional<List<Payment>> paymentList = Optional.of(paymentRepository.findByUserUserId(readerId).orElseThrow(()->new ResourceNotFoundException("No purchased books available.")));
		if(paymentList.isPresent()) {
			paymentList.get().forEach(payment -> {
				Books books = payment.getBook();
				BookModel bookModel = new BookModel(books.getBookId(),books.getTitle(), books.getCategory(), 
						books.getPublisher(), books.getContent(), books.getLogo(), books.getPrice(), books.getStatus());
				booksList.add(bookModel);
			});
		}
		return booksList;
	}
	
	public BookModel listBookByPaymentID(Long readerId, Long pid) {
		Optional<Payment> payment = Optional.of(paymentRepository.findByUserUserIdAndId(readerId, pid).orElseThrow(()->new ResourceNotFoundException("Payment with ID :"+pid+" Not Found!")));
		Books books = payment.isPresent() ? payment.get().getBook() : null;
		BookModel bookModel = new BookModel(books.getBookId(),books.getTitle(), books.getCategory(), 
				books.getPublisher(), books.getContent(), books.getLogo(), books.getPrice(), books.getStatus());
		return bookModel;
	}
	
	public BookModel readBook(Long readerId, Long bookId) {
		Optional<Payment> payment = Optional.of(paymentRepository.findByUserUserIdAndBookBookId(readerId, bookId).orElseThrow(()->new ResourceNotFoundException("Book with ID :"+bookId+" Not Found!")));;
		Books book = payment.isPresent() ? payment.get().getBook() : null;
		BookModel bookModel = new BookModel(book.getBookId(),book.getTitle(), book.getCategory(), 
				book.getPublisher(), book.getContent(), book.getLogo(), book.getPrice(), book.getStatus());
		return bookModel;
	}
}
