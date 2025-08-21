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
-- Table structure for table `wj_base_param`
--

DROP TABLE IF EXISTS `wj_base_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wj_base_param` (
  `id` int NOT NULL AUTO_INCREMENT,
  `base_name` varchar(100) DEFAULT NULL COMMENT '参数',
  `param_name` varchar(100) DEFAULT NULL COMMENT '参数名',
  `param_value` varchar(100) DEFAULT NULL COMMENT '参数值',
  `param_desc` varchar(200) DEFAULT NULL COMMENT '参数描述',
  `priority` int DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wj_base_param`
--

LOCK TABLES `wj_base_param` WRITE;
/*!40000 ALTER TABLE `wj_base_param` DISABLE KEYS */;
INSERT INTO `wj_base_param` VALUES (7,'question_type','考生信息','0','考生信息',0),(8,'question_type','单选题','1','单选题',0),(9,'question_type','多选题','2','多选题',0),(10,'surveyStatus','状态',NULL,'状态',0),(11,'surveyStatus','编辑中','0','编辑中',1),(12,'surveyStatus','已就绪','1','已就绪',2),(13,'surveyStatus','已发布','2','已发布',3),(14,'surveyOrder','默认排序','id desc','默认排序',0),(15,'surveyOrder','时间倒序','fcd desc','时间倒序',1),(16,'surveyOrder','时间正序','fcd','时间正序',2);
/*!40000 ALTER TABLE `wj_base_param` ENABLE KEYS */;
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
