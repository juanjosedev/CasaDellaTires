-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-12-2018 a las 23:29:57
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
-- Base de datos: `casadellatires`
--

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
(1001, 'José Miguel', 'Cuesta', '', '', 'Madrid'),
(1002, 'Natalia', 'Cuesta', '', '4575777', 'Madrid'),
(1004, 'Juan', 'Cuesta', '', '4874955', 'Madrid'),
(1008, 'Emilio', 'Delgado', '', '7589476', 'Madrid'),
(1016, 'Lucia', 'Alvarez', '', '1635488', 'Madrid'),
(1024, 'Fernando', 'Navarro', '', '5645247', 'Madrid'),
(1032, 'Bélen', 'López', 'Vásquez', '1023647', 'Madrid'),
(1064, 'Mauricio', 'Hidalgo', '', '2234569', 'Madrid'),
(1128, 'Mariano', 'Delgado', '', '1044544', 'Madrid'),
(1256, 'Vicenta', 'Benito', '', '1455468', 'Madrid'),
(1512, 'Marisa', 'Benito', '', '4568564', 'Madrid'),
(2003, 'Beatriz', 'Villarejo', '', '1445632', 'Madrid'),
(2006, 'Andrés', 'De Fonollosa', '', '7774445', 'Madrid'),
(2012, 'Alicia', 'Sanz', '', '1758776', 'Madrid'),
(2024, 'Concepción', 'De La Fuente', '', '', 'Madrid'),
(2048, 'Francisco', 'Delgado', '', '3654664', 'Madrid'),
(2096, 'Roberto Alonso', 'Castillo', '', '1242311', 'Madrid'),
(2192, 'Silene', 'Oliveira', '', '', 'Burgos'),
(2284, 'Sergio', 'Manquina', '', '', 'Burgos');

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
(0000000001, NULL, NULL, 'Cambio de Aceite', 30000),
(0000000002, NULL, NULL, 'Cambio de Aceite', 20000),
(0000000002, NULL, NULL, 'Balanceo', 23000),
(0000000003, NULL, NULL, 'Tuneado', 88000),
(0000000004, NULL, NULL, 'Cambio de Aceite', 22000),
(0000000004, NULL, NULL, 'Balanceo', 22000),
(0000000005, NULL, NULL, 'Cambio de Aceite', 22000),
(0000000005, NULL, NULL, 'Balanceo', 22000),
(0000000006, NULL, NULL, 'Cambio de Aceite', 50000),
(0000000006, NULL, NULL, 'Balanceo', 36000),
(0000000007, NULL, NULL, 'Cambio de Aceite', 20000),
(0000000007, NULL, NULL, 'Balanceo', 23000);

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
(0000000001, 2012, 'QWE789', '2018-11-29 08:31:00', '2018-11-29 08:48:00', 30000, 0, 30000),
(0000000002, 2006, 'ASD456', '2018-11-29 14:18:00', '2018-11-29 15:03:00', 43000, 0, 43000),
(0000000003, 1016, 'LKJ654', '2018-11-29 11:36:00', '2018-11-29 13:48:00', 88000, 0, 88000),
(0000000004, 1064, 'FGH741', '2018-11-29 15:30:00', '2018-11-29 16:12:00', 44000, 0, 44000),
(0000000005, 1032, 'FGH741', '2018-11-29 20:02:00', '2018-11-29 23:12:00', 44000, 0, 44000),
(0000000006, 2012, 'JHG963', '2018-11-29 09:09:00', NULL, 86000, 0, 86000),
(0000000007, 1016, 'ASD456', '2018-11-29 00:21:49', '2018-11-29 00:22:01', 43000, 0, 43000);

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
(0000000018, 0000000003, 'Cambio de Aceite', 50000),
(0000000019, 0000000006, 'Cambio de Aceite', 22000),
(0000000020, 0000000004, 'Cambio de Aceite', 22000),
(0000000021, 0000000005, 'Cambio de Aceite', 30000),
(0000000022, 0000000001, 'Cambio de Aceite', 28000),
(0000000023, 0000000007, 'Cambio de Aceite', 36000),
(0000000024, 0000000002, 'Cambio de Aceite', 15000),
(0000000025, 0000000012, 'Cambio de Aceite', 20000),
(0000000026, 0000000003, 'Balanceo', 36000),
(0000000027, 0000000006, 'Balanceo', 22000),
(0000000028, 0000000004, 'Balanceo', 22000),
(0000000029, 0000000005, 'Balanceo', 26000),
(0000000030, 0000000001, 'Balanceo', 26000),
(0000000031, 0000000002, 'Balanceo', 18000),
(0000000032, 0000000012, 'Balanceo', 23000),
(0000000033, 0000000003, 'Lavado', 45000),
(0000000034, 0000000006, 'Lavado', 27000),
(0000000035, 0000000004, 'Lavado', 27000),
(0000000036, 0000000005, 'Lavado', 32000),
(0000000037, 0000000001, 'Lavado', 28000),
(0000000038, 0000000007, 'Lavado', 38000),
(0000000039, 0000000002, 'Lavado', 18000),
(0000000040, 0000000005, 'Tuneado', 360000),
(0000000041, 0000000014, 'Tuneado', 88000),
(0000000042, 0000000007, 'Pintura', 120000);

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
(0000000009, 'Bicicleta'),
(0000000003, 'Bus'),
(0000000006, 'Camioneta'),
(0000000004, 'Campero'),
(0000000014, 'Cuatrimoto'),
(0000000005, 'Deportivo'),
(0000000001, 'Furgoneta'),
(0000000007, 'Limusina'),
(0000000002, 'Motocicleta'),
(0000000012, 'Sedán'),
(0000000013, 'Taxis');

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
('Admin', 'f865b53623b121fd34ee5426c792e5c33af8c227', 'Admin', 'Admin', 'Admin', 'Admin', '3014578', 'Serviteca (Envigado)', 'bg-indigo'),
('bososcar', '7c222fb2927d828af22f592134e8932480637c0d', 'Operador', 'Oscar Julián', 'Hernández', '', '30045852', 'El Poblado', 'bg-azul'),
('juanjosecode', 'a7d579ba76398070eae654c30ff153a4c273272a', 'Operador', 'Juan José', 'Gutiérrez', 'González', '3005579573', 'Bello lo más hermoso', 'bg-azul');

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
('ASD456', 0000000012, 'Kia', 'Niro'),
('FGH741', 0000000006, 'Fiat', 'M19'),
('JHG963', 0000000003, 'Marcopolo', ''),
('LKJ654', 0000000014, 'Mini', ''),
('MNB321', 0000000001, 'Ford', ''),
('POI987', 0000000006, 'Mazda', 'CX5'),
('QWE789', 0000000005, 'Porsche', '911'),
('RFV159', 0000000005, 'Tesla', 'Roadster'),
('ZXC123', 0000000012, 'Mazda', 'Mazda6');

--
-- Índices para tablas volcadas
--

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
-- AUTO_INCREMENT de la tabla `servicios`
--
ALTER TABLE `servicios`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT de la tabla `tipos_vehiculos`
--
ALTER TABLE `tipos_vehiculos`
  MODIFY `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Restricciones para tablas volcadas
--

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
