package com.digitalbooks.demo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="readerBookPayments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date paymentDate;
	
	@ManyToOne
	private User user;

	@ManyToOne
	private Books book;
	
	public Payment() {
		
	}

	public Payment(Date paymentDate, User user, Books book) {
		super();
		this.paymentDate = paymentDate;
		this.user = user;
		this.book = book;
	}

	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Books getBook() {
		return book;
	}



	public void setBook(Books book) {
		this.book = book;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
