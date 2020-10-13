-- MySQL dump 10.13  Distrib 8.0.20, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: db_bookcase
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (8,'Rowston Thebest',5,'5613521','Vol 1  : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : script contemporains, hi-fi d\'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.','Java pour les nuls',6),(9,'Rowston Thebest',5,'5613522','Vol 2  : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : script contemporains, hi-fi d\'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.','Java pour les nuls Volume 2',6),(10,'Rowston Thebest',4,'5613523','Vol 3  : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : script contemporains, hi-fi d\'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.','Java pour les nuls Volume 3',6),(11,'Nicolas Junior',5,'561353','Une présentation des techniques de base de dessin et du matériel, et des projets exposés pas à pas pour découvrir des techniques particulières.','Ma vie de dev',7),(12,'Peter Bishop',4,'56135300','Une présentation des techniques de base, et des projets exposés pas à pas pour découvrir des techniques de physique quantique.','Vive le quantique',7),(13,'Walter Bishop',4,'561353000','Carnet de travail des techniques alternatives, et des projets exposés pas à pas pour découvrir des techniques de physique avancées.','Carnet de sciences',7);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_category`
--

LOCK TABLES `book_category` WRITE;
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT INTO `book_category` VALUES (6,'Polar'),(7,'Science');
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (14,'28',NULL,'2020-02-27','2020-02-13 23:00:00','ENCOURS','0',13,4),(15,'28',NULL,'2020-02-27','2020-02-13 23:00:00','TERMINE','0',8,4),(16,'28',NULL,'2020-12-04','2020-10-09 13:51:59','PROLONGE','1',10,4),(17,'28',NULL,'2020-11-06','2020-10-09 13:52:00','ENCOURS','0',12,4);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (18),(18),(18),(18),(18);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'bibliothécaire'),(2,'usager');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'employe@mail.com','nicolas','desdevises','$2a$10$vXZz79Cwpc.Oh38ItnQyZuapbXb0wSjAWl5Kukvs3qvdbRPvUIWo2','2020-10-09 13:51:57',1),(4,'usager@mail.com','laure','desdevises','$2a$10$/shh8LUxp7PfiKQC/XzWsu5ffM31q1LOxZ3fxcklWLMvkp/LLupga','2020-10-09 13:51:57',2),(5,'admin@mail.com','root','admin','$2a$10$Ymn.4Z1m8Z4zJ446FgvZ5uN0Ioz84BnJCTGrmkUWTlIm15xboAKLu','2020-10-09 13:51:57',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db_bookcase'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-09 16:05:05
