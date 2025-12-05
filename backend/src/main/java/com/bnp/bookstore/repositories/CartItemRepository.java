package com.bnp.bookstore.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnp.bookstore.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	Set<CartItem> findByCartId(Long cartId);
}
