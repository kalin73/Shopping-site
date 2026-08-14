import React from 'react';
import { ShoppingCart, Eye, Star } from 'lucide-react';

export const ProductCard = ({ product, onAddToCart, onQuickView }) => {
    return (
        <div className="group relative bg-white dark:bg-slate-800/80 rounded-2xl p-4 border border-slate-200 dark:border-slate-700/60 shadow-sm hover:shadow-xl dark:hover:shadow-blue-900/10 transition-all duration-300 flex flex-col justify-between">
            <div>
                {/* Badge */}
                {product.isNew && (
                    <span className="absolute top-6 left-6 z-10 bg-blue-600 text-white text-xs font-semibold px-2.5 py-1 rounded-full">
            Ново
          </span>
                )}

                {/* Product Image Container */}
                <div className="relative aspect-square w-full rounded-xl overflow-hidden bg-slate-100 dark:bg-slate-900 mb-4 flex items-center justify-center">
                    <img
                        src={product.imageUrl || '/placeholder.png'}
                        alt={product.title}
                        className="object-cover w-full h-full group-hover:scale-105 transition-transform duration-300"
                    />

                    {/* Quick View Overlay */}
                    <div className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center gap-2">
                        <button
                            onClick={() => onQuickView(product)}
                            className="p-2.5 bg-white text-slate-900 rounded-full hover:bg-slate-100 transition shadow-lg"
                            title="Бърз преглед"
                        >
                            <Eye size={18} />
                        </button>
                    </div>
                </div>

                {/* Category & Title */}
                <span className="text-xs font-medium text-blue-600 dark:text-blue-400 tracking-wider uppercase">
          {product.category}
        </span>
                <h3 className="text-lg font-bold text-slate-900 dark:text-white mt-1 line-clamp-2">
                    {product.title}
                </h3>

                {/* Rating */}
                <div className="flex items-center gap-1 mt-2 text-amber-400">
                    <Star size={16} fill="currentColor" />
                    <span className="text-xs font-semibold text-slate-600 dark:text-slate-400">
            {product.rating || '4.8'}
          </span>
                </div>
            </div>

            {/* Price and Add to Cart */}
            <div className="mt-4 pt-3 border-t border-slate-100 dark:border-slate-700/50 flex items-center justify-between">
                <div>
                    <span className="text-xs text-slate-400 block">Цена</span>
                    <span className="text-xl font-extrabold text-slate-900 dark:text-white">
            {product.price.toFixed(2)} лв.
          </span>
                </div>

                <button
                    onClick={() => onAddToCart(product)}
                    className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white px-4 py-2.5 rounded-xl font-medium transition active:scale-95 shadow-md shadow-blue-500/20"
                >
                    <ShoppingCart size={18} />
                    <span className="hidden sm:inline">Купи</span>
                </button>
            </div>
        </div>
    );
};