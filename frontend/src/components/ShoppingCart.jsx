const ShoppingCart = ({ items, total, onUpdateQuantity, onRemoveItem, onCheckout }) => {
  if (items.length === 0) {
    return (
      <div className="bg-white rounded-lg shadow-md p-6">
        <h2 className="text-2xl font-bold mb-4 text-gray-800">Shopping Cart</h2>
        <p className="text-gray-600 text-center py-8">Your cart is empty</p>
      </div>
    );
  }

  return (
    <div className="bg-white rounded-lg shadow-md p-6">
      <h2 className="text-2xl font-bold mb-4 text-gray-800">Shopping Cart</h2>
      <div className="space-y-4">
        {items.map((item) => (
          <div
            key={item.id}
            className="flex items-center justify-between border-b pb-4"
          >
            <div className="flex-1">
              <h3 className="text-lg font-semibold text-gray-800">{item.book.title}</h3>
              <p className="text-gray-600 text-sm">{item.book.isbn}</p>
              <p className="text-gray-600 text-sm">{item.book.authors.map(a => a.name).join(',')}</p>
              <p className="text-green-600 font-bold">€{item.book.price}</p>
            </div>
            
            <div className="flex items-center gap-4">
              <div className="flex items-center gap-2">
                <button
                  onClick={() => onUpdateQuantity(item.book, Math.max(1, item.purchasedQuantity - 1))}
                  className="w-8 h-8 bg-gray-200 rounded hover:bg-gray-300 font-bold"
                >
                  -
                </button>
                <span className="w-12 text-center font-semibold">{item.purchasedQuantity}</span>
                <button
                  onClick={() => onUpdateQuantity(item.book, item.purchasedQuantity + 1)}
                  className="w-8 h-8 bg-gray-200 rounded hover:bg-gray-300 font-bold"
                >
                  +
                </button>
              </div>
              
              <div className="text-right min-w-[80px]">
                <p className="font-bold text-lg">€{(item.book.price * item.purchasedQuantity).toFixed(2)}</p>
              </div>
              
              <button
                onClick={() => onRemoveItem(item)}
                className="text-red-600 hover:text-red-800 font-semibold px-3"
              >
                Remove
              </button>
            </div>
          </div>
        ))}
      </div>
      
      <div className="mt-6 pt-4 border-t">
        <div className="flex justify-between items-center mb-4">
          <span className="text-xl font-bold text-gray-800">Total:</span>
          <span className="text-2xl font-bold text-green-600">€{total.toFixed(2)}</span>
        </div>
        
        <button
          onClick={onCheckout}
          className="w-full bg-blue-600 text-white py-3 rounded-md font-semibold hover:bg-blue-700 transition-colors"
        >
          Proceed to Checkout
        </button>
      </div>
    </div>
  );
};

export default ShoppingCart;
