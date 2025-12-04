package com.bnp.bookstore.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Builder
@Getter
@Setter
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Long isbn;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(name = "available_quantity", nullable = false)
	private Long availableQuantity;
	
	@Column(nullable = true)
	private String about;
	
	@ManyToMany
	@JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	private Set<Author> authors; 
	
}
