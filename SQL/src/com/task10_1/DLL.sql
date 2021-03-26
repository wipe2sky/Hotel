CREATE SCHEMA IF NOT EXISTS `mydb`;
USE `mydb` ;

-- DROP TABLE `mydb`.`product`;
-- DROP TABLE `mydb`.`laptop`;
-- DROP TABLE `mydb`.`pc`;
-- DROP TABLE `mydb`.`printer`;
CREATE TABLE IF NOT EXISTS `mydb`.`product` (
  `maker` VARCHAR(10) NOT NULL,
  `model` VARCHAR(50) NOT NULL,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`model`));

CREATE TABLE IF NOT EXISTS `mydb`.`laptop` (
  `code` INT AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `speed` SMALLINT UNSIGNED NOT NULL,
  `ram` SMALLINT UNSIGNED NOT NULL,
  `hd` REAL NOT NULL,
  `price` DECIMAL(7,2) NULL,
  `screen` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`code`),
  INDEX `fk_laptop_product_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `fk_laptop_product`
    FOREIGN KEY (`model`)
    REFERENCES `mydb`.`product` (`model`));

CREATE TABLE IF NOT EXISTS `mydb`.`pc` (
  `code` INT AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `speed` SMALLINT UNSIGNED NOT NULL,
  `ram` SMALLINT UNSIGNED NOT NULL,
  `hd` REAL NOT NULL,
  `cd` VARCHAR(10) NOT NULL,
  `price` DECIMAL(7,2) NULL,
  PRIMARY KEY (`code`),
  INDEX `fk_pc_product_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `fk_pc_product`
    FOREIGN KEY (`model`)
    REFERENCES `mydb`.`product` (`model`));

CREATE TABLE IF NOT EXISTS `mydb`.`printer` (
  `code` INT AUTO_INCREMENT,
  `model` VARCHAR(50) NOT NULL,
  `color` CHAR(1) NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `price` DECIMAL(7,2) NULL,
  PRIMARY KEY (`code`),
  INDEX `fk_printer_product_idx` (`model` ASC) VISIBLE,
  CONSTRAINT `fk_printer_product`
    FOREIGN KEY (`model`)
    REFERENCES `mydb`.`product` (`model`));
