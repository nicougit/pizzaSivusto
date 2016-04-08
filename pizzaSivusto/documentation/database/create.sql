// Taulujen luontilausekkeet
// by Reptile Mafia

// Tayte-taulu
CREATE TABLE Tayte (
    tayte_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nimi VARCHAR(20),
    saatavilla CHAR(1)
)Engine=InnoDB;

// Pizza-taulu
CREATE TABLE Pizza (
    pizza_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nimi VARCHAR(30) NOT NULL,
    hinta DECIMAL(5,2) NOT NULL,
    poistomerkinta DATE
)Engine=InnoDB;

// PizzanTayte-taulu
CREATE TABLE PizzanTayte (
    pizza_id INT NOT NULL,
    tayte_id INT NOT NULL,
    PRIMARY KEY (pizza_id, tayte_id),
    FOREIGN KEY (pizza_id) REFERENCES Pizza(pizza_id),
    FOREIGN KEY (tayte_id) REFERENCES Tayte(tayte_id)
)Engine=InnoDB;

// Kayttaja-taulu
CREATE TABLE Kayttaja (
	id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
	tunnus VARCHAR(50) NOT NULL,
	etunimi VARCHAR(50) NOT NULL,
	sukunimi VARCHAR(50) NOT NULL,
	puhelin VARCHAR(12),
	suola CHAR(12) NOT NULL,
	salasana CHAR(88) NOT NULL,
	tyyppi VARCHAR(12) NOT NULL DEFAULT 'user'
)Engine=InnoDB;