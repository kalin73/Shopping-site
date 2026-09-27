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
    const response = await API.get(`/products/info/${id}`);
    return response.data;
};

/* ==========================================================================
   2. АВТЕНТИКАЦИЯ (Auth Endpoints)
   ========================================================================== */

/**
 * Вход в системата (Login)
 * POST /auth/login, form-urlencoded, полета "email" и "password"
 * ВАЖНО: Spring Security отговаря с 302 redirect към АБСОЛЮТЕН URL
 * (http://localhost:8080/...) при успех, а не относителен път. axios/XHR следва
 * редиректи автоматично, което го превръща в истинска cross-origin заявка и CORS
 * я блокира (CORS е изключен в бекенда). Затова тук се ползва fetch() с
 * redirect: 'manual' - НЕ следваме редиректа; "opaqueredirect" = успешен вход.
 * Грешка -> сървърът прави forward (не redirect) към /auth/login-error,
 * който връща 401/400 директно, без пренасочване - там няма CORS проблем.
 */
export const loginUser = async ({ email, password }) => {
    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    const response = await fetch('/auth/login', {
        method: 'POST',
        body: params,
        credentials: 'include',
        redirect: 'manual',
    });

    if (response.type === 'opaqueredirect' || response.ok) {
        return { success: true };
    }

    throw new Error(
        response.status === 401
            ? 'Грешен имейл или парола.'
            : 'Възникна грешка при вход. Опитайте отново.'
    );
};

/**
 * Регистрация на нов потребител
 * POST: /api/auth/register
 * Бекендът не връща структурирани грешки (липсва @Valid + global handler),
 * затова 500 се третира като "вероятно вече регистриран имейл" - предположение, не гаранция.
 */
export const registerUser = async (userData) => {
    try {
        const response = await API.post('/auth/register', userData);
        return response.data;
    } catch (err) {
        throw new Error(
            err.response?.status === 500
                ? 'Регистрацията не бе успешна. Възможно е този имейл вече да е регистриран.'
                : 'Възникна грешка при регистрацията. Опитайте отново.'
        );
    }
};

/**
 * Изход от системата
 * POST /logout - същият проблем като login (logoutSuccessUrl генерира абсолютен
 * cross-origin redirect), затова и тук fetch() с redirect: 'manual'.
 */
export const logoutUser = async () => {
    await fetch('/logout', {
        method: 'POST',
        credentials: 'include',
        redirect: 'manual',
    });
};

/**
 * Текущо логнат потребител
 * GET /api/user - вече е SECURED_ENDPOINT. За неавтентикиран потребител Spring
 * Security пренасочва (абсолютен cross-origin redirect) към /auth/login, преди
 * заявката изобщо да стигне контролера - същият CORS проблем както при login,
 * затова и тук fetch() с redirect: 'manual'.
 */
export const getCurrentUser = async () => {
    const response = await fetch('/api/user', {
        credentials: 'include',
        redirect: 'manual',
    });

    if (response.type === 'opaqueredirect' || !response.ok) {
        return null;
    }

    return response.json();
};

/**
 * URL за старт на Google вход (Spring Security OAuth2 Client конвенция).
 * ВАЖНО: изисква бекендът да има spring-boot-starter-oauth2-client
 * и регистриран "google" client - все още НЕ е направено в бекенда.
 */
export const getGoogleLoginUrl = () => '/oauth2/authorization/google';

export default API;