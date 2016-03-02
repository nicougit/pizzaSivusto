package login;

import bean.Kayttaja;
import daot.KayttajaDao;

public class LoginTesti {

	public static void main(String[] args) {
		
		// Testataan loginin toimivuus

		Kayttaja kayttaja = new Kayttaja();

		KayttajaDao kayttajadao = new KayttajaDao();

		String username = "admin@pizza.fi";
		String password = "salasana123";
		
		kayttaja = kayttajadao.kirjaudu(username, password);
		
		System.out.println("Tietokannasta noudettu käyttäjä: " + kayttaja.toString());

	}

}
