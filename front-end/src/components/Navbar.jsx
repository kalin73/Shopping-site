import React, { useState } from 'react';
import { ShoppingBag, Search, Moon, Sun, User, Menu, X } from 'lucide-react';
import { useTheme } from '../context/ThemeContext';

export const Navbar = ({ cartCount, onOpenCart, onOpenAuth }) => {
    const { theme, toggleTheme } = useTheme();
    const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

    return (
        <nav className="sticky top-0 z-50 backdrop-blur-md bg-white/80 dark:bg-slate-900/80 border-b border-slate-200 dark:border-slate-800 transition-colors">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex items-center justify-between h-16">

                    {/* Logo */}
                    <div className="flex items-center gap-2">
            <span className="text-2xl font-black bg-gradient-to-r from-blue-600 to-indigo-500 bg-clip-text text-transparent">
              ITStore
            </span>
                    </div>

                    {/* Desktop Navigation Links */}
                    <div className="hidden md:flex items-center gap-8 font-medium text-slate-700 dark:text-slate-200">
                        <a href="#" className="hover:text-blue-600 dark:hover:text-blue-400 transition">Начало</a>
                        <a href="#categories" className="hover:text-blue-600 dark:hover:text-blue-400 transition">Категории</a>
                        <a href="#products" className="hover:text-blue-600 dark:hover:text-blue-400 transition">Продукти</a>
                    </div>

                    {/* Action Icons */}
                    <div className="flex items-center gap-4">
                        {/* Dark Mode Toggle */}
                        <button
                            onClick={toggleTheme}
                            className="p-2 rounded-lg bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 hover:bg-slate-200 dark:hover:bg-slate-700 transition"
                            aria-label="Toggle Theme"
                        >
                            {theme === 'dark' ? <Sun size={20} /> : <Moon size={20} />}
                        </button>

                        {/* Profile / Login */}
                        <button
                            onClick={onOpenAuth}
                            className="p-2 rounded-lg bg-slate-100 dark:bg-slate-800 text-slate-600 dark:text-slate-300 hover:bg-slate-200 dark:hover:bg-slate-700 transition"
                            aria-label="Вход в профила"
                        >
                            <User size={20} />
                        </button>

                        {/* Cart Button with Counter */}
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

                        {/* Mobile Menu Button */}
                        <button
                            onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
                            className="md:hidden p-2 rounded-lg text-slate-600 dark:text-slate-300"
                        >
                            {isMobileMenuOpen ? <X size={24} /> : <Menu size={24} />}
                        </button>
                    </div>
                </div>
            </div>

            {/* Mobile Menu */}
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