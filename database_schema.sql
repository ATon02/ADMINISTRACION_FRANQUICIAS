-- =====================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS - POSTGRESQL
-- ADMINISTRACIÓN DE FRANQUICIAS
-- =====================================================

-- Crear tabla de franquicias
CREATE TABLE IF NOT EXISTS franchises (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Crear tabla de sucursales
CREATE TABLE IF NOT EXISTS branches (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    franchise_id BIGINT
);

-- Crear tabla de productos
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    stock BIGINT,
    branch_id BIGINT
);

