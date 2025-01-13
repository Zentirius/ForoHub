# **ForoHub** - API REST para Gestión de Tópicos en Foros

### Creado por: Jaime Rossi Serrano

---

## **Descripción del Proyecto**

ForoHub es una API REST desarrollada en Java utilizando Spring Boot. Permite la gestión de tópicos en un foro, incluyendo operaciones CRUD (Crear, Leer, Actualizar y Eliminar), además de autenticación basada en JWT para garantizar un acceso seguro.

---

## **Características Principales**

- **Gestión de Tópicos:** Operaciones CRUD con atributos como título, mensaje, fecha de creación, estado, autor y curso.
- **Autenticación y Seguridad:** Implementación de autenticación basada en JWT con roles de usuario (`ADMIN`, `USER`).
- **Gestión de Cursos:** CRUD para cursos, con categorías predefinidas.
- **Persistencia:** Integración con una base de datos MySQL mediante Spring Data JPA, migración automática de datos y ejecución automática de scripts SQL desde el paquete `config`.
- **Endpoints Seguros:** Restricción de acceso a usuarios autenticados según sus roles.

---

## **Requisitos Previos**

1. **JDK 17** o superior instalado.
2. **MySQL Server** (versión 8.0 o superior).
3. **Maven** para gestionar dependencias.
4. Herramienta para pruebas de API como **Postman** o **Insomnia**.

---

## **Configuración del Proyecto**

### **1. Uso de Variables de Entorno**

ForoHub utiliza variables de entorno para manejar configuraciones sensibles y dinámicas, como las credenciales de la base de datos y claves secretas para JWT.

#### **Qué son las Variables de Entorno**

Las variables de entorno son pares de clave y valor que se configuran en el sistema operativo o en un archivo. En este proyecto, se utilizan para evitar almacenar información sensible directamente en el código fuente, como contraseñas o claves secretas.

#### **Generar una Clave Secreta para JWT**

Es importante generar una clave segura para `JWT_SECRET`. Evita usar valores visibles o fáciles de adivinar. Puedes generar una clave aleatoria en sitios como [Secret Generator](https://www.allkeysgenerator.com/) o herramientas similares. Ejemplo de una clave segura: `F1ndTh3$ecr3tK3y!`

#### **Configuración en Windows**

1. Abre el buscador de Windows y escribe "Variables de entorno".
2. Selecciona "Editar las variables de entorno del sistema".
3. En la ventana que aparece, haz clic en "Variables de entorno".
4. En la sección "Variables de usuario" o "Variables del sistema", haz clic en "Nueva...".
5. Agrega las siguientes variables con sus valores correspondientes:
   - **Nombre de la variable:** `DB_HOST`
     **Valor de la variable:** `127.0.0.1`
   - **Nombre de la variable:** `DB_PORT`
     **Valor de la variable:** `3306`
   - **Nombre de la variable:** `DB_USERNAME`
     **Valor de la variable:** `root`
   - **Nombre de la variable:** `DB_PASSWORD`
     **Valor de la variable:** Una contraseña segura que no sea visible en el código fuente. Usa herramientas como [Password Generator](https://passwordsgenerator.net/) para crearla.
   - **Nombre de la variable:** `JWT_SECRET`
     **Valor de la variable:** Una clave segura generada previamente.
6. Haz clic en "Aceptar" para guardar cada variable y cierra las ventanas.

#### **Configuración en macOS o Linux**

1. Abre el archivo de configuración de tu terminal, como `~/.bashrc`, `~/.zshrc` o `~/.bash_profile`.
2. Agrega las siguientes líneas al final del archivo:
   ```bash
   export DB_HOST=127.0.0.1
   export DB_PORT=3306
   export DB_USERNAME=root
   export DB_PASSWORD=UnaContraseñaSegura123!
   export JWT_SECRET=F1ndTh3$ecr3tK3y!
   ```
3. Guarda el archivo y recarga la configuración ejecutando:
   ```bash
   source ~/.bashrc
   ```
4. Verifica que las variables se hayan configurado correctamente ejecutando:
   ```bash
   echo $DB_HOST
   ```

#### **Cómo Funcionan las Variables en el Proyecto**

En el archivo `application.properties` se referencian estas variables como:

```properties
spring.datasource.url=jdbc:mysql://${DB_HOST:127.0.0.1}:${DB_PORT:3306}/forohub?useSSL=false&serverTimezone=UTC
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:secret}
jwt.secret=${JWT_SECRET:my-super-secure-key}
```

Si no se encuentran definidas las variables de entorno, Spring usará los valores por defecto indicados después de los dos puntos (`:`).

---

### **2. Base de Datos**

1. Crea la base de datos `forohub` en MySQL:

   ```sql
   CREATE DATABASE forohub;
   ```

2. Ejecuta el programa directamente con `mvn spring-boot:run`. Si las tablas de la base de datos se crean correctamente y el programa inicia sin errores, puedes omitir el siguiente paso.

3. **RECUERDA USAR ESTE SIGUIENTE PASO SOLO SI NO SE HICIERON LAS MIGRACIONES AUTOMÁTICAS MUY IMPORTANTE DE RECORDAR!!!** Los scripts SQL necesarios se ejecutan automáticamente desde el paquete `config`. A continuación, se incluye el script como respaldo si es que algo falla:

   ```sql
   -- Script completo SQL para respaldo 
    -- Desactivar verificaciones de claves foráneas y restricciones
    SET FOREIGN_KEY_CHECKS = 0;

    -- Crear tabla usuario
    DROP TABLE IF EXISTS usuario;
    CREATE TABLE usuario (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      password VARCHAR(255) NOT NULL,
      username VARCHAR(255) NOT NULL,
      roles VARCHAR(255) DEFAULT NULL,
      UNIQUE KEY (username)
    );

    -- Crear tabla usuario_roles
    DROP TABLE IF EXISTS usuario_roles;
    CREATE TABLE usuario_roles (
      usuario_id BIGINT NOT NULL,
      role VARCHAR(255) NOT NULL,
      PRIMARY KEY (usuario_id, role),
      FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
    );

    -- Crear tabla curso
    DROP TABLE IF EXISTS curso;
    CREATE TABLE curso (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      categoria ENUM('ESTRATEGIAS', 'GENERAL', 'MECANICAS_AVANZADAS', 'PERSONAJES', 'TECNICAS_JUEGO', 'TORNEOS') NOT NULL,
      nombre VARCHAR(255) NOT NULL,
      UNIQUE KEY (nombre)
    );

    -- Crear tabla topico
    DROP TABLE IF EXISTS topico;
    CREATE TABLE topico (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      categoria ENUM('ESTRATEGIAS', 'GENERAL', 'MECANICAS_AVANZADAS', 'PERSONAJES', 'TECNICAS_JUEGO', 'TORNEOS') NOT NULL,
      fecha_creacion DATETIME(6) NOT NULL,
      mensaje TINYTEXT NOT NULL,
      titulo VARCHAR(100) NOT NULL,
      autor_id BIGINT NOT NULL,
      curso_id BIGINT NOT NULL,
      status ENUM('ACTIVO', 'CERRADO', 'RESUELTO') DEFAULT NULL,
      FOREIGN KEY (autor_id) REFERENCES usuario(id),
      FOREIGN KEY (curso_id) REFERENCES curso(id)
    );

    -- Crear tabla respuesta
    DROP TABLE IF EXISTS respuesta;
    CREATE TABLE respuesta (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      contenido VARCHAR(255) DEFAULT NULL,
      fecha_creacion DATETIME(6) DEFAULT NULL,
      solucion BIT(1) NOT NULL,
      autor_id BIGINT NOT NULL,
      topico_id BIGINT NOT NULL,
      FOREIGN KEY (autor_id) REFERENCES usuario(id),
      FOREIGN KEY (topico_id) REFERENCES topico(id) ON DELETE CASCADE
    );

    -- Crear tabla topico_etiquetas
    DROP TABLE IF EXISTS topico_etiquetas;
    CREATE TABLE topico_etiquetas (
      topico_id BIGINT NOT NULL,
      etiquetas VARCHAR(255) DEFAULT NULL,
      FOREIGN KEY (topico_id) REFERENCES topico(id)
    );

    -- Insertar datos iniciales en usuario con validación
    INSERT INTO usuario (id, password, username, roles) VALUES 
    (1, '$2a$12$MN2tS43sr3Z5H09LabwUJOn7VEuDwKmZsPApxg4mr1RDxYQsDNe2S', 'usuario_prueba', NULL)
    ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    roles = VALUES(roles);

    INSERT INTO usuario (id, password, username, roles) VALUES 
    (2, '$2a$12$2eyu6.XMttOg/qvr/0ixGuGWHkkCseq5XlGXBTeR0J1KrlMNiP8tC', 'usuario', 'ROLE_ADMIN')
    ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    roles = VALUES(roles);

    INSERT INTO usuario (id, password, username, roles) VALUES 
    (4, '$2a$12$e6u6.XMttOg/qvr/6ixGuGHkHkc5eqSXIGXBtEroJ1K1MNPt8C', 'falconmaster', NULL),
    (5, '$2a$12$e6u6.XMttOg/qvr/6ixGuGHkHkc5eqSXIGXBtEroJ1K1MNPt8C', 'zeldaplayer', NULL)
    ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    roles = VALUES(roles);

    -- Eliminar usuario duplicado (admin sin rol)
    DELETE FROM usuario WHERE username = 'admin';

    -- Insertar datos iniciales en usuario_roles con validación
    INSERT INTO usuario_roles (usuario_id, role) VALUES 
    (2, 'ROLE_ADMIN')
    ON DUPLICATE KEY UPDATE 
    role = VALUES(role);

    -- Insertar datos iniciales en curso con validación
    INSERT INTO curso (id, categoria, nombre) VALUES 
    (1, 'ESTRATEGIAS', 'Curso Avanzado de Estrategias'),
    (2, 'PERSONAJES', 'Guía de Personajes Secundarios'),
    (3, 'TECNICAS_JUEGO', 'Técnicas Avanzadas de Juego')
    ON DUPLICATE KEY UPDATE 
    nombre = VALUES(nombre);

    -- Insertar datos iniciales en topico con validación
    INSERT INTO topico (id, categoria, fecha_creacion, mensaje, titulo, autor_id, curso_id, status) VALUES 
    (1, 'ESTRATEGIAS', NOW(), 'Discusión sobre estrategias avanzadas', 'Estrategias de alto nivel', 1, 1, 'ACTIVO'),
    (2, 'PERSONAJES', NOW(), '¿Qué opinas sobre este personaje?', 'Opiniones de personajes', 2, 2, 'ACTIVO')
    ON DUPLICATE KEY UPDATE 
    mensaje = VALUES(mensaje),
    titulo = VALUES(titulo);

    -- Insertar datos iniciales en respuesta con validación
    INSERT INTO respuesta (id, contenido, fecha_creacion, solucion, autor_id, topico_id) VALUES 
    (1, 'Me parece interesante.', NOW(), 0, 2, 1),
    (2, 'Estoy de acuerdo contigo.', NOW(), 0, 3, 1)
    ON DUPLICATE KEY UPDATE 
    contenido = VALUES(contenido);

    -- Insertar datos iniciales en topico_etiquetas con validación
    INSERT INTO topico_etiquetas (topico_id, etiquetas) VALUES 
    (1, 'estrategia'),
    (1, 'avanzado'),
    (2, 'personajes')
    ON DUPLICATE KEY UPDATE 
    etiquetas = VALUES(etiquetas);

    -- Reactivar verificaciones de claves foráneas
    SET FOREIGN_KEY_CHECKS = 1;

   ```

---

### **3. Iniciar el Programa**

El programa puede iniciarse directamente desde la clase principal `ForoApplication`:

```java
package com.example.ForoHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ForoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForoApplication.class, args);
    }
}
```

Para iniciar, ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn spring-boot:run
```

---

## **Endpoints Principales**

### **Dirección Base de la API**

Todos los endpoints deben ser probados en la siguiente dirección base:

```
http://localhost:8080
```

### **Autenticación**

- **POST /auth/login:** Genera un token JWT.
  ```json
  {
    "username": "usuario",
    "password": "password123"
  }
  ```
  - Usa el token recibido en los siguientes endpoints. Para facilitar su uso, en herramientas como Postman o Insomnia, colócalo en la pestaña **Auth**, selecciona el tipo de autenticación "Bearer Token" y pégalo en el campo de token.

### **Cursos**

- **GET /cursos:** Lista todos los cursos.
- **POST /cursos:** Crea un nuevo curso. (Requiere autenticación JWT)
  ```json
  {
    "nombre": "Curso Avanzado de Estrategias",
    "categoria": "ESTRATEGIAS"
  }
  ```
- **PUT /cursos/{id}:** Actualiza un curso por ID. (Requiere autenticación JWT)
  ```json
  {
    "nombre": "Estrategias de Torneos",
    "categoria": "TORNEOS"
  }
  ```
- **DELETE /cursos/{id}:** Elimina un curso por ID. (Requiere rol `ADMIN`)

### **Tópicos**

- **GET /topicos:** Lista todos los tópicos (soporta paginación).
- **GET /topicos/{id}:** Obtiene detalles de un tópico específico.
- **POST /topicos:** Crea un nuevo tópico. (Requiere autenticación JWT)
  ```json
  {
    "titulo": "Estrategias avanzadas",
    "mensaje": "Discutamos estrategias para torneos.",
    "autorUsername": "usuario",
    "cursoNombre": "Estrategias",
    "categoria": "ACTIVO",
    "etiquetas": ["estrategias", "avanzadas"]
  }
  ```
- **PUT /topicos/{id}:** Actualiza un tópico existente. (Requiere autenticación JWT)
  ```json
  {
    "titulo": "Nuevas estrategias",
    "mensaje": "Actualización del contenido del tópico",
    "categoria": "CERRADO",
    "etiquetas": ["nuevas", "actualizado"]
  }
  ```
- **DELETE /topicos/{id}:** Elimina un tópico por ID. (Requiere rol `ADMIN`)

---

## **Pruebas de la API**

Recomendamos usar herramientas como Postman o Insomnia para probar los endpoints. Pasos:

1. Genera un token JWT con el endpoint `/auth/login`.
2. Usa el token como Bearer Token en la pestaña **Auth** para simplificar las solicitudes.
3. Realiza solicitudes a los endpoints protegidos usando la dirección base:
   ```
   http://localhost:8080
   ```

---

## **Autor**

**Jaime Rossi Serrano**

