package com.digitalbooks.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.demo.entity.Books;

@Repository
public interface BookRepository extends JpaRepository<Books, Long>{
	
	List<Books> findByUserUserIdAndCategoryAndPriceAndPublisher(Long userId, String category, double price,String publisher);
	


}
