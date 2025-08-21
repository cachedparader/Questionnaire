-- MySQL dump 10.13  Distrib 8.0.40, for macos14 (arm64)
--
-- Host: localhost    Database: wjdb
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `wj_user`
--

DROP TABLE IF EXISTS `wj_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wj_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮件',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信',
  `fcd` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lud` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `user_type` varchar(10) DEFAULT 'normal' COMMENT 'admin/normal',
  `avatar` varchar(200) DEFAULT '/src/assets/images/avatar.gif' COMMENT '头像',
  `avatar_data` longblob,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`),
  UNIQUE KEY `unique_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wj_user`
--

LOCK TABLES `wj_user` WRITE;
/*!40000 ALTER TABLE `wj_user` DISABLE KEYS */;
INSERT INTO `wj_user` VALUES (1,'19988889999','老蔡','19988889999','$2a$10$LNr83qV3HKeILDjHGMbyletEG3R7Ieyq4iqM5mPKyVE3/29PcI13S',NULL,NULL,'2046-03-15 16:51:05','2046-03-15 16:51:05','normal','1620f91f583840b1b3f3d80dd1b4f7bb.png',NULL),(2,'19999990000','青青菜鸟','19999990000','$2a$10$HiS6RhvywJTjAx57DBhut.bc9bZBdC67lmSxV7kBikfCTeVLWyECu',NULL,NULL,'2046-03-15 16:56:10','2046-03-15 16:56:10','admin','/src/assets/images/default.png',NULL);
/*!40000 ALTER TABLE `wj_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-21 21:13:35
