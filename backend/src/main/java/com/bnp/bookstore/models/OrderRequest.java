package com.bnp.bookstore.models;

import jakarta.annotation.Nonnull;

public record OrderRequest(
		@Nonnull Long cartId, 
		@Nonnull Boolean paymentComplete
) {}
