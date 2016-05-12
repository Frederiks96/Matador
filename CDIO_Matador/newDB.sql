CREATE TABLE `Vehicle` (`vehicle_id` INT NOT NULL,`v_colour` VARCHAR(8) NOT NULL,`v_type` VARCHAR(10) NOT NULL,PRIMARY KEY (`vehicle_id`));

CREATE TABLE IF NOT EXISTS `Bank` (`account_id` INT NOT NULL,`balance` INT NOT NULL,PRIMARY KEY (`account_id`));

CREATE TABLE IF NOT EXISTS `Player`(`player_id` INT NOT NULL,`vehicle_id` INT NOT NULL,`account_id` INT NOT NULL,`name` VARCHAR(45) NOT NULL,`position` INT NOT NULL,`jail_time` INT NOT NULL,`is_active` BOOLEAN NOT NULL,`turn` BOOLEAN NOT NULL,PRIMARY KEY (`player_id`),CONSTRAINT `vehicle_id`FOREIGN KEY (`vehicle_id`)REFERENCES `Vehicle` (`vehicle_id`),FOREIGN KEY (`account_id`)REFERENCES `Bank` (`account_id`));

CREATE TABLE IF NOT EXISTS `ChanceCard` (`card_id` INT NOT NULL,`player_id` INT NOT NULL,`position` INT NOT NULL,PRIMARY KEY (`card_id`),FOREIGN KEY (`player_id`)REFERENCES `Player` (`player_id`));

CREATE TABLE IF NOT EXISTS `Property` (`field_id` INT NOT NULL,`player_id` INT NOT NULL,`house_count` INT NOT NULL,`mortgage` BOOLEAN NOT NULL,PRIMARY KEY (`field_id`),FOREIGN KEY (`player_id`)REFERENCES `Player` (`player_id`));

INSERT INTO Vehicle VALUES(0,"","");
INSERT INTO Bank VALUES(0,0);
INSERT INTO Player VALUES(0,0,0,"MANAGER",0,0,false,false);

delimiter //
CREATE PROCEDURE save_players (in player_id_in INT, in position_in INT, in jail_time_in INT, in is_active_in BOOLEAN, in turn_in BOOLEAN, in balance_in INT) begin update player set position = position_in where player_id = player_id_in; 
update player set is_active = is_active_in where player_id = player_id_in;
update player set turn = turn_in where player_id = player_id_in;
update player set jail_time = jail_time_in where player_id = player_id_in;
update bank set balance = balance_in where account_id = (select account_id from player where player_id = player_id_in); 
end; //
delimiter ;

delimiter //
CREATE PROCEDURE save_fields (in field_id_in INT, in player_id_in int, in house_count_in INT, in mortgage_in BOOLEAN) begin update property set player_id = player_id_in where field_id = field_id_in;
update property set house_count = house_count_in where field_id = field_id_in;
update property set mortgage = mortgage_in where field_id = field_id_in;
end; //
delimiter ;

delimiter //
CREATE PROCEDURE save_cards (in card_id_in INT, in player_id_in int, in position_in INT) begin update chanceCard set position = position_in where card_id = card_id_in; 
update chanceCard set player_id = player_id_in where card_id = card_id_in;
end; //
delimiter ;

delimiter //
CREATE PROCEDURE buy_field (in field_id_in int, in Player_id_in int, in balance_in int) begin start transaction;
update property set player_id = player_id_in where field_id = field_id_in;
update bank set balance = balance_in where account_id = (select account_id from player where player_id = player_id_in); 
if((select player_id from property where field_id = field_id_in) = player_id_in AND (select balance from bank where account_id = (select account_id from player where player_id = player_id_in)) = balance_in) then commit;
else rollback;
end if;
end; //
delimiter ;

delimiter //
CREATE PROCEDURE money_transfer (player_id_in int, in owner_id_in int, player_balance_in int, in owner_balance_in int) begin start transaction;
update bank set balance = owner_balance_in where account_id = (select account_id from player where player_id = owner_id_in); 
update bank set balance = player_balance_in where account_id = (select account_id from player where player_id = player_id_in); 
if ((select balance from bank where account_id = (select account_id from player where player_id = player_id_in)) = player_balance_in AND (select balance from bank where account_id = (select account_id from player where player_id = owner_id_in)) = owner_balance_in) then commit;
else rollback;
end if;
end; //
delimiter ;


