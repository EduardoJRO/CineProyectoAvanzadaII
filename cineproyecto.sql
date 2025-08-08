-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cineproyecto
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `boletos`
--

DROP TABLE IF EXISTS `boletos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `boletos` (
  `id_boleto` int NOT NULL AUTO_INCREMENT,
  `idfuncion` int NOT NULL,
  `idcompra` int NOT NULL,
  `asiento` varchar(4) NOT NULL,
  `precio_final` decimal(6,2) NOT NULL,
  PRIMARY KEY (`id_boleto`),
  KEY `idcompra` (`idcompra`),
  KEY `idx_boletos_funcion` (`idfuncion`),
  CONSTRAINT `boletos_ibfk_1` FOREIGN KEY (`idfuncion`) REFERENCES `funciones` (`idfunciones`),
  CONSTRAINT `boletos_ibfk_2` FOREIGN KEY (`idcompra`) REFERENCES `compras` (`idcompras`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boletos`
--

LOCK TABLES `boletos` WRITE;
/*!40000 ALTER TABLE `boletos` DISABLE KEYS */;
INSERT INTO `boletos` VALUES (1,1,1,'A12',75.00),(2,2,2,'B05',120.00),(3,3,3,'C08',60.00),(4,4,4,'D03',150.00);
/*!40000 ALTER TABLE `boletos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clasificacion`
--

DROP TABLE IF EXISTS `clasificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clasificacion` (
  `idclasificacion` int NOT NULL AUTO_INCREMENT,
  `clasificacion` varchar(30) NOT NULL,
  PRIMARY KEY (`idclasificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clasificacion`
--

LOCK TABLES `clasificacion` WRITE;
/*!40000 ALTER TABLE `clasificacion` DISABLE KEYS */;
INSERT INTO `clasificacion` VALUES (1,'A - Todo público'),(2,'B - Mayores de 12 años'),(3,'B15 - Mayores de 15 años'),(4,'C - Mayores de 18 años'),(5,'D - Películas para adultos'),(6,'AA - Para niños'),(7,'A+ - Con supervisión'),(8,'B12 - 12 años con guía'),(9,'C15 - 15 años con guía'),(10,'D18 - Estricto 18+'),(11,'E - Educativo'),(12,'F - Familiar');
/*!40000 ALTER TABLE `clasificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idcliente` varchar(20) NOT NULL,
  `nombre_cliente` varchar(50) NOT NULL,
  `correo_cliente` varchar(50) DEFAULT NULL,
  `telefono_cliente` varchar(9) DEFAULT NULL,
  `fecha_registro` date NOT NULL,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES ('0123456789456','Roberto Mendoza','roberto.mendoza@email.com','5590-2376','2023-01-15'),('0234567890342','Laura Sánchez','laura.sanchez@email.com','5690-3422','2023-02-20'),('0345678901345','Pedro Gómez','pedro.gomez@email.com','4542-3444','2023-03-10'),('0456789012375','Sofía Ramírez','sofia.ramirez@email.com','5356-6789','2023-04-05');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras` (
  `idcompras` int NOT NULL AUTO_INCREMENT,
  `idcliente` varchar(20) NOT NULL,
  `idempleado` varchar(20) NOT NULL,
  `fecha_compra` date DEFAULT NULL,
  PRIMARY KEY (`idcompras`),
  KEY `idempleado` (`idempleado`),
  KEY `idx_compras_cliente` (`idcliente`),
  KEY `idx_compras_fecha` (`fecha_compra`),
  CONSTRAINT `compras_ibfk_1` FOREIGN KEY (`idcliente`) REFERENCES `clientes` (`idcliente`),
  CONSTRAINT `compras_ibfk_2` FOREIGN KEY (`idempleado`) REFERENCES `empleados` (`id_empleado`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
INSERT INTO `compras` VALUES (1,'0123456789456','0512345678567','2023-11-14'),(2,'0234567890342','0612345678990','2023-11-14'),(3,'0345678901345','0812345678897','2023-11-15'),(4,'0456789012375','1312345678322','2023-11-15');
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_compras`
--

DROP TABLE IF EXISTS `detalle_compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_compras` (
  `iddetalle` int NOT NULL AUTO_INCREMENT,
  `idcompra` int NOT NULL,
  `idproducto` bigint NOT NULL,
  `cantidad` int NOT NULL,
  `precio_unitario` decimal(6,2) NOT NULL,
  PRIMARY KEY (`iddetalle`),
  KEY `idcompra` (`idcompra`),
  KEY `idproducto` (`idproducto`),
  CONSTRAINT `detalle_compras_ibfk_1` FOREIGN KEY (`idcompra`) REFERENCES `compras` (`idcompras`),
  CONSTRAINT `detalle_compras_ibfk_2` FOREIGN KEY (`idproducto`) REFERENCES `productos` (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_compras`
--

LOCK TABLES `detalle_compras` WRITE;
/*!40000 ALTER TABLE `detalle_compras` DISABLE KEYS */;
INSERT INTO `detalle_compras` VALUES (1,1,7501055300054,1,100.00),(2,2,7501055300016,2,45.00),(3,3,7501055300047,3,20.00),(4,4,7501055300061,1,180.00);
/*!40000 ALTER TABLE `detalle_compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados` (
  `id_empleado` varchar(20) NOT NULL,
  `nombre_empleado` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `telefono_empleado` varchar(9) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `idpuesto` int NOT NULL,
  `id_usuario` int DEFAULT NULL,
  PRIMARY KEY (`id_empleado`),
  KEY `idpuesto` (`idpuesto`),
  KEY `fk_empleado_usuario` (`id_usuario`),
  CONSTRAINT `empleados_ibfk_1` FOREIGN KEY (`idpuesto`) REFERENCES `puestos` (`idpuesto`),
  CONSTRAINT `fk_empleado_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES ('0423456789967','Juan Pérez','5551-2312',1,1),('0512345678567','María López','5151-2542',2,2),('0612345678990','Carlos Ruiz','3451-2212',3,3),('0712345678458','Ana García','5491-7812',4,4),('0812345678897','Luis Torres','5001-1111',5,5),('0912345678455','Elena Díaz','5001-2222',6,6),('1012345678345','Manuel Herrera','5001-3333',7,7),('1112345678005','Lucía Vega','5001-4444',8,8),('1212345678003','Raúl Rivera','5001-5555',9,9),('1312345678322','Silvia Cruz','5001-6666',10,10),('1412345678567','Ismael Soto','5001-7777',11,11),('1512345678449','Gabriela Méndez','5001-8888',12,12);
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funciones`
--

DROP TABLE IF EXISTS `funciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funciones` (
  `idfunciones` int NOT NULL AUTO_INCREMENT,
  `idpelicula` int NOT NULL,
  `fecha` datetime NOT NULL,
  `hora_inicio` time NOT NULL,
  `idsalas` int NOT NULL,
  `precio_unitario` decimal(6,2) NOT NULL,
  PRIMARY KEY (`idfunciones`),
  KEY `idx_funciones_pelicula` (`idpelicula`),
  KEY `idx_funciones_sala` (`idsalas`),
  CONSTRAINT `funciones_ibfk_1` FOREIGN KEY (`idsalas`) REFERENCES `salas` (`idsalas`),
  CONSTRAINT `funciones_ibfk_2` FOREIGN KEY (`idpelicula`) REFERENCES `pelicula` (`idpelicula`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funciones`
--

LOCK TABLES `funciones` WRITE;
/*!40000 ALTER TABLE `funciones` DISABLE KEYS */;
INSERT INTO `funciones` VALUES (1,1,'2023-11-15 00:00:00','18:00:00',1,75.00),(2,2,'2023-11-15 00:00:00','20:30:00',5,120.00),(3,3,'2023-11-16 00:00:00','16:00:00',2,60.00),(4,4,'2023-11-16 00:00:00','22:00:00',3,150.00),(5,5,'2023-11-17 00:00:00','19:30:00',4,200.00),(6,6,'2023-11-18 00:00:00','14:00:00',6,130.00),(7,7,'2023-11-18 00:00:00','16:00:00',7,140.00),(8,8,'2023-11-19 00:00:00','17:30:00',8,190.00),(9,9,'2023-11-19 00:00:00','20:00:00',9,210.00),(10,10,'2023-11-20 00:00:00','15:30:00',10,160.00),(11,11,'2023-11-20 00:00:00','18:30:00',11,170.00),(12,12,'2023-11-21 00:00:00','19:00:00',12,150.00);
/*!40000 ALTER TABLE `funciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genero`
--

DROP TABLE IF EXISTS `genero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genero` (
  `idgenero` int NOT NULL AUTO_INCREMENT,
  `genero` varchar(30) NOT NULL,
  PRIMARY KEY (`idgenero`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genero`
--

LOCK TABLES `genero` WRITE;
/*!40000 ALTER TABLE `genero` DISABLE KEYS */;
INSERT INTO `genero` VALUES (1,'Acción'),(2,'Aventura'),(3,'Comedia'),(4,'Drama'),(5,'Terror'),(6,'Ciencia Ficción'),(7,'Romance'),(8,'Animación'),(9,'Documental'),(10,'Fantasía'),(11,'Crimen'),(12,'Suspenso');
/*!40000 ALTER TABLE `genero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `idiomas`
--

DROP TABLE IF EXISTS `idiomas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `idiomas` (
  `id_idioma` int NOT NULL AUTO_INCREMENT,
  `nombre_idioma` varchar(50) NOT NULL,
  `codigo_idioma` varchar(5) NOT NULL,
  PRIMARY KEY (`id_idioma`),
  UNIQUE KEY `idx_idioma_unico` (`nombre_idioma`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idiomas`
--

LOCK TABLES `idiomas` WRITE;
/*!40000 ALTER TABLE `idiomas` DISABLE KEYS */;
INSERT INTO `idiomas` VALUES (1,'Inglés','en'),(2,'Español','es');
/*!40000 ALTER TABLE `idiomas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula`
--

DROP TABLE IF EXISTS `pelicula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula` (
  `idpelicula` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `id_idioma` int NOT NULL,
  `disponible` tinyint(1) NOT NULL,
  `sinopsis` varchar(500) DEFAULT NULL,
  `duracion_minutos` int NOT NULL,
  `idclasificacion` int NOT NULL,
  `idgenero` int NOT NULL,
  PRIMARY KEY (`idpelicula`),
  KEY `idx_pelicula_clasificacion` (`idclasificacion`),
  KEY `pelicula_ibkf_2_idx` (`idgenero`),
  KEY `fk_pelicula_idioma` (`id_idioma`),
  CONSTRAINT `fk_pelicula_idioma` FOREIGN KEY (`id_idioma`) REFERENCES `idiomas` (`id_idioma`),
  CONSTRAINT `pelicula_ibfk_1` FOREIGN KEY (`idclasificacion`) REFERENCES `clasificacion` (`idclasificacion`),
  CONSTRAINT `pelicula_ibkf_2` FOREIGN KEY (`idgenero`) REFERENCES `genero` (`idgenero`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula`
--

LOCK TABLES `pelicula` WRITE;
/*!40000 ALTER TABLE `pelicula` DISABLE KEYS */;
INSERT INTO `pelicula` VALUES (1,'El Padrino',1,1,'La historia de una familia mafiosa italiana en Estados Unidos',175,3,1),(2,'Avengers: Endgame',1,1,'Los Vengadores se unen para revertir el Blip',181,2,1),(3,'Toy Story',1,1,'Los juguetes cobran vida cuando los humanos no miran',81,1,8),(4,'El Conjuro',1,1,'Una familia es aterrorizada por una presencia oscura',112,3,5),(5,'La La Land',1,1,'Un pianista y una actriz se enamoran en Los Ángeles',128,2,7),(6,'Inception',1,1,'Ladrones que roban sueños dentro de la mente',148,3,6),(7,'Titanic',1,1,'Historia de amor en el Titanic',195,2,7),(8,'Shrek',1,1,'Un ogro rescata a una princesa',90,1,3),(9,'Coco',2,1,'Un niño viaja al mundo de los muertos',105,1,8),(10,'Matrix',1,1,'Un programador descubre la verdad sobre su realidad',136,3,6),(11,'Black Panther',1,1,'El rey de Wakanda protege su nación',134,2,1),(12,'Frozen',1,1,'La princesa con poderes de hielo',102,1,8);
/*!40000 ALTER TABLE `pelicula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `idproducto` bigint NOT NULL,
  `idtipoproducto` int NOT NULL,
  `precio_unitario` decimal(6,2) NOT NULL,
  `producto` varchar(50) NOT NULL,
  PRIMARY KEY (`idproducto`),
  KEY `idx_productos_tipo` (`idtipoproducto`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`idtipoproducto`) REFERENCES `tipo_producto` (`idtipoproducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (7501055300016,1,45.00,'Palomitas grandes'),(7501055300023,1,35.00,'Palomitas medianas'),(7501055300030,2,25.00,'Refresco grande'),(7501055300047,2,20.00,'Refresco mediano'),(7501055300054,4,100.00,'Combo individual'),(7501055300061,4,180.00,'Combo pareja'),(7501055300078,3,25.00,'Chocolates'),(7501055300085,3,30.00,'Gomitas'),(7501055300092,4,250.00,'Combo Familiar'),(7501055300108,2,30.00,'Refresco Extra Grande'),(7501055300115,12,40.00,'Pastelito'),(7501055300122,10,45.00,'Nachos grandes');
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `puestos`
--

DROP TABLE IF EXISTS `puestos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puestos` (
  `idpuesto` int NOT NULL AUTO_INCREMENT,
  `puesto` varchar(30) NOT NULL,
  PRIMARY KEY (`idpuesto`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puestos`
--

LOCK TABLES `puestos` WRITE;
/*!40000 ALTER TABLE `puestos` DISABLE KEYS */;
INSERT INTO `puestos` VALUES (1,'Gerente'),(2,'Taquillero'),(3,'Cajero de dulcería'),(4,'Acomodador'),(5,'Limpieza'),(6,'Proyeccionista'),(7,'Supervisor'),(8,'RRHH'),(9,'Contador'),(10,'Recepcionista'),(11,'Seguridad'),(12,'Mantenimiento');
/*!40000 ALTER TABLE `puestos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salas`
--

DROP TABLE IF EXISTS `salas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salas` (
  `idsalas` int NOT NULL AUTO_INCREMENT,
  `nombre_sala` varchar(30) NOT NULL,
  `capacidad` int NOT NULL,
  `idtiposala` int NOT NULL,
  PRIMARY KEY (`idsalas`),
  KEY `idtiposala` (`idtiposala`),
  CONSTRAINT `salas_ibfk_1` FOREIGN KEY (`idtiposala`) REFERENCES `tipo_salas` (`idtiposala`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salas`
--

LOCK TABLES `salas` WRITE;
/*!40000 ALTER TABLE `salas` DISABLE KEYS */;
INSERT INTO `salas` VALUES (1,'Sala 1',120,1),(2,'Sala 2',100,2),(3,'Sala 3',80,3),(4,'Sala 4',50,4),(5,'Sala 5',200,5),(6,'Sala 6',110,6),(7,'Sala 7',95,7),(8,'Sala 8',85,8),(9,'Sala 9',130,9),(10,'Sala 10',140,10),(11,'Sala 11',150,11),(12,'Sala 12',125,12);
/*!40000 ALTER TABLE `salas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_producto`
--

DROP TABLE IF EXISTS `tipo_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_producto` (
  `idtipoproducto` int NOT NULL AUTO_INCREMENT,
  `tipo_producto` varchar(30) NOT NULL,
  PRIMARY KEY (`idtipoproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_producto`
--

LOCK TABLES `tipo_producto` WRITE;
/*!40000 ALTER TABLE `tipo_producto` DISABLE KEYS */;
INSERT INTO `tipo_producto` VALUES (1,'Comida'),(2,'Bebida'),(3,'Dulcería'),(4,'Combos'),(5,'Merchandising'),(6,'Snack Pequeño'),(7,'Snack Grande'),(8,'Postre'),(9,'Combo Individual'),(10,'Combo Pareja'),(11,'Bebida Extra Grande'),(12,'Promoción');
/*!40000 ALTER TABLE `tipo_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_salas`
--

DROP TABLE IF EXISTS `tipo_salas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_salas` (
  `idtiposala` int NOT NULL AUTO_INCREMENT,
  `tipo_sala` varchar(20) NOT NULL,
  PRIMARY KEY (`idtiposala`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_salas`
--

LOCK TABLES `tipo_salas` WRITE;
/*!40000 ALTER TABLE `tipo_salas` DISABLE KEYS */;
INSERT INTO `tipo_salas` VALUES (1,'2D Estándar'),(2,'3D'),(3,'4DX'),(4,'VIP'),(5,'IMAX'),(6,'Dolby Cinema'),(7,'D-BOX'),(8,'ScreenX'),(9,'Gold Class'),(10,'Atmos'),(11,'Platino'),(12,'Premium');
/*!40000 ALTER TABLE `tipo_salas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `correo` varchar(50) NOT NULL,
  `contrasena_hash` varchar(255) NOT NULL,
  `ultimo_acceso` datetime DEFAULT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `idx_correo_unico` (`correo`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'juan.perez@cineproyecto.com','admin123',NULL,1),(2,'maria.lopez@cineproyecto.com','usuario2',NULL,1),(3,'carlos.ruiz@cineproyecto.com','usuario3',NULL,1),(4,'ana.garcia@cineproyecto.com','usuario4',NULL,1),(5,'luis.torres@cineproyecto.com','usuario5',NULL,1),(6,'elena.diaz@cineproyecto.com','usuario6',NULL,1),(7,'manuel.herrera@cineproyecto.com','usuario7',NULL,1),(8,'lucia.vega@cineproyecto.com','usuario8',NULL,1),(9,'raul.rivera@cineproyecto.com','usuario9',NULL,1),(10,'silvia.cruz@cineproyecto.com','usuario10',NULL,1),(11,'ismael.soto@cineproyecto.com','usuario11',NULL,1),(12,'gabriela.mendez@cineproyecto.com','usuario12',NULL,1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-07 21:56:16
