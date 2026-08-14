import React from 'react';
import { X, Trash2, ShoppingBag, ArrowRight } from 'lucide-react';

export const CartDrawer = ({ isOpen, onClose, cartItems, onUpdateQuantity, onRemoveItem }) => {
    if (!isOpen) return null;

    const total = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div className="fixed inset-0 z-50 overflow-hidden">
            {/* Background Backdrop */}
            <div
                onClick={onClose}
                className="absolute inset-0 bg-black/60 backdrop-blur-sm transition-opacity"
            />

            <div className="fixed inset-y-0 right-0 max-w-full flex pl-10">
                <div className="w-screen max-w-md bg-white dark:bg-slate-900 border-l border-slate-200 dark:border-slate-800 shadow-2xl flex flex-col justify-between">

                    {/* Header */}
                    <div className="p-6 border-b border-slate-200 dark:border-slate-800 flex items-center justify-between">
                        <div className="flex items-center gap-3">
                            <ShoppingBag className="text-blue-600 dark:text-blue-400" size={24} />
                            <h2 className="text-xl font-bold text-slate-900 dark:text-white">Количка</h2>
                            <span className="bg-blue-100 dark:bg-blue-900/40 text-blue-600 dark:text-blue-400 text-xs font-semibold px-2.5 py-0.5 rounded-full">
                {cartItems.length}
              </span>
                        </div>
                        <button
                            onClick={onClose}
                            className="p-2 rounded-lg text-slate-400 hover:text-slate-600 dark:hover:text-white hover:bg-slate-100 dark:hover:bg-slate-800 transition"
                        >
                            <X size={20} />
                        </button>
                    </div>

                    {/* Cart Items List */}
                    <div className="flex-1 overflow-y-auto p-6 space-y-4">
                        {cartItems.length === 0 ? (
                            <div className="text-center py-12">
                                <ShoppingBag size={48} className="mx-auto text-slate-300 dark:text-slate-700 mb-4" />
                                <p className="text-slate-500 dark:text-slate-400 font-medium">Количката ви е празна</p>
                            </div>
                        ) : (
                            cartItems.map((item) => (
                                <div
                                    key={item.id}
                                    className="flex gap-4 p-3 bg-slate-50 dark:bg-slate-800/50 rounded-xl border border-slate-100 dark:border-slate-800"
                                >
                                    <img
                                        src={item.imageUrl || '/placeholder.png'}
                                        alt={item.title}
                                        className="w-20 h-20 object-cover rounded-lg bg-white dark:bg-slate-900"
                                    />
                                    <div className="flex-1 flex flex-col justify-between">
                                        <div>
                                            <h4 className="font-semibold text-slate-900 dark:text-white text-sm line-clamp-1">
                                                {item.title}
                                            </h4>
                                            <p className="text-sm font-bold text-blue-600 dark:text-blue-400 mt-0.5">
                                                {item.price.toFixed(2)} лв.
                                            </p>
                                        </div>

                                        <div className="flex items-center justify-between">
                                            {/* Quantity Controls */}
                                            <div className="flex items-center gap-2 bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-700 rounded-lg px-2 py-0.5">
                                                <button
                                                    onClick={() => onUpdateQuantity(item.id, item.quantity - 1)}
                                                    className="text-slate-500 hover:text-slate-900 dark:hover:text-white font-bold"
                                                >
                                                    -
                                                </button>
                                                <span className="text-xs font-semibold text-slate-900 dark:text-white px-1">
                          {item.quantity}
                        </span>
                                                <button
                                                    onClick={() => onUpdateQuantity(item.id, item.quantity + 1)}
                                                    className="text-slate-500 hover:text-slate-900 dark:hover:text-white font-bold"
                                                >
                                                    +
                                                </button>
                                            </div>

                                            {/* Remove Button */}
                                            <button
                                                onClick={() => onRemoveItem(item.id)}
                                                className="text-red-500 hover:text-red-700 p-1 transition"
                                                title="Премахни"
                                            >
                                                <Trash2 size={16} />
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            ))
                        )}
                    </div>

                    {/* Footer / Checkout */}
                    {cartItems.length > 0 && (
                        <div className="p-6 border-t border-slate-200 dark:border-slate-800 bg-slate-50/50 dark:bg-slate-900/50">
                            <div className="flex justify-between items-center mb-4">
                                <span className="text-slate-500 dark:text-slate-400 font-medium">Общо:</span>
                                <span className="text-2xl font-black text-slate-900 dark:text-white">
                  {total.toFixed(2)} лв.
                </span>
                            </div>
                            <button
                                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3.5 px-4 rounded-xl font-bold flex items-center justify-center gap-2 shadow-lg shadow-blue-500/25 transition active:scale-[0.98]"
                            >
                                Към финализиране
                                <ArrowRight size={18} />
                            </button>
                        </div>
                    )}

                </div>
            </div>
        </div>
    );
};