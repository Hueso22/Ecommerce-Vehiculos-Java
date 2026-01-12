CREATE DATABASE IF NOT EXISTS ecommerce_db;
USE ecommerce_db;

CREATE TABLE IF NOT EXISTS vehiculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    anio INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    color VARCHAR(30),
    tipo VARCHAR(20),
    estado VARCHAR(20),
    imagen_url VARCHAR(255),
    descripcion TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    eliminado BOOLEAN DEFAULT FALSE 
);

-- Insertamos datos de prueba
INSERT INTO vehiculos (marca, modelo, anio, precio, color, tipo, estado, imagen_url, descripcion) VALUES 
('Toyota', 'Corolla', 2024, 25000.00, 'Blanco', 'Sedan', 'NUEVO', '', 'Motor híbrido, excelente rendimiento.'),
('Kia', 'Sportage', 2023, 28500.00, 'Negro', 'SUV', 'USADO', '', 'Poco kilometraje, único dueño.'),
('Hyundai', 'Tucson', 2024, 29990.00, 'Gris Metalico', 'SUV', 'NUEVO', '', 'Versión Full Equipo con techo panorámico.');