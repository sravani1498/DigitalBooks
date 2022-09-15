package com.digitalbooks.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.demo.entity.Books;
import com.digitalbooks.demo.entity.Payment;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByUserUserId(Long userId);
	Payment findByUserUserIdAndId(Long userId, Long pid);
}
