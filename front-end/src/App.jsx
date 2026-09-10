import React, { useState, useEffect } from 'react';
import { ThemeProvider } from './context/ThemeContext';
import { Navbar } from './components/Navbar';
import { ProductCard } from './components/ProductCard';
import { CartDrawer } from './components/CartDrawer';
import { QuickViewModal } from './components/QuickViewModal';
import { AuthModal } from './components/AuthModal';
import { getAllProducts } from './services/api';

export default function App() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [cart, setCart] = useState([]);
  const [isCartOpen, setIsCartOpen] = useState(false);
  const [quickViewProduct, setQuickViewProduct] = useState(null);
  const [isAuthOpen, setIsAuthOpen] = useState(false);

  // Извличане на продуктите от Spring Boot API при зареждане на страницата
  useEffect(() => {
    getAllProducts()
        .then((data) => {
          setProducts(data);
          setLoading(false);
        })
        .catch((err) => {
          console.error("Грешка при връзката с бекенд API:", err);
          setError("Не успяхме да заредим продуктите от сървъра.");
          setLoading(false);
        });
  }, []);

  // Добавяне на продукт в количката
  const handleAddToCart = (product) => {
    setCart((prev) => {
      const existing = prev.find((item) => item.id === product.id);
      if (existing) {
        return prev.map((item) =>
            item.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
        );
      }
      return [...prev, { ...product, quantity: 1 }];
    });
    setIsCartOpen(true);
  };

  // Промяна на количество на артикул в количката
  const handleUpdateQuantity = (id, newQty) => {
    if (newQty <= 0) {
      handleRemoveFromCart(id);
      return;
    }
    setCart((prev) =>
        prev.map((item) => (item.id === id ? { ...item, quantity: newQty } : item))
    );
  };

  // Премахване на артикул от количката
  const handleRemoveFromCart = (id) => {
    setCart((prev) => prev.filter((item) => item.id !== id));
  };

  return (
      <ThemeProvider>
        <div className="w-full min-h-screen bg-slate-50 dark:bg-slate-950 text-slate-900 dark:text-slate-100 transition-colors duration-300">

          {/* Навигационна лента */}
          <Navbar
              cartCount={cart.reduce((sum, item) => sum + item.quantity, 0)}
              onOpenCart={() => setIsCartOpen(true)}
              onOpenAuth={() => setIsAuthOpen(true)}
          />

          {/* Главна банeр / Hero секция */}
          <header className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-12 pb-8 text-center">
          <span className="text-xs font-bold uppercase tracking-widest text-blue-600 dark:text-blue-400 bg-blue-100 dark:bg-blue-950/60 px-3 py-1 rounded-full">
            IT Магазин от ново поколение
          </span>
            <h1 className="text-4xl sm:text-6xl font-black mt-4 tracking-tight">
              Открий най-добрата <br />
              <span className="bg-gradient-to-r from-blue-600 to-indigo-500 bg-clip-text text-transparent">
              компютърна техника
            </span>
            </h1>
            <p className="text-slate-600 dark:text-slate-400 max-w-2xl mx-auto mt-4 text-base sm:text-lg">
              Модерно пазаруване с бърза доставка, официална гаранция и топ цени за всички IT продукти.
            </p>
          </header>

          {/* Секция с Продукти */}
          <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <div className="flex items-center justify-between mb-8">
              <h2 className="text-2xl font-extrabold">Популярни продукти</h2>
            </div>

            {/* Индикатор за зареждане */}
            {loading && (
                <div className="text-center py-16">
                  <div className="inline-block animate-spin rounded-full h-8 w-8 border-4 border-blue-600 border-t-transparent mb-4"></div>
                  <p className="text-slate-500 dark:text-slate-400">Зареждане на продуктите от сървъра...</p>
                </div>
            )}

            {/* Съобщение за грешка */}
            {error && (
                <div className="bg-red-50 dark:bg-red-950/30 border border-red-200 dark:border-red-900 text-red-600 dark:text-red-400 p-6 rounded-2xl text-center my-8">
                  <p className="font-semibold">{error}</p>
                  <p className="text-xs mt-1 text-slate-500">Уверете се, че Spring Boot бекендът работи на http://localhost:8080</p>
                </div>
            )}

            {/* Решетка с продукти */}
            {!loading && !error && (
                products.length > 0 ? (
                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
                      {products.map((product) => (
                          <ProductCard
                              key={product.id}
                              product={product}
                              onAddToCart={handleAddToCart}
                              onQuickView={(p) => setQuickViewProduct(p)}
                          />
                      ))}
                    </div>
                ) : (
                    <div className="text-center py-16 text-slate-500">
                      Все още няма налични продукти в базата данни.
                    </div>
                )
            )}
          </main>

          {/* Изскачащи прозорци и панел за количка */}
          <CartDrawer
              isOpen={isCartOpen}
              onClose={() => setIsCartOpen(false)}
              cartItems={cart}
              onUpdateQuantity={handleUpdateQuantity}
              onRemoveItem={handleRemoveFromCart}
          />

          <QuickViewModal
              product={quickViewProduct}
              onClose={() => setQuickViewProduct(null)}
              onAddToCart={handleAddToCart}
          />

          <AuthModal
              isOpen={isAuthOpen}
              onClose={() => setIsAuthOpen(false)}
          />

        </div>
      </ThemeProvider>
  );
}