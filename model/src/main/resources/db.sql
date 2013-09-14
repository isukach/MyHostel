CREATE TABLE IF NOT EXISTS `hostel`.`role` (
  `id` DECIMAL(8,0) NOT NULL,
  `name` VARCHAR(50) NOT NULL ,
  `description` TEXT NULL ,
  PRIMARY KEY (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`user` (
  `id` DECIMAL(8,0) NOT NULL,
  `username` VARCHAR(50) NOT NULL ,
  `password` VARCHAR(40) NOT NULL ,
  `active` TINYINT(1)  NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`user_role` (
  `user_id` DECIMAL(8,0) NOT NULL ,
  `role_id` DECIMAL(8,0) NOT NULL ,
  PRIMARY KEY (`user_id`, `role_id`) ,
    FOREIGN KEY (`user_id` )
    REFERENCES `hostel`.`user` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`role_id` )
    REFERENCES `hostel`.`role` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE  TABLE IF NOT EXISTS `hostel`.`hostel` (
  `id` DECIMAL(8,0) NOT NULL ,
  `number` INT NOT NULL UNIQUE ,
  `address` VARCHAR(45) NOT NULL ,
  `stages` INT NOT NULL ,
  `isBlockType` TINYINT(1) NOT NULL DEFAULT 1 ,
  `rank` INT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE  TABLE IF NOT EXISTS `hostel`.`floor` (
  `id` DECIMAL(8,0) NOT NULL ,
  `number` VARCHAR(20) NOT NULL,
  `floor_head` VARCHAR(45),
  `educator` VARCHAR(45),
  `rooms` INT NOT NULL ,
  `hostel_id` DECIMAL(8,0) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_floor_hostel1`
    FOREIGN KEY (`hostel_id` )
    REFERENCES `hostel`.`hostel` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE  TABLE IF NOT EXISTS `hostel`.`room` (
  `id` DECIMAL(8,0) NOT NULL ,
  `number` VARCHAR(10) NOT NULL,
  `persons` INT ,
  `floor_id` DECIMAL(8,0) NOT NULL ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `fk_room_floor1`
    FOREIGN KEY (`floor_id` )
    REFERENCES `hostel`.`floor` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`person` (
  `id` DECIMAL(8,0) NOT NULL ,
  `firstName` VARCHAR(30) NOT NULL ,
  `lastName` VARCHAR(30) NOT NULL ,
  `middleName` VARCHAR(30) NULL ,
  `faculty` VARCHAR(45) NULL ,
  `residence_status` VARCHAR(45) NOT NULL ,
  `univ_group` VARCHAR(6) NULL ,
  `about` TEXT NULL ,
  `facilities` TEXT NULL ,
  `from_city` VARCHAR(100) NULL,
  `avatar_path` VARCHAR(100) NULL,
  `email` VARCHAR(75) NULL ,
  `tel` VARCHAR(20) NULL ,
  `required_duties` DECIMAL(8,0) NULL,
  `extra_duties` DECIMAL(8,0) NULL,
  `user_id` DECIMAL(8,0) NOT NULL ,
  `room_id` DECIMAL(8,0) NULL,
  `work_id` DECIMAL(8,0) NULL,
  `lan_id` DECIMAL(8,0) NULL,
  PRIMARY KEY (`id`) ,
    FOREIGN KEY (`user_id` )
    REFERENCES `hostel`.`user` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE ,
    FOREIGN KEY (`room_id` )
    REFERENCES `hostel`.`room` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`work` (
  `id` DECIMAL(8,0) NOT NULL ,
  `requiredHours` INT NOT NULL ,
  `penaltyHours` INT NOT NULL ,
  `person_id` DECIMAL(8,0) NULL ,
  PRIMARY KEY (`id`) ,
  FOREIGN KEY (`person_id` )
    REFERENCES `hostel`.`person` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`job_offer` (
  `id` DECIMAL(8,0) NOT NULL,
  `date` DATE NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  `hours` INT NOT NULL,
  `number_of_people` INT NOT NULL,
  `isActive` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`person_job_offer` (
  `person_id` DECIMAL(8, 0) NOT NULL,
  `jobOffer_id` DECIMAL(8, 0) NOT NULL,
  FOREIGN KEY (`person_id` )
    REFERENCES `hostel`.`person` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`jobOffer_id` )
    REFERENCES `hostel`.`job_offer` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE  TABLE IF NOT EXISTS `hostel`.`lan` (
  `id` DECIMAL(8,0) NOT NULL ,
  `ip` VARCHAR(45) NULL ,
  `january` TINYINT(1) NOT NULL DEFAULT 0,
  `february` TINYINT(1) NOT NULL DEFAULT 0,
  `march` TINYINT(1) NOT NULL DEFAULT 0,
  `april` TINYINT(1) NOT NULL DEFAULT 0,
  `may` TINYINT(1) NOT NULL DEFAULT 0,
  `june` TINYINT(1) NOT NULL DEFAULT 0,
  `july` TINYINT(1) NOT NULL DEFAULT 0,
  `august` TINYINT(1) NOT NULL DEFAULT 0,
  `september` TINYINT(1) NOT NULL DEFAULT 0,
  `october` TINYINT(1) NOT NULL DEFAULT 0,
  `november` TINYINT(1) NOT NULL DEFAULT 0,
  `december` TINYINT(1) NOT NULL DEFAULT 0,
  `year` INT NOT NULL,
  `activated` TINYINT(1) NOT NULL DEFAULT 0,
  `version` DECIMAL(8,0) NOT NULL,
  `person_id` DECIMAL(8,0) NULL ,
  PRIMARY KEY (`id`) ,
  FOREIGN KEY (`person_id` )
    REFERENCES `hostel`.`person` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE  TABLE IF NOT EXISTS `hostel`.`links` (
  `id` DECIMAL(8,0) NOT NULL ,
  `facebook` VARCHAR(100) NULL ,
  `twitter` VARCHAR(100) NULL ,
  `vk` VARCHAR(100) NULL ,
  `skype` VARCHAR(100) NULL ,
  `devart` VARCHAR(100) NULL ,
  `vimeo` VARCHAR(100) NULL ,
  `google` VARCHAR(100) NULL ,
  `lastfm` VARCHAR(100) NULL ,
  `youtube` VARCHAR(100) NULL ,
  `person_id` DECIMAL(8,0) NULL ,
  PRIMARY KEY (`id`) ,
  FOREIGN KEY (`person_id` )
    REFERENCES `hostel`.`person` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`job` (
  `id` DECIMAL(8,0) NOT NULL ,
  `date` DATE NOT NULL ,
  `hours` INT NOT NULL ,
  `description` VARCHAR(500) NULL ,
  `work_id` DECIMAL(8, 0) NOT NULL,
  PRIMARY KEY (`id`),
   FOREIGN KEY (`work_id`)
   REFERENCES `hostel`.`work` (`id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`duty` (
  `id` DECIMAL(8,0) NOT NULL,
  `date` DATE NOT NULL,
  `shift` VARCHAR(50) NOT NULL,
  `status` VARCHAR(50) NOT NULL,
  `status_comment` VARCHAR(200) NULL,
  `person_id` DECIMAL(8, 0),
  `version` DECIMAL(8,0) NOT NULL,
  `closed` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) ,
  FOREIGN KEY (`person_id` )
  REFERENCES `hostel`.`person` (`id` )
  ON DELETE CASCADE
  ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`month` (
  `id` DECIMAL(8,0) NOT NULL,
  `month` INT NOT NULL,
  `year` INT NOT NULL,
  `floor_id` DECIMAL(8,0) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY(`id`),
    FOREIGN KEY (`floor_id`)
    REFERENCES `hostel`.`floor` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hostel`.`month_duty` (
  `month_id` DECIMAL(8,0) NOT NULL ,
  `duty_id` DECIMAL(8,0) NOT NULL UNIQUE,
  PRIMARY KEY (`month_id`, `duty_id`) ,
    FOREIGN KEY (`month_id` )
    REFERENCES `hostel`.`month` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`duty_id` )
    REFERENCES `hostel`.`duty` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;

CREATE  TABLE IF NOT EXISTS `hostel`.`news` (
  `id` DECIMAL(8,0) NOT NULL ,
  `category` VARCHAR(20) NOT NULL ,
  `timestamp` DATETIME NOT NULL ,
  `caption` VARCHAR(200) NULL DEFAULT NULL ,
  `text` MEDIUMTEXT NULL DEFAULT NULL ,
  `courses` VARCHAR(20) NULL DEFAULT NULL ,
  `person_id` DECIMAL(8,0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
    CONSTRAINT `fk_news_person`
    FOREIGN KEY (`person_id` )
    REFERENCES `hostel`.`person` (`id` )
    ON DELETE SET NULL
    ON UPDATE SET NULL)
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;


CREATE  TABLE IF NOT EXISTS `hostel`.`floor_news` (
  `news_id` DECIMAL(8,0) NOT NULL ,
  `floor_id` DECIMAL(8,0) NOT NULL ,
  PRIMARY KEY (`news_id`, `floor_id`) ,
  INDEX `floor_id` (`floor_id` ASC) ,
  FOREIGN KEY (`floor_id` )
  REFERENCES `hostel`.`floor` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  FOREIGN KEY (`news_id` )
  REFERENCES `hostel`.`news` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;


CREATE  TABLE IF NOT EXISTS `hostel`.`course` (
  `id` DECIMAL(8,0) NOT NULL ,
  `course` INT(11) NOT NULL ,
  `news_id` DECIMAL(8,0) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_courses_news` (`news_id` ASC),
  FOREIGN KEY (`news_id` )
  REFERENCES `hostel`.`news` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `hostel`.`penalty_reward` (
  `id` INT NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `date` DATE NULL ,
  `orderNumber` VARCHAR(45) NOT NULL ,
  `comment` TEXT NULL ,
  `person_id` DECIMAL(8,0) NOT NULL ,
  PRIMARY KEY (`id`) ,
    FOREIGN KEY (`person_id` )
    REFERENCES `hostel`.`person` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;


CREATE  TABLE IF NOT EXISTS `hostel`.`notification` (
  `id` DECIMAL(8,0) NOT NULL ,
  `text` TINYTEXT NULL ,
  `header` TINYTEXT NOT NULL ,
  `type` VARCHAR(30) NOT NULL ,
  `date` DATETIME NOT NULL ,
  `header_params` VARCHAR(100) NULL,
  `text_params` VARCHAR(100) NULL,
  `viewed` BIT NOT NULL,
  `person_id` DECIMAL(8,0) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_notification_person1_idx` (`person_id` ASC) ,
  CONSTRAINT `fk_notification_person1`
    FOREIGN KEY (`person_id` )
    REFERENCES `hostel`.`person` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `hostel`.`system_setting` (
  `id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `enabled` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_swedish_ci ROW_FORMAT=DYNAMIC;