import axios from 'axios';

// Относителен baseURL - Vite прокси-ва към Spring Boot (виж vite.config.js),
// за да избегнем CORS проблеми в development
const API = axios.create({
    baseURL: '/api',
    withCredentials: true, // Нужно за Spring Security сесии / кукита
    headers: {
        'Content-Type': 'application/json',
    },
});

/* ==========================================================================
   1. ПРОДУКТИ (Products Endpoints)
   ========================================================================== */

export const getAllProducts = async () => {
    const response = await API.get('/products');
    return response.data;
};

export const getProductsByCategory = async (catId) => {
    const response = await API.get(`/products/${catId}`);
    return response.data;
};

export const getProductById = async (id) => {
    const response = await API.get(`/product/info/${id}`);
    return response.data;
};

/* ==========================================================================
   2. АВТЕНТИКАЦИЯ (Auth Endpoints)
   ========================================================================== */

/**
 * Вход в системата (Login)
 * Бекендът НЕ е JSON REST ендпойнт - използва Spring Security form login:
 * POST /login (root, не е под /api), form-urlencoded, полета "email" и "password"
 * (виж SecurityConfiguration: usernameParameter("email").passwordParameter("password"))
 * Успех -> 302 redirect към "/", следван автоматично (крайният статус е 200).
 * Грешка -> сървърът прави forward към /auth/login-error, който връща 401/400 директно.
 */
export const loginUser = async ({ email, password }) => {
    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    try {
        await axios.post('/login', params, { withCredentials: true });
        return { success: true };
    } catch (err) {
        throw new Error(
            err.response?.status === 401
                ? 'Грешен имейл или парола.'
                : 'Възникна грешка при вход. Опитайте отново.'
        );
    }
};

/**
 * Регистрация на нов потребител
 * POST: /api/auth/register (реален ендпойнт, вече съвпада коректно)
 */
export const registerUser = async (userData) => {
    const response = await API.post('/auth/register', userData);
    return response.data;
};

/**
 * Изход от системата
 * POST /logout (root, конфигуриран директно в SecurityConfiguration)
 */
export const logoutUser = async () => {
    await axios.post('/logout', null, { withCredentials: true });
};

/**
 * URL за старт на Google вход (Spring Security OAuth2 Client конвенция).
 * ВАЖНО: изисква бекендът да има spring-boot-starter-oauth2-client
 * и регистриран "google" client - все още НЕ е направено в бекенда.
 */
export const getGoogleLoginUrl = () => '/oauth2/authorization/google';

export default API;