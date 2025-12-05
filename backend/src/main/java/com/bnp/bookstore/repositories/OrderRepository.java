package com.bnp.bookstore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnp.bookstore.entities.Order;
import com.bnp.bookstore.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	Optional<Order> findByUserAndPaymentComplete(User user, Boolean paymentComplete);
}
