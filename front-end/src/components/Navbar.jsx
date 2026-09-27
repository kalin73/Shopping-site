import React, { useState } from 'react';
import { ShoppingBag, Moon, Sun, User, Menu, X, LogOut, ChevronDown } from 'lucide-react';
import { useTheme } from '../context/ThemeContext';

const getInitials = (user) => {
    if (!user) return '?';
    if (user.firstName && user.lastName) return `${user.firstName[0]}${user.lastName[0]}`.toUpperCase();
    if (user.firstName) return user.firstName[0].toUpperCase();
    if (user.email) return user.email[0].toUpperCase();
    return '?';
};

export const Navbar = ({ cartCount, onOpenCart, onOpenAuth, user, onLogout }) => {
    const { theme, toggleTheme } = useTheme();
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);
    const [isProfileMenuOpen, setIsProfileMenuOpen] = useState(false);

    const displayName = user?.firstName || user?.email || 'Профил';

    return (
        <nav className="sticky top-0 z-50 backdrop-blur-md bg-white/80 dark:bg-slate-900/80 border-b border-slate-200 dark:border-slate-800 transition-colors">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex items-center justify-between h-16">

                    <div className="flex items-center gap-2">
                        <span className="text-2xl font-black bg-gradient-to-r from-blue-600 to-indigo-500 bg-clip-text text-transparent">
                            ITStore
                        </span>
                    </div>

                    <div className="hidden md:flex items-center gap-8 font-medium text-slate-700 dark:text-slate-200">
                        <a href="#" className="hover:text-blue-600 dark:hover:text-blue-400 transition">Начало</a>
                        <a href="#categories" className="hover:text-blue-600 dark:hover:text-blue-400 transition">Категории</a>
                        <a href="#products" className="hover:text-blue-600 dark:hover:text-blue-400 transition">Продукти</a>
                    </div>

                    <div className="flex items-center gap-4">
                        <button
                            onClick={toggleTheme}
                            className="p-2 rounded-lg bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 hover:bg-slate-200 dark:hover:bg-slate-700 transition"
                            aria-label="Toggle Theme"
                        >
                            {theme === 'dark' ? <Sun size={20} /> : <Moon size={20} />}
                        </button>

                        {user ? (
                            <div className="relative">
                                <button
                                    onClick={() => setIsProfileMenuOpen((v) => !v)}
                                    className="flex items-center gap-2 pl-1.5 pr-3 py-1.5 rounded-full bg-slate-100 dark:bg-slate-800 hover:bg-slate-200 dark:hover:bg-slate-700 transition"
                                >
                                    <span className="w-7 h-7 rounded-full bg-blue-600 text-white text-xs font-bold flex items-center justify-center">
                                        {getInitials(user)}
                                    </span>
                                    <span className="hidden sm:block text-sm font-semibold text-slate-700 dark:text-slate-200 max-w-[120px] truncate">
                                        {displayName}
                                    </span>
                                    <ChevronDown size={14} className="text-slate-400" />
                                </button>

                                {isProfileMenuOpen && (
                                    <>
                                        <div
                                            className="fixed inset-0 z-40"
                                            onClick={() => setIsProfileMenuOpen(false)}
                                        />
                                        <div className="absolute right-0 mt-2 w-56 bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-800 rounded-xl shadow-xl z-50 overflow-hidden">
                                            <div className="px-4 py-3 border-b border-slate-100 dark:border-slate-800">
                                                <p className="text-xs text-slate-400">Влезли сте като</p>
                                                <p className="text-sm font-semibold text-slate-800 dark:text-slate-100 truncate">
                                                    {user.email || displayName}
                                                </p>
                                            </div>
                                            <button
                                                onClick={() => {
                                                    setIsProfileMenuOpen(false);
                                                    onLogout();
                                                }}
                                                className="w-full flex items-center gap-2 px-4 py-3 text-sm text-red-600 dark:text-red-400 hover:bg-red-50 dark:hover:bg-red-950/30 transition"
                                            >
                                                <LogOut size={16} />
                                                Изход
                                            </button>
                                        </div>
                                    </>
                                )}
                            </div>
                        ) : (
                            <button
                                onClick={onOpenAuth}
                                className="p-2 rounded-lg bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 hover:bg-slate-200 dark:hover:bg-slate-700 transition"
                                aria-label="Вход в профила"
                            >
                                <User size={20} />
                            </button>
                        )}

                        <button
                            onClick={onOpenCart}
                            className="relative p-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 transition"
                        >
                            <ShoppingBag size={20} />
                            {cartCount > 0 && (
                                <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs font-bold w-5 h-5 rounded-full flex items-center justify-center animate-pulse">
                                    {cartCount}
                                </span>
                            )}
                        </button>

                        <button
                            onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
                            className="md:hidden p-2 rounded-lg text-slate-600 dark:text-slate-300"
                        >
                            {isMobileMenuOpen ? <X size={24} /> : <Menu size={24} />}
                        </button>
                    </div>
                </div>
            </div>

            {isMobileMenuOpen && (
                <div className="md:hidden px-4 pt-2 pb-4 space-y-2 bg-white dark:bg-slate-900 border-b border-slate-200 dark:border-slate-800">
                    <a href="#" className="block py-2 text-slate-700 dark:text-slate-200">Начало</a>
                    <a href="#categories" className="block py-2 text-slate-700 dark:text-slate-200">Категории</a>
                    <a href="#products" className="block py-2 text-slate-700 dark:text-slate-200">Продукти</a>
                </div>
            )}
        </nav>
    );
};