package com.bnp.bookstore.services;

import org.springframework.stereotype.Service;

import com.bnp.bookstore.repositories.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
}
