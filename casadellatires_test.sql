-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-02-2019 a las 03:29:00
-- Versión del servidor: 10.1.35-MariaDB
-- Versión de PHP: 7.2.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `casadellatires_test`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `auditorias`
--

CREATE TABLE `auditorias` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_usuario` varchar(15) NOT NULL,
  `fecha` datetime NOT NULL,
  `modulo` enum('clientes','liquidaciones','precios','servicios','usuarios','vehiculos') NOT NULL,
  `operacion` enum('Crear','Eliminar','Editar') NOT NULL,
  `id_modulo` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `auditorias`
--

INSERT INTO `auditorias` (`id`, `id_usuario`, `fecha`, `modulo`, `operacion`, `id_modulo`) VALUES
(0000000009, 'Admin', '2019-02-27 01:27:41', 'liquidaciones', 'Crear', NULL),
(0000000010, 'Admin', '2019-02-27 13:58:19', 'liquidaciones', 'Crear', NULL),
(0000000011, 'Admin', '2019-02-27 13:58:31', 'liquidaciones', 'Editar', NULL),
(0000000012, 'Admin', '2019-02-27 13:59:20', 'clientes', 'Crear', NULL),
(0000000013, 'Admin', '2019-02-27 13:59:47', 'clientes', 'Editar', NULL),
(0000000014, 'Admin', '2019-02-27 14:00:40', 'vehiculos', 'Crear', NULL),
(0000000015, 'Admin', '2019-02-27 14:06:38', 'vehiculos', 'Crear', NULL),
(0000000016, 'Admin', '2019-02-27 14:07:55', 'precios', 'Eliminar', NULL),
(0000000017, 'Admin', '2019-02-27 14:08:07', 'precios', 'Editar', NULL),
(0000000018, 'Admin', '2019-02-27 14:08:23', 'precios', 'Crear', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `cc` bigint(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `primer_apellido` varchar(30) NOT NULL,
  `segundo_apellido` varchar(30) DEFAULT '',
  `telefono` varchar(15) DEFAULT '',
  `direccion` varchar(30) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`cc`, `nombre`, `primer_apellido`, `segundo_apellido`, `telefono`, `direccion`) VALUES
(1011, 'Emilio', 'Delgado', '', '7589476', 'Córdoba'),
(1012, 'Mariano', 'Delgado', '', '5146245', 'Córdoba'),
(1121, 'Mauricio', 'Hidalgo', '', '2234569', 'Málaga'),
(1311, 'Lucia', 'Alvarez', '', '333333', 'Madrid'),
(1321, 'Bélen', 'López', 'Vásquez', '1023647', 'Provincia de Buenos Aires');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

CREATE TABLE `detalle` (
  `consecutivo_liquidaciones` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_servicios` int(10) UNSIGNED ZEROFILL DEFAULT NULL,
  `id_tipo_vehiculo_servicios` int(10) UNSIGNED ZEROFILL DEFAULT NULL,
  `nombre` varchar(30) NOT NULL,
  `precio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`consecutivo_liquidaciones`, `id_servicios`, `id_tipo_vehiculo_servicios`, `nombre`, `precio`) VALUES
(0000000001, NULL, NULL, 'Lavado', 28),
(0000000001, NULL, NULL, 'AlineacionLuces', 15),
(0000000002, NULL, NULL, 'AlineacionLuces', 20),
(0000000003, NULL, NULL, 'Lavado', 35),
(0000000004, NULL, NULL, 'Lavado', 35),
(0000000004, NULL, NULL, 'AlineacionLuces', 15),
(0000000005, NULL, NULL, 'Lavado', 28),
(0000000006, NULL, NULL, 'Lavado', 28);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `liquidaciones`
--

CREATE TABLE `liquidaciones` (
  `consecutivo` int(10) UNSIGNED ZEROFILL NOT NULL,
  `cc` bigint(20) NOT NULL,
  `placa` varchar(6) NOT NULL,
  `hora_inicio` datetime NOT NULL,
  `hora_final` datetime DEFAULT NULL,
  `subtotal` int(11) NOT NULL,
  `descuento` int(11) NOT NULL,
  `total` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `liquidaciones`
--

INSERT INTO `liquidaciones` (`consecutivo`, `cc`, `placa`, `hora_inicio`, `hora_final`, `subtotal`, `descuento`, `total`) VALUES
(0000000001, 1311, 'QWE789', '2019-02-20 12:23:48', '2019-02-20 13:29:50', 43, 0, 43),
(0000000002, 1011, 'ZXC123', '2019-02-25 14:03:22', '2019-02-25 14:36:53', 20, 0, 20),
(0000000003, 1321, 'POI987', '2019-02-26 11:25:24', '2019-02-26 11:57:56', 35, 0, 35),
(0000000004, 1121, 'MNB321', '2019-02-26 15:13:41', '2019-02-26 17:03:59', 50, 0, 50),
(0000000005, 1121, 'QWE789', '2019-02-27 01:27:39', '2019-02-27 20:22:21', 28, 0, 28),
(0000000006, 1311, 'QWE789', '2019-02-27 20:22:21', NULL, 28, 0, 28);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicios`
--

CREATE TABLE `servicios` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `id_tipo_vehiculo` int(10) UNSIGNED ZEROFILL NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `precio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `servicios`
--

INSERT INTO `servicios` (`id`, `id_tipo_vehiculo`, `nombre`, `precio`) VALUES
(0000000132, 0000000012, 'Lavado', 30),
(0000000133, 0000000004, 'Lavado', 35),
(0000000134, 0000000005, 'Lavado', 32),
(0000000135, 0000000006, 'Lavado', 35),
(0000000136, 0000000012, 'AlineacionLuces', 15),
(0000000137, 0000000004, 'AlineacionLuces', 15),
(0000000138, 0000000005, 'AlineacionLuces', 20),
(0000000139, 0000000006, 'AlineacionLuces', 15),
(0000000151, 0000000012, 'Polichado', 66);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipos_vehiculos`
--

CREATE TABLE `tipos_vehiculos` (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL,
  `nombre` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipos_vehiculos`
--

INSERT INTO `tipos_vehiculos` (`id`, `nombre`) VALUES
(0000000012, 'Automóvil'),
(0000000006, 'Camioneta'),
(0000000004, 'Campero'),
(0000000005, 'Deportivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `usuario` varchar(15) NOT NULL,
  `clave` varchar(100) NOT NULL,
  `tipo` enum('Admin','Operador') NOT NULL DEFAULT 'Operador',
  `nombre` varchar(50) NOT NULL,
  `primer_apellido` varchar(50) NOT NULL,
  `segundo_apellido` varchar(50) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `direccion` varchar(30) NOT NULL,
  `color` enum('bg-rojo','bg-rosa','bg-morado','bg-morado-profundo','bg-indigo','bg-azul','bg-azul-claro','bg-cian','bg-verde-azul','bg-verde','bg-verde-claro','bg-lima','bg-ambar','bg-anaranjado','bg-anaranjado-profundo','bg-gris','bg-gris-azul') NOT NULL DEFAULT 'bg-azul'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`usuario`, `clave`, `tipo`, `nombre`, `primer_apellido`, `segundo_apellido`, `telefono`, `direccion`, `color`) VALUES
('Admin', 'f865b53623b121fd34ee5426c792e5c33af8c227', 'Admin', 'Admin', 'Admin', 'Admin', '3014578', 'Serviteca (Envigado)', 'bg-azul'),
('juanjosedev', '7c222fb2927d828af22f592134e8932480637c0d', 'Operador', 'Juan José', 'Gutiérrez', 'González', '3005579573', 'Bello', 'bg-verde-claro'),
('oscar', '7c222fb2927d828af22f592134e8932480637c0d', 'Operador', 'Oscar', 'Hernandez', '', '684868', 'Poblado', 'bg-azul');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculos`
--

CREATE TABLE `vehiculos` (
  `placa` varchar(6) NOT NULL,
  `id_tipo_vehiculo` int(10) UNSIGNED ZEROFILL NOT NULL,
  `marca` varchar(20) DEFAULT '',
  `modelo` varchar(20) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vehiculos`
--

INSERT INTO `vehiculos` (`placa`, `id_tipo_vehiculo`, `marca`, `modelo`) VALUES
('FGH456', 0000000005, 'Tesla', 'S'),
('MNB321', 0000000004, 'Chevrolet', ''),
('POI987', 0000000006, 'Toyota', ''),
('QWE789', 0000000012, 'Audi', ''),
('ZXC123', 0000000005, 'Mini', '');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `auditorias`
--
ALTER TABLE `auditorias`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`cc`);

--
-- Indices de la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD KEY `id_servicios` (`id_servicios`,`id_tipo_vehiculo_servicios`),
  ADD KEY `id_tipo_vehiculo_servicios` (`id_tipo_vehiculo_servicios`),
  ADD KEY `consecutivo_liquidaciones` (`consecutivo_liquidaciones`);

--
-- Indices de la tabla `liquidaciones`
--
ALTER TABLE `liquidaciones`
  ADD PRIMARY KEY (`consecutivo`),
  ADD KEY `placa` (`placa`,`cc`),
  ADD KEY `cc` (`cc`);

--
-- Indices de la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_tipo_vehiculo` (`id_tipo_vehiculo`);

--
-- Indices de la tabla `tipos_vehiculos`
--
ALTER TABLE `tipos_vehiculos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`usuario`),
  ADD UNIQUE KEY `usuario` (`usuario`);

--
-- Indices de la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD PRIMARY KEY (`placa`),
  ADD KEY `id_tipo_vehiculo` (`id_tipo_vehiculo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `auditorias`
--
ALTER TABLE `auditorias`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `servicios`
--
ALTER TABLE `servicios`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=152;

--
-- AUTO_INCREMENT de la tabla `tipos_vehiculos`
--
ALTER TABLE `tipos_vehiculos`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `auditorias`
--
ALTER TABLE `auditorias`
  ADD CONSTRAINT `auditorias_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`usuario`);

--
-- Filtros para la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD CONSTRAINT `detalle_ibfk_1` FOREIGN KEY (`id_servicios`) REFERENCES `servicios` (`id`),
  ADD CONSTRAINT `detalle_ibfk_2` FOREIGN KEY (`id_tipo_vehiculo_servicios`) REFERENCES `tipos_vehiculos` (`id`),
  ADD CONSTRAINT `detalle_ibfk_3` FOREIGN KEY (`consecutivo_liquidaciones`) REFERENCES `liquidaciones` (`consecutivo`);

--
-- Filtros para la tabla `liquidaciones`
--
ALTER TABLE `liquidaciones`
  ADD CONSTRAINT `liquidaciones_ibfk_1` FOREIGN KEY (`cc`) REFERENCES `clientes` (`cc`),
  ADD CONSTRAINT `liquidaciones_ibfk_2` FOREIGN KEY (`placa`) REFERENCES `vehiculos` (`placa`);

--
-- Filtros para la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD CONSTRAINT `servicios_ibfk_1` FOREIGN KEY (`id_tipo_vehiculo`) REFERENCES `tipos_vehiculos` (`id`);

--
-- Filtros para la tabla `vehiculos`
--
ALTER TABLE `vehiculos`
  ADD CONSTRAINT `vehiculos_ibfk_1` FOREIGN KEY (`id_tipo_vehiculo`) REFERENCES `tipos_vehiculos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
