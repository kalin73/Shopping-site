import axios from 'axios';

// Базова конфигурация за връзка със Spring Boot бекенда (localhost:8080)
const API = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: true, // Нужно за Spring Security сесии / кукита
    headers: {
        'Content-Type': 'application/json',
    },
});

/* ==========================================================================
   1. ПРОДУКТИ (Products Endpoints)
   ========================================================================== */

/**
 * Взима всички продукти
 * GET: /api/product
 */
export const getAllProducts = async () => {
    const response = await API.get('/products');
    return response.data;
};

/**
 * Взима продукти от специфична категория
 * GET: /api/product/{catId}
 */
export const getProductsByCategory = async (catId) => {
    const response = await API.get(`/products/${catId}`);
    return response.data;
};

/**
 * Взима подробна информация за конкретен продукт
 * GET: /api/product/info/{id}
 */
export const getProductById = async (id) => {
    const response = await API.get(`/product/info/${id}`);
    return response.data;
};

/* ==========================================================================
   2. АВТЕНТИКАЦИЯ (Auth Endpoints)
   ========================================================================== */

/**
 * Вход в системата (Login)
 * POST: /api/auth/login (или модифицирай пътя според твоя Spring Controller)
 */
export const loginUser = async (credentials) => {
    // credentials: { username/email, password }
    const response = await API.post('/auth/login', credentials);
    return response.data;
};

/**
 * Регистрация на нов потребител
 * POST: /api/auth/register
 */
export const registerUser = async (userData) => {
    // userData: { username, email, password }
    const response = await API.post('/auth/register', userData);
    return response.data;
};

/**
 * Изход от системата (Logout)
 * POST: /api/auth/logout
 */
export const logoutUser = async () => {
    const response = await API.post('/auth/logout');
    return response.data;
};

export default API;