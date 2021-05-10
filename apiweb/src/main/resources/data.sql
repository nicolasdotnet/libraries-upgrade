-- MySQL dump 10.18  Distrib 10.3.27-MariaDB, for debian-linux-gnueabihf (armv8l)
--
-- Host: localhost    Database: db_libraries
-- ------------------------------------------------------
-- Server version	10.3.27-MariaDB-0+deb10u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (8,'Rowston Thebest',4,'5613521',5,'Vol 1  : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : script contemporains, hi-fi d\'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.','Java pour les nuls',6),(9,'Rowston Thebest',5,'5613522',5,'Vol 2  : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : script contemporains, hi-fi d\'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.','Java pour les nuls Volume 2',6),(10,'Rowston Thebest',4,'5613523',5,'Vol 3  : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : script contemporains, hi-fi d\'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.','Java pour les nuls Volume 3',6),(11,'Nicolas Junior',5,'561353',5,'Une présentation des techniques de base de dessin et du matériel, et des projets exposés pas à pas pour découvrir des techniques particulières.','Ma vie de dev',7),(12,'Peter Bishop',4,'56135300',5,'Une présentation des techniques de base, et des projets exposés pas à pas pour découvrir des techniques de physique quantique.','Vive le quantique',7),(13,'Walter Bishop',4,'561353000',5,'Carnet de travail des techniques alternatives, et des projets exposés pas à pas pour découvrir des techniques de physique avancées.','Carnet de sciences',7),(21,'Nicole Thebest',0,'56135289',2,'Vol : Le dev ninja délivre ses conseils pour savoir repérer les beaux codes du XXIe siècle : script contemporains, hi-fi d\'antan, nouvelles matières comme le Formica ou luminaires, modèles iconiques.','Histoire du Java',20),(36,'Nicole Thebest',0,'56135200',1,'histoire contemporaine du developement depuis d\'après guerre.','Histoire du development',20);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `book_category`
--

LOCK TABLES `book_category` WRITE;
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT INTO `book_category` VALUES (6,'Polar'),(7,'Science'),(20,'Histoire');
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (14,'28',NULL,'2020-02-28','2020-02-14 00:00:00','ENCOURS','0',13,4),(15,'28',NULL,'2020-02-28','2020-02-14 00:00:00','TERMINE','0',8,4),(16,'28',NULL,'2021-07-04','2021-05-07 14:53:47','PROLONGE','1',10,4),(17,'28',NULL,'2021-06-05','2021-05-07 14:53:47','ENCOURS','0',12,4),(22,'28','2021-05-07 14:53:48','2021-06-05','2021-05-07 14:53:48','TERMINE','0',21,4),(27,'28',NULL,'2021-06-05','2021-05-07 14:53:48','ENCOURS','0',21,19),(31,'28','2021-05-07 14:53:49','2021-06-05','2021-05-07 14:53:49','TERMINE','0',21,28),(35,'28',NULL,'2021-06-05','2021-05-07 14:53:49','RESERVE','0',21,4),(37,'28',NULL,'2021-06-05','2021-05-07 14:53:49','ENCOURS','0',36,19);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (40),(40),(40),(40),(40),(40);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (33,'2021-05-07 14:53:49','ENCOURS',NULL,21,30),(26,'2021-05-07 14:53:48','ATTENTE','2021-05-07',21,4),(32,'2021-05-07 14:53:49','ENCOURS',NULL,21,18),(34,'2021-05-07 14:53:49','ENCOURS',NULL,21,29),(38,'2021-05-07 14:53:49','ENCOURS',NULL,36,18),(39,'2021-05-07 14:53:49','ENCOURS',NULL,36,4);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'bibliothecaire'),(2,'usager');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'employe@mail.com','nicolas','desdevises','$2a$10$COsneE.L0xZf36NvNpAkHOAgK0p2sGPftHNFfdrSKFTRy0ZEY23yS','2021-05-07 14:53:46',1),(4,'usager@mail.com','laure','desdevises','$2a$10$cfTajsLAEEXVrpn3blgWWufdN2WE1i.z1sXdioU7CyT8l0dpveESy','2021-05-07 14:53:46',2),(5,'admin@mail.com','root','admin','$2a$10$sYWUUIe3Me9iY4kykdrUD.8iL8VtgiJvv1dX7FqYetQ4gIbQakpuK','2021-05-07 14:53:46',1),(18,'usager2@mail.com','nicole','desdevises','$2a$10$pn690GXeInRm3eq731MxTOqBenp6HlCtIWV7E7USyxYqu4B.UTIFO','2021-05-07 14:53:47',2),(19,'usager3@mail.com','Alain','desdevises','$2a$10$Z/E6POvC08QEY/i8AQuyROWnJ6hs0aDUCBDskuh6MBwmTkN5iqDqC','2021-05-07 14:53:48',2),(28,'usager4@mail.com','sam','desdevises','$2a$10$T/lgdOYj9dYg2/iUDZdw4uwyU9qyqQbRHnGvQm3iyGGYZjmevurG2','2021-05-07 14:53:49',2),(29,'usager5@mail.com','daniel','desdevises','$2a$10$iP7TBCApuHYgKu4mGgWTh.NsgMzNo60ggRFs23wOJVSmWo3KOCaqO','2021-05-07 14:53:49',2),(30,'usager6@mail.com','fred','desdevises','$2a$10$/bjjBeHSI37xpH0hee6/jO43ELAE7dAEqpxpUVwbdtC/9IA.4d7VG','2021-05-07 14:53:49',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-10  9:28:55
