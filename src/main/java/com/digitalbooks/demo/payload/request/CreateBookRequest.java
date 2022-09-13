package com.digitalbooks.demo.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateBookRequest {
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String category;
	
	@NotBlank
	private String publisher;
	
	@NotBlank
	private String content;
	 
	private Boolean status;
	
	private String logo;
	
	private Double price;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
