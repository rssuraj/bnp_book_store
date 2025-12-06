const OrderConfirmation = ({ orderId, items, total, onBackToStore }) => {
  return (
    <div className="max-w-2xl mx-auto p-6">
      <div className="bg-white rounded-lg shadow-md p-8 text-center">
        <div className="mb-6">
          <svg
            className="w-16 h-16 text-green-600 mx-auto"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
        </div>
        
        <h2 className="text-3xl font-bold text-gray-800 mb-2">Order Confirmed!</h2>
        <p className="text-gray-600 mb-6">Thank you for your purchase</p>
        
        <div className="bg-gray-100 rounded-lg p-4 mb-6">
          <p className="text-sm text-gray-600 mb-1">Order ID</p>
          <p className="text-lg font-bold text-gray-800">{orderId}</p>
        </div>
        
        <div className="text-left mb-6">
          <h3 className="font-bold text-lg mb-3">Order Details</h3>
          {items.map((item) => (
            <div key={item.book.id} className="flex justify-between py-2 border-b">
              <div>
                <p className="font-semibold">{item.book.title}</p>
                <p className="text-sm text-gray-600">Qty: {item.purchasedQuantity}</p>
              </div>
              <p className="font-bold">€{(item.book.price * item.purchasedQuantity).toFixed(2)}</p>
            </div>
          ))}
          <div className="flex justify-between py-3 mt-2">
            <span className="font-bold text-lg">Total:</span>
            <span className="font-bold text-lg text-green-600">€{total.toFixed(2)}</span>
          </div>
        </div>
        
        <button
          onClick={onBackToStore}
          className="w-full bg-blue-600 text-white py-3 rounded-md font-semibold hover:bg-blue-700 transition-colors"
        >
          Continue Shopping
        </button>
      </div>
    </div>
  );
};

export default OrderConfirmation;
