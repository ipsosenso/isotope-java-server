SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
-- -----------------------------------------------------
-- Table `is_mail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `is_mail` (
  `id` BIGINT(20) NOT NULL COMMENT 'Unique identifier',
  `mail_from` VARCHAR(255) NULL COMMENT 'Sender of the mail',
  `mail_reply_to` VARCHAR(255) NULL COMMENT 'Reply to sender',
  `subject` VARCHAR(255) NULL COMMENT 'Subject of the mail',
  `body` MEDIUMTEXT NULL COMMENT 'Body of this mail',
  `creation_date` TIMESTAMP NULL COMMENT 'Creation date',
  `priority` SMALLINT(5) NULL COMMENT 'Priority in submission',
  `status` VARCHAR(10) NULL COMMENT 'Delivery status',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = 'Mail persisted into database';

-- -----------------------------------------------------
-- Table `is_mail_attachement`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `is_mail_attachement` (
  `id` BIGINT(20) NOT NULL COMMENT 'Unique identifier',
  `id_mail` BIGINT(20) NOT NULL COMMENT 'Id of the mail',
  `attachement_name` VARCHAR(255) NULL COMMENT 'Name of the attachement',
  `content` MEDIUMBLOB NULL COMMENT 'Content of the mail (Render already don)',
  `mime_type` VARCHAR(45) NULL COMMENT 'Mime type for this attachement',
  PRIMARY KEY (`id`),
  INDEX `FK_PJ_MAIL_idx` (`id_mail` ASC),
  CONSTRAINT `FK_PJ_MAIL`
    FOREIGN KEY (`id_mail`)
    REFERENCES `is_mail` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = 'Mail Attachement ';


-- -----------------------------------------------------
-- Table `is_mail_recipient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `is_mail_recipient` (
  `id` BIGINT(20) NOT NULL COMMENT 'Unique identifier',
  `id_mail` BIGINT(20) NOT NULL COMMENT 'Id of the mail',
  `mail_to` VARCHAR(255) NULL COMMENT 'Recipient',
  `type` VARCHAR(3) NULL COMMENT 'Recipient type',
  PRIMARY KEY (`id`),
  INDEX `FK_RECIPIENT_MAIL_IDX` (`id_mail` ASC),
  CONSTRAINT `FK_RECIPIENT_MAIL`
    FOREIGN KEY (`id_mail`)
    REFERENCES `is_mail` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = 'Mail Recipient ';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;