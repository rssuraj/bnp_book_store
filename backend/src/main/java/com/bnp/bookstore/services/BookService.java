package com.bnp.bookstore.services;

import org.springframework.stereotype.Service;

import com.bnp.bookstore.repositories.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;

}
