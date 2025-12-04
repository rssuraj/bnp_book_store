package com.bnp.bookstore.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authors")
@Builder
@Getter
@Setter
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(nullable = true)
	private String about;
	
	@Column(nullable = true)
	private Integer age;
	
	@Column(nullable = true)
	private String gender;
	
	@ManyToMany(mappedBy = "authors")
	private Set<Book> books;
	
}
