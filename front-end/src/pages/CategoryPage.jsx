import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';
import { ProductCard } from '../components/ProductCard';
import { getProductsByCategory } from '../services/api';
import { CATEGORIES } from '../constants/categories';

export function CategoryPage({ onAddToCart, onQuickView }) {
    const { catId } = useParams();
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const category = CATEGORIES.find((c) => String(c.id) === catId);

    useEffect(() => {
        setLoading(true);
        setError(null);

        getProductsByCategory(catId)
            .then(setProducts)
            .catch((err) => {
                console.error('Грешка при зареждане на продуктите по категория:', err);
                setError('Не успяхме да заредим продуктите за тази категория.');
            })
            .finally(() => setLoading(false));
    }, [catId]);

    return (
        <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <Link to="/categories" className="inline-flex items-center gap-2 text-sm text-slate-500 dark:text-slate-400 hover:text-blue-600 dark:hover:text-blue-400 mb-6 transition">
                <ArrowLeft size={16} /> Обратно към категориите
            </Link>

            <h1 className="text-3xl font-black text-slate-900 dark:text-white mb-8">
                {category?.name || 'Категория'}
            </h1>

            {loading && (
                <div className="text-center py-16">
                    <div className="inline-block animate-spin rounded-full h-8 w-8 border-4 border-blue-600 border-t-transparent mb-4"></div>
                    <p className="text-slate-500 dark:text-slate-400">Зареждане на продуктите...</p>
                </div>
            )}

            {error && (
                <div className="bg-red-50 dark:bg-red-950/30 border border-red-200 dark:border-red-900 text-red-600 dark:text-red-400 p-6 rounded-2xl text-center my-8">
                    <p className="font-semibold">{error}</p>
                </div>
            )}

            {!loading && !error && (
                products.length > 0 ? (
                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
                        {products.map((product) => (
                            <ProductCard
                                key={product.id}
                                product={product}
                                onAddToCart={onAddToCart}
                                onQuickView={onQuickView}
                            />
                        ))}
                    </div>
                ) : (
                    <div className="text-center py-16 text-slate-500">
                        Няма намерени продукти в тази категория.
                    </div>
                )
            )}
        </main>
    );
}