package com.digitalbooks.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.demo.entity.Payment;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<List<Payment>> findByUserUserId(Long userId);
	Optional<Payment> findByUserUserIdAndId(Long userId, Long pid);
	Optional<Payment> findByUserUserIdAndBookBookId(Long userId, Long bookId);
}
