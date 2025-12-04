package com.bnp.bookstore.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.bookstore.entities.Book;
import com.bnp.bookstore.services.BookService;

import lombok.AllArgsConstructor;

@RestController("/books")
@AllArgsConstructor
public class BookController {

	private final BookService bookService;
	
	@GetMapping
	public ResponseEntity<Page<Book>> getBooks(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int pageSize) {
		
		Page<Book> books = bookService.getBooks(page, pageSize);
		
		return new ResponseEntity<Page<Book>>(books, HttpStatus.OK);
	}
}
