package daot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import bean.Juoma;
import bean.Kayttaja;
import bean.Osoite;
import bean.Pizza;
import bean.Tayte;
import bean.Tilaus;
import tietokanta.Kysely;
import tietokanta.Paivitys;
import tietokanta.Yhteys;

public class AsiakasDao {

	public ArrayList<Pizza> haeKaikkiPizzat() {
		ArrayList<Pizza> pizzat = new ArrayList<>();

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		if (yhteys.getYhteys() == null) {
			yhteys.suljeYhteys();
			return pizzat;
		}
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT pizza_id, p.nimi AS pizza, kuvaus, hinta, t.nimi AS tayte, p.poistomerkinta, tayte_id, t.saatavilla FROM PizzanTayte pt JOIN Pizza p USING(pizza_id) JOIN Tayte t USING(tayte_id) WHERE NOT EXISTS (SELECT * FROM PizzanTayte JOIN Tayte USING(tayte_id) WHERE p.pizza_id = pizza_id AND saatavilla = 'E') AND p.poistomerkinta IS NULL ORDER BY hinta ASC";

		kysely.suoritaKysely(sql);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		while (iteraattori.hasNext()) {
			HashMap pizzaMappi = (HashMap) iteraattori.next();
			String idString = (String) pizzaMappi.get("pizza_id");
			String nimikanta = (String) pizzaMappi.get("pizza");
			String hintaString = (String) pizzaMappi.get("hinta");
			String tayteKanta = (String) pizzaMappi.get("tayte");
			String tayteIdKanta = (String) pizzaMappi.get("tayte_id");
			String tayteSaatavilla = (String) pizzaMappi.get("saatavilla");
			String poistoKanta = (String) pizzaMappi.get("poistomerkinta");
			String kuvausKanta = (String) pizzaMappi.get("kuvaus");
			int idKanta = Integer.parseInt(idString);
			double hintaKanta = Double.parseDouble(hintaString);

			Tayte tayte = new Tayte();

			// Täyte-oliolle tiedot
			tayte.setId(Integer.parseInt(tayteIdKanta));
			tayte.setNimi(tayteKanta);
			if (tayteSaatavilla.equals("K")) {
				tayte.setSaatavilla(true);
			} else if (tayteSaatavilla.equals("E")) {
				tayte.setSaatavilla(false);
			} else {
				System.out.println("Tuntematon saatavilla-arvo: " + tayteSaatavilla + " - Asetetaan false.");
				tayte.setSaatavilla(false);
			}

			// Katsotaan, onko pizza jo listalla
			// Jos on, lisätään siihen täyte
			// Jos ei, luodaan uusi pizza
			boolean pizzaloyty = false;

			for (int i = 0; i < pizzat.size(); i++) {
				if (pizzat.get(i).getId() == idKanta) {
					ArrayList<Tayte> taytteet = pizzat.get(i).getTaytteet();
					taytteet.add(tayte);
					pizzat.get(i).setTaytteet(taytteet);
					pizzaloyty = true;
				}
			}

			if (pizzaloyty == false) {
				ArrayList<Tayte> taytteet = new ArrayList<>();
				taytteet.add(tayte);
				Pizza pizza = new Pizza(idKanta, nimikanta, hintaKanta, taytteet, poistoKanta, null, kuvausKanta);
				pizzat.add(pizza);
			}
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Pizzojen palautus
		return pizzat;

	}

	public Pizza haeYksiPizza(String id) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		if (yhteys.getYhteys() == null) {
			yhteys.suljeYhteys();
			return null;
		}
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT pizza_id, p.nimi AS pizza, kuvaus, hinta, t.nimi AS tayte, p.poistomerkinta, tayte_id, t.saatavilla FROM PizzanTayte pt JOIN Pizza p USING(pizza_id) JOIN Tayte t USING(tayte_id) WHERE NOT EXISTS (SELECT * FROM PizzanTayte JOIN Tayte USING(tayte_id) WHERE p.pizza_id = pizza_id AND saatavilla = 'E') AND p.poistomerkinta IS NULL AND pizza_id = ?";
		ArrayList<String> parametrit = new ArrayList<>();
		parametrit.add(id);

		Pizza pizza = new Pizza();

		if (kysely.montaRivia(sql, parametrit) < 1) {
			System.out.println("Ostoskoriin lisättävää pizzaa ei ole tietokannassa, tai se ei ole saatavilla.");
			return pizza;
		}

		kysely.suoritaYksiKyselyParam(sql, parametrit);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		int looppeja = 0;
		ArrayList<String> taytteet = new ArrayList<>();

		while (iteraattori.hasNext()) {
			HashMap pizzaMappi = (HashMap) iteraattori.next();
			String idString = (String) pizzaMappi.get("pizza_id");
			String nimikanta = (String) pizzaMappi.get("pizza");
			String hintaString = (String) pizzaMappi.get("hinta");
			String tayteKanta = (String) pizzaMappi.get("tayte");
			String tayteId = (String) pizzaMappi.get("tayte_id");
			String poistoKanta = (String) pizzaMappi.get("poistomerkinta");
			String tayteSaatavilla = (String) pizzaMappi.get("saatavilla");
			String kuvausKanta = (String) pizzaMappi.get("kuvaus");
			int idKanta = Integer.parseInt(idString);
			double hintaKanta = Double.parseDouble(hintaString);

			taytteet.add(tayteId);

			Tayte tayte = new Tayte();

			// Täyte-oliolle tiedot
			tayte.setId(Integer.parseInt(tayteId));
			tayte.setNimi(tayteKanta);
			if (tayteSaatavilla.equals("K")) {
				tayte.setSaatavilla(true);
			} else if (tayteSaatavilla.equals("E")) {
				tayte.setSaatavilla(false);
			} else {
				System.out.println("Tuntematon saatavilla-arvo: " + tayteSaatavilla + " - Asetetaan false.");
				tayte.setSaatavilla(false);
			}

			if (looppeja == 0) {
				ArrayList<Tayte> taytelista = new ArrayList<>();
				taytelista.add(tayte);
				pizza = new Pizza(idKanta, nimikanta, hintaKanta, taytelista, poistoKanta, null, kuvausKanta);
			} else {
				ArrayList<Tayte> taytelista = pizza.getTaytteet();
				taytelista.add(tayte);
				pizza.setTaytteet(taytelista);
			}

			looppeja++;

		}

		pizza.setTayteIdt(taytteet);

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Pizzojen palautus
		return pizza;

	}

	public Juoma haeYksiJuoma(String id) {
		Juoma juoma = new Juoma();

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		if (yhteys.getYhteys() == null) {
			yhteys.suljeYhteys();
			return null;
		}
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT juoma_id, nimi, hinta, koko, kuvaus, saatavilla, poistomerkinta FROM Juoma WHERE saatavilla = 'K' AND poistomerkinta IS NULL AND juoma_id = ?";
		ArrayList<String> parametrit = new ArrayList<>();
		parametrit.add(id);

		if (kysely.montaRivia(sql, parametrit) < 1) {
			System.out.println("Ostoskoriin lisättävää juomaa ei ole tietokannassa, tai se ei ole saatavilla.");
			yhteys.suljeYhteys();
			return juoma;
		}

		kysely.suoritaYksiKyselyParam(sql, parametrit);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		int looppeja = 0;
		ArrayList<String> taytteet = new ArrayList<>();

		while (iteraattori.hasNext()) {
			HashMap juomaMappi = (HashMap) iteraattori.next();
			String idString = (String) juomaMappi.get("juoma_id");
			String nimikanta = (String) juomaMappi.get("nimi");
			String hintaString = (String) juomaMappi.get("hinta");
			String kokoString = (String) juomaMappi.get("koko");
			String kuvausKanta = (String) juomaMappi.get("kuvaus");
			String saatavillaKanta = (String) juomaMappi.get("saatavilla");
			String poistoKanta = (String) juomaMappi.get("poistomerkinta");
			int idKanta = Integer.parseInt(idString);
			double hintaKanta = Double.parseDouble(hintaString);
			double kokoKanta = Double.parseDouble(kokoString);
			boolean saatavilla = false;

			if (poistoKanta != null && poistoKanta.equals("null")) {
				poistoKanta = null;
			}

			if (saatavillaKanta.equals("K")) {
				saatavilla = true;
			} else if (saatavillaKanta.equals("E")) {
				saatavilla = false;
			} else {
				System.out.println("Virheellinen 'saatavilla' arvo (" + saatavillaKanta + ") täytteellä " + idString);
			}

			juoma = new Juoma(idKanta, nimikanta, hintaKanta, kokoKanta, kuvausKanta, poistoKanta, saatavilla);

		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Pizzojen palautus
		return juoma;

	}

	public ArrayList<Juoma> haeKaikkiJuomat() {
		ArrayList<Juoma> juomat = new ArrayList<>();

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		if (yhteys.getYhteys() == null) {
			yhteys.suljeYhteys();
			return juomat;
		}
		Kysely kysely = new Kysely(yhteys.getYhteys());

		ArrayList<String> parametrit = new ArrayList<>();

		String sql = "SELECT juoma_id, nimi, hinta, koko, kuvaus, saatavilla, poistomerkinta FROM Juoma WHERE saatavilla = 'K' AND poistomerkinta IS NULL ORDER BY hinta ASC";

		kysely.suoritaKysely(sql);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		while (iteraattori.hasNext()) {
			HashMap juomaMappi = (HashMap) iteraattori.next();
			String idString = (String) juomaMappi.get("juoma_id");
			String nimikanta = (String) juomaMappi.get("nimi");
			String hintaString = (String) juomaMappi.get("hinta");
			String kokoString = (String) juomaMappi.get("koko");
			String kuvausKanta = (String) juomaMappi.get("kuvaus");
			String saatavillaKanta = (String) juomaMappi.get("saatavilla");
			String poistoKanta = (String) juomaMappi.get("poistomerkinta");
			int idKanta = Integer.parseInt(idString);
			double hintaKanta = Double.parseDouble(hintaString);
			double kokoKanta = Double.parseDouble(kokoString);
			boolean saatavilla = false;

			if (poistoKanta != null && poistoKanta.equals("null")) {
				poistoKanta = null;
			}

			if (saatavillaKanta.equals("K")) {
				saatavilla = true;
			} else if (saatavillaKanta.equals("E")) {
				saatavilla = false;
			} else {
				System.out.println("Virheellinen 'saatavilla' arvo (" + saatavillaKanta + ") täytteellä " + idString);
			}

			Juoma juoma = new Juoma(idKanta, nimikanta, hintaKanta, kokoKanta, kuvausKanta, poistoKanta, saatavilla);
			juomat.add(juoma);
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Pizzojen palautus
		return juomat;

	}

	public HashMap<String, String> lisaaTilaus(Tilaus tilaus) {
		HashMap<String, String> vastaus = new HashMap<>();

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		if (yhteys.getYhteys() == null) {
			String virhe = "Tietokantayhteyttä ei saatu avattua";
			vastaus.put("virhe", virhe);
			return vastaus;
		}

		// Tilauksen lisääminen
		String sql = "INSERT INTO Tilaus (kayttaja_id, osoite_id, tilaushetki, toimitustapa, toimitusaika, lisatiedot, kokonaishinta, maksutapa, maksutilanne) VALUES (?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
		ArrayList<String> parametrit = new ArrayList<String>();
		Paivitys paivitys = new Paivitys(yhteys.getYhteys());

		parametrit.add(String.valueOf(tilaus.getKayttaja().getId()));
		if (tilaus.getOsoite() != null) {
		parametrit.add(String.valueOf(tilaus.getOsoite().getOsoiteid()));
		}
		else {
			parametrit.add(null);
		}
		parametrit.add(tilaus.getToimitustapa());
		parametrit.add(tilaus.getLisatiedot());
		parametrit.add(String.valueOf(tilaus.getKokonaishinta()));
		parametrit.add(tilaus.getMaksutapa());
		parametrit.add("E");

		// Palauttaa lisätyn tilauksen tilaus_id:n
		int tilausid = paivitys.suoritaSqlParamPalautaAvaimet(sql, parametrit);

		System.out.println("Lisätyn tilauksen tilaus_id: " + tilausid);

		if (tilausid < 1) {
			String virhe = "Tilausta lisätessä generated key palautti < 1";
			vastaus.put("virhe", virhe);
			return vastaus;
		}

		ArrayList<Pizza> pizzat = tilaus.getPizzat();
		ArrayList<Juoma> juomat = tilaus.getJuomat();
		parametrit.clear();

		if (pizzat.size() > 0) {
			// tilaus_id, pizza_id, laktoositon; gluteeniton, oregano,
			// valkosipuli
			sql = "INSERT INTO Tilausrivi VALUES (null, ?, ?, null, ?, ?, ?, ?, 1)";

			for (int i = 0; i < pizzat.size(); i++) {
				if (i > 0) {
					sql += ", (null, ?, ?, null, ?, ?, ?, ?, 1)";
				}
				parametrit.add(String.valueOf(tilausid));
				parametrit.add(String.valueOf(pizzat.get(i).getId()));
				if (pizzat.get(i).getLisatiedot() != null && pizzat.get(i).getLisatiedot().contains("Laktoositon")) {
					parametrit.add("K");
				} else {
					parametrit.add("E");
				}

				if (pizzat.get(i).getLisatiedot() != null &&pizzat.get(i).getLisatiedot().contains("Gluteeniton")) {
					parametrit.add("K");
				} else {
					parametrit.add("E");
				}

				if (pizzat.get(i).getLisatiedot() != null &&pizzat.get(i).getLisatiedot().contains("Oregano")) {
					parametrit.add("K");
				} else {
					parametrit.add("E");
				}

				if (pizzat.get(i).getLisatiedot() != null &&pizzat.get(i).getLisatiedot().contains("Valkosipuli")) {
					parametrit.add("K");
				} else {
					parametrit.add("E");
				}
			}

		}
		
		if (juomat.size() > 0) {
			for (int i = 0; i < juomat.size(); i++) {
				if (pizzat.size() == 0 && i == 0) {
					sql = "INSERT INTO Tilausrivi VALUES (null, ?, null, ?, 'E', 'E', 'E', 'E', 1);";
				} else {
					sql += ", (null, ?, null, ?, 'E', 'E', 'E', 'E', 1)";
				}
				
				parametrit.add(String.valueOf(tilausid));
				parametrit.add(String.valueOf(juomat.get(i).getId()));
			}

		}

		String parametritstr = "";
		for (int i = 0; i < parametrit.size(); i++) {
			parametritstr += (i + 1) + ": " + parametrit.get(i);
			if (i + 1 != parametrit.size()) {
				parametritstr += ", ";
			}
		}

		System.out.println("SQL: " + sql);
		System.out.println("Parametrit: " + parametritstr);

		// Palauttaa lisättyjen tilausrivien määrän
		int tilausrivitok = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		System.out.println("Tilausrivien lisäys palautti " + tilausrivitok);

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Palautetaan true jos kaikki onnistui, false jos kaikki ei onnistunut
		// Poikkeuksellisesti lähetetään tässä hashmapissa success-viestissä tilauksen id
		if (tilausrivitok == (pizzat.size() + juomat.size())) {
			String success = String.valueOf(tilausid);
			vastaus.put("success", success);
			return vastaus;
		} else {
			String virhe = "Tilausta lähettäessä tapahtui virhe.";
			vastaus.put("virhe", virhe);
			return vastaus;
		}
	}
	
	public Tilaus haeYksiTilaus(String tilausid, String kayttajaid) {
		Tilaus tilaus = new Tilaus();
		ArrayList<Pizza> pizzat = new ArrayList<>();
		ArrayList<Juoma> juomat = new ArrayList<>();
		

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		
		if (yhteys.getYhteys() == null) {
			return tilaus;
		}
		
		Kysely kysely = new Kysely(yhteys.getYhteys());
		
		ArrayList<String> parametrit = new ArrayList<>();
		
		// Haetaan käyttäjän osoitteet
		String sqlkysely = "SELECT tr.tilaus_id, k.etunimi, k.sukunimi, k.puhelin, k.tunnus, os.toimitusosoite, os.postinro, os.postitmp, t.tilaushetki, t.toimitusaika, t.toimitustapa, t.lisatiedot, t.kokonaishinta, t.maksutapa, t.maksutilanne, j.nimi AS juoma, p.nimi AS pizza, tr.laktoositon, tr.gluteeniton, tr.oregano, tr.valkosipuli, p.hinta AS pizzahinta, j.hinta AS juomahinta FROM Tilausrivi tr JOIN Tilaus t USING (tilaus_id) JOIN Kayttaja k ON t.kayttaja_id = k.id LEFT OUTER JOIN Pizza p ON tr.tuote_pizza = p.pizza_id LEFT OUTER JOIN Juoma j ON tr.tuote_juoma = j.juoma_id LEFT OUTER JOIN Toimitusosoite os ON t.osoite_id = os.osoite_id WHERE tr.tilaus_id = ?";
		parametrit.add(tilausid);
		if (!kayttajaid.equals("admin")) {
			sqlkysely += " AND k.id = ?";
		parametrit.add(kayttajaid);
		}

		kysely.suoritaYksiKyselyParam(sqlkysely, parametrit);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		Iterator iteraattori = kysely.getTulokset().iterator();

		int looppi = 0;
		while (iteraattori.hasNext()) {
			HashMap map = (HashMap) iteraattori.next();
			String idStr;
			if (looppi < 1) {
			idStr = (String) map.get("tilaus_id");
			String etunimi = (String) map.get("etunimi");
			String sukunimi = (String) map.get("sukunimi");
			String puhelin = (String) map.get("puhelin");
			String tunnus = (String) map.get("tunnus");
			String toimitusosoite = (String) map.get("toimitusosoite");
			String postinro = (String) map.get("postinro");
			String postitmp = (String) map.get("postitmp");
			String tilaushetkistr = (String) map.get("tilaushetki");
			String toimitusaikastr = (String) map.get("toimitusaika");
			String toimitustapa = (String) map.get("toimitustapa");
			String lisatiedot = (String) map.get("lisatiedot");
			String kokonaishintastr = (String) map.get("kokonaishinta");
			String maksutapa = (String) map.get("maksutapa");
			String maksutilanne = (String) map.get("maksutilanne");
			
			// Käyttäjän setit
			Kayttaja kayttaja = new Kayttaja();
			kayttaja.setEtunimi(etunimi);
			kayttaja.setSukunimi(sukunimi);
			kayttaja.setTunnus(tunnus);
			if (puhelin != null) {
				kayttaja.setPuhelin(puhelin);
			}
			tilaus.setKayttaja(kayttaja);
			
			// Toimitusosoite
			if (toimitustapa.equals("Kotiinkuljetus")) {
				Osoite osoite = new Osoite();
				osoite.setOsoite(toimitusosoite);
				osoite.setPostinro(postinro);
				osoite.setPostitmp(postitmp);
				tilaus.setOsoite(osoite);
			}
			
			// Loput tilauskohtaiset setit
			int tilausidint;
			Timestamp tilaushetki;
			Timestamp toimitusaika;
			double kokonaishinta;
			
			try {
				tilausidint = Integer.parseInt(idStr);
				tilaushetki = Timestamp.valueOf(tilaushetkistr);
				toimitusaika = Timestamp.valueOf(toimitusaikastr);
				kokonaishinta = Double.valueOf(kokonaishintastr);
				
				tilaus.setTilausid(tilausidint);
				tilaus.setTilaushetki(tilaushetki);
				tilaus.setToimitusaika(toimitusaika);
				tilaus.setKokonaishinta(kokonaishinta);
				tilaus.setMaksutapa(maksutapa);
				tilaus.setToimitustapa(toimitustapa);
				
			} catch (Exception ex) {
				System.out.println("KAUHEE VIRHE TILAUSTA PARSIESSA");
				return new Tilaus();
			}
			
			if (lisatiedot != null) {
				tilaus.setLisatiedot(lisatiedot);
			}
			
			if (maksutilanne.equals("K")) {
				tilaus.setMaksettu(true);
			}
			else {
				tilaus.setMaksettu(false);
			}
			
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
				
				pizzat.add(pizza);
				
			}
			else if (juomanimi != null) {
				Juoma juoma = new Juoma();
				juoma.setNimi(juomanimi);
				String hintaStr = (String) map.get("juomahinta");
				double hinta = 0;
				
				try {
					if (hintaStr != null) {
						hinta = Double.parseDouble(hintaStr);
					}
				} catch (Exception ex) {
					System.out.println("Virhe doublee parsiessa");
				}
				
				juoma.setHinta(hinta);
				juomat.add(juoma);
			}
			
			looppi++;
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();
		
		tilaus.setPizzat(pizzat);
		tilaus.setJuomat(juomat);
		
		return tilaus;
	}

}
