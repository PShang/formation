CREATE TABLE IF NOT EXISTS `pizza` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categorie` varchar(255) NOT NULL,
  `code` varchar(8) NOT NULL,
  `nom` varchar(32) NOT NULL,
  `prix` decimal(19,2) NOT NULL,
  `url_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_m169cbpctqeb9bc04mkr6nw7n` (`code`)
);
CREATE TABLE IF NOT EXISTS `performance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `service` varchar(255) NOT NULL,
  `tempsExecution` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
);
