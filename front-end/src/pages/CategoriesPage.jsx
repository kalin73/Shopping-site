import React from 'react';
import { Link } from 'react-router-dom';
import { Cpu, Smartphone, Watch, Tablet, Laptop, Gamepad2, Monitor, Usb } from 'lucide-react';
import { CATEGORIES } from '../constants/categories';

const ICONS = {
    1: Cpu,
    2: Smartphone,
    3: Watch,
    4: Tablet,
    5: Laptop,
    6: Gamepad2,
    7: Monitor,
    8: Usb,
};

export function CategoriesPage() {
    return (
        <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
            <h1 className="text-3xl font-black text-slate-900 dark:text-white mb-8">Категории</h1>

            <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 gap-6">
                {CATEGORIES.map((cat) => {
                    const Icon = ICONS[cat.id];
                    return (
                        <Link
                            key={cat.id}
                            to={`/category/${cat.id}`}
                            className="group flex flex-col items-center justify-center gap-3 bg-white dark:bg-slate-900 border border-slate-200 dark:border-slate-800 rounded-2xl p-8 text-center hover:shadow-xl hover:-translate-y-1 transition-all duration-300"
                        >
                            <div className="w-14 h-14 rounded-full bg-blue-50 dark:bg-blue-950/50 flex items-center justify-center text-blue-600 dark:text-blue-400 group-hover:bg-blue-600 group-hover:text-white transition-colors">
                                <Icon size={26} />
                            </div>
                            <h3 className="font-bold text-slate-800 dark:text-slate-100">{cat.name}</h3>
                        </Link>
                    );
                })}
            </div>
        </main>
    );
}