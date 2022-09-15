package com.digitalbooks.demo.model;

import javax.validation.constraints.NotNull;

public class PaymentRequest {
	@NotNull
	private Long bookId;
	@NotNull
	private Long readerId;
	
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public Long getReaderId() {
		return readerId;
	}
	public void setReaderId(Long readerId) {
		this.readerId = readerId;
	}

}
