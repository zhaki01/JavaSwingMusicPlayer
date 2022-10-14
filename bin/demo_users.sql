-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(90) DEFAULT NULL,
  `lastname` varchar(90) DEFAULT NULL,
  `username` varchar(80) DEFAULT NULL,
  `password` varchar(80) DEFAULT NULL,
  `newpassword` varchar(45) DEFAULT NULL,
  `confirmpass` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Zhakshylyk','Tazhibaev','zhaki','12345','null','admin'),(2,'Mke','Mavlonov','Bayke','1998',NULL,NULL),(3,'Mira','Tazhibaev','mir','1234',NULL,NULL),(4,'Zhakshylyk','Tazhibaev','Dapeng','2001',NULL,NULL),(5,'Eli','Tazhibaev','eli','2004',NULL,NULL),(6,'Habibi','Abood','habib','2022',NULL,NULL),(7,'Mike','Mavlonov','mik','12345','3434','3434'),(8,'Louis','Baptims','anpei','131313',NULL,NULL),(9,'Ali','Bolsun','ali','1998',NULL,NULL),(10,'Beki','Alibekov','beka','1001',NULL,NULL),(11,'Wang','Li','Wangli','0909',NULL,NULL),(12,'Alina','Amir','alina','1221',NULL,NULL),(13,'Nurlan','Abdibaitov','nur','9090',NULL,NULL),(14,'Robert','Ball','bali','5656',NULL,NULL),(15,'Oni','Tak','ooni','1221',NULL,NULL),(16,'Louis','Kojo','kojo','0909',NULL,NULL),(17,'Tali','Tab','tom','8888',NULL,NULL),(18,'zhangchu','zhang','zhangyun','123456',NULL,NULL),(19,'dedjd','bar','beka','2020',NULL,NULL),(20,'Teka','baka','mike','1212',NULL,NULL),(21,'Adilet','Abi','Habib','2323',NULL,NULL),(22,'Almaz','Esenov','wang','0909',NULL,NULL),(23,'Acan','Beka','bek','1212',NULL,NULL),(24,'zhakshylyk','Tazhibaev','zhaki01','2001',NULL,NULL),(25,'Rus','Da','rus','1221',NULL,NULL),(26,'Abood','HABIBI','habibi','1234',NULL,NULL),(27,'zhaki','Mac','Mac','zhaki',NULL,NULL),(28,'mira','mira','mira','mira',NULL,NULL);
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

-- Dump completed on 2022-10-14 13:37:21
