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
