CREATE TABLE `location` (
   `siteName` varchar(50) NOT NULL,
   `city` varchar(50) DEFAULT NULL,
   `state` varchar(2) DEFAULT NULL,
   `lastScanDate` date DEFAULT NULL,
   PRIMARY KEY (`siteName`)
 )