// Täytyy vielä testailla näitä. Varsinkin tuota viimeistä taulua. Mutta nämähän ovat virallisesti valmiita vasta maanantaina. ;)

// Muutaman taulun päivitys.
// Juoma, SuosikkiPizza, Toimitusosoite, Tilaus ja Tilausrivi -taulujen luonti.
// by Reptile Mafia

// Mahdollistetaan pidemmät pizzojen nimet.
ALTER TABLE Pizza MODIFY COLUMN nimi VARCHAR(50) NOT NULL;

// Mahdollistetaan pidemmät täytteiden nimet.
ALTER TABLE Tayte MODIFY COLUMN nimi VARCHAR(50) NOT NULL;

// Tilaukselle status, tehdään numeraaliseksi
// 0 = tilaus lähetetty, 1 = tilaus otettu työnalle, 2 = tilaus valmis, 3 = tilaus noudettu / toimitettu
ALTER TABLE Tilaus ADD COLUMN status CHAR(1)

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

// Juomat
INSERT INTO Juoma VALUES (null, 'Sitruunalimonaadi', '3.00', '0.33', 'Laitilan Sitruunasooda', 'K', null);
INSERT INTO Juoma VALUES (null, 'Veriappelsiinilimonaadi', '3.00', '0.33', 'Laitilan Messina', 'K', null);
INSERT INTO Juoma VALUES (null, 'Kolalimonaadi', '3.00', '0.33', 'Laitilan Rio Cola', 'K', null);
INSERT INTO Juoma VALUES (null, 'Vadelmalimonaadi', '3.00',  '0.33', 'Laitilan Herra Hakkaraisen vadelmalimonaadi', 'K', null);
INSERT INTO Juoma VALUES (null, 'Pöytävesi', '3.00', '0.33', 'Laitilan Pore, kevyesti hiilihapotettu', 'K', null);

// SuosikkiPizza-taulu
// Huom! Asiakas voi tallentaa suosikkipizzansa muistiin.
CREATE TABLE SuosikkiPizza (
suosikki_id INT AUTO_INCREMENT NOT NULL,
pizza_id INT NOT NULL,
kayttaja_id INT NOT NULL,
PRIMARY KEY (suosikki_id, pizza_id, kayttaja_id),
FOREIGN KEY (pizza_id) REFERENCES Pizza (pizza_id),
FOREIGN KEY (kayttaja_id) REFERENCES Kayttaja (id)
)Engine=InnoDB;

// Toimitusosoite-taulu
// Huom! Käytetään mikäli asiakas haluaa tallentaa toimitusosoitteen muistiin tilausta tehdessään.
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
toimitusaika DATETIME,
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
