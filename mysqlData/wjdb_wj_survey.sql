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
-- Table structure for table `wj_survey`
--

DROP TABLE IF EXISTS `wj_survey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wj_survey` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `status` int DEFAULT '0' COMMENT '状态 0编辑中 1已就绪 2 已发布',
  `answer_total` int DEFAULT '0' COMMENT '答卷数',
  `star` int DEFAULT '0' COMMENT '星标',
  `deleted` int DEFAULT '0' COMMENT '0正常 1回收站',
  `user_id` int DEFAULT NULL COMMENT '问卷创建者id',
  `time_limit` int DEFAULT '60' COMMENT '时长限制',
  `fcd` datetime DEFAULT CURRENT_TIMESTAMP,
  `lud` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wj_survey`
--

LOCK TABLES `wj_survey` WRITE;
/*!40000 ALTER TABLE `wj_survey` DISABLE KEYS */;
INSERT INTO `wj_survey` VALUES (24,'奥外科技开发岗综合技能测试','入口测试',1,15,1,0,1,40,'2046-06-29 14:07:09','2024-06-29 14:07:09'),(25,'Java基础知识评测',NULL,0,0,0,1,2,60,'2046-06-30 20:21:22','2024-06-30 20:21:22'),(28,'Mysql测试','mysql阶段考试',1,0,1,0,2,60,'2046-07-22 12:30:02','2024-07-22 12:30:02'),(40,'Java测试','阶段考试',1,0,0,0,2,60,'2046-07-22 17:31:00','2024-07-22 17:31:00'),(41,'xxx',NULL,0,0,0,0,2,60,'2046-08-17 15:58:39','2024-08-17 15:58:39'),(42,'xxxyyyzzz',NULL,0,0,0,0,2,60,'2046-08-17 17:44:05','2024-08-17 17:44:05'),(43,'111222333',NULL,0,0,0,1,1,60,'2046-08-17 17:48:31','2024-08-17 17:48:31'),(46,'Java综合测试','Java测试Java测试Java测试',1,6,0,0,1,60,'2046-08-19 00:00:00','2024-08-19 00:00:00'),(48,'xxxx',NULL,0,0,0,1,1,60,'2024-08-05 22:14:58','2024-08-05 22:14:58'),(49,'网络安全基础测试',NULL,1,0,0,1,1,60,'2024-08-18 17:32:35','2024-08-18 17:32:35'),(50,'xxx',NULL,0,0,0,1,1,60,'2024-08-18 17:33:39','2024-08-18 17:33:39'),(51,'网络安全测试',NULL,1,0,0,1,1,60,'2024-08-18 17:49:17','2024-08-18 17:49:17'),(52,'【Java100】Java综合测试','Java测试Java测试Java测试',1,5,0,1,1,60,'2024-08-18 00:00:00','2024-08-18 00:00:00'),(53,'网络安全测试',NULL,1,0,0,0,1,60,'2024-08-18 20:02:51','2024-08-18 20:02:51'),(54,'【复制】Java综合测试','Java测试Java测试Java测试',1,0,0,0,1,60,'2024-08-18 20:06:58','2024-08-18 20:06:58');
/*!40000 ALTER TABLE `wj_survey` ENABLE KEYS */;
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
