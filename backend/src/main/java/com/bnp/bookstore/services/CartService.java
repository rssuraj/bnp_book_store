package com.bnp.bookstore.services;

import org.springframework.stereotype.Service;

import com.bnp.bookstore.repositories.CartRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
}
