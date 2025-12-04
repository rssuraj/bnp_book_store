package com.bnp.bookstore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnp.bookstore.entities.Cart;
import com.bnp.bookstore.entities.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	public Optional<Cart> findByUserAndIsComplete(User user, Boolean isComplete);
}
