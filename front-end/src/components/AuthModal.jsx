import React, { useState } from 'react';
import { X, Mail, Lock, User, ArrowRight, CheckCircle2 } from 'lucide-react';

export const AuthModal = ({ isOpen, onClose }) => {
    const [isLogin, setIsLogin] = useState(true);
    const [isRegistered, setIsRegistered] = useState(false);
    const [formData, setFormData] = useState({ email: '', password: '', username: '' });

    if (!isOpen) return null;

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!isLogin) {
            // Имитация на успешна регистрация за верификация
            setIsRegistered(true);
        } else {
            // Тук ще е заявката за Вход към Spring Boot
            onClose();
        }
    };

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
            <div onClick={onClose} className="absolute inset-0 bg-black/60 backdrop-blur-sm" />

            <div className="relative bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-800 rounded-2xl max-w-md w-full p-6 shadow-2xl z-10">
                <button
                    onClick={onClose}
                    className="absolute top-4 right-4 p-2 rounded-lg text-slate-400 hover:text-slate-600 dark:hover:text-white hover:bg-slate-100 dark:hover:bg-slate-800 transition"
                >
                    <X size={20} />
                </button>

                {isRegistered ? (
                    <div className="text-center py-6">
                        <CheckCircle2 size={56} className="mx-auto text-emerald-500 mb-4" />
                        <h3 className="text-xl font-bold text-slate-900 dark:text-white">Проверете имейла си</h3>
                        <p className="text-slate-600 dark:text-slate-400 text-sm mt-2">
                            Изпратихме линк за потвърждение на <span className="font-semibold text-slate-800 dark:text-slate-200">{formData.email}</span>.
                        </p>
                        <button
                            onClick={() => { setIsRegistered(false); setIsLogin(true); }}
                            className="mt-6 text-sm text-blue-600 dark:text-blue-400 font-semibold hover:underline"
                        >
                            Към вход в профила
                        </button>
                    </div>
                ) : (
                    <div>
                        <h2 className="text-2xl font-bold text-slate-900 dark:text-white">
                            {isLogin ? 'Вход в профила' : 'Създай профил'}
                        </h2>
                        <p className="text-slate-500 dark:text-slate-400 text-sm mt-1">
                            {isLogin ? 'Добре дошли отново!' : 'Регистрирайте се за бързи поръчки.'}
                        </p>

                        <form onSubmit={handleSubmit} className="mt-6 space-y-4">
                            {!isLogin && (
                                <div>
                                    <label className="text-xs font-semibold text-slate-700 dark:text-slate-300 uppercase">Потребителско име</label>
                                    <div className="relative mt-1">
                                        <User size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" />
                                        <input
                                            type="text"
                                            required
                                            value={formData.username}
                                            onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                                            placeholder="john_doe"
                                            className="w-full pl-10 pr-4 py-2.5 bg-slate-50 dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl text-slate-900 dark:text-white text-sm focus:outline-none focus:border-blue-500"
                                        />
                                    </div>
                                </div>
                            )}

                            <div>
                                <label className="text-xs font-semibold text-slate-700 dark:text-slate-300 uppercase">Имейл адрес</label>
                                <div className="relative mt-1">
                                    <Mail size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" />
                                    <input
                                        type="email"
                                        required
                                        value={formData.email}
                                        onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                                        placeholder="name@example.com"
                                        className="w-full pl-10 pr-4 py-2.5 bg-slate-50 dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl text-slate-900 dark:text-white text-sm focus:outline-none focus:border-blue-500"
                                    />
                                </div>
                            </div>

                            <div>
                                <label className="text-xs font-semibold text-slate-700 dark:text-slate-300 uppercase">Парола</label>
                                <div className="relative mt-1">
                                    <Lock size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400" />
                                    <input
                                        type="password"
                                        required
                                        value={formData.password}
                                        onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                                        placeholder="••••••••"
                                        className="w-full pl-10 pr-4 py-2.5 bg-slate-50 dark:bg-slate-800 border border-slate-200 dark:border-slate-700 rounded-xl text-slate-900 dark:text-white text-sm focus:outline-none focus:border-blue-500"
                                    />
                                </div>
                            </div>

                            <button
                                type="submit"
                                className="w-full bg-blue-600 hover:bg-blue-700 text-white py-3 rounded-xl font-bold flex items-center justify-center gap-2 transition mt-2"
                            >
                                {isLogin ? 'Влез' : 'Регистрация'}
                                <ArrowRight size={18} />
                            </button>
                        </form>

                        <div className="mt-6 text-center text-sm text-slate-500 dark:text-slate-400">
                            {isLogin ? "Нямате профил? " : "Вече имате профил? "}
                            <button
                                onClick={() => setIsLogin(!isLogin)}
                                className="text-blue-600 dark:text-blue-400 font-semibold hover:underline"
                            >
                                {isLogin ? 'Регистрирайте се' : 'Влезте оттук'}
                            </button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};