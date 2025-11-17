# Base_De_Datos_Backend

Este es el proyecto backend para la aplicación Lvl Up, construido con Spring Boot.



1.  Clonar el repositorio de git .
2.  Asegurarse de tener Java 21 instalado.
3.  El proyecto se conecta a una base de datos AWS RDS. Las credenciales están en `application.properties`.
4.  Ejecutar la aplicación desde `BaseDatosApplication.java`.

 Endpoints Principales

* `POST /api/users/login`: Autenticación de usuarios.
* `GET /api/products`: Obtiene todos los productos.
* `POST /api/carrito/add`: Agrega un item al carrito.