import React, { useState } from 'react';
import { ThemeProvider } from './context/ThemeContext';
import { Navbar } from './components/Navbar';
import { ProductCard } from './components/ProductCard';
import { CartDrawer } from './components/CartDrawer';
import { QuickViewModal } from './components/QuickViewModal';
import { AuthModal } from './components/AuthModal';

// Примерни (mock) данни за IT продукти
const INITIAL_PRODUCTS = [
  {
    id: 1,
    title: 'Геймърски Лаптоп Lenovo Legion Pro 5',
    category: 'Лаптопи',
    price: 2499.99,
    rating: '4.9',
    isNew: true,
    imageUrl: 'https://images.unsplash.com/photo-1603302576837-37561b2e2302?auto=format&fit=crop&w=600&q=80',
    description: 'Мощен геймърски лаптоп с процесор AMD Ryzen 7, NVIDIA RTX 4070 и 32GB RAM за безпроблемна игра и работа.'
  },
  {
    id: 2,
    title: 'Механична Клавиатура Keychron K2 Wireless',
    category: 'Периферия',
    price: 189.00,
    rating: '4.8',
    isNew: false,
    imageUrl: 'https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=600&q=80',
    description: 'Компактна безжична механична клавиатура с RGB подсветка и сменяеми суичове.'
  },
  {
    id: 3,
    title: 'Монитор Dell UltraSharp 27" 4K USB-C',
    category: 'Монитори',
    price: 899.00,
    rating: '4.7',
    isNew: true,
    imageUrl: 'https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?auto=format&fit=crop&w=600&q=80',
    description: 'Професионален IPS монитор с 4K резолюция, перфектно цветопредаване и USB-C хъб.'
  },
  {
    id: 4,
    title: 'Безжична мишка Logitech MX Master 3S',
    category: 'Периферия',
    price: 219.00,
    rating: '5.0',
    isNew: false,
    imageUrl: 'https://images.unsplash.com/photo-1615663245857-ac93bb7c39e7?auto=format&fit=crop&w=600&q=80',
    description: 'Ергономична безжична мишка за максимална продуктивност и тихо щракване.'
  }
];

export default function App() {
  const [cart, setCart] = useState([]);
  const [isCartOpen, setIsCartOpen] = useState(false);
  const [quickViewProduct, setQuickViewProduct] = useState(null);
  const [isAuthOpen, setIsAuthOpen] = useState(false);

  // Добавяне в количката
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

  // Промяна на количество в количката
  const handleUpdateQuantity = (id, newQty) => {
    if (newQty <= 0) {
      handleRemoveFromCart(id);
      return;
    }
    setCart((prev) =>
        prev.map((item) => (item.id === id ? { ...item, quantity: newQty } : item))
    );
  };

  // Премахване от количката
  const handleRemoveFromCart = (id) => {
    setCart((prev) => prev.filter((item) => item.id !== id));
  };

  return (
      <ThemeProvider>
        <div className="min-h-screen bg-slate-50 dark:bg-slate-950 text-slate-900 dark:text-slate-100 transition-colors duration-300">

          {/* Навигация */}
          <Navbar
              cartCount={cart.reduce((sum, item) => sum + item.quantity, 0)}
              onOpenCart={() => setIsCartOpen(true)}
              onOpenAuth={() => setIsAuthOpen(true)}
          />

          {/* Hero Секция */}
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

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              {INITIAL_PRODUCTS.map((product) => (
                  <ProductCard
                      key={product.id}
                      product={product}
                      onAddToCart={handleAddToCart}
                      onQuickView={(p) => setQuickViewProduct(p)}
                  />
              ))}
            </div>
          </main>

          {/* Модали & Изскачащи панели */}
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