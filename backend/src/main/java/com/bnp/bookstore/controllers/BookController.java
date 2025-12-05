package com.bnp.bookstore.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnp.bookstore.entities.Book;
import com.bnp.bookstore.services.BookService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BookController {

	private final BookService bookService;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getBooks() {
		
		return new ResponseEntity<List<Book>>(bookService.getAll(), HttpStatus.OK);
	}
}
