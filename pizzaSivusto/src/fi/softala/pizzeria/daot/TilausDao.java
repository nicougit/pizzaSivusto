package fi.softala.pizzeria.daot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fi.softala.pizzeria.bean.Kayttaja;
import fi.softala.pizzeria.bean.Tilaus;
import fi.softala.pizzeria.tietokanta.Kysely;
import fi.softala.pizzeria.tietokanta.Yhteys;

public class TilausDao {
	
	public ArrayList<Tilaus> haeTilaukset() {
		ArrayList<Tilaus> tilaukset = new ArrayList<>();

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		
		if (yhteys.getYhteys() == null) {
			return tilaukset;
		}
		
		Kysely kysely = new Kysely(yhteys.getYhteys());
		
		// Haetaan käyttäjän osoitteet
		String sql = "SELECT tilaus_id, tilaushetki, kokonaishinta, lisatiedot, maksutapa, toimitustapa, maksutilanne, status, k.id, k.etunimi, k.sukunimi, k.puhelin, k.tunnus FROM Tilaus JOIN Kayttaja k ON kayttaja_id = k.id ORDER BY tilaus_id DESC";

		kysely.suoritaKysely(sql);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		Iterator iteraattori = kysely.getTulokset().iterator();

		while (iteraattori.hasNext()) {
			HashMap map = (HashMap) iteraattori.next();
			String idStr = (String) map.get("tilaus_id");
			String tilaushetkiStr = (String) map.get("tilaushetki");
			String toimitustapa = (String) map.get("toimitustapa");
			String kokonaishintaStr = (String) map.get("kokonaishinta");
			String lisatiedot = (String) map.get("lisatiedot");
			String maksutapa = (String) map.get("maksutapa");
			String maksutilanne = (String) map.get("maksutilanne");
			String status = (String) map.get("status");
			
			String kayttajaId = (String) map.get("id");
			String kayttajaEtunimi = (String) map.get("etunimi");
			String kayttajaSukunimi = (String) map.get("sukunimi");
			String kayttajaPuhnro = (String) map.get("puhelin");
			String kayttajaTunnus = (String) map.get("tunnus");
			
			int kayttajaid;
			int tilausid;
			Timestamp tilaushetki;
			double kokonaishinta;
			boolean maksettu;

			try {
			tilausid = Integer.parseInt(idStr);
			kayttajaid = Integer.parseInt(kayttajaId);
			tilaushetki = Timestamp.valueOf(tilaushetkiStr);
			kokonaishinta = Double.parseDouble(kokonaishintaStr);
			} catch (Exception ex) {
				System.out.println("Virhe tilaushistorian tietoja parsiessa: " + ex);
				return tilaukset;
			}
			
			if (maksutilanne != null && maksutilanne.equals("E")) {
				maksettu = false;
			}
			else if (maksutilanne != null && maksutilanne.equals("K")) {
				maksettu = true;
			}
			else {
				System.out.println("Väärä arvo maksetulla = " + maksutilanne + " - asetetaan false");
				maksettu = false;
			}
			
			/*
			 * Ei sittenkään ehkä muuteta statusta täällä, vaan käsitellään JavaScriptillä
			 * 
			if (status != null && status.equals("0")) {
				status = "Vastaanotettu";
			}
			else if (status != null && status.equals("1")) {
				status = "Työn alla";
			}
			else if (status != null && status.equals("2")) {
				if (toimitustapa != null && toimitustapa.equals("Kotiinkuljetus")) {
				status = "Kuljetuksessa";
				}
				else if (toimitustapa != null && toimitustapa.equals("Nouto")) {
					status = "Odottaa noutoa";
				}
				else {
					status = "Pizza valmis";
				}
			}
			else if (status != null && status.equals("3")) {
				status = "Pizza toimitettu";
			}
			else {
				status = "Tuntematon status";
			}
			
			*/

			// Oliot
			
			Kayttaja kayttaja = new Kayttaja(kayttajaid, kayttajaTunnus, kayttajaEtunimi, kayttajaSukunimi, kayttajaPuhnro, null, null);
			
			Tilaus tilaus = new Tilaus();
			tilaus.setTilausid(tilausid);
			tilaus.setTilaushetki(tilaushetki);
			tilaus.setKokonaishinta(kokonaishinta);
			tilaus.setLisatiedot(lisatiedot);
			tilaus.setKayttaja(kayttaja);
			tilaus.setToimitustapa(toimitustapa);
			tilaus.setMaksutapa(maksutapa);
			tilaus.setMaksettu(maksettu);
			tilaus.setStatus(status);
			tilaukset.add(tilaus);
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();
		
		return tilaukset;
	}

}
