# Microservices Project - User Service & Order Service

## Descripción
Este proyecto contiene dos microservicios Spring Boot que se comunican entre sí mediante API REST.

## Servicios
- **User Service**: Puerto 8081 - Gestión de usuarios
- **Order Service**: Puerto 8082 - Gestión de pedidos (depende del User Service)

## Requisitos
- Java 17+
- Maven 3.6+

## Ejecución
Nota: Ejecutar los servicios en terminal deferente

### 1. Ejecutar User Service
```bash
mvn spring-boot:run
cd user-service
```

### 2. Ejecutar Order Servicio
```bash
mvn spring-boot:run
cd order-service
```

## Endpoints
User Service (8081)

    GET /api/users - Listar todos los usuarios
    GET /api/users/{id} - Obtener usuario por ID
    POST /api/users - Crear usuario
    GET /api/users/email/{email} - Buscar usuario por email

Order Service (8082)

    GET /api/orders - Listar todos los pedidos
    GET /api/orders/{id} - Obtener pedido por ID
    POST /api/orders - Crear pedido (verifica usuario)
    GET /api/orders/user/{userId} - Pedidos por usuario
    GET /api/orders/{id}/user-info - Pedido con info de usuario