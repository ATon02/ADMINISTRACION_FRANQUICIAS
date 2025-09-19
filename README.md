# Franchises Management API

Una API reactiva construida con Spring WebFlux, Java 17 y MySQL para la persistencia. Este proyecto permite gestionar franquicias, sucursales y productos, y puede ser desplegado localmente o utilizando Docker.

## Requisitos

- **Java 17**
- **Maven 3.x**
- **MySQL 8.x** (o compatible)
- **Docker** (opcional, para despliegue en contenedores)

### Configuración de la base de datos

1. Crear un esquema llamado `franchises_management` en MySQL.
2. Declarar las siguientes variables de entorno:
   - `SPRING_R2DBC_URL`: La URL de conexión R2DBC para MySQL.
   - `SPRING_R2DBC_USERNAME`: Nombre de usuario de la base de datos.
   - `SPRING_R2DBC_PASSWORD`: Contraseña de la base de datos.

---

## Pasos para el despliegue con Docker

1. Generar el archivo JAR del proyecto:
   ```bash
   mvn clean install -DskipTests
   ```
2. Construir la imagen de Docker:
    docker build -t franchises-management-app .
3. Ejecutar el contenedor de la aplicación:
    docker run --name franchises-app \
        -e SPRING_R2DBC_URL=r2dbc:mysql://<HOST_MYSQL>:3306/franchises_management \
        -e SPRING_R2DBC_USERNAME=<USUARIO_MYSQL> \
        -e SPRING_R2DBC_PASSWORD=<CONTRASEÑA_MYSQL> \
        -p 8080:8080 franchises-management-app

Modelos
    Modelo de Franquicia

        {
            "name": "Franchise name" // String - No deben existir franquicias con el mismo nombre.
        }
    Modelo de Sucursal
        {
            "name": "Branch name",   // String
            "franchiseId": 1         // Long - Debe existir una franquicia con este ID.
        }
    Modelo de Producto
        {
            "name": "Product name",  // String
            "stock": 1,              // Long
            "branchId": 1            // Long - Debe existir una sucursal con este ID.
        }

