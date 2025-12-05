package com.bnp.bookstore.config;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.bnp.bookstore.entities.Author;
import com.bnp.bookstore.entities.Book;
import com.bnp.bookstore.repositories.BookRepository;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class InitialLoad {

	private BookRepository bookRepository;
	
	@PostConstruct
	public void loadData() {
		
		loadBookAndAuthor();
		
	}
	
	private void loadBookAndAuthor() {
		
		Author author = new Author();
		author.setFirstName("Plato");
		author.setLastName("Plato");
		author.setAbout("Philosophy, political theory");
		
		Book book = new Book();
		book.setIsbn(123456l);
		book.setTitle("The Republic");
		book.setPrice(10.0);
		book.setAvailableQuantity(10l);
		book.setAbout("Philosophy, political theory");
		book.setAuthors(Set.of(author));
		
		bookRepository.save(book);
		
	}
}
