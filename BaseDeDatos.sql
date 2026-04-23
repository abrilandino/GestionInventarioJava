CREATE DATABASE IF NOT EXISTS inventario_db;
USE inventario_db;

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    precio DOUBLE,
    stock INT
);

-- inserta datos para que no se vea vacia en la presentacion
INSERT INTO productos (nombre, precio, stock) VALUES ('laptop', 15000, 10);
INSERT INTO productos (nombre, precio, stock) VALUES ('mouse', 350, 25);
