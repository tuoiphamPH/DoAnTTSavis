-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: final_doan
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_by` varchar(255) DEFAULT NULL,
  `modifier_date` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `active` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'admin','2022-06-09 20:19:30',NULL,'2022-06-09 20:19:30','Áo Đá Bóng',1),(2,'admin','2022-06-09 20:20:15',NULL,'2022-06-09 20:20:15','Áo Bóng Chuyền',1),(3,'admin','2022-06-09 20:20:27',NULL,'2022-06-09 20:20:27','Giày Đá Bóng',1),(4,'admin','2022-06-09 20:20:38',NULL,'2022-06-09 20:20:38','Giày Bóng Chuyền',1),(5,'admin','2022-06-09 20:30:33',NULL,'2022-06-09 20:30:33','Bóng Đá',1),(6,'admin','2022-06-09 20:30:40',NULL,'2022-06-09 20:30:40','Bóng Chuyền',1),(7,'admin','2022-11-30 14:48:31','admin','2022-11-30 14:48:31','fdgfg',0),(8,'admin','2022-11-30 14:48:29','admin','2022-11-30 14:48:29','ghsdfsdf',0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_by` varchar(255) DEFAULT NULL,
  `modifier_date` datetime DEFAULT NULL,
  `content` varchar(5000) DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `total_like` int DEFAULT NULL,
  `review_entity_id` bigint DEFAULT NULL,
  `user_entity_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6w5mmwvixa37cvy8u7u60bik8` (`review_entity_id`),
  KEY `FKulelxhnjl2sf2030105dbc1i` (`user_entity_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,'quyen','2022-06-10 17:17:42',NULL,'2022-06-10 17:17:42','đẹp quá',NULL,NULL,18,3),(2,'quyen','2022-06-10 17:17:53',NULL,'2022-06-10 17:17:53','quá đẹp',NULL,NULL,18,3),(3,'unknowUser','2022-06-24 08:12:39',NULL,'2022-06-24 08:12:39','sdfsdfsdf',NULL,NULL,18,8),(4,'quangpro2000','2022-06-24 08:13:01',NULL,'2022-06-24 08:13:01','quas tuyet',NULL,NULL,18,7),(5,'quangpro2000','2022-06-24 08:16:07',NULL,'2022-06-24 08:16:07','tuyet voi',NULL,NULL,28,7),(6,'quangpro2000','2022-06-24 08:16:18',NULL,'2022-06-24 08:16:18','hay qua di',NULL,NULL,28,7),(7,'quangpro2000','2022-06-24 08:18:07',NULL,'2022-06-24 08:18:07','qua hay',NULL,NULL,18,7),(8,'Hữu Quang','2022-07-12 22:15:50',NULL,'2022-07-12 22:15:50','quas dep\n',NULL,NULL,28,2),(9,'unknowUser','2022-07-12 22:16:54',NULL,'2022-07-12 22:16:54','tuyet voi',NULL,NULL,18,11);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_by` varchar(255) DEFAULT NULL,
  `modifier_date` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `total` bigint DEFAULT NULL,
  `category_entity_id` bigint DEFAULT NULL,
  `review_id` bigint DEFAULT NULL,
  `active` int DEFAULT NULL,
  `sale` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm3owfv4y4429l2xo043ym447m` (`category_entity_id`),
  KEY `FK63qfnwyiuhg8m8ywe84ehhj9` (`review_id`)
) ENGINE=MyISAM AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'admin','2022-11-30 14:10:46','admin','2022-11-30 14:10:46',NULL,'11_20221669792246mu.jpg','Áo MU',300000,NULL,1,42,1,10),(2,'admin','2022-11-30 14:11:55','admin','2022-11-30 14:11:55',NULL,'11_20221669792315tot.jpg','Áo Tottenham',300000,NULL,1,43,1,10),(3,'admin','2022-11-30 14:12:25','admin','2022-11-30 14:12:25',NULL,'11_20221669792344mc.png','Áo Man City',300000,NULL,1,44,1,10),(4,'admin','2022-11-30 14:12:54','admin','2022-11-30 14:12:54',NULL,'11_20221669792374psg.jpg','Áo PSG',300000,NULL,1,45,1,20),(5,'admin','2022-11-30 14:13:54','admin','2022-11-30 14:13:54',NULL,'11_20221669792433paul.jpg','Áo Thiết Kế',200000,NULL,1,46,1,5),(6,'admin','2022-11-30 14:14:52','admin','2022-11-30 14:14:52',NULL,'11_20221669792492chese.jpg','Áo Chelsea',300000,NULL,1,47,1,5),(7,'admin','2022-11-30 14:15:40','admin','2022-11-30 14:15:40',NULL,'11_20221669792539vang.jpg','Áo Vàng Họa Mi',300000,NULL,2,48,1,10),(8,'admin','2022-11-30 14:16:13','admin','2022-11-30 14:16:13',NULL,'11_20221669792573xanh.jpg','Áo Xanh Bầu Trời',399000,NULL,2,49,1,20),(9,'admin','2022-11-30 14:17:20','admin','2022-11-30 14:17:20',NULL,'11_20221669792639trang.jpg','Áo Trắng Tinh khôi',300000,NULL,2,50,1,5),(10,'admin','2022-11-30 14:18:25','admin','2022-11-30 14:18:25',NULL,'11_20221669792705trangnam.jpg','Áo Trắng Nam',300000,NULL,2,51,1,5),(11,'admin','2022-11-30 14:19:05','admin','2022-11-30 14:19:05',NULL,'11_20221669792745do.jpg','Áo Đỏ Đô',300000,NULL,2,52,1,10),(12,'admin','2022-11-30 14:19:58','admin','2022-11-30 14:19:58',NULL,'11_20221669792797xanhblue.jpg','Áo Xanh Thanh Lịch',300000,NULL,2,53,1,NULL),(13,'admin','2022-11-30 14:20:53','admin','2022-11-30 14:20:53',NULL,'11_20221669792852giayxam.jpg','Giày Xám',300000,NULL,3,54,1,NULL),(14,'admin','2022-11-30 14:27:33','admin','2022-11-30 14:27:33',NULL,'11_20221669793253789.jpg','Giày Tím',300000,NULL,3,59,1,NULL),(15,'admin','2022-11-30 14:22:47','admin','2022-11-30 14:22:47',NULL,'11_20221669792967do.jpg','Giày Đỏ',300000,NULL,3,55,1,NULL),(16,'admin','2022-11-30 14:23:28','admin','2022-11-30 14:23:28',NULL,'11_202216697930074.jpg','Giày Đen',300000,NULL,3,56,1,NULL),(17,'admin','2022-11-30 14:24:02','admin','2022-11-30 14:24:02',NULL,'11_20221669793041789.jpg','Giày Xanh',300000,NULL,3,57,1,NULL),(18,'admin','2022-11-30 14:24:43','admin','2022-11-30 14:24:43',NULL,'11_202216697930829.jpg','Giày Trắng',4000000,NULL,3,58,1,NULL),(19,'admin','2022-11-30 14:38:11','admin','2022-11-30 14:38:11',NULL,'11_2022166979389168.jpg','Giày Bóng Chuyền Xanh',1000000,NULL,4,68,1,NULL),(20,'admin','2022-11-30 14:39:58','admin','2022-11-30 14:39:58',NULL,'11_20221669793997giay-bong-da-coavu-rong-den.jpg','Giày Bóng Chuyền Đen',3000000,NULL,4,70,1,NULL),(21,'admin','2022-11-30 14:37:50','admin','2022-11-30 14:37:50',NULL,'11_202216697938696.jpg','Giày Bóng Chuyền Trắng',3000000,NULL,4,67,1,NULL),(22,'admin','2022-11-30 14:38:38','admin','2022-11-30 14:38:38',NULL,'11_20221669793917987.jpg','Giày Bóng Chuyền Vàng',300000,NULL,4,69,1,NULL),(23,'admin','2022-11-30 14:40:28','admin','2022-11-30 14:40:28',NULL,'11_20221669794028giay-bong-da-coavu-den-do-1.jpg','Giày Bóng Chuyền Đen 2',300000,NULL,4,71,1,NULL),(24,'admin','2022-11-30 14:40:53','admin','2022-11-30 14:40:53',NULL,'11_2022166979405268.jpg','Giày Bóng Chuyền Xanh Biển',100000,NULL,4,72,1,NULL),(25,'admin','2022-11-30 14:29:57','admin','2022-11-30 14:29:57',NULL,'11_2022166979339732.png','Bóng Vàng',100000,NULL,5,60,1,NULL),(26,'admin','2022-11-30 14:31:33','admin','2022-11-30 14:31:33',NULL,'11_202216697934939544.jpg','Bóng Trắng',300000,NULL,5,61,1,NULL),(27,'admin','2022-11-30 14:32:01','admin','2022-11-30 14:32:01',NULL,'11_202216697935205.jpg','Bóng Động Lực',300000,NULL,5,62,1,NULL),(28,'admin','2022-11-30 14:33:04','admin','2022-11-30 14:33:04',NULL,'11_20221669793584785.jpg','Bóng Động Lực 2',300000,NULL,5,63,1,NULL),(29,'admin','2022-11-30 14:33:55','admin','2022-11-30 14:33:55',NULL,'11_202216697936357.jfif','Bóng AFC',300000,NULL,5,64,1,NULL),(30,'admin','2022-11-30 14:34:44','admin','2022-11-30 14:34:44',NULL,'11_20221669793684564.jpg','Bóng Động Lực 3',1000000,NULL,5,65,1,NULL),(31,'admin','2022-11-30 14:36:30','admin','2022-11-30 14:36:30',NULL,'11_20221669793790qua-bong-chuyen-hoi.jpg','Bóng Chuyền',200000,NULL,6,66,1,NULL),(32,'admin','2022-06-23 20:15:35','admin','2022-06-23 20:15:35',NULL,'6_20221655990071bong-chuyen-thang-long-vb7000_large.jpg','1111',1111,NULL,1,38,0,NULL),(33,'admin','2022-07-12 21:59:31','admin','2022-07-12 21:59:31',NULL,'6_20221656056753dfdsf.png','ha ',912303012,NULL,1,39,0,NULL),(34,'admin','2022-11-30 14:13:06','admin','2022-11-30 14:13:06',NULL,'11_202216696951681.jpg','1,sdfsd',1,NULL,1,40,0,10),(35,'admin','2022-11-30 14:13:03','admin','2022-11-30 14:13:03',NULL,'11_202216696952161.jpg','1',1,NULL,1,41,0,10),(36,'admin','2022-11-30 14:43:28','admin','2022-11-30 14:43:28',NULL,'11_202216697942031.jpg','sdfsd,dgfhffdg',3521,NULL,4,73,0,NULL);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_history`
--

DROP TABLE IF EXISTS `product_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_by` varchar(255) DEFAULT NULL,
  `modifier_date` datetime DEFAULT NULL,
  `total` int DEFAULT NULL,
  `product_entity_id` bigint DEFAULT NULL,
  `user_entity_id` bigint DEFAULT NULL,
  `sale` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr4v528xnlt4illcls8hxy6c65` (`product_entity_id`),
  KEY `FKhn5ep66syj26xx3ft8gfmce9p` (`user_entity_id`)
) ENGINE=MyISAM AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_history`
--

LOCK TABLES `product_history` WRITE;
/*!40000 ALTER TABLE `product_history` DISABLE KEYS */;
INSERT INTO `product_history` VALUES (47,'quyen','2022-06-19 16:23:21',NULL,'2022-06-19 16:23:21',1,19,3,NULL),(48,'Hữu Quang','2022-06-19 16:25:26',NULL,'2022-06-19 16:25:26',1,18,2,NULL),(49,'Hữu Quang','2022-06-19 16:51:06',NULL,'2022-06-19 16:51:06',1,18,2,NULL),(50,'Hữu Quang','2022-06-19 16:51:06',NULL,'2022-06-19 16:51:06',1,20,2,NULL),(51,'quyen','2022-06-19 16:55:30',NULL,'2022-06-19 16:55:30',1,20,3,NULL),(52,'quyen','2022-06-19 16:55:30',NULL,'2022-06-19 16:55:30',1,21,3,NULL),(53,'quyen','2022-06-19 16:56:11',NULL,'2022-06-19 16:56:11',1,18,3,NULL),(54,'quyen','2022-06-19 16:56:11',NULL,'2022-06-19 16:56:11',1,30,3,NULL),(55,'quyen','2022-06-19 16:58:47',NULL,'2022-06-19 16:58:47',1,18,3,NULL),(56,'quyen','2022-06-19 16:58:47',NULL,'2022-06-19 16:58:47',1,19,3,NULL),(57,'Hữu Quang','2022-06-23 20:39:50',NULL,'2022-06-23 20:39:50',1,20,2,NULL),(58,'Hữu Quang','2022-06-23 20:39:50',NULL,'2022-06-23 20:39:50',1,8,2,NULL),(59,'Hữu Quang','2022-06-23 20:39:50',NULL,'2022-06-23 20:39:50',1,20,2,NULL),(60,'Hữu Quang','2022-06-23 20:39:50',NULL,'2022-06-23 20:39:50',1,8,2,NULL),(61,'Hữu Quang','2022-06-23 20:40:57',NULL,'2022-06-23 20:40:57',1,21,2,NULL),(62,'test11','2022-06-23 23:20:21',NULL,'2022-06-23 23:20:21',1,18,6,NULL),(63,'quangpro2000','2022-06-24 08:00:14',NULL,'2022-06-24 08:00:14',1,18,7,NULL),(64,'quangpro2000','2022-06-24 08:00:14',NULL,'2022-06-24 08:00:14',1,8,7,NULL),(65,'quangpro2000','2022-06-24 08:28:53',NULL,'2022-06-24 08:28:53',1,18,7,NULL),(66,'quangpro2000','2022-06-24 08:39:32',NULL,'2022-06-24 08:39:32',1,18,7,NULL),(67,'quangpro2000','2022-06-24 08:39:32',NULL,'2022-06-24 08:39:32',1,21,7,NULL),(68,'quangpro2000','2022-06-24 08:48:33',NULL,'2022-06-24 08:48:33',1,18,7,NULL),(69,'quangpro2000','2022-06-24 08:50:59',NULL,'2022-06-24 08:50:59',1,18,7,NULL),(70,'quangpro2000','2022-06-24 09:05:32',NULL,'2022-06-24 09:05:32',1,18,7,NULL),(71,'quangpro2000','2022-06-24 09:10:47',NULL,'2022-06-24 09:10:47',1,18,7,NULL),(72,'quangpro2000','2022-06-24 09:10:47',NULL,'2022-06-24 09:10:47',1,20,7,NULL),(73,'Hữu Quang','2022-06-24 09:24:25',NULL,'2022-06-24 09:24:25',1,18,2,NULL),(74,'Hữu Quang','2022-06-24 14:40:01',NULL,'2022-06-24 14:40:01',6,18,2,NULL),(75,'Hữu Quang','2022-06-24 14:40:01',NULL,'2022-06-24 14:40:01',2,20,2,NULL),(76,'Hữu Quang','2022-06-24 14:40:01',NULL,'2022-06-24 14:40:01',1,8,2,NULL),(77,'Hữu Quang','2022-07-12 22:09:49',NULL,'2022-07-12 22:09:49',3,1,2,NULL),(78,'Hữu Quang','2022-07-12 22:12:03',NULL,'2022-07-12 22:12:03',4,1,2,NULL),(79,'Hữu Quang','2022-07-12 22:18:15',NULL,'2022-07-12 22:18:15',4,20,2,NULL),(80,'Hữu Quang','2022-07-12 22:18:15',NULL,'2022-07-12 22:18:15',5,21,2,NULL),(81,'Quang Nguyễn Hữu','2022-07-12 22:22:22',NULL,'2022-07-12 22:22:22',1,18,12,NULL),(82,'Hữu Quang','2022-11-29 16:45:56',NULL,'2022-11-29 16:45:56',1,18,2,NULL),(83,'Hữu Quang','2022-11-29 16:45:56',NULL,'2022-11-29 16:45:56',1,8,2,20);
/*!40000 ALTER TABLE `product_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_by` varchar(255) DEFAULT NULL,
  `modifier_date` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `price_ship` double DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES (46,NULL,'2022-11-29 09:40:50','admin','2022-11-29 09:40:50','INTERN','HUU QUANG','0987654321',30000,3),(45,NULL,'2022-06-24 14:43:28','Hữu Quang','2022-06-24 14:43:28','ha noi','ha sấu gái','0963003234234',30000,1),(44,NULL,'2022-06-24 09:25:12','Hữu Quang','2022-06-24 09:25:12','ha nội','testquan','09531232',30000,1),(43,NULL,'2022-06-24 09:11:25','quangpro2000','2022-06-24 09:11:25','ha noi','ok chua','0963089510',30000,1),(42,NULL,'2022-06-24 09:06:07','quangpro2000','2022-06-24 09:06:07','HA NOI','ok','111111111',30000,1),(41,NULL,'2022-06-24 08:51:29','quangpro2000','2022-06-24 08:51:29','111','ertert','345345345',30000,1),(40,NULL,'2022-06-24 08:49:33','quangpro2000','2022-06-24 08:49:33','ha noi','quyendaumoi','0963089510',30000,1),(39,NULL,'2022-06-24 08:39:59','quangpro2000','2022-06-24 08:39:59','ha noi','tesst1','0942342',30000,1),(38,NULL,'2022-06-24 08:29:24','quangpro2000','2022-06-24 08:29:24','ha noi','thu2','0963089510',30000,1),(37,NULL,'2022-06-24 08:09:35','quangpro2000','2022-06-24 08:09:35','ha noi','quangdz','0963089510',30000,1),(36,NULL,'2022-11-29 09:35:23','admin','2022-11-29 09:35:23','Hà nội','test222','0953212312',30000,4),(35,NULL,'2022-06-24 08:31:26','Hữu Quang','2022-06-24 08:31:26','ha tay','mua het','0999999999',30000,1),(34,'Hữu Quang','2022-07-12 22:05:34','admin','2022-07-12 22:05:34','ha noi','mua tang vo','0963089510',30000,4),(33,NULL,'2022-06-24 08:38:24','Hữu Quang','2022-06-24 08:38:24','ha noi','mua tang vo','0963089510',30000,1),(32,NULL,'2022-06-19 16:59:13','quyen','2022-06-19 16:59:13','ha noi','hhh','0904214090',30000,1),(31,NULL,'2022-06-19 16:56:36','quyen','2022-06-19 16:56:36','hà nội','chuẩn','0963089510',30000,1),(29,NULL,'2022-06-19 16:51:44','Hữu Quang','2022-06-19 16:51:44','ha noi','ha hah','0963089510',30000,1),(28,NULL,'2022-06-24 09:17:39','Hữu Quang','2022-06-24 09:17:39','ha nội','huuu','0963089510',30000,1),(27,'quyen','2022-06-19 16:23:21',NULL,'2022-06-19 16:23:21','hà nội','test','0963089510',30000,2),(30,NULL,'2022-06-24 08:24:08','quyen','2022-06-24 08:24:08','ha noi','ok','095300941',30000,1),(47,NULL,'2022-07-12 22:13:04','Hữu Quang','2022-07-12 22:13:04','ha noi','hieuy13','0987654321',30000,1),(48,'Hữu Quang','2022-07-12 22:18:15',NULL,'2022-07-12 22:18:15','ha tay','mua xem sai','6767676766',30000,2),(49,NULL,'2022-07-12 22:23:21','Quang Nguyễn Hữu','2022-07-12 22:23:21','hjhfds','ao','0989089',30000,1),(50,NULL,'2022-11-29 16:47:54','Hữu Quang','2022-11-29 16:47:54','ha noi','thuan bep','132132131',30000,1);
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt_product_historys`
--

DROP TABLE IF EXISTS `receipt_product_historys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt_product_historys` (
  `receipt_entity_id` bigint NOT NULL,
  `product_historys_id` bigint NOT NULL,
  UNIQUE KEY `UK_5kocp3qvm9thcfi8onrj9bh0h` (`product_historys_id`),
  KEY `FK7fx46jqaeee7ub2cjvjkry6t8` (`receipt_entity_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt_product_historys`
--

LOCK TABLES `receipt_product_historys` WRITE;
/*!40000 ALTER TABLE `receipt_product_historys` DISABLE KEYS */;
INSERT INTO `receipt_product_historys` VALUES (27,47),(28,48),(29,50),(29,49),(30,52),(30,51),(31,54),(31,53),(32,56),(32,55),(33,58),(33,57),(34,59),(34,60),(35,61),(36,62),(37,64),(37,63),(38,65),(39,67),(39,66),(40,68),(41,69),(42,70),(43,72),(43,71),(44,73),(45,76),(45,75),(45,74),(46,77),(47,78),(48,79),(48,80),(49,81),(50,83),(50,82);
/*!40000 ALTER TABLE `receipt_product_historys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_by` varchar(255) DEFAULT NULL,
  `modifier_date` datetime DEFAULT NULL,
  `rating` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'admin','2022-06-09 20:43:22',NULL,'2022-06-09 20:43:22',4),(2,'admin','2022-06-09 20:44:05',NULL,'2022-06-09 20:44:05',3),(3,'admin','2022-06-09 20:44:55',NULL,'2022-06-09 20:44:55',5),(4,'admin','2022-06-09 20:45:20',NULL,'2022-06-09 20:45:20',5),(5,'admin','2022-06-09 20:46:00',NULL,'2022-06-09 20:46:00',4),(6,'admin','2022-06-09 20:46:43',NULL,'2022-06-09 20:46:43',2),(7,'admin','2022-06-09 20:53:24',NULL,'2022-06-09 20:53:24',3),(8,'admin','2022-06-09 20:53:51',NULL,'2022-06-09 20:53:51',4),(9,'admin','2022-06-09 20:54:18',NULL,'2022-06-09 20:54:18',5),(10,'admin','2022-06-09 20:54:41',NULL,'2022-06-09 20:54:41',5),(11,'admin','2022-06-09 20:55:19',NULL,'2022-06-09 20:55:19',5),(12,'admin','2022-06-09 20:55:56',NULL,'2022-06-09 20:55:56',5),(13,'admin','2022-06-10 16:51:30','quyen','2022-06-10 16:51:30',1),(14,'admin','2022-06-10 16:51:30','quyen','2022-06-10 16:51:30',1),(15,'admin','2022-06-09 20:59:30',NULL,'2022-06-09 20:59:30',5),(16,'admin','2022-06-09 20:59:45',NULL,'2022-06-09 20:59:45',5),(17,'admin','2022-06-09 21:00:26',NULL,'2022-06-09 21:00:26',2),(18,'admin','2022-06-24 14:43:39','Hữu Quang','2022-06-24 14:43:39',4),(19,'admin','2022-06-09 21:07:58',NULL,'2022-06-09 21:07:58',5),(20,'admin','2022-06-09 21:08:16',NULL,'2022-06-09 21:08:16',2),(21,'admin','2022-06-09 21:08:39',NULL,'2022-06-09 21:08:39',2),(22,'admin','2022-06-09 21:08:58',NULL,'2022-06-09 21:08:58',3),(23,'admin','2022-06-09 21:09:20',NULL,'2022-06-09 21:09:20',5),(24,'admin','2022-06-09 21:09:48',NULL,'2022-06-09 21:09:48',5),(25,'admin','2022-06-09 21:14:09',NULL,'2022-06-09 21:14:09',5),(26,'admin','2022-06-09 21:14:32',NULL,'2022-06-09 21:14:32',5),(27,'admin','2022-06-19 16:59:17','quyen','2022-06-19 16:59:17',5),(28,'admin','2022-06-19 16:51:55','Hữu Quang','2022-06-19 16:51:55',4),(29,'admin','2022-06-24 08:24:10','quyen','2022-06-24 08:24:10',4),(30,'admin','2022-06-09 21:25:43',NULL,'2022-06-09 21:25:43',5),(31,'admin','2022-06-09 21:25:58',NULL,'2022-06-09 21:25:58',5),(32,'admin','2022-06-09 21:26:20',NULL,'2022-06-09 21:26:20',5),(33,'admin','2022-06-09 21:28:57',NULL,'2022-06-09 21:28:57',5),(34,'admin','2022-06-10 17:06:39','quyen','2022-06-10 17:06:39',1),(35,'admin','2022-06-10 17:06:39','quyen','2022-06-10 17:06:39',1),(36,'admin','2022-06-19 16:56:38','quyen','2022-06-19 16:56:38',4),(37,'admin','2022-06-09 21:38:45',NULL,'2022-06-09 21:38:45',5),(38,'admin','2022-06-23 20:14:31',NULL,'2022-06-23 20:14:31',5),(39,'admin','2022-06-24 14:45:54',NULL,'2022-06-24 14:45:54',5),(40,'admin','2022-11-29 11:12:49',NULL,'2022-11-29 11:12:49',5),(41,'admin','2022-11-29 11:13:36',NULL,'2022-11-29 11:13:36',5),(42,'admin','2022-11-30 14:10:46',NULL,'2022-11-30 14:10:46',5),(43,'admin','2022-11-30 14:11:55',NULL,'2022-11-30 14:11:55',5),(44,'admin','2022-11-30 14:12:25',NULL,'2022-11-30 14:12:25',5),(45,'admin','2022-11-30 14:12:54',NULL,'2022-11-30 14:12:54',5),(46,'admin','2022-11-30 14:13:54',NULL,'2022-11-30 14:13:54',5),(47,'admin','2022-11-30 14:14:52',NULL,'2022-11-30 14:14:52',5),(48,'admin','2022-11-30 14:15:40',NULL,'2022-11-30 14:15:40',5),(49,'admin','2022-11-30 14:16:13',NULL,'2022-11-30 14:16:13',5),(50,'admin','2022-11-30 14:17:20',NULL,'2022-11-30 14:17:20',5),(51,'admin','2022-11-30 14:18:25',NULL,'2022-11-30 14:18:25',5),(52,'admin','2022-11-30 14:19:05',NULL,'2022-11-30 14:19:05',5),(53,'admin','2022-11-30 14:19:58',NULL,'2022-11-30 14:19:58',5),(54,'admin','2022-11-30 14:20:53',NULL,'2022-11-30 14:20:53',5),(55,'admin','2022-11-30 14:22:47',NULL,'2022-11-30 14:22:47',5),(56,'admin','2022-11-30 14:23:28',NULL,'2022-11-30 14:23:28',5),(57,'admin','2022-11-30 14:24:02',NULL,'2022-11-30 14:24:02',5),(58,'admin','2022-11-30 14:24:43',NULL,'2022-11-30 14:24:43',5),(59,'admin','2022-11-30 14:27:33',NULL,'2022-11-30 14:27:33',5),(60,'admin','2022-11-30 14:29:57',NULL,'2022-11-30 14:29:57',5),(61,'admin','2022-11-30 14:31:33',NULL,'2022-11-30 14:31:33',5),(62,'admin','2022-11-30 14:32:01',NULL,'2022-11-30 14:32:01',5),(63,'admin','2022-11-30 14:33:04',NULL,'2022-11-30 14:33:04',5),(64,'admin','2022-11-30 14:33:55',NULL,'2022-11-30 14:33:55',5),(65,'admin','2022-11-30 14:34:44',NULL,'2022-11-30 14:34:44',5),(66,'admin','2022-11-30 14:36:30',NULL,'2022-11-30 14:36:30',5),(67,'admin','2022-11-30 14:37:50',NULL,'2022-11-30 14:37:50',5),(68,'admin','2022-11-30 14:38:11',NULL,'2022-11-30 14:38:11',5),(69,'admin','2022-11-30 14:38:38',NULL,'2022-11-30 14:38:38',5),(70,'admin','2022-11-30 14:39:58',NULL,'2022-11-30 14:39:58',5),(71,'admin','2022-11-30 14:40:28',NULL,'2022-11-30 14:40:28',5),(72,'admin','2022-11-30 14:40:53',NULL,'2022-11-30 14:40:53',5),(73,'admin','2022-11-30 14:43:24',NULL,'2022-11-30 14:43:24',5);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modifier_by` varchar(255) DEFAULT NULL,
  `modifier_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `method_login` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` int NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `active` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'unknowUser','2022-06-12 13:26:43','admin','2022-06-12 13:26:43','sdsds@gmail.com','Huuquang',NULL,'$2a$10$NMTTf3LUZHsSeOthro/c7O3X4dZYtJpkBwZnJIjepojH2.yn887hK',0,'admin',1),(2,'Hữu Quang','2022-11-30 14:05:13','Hữu Quang','2022-11-30 14:05:13','coolquanghuu@gmail.com','Hữu Quang','Facebook',NULL,6058,'Hữu Quang',1),(3,'unknowUser','2022-06-10 06:19:07',NULL,'2022-06-10 06:19:07','quyenmail@gmail.com','quyen smile',NULL,'$2a$10$KqkCCrx61WKoJTObSOwAK.hJmmqDqF2eCIVT7AUYJL/38/sQKBKuS',0,'quyen',1),(12,'Quang Nguyễn Hữu','2022-07-12 22:21:57',NULL,'2022-07-12 22:21:57','huuquanghn2000@gmail.com','Quang Nguyễn Hữu','Google',NULL,0,'Quang Nguyễn Hữu',1),(5,'admin','2022-06-23 21:03:26','admin','2022-06-23 21:03:26','1111@gmail.com','ccc',NULL,'$2a$10$2iDKi/9nb7yx3Kex9C9DEuevEjYFbinwzxgm2uRHZ9Ab8yCbau38u',0,'tubeo',0),(6,'admin','2022-06-24 08:20:18','admin','2022-06-24 08:20:18','Quangnh@gmail.com','quangpro',NULL,'$2a$10$xPQl3mzkDFc5xfTf9GOdWuz2auxV3UfpOkIUVjFKcWhVp6O2C6Lqm',0,'test11',0),(7,'unknowUser','2022-06-24 08:44:22','unknowUser','2022-06-24 08:44:22','quangdzxxxxx@gmail.com','Quangpro2000',NULL,'$2a$10$AXPeCLBNx.KrG7p2w7Wd4.0bfUAI5PHbBxdvAnB9D7Nmsj1oPhoA2',2608,'quangpro2000',1),(8,'unknowUser','2022-06-24 08:12:39',NULL,'2022-06-24 08:12:39','quangprox@gmail.com','test',NULL,NULL,0,NULL,NULL),(9,'Nam Phạm','2022-06-24 14:34:52',NULL,'2022-06-24 14:34:52','phamnam1720@gmail.com','Nam Phạm','Google',NULL,0,'Nam Phạm',1),(10,'unknowUser','2022-07-12 22:03:30',NULL,'2022-07-12 22:03:30','hieu@gmail.com','test123212',NULL,'$2a$10$Q6jtZIeQSYQfKX4c3KsFZ.vFu6UsN4qmWqN1vm1JdsFvWKOEWQ6fy',0,'hieupro',1),(11,'unknowUser','2022-07-12 22:16:54',NULL,'2022-07-12 22:16:54','tesydssdf@gmail.com','hieudz',NULL,NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_entity_id` bigint NOT NULL,
  `roles_id` bigint NOT NULL,
  PRIMARY KEY (`user_entity_id`,`roles_id`),
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(2,2),(3,2),(5,2),(6,2),(7,2),(9,2),(10,2),(12,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-30 14:58:07
