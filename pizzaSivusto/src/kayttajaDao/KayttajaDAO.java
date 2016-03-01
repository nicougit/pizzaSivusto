package kayttajaDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import bean.Kayttaja;
import login.KayttajaLista;
import login.Tiiviste;
import tietokanta.Kysely;
import tietokanta.Yhteys;

public class KayttajaDAO {

	public Kayttaja kirjaudu(String kayttajatunnus, String salasana) {

		// Käyttäjäolion luonti
		Kayttaja kayttaja = new Kayttaja();

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		// Suolan haku
		String suolakysely = "SELECT suola FROM Kayttajat WHERE tunnus = ?";

		ArrayList<String> parametrit = new ArrayList<String>();
		parametrit.add(kayttajatunnus);
		kysely.suoritaYksiKyselyParam(suolakysely, parametrit);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		String suola = null;
		while (iteraattori.hasNext()) {
			HashMap kayttajaMappi = (HashMap) iteraattori.next();
			suola = (String) kayttajaMappi.get("suola");
		}

		// Ei jatketa, jos käyttäjää ei löydy
		if (suola == null) {
			System.out.println("Käyttäjää ei löydy");
			return kayttaja;
		} else {

			// Salasana tiivisteeksi
			Tiiviste tiiviste = new Tiiviste();
			String salasanatiiviste = null;
			try {
				salasanatiiviste = tiiviste.salaa(salasana, suola, 1);
			} catch (Exception ex) {
				System.out.println("Virhe salasanatiivisteen käsittelyssä - " + ex);
			}

			// Käyttäjän haku tietokannasta
			String sqlkysely = "SELECT id, tunnus, etunimi, sukunimi, puhelin FROM Kayttajat WHERE tunnus = ? AND salasana = ?";
			parametrit.add(salasanatiiviste);

			kysely.suoritaYksiKyselyParam(sqlkysely, parametrit);
			tulokset = kysely.getTulokset();

			// Testaillaan

			iteraattori = kysely.getTulokset().iterator();

			while (iteraattori.hasNext()) {
				HashMap kayttajaMappi = (HashMap) iteraattori.next();
				String idString = (String) kayttajaMappi.get("id");
				String tunnusKanta = (String) kayttajaMappi.get("tunnus");
				String etunimiKanta = (String) kayttajaMappi.get("etunimi");
				String sukunimiKanta = (String) kayttajaMappi.get("sukunimi");
				String puhelinKanta = (String) kayttajaMappi.get("puhelin");
				int idKanta = Integer.parseInt(idString);

				// Oliolle attribuutit
				kayttaja.setTunnus(tunnusKanta);
				kayttaja.setId(idKanta);
				kayttaja.setEtunimi(etunimiKanta);
				kayttaja.setSukunimi(sukunimiKanta);
				kayttaja.setPuhelin(puhelinKanta);
			}

			// Yhteyden sulkeminen
			yhteys.suljeYhteys();

			// Käyttäjän palautus
			return kayttaja;
		}
	}

	// Väliaikainen käyttäjätietojen haku
	public ArrayList<KayttajaLista> haeKayttajat() {
		// Suoritetaan haku

		ArrayList<KayttajaLista> lista = new ArrayList<KayttajaLista>();

		Yhteys yhteysolio = new Yhteys();
		Connection yhteys = yhteysolio.getYhteys();

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

				KayttajaLista kayttaja = new KayttajaLista(id, tunnus, etunimi, sukunimi, tyyppi);

				lista.add(kayttaja);

			}
		} catch (Exception ex) {
			System.out.println("Käyttäjien haussa tapahtui virhe.");
		}

		// Yhteyden sulkeminen
		yhteysolio.suljeYhteys();

		return lista;
	}

}
