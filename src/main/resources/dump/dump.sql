CREATE TABLE IF NOT EXISTS `cartao` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NUMERO_CARTAO` VARCHAR(255) NOT NULL,
  `SENHA` VARCHAR(255) NOT NULL,
  `SALDO` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB