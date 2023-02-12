CREATE TABLE IF NOT EXISTS `artifact_type_enum`(
    `artifact_type_abbrevation` varchar(45) NOT NULL,
    `artifact_type_name` varchar(45) NOT NULL,
    PRIMARY KEY(`artifact_type_abbrevation`)
    );

CREATE TABLE IF NOT EXISTS `artifact_stats_type_enum`(
    `artifact_stats_type_abbrevation` varchar(45) NOT NULL,
    `artifact_stats_type_name` varchar(45) NOT NULL,
    PRIMARY KEY(`artifact_stats_type_abbrevation`)
    );

CREATE TABLE IF NOT EXISTS `artifact_set_type_enum`(
    `artifact_set_type_abbrevation` varchar(45) NOT NULL,
    `artifact_set_type_name` varchar(45) NOT NULL,
    PRIMARY KEY(`artifact_set_type_abbrevation`)
);


CREATE TABLE IF NOT EXISTS  `artifact`
(
    `artifact_id`                        INT NOT NULL AUTO_INCREMENT,
    `artifact_date`                      timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `artifact_set`                       varchar(45)  DEFAULT NULL,
    `artifact_type`                      varchar(45)  DEFAULT NULL,
    `artifact_main_stats`                varchar(45)  DEFAULT NULL,
    `artifact_sub_initial_stats_1`       varchar(45)  DEFAULT NULL,
    `artifact_sub_initial_stats_1_value` DOUBLE       DEFAULT NULL,
    `artifact_sub_initial_stats_2`       varchar(45)  DEFAULT NULL,
    `artifact_sub_initial_stats_2_value` DOUBLE        DEFAULT NULL,
    `artifact_sub_initial_stats_3`       varchar(45)  DEFAULT NULL,
    `artifact_sub_initial_stats_3_value` DOUBLE        DEFAULT NULL,
    `artifact_sub_initial_stats_4`       varchar(45)  DEFAULT NULL,
    `artifact_sub_initial_stats_4_value` DOUBLE        DEFAULT NULL,
    PRIMARY KEY (`artifact_id`),
    CONSTRAINT `artifact_set_fk` FOREIGN KEY(`artifact_set`) REFERENCES `artifact_set_type_enum` (`artifact_set_type_abbrevation`),
    CONSTRAINT `artifact_type_fk` FOREIGN KEY(`artifact_type`) REFERENCES `artifact_type_enum`(`artifact_type_abbrevation`)
);

CREATE TABLE IF NOT EXISTS `artifact_enchanting` (
                                       `artifact_id` int NOT NULL,
                                       `artifact_enchanting_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                       `artifact_enchanting_1` varchar(45) DEFAULT NULL,
                                       `artifact_enchanting_1_value` double DEFAULT NULL,
                                       `artifact_enchanting_2` varchar(45) DEFAULT NULL,
                                       `artifact_enchanting_2_value` double DEFAULT NULL,
                                       `artifact_enchanting_3` varchar(45) DEFAULT NULL,
                                       `artifact_enchanting_3_value` double DEFAULT NULL,
                                       `artifact_enchanting_4` varchar(45) DEFAULT NULL,
                                       `artifact_enchanting_4_value` double DEFAULT NULL,
                                       `artifact_enchanting_5` varchar(45) DEFAULT NULL,
                                       `artifact_enchanting_5_value` varchar(45) DEFAULT NULL,
                                       PRIMARY KEY (`artifact_id`),
                                       CONSTRAINT `foreign_key` FOREIGN KEY (`artifact_id`) REFERENCES `artifact` (`artifact_id`)
);

CREATE TABLE IF NOT EXISTS `users`(
    `name` varchar(255) NOT NULL,
    `authority` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY(`name`)
);


