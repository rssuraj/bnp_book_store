package com.bnp.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnp.bookstore.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {}
