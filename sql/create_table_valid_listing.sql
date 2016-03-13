CREATE TABLE `valid_listing` (
   `listingID` int(11) NOT NULL,
   `make` varchar(50) NOT NULL,
   `model` varchar(50) NOT NULL,
   `year` int(11) NOT NULL,
   PRIMARY KEY (`listingID`),
   KEY `make` (`make`,`model`),
   CONSTRAINT `valid_listing_ibfk_1` FOREIGN KEY (`make`, `model`) REFERENCES `make_model` (`make`, `model`)
 )