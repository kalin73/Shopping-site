import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, ShoppingCart } from 'lucide-react';
import { getProductById } from '../services/api';

const BACKEND_URL = 'http://localhost:8080';
const resolveUrl = (path) => (!path ? null : path.startsWith('http') ? path : `${BACKEND_URL}${path}`);

export function ProductPage({ onAddToCart }) {
    const { id } = useParams();
    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [notFound, setNotFound] = useState(false);

    useEffect(() => {
        setLoading(true);
        setNotFound(false);
        setProduct(null);

        getProductById(id)
            .then(setProduct)
            .catch((err) => {
                if (err.response?.status === 404) {
                    setNotFound(true);
                } else {
                    console.error('Грешка при зареждане на продукта:', err);
                }
            })
            .finally(() => setLoading(false));
    }, [id]);

    if (loading) {
        return (
            <div className="text-center py-24">
                <div className="inline-block animate-spin rounded-full h-8 w-8 border-4 border-blue-600 border-t-transparent mb-4"></div>
                <p className="text-slate-500 dark:text-slate-400">Зареждане на продукта...</p>
            </div>
        );
    }

    if (notFound || !product) {
        return (
            <div className="text-center py-24">
                <p className="text-xl font-bold text-slate-800 dark:text-slate-100">Продуктът не бе намерен.</p>
                <Link to="/" className="inline-flex items-center gap-2 mt-4 text-blue-600 dark:text-blue-400 font-semibold hover:underline">
                    <ArrowLeft size={16} /> Към началната страница
                </Link>
            </div>
        );
    }

    const imageUrl = resolveUrl(product.image) || 'https://via.placeholder.com/500x500?text=No+Image';
    const videoUrl = resolveUrl(product.video);

    return (
        <main className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <Link to="/" className="inline-flex items-center gap-2 text-sm text-slate-500 dark:text-slate-400 hover:text-blue-600 dark:hover:text-blue-400 mb-6 transition">
                <ArrowLeft size={16} /> Обратно към продуктите
            </Link>

            <div className="grid grid-cols-1 lg:grid-cols-2 gap-10">
                <div className="space-y-4">
                    <div className="aspect-square rounded-2xl bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-800 overflow-hidden flex items-center justify-center p-6">
                        <img src={imageUrl} alt={product.productName} className="w-full h-full object-contain" />
                    </div>
                    {videoUrl && (
                        <video controls className="w-full rounded-2xl border border-slate-200 dark:border-slate-800">
                            <source src={videoUrl} />
                        </video>
                    )}
                </div>

                <div>
                    <h1 className="text-3xl font-black text-slate-900 dark:text-white">{product.productName}</h1>

                    <p className="text-slate-600 dark:text-slate-300 mt-6 leading-relaxed">
                        {product.description || 'Няма добавено описание за този продукт.'}
                    </p>

                    {product.specs?.length > 0 && (
                        <div className="mt-8">
                            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-500 dark:text-slate-400 mb-3">
                                Спецификации
                            </h2>
                            <dl className="divide-y divide-slate-200 dark:divide-slate-800 border-y border-slate-200 dark:border-slate-800">
                                {product.specs.map((spec, i) => (
                                    <div key={i} className="flex justify-between py-2.5 text-sm">
                                        <dt className="text-slate-500 dark:text-slate-400">{spec.name}</dt>
                                        <dd className="font-medium text-slate-800 dark:text-slate-200">{spec.value}</dd>
                                    </div>
                                ))}
                            </dl>
                        </div>
                    )}

                    <div className="mt-8 pt-6 border-t border-slate-200 dark:border-slate-800 flex items-center justify-between gap-4">
              <span className="text-3xl font-black text-slate-900 dark:text-white">
                {product.price?.toFixed(2)} лв.
              </span>
                        <button
                            onClick={() => onAddToCart(product)}
                            className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-xl font-semibold transition active:scale-95"
                        >
                            <ShoppingCart size={18} />
                            Добави в количката
                        </button>
                    </div>
                </div>
            </div>
        </main>
    );
}