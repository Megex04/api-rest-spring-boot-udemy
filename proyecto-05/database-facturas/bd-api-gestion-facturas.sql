-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_gestion_facturas
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Batidos premium'),(2,'Electronicos'),(3,'Extra');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facturas`
--

DROP TABLE IF EXISTS `facturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facturas` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `numero_contacto` varchar(255) DEFAULT NULL,
  `producto_detalles` varchar(255) DEFAULT NULL,
  `total` int DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facturas`
--

LOCK TABLES `facturas` WRITE;
/*!40000 ALTER TABLE `facturas` DISABLE KEYS */;
INSERT INTO `facturas` VALUES (1,'mimi03@gmail.com','test@gmail.com','Cash','Test','981091672','[{\"id\":18,\"nombre\":\"Nombre 01\",\"categoria\":\"Coffee\",\"cantidad\":\"1\",\"precio\":120,\"total\":120},{\"id\":21,\"nombre\":\"Nombre 04\",\"categoria\":\"Coffee\",\"cantidad\":\"1\",\"precio\":120,\"total\":120}]',279,'FACTURA-1716862188911'),(3,'lacunzamiguel04@gmail.com','test02@gmail.com','Cash-Pay','Test2','981091672','[{\"id\":18,\"nombre\":\"Nombre 01\",\"categoria\":\"Coffee\",\"cantidad\":\"1\",\"precio\":120,\"total\":120},{\"id\":21,\"nombre\":\"Nombre 04\",\"categoria\":\"Coffee\",\"cantidad\":\"1\",\"precio\":120,\"total\":120}]',450,'FACTURA-1716862572693'),(4,'mimi03@gmail.com','test02@gmail.com','Cash-Pay','Test2','981091672','[{\"id\":18,\"nombre\":\"Nombre 01\",\"categoria\":\"Coffee\",\"cantidad\":\"1\",\"precio\":120,\"total\":120},{\"id\":21,\"nombre\":\"Nombre 04\",\"categoria\":\"Coffee\",\"cantidad\":\"1\",\"precio\":120,\"total\":120}]',450,'FACTURA-1716941010419');
/*!40000 ALTER TABLE `facturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `categoria_fk` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKclm1tmrbu6watc3so3lha7ekm` (`categoria_fk`),
  CONSTRAINT `FKclm1tmrbu6watc3so3lha7ekm` FOREIGN KEY (`categoria_fk`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Agitado con piña selvatica','Batido de piña',6.7,'true',1),(3,'Con bastante vitamina y coccion buena','Ensalada de frutas',9.6,'true',1),(4,'Con alta garantia','Refrigeradora Ultimate',955.8,'false',2),(5,'Con añta potencia en motor','Lavadora LG',775.2,'true',2),(6,'Con roseador','Plancha Heavy',295.6,'true',2);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `numero_de_contacto` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'miantolalfa@gmail.com','Amamzon EC2','996969696','$2a$10$NHx2kgHS/k3BCSXJt/xAqejr27b3HwHOwYZPhCG50YToa2okU4wUy','admin','true'),(2,'contacto2@gmail.com','Azure Datafactory','951951951','mycontact02','user','false'),(3,'lacunzamiguel04@gmail.com','Admin test','985632145','$2a$10$/xAVXFM/g/SxBHxCdJKPUOHr4sNTlOdKmDSs83mbBxeSu6/7upbga','user','true'),(4,'mimi02@gmail.com','Admin test (2)','963852574','admintest02','user','false'),(5,'mimi03@gmail.com','Admin test (3)','969555412','$2a$10$.ntiarEKLWKZDn7JkwbDsOhpmrTSB0LYz/s1gjHBWHux5Km11flEi','admin','true'),(6,'mimi05@gmail.com','Admin test (5)','985777888','$2a$10$8pXgXlBoTeXH93Of2gl3yefnIDMikI19KMJ1iYwx0juG97ZeZ9.Dy','user','false'),(15,'lacunzaalfaromiguel2000@gmail.com','Admin test (6)','985777888','$2a$10$.hl7SuJtlajfEHYMPn15weOssTG.TyJVA9x3Wtcm/hT76py5.5Awi','user','false');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-28 18:09:24
