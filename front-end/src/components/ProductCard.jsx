import React from 'react';
import { Link } from 'react-router-dom';
import { Eye, ShoppingBag } from 'lucide-react';

export function ProductCard({ product, onAddToCart, onQuickView }) {
    const BACKEND_URL = 'http://localhost:8080';

    const fullImageUrl = product.image
        ? product.image.startsWith('http')
            ? product.image
            : `${BACKEND_URL}${product.image}`
        : 'https://via.placeholder.com/300x300?text=No+Image';

    return (
        <div className="group relative bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-800 rounded-2xl overflow-hidden transition-all duration-300 hover:shadow-xl hover:-translate-y-1 flex flex-col justify-between">

            <div className="relative aspect-square overflow-hidden bg-slate-100 dark:bg-slate-950 flex items-center justify-center p-4">
                <Link to={`/product/${product.id}`} className="w-full h-full flex items-center justify-center">
                    <img
                        src={fullImageUrl}
                        alt={product.productName}
                        className="w-full h-full object-contain object-center group-hover:scale-105 transition-transform duration-500"
                    />
                </Link>

                {product.quantity > 0 ? (
                    <span className="absolute top-3 left-3 bg-emerald-500/10 text-emerald-600 dark:text-emerald-400 text-xs font-semibold px-2.5 py-1 rounded-full border border-emerald-500/20 backdrop-blur-md">
            В наличност ({product.quantity})
          </span>
                ) : (
                    <span className="absolute top-3 left-3 bg-red-500/10 text-red-600 dark:text-red-400 text-xs font-semibold px-2.5 py-1 rounded-full border border-red-500/20 backdrop-blur-md">
            Изчерпан
          </span>
                )}

                <button
                    onClick={() => onQuickView(product)}
                    className="absolute right-3 top-3 bg-white/80 dark:bg-slate-800/80 p-2 rounded-full text-slate-700 dark:text-slate-200 opacity-0 group-hover:opacity-100 transition-opacity duration-200 hover:bg-white dark:hover:bg-slate-800 shadow-md backdrop-blur-sm"
                    title="Бърз преглед"
                >
                    <Eye size={18} />
                </button>
            </div>

            <div className="p-5 flex-1 flex flex-col justify-between">
                <div>
                    <Link to={`/product/${product.id}`}>
                        <h3 className="font-bold text-slate-800 dark:text-slate-100 text-base line-clamp-2 hover:text-blue-600 dark:hover:text-blue-400 transition-colors">
                            {product.productName}
                        </h3>
                    </Link>
                </div>

                <div className="mt-4 pt-3 border-t border-slate-100 dark:border-slate-800/60 flex items-center justify-between">
                    <div>
                        <span className="text-xs text-slate-400 block font-medium">Цена</span>
                        <span className="text-lg font-black text-slate-900 dark:text-white">
              {product.price?.toFixed(2)} лв.
            </span>
                    </div>

                    <button
                        onClick={() => onAddToCart(product)}
                        disabled={product.quantity <= 0}
                        className={`flex items-center gap-2 px-4 py-2.5 rounded-xl font-medium text-sm transition-all duration-200 shadow-sm ${
                            product.quantity > 0
                                ? 'bg-blue-600 hover:bg-blue-700 text-white hover:shadow-blue-500/20 active:scale-95'
                                : 'bg-slate-200 dark:bg-slate-800 text-slate-400 cursor-not-allowed'
                        }`}
                    >
                        <ShoppingBag size={16} />
                        <span>{product.quantity > 0 ? 'Купи' : 'Изчерпан'}</span>
                    </button>
                </div>
            </div>
        </div>
    );
}