package com.bnp.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnp.bookstore.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {}
