package com.bnp.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnp.bookstore.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {}
