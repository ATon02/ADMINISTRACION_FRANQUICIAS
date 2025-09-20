# Sistema de Administración de Franquicias

Una aplicación Spring Boot WebFlux construida con Clean Architecture para gestionar franquicias, sucursales y productos con programación reactiva.

## 🏗️ Arquitectura

Este proyecto implementa **Clean Architecture** con programación reactiva utilizando Spring WebFlux y R2DBC.

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## 🚀 Características

### **Funcionalidades del Sistema:**
- ✅ **Gestión de Franquicias**: Crear y actualizar nombres
- ✅ **Gestión de Sucursales**: Crear y actualizar nombres por franquicia
- ✅ **Gestión de Productos**: CRUD completo con control de stock
- ✅ **Consulta Optimizada**: Obtener producto con mayor stock por sucursal de una franquicia

### **Características Técnicas:**
- ✅ **Programación Reactiva** con Spring WebFlux y Project Reactor
- ✅ **Base de datos reactiva** con R2DBC + PostgreSQL
- ✅ **Clean Architecture** con separación de capas
- ✅ **Documentación API** con OpenAPI/Swagger
- ✅ **Tests de alta calidad** con 95% cobertura de mutaciones
- ✅ **Validaciones robustas** y manejo de errores

## 📋 Requisitos Previos

- **Java 17** 
- **PostgreSQL** 
- **Gradle 8.x** (incluido wrapper)

## 🗄️ Configuración de Base de Datos

1. **Crear la base de datos PostgreSQL:**
   ```sql
   CREATE DATABASE administracion_franquicias;
   ```

2. **Ejecutar el script de esquema:**
   ```bash
   psql -d administracion_franquicias -f database_schema.sql
   ```

3. **Configurar conexión en `application.yml`:**
   ```yaml
   spring:
     r2dbc:
       url: r2dbc:postgresql://localhost:5432/administracion_franquicias
       username: tu_usuario
       password: tu_password
   ```

## 🛠️ Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone <repository-url>
   cd ADMINISTRACION_FRANQUICIAS
   ```

2. **Compilar el proyecto:**
   ```bash
   ./gradlew build
   ```

3. **Ejecutar tests:**
   ```bash
   ./gradlew test
   ```

4. **Ejecutar la aplicación:**
   ```bash
   ./gradlew bootRun
   ```

5. **Acceder a la documentación API:**
   - Swagger UI: http://localhost:8080/webjars/swagger-ui/index.html#/

## 📁 Estructura del Proyecto

```
├── applications/
│   └── app-service/                 # Configuración principal de la aplicación
├── domain/
│   ├── model/                       # Entidades del dominio y gateways
│   │   ├── franchise/
│   │   ├── branch/
│   │   └── product/
│   └── usecase/                     # Casos de uso y lógica de negocio
│       ├── franchise/
│       ├── branch/
│       ├── product/
│       └── dtos/                    # DTOs de respuesta
├── infrastructure/
│   ├── driven-adapters/
│   │   └── r2dbc-postgresql/        # Adaptador de base de datos reactiva
│   └── entry-points/
│       └── reactive-web/            # Controllers y configuración web
└── database_schema.sql              # Script de creación de BD
```

## Infrastructure

### Helpers

En el apartado de helpers tendremos utilidades generales para los Driven Adapters y Entry Points.

Estas utilidades no están arraigadas a objetos concretos, se realiza el uso de generics para modelar comportamientos
genéricos de los diferentes objetos de persistencia que puedan existir, este tipo de implementaciones se realizan
basadas en el patrón de diseño [Unit of Work y Repository](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

Estas clases no puede existir solas y debe heredarse su compartimiento en los **Driven Adapters**

### Driven Adapters

Los driven adapter representan implementaciones externas a nuestro sistema, como lo son conexiones a servicios rest,
soap, bases de datos, lectura de archivos planos, y en concreto cualquier origen y fuente de datos con la que debamos
interactuar.

### Entry Points

Los entry points representan los puntos de entrada de la aplicación o el inicio de los flujos de negocio.

## Application

Este módulo es el más externo de la arquitectura, es el encargado de ensamblar los distintos módulos, resolver las dependencias y crear los beans de los casos de use (UseCases) de forma automática, inyectando en éstos instancias concretas de las dependencias declaradas. Además inicia la aplicación (es el único módulo del proyecto donde encontraremos la función “public static void main(String[] args)”.

**Los beans de los casos de uso se disponibilizan automaticamente gracias a un '@ComponentScan' ubicado en esta capa.**
