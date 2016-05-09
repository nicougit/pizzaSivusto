// Tämä korvaa aiemmat SQL-dokumentaatiot
// Create table- ja insert into -lauseet
// by Reptile Mafia

// Taulujen luonti tietokantaan: create table

// Tayte-taulu
CREATE TABLE Tayte (
    tayte_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nimi VARCHAR(50),
    saatavilla CHAR(1)
)Engine=InnoDB;

// Pizza-taulu
CREATE TABLE Pizza (
    pizza_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nimi VARCHAR(50) NOT NULL,
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

// Juoma-taulu
CREATE TABLE Juoma (
juoma_id INT AUTO_INCREMENT NOT NULL,
nimi VARCHAR(50) NOT NULL,
hinta DECIMAL(5,2) NOT NULL,
koko DECIMAL(5,2) NOT NULL,
kuvaus VARCHAR(255),
saatavilla CHAR(1),
poistomerkinta DATE,
PRIMARY KEY (juoma_id)
)Engine=InnoDB;

// SuosikkiPizza-taulu
CREATE TABLE SuosikkiPizza (
suosikki_id INT AUTO_INCREMENT NOT NULL,
pizza_id INT NOT NULL,
kayttaja_id INT NOT NULL,
PRIMARY KEY (suosikki_id, pizza_id, kayttaja_id),
FOREIGN KEY (pizza_id) REFERENCES Pizza (pizza_id),
FOREIGN KEY (kayttaja_id) REFERENCES Kayttaja (id)
)Engine=InnoDB;

// Toimitusosoite-taulu
CREATE TABLE Toimitusosoite (
osoite_id INT AUTO_INCREMENT NOT NULL,
kayttaja_id INT NOT NULL,
toimitusosoite VARCHAR(50) NOT NULL,
postinro CHAR(5) NOT NULL,
postitmp VARCHAR(50) NOT NULL,
poistomerkinta DATE NULL,
PRIMARY KEY (osoite_id, kayttaja_id),
FOREIGN KEY (kayttaja_id) REFERENCES Kayttaja(id)
)Engine=InnoDB;

// Tilaus-taulu
CREATE TABLE Tilaus (
tilaus_id INT AUTO_INCREMENT NOT NULL,
kayttaja_id INT NOT NULL,
osoite_id INT NULL,
tilaushetki DATETIME NOT NULL,
toimitustapa VARCHAR(50) NOT NULL,
lisatiedot VARCHAR(255),
kokonaishinta DECIMAL(7,2) NOT NULL,
maksutapa VARCHAR(50) NOT NULL,
maksutilanne CHAR(1),
status CHAR(1),
PRIMARY KEY (tilaus_id),
FOREIGN KEY (kayttaja_id) REFERENCES Kayttaja(id),
FOREIGN KEY (osoite_id) REFERENCES Toimitusosoite(osoite_id)
)Engine=InnoDB;

// Tilausrivi-taulu
CREATE TABLE Tilausrivi (
rivi_id INT AUTO_INCREMENT NOT NULL,
tilaus_id INT NOT NULL,
tuote_pizza INT NULL,
tuote_juoma INT NULL,
laktoositon CHAR(1),
gluteeniton CHAR(1),
oregano CHAR(1),
valkosipuli CHAR(1),
lkm INT,
PRIMARY KEY (rivi_id, tilaus_id),
FOREIGN KEY (tilaus_id) REFERENCES Tilaus(tilaus_id),
CONSTRAINT a FOREIGN KEY (tuote_pizza) REFERENCES Pizza(pizza_id),
CONSTRAINT b FOREIGN KEY (tuote_juoma) REFERENCES Juoma(juoma_id),
CONSTRAINT c CHECK(
CASE WHEN Pizza IS NULL THEN 0 ELSE 1 END +
CASE WHEN Juoma IS NULL THEN 0 ELSE 1 END = 1)
)Engine=InnoDB;

// Testiaineiston syöttö tietokantaan: insert into

// Täytteet
INSERT INTO Tayte VALUES (null, 'Ananas', 'K');
INSERT INTO Tayte VALUES (null, 'Aurajuusto', 'K');
INSERT INTO Tayte VALUES (null, 'BBQ-kastike', 'K');
INSERT INTO Tayte VALUES (null, 'Fetajuusto', 'K');
INSERT INTO Tayte VALUES (null, 'Herkkusieni', 'K');
INSERT INTO Tayte VALUES (null, 'Jauheliha', 'K');
INSERT INTO Tayte VALUES (null, 'Jalopeno', 'K');
INSERT INTO Tayte VALUES (null, 'Kana', 'K');
INSERT INTO Tayte VALUES (null, 'Kapris', 'K');
INSERT INTO Tayte VALUES (null, 'Katkarapu', 'K');
INSERT INTO Tayte VALUES (null, 'Kebabliha', 'K');
INSERT INTO Tayte VALUES (null, 'Kinkku', 'K');
INSERT INTO Tayte VALUES (null, 'Mozzarellajuusto', 'K');
INSERT INTO Tayte VALUES (null, 'Oliivi', 'K');
INSERT INTO Tayte VALUES (null, 'Paprika', 'K');
INSERT INTO Tayte VALUES (null, 'Pekoni', 'K');
INSERT INTO Tayte VALUES (null, 'Pepperoni', 'K');
INSERT INTO Tayte VALUES (null, 'Salami', 'K');
INSERT INTO Tayte VALUES (null, 'Simpukka', 'K');
INSERT INTO Tayte VALUES (null, 'Sipuli', 'K');
INSERT INTO Tayte VALUES (null, 'Smetana', 'K');
INSERT INTO Tayte VALUES (null, 'Tomaatti', 'K');
INSERT INTO Tayte VALUES (null, 'Tonnikala', 'K');
INSERT INTO Tayte VALUES (null, 'Valkosipuli', 'K');

// Nämä testipizzat ja testipizzojen täyteet on myöhemmin korvattu pizzerian imagoon paremmin sopivilla pizzoilla
// Ensimmäiset testipizzat
INSERT INTO Pizza VALUES (null, 'Bolognese', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Fransescana', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Americano', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Fetapizza', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Frutti Di Mare', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Julia', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Romeo', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Quattro', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Vegetariana', 6.50, null);
INSERT INTO Pizza VALUES (null, 'Chicken Hawaii', 6.50, null);

// Ensimmäiset testipizzojen täytteet
INSERT INTO PizzanTayte VALUES (1, 6)
INSERT INTO PizzanTayte VALUES (2, 5)
INSERT INTO PizzanTayte VALUES (2, 12)
INSERT INTO PizzanTayte VALUES (3, 1)
INSERT INTO PizzanTayte VALUES (3, 2)
INSERT INTO PizzanTayte VALUES (3, 12)
INSERT INTO PizzanTayte VALUES (4, 4)
INSERT INTO PizzanTayte VALUES (4, 14)
INSERT INTO PizzanTayte VALUES (4, 20)
INSERT INTO PizzanTayte VALUES (4, 22)
INSERT INTO PizzanTayte VALUES (5, 10)
INSERT INTO PizzanTayte VALUES (5, 23)
INSERT INTO PizzanTayte VALUES (6, 1)
INSERT INTO PizzanTayte VALUES (6, 2)
INSERT INTO PizzanTayte VALUES (6, 10)
INSERT INTO PizzanTayte VALUES (6, 12)
INSERT INTO PizzanTayte VALUES (7, 1)
INSERT INTO PizzanTayte VALUES (7, 2)
INSERT INTO PizzanTayte VALUES (7, 10)
INSERT INTO PizzanTayte VALUES (7, 18)
INSERT INTO PizzanTayte VALUES (8, 5)
INSERT INTO PizzanTayte VALUES (8, 10)
INSERT INTO PizzanTayte VALUES (8, 12)
INSERT INTO PizzanTayte VALUES (8, 15)
INSERT INTO PizzanTayte VALUES (9, 5)
INSERT INTO PizzanTayte VALUES (9, 14)
INSERT INTO PizzanTayte VALUES (9, 20)
INSERT INTO PizzanTayte VALUES (9, 22)
INSERT INTO PizzanTayte VALUES (10, 1)
INSERT INTO PizzanTayte VALUES (10, 2)
INSERT INTO PizzanTayte VALUES (10, 8)

// Käyttäjät - kaikilla salasana = salasana123
INSERT INTO Kayttaja VALUES (null, 'admin@pizza.fi', 'Antti', 'Admin', null, 'F8bF2H0xQ88=', 'Qguq08IoKtQvD+U1KLyY3cYunk/hDtR3X6VQSNd+SDLYozOEAT99tkiP3lsPxe8ftYx4KcNGaAUsTlR0NIoqWw==', 'admin')
INSERT INTO Kayttaja VALUES (null, 'staff@pizza.fi', 'Gandalf', 'Velho', null, 'Oqzp2NylnjU=', 'GLH2ug3MSRAGlO+EaLKDm+rg91hnI3LLCF5AcZQfi7/eYm4vDX5aobB8Z2cImKsZ3ALlbGs7Mem1LYZosyMrNw==', 'staff')
INSERT INTO Kayttaja VALUES (null, 'user@pizza.fi', 'Ulla', 'Useri', '0401231231', 'n6WPpwcD9EY=', 'lWhH4P6LwtG+PZl00EWnhd7C2n7d32cOl5L5oTYYWVPhR4qKAjm6SoPHsnuNIlO0ESRT9f1iihwlbZJkFH/T1A==', 'user')

// Juomat
INSERT INTO Juoma VALUES (null, 'Sitruunalimonaadi', '3.00', '0.33', 'Laitilan Sitruunasooda', 'K', null);
INSERT INTO Juoma VALUES (null, 'Veriappelsiinilimonaadi', '3.00', '0.33', 'Laitilan Messina', 'K', null);
INSERT INTO Juoma VALUES (null, 'Kolalimonaadi', '3.00', '0.33', 'Laitilan Rio Cola', 'K', null);
INSERT INTO Juoma VALUES (null, 'Vadelmalimonaadi', '3.00',  '0.33', 'Laitilan Herra Hakkaraisen vadelmalimonaadi', 'K', null);
INSERT INTO Juoma VALUES (null, 'Pöytävesi', '3.00', '0.33', 'Laitilan Pore, kevyesti hiilihapotettu', 'K', null);

// Testitilaus esimerkki
// Tilauksen lisäys
INSERT INTO Tilaus VALUES (null, 3, 7, NOW(), "toimitus", NOW(), null, 100, "kateinen", "E");
// Pizzan lisäys
INSERT INTO Tilausrivi VALUES (null, 1, 35, null, "E", "E", "E", "E", 1);
// Juoman lisäys
INSERT INTO Tilausrivi VALUES (null, 1, null, 4, "E", "E", "E", "E", 1);
// Kokonaishinnan päivitys
UPDATE Tilaus SET kokonaishinta = 
(SELECT ((SELECT SUM(hinta) FROM Tilausrivi JOIN Juoma p ON tuote_juoma = juoma_id WHERE tilaus_id = 1) + 
(SELECT SUM(hinta) FROM Tilausrivi JOIN Pizza p ON tuote_pizza = pizza_id  WHERE tilaus_id = 1)) AS yhteishinta) WHERE tilaus_id = 1;
