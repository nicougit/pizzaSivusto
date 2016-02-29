package login;

import java.util.Scanner;

public class Testeri {

	public static void main(String[] args) {
		
		// Kehitysvaiheen väliaikainen luokka login hashien luontiin ja testaukseen
		
		// Tällä hetkellä tekee SQL-luontilausekkeen Käyttäjä-taulukkoon
		
		Tiiviste tiiviste = new Tiiviste();

		//String username = "admin@pizza.fi";
		//String password = "salasana123";

		// Käyttäjägeneraattori
		Scanner skanneri = new Scanner(System.in);
		
		System.out.print("Syötä käyttäjänimi (email): ");
		String kayttajanimi = skanneri.nextLine();
		System.out.print("Syötä etunimi: ");
		String etunimi = skanneri.nextLine();
		System.out.print("Syötä sukunimi: ");
		String sukunimi = skanneri.nextLine();
		System.out.print("Syötä puhelinnumero: ");
		String puhnro = skanneri.nextLine();
		System.out.print("Syötä salasana: ");
		String salasana = skanneri.nextLine();
		System.out.print("Syötä käyttäjätyyppi (user / staff / admin): ");
		String tyyppi = skanneri.nextLine();
		
		System.out.println(kayttajanimi + " - " + etunimi + " " + sukunimi + " - " + puhnro + " - " + salasana + " - " + tyyppi);
		
		skanneri.close();
		
		try {

			// Generoidaan suola
			String suola = tiiviste.generoiSuola();
			// System.out.println("Suola generoitu: " + suola);

			// Generoidaan suolattu tiiviste
			String pwtiiviste = tiiviste.salaa(salasana, suola, 1);
			// System.out.println("Passutiiviste generoitu: " + pwtiiviste);	
			
			System.out.println("");
			
			// Tulostetaan käyttäjän SQL-luontilauseke
			String kayttajataulukko = "Kayttaja";
			
			System.out.println("INSERT INTO " + kayttajataulukko + " VALUES (");
			System.out.println("null,");
			System.out.println("'" + kayttajanimi + "',");
			System.out.println("'" + etunimi + "',");
			System.out.println("'" + sukunimi + "',");
			System.out.println("'" + puhnro + "',");
			System.out.println("'" + suola + "',");
			System.out.println("'" + pwtiiviste + "',");
			System.out.println("'" + tyyppi + "');");

		} catch (Exception ex) {
			System.out.println("Virhe suolassa tai tiivisteessä - " + ex);
		}

	}

}
