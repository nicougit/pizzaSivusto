package login;

import bean.Kayttaja;
import kayttajaDao.KayttajaDAO;

public class LoginTesti {

	public static void main(String[] args) {
		
		// Testataan loginin toimivuus

		Kayttaja kayttaja = new Kayttaja();

		KayttajaDAO kayttajadao = new KayttajaDAO();

		String username = "admin@pizza.fi";
		String password = "salasana123";
		
		kayttaja = kayttajadao.kirjaudu(username, password);
		
		kayttaja.toString();

	}

}