import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import App from './App';
import * as api from './services/api';

vi.mock('./services/api');

describe('App Integration Tests', () => {
  const mockBooks = [
    { id: 1, title: 'Test Book 1', author: 'Author 1', price: 19.99, quantity: 5 },
    { id: 2, title: 'Test Book 2', author: 'Author 2', price: 24.99, quantity: 3 },
  ];

  beforeEach(() => {
    vi.clearAllMocks();
    localStorage.clear();
    
    api.fetchCart.mockResolvedValue({ items: [], total: 0 });
    api.fetchBooks.mockResolvedValue({
      books: mockBooks,
      total: 2,
      page: 1,
      totalPages: 1,
    });
  });

  it('should render the app with header and book list', async () => {
    render(<App />);

    expect(screen.getByText('📚 Bookstore')).toBeInTheDocument();
    
    await waitFor(() => {
      expect(screen.getByText('Test Book 1')).toBeInTheDocument();
    });
  });

  it('should allow adding books to cart', async () => {
    api.addToCart.mockResolvedValue({
      items: [{ bookId: 1, title: 'Test Book 1', author: 'Author 1', price: 19.99, quantity: 1 }],
      total: 19.99,
    });

    render(<App />);

    await waitFor(() => {
      expect(screen.getByText('Test Book 1')).toBeInTheDocument();
    });

    const addButtons = screen.getAllByRole('button', { name: /add to cart/i });
    fireEvent.click(addButtons[0]);

    await waitFor(() => {
      expect(api.addToCart).toHaveBeenCalledWith(1, 1);
    });
  });

  it('should navigate to cart view', async () => {
    render(<App />);

    const cartButton = screen.getByRole('button', { name: /cart/i });
    fireEvent.click(cartButton);

    await waitFor(() => {
      expect(screen.getByText(/shopping cart/i)).toBeInTheDocument();
    });
  });

  it('should display cart item count in header', async () => {
    api.fetchCart.mockResolvedValue({
      items: [
        { bookId: 1, title: 'Test Book 1', author: 'Author 1', price: 19.99, quantity: 2 },
      ],
      total: 39.98,
    });

    render(<App />);

    await waitFor(() => {
      expect(screen.getByText(/cart \(2\)/i)).toBeInTheDocument();
    });
  });
});
