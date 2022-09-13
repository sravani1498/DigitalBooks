package com.digitalbooks.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.demo.models.Books;

@Repository
public interface BookRepository extends JpaRepository<Books, Long>{

}
