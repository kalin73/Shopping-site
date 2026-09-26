import React from 'react';
import { ProductCard } from '../components/ProductCard';

export function HomePage({ products, loading, error, onAddToCart, onQuickView }) {
    return (
        <>
            <header className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-12 pb-8 text-center">
          <span className="text-xs font-bold uppercase tracking-widest text-blue-600 dark:text-blue-400 bg-blue-100 dark:bg-blue-950/60 px-3 py-1 rounded-full">
            IT Магазин от ново поколение
          </span>
                <h1 className="text-4xl sm:text-6xl font-black mt-4 tracking-tight">
                    Открий най-добрата <br />
                    <span className="bg-gradient-to-r from-blue-600 to-indigo-500 bg-clip-text text-transparent">
              компютърна техника
            </span>
                </h1>
                <p className="text-slate-600 dark:text-slate-400 max-w-2xl mx-auto mt-4 text-base sm:text-lg">
                    Модерно пазаруване с бърза доставка, официална гаранция и топ цени за всички IT продукти.
                </p>
            </header>

            <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                <div className="flex items-center justify-between mb-8">
                    <h2 className="text-2xl font-extrabold">Популярни продукти</h2>
                </div>

                {loading && (
                    <div className="text-center py-16">
                        <div className="inline-block animate-spin rounded-full h-8 w-8 border-4 border-blue-600 border-t-transparent mb-4"></div>
                        <p className="text-slate-500 dark:text-slate-400">Зареждане на продуктите от сървъра...</p>
                    </div>
                )}

                {error && (
                    <div className="bg-red-50 dark:bg-red-950/30 border border-red-200 dark:border-red-900 text-red-600 dark:text-red-400 p-6 rounded-2xl text-center my-8">
                        <p className="font-semibold">{error}</p>
                        <p className="text-xs mt-1 text-slate-500">Уверете се, че Spring Boot бекендът работи на http://localhost:8080</p>
                    </div>
                )}

                {!loading && !error && (
                    products.length > 0 ? (
                        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
                            {products.map((product) => (
                                <ProductCard
                                    key={product.id}
                                    product={product}
                                    onAddToCart={onAddToCart}
                                    onQuickView={onQuickView}
                                />
                            ))}
                        </div>
                    ) : (
                        <div className="text-center py-16 text-slate-500">
                            Все още няма налични продукти в базата данни.
                        </div>
                    )
                )}
            </main>
        </>
    );
}