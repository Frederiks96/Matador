CREATE TABLE `Vehicle` (`vehicle_id` INT NOT NULL,`v_colour` VARCHAR(8) NOT NULL,`v_type` VARCHAR(10) NOT NULL,PRIMARY KEY (`vehicle_id`));

CREATE TABLE IF NOT EXISTS `Bank` (`account_id` INT NOT NULL,`balance` INT NOT NULL,PRIMARY KEY (`account_id`));

CREATE TABLE IF NOT EXISTS `Player`(`player_id` INT NOT NULL,`vehicle_id` INT NOT NULL,`account_id` INT NOT NULL,`name` VARCHAR(45) NOT NULL,`position` INT NOT NULL,`jail_time` INT NOT NULL,`is_active` BOOLEAN NOT NULL,`turn` BOOLEAN NOT NULL,PRIMARY KEY (`player_id`),CONSTRAINT `vehicle_id`FOREIGN KEY (`vehicle_id`)REFERENCES `Vehicle` (`vehicle_id`),FOREIGN KEY (`account_id`)REFERENCES `Bank` (`account_id`));

CREATE TABLE IF NOT EXISTS `ChanceCard` (`card_id` INT NOT NULL,`player_id` INT NOT NULL,`position` INT NOT NULL,PRIMARY KEY (`card_id`),FOREIGN KEY (`player_id`)REFERENCES `Player` (`player_id`));

CREATE TABLE IF NOT EXISTS `Property` (`field_id` INT NOT NULL,`player_id` INT NOT NULL,`house_count` INT NOT NULL,`hotel` BOOLEAN NOT NULL,`mortgage` BOOLEAN NOT NULL,PRIMARY KEY (`field_id`),FOREIGN KEY (`player_id`)REFERENCES `Player` (`player_id`));