/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;

ALTER TABLE user DROP FOREIGN KEY FK_USER_ID_USER_CREA;
ALTER TABLE user DROP FOREIGN KEY FK_USER_ID_USER_MOD;

ALTER TABLE asso_user_group DROP FOREIGN KEY FK_ASSO_USER_GROUP_ID_GROUP;
ALTER TABLE asso_user_group DROP FOREIGN KEY FK_ASSO_USER_GROUP_ID_USER;

ALTER TABLE user MODIFY ID BIGINT(20) NOT NULL AUTO_INCREMENT;
ALTER TABLE user MODIFY ID_USER_CREA BIGINT(20) DEFAULT NULL;
ALTER TABLE user MODIFY ID_USER_MOD BIGINT(20) DEFAULT NULL;

ALTER TABLE group MODIFY id BIGINT(20) NOT NULL;

ALTER TABLE asso_user_group MODIFY id_user BIGINT(20) NOT NULL;
ALTER TABLE asso_user_group MODIFY id_group  BIGINT(20) NOT NULL;

ALTER TABLE user ADD FOREIGN KEY FK_USER_ID_USER_CREA (ID_USER_CREA) REFERENCES user (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE user ADD FOREIGN KEY FK_USER_ID_USER_MOD (ID_USER_CREA) REFERENCES user (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE asso_user_group ADD FOREIGN KEY FK_ASSO_USER_GROUP_ID_GROUP (id_group) REFERENCES group (id) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE asso_user_group ADD FOREIGN KEY FK_ASSO_USER_GROUP_ID_USER (id_user) REFERENCES user (ID) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
  UNIQUE KEY (url, code_langue),
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
  id      BIGINT(20) NOT NULL,
  id_user BIGINT(20) NOT NULL,
  id_role BIGINT(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY (id_user, id_role),
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

ALTER TABLE USER
  DROP COLUMN ROLE;

INSERT INTO is_role VALUES (1, 'admin', '2016-12-23');
INSERT INTO is_role VALUES (2, 'user', '2016-12-23');
INSERT INTO is_role VALUES (3, 'invite', '2016-12-23');

/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;