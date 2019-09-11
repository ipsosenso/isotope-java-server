-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- ------------------------------------------------------
-- Server version	5.6.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table user
--

DROP TABLE IF EXISTS user;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE user (
  ID                BIGINT(20)      NOT NULL AUTO_INCREMENT,
  LASTNAME          VARCHAR(255) NOT NULL,
  FIRSTNAME         VARCHAR(255) NOT NULL,
  EMAIL             VARCHAR(255) NOT NULL,
  LOGIN             VARCHAR(50)  NOT NULL,
  PASSWORD_HASH     VARCHAR(100) NOT NULL,
  PHONE_NUMBER      VARCHAR(20)           DEFAULT NULL,
  COMPANY           VARCHAR(255)          DEFAULT NULL,
  DISABLED          TINYINT(1)   NOT NULL DEFAULT '0',
  ID_USER_CREA      BIGINT(20)               DEFAULT NULL,
  ID_USER_MOD       BIGINT(20)               DEFAULT NULL,
  DATE_CREATION     DATETIME     NOT NULL,
  DATE_MODIFICATION DATETIME              DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY LOGIN (LOGIN),
  UNIQUE KEY EMAIL (EMAIL),
  KEY IDX_DISABLED (DISABLED),
  KEY FK_USER_ID_USER_CREA (ID_USER_CREA),
  KEY FK_USER_ID_USER_MOD (ID_USER_MOD),
  CONSTRAINT FK_USER_ID_USER_CREA FOREIGN KEY (ID_USER_CREA) REFERENCES user (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_USER_ID_USER_MOD FOREIGN KEY (ID_USER_MOD) REFERENCES user (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table group
--

DROP TABLE IF EXISTS `group`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group` (
  id       BIGINT(20)     NOT NULL,
  name     VARCHAR(45) DEFAULT NULL,
  shortcut VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY IDX_SHORTCUT (shortcut)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table asso_user_group
--

DROP TABLE IF EXISTS asso_user_group;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE asso_user_group (
  id_user  BIGINT(20) NOT NULL,
  id_group BIGINT(20) NOT NULL,
  PRIMARY KEY (id_user, id_group),
  KEY FK_ASSO_USER_GROUP_ID_GROUP_idx (id_group),
  CONSTRAINT FK_ASSO_USER_GROUP_ID_GROUP FOREIGN KEY (id_group) REFERENCES `group` (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT FK_ASSO_USER_GROUP_ID_USER FOREIGN KEY (id_user) REFERENCES user (ID)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;


CREATE TABLE IS_CM_VALUE_LIST (
  ID                BIGINT(20)   NOT NULL DEFAULT '0',
  NAME              VARCHAR(100) NOT NULL DEFAULT '',
  SHORTCUT          VARCHAR(50)  NOT NULL DEFAULT '',
  DESCRIPTION       VARCHAR(255)          DEFAULT NULL,
  CREATOR_ID        BIGINT(20)            DEFAULT NULL,
  CREATOR_NAME      VARCHAR(255) NOT NULL,
  CREATION_DATE     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  MODIFIER_ID       BIGINT(20)            DEFAULT NULL,
  MODIFIER_NAME     VARCHAR(255) NOT NULL,
  MODIFICATION_DATE TIMESTAMP    NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (ID),
  UNIQUE KEY IDX_VALUELIST_SHORTCUT (SHORTCUT),
  KEY FK_CB_CM_VALUE_LIST_CREATOR (CREATOR_ID),
  KEY FK_CB_CM_VALUE_LIST_MODIFIER (MODIFIER_ID)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
CREATE TABLE IS_CM_VALUE_LIST_ENTRY (
  ID            BIGINT(20)   NOT NULL DEFAULT '0',
  VALUE_LIST_ID BIGINT(20)   NOT NULL DEFAULT '0',
  CODE          VARCHAR(255) NOT NULL,
  LABEL         VARCHAR(255) NOT NULL DEFAULT '',
  PRIORITY      SMALLINT(6)  NOT NULL DEFAULT '0',
  DISABLED      TINYINT(1)   NOT NULL DEFAULT '0',
  PRIMARY KEY (ID),
  UNIQUE KEY CB_CM_VALUE_LIST_ENTRY_CODE (VALUE_LIST_ID, CODE) USING BTREE,
  KEY CB_CM_VALUE_LIST_ENTRIES_FK (VALUE_LIST_ID),
  KEY VALUE_LIST_ENTRY_INACTIVE (DISABLED),
  CONSTRAINT FK_IS_CM_VALUE_LIST_ENTRIES FOREIGN KEY (VALUE_LIST_ID) REFERENCES IS_CM_VALUE_LIST (ID)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


DROP TABLE IF EXISTS is_asso_menu_menu_entry;
DROP TABLE IF EXISTS is_menu_entry;
DROP TABLE IF EXISTS is_menu;

DROP TABLE IF EXISTS is_asso_function_role;
DROP TABLE IF EXISTS is_asso_user_role;
DROP TABLE IF EXISTS is_url;
DROP TABLE IF EXISTS is_function;
DROP TABLE IF EXISTS is_role;

CREATE TABLE is_function (
  id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  code          VARCHAR(255) NOT NULL,
  date_creation DATETIME     NOT NULL,
  type          VARCHAR(20)  NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY code (code)
)
  ENGINE = InnoDB;

CREATE TABLE is_url (
  id            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  url           VARCHAR(255) NOT NULL,
  code_langue   VARCHAR(2)   NULL,
  id_function   BIGINT(20)   NOT NULL,
  date_creation DATETIME     NOT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY(url, code_langue),
  CONSTRAINT fk_url_id_function FOREIGN KEY (id_function) REFERENCES is_function (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE TABLE is_role (
  id            BIGINT(20)  NOT NULL AUTO_INCREMENT,
  code          VARCHAR(50) NOT NULL,
  date_creation DATETIME    NOT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY code (code)
)
  ENGINE = InnoDB;

CREATE TABLE is_asso_function_role (
  id          BIGINT(20) NOT NULL,
  id_function BIGINT(20) NOT NULL,
  id_role     BIGINT(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (id_function, id_role),
  CONSTRAINT fk_asso_function_role_id_function FOREIGN KEY (id_function) REFERENCES is_function (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_asso_function_role_id_role FOREIGN KEY (id_role) REFERENCES is_role (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE TABLE is_asso_user_role (
  id_user BIGINT(20) NOT NULL,
  id_role BIGINT(20) NOT NULL,
  PRIMARY KEY (id_user, id_role),
  CONSTRAINT fk_asso_user_role_id_user FOREIGN KEY (id_user) REFERENCES user (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_asso_user_role_id_role FOREIGN KEY (id_role) REFERENCES is_role (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE TABLE is_menu (
  id   BIGINT(20)  NOT NULL,
  code VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY code (code)
)
  ENGINE = InnoDB;

CREATE TABLE is_menu_entry (
  id          BIGINT(20)  NOT NULL,
  code        VARCHAR(50),
  type        VARCHAR(20) NOT NULL,
  icon        TEXT        NULL,
  url         TEXT        NULL,
  id_function BIGINT(20)  NOT NULL,
  id_parent   BIGINT(20)  NULL,
  PRIMARY KEY (id),
  UNIQUE KEY code (code),
  CONSTRAINT fk_asso_is_menu_entry_id_function FOREIGN KEY (id_function) REFERENCES is_function (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_asso_is_menu_entry_id_parent FOREIGN KEY (id_parent) REFERENCES is_menu_entry (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

CREATE TABLE is_asso_menu_menu_entry (
  id            BIGINT(20) NOT NULL,
  id_menu       BIGINT(20) NOT NULL,
  id_menu_entry BIGINT(20) NOT NULL,
  ordre         INT(11)    NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (id_menu, id_menu_entry),
  CONSTRAINT fk_is_asso_menu_menu_entry_id_menu FOREIGN KEY (id_menu) REFERENCES is_menu (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_is_asso_menu_menu_entry_id_menu_entry FOREIGN KEY (id_menu_entry) REFERENCES is_menu_entry (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

INSERT INTO is_role VALUES (1, 'admin', '2016-12-23');
INSERT INTO is_role VALUES (2, 'user', '2016-12-23');
INSERT INTO is_role VALUES (3, 'invite', '2016-12-23');

/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2016-07-19 10:13:10

