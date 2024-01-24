CREATE DATABASE  IF NOT EXISTS `bd_sistema_encuestas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bd_sistema_encuestas`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_sistema_encuestas
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
-- Table structure for table `encuesta`
--

DROP TABLE IF EXISTS `encuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encuesta` (
  `encuesta_id` bigint NOT NULL,
  `pregunta` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`encuesta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encuesta`
--

LOCK TABLES `encuesta` WRITE;
/*!40000 ALTER TABLE `encuesta` DISABLE KEYS */;
INSERT INTO `encuesta` VALUES (1,'¿Cual es tu puto lenguaje de programacion favorito?'),(52,'¿Cual es lenguaje XD de programacion mas popular?');
/*!40000 ALTER TABLE `encuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `encuesta_seq`
--

DROP TABLE IF EXISTS `encuesta_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `encuesta_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `encuesta_seq`
--

LOCK TABLES `encuesta_seq` WRITE;
/*!40000 ALTER TABLE `encuesta_seq` DISABLE KEYS */;
INSERT INTO `encuesta_seq` VALUES (151);
/*!40000 ALTER TABLE `encuesta_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opcion`
--

DROP TABLE IF EXISTS `opcion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `opcion` (
  `opcion_id` bigint NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  `encuesta_id` bigint DEFAULT NULL,
  PRIMARY KEY (`opcion_id`),
  KEY `FKjh6972m9o1nr4f92nrvyw75of` (`encuesta_id`),
  CONSTRAINT `FKjh6972m9o1nr4f92nrvyw75of` FOREIGN KEY (`encuesta_id`) REFERENCES `encuesta` (`encuesta_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opcion`
--

LOCK TABLES `opcion` WRITE;
/*!40000 ALTER TABLE `opcion` DISABLE KEYS */;
INSERT INTO `opcion` VALUES (1,'C#',1),(2,'C++',1),(3,'java',1),(4,'Go',1),(5,'javascript',1),(6,'Python',1),(52,'C#',52),(53,'C++',52),(54,'java',52),(55,'Go',52),(56,'javascript',52),(57,'Python',52);
/*!40000 ALTER TABLE `opcion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `opcion_seq`
--

DROP TABLE IF EXISTS `opcion_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `opcion_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `opcion_seq`
--

LOCK TABLES `opcion_seq` WRITE;
/*!40000 ALTER TABLE `opcion_seq` DISABLE KEYS */;
INSERT INTO `opcion_seq` VALUES (151);
/*!40000 ALTER TABLE `opcion_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voto`
--

DROP TABLE IF EXISTS `voto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voto` (
  `voto_id` bigint NOT NULL,
  `opcion_id` bigint DEFAULT NULL,
  PRIMARY KEY (`voto_id`),
  KEY `FK622aaspfqgbp4dn4aai1pmhbv` (`opcion_id`),
  CONSTRAINT `FK622aaspfqgbp4dn4aai1pmhbv` FOREIGN KEY (`opcion_id`) REFERENCES `opcion` (`opcion_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voto`
--

LOCK TABLES `voto` WRITE;
/*!40000 ALTER TABLE `voto` DISABLE KEYS */;
INSERT INTO `voto` VALUES (52,1),(53,1),(54,3),(55,3),(102,6),(4,52),(5,52),(6,52),(7,52),(8,52),(9,52),(10,52),(56,53),(57,53),(58,53);
/*!40000 ALTER TABLE `voto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voto_seq`
--

DROP TABLE IF EXISTS `voto_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voto_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voto_seq`
--

LOCK TABLES `voto_seq` WRITE;
/*!40000 ALTER TABLE `voto_seq` DISABLE KEYS */;
INSERT INTO `voto_seq` VALUES (201);
/*!40000 ALTER TABLE `voto_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-24  9:30:56
