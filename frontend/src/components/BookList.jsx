import { useState, useEffect } from 'react';
import { fetchBooks } from '../services/api';

const BookList = ({ onAddToCart, token }) => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    loadBooks();
  }, [page]);

  const loadBooks = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await fetchBooks(token);
      setBooks(response);
      setTotalPages(response.length);
    } catch (err) {
      setError('Error loading books. Please try again later.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center py-12">
        <div className="text-xl text-gray-600">Loading books...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex justify-center items-center py-12">
        <div className="text-xl text-red-600">{error}</div>
      </div>
    );
  }

  return (
    <div className="container mx-auto px-4">
      <h2 className="text-3xl font-bold mb-6 text-gray-800">Available Books</h2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {books.map((book) => (
          <div
            key={book.id}
            className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow"
          >
            <h3 className="text-xl font-semibold mb-2 text-gray-800">{book.title}</h3>
            <p className="text-gray-600 mb-2">{book.isbn}</p>
            <p className="text-gray-600 mb-2">{book.authors.map(a => a.name).join(',')}</p>
            <div className="flex justify-between items-center mb-4">
              <span className="text-2xl font-bold text-green-600">€{book.price}</span>
              <span className={`text-sm ${book.availableQuantity > 0 ? 'text-gray-500' : 'text-red-500'}`}>
                {book.availableQuantity > 0 ? `${book.availableQuantity} available` : 'Out of stock'}
              </span>
            </div>
            <button
              onClick={() => onAddToCart(book)}
              disabled={book.availableQuantity === 0}
              className={`w-full py-2 px-4 rounded-md font-semibold transition-colors ${
                book.availableQuantity === 0
                  ? 'bg-gray-300 text-gray-500 cursor-not-allowed'
                  : 'bg-blue-600 text-white hover:bg-blue-700'
              }`}
            >
              {book.availableQuantity === 0 ? 'Out of Stock' : 'Add to Cart'}
            </button>
          </div>
        ))}
      </div>
      
      {/* Pagination */}
      {totalPages > 1 && (
        <div className="flex justify-center mt-8 gap-2">
          <button
            onClick={() => setPage(p => Math.max(1, p - 1))}
            disabled={page === 1}
            className="px-4 py-2 bg-gray-200 rounded disabled:opacity-50"
          >
            Previous
          </button>
          <span className="px-4 py-2">
            Page {page} of {totalPages}
          </span>
          <button
            onClick={() => setPage(p => Math.min(totalPages, p + 1))}
            disabled={page === totalPages}
            className="px-4 py-2 bg-gray-200 rounded disabled:opacity-50"
          >
            Next
          </button>
        </div>
      )}
    </div>
  );
};

export default BookList;
