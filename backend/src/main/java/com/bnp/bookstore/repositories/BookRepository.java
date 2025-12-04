package com.bnp.bookstore.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bnp.bookstore.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {}
