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
		
		loadBookAndAuthor("F. Scott Fitzgerald", 123456l, "The Great Gatsby", 12.99, 10l);
		loadBookAndAuthor("Harper Lee", 223456l, "To Kill a Mockingbird", 14.99, 8l);
		loadBookAndAuthor("George Orwell", 323456l, "1984", 13.99, 15l);
		loadBookAndAuthor("Jane Austen", 423456l, "Pride and Prejudice", 11.99, 12l);
		loadBookAndAuthor("J.D. Salinger", 523456l, "The Catcher in the Rye", 10.99, 7l);
		loadBookAndAuthor("George Orwell", 623456l, "Animal Farm", 9.99, 20l);
		loadBookAndAuthor("Aldous Huxley", 723456l, "Brave New World", 12.49, 9l);
		loadBookAndAuthor("J.R.R. Tolkien", 823456l, "The Hobbit", 15.99, 11l);
		loadBookAndAuthor("J.K. Rowling", 923456l, "Harry Potter and the Sorcerer\\'s Stone", 16.99, 25l);
		loadBookAndAuthor("William Golding", 103456l, "Lord of the Flies", 11.49, 6l);
		loadBookAndAuthor("C.S. Lewis", 113456l, "The Chronicles of Narnia", 18.99, 14l);
		loadBookAndAuthor("Charlotte Bronte", 133456l, "Jane Eyre", 13.49, 8l);
		
	}
	
	private void loadBookAndAuthor(String authorName, Long isbn, String title, double price, long quantity) {
		
		Author author = new Author();
		author.setName(authorName);
		
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setPrice(price);
		book.setAvailableQuantity(quantity);
		book.setAuthors(Set.of(author));
		
		bookRepository.save(book);
		
	}
}
