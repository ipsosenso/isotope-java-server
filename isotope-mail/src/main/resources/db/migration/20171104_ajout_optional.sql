ALTER TABLE `is_mail`
CHANGE COLUMN `mail_from` `mail_from` VARCHAR(255) NOT NULL COMMENT 'Sender of the mail' ,
CHANGE COLUMN `subject` `subject` VARCHAR(255) NOT NULL COMMENT 'Subject of the mail' ,
CHANGE COLUMN `body` `body` MEDIUMTEXT NOT NULL COMMENT 'Body of this mail' ,
CHANGE COLUMN `creation_date` `creation_date` TIMESTAMP NOT NULL COMMENT 'Creation date' ,
CHANGE COLUMN `priority` `priority` SMALLINT(5) NOT NULL DEFAULT 0 COMMENT 'Priority in submission' ,
CHANGE COLUMN `status` `status` VARCHAR(10) NOT NULL COMMENT 'Delivery status' ;

ALTER TABLE `is_mail_attachement`
CHANGE COLUMN `attachement_name` `attachement_name` VARCHAR(255) NOT NULL COMMENT 'Name of the attachement' ,
CHANGE COLUMN `content` `content` MEDIUMBLOB NOT NULL COMMENT 'Content of the mail (Render already don)' ,
CHANGE COLUMN `mime_type` `mime_type` VARCHAR(45) NOT NULL COMMENT 'Mime type for this attachement' ;

ALTER TABLE `is_mail_recipient`
CHANGE COLUMN `mail_to` `mail_to` VARCHAR(255) NOT NULL COMMENT 'Recipient' ,
CHANGE COLUMN `type` `type` VARCHAR(3) NOT NULL COMMENT 'Recipient type' ;
