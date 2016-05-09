package fi.softala.pizzeria.daot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fi.softala.pizzeria.bean.Juoma;
import fi.softala.pizzeria.bean.Kayttaja;
import fi.softala.pizzeria.bean.Pizza;
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
		String sql = "SELECT tilaus_id, tilaushetki, kokonaishinta, lisatiedot, maksutapa, toimitustapa, maksutilanne, status, k.id, k.etunimi, k.sukunimi, k.puhelin, k.tunnus, j.nimi AS juoma, p.nimi AS pizza, tr.laktoositon, tr.gluteeniton, tr.oregano, tr.valkosipuli, p.hinta AS pizzahinta, j.hinta AS juomahinta, j.koko AS juomakoko FROM Tilausrivi tr JOIN Tilaus t USING (tilaus_id) JOIN Kayttaja k ON t.kayttaja_id = k.id LEFT OUTER JOIN Pizza p ON tr.tuote_pizza = p.pizza_id LEFT OUTER JOIN Juoma j ON tr.tuote_juoma = j.juoma_id ORDER BY tilaus_id DESC;";

		kysely.suoritaKysely(sql);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		Iterator iteraattori = kysely.getTulokset().iterator();

		while (iteraattori.hasNext()) {
			HashMap map = (HashMap) iteraattori.next();
			String idStr = (String) map.get("tilaus_id");
			int tilausid;
			try {
			tilausid = Integer.parseInt(idStr);
			} catch (Exception ex) {
				System.out.println("Virhe tilauksen id parsiessa: " + ex);
				return tilaukset;
			}
			
			boolean loytyy = false;
			
			for (int i = 0; i < tilaukset.size(); i++) {
				if (tilaukset.get(i).getTilausid() == tilausid) {
					loytyy = true;
					i = tilaukset.size();
				}
			}
			
			if (loytyy == false) {
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
			tilaus.setPizzat(new ArrayList<Pizza>());
			tilaus.setJuomat(new ArrayList<Juoma>());
			tilaukset.add(tilaus);
			}

			String pizzanimi = (String) map.get("pizza");
			String juomanimi = (String) map.get("juoma");
			
			if (pizzanimi != null) {
				String valkosipuli = (String) map.get("valkosipuli");
				String gluteeniton = (String) map.get("gluteeniton");
				String oregano = (String) map.get("oregano");
				String vl = (String) map.get("laktoositon");
				String hintaStr = (String) map.get("pizzahinta");
				double hinta = 0;
				
				try {
					if (hintaStr != null) {
						hinta = Double.parseDouble(hintaStr);
					}
				} catch (Exception ex) {
					System.out.println("Virhe doublee parsiessa");
				}
				
				Pizza pizza = new Pizza();
				ArrayList<String> pizzatiedot = new ArrayList<>();
				pizza.setNimi(pizzanimi);
				if (valkosipuli != null && valkosipuli.equals("K")) {
					pizzatiedot.add("Valkosipuli");
				}
				if (gluteeniton != null && gluteeniton.equals("K")) {
					pizzatiedot.add("Gluteeniton");
				}
				if (oregano != null && oregano.equals("K")) {
					pizzatiedot.add("Oregano");
				}
				if (vl != null && vl.equals("K")) {
					pizzatiedot.add("Laktoositon");
				}
				
				if (pizzatiedot.size() > 0) {
					pizza.setLisatiedot(pizzatiedot);
				}
				
				pizza.setHinta(hinta);
				
				for (int i = 0; i < tilaukset.size(); i++) {
					if (tilaukset.get(i).getTilausid() == tilausid) {
						tilaukset.get(i).getPizzat().add(pizza);
					}
				}
				
			}
			else if (juomanimi != null) {
				Juoma juoma = new Juoma();
				juoma.setNimi(juomanimi);
				String hintaStr = (String) map.get("juomahinta");
				String kokoStr = (String) map.get("juomakoko");
				double hinta = 0;
				double koko = 0;
				
				try {
					if (hintaStr != null) {
						hinta = Double.parseDouble(hintaStr);
					}
					if (kokoStr != null) {
						koko = Double.parseDouble(kokoStr);
					}
				} catch (Exception ex) {
					System.out.println("Virhe doublee parsiessa");
				}
				
				juoma.setHinta(hinta);
				juoma.setKoko(koko);

				for (int i = 0; i < tilaukset.size(); i++) {
					if (tilaukset.get(i).getTilausid() == tilausid) {
						tilaukset.get(i).getJuomat().add(juoma);
					}
				}
			}
			
		}
		

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();
		
		return tilaukset;
	}

}
