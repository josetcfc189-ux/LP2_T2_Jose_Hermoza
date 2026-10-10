-- Datos ficticios de anime. Misma estructura de la evaluación.
-- Eliminar la base de datos si existe
DROP DATABASE IF EXISTS `anime_compras_t2`;
-- Crear la base de datos
CREATE DATABASE `anime_compras_t2` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `anime_compras_t2`;

-- CREACIÓN DE TABLAS
-- Tabla rubro
CREATE TABLE `tbl_rubro` (
  `id_rubro` INT AUTO_INCREMENT PRIMARY KEY,
  `denominacion` VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

INSERT INTO `tbl_rubro` (`denominacion`) VALUES
  ('Ropa de anime'),
  ('Figuras coleccionables'),
  ('Accesorios de anime'),
  ('Manga y decoración'),
  ('Cosplay');

-- Tabla proveedor
CREATE TABLE `tbl_proveedor` (
  `id_proveedor` INT AUTO_INCREMENT PRIMARY KEY,
  `ruc` CHAR(11) NOT NULL CHECK (`ruc` REGEXP '^20[0-9]{9}$'),
  `razon_social` VARCHAR(50) NOT NULL UNIQUE,
  `fecha_registro` DATETIME NOT NULL,
  `direccion` VARCHAR(100) NOT NULL,
  `pagina_web` VARCHAR(100) NULL CHECK (`pagina_web` LIKE 'www.%'),
  `id_rubro` INT,
  FOREIGN KEY (`id_rubro`) REFERENCES `tbl_rubro`(`id_rubro`)
) ENGINE=InnoDB;

INSERT INTO `tbl_proveedor` (
  `ruc`, `razon_social`, `fecha_registro`, `direccion`, `pagina_web`, `id_rubro`
) VALUES
  ('20345678901', 'Konoha Merch S.A.C.', '2023-01-10', 'Av. Los Olivos 123, Lima', 'www.konoha.example', 1),
  ('20456789012', 'Uzumaki Store S.A.C.', '2022-11-15', 'Calle Las Palmas 456, Lima', 'www.uzumaki.example', 1),
  ('20691357902', 'Demon Slayer Cosplay S.A.C.', '2022-10-10', 'Av. Villarán 88, Lima', 'www.demonslayer.example', 5),
  ('20678901234', 'Saiyan Figures S.A.C.', '2023-04-05', 'Av. Javier Prado 1000, Lima', 'www.saiyan.example', 2),
  ('20789012345', 'La Casa de la Accesorios de anime', '2022-10-20', 'Calle Los Álamos 45, Cusco', 'www.capsule.example', 3),
  ('20890123456', 'One Piece Wear S.A.C.', '2021-06-10', 'Av. Brasil 2500, Trujillo', 'www.onepiece.example', 1),
  ('20901234567', 'Akatsuki Fashion S.A.C.', '2023-02-14', 'Calle El Sol 200, Piura', 'www.akatsuki.example', 1),
  ('20012345678', 'Titan Figures S.A.C.', '2023-03-18', 'Jr. Bolívar 345, Lima', 'www.titan.example', 2),
  ('20123456789', 'Jujutsu Merch S.A.C.', '2022-05-30', 'Av. Pardo 88, Arequipa', 'www.jujutsu.example', 3),
  ('20234567890', 'Manga Paradise S.A.C.', '2022-08-12', 'Calle Real 1020, Callao', 'www.manga.example', 4);

-- Tabla orden_compra
CREATE TABLE `tbl_orden_compra` (
  `nro_orden` INT AUTO_INCREMENT PRIMARY KEY,
  `id_proveedor` INT NOT NULL,
  `monto` DECIMAL(10,2) NOT NULL,
  `direccion_entrega` VARCHAR(255) NOT NULL,
  `fecha_entrega` DATE NOT NULL,
  `estado` CHAR(1) DEFAULT 'P' CHECK (`estado` IN ('P','C','A')), -- A: Pendiente, C: Completado, A: Anulado
  FOREIGN KEY (`id_proveedor`) REFERENCES `tbl_proveedor`(`id_proveedor`)
) ENGINE=InnoDB;

INSERT INTO `tbl_orden_compra` (
  `id_proveedor`, `monto`, `direccion_entrega`, `fecha_entrega`, `estado`
) VALUES
  (3, 675.48, 'Av. Pardo 88, Arequipa', '2025-05-10', 'P'),
  (7, 122.51, 'Calle Las Palmas 456, Lima', '2023-05-12', 'C'),
  (2, 347.53, 'Av. Los Olivos 123, Lima', '2023-05-15', 'C'),
  (4, 300.89, 'Calle El Sol 200, Piura', '2025-05-08', 'P'),
  (1, 762.82, 'Av. Brasil 2500, Trujillo', '2023-05-14', 'C'),
  (9, 709.03, 'Calle Real 1020, Callao', '2023-05-16', 'C'),
  (6, 902.96, 'Jr. Bolívar 345, Lima', '2025-05-11', 'P'),
  (8, 178.24, 'Av. Villarán 88, Lima', '2025-05-18', 'A'),
  (10, 479.73, 'Av. Pardo 88, Arequipa', '2023-05-10', 'C'),
  (3, 126.82, 'Calle Las Palmas 456, Lima', '2023-05-13', 'C'),
  (5, 296.77, 'Av. Los Olivos 123, Lima', '2025-05-07', 'P'),
  (2, 554.82, 'Av. Los Álamos 45, Cusco', '2023-05-12', 'C'),
  (4, 123.88, 'Av. Brasil 2500, Trujillo', '2023-05-10', 'C'),
  (7, 278.95, 'Calle El Sol 200, Piura', '2025-05-13', 'P'),
  (1, 684.90, 'Calle Real 1020, Callao', '2023-05-17', 'C'),
  (9, 590.45, 'Jr. Bolívar 345, Lima', '2023-05-19', 'C'),
  (10, 298.40, 'Av. Villarán 88, Lima', '2025-05-06', 'P'),
  (6, 630.34, 'Calle Las Palmas 456, Lima', '2023-05-08', 'C');
