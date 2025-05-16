-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: db_hotel
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `tb_admins`
--

DROP TABLE IF EXISTS `tb_admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_admins` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `keys_id` int NOT NULL,
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `keys_id_UNIQUE` (`keys_id`),
  CONSTRAINT `fk_admins_keys` FOREIGN KEY (`keys_id`) REFERENCES `tb_keys` (`keys_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_admins`
--

LOCK TABLES `tb_admins` WRITE;
/*!40000 ALTER TABLE `tb_admins` DISABLE KEYS */;
INSERT INTO `tb_admins` VALUES (2,72);
/*!40000 ALTER TABLE `tb_admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_booking`
--

DROP TABLE IF EXISTS `tb_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_booking` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `client_id` int NOT NULL,
  `room_id` int NOT NULL,
  `serv_id` int DEFAULT '0',
  `check_in` date NOT NULL,
  `check_out` date NOT NULL,
  `booking_status` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`booking_id`),
  KEY `fk_room_idx` (`room_id`),
  KEY `fk_rserv_idx` (`serv_id`),
  KEY `fk_booking_client_idx` (`client_id`),
  CONSTRAINT `fk_booking_client` FOREIGN KEY (`client_id`) REFERENCES `tb_clients` (`client_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_room` FOREIGN KEY (`room_id`) REFERENCES `tb_rooms` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_rserv` FOREIGN KEY (`serv_id`) REFERENCES `tb_roomservices` (`rserv_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_booking`
--

LOCK TABLES `tb_booking` WRITE;
/*!40000 ALTER TABLE `tb_booking` DISABLE KEYS */;
INSERT INTO `tb_booking` VALUES (34,39,8,6,'2025-05-08','2025-05-09',1),(35,40,15,10,'2025-05-11','2025-05-12',1),(37,41,16,6,'2025-05-24','2025-05-25',1),(39,38,15,10,'2025-05-13','2025-05-15',2),(41,40,16,0,'2025-05-22','2025-05-23',1);
/*!40000 ALTER TABLE `tb_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_clients`
--

DROP TABLE IF EXISTS `tb_clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_clients` (
  `client_id` int NOT NULL AUTO_INCREMENT,
  `client_name` varchar(45) NOT NULL,
  `client_surname` varchar(45) NOT NULL,
  `client_email` varchar(45) NOT NULL,
  `client_status` tinyint NOT NULL DEFAULT '1',
  `keys_id` int NOT NULL,
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `keys_id_UNIQUE` (`keys_id`),
  CONSTRAINT `fk_clients_keys` FOREIGN KEY (`keys_id`) REFERENCES `tb_keys` (`keys_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_clients`
--

LOCK TABLES `tb_clients` WRITE;
/*!40000 ALTER TABLE `tb_clients` DISABLE KEYS */;
INSERT INTO `tb_clients` VALUES (38,'Василий','Пупков','vas11@mail.ru',1,71),(39,'Петр','Петров','petro@mail.ru',0,73),(40,'Ольга','Ольха','ola@mail.com',1,74),(41,'Маша','Андронова','mary@gmail.com',1,75);
/*!40000 ALTER TABLE `tb_clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_keys`
--

DROP TABLE IF EXISTS `tb_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_keys` (
  `keys_id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`keys_id`),
  UNIQUE KEY `login_UNIQUE` (`login`) /*!80000 INVISIBLE */
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_keys`
--

LOCK TABLES `tb_keys` WRITE;
/*!40000 ALTER TABLE `tb_keys` DISABLE KEYS */;
INSERT INTO `tb_keys` VALUES (71,'vasgen','11113'),(72,'admin126','admin126'),(73,'petro','petro11'),(74,'olga','olga22'),(75,'cookies','cookies126');
/*!40000 ALTER TABLE `tb_keys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_reviews`
--

DROP TABLE IF EXISTS `tb_reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_reviews` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `rating` int NOT NULL DEFAULT '0',
  `comment` varchar(200) DEFAULT NULL,
  `client_id` int NOT NULL,
  PRIMARY KEY (`review_id`),
  KEY `fk_review_client_idx` (`client_id`),
  CONSTRAINT `fk_review_client` FOREIGN KEY (`client_id`) REFERENCES `tb_clients` (`client_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_reviews`
--

LOCK TABLES `tb_reviews` WRITE;
/*!40000 ALTER TABLE `tb_reviews` DISABLE KEYS */;
INSERT INTO `tb_reviews` VALUES (2,4,'Все хорошо',40),(3,4,'Шикарный отель! \nВсем советую! :))',38),(4,4,'Всё понравилось, спасибо администрации!',41),(5,5,'Очень понравился ваш отель!',38),(6,5,'Все просто супер!!!',38);
/*!40000 ALTER TABLE `tb_reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_rooms`
--

DROP TABLE IF EXISTS `tb_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_rooms` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `room_number` int NOT NULL,
  `room_level` tinyint NOT NULL,
  `rtype_id` int NOT NULL,
  PRIMARY KEY (`room_id`),
  KEY `fk_roomtype` (`rtype_id`),
  CONSTRAINT `fk_roomtype` FOREIGN KEY (`rtype_id`) REFERENCES `tb_roomtype` (`rtype_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_rooms`
--

LOCK TABLES `tb_rooms` WRITE;
/*!40000 ALTER TABLE `tb_rooms` DISABLE KEYS */;
INSERT INTO `tb_rooms` VALUES (1,101,1,1),(2,102,1,1),(3,103,1,1),(4,104,1,1),(5,105,1,1),(6,106,1,1),(7,201,2,2),(8,202,2,2),(9,203,2,2),(10,204,2,3),(11,205,2,3),(12,206,2,3),(13,301,3,4),(14,302,3,4),(15,303,3,5),(16,304,3,5),(17,401,4,6),(18,402,4,6);
/*!40000 ALTER TABLE `tb_rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_roomservices`
--

DROP TABLE IF EXISTS `tb_roomservices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_roomservices` (
  `rserv_id` int NOT NULL AUTO_INCREMENT,
  `rserv_name` varchar(45) NOT NULL,
  `rserv_price` int NOT NULL,
  PRIMARY KEY (`rserv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_roomservices`
--

LOCK TABLES `tb_roomservices` WRITE;
/*!40000 ALTER TABLE `tb_roomservices` DISABLE KEYS */;
INSERT INTO `tb_roomservices` VALUES (0,'нет',0),(5,'сауна',80),(6,'фитнес',70),(7,'бассейн',50),(9,'завтраки',65),(10,'завтраки + сауна',140);
/*!40000 ALTER TABLE `tb_roomservices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_roomtype`
--

DROP TABLE IF EXISTS `tb_roomtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_roomtype` (
  `rtype_id` int NOT NULL AUTO_INCREMENT,
  `rtype_name` varchar(24) NOT NULL,
  `rtype_discription` varchar(250) NOT NULL,
  `rtype_price` int NOT NULL,
  `rtype_square` int NOT NULL,
  `rtype_place` varchar(60) NOT NULL,
  `rtype_explace` varchar(60) NOT NULL,
  PRIMARY KEY (`rtype_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_roomtype`
--

LOCK TABLES `tb_roomtype` WRITE;
/*!40000 ALTER TABLE `tb_roomtype` DISABLE KEYS */;
INSERT INTO `tb_roomtype` VALUES (1,'одноместный','Уютный классический однокомнатный номер для одноместного размещения',200,16,'1 (односпальная кровать)','1 (раскладной диван)'),(2,'двухместный','Классический однокомнатный номер с двумя односпальными кроватями и зоной для отдыха',240,18,'2 (две односпальные кровати)','-'),(3,'дабл','Классический однокомнатный номер с большой двуспальной кроватью и зоной для отдыха с видом на парк.',250,18,'2 (двуспальная кровать)','1 (раскладное кресло)'),(4,'люкс','Просторный двухместный номер, состоящий из спальни и гостиной',360,27,'2 (двуспальная кровать)','-'),(5,'семейный','Трехкомнатный номер, состоящий из 2-х спален и гостиной',420,41,'4 (двуспальная кровать, 2 односпальных кровати)','1 (раскладной диван в гостиной)'),(6,'апартамент','Просторный номер состоит из спальни и элегантной гостиной, включающей обеденную зону, которая может служить местом для отдыха и деловых встреч',800,67,'2 (двуспальная кровать)','-');
/*!40000 ALTER TABLE `tb_roomtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db_hotel'
--
/*!50003 DROP FUNCTION IF EXISTS `calc_cost` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `calc_cost`(room int, serv int, startd date, endd date) RETURNS int
BEGIN	
    select `rtype_id` into @rtype_id from `tb_rooms` 
    where `room_id` = room;    
    
    select `rtype_price` into @price from `tb_roomtype`
    where `rtype_id` = @rtype_id;    
    
    select `rserv_price` into @price_serv from `tb_roomservices`
    where `rserv_id` = serv;
    
    /*select if(serv=0, @price_s, 0) into @price_serv;*/
    
    select datediff(endd, startd) into @days;    
    
RETURN ((@price + @price_serv) * @days);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `find_login`(uslogin VARCHAR(45), uspass VARCHAR(45), OUT id_user INT, OUT usrole VARCHAR(10))
BEGIN
SET id_user = 0;
SET usrole = "";
SELECT keys_id INTO id_user FROM `tb_keys`
WHERE `login` = uslogin AND `password` = uspass;

SELECT COALESCE(ur, "") into usrole
FROM ( select "client" as ur from `tb_clients` where keys_id = id_user
union
select "admin" as ur from `tb_admins` where keys_id = id_user
) as T;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_admin` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_admin`(login varchar(45), pass varchar(45))
BEGIN
	CALL insert_keys(login, pass, @keys_id);
    INSERT INTO `db_hotel`.`tb_admins` (`keys_id`)
	VALUES (@keys_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_booking` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_booking`(c_id int, r_id int, s_id int, c_in date, c_out date)
BEGIN
	insert into `db_hotel`.`tb_booking` (`client_id`, `room_id`, `serv_id`, `check_in`, `check_out`, `booking_status`)
    values (c_id, r_id, s_id, c_in, c_out, 1);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_client` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_client`(firstname varchar(45), lastname varchar(45),
email varchar(45), login varchar(45), pass varchar(45), out id_keys int)
BEGIN
	CALL insert_keys(login, pass, id_keys);
    INSERT INTO `db_hotel`.`tb_clients` (`client_name`, `client_surname`, `client_email`, `keys_id`)
	VALUES (firstname, lastname, email, id_keys);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_keys` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_keys`(login varchar(45), pass varchar(45), out id_keys int)
BEGIN
	insert into `db_hotel`.`tb_keys`(`login`, `password`)
    values (login, pass);
    select last_insert_id() into id_keys;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_review` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_review`(rating int, comm varchar(200), client_id int)
BEGIN
	INSERT INTO `db_hotel`.`tb_reviews` (`rating`, `comment`, `client_id`)
	VALUES (rating, comm, client_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insert_service` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_service`(nameserv varchar(45), price int)
BEGIN
	INSERT INTO `db_hotel`.`tb_roomservices` (`rserv_name`, `rserv_price`)
	VALUES (nameserv, price);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `set_status` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `set_status`(login1 varchar(45), stat int)
BEGIN
	select `keys_id` into @id_k from `tb_keys` where (`login` = login1);
	update `tb_clients` set `client_status` = stat where (keys_id = @id_k);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-14 17:57:32
