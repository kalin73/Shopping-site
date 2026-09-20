import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
  ],
  server: {
    proxy: {
      // REST API (продукти, регистрация...)
      '/api': { target: 'http://localhost:8080', changeOrigin: true },
      // Spring Security form login/logout - НЕ са под /api
      '/login': { target: 'http://localhost:8080', changeOrigin: true },
      '/logout': { target: 'http://localhost:8080', changeOrigin: true },
      // Google OAuth2 вход (изисква бекенд конфигурация - виж бележките)
      '/oauth2': { target: 'http://localhost:8080', changeOrigin: true },
    },
  },
})