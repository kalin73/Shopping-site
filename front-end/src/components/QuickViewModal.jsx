import React from 'react';
import { X, Star, ShoppingCart, CheckCircle } from 'lucide-react';

export const QuickViewModal = ({ product, onClose, onAddToCart }) => {
    if (!product) return null;

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
            {/* Backdrop */}
            <div onClick={onClose} className="absolute inset-0 bg-black/60 backdrop-blur-sm" />

            {/* Modal Box */}
            <div className="relative bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-800 rounded-2xl max-w-2xl w-full p-6 shadow-2xl overflow-hidden z-10">
                <button
                    onClick={onClose}
                    className="absolute top-4 right-4 p-2 rounded-lg text-slate-400 hover:text-slate-600 dark:hover:text-white hover:bg-slate-100 dark:hover:bg-slate-800 transition"
                >
                    <X size={20} />
                </button>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-2">
                    {/* Image */}
                    <div className="aspect-square rounded-xl bg-slate-100 dark:bg-slate-800 overflow-hidden flex items-center justify-center">
                        <img
                            src={product.imageUrl || '/placeholder.png'}
                            alt={product.title}
                            className="object-cover w-full h-full"
                        />
                    </div>

                    {/* Details */}
                    <div className="flex flex-col justify-between">
                        <div>
              <span className="text-xs font-semibold uppercase tracking-wider text-blue-600 dark:text-blue-400">
                {product.category}
              </span>
                            <h2 className="text-xl font-bold text-slate-900 dark:text-white mt-1">
                                {product.title}
                            </h2>

                            <div className="flex items-center gap-2 mt-2">
                                <div className="flex text-amber-400">
                                    <Star size={16} fill="currentColor" />
                                </div>
                                <span className="text-xs font-semibold text-slate-600 dark:text-slate-400">
                  {product.rating || '4.8'}
                </span>
                                <span className="text-slate-300 dark:text-slate-700">•</span>
                                <span className="text-xs text-emerald-500 font-medium flex items-center gap-1">
                  <CheckCircle size={14} /> В наличност
                </span>
                            </div>

                            <p className="text-slate-600 dark:text-slate-300 text-sm mt-4 line-clamp-4">
                                {product.description || 'Висококачествен IT продукт с официална гаранция и възможност за бърза доставка.'}
                            </p>
                        </div>

                        <div className="mt-6 pt-4 border-t border-slate-100 dark:border-slate-800">
                            <div className="text-2xl font-black text-slate-900 dark:text-white mb-4">
                                {product.price.toFixed(2)} лв.
                            </div>

                            <button
                                onClick={() => {
                                    onAddToCart(product);
                                    onClose();
                                }}
                                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-xl font-semibold flex items-center justify-center gap-2 transition active:scale-[0.98]"
                            >
                                <ShoppingCart size={18} />
                                Добави в количката
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};