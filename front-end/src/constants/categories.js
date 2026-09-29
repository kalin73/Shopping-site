// Категориите не се връщат от REST API засега (само старата /categories
// Thymeleaf страница ги има) - затова са хардкоднати тук, огледално на
// backend Category.java enum-а и старите Categories.html /products/{id} линкове.
export const CATEGORIES = [
    { id: 1, name: 'Компютри' },
    { id: 2, name: 'Смартфони' },
    { id: 3, name: 'Смарт часовници' },
    { id: 4, name: 'Таблети' },
    { id: 5, name: 'Лаптопи' },
    { id: 6, name: 'Конзоли' },
    { id: 7, name: 'Монитори' },
    { id: 8, name: 'USB флашки' },
];