package com.bnp.bookstore.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bnp.bookstore.entities.Book;
import com.bnp.bookstore.exceptions.ResourceNotFoundException;
import com.bnp.bookstore.repositories.BookRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	
	public Page<Book> getBooks(int page, int pageSize) {
		
		Pageable pageable = PageRequest.of(page, pageSize);
		
		return bookRepository.findAll(pageable);
	}
	
	public Book getBook(Long bookId) throws ResourceNotFoundException {
		
		Optional<Book> book = bookRepository.findById(bookId);
		
		if(book.isEmpty())
			throw new ResourceNotFoundException("Book not found");
		
		return book.get();
	}

}
