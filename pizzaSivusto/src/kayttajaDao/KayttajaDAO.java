package kayttajaDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import login.Kayttaja;

public class KayttajaDAO {
	
	Connection yhteys = null;

	public void avaaYhteys() {
		// Säädöt - Koulun protokanta
//		String username = "a1500955";
//		String password = "paFAtd56t";
//		String url = "jdbc:mysql://localhost:3306/a1500955";
		
		// Säädöt - mun oma servu
		String username = "javatesti";
		String password = "salsasana";
		String url = "jdbc:mysql://192.168.10.35:3306/Pizzat";

		try {
			// Ajurin lataus SQLite
			Class.forName("com.mysql.jdbc.Driver");

			// Yhteyden avaus
			yhteys = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			// Virheet
			System.out.println("Tietokantayhteyden avauksessa tapahtui virhe");
			e.printStackTrace();
		}

	}

	public void suljeYhteys() {
		try {
			if (yhteys != null && !yhteys.isClosed())
				yhteys.close();
		} catch (Exception e) {
			System.out.println("Tietokantayhteys ei jostain syystä suostu menemään kiinni.");
		}
	}
	
	public ArrayList<Kayttaja> haeKayttajat() {
		// Suoritetaan haku

		ArrayList<Kayttaja> lista = new ArrayList<Kayttaja>();

		try {
			String sql = "SELECT id, tunnus, etunimi, sukunimi, tyyppi FROM Kayttajat";
			Statement haku = yhteys.createStatement();
			ResultSet tulokset = haku.executeQuery(sql);

			// Käydään hakutulokset läpi
			while (tulokset.next()) {
				int id = tulokset.getInt("id");
				String tunnus = tulokset.getString("tunnus");
				String etunimi = tulokset.getString("etunimi");
				String sukunimi = tulokset.getString("sukunimi");
				String tyyppi = tulokset.getString("tyyppi");

				// Luodaan käyttäjästä olio
				
				Kayttaja kayttaja = new Kayttaja(id, tunnus, etunimi, sukunimi, tyyppi);

				lista.add(kayttaja);

			}
		} catch (Exception ex) {
			System.out.println("Tietokantahaussa tapahtui virhe.");
		}

		return lista;
	}

}
