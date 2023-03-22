CREATE TABLE `temperaturehumiditydata`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `sensor_id` varchar(255) NOT NULL,
  `temperature` decimal(5, 2) NOT NULL,
  `humidity` decimal(5, 2) NOT NULL,
  `timestamp` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
);
