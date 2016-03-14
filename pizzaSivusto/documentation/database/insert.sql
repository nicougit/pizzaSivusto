// Testimateriaalin syöttö tietokantaan
// by Reptile Mafia

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

// Muutamat testipizzat
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

// Testipizzoille täytteet
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