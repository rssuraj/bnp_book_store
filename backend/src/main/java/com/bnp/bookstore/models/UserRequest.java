package com.bnp.bookstore.models;

import jakarta.annotation.Nonnull;

public record UserRequest(
		@Nonnull String name, 
		@Nonnull String email, 
		@Nonnull String password
) {}
