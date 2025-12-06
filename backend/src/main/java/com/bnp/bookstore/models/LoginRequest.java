package com.bnp.bookstore.models;

import jakarta.annotation.Nonnull;

public record LoginRequest(
		@Nonnull String username, 
		@Nonnull String password
) {}
