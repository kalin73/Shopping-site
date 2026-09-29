import React, {useState, useEffect} from 'react';
import {Routes, Route} from 'react-router-dom';
import {ThemeProvider} from './context/ThemeContext';
import {Navbar} from './components/Navbar';
import {CartDrawer} from './components/CartDrawer';
import {QuickViewModal} from './components/QuickViewModal';
import {AuthModal} from './components/AuthModal';
import {HomePage} from './pages/HomePage';
import {ProductPage} from './pages/ProductPage';
import {getAllProducts, getCurrentUser, logoutUser} from './services/api';
import {CategoriesPage} from './pages/CategoriesPage';
import {CategoryPage} from './pages/CategoryPage';

export default function App() {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [cart, setCart] = useState([]);
    const [isCartOpen, setIsCartOpen] = useState(false);
    const [quickViewProduct, setQuickViewProduct] = useState(null);
    const [isAuthOpen, setIsAuthOpen] = useState(false);

    const [user, setUser] = useState(null);

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

    useEffect(() => {
        // Проверява дали вече има активна сесия (напр. след презареждане на страницата)
        getCurrentUser().then(setUser);
    }, []);

    const handleLoginSuccess = async () => {
        const me = await getCurrentUser();
        setUser(me);
    };

    const handleLogout = async () => {
        await logoutUser();
        setUser(null);
    };

    const handleAddToCart = (product) => {
        setCart((prev) => {
            const existing = prev.find((item) => item.id === product.id);
            if (existing) {
                return prev.map((item) =>
                    item.id === product.id ? {...item, quantity: item.quantity + 1} : item
                );
            }
            return [...prev, {...product, quantity: 1}];
        });
        setIsCartOpen(true);
    };

    const handleUpdateQuantity = (id, newQty) => {
        if (newQty <= 0) {
            handleRemoveFromCart(id);
            return;
        }
        setCart((prev) =>
            prev.map((item) => (item.id === id ? {...item, quantity: newQty} : item))
        );
    };

    const handleRemoveFromCart = (id) => {
        setCart((prev) => prev.filter((item) => item.id !== id));
    };

    return (
        <ThemeProvider>
            <div
                className="w-full min-h-screen bg-slate-50 dark:bg-slate-950 text-slate-900 dark:text-slate-100 transition-colors duration-300">

                <Navbar
                    cartCount={cart.reduce((sum, item) => sum + item.quantity, 0)}
                    onOpenCart={() => setIsCartOpen(true)}
                    onOpenAuth={() => setIsAuthOpen(true)}
                    user={user}
                    onLogout={handleLogout}
                />

                <Routes>
                    <Route
                        path="/"
                        element={
                            <HomePage
                                products={products}
                                loading={loading}
                                error={error}
                                onAddToCart={handleAddToCart}
                                onQuickView={(p) => setQuickViewProduct(p)}
                            />
                        }
                    />
                    <Route path="/categories" element={<CategoriesPage/>}/>
                    <Route
                        path="/category/:catId"
                        element={<CategoryPage onAddToCart={handleAddToCart}
                                               onQuickView={(p) => setQuickViewProduct(p)}/>}
                    />
                    <Route
                        path="/product/:id"
                        element={<ProductPage onAddToCart={handleAddToCart}/>}
                    />
                </Routes>

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
                    onLoginSuccess={handleLoginSuccess}
                />

            </div>
        </ThemeProvider>
    );
}