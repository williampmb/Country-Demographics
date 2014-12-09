-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.21 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for demographics
DROP DATABASE IF EXISTS `demographics`;
CREATE DATABASE IF NOT EXISTS `demographics` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `demographics`;


-- Dumping structure for table demographics.continents
DROP TABLE IF EXISTS `continents`;
CREATE TABLE IF NOT EXISTS `continents` (
  `cont_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cont_name` varchar(50) DEFAULT '0',
  KEY `cont_id` (`cont_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- Dumping data for table demographics.continents: ~2 rows (approximately)
DELETE FROM `continents`;
/*!40000 ALTER TABLE `continents` DISABLE KEYS */;
INSERT INTO `continents` (`cont_id`, `cont_name`) VALUES
	(1, 'South America'),
	(2, 'North America');
/*!40000 ALTER TABLE `continents` ENABLE KEYS */;


-- Dumping structure for table demographics.countries
DROP TABLE IF EXISTS `countries`;
CREATE TABLE IF NOT EXISTS `countries` (
  `count_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `count_name` varchar(50) NOT NULL,
  `count_pop` int(10) unsigned DEFAULT NULL,
  `count_area` int(10) unsigned DEFAULT NULL,
  `count_language` varchar(50) DEFAULT NULL,
  `count_timezone` varchar(50) DEFAULT NULL,
  `count_currency` varchar(50) DEFAULT NULL,
  `count_tld` varchar(10) DEFAULT NULL,
  `cont_id` int(10) unsigned NOT NULL,
  `count_flag` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`count_id`),
  KEY `count_id` (`count_id`),
  KEY `cont_id` (`cont_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- Dumping data for table demographics.countries: ~5 rows (approximately)
DELETE FROM `countries`;
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT INTO `countries` (`count_id`, `count_name`, `count_pop`, `count_area`, `count_language`, `count_timezone`, `count_currency`, `count_tld`, `cont_id`, `count_flag`) VALUES
	(1, 'Brazil', 202656788, 6553, 'Portuguese', 'GMT', 'Real', '.br', 1, 'file:/C:/Users/admin/Documents/countrydemos/Country-Demographics/Country%20Demographics/src/country/demographics/flags/Brazil.png'),
	(2, 'United States', 318968000, 9629091, 'English', 'GMT', 'Dollar', '.us', 2, 'file:/C:/Users/admin/Documents/countrydemos/Country-Demographics/Country%20Demographics/src/country/demographics/flags/United-States-of-America(USA).png'),
	(3, 'Argentina', 42669500, 2780400, 'Spanish', 'GMT', 'Peso ($) (ARS)', '.ar', 1, 'file:/C:/Users/admin/Documents/countrydemos/Country-Demographics/Country%20Demographics/src/country/demographics/flags/Argentina.png'),
	(4, 'Canada', 35540419, 9984670, 'English/French', 'GMT', 'Canadian dollar', '.ca', 2, 'file:/C:/Users/admin/Documents/countrydemos/Country-Demographics/Country%20Demographics/src/country/demographics/flags/Canada.png'),
	(5, 'Mexico', 118395054, 1972550, 'Spanish', 'GMT', 'Peso (MXN)', '.mx', 1, 'file:/C:/Users/admin/Documents/countrydemos/Country-Demographics/Country%20Demographics/src/country/demographics/flags/Mexico.png');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;


-- Dumping structure for table demographics.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_type` int(11) NOT NULL,
  KEY `uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- Dumping data for table demographics.users: ~4 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`uid`, `username`, `password`, `user_type`) VALUES
	(1, 'william', 'NCMuZN6fMCEM1ZngF9aaGg==', 1),
	(2, 'jim', 'f8kzbjD21P4xNxDn/9RG9Q==', 1),
	(3, 'simpleuser', 'f8kzbjD21P4xNxDn/9RG9Q==', 0),
	(15, 'bob', 'uRxjOsrPOiAeEx9D24BKfw==', 0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
