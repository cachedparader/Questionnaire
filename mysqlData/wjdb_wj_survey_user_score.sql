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
-- Table structure for table `wj_survey_user_score`
--

DROP TABLE IF EXISTS `wj_survey_user_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wj_survey_user_score` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL COMMENT '考生名',
  `survey_id` int DEFAULT NULL COMMENT '问卷id',
  `score` int DEFAULT NULL COMMENT '得分',
  `exam_duration` int DEFAULT NULL COMMENT '考试用时：秒',
  `survey_score` int DEFAULT NULL COMMENT '问卷总分',
  `question_number` int DEFAULT NULL COMMENT '试题数',
  `correct_num` int DEFAULT NULL COMMENT '答对数',
  `fcd` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wj_survey_user_score`
--

LOCK TABLES `wj_survey_user_score` WRITE;
/*!40000 ALTER TABLE `wj_survey_user_score` DISABLE KEYS */;
INSERT INTO `wj_survey_user_score` VALUES (33,'赵四',46,70,50,100,10,7,'2024-08-14 12:08:01'),(34,'李四',46,60,36,100,10,6,'2024-08-14 12:57:05'),(35,'周大大',46,80,43,100,10,8,'2024-08-14 13:01:17'),(36,'老蔡',46,90,45,100,10,9,'2024-08-14 13:04:03'),(37,'张三疯',46,70,40,100,10,7,'2024-08-18 17:51:50'),(38,'张三',46,70,43,100,10,7,'2024-08-18 20:05:31');
/*!40000 ALTER TABLE `wj_survey_user_score` ENABLE KEYS */;
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
