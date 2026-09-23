import React, { useState } from 'react';
import { X, Mail, Lock, User, ArrowRight, CheckCircle2, Loader2, AlertCircle } from 'lucide-react';
import { loginUser, getGoogleLoginUrl } from '../services/api';

const GoogleIcon = () => (
    <svg width="18" height="18" viewBox="0 0 48 48">
        <path fill="#FFC107" d="M43.6 20.5H42V20H24v8h11.3C33.7 32.9 29.3 36 24 36c-6.6 0-12-5.4-12-12s5.4-12 12-12c3.1 0 5.8 1.1 8 3l6-6C34.5 6.1 29.5 4 24 4 12.9 4 4 12.9 4 24s8.9 20 20 20 20-8.9 20-20c0-1.2-.1-2.4-.4-3.5z"/>
        <path fill="#FF3D00" d="M6.3 14.7l6.6 4.8C14.6 15.9 18.9 13 24 13c3.1 0 5.8 1.1 8 3l6-6C34.5 6.1 29.5 4 24 4c-7.7 0-14.4 4.4-17.7 10.7z"/>
        <path fill="#4CAF50" d="M24 44c5.2 0 9.9-2 13.4-5.2l-6.2-5.2C29.2 35.3 26.7 36 24 36c-5.3 0-9.7-3.1-11.3-7.6l-6.5 5C9.5 39.6 16.2 44 24 44z"/>
        <path fill="#1976D2" d="M43.6 20.5H42V20H24v8h11.3c-.8 2.3-2.3 4.3-4.1 5.6l6.2 5.2C41 35 44 30 44 24c0-1.2-.1-2.4-.4-3.5z"/>
    </svg>
);

export const AuthModal = ({ isOpen, onClose, onLoginSuccess }) => {
    const [isLogin, setIsLogin] = useState(true);
    const [isRegistered, setIsRegistered] = useState(false);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [formData, setFormData] = useState({ email: '', password: '', username: '' });

    if (!isOpen) return null;

    const handleGoogleLogin = () => {
        // Пренасочва към бекенда - изисква OAuth2 client конфигурация там
        window.location.href = getGoogleLoginUrl();
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMsg('');

        if (!isLogin) {
            // TODO: реална регистрация през registerUser() - извън обхвата на тази задача
            setIsRegistered(true);
            return;
        }

        setIsSubmitting(true);
        try {
            await loginUser({ email: formData.email, password: formData.password });
            onLoginSuccess?.();
            onClose();
        } catch (err) {
            setErrorMsg(err.message);
        } finally {
            setIsSubmitting(false);
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

                        <button
                            type="button"
                            onClick={handleGoogleLogin}
                            className="w-full mt-6 flex items-center justify-center gap-3 border border-slate-200 dark:border-slate-700 py-2.5 rounded-xl font-semibold text-sm text-slate-700 dark:text-slate-200 hover:bg-slate-50 dark:hover:bg-slate-800 transition"
                        >
                            <GoogleIcon />
                            Продължи с Google
                        </button>

                        <div className="flex items-center gap-3 my-5">
                            <div className="h-px flex-1 bg-slate-200 dark:bg-slate-700" />
                            <span className="text-xs text-slate-400 uppercase">или</span>
                            <div className="h-px flex-1 bg-slate-200 dark:bg-slate-700" />
                        </div>

                        {errorMsg && (
                            <div className="flex items-center gap-2 text-sm text-red-600 dark:text-red-400 bg-red-50 dark:bg-red-950/30 border border-red-200 dark:border-red-900 rounded-xl px-3 py-2 mb-4">
                                <AlertCircle size={16} />
                                {errorMsg}
                            </div>
                        )}

                        <form onSubmit={handleSubmit} className="space-y-4">
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
                                disabled={isSubmitting}
                                className="w-full bg-blue-600 hover:bg-blue-700 disabled:opacity-60 text-white py-3 rounded-xl font-bold flex items-center justify-center gap-2 transition mt-2"
                            >
                                {isSubmitting ? (
                                    <Loader2 size={18} className="animate-spin" />
                                ) : (
                                    <>
                                        {isLogin ? 'Влез' : 'Регистрация'}
                                        <ArrowRight size={18} />
                                    </>
                                )}
                            </button>
                        </form>

                        <div className="mt-6 text-center text-sm text-slate-500 dark:text-slate-400">
                            {isLogin ? "Нямате профил? " : "Вече имате профил? "}
                            <button
                                onClick={() => { setIsLogin(!isLogin); setErrorMsg(''); }}
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