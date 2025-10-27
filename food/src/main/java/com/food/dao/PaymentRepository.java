package com.food.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.food.entity.Payment;

import java.util.Optional;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
    Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);
    List<Payment> findByUserId(Long userId);
}
