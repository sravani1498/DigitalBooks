package com.digitalbooks.demo.model;

import javax.validation.constraints.NotBlank;

public class BookModel {
	
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Double price;
	
	private Long id;

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
	public BookModel(String content) {
		this.content = content;
	}
	
	public BookModel() {
		
	}
	 
	public BookModel(Long id,String title, String category, String publisher, String content, String logo, Double price, Boolean status) {
		this.category = category;
		this.content = content;
		this.logo = logo;
		this.price = price;
		this.publisher = publisher;
		this.status = status;
		this.title = title;
		this.id = id;
	}
	

}
