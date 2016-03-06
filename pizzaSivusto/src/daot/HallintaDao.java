package daot;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import bean.Pizza;
import bean.Tayte;
import tietokanta.Kysely;
import tietokanta.Paivitys;
import tietokanta.Yhteys;

public class HallintaDao {

	// Tyyppi = 1 ja id pitää päärittää vain, jos haluaa noutaa pizzat joissa on
	// tietty täyte
	// Muussa tapauksessa kelpaa mitkä tahansa arvot
	public ArrayList<Pizza> haeKaikkiPizzat(int tyyppi, String tayteId) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		ArrayList<String> parametrit = new ArrayList<>();

		String sql = "SELECT pizza_id, p.nimi AS pizza, hinta, t.nimi AS tayte, p.poistomerkinta FROM PizzanTayte pt JOIN Pizza p USING(pizza_id) JOIN Tayte t USING(tayte_id)";

		if (tyyppi == 1) {
			sql += " WHERE tayte_id = ?";
			parametrit.add(tayteId);
		}

		sql += " ORDER BY pizza_id ASC";

		kysely.suoritaYksiKyselyParam(sql, parametrit);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		// Hashmap jossa pizzan id ja pizzaolio
		// Pointtina tässä on yhdistää yhteen pizzaolioon jokainen rivi jossa
		// sama pizza_id
		// Kaikki täytteet menevät 'taytteet' Stringiin pilkuilla eroteltuina
		HashMap<Integer, Pizza> pizzaVarasto = new HashMap<>();

		while (iteraattori.hasNext()) {
			HashMap pizzaMappi = (HashMap) iteraattori.next();
			String idString = (String) pizzaMappi.get("pizza_id");
			String nimikanta = (String) pizzaMappi.get("pizza");
			String hintaString = (String) pizzaMappi.get("hinta");
			String tayteKanta = (String) pizzaMappi.get("tayte");
			String poistoKanta = (String) pizzaMappi.get("poistomerkinta");
			int idKanta = Integer.parseInt(idString);
			double hintaKanta = Double.parseDouble(hintaString);

			// Jos HashMapissa on jo pizza tällä ID:llä, lisätään siihen uusi
			// täyte
			// Muuten lisätään HashMappiin uusi pizza
			if (pizzaVarasto.containsKey(idKanta)) {
				Pizza pizza = pizzaVarasto.get(idKanta);
				pizza.setTaytteet(pizza.getTaytteet() + ", " + tayteKanta);
				pizzaVarasto.put(idKanta, pizza);

			} else {
				// Olion luonti ja vienti HashMappiin
				Pizza pizza = new Pizza(idKanta, nimikanta, hintaKanta, tayteKanta, poistoKanta, null);
				pizzaVarasto.put(idKanta, pizza);
			}

		}

		// Luodaan ArrayList pizzoille ja siirretään HashMapin pizzat sinne
		ArrayList<Pizza> pizzat = new ArrayList<>();

		for (Map.Entry<Integer, Pizza> entry : pizzaVarasto.entrySet()) {
			pizzat.add(entry.getValue());
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Pizzojen palautus
		return pizzat;

	}

	public Tayte haeTayte(String id) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT * FROM Tayte WHERE tayte_id = ?";
		ArrayList<String> parametrit = new ArrayList<>();
		parametrit.add(id);

		Tayte tayte = new Tayte();

		// Katsotaan, onko täytettä olemassa
		if (kysely.montaRivia(sql, parametrit) != 1) {
			System.out.println("Virhe! Täytettä ei ole tietokannassa, tai duplicate id");
			return tayte;
		}

		// Suoritetaan itse kysely
		kysely.suoritaYksiKyselyParam(sql, parametrit);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		// Tulosten läpi käynti
		while (iteraattori.hasNext()) {
			HashMap tayteMappi = (HashMap) iteraattori.next();
			String tayteid = (String) tayteMappi.get("tayte_id");
			String nimikanta = (String) tayteMappi.get("nimi");
			String saatavilla = (String) tayteMappi.get("saatavilla");

			// Oliolle tiedot
			tayte.setId(Integer.parseInt(tayteid));
			tayte.setNimi(nimikanta);
			if (saatavilla.equals("K")) {
				tayte.setSaatavilla(true);
			} else if (saatavilla.equals("E")) {
				tayte.setSaatavilla(false);
			} else {
				System.out.println("Tuntematon saatavilla-arvo: " + saatavilla + " - Asetetaan false.");
				tayte.setSaatavilla(false);
			}

			System.out.println(
					"Olion tiedot: " + tayte.getId() + " - " + tayte.getNimi() + " - " + tayte.getSaatavilla());

		}

		return tayte;
	}

	public Pizza haePizza(String id) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT pizza_id, p.nimi AS pizza, hinta, tayte_id, t.nimi AS tayte, p.poistomerkinta FROM PizzanTayte pt JOIN Pizza p USING(pizza_id) JOIN Tayte t USING(tayte_id) WHERE pizza_id = ?";
		ArrayList<String> parametrit = new ArrayList<>();
		parametrit.add(id);

		Pizza pizza = new Pizza();

		if (kysely.montaRivia(sql, parametrit) < 1) {
			System.out.println("Virhe! Muokattavaksi haluttua pizzaa ei ole tietokannassa.");
			return pizza;
		}

		kysely.suoritaYksiKyselyParam(sql, parametrit);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		// Hashmap jossa pizzan id ja pizzaolio
		// Pointtina tässä on yhdistää yhteen pizzaolioon jokainen rivi jossa
		// sama pizza_id
		// Kaikki täytteet menevät 'taytteet' Stringiin pilkuilla eroteltuina

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
			int idKanta = Integer.parseInt(idString);
			double hintaKanta = Double.parseDouble(hintaString);

			taytteet.add(tayteId);

			if (looppeja == 0) {
				pizza = new Pizza(idKanta, nimikanta, hintaKanta, tayteKanta, poistoKanta, null);
			} else {
				pizza.setTaytteet(pizza.getTaytteet() + ", " + tayteKanta);
			}

			looppeja++;

		}

		pizza.setTayteIdt(taytteet);

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Pizzojen palautus
		return pizza;

	}

	public ArrayList<Tayte> haeKaikkiTaytteet() {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		ArrayList<Tayte> taytteet = new ArrayList<>();

		String sql = "SELECT tayte_id, nimi, saatavilla FROM Tayte";

		kysely.suoritaKysely(sql);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		while (iteraattori.hasNext()) {
			HashMap kayttajaMappi = (HashMap) iteraattori.next();
			String idString = (String) kayttajaMappi.get("tayte_id");
			String nimikanta = (String) kayttajaMappi.get("nimi");
			String saatavillaKanta = (String) kayttajaMappi.get("saatavilla");
			int idKanta = Integer.parseInt(idString);

			// Oliolle attribuutit
			Tayte tayte = new Tayte();

			tayte.setId(idKanta);
			tayte.setNimi(nimikanta);
			if (saatavillaKanta.equals("K")) {
				tayte.setSaatavilla(true);
			} else if (saatavillaKanta.equals("E")) {
				tayte.setSaatavilla(false);
			} else {
				System.out.println("Täytteellä ID" + idKanta + " (" + nimikanta + ") virheellinen saatavuus: '"
						+ saatavillaKanta + "', asetetaan false");
				tayte.setSaatavilla(false);
			}

			taytteet.add(tayte);

		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		return taytteet;

	}

	public boolean lisaaPizza(String nimi, String hinta, String[] taytteet) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		// Katsotaan ensin, että samannimistä pizzaa ei ole, ja että kaikki
		// täytteet ovat tietokannassa
		String sql = "SELECT nimi FROM Pizza WHERE nimi = ?";
		ArrayList<String> parametrit = new ArrayList<String>();
		parametrit.add(nimi);

		if (kysely.montaRivia(sql, parametrit) > 0) {
			System.out.println("Virhe! Saman niminen pizza on jo tietokannassa.");
			return false;
		} else {
			// Katsotaan, että kaikki täytteet ovat tietokannassa
			sql = "SELECT tayte_id FROM Tayte WHERE tayte_id = ?";
			parametrit.clear();
			parametrit.add(taytteet[0]);
			if (taytteet.length > 1) {
				for (int i = 1; i < taytteet.length; i++) {
					sql += " OR tayte_id = ?";
					parametrit.add(taytteet[i]);
				}
			}
			if (kysely.montaRivia(sql, parametrit) < taytteet.length) {
				System.out
						.println("Virhe! Kaikkia täytteitä ei ole tietokannassa, tai on valittu kaksi samaa täytettä.");
				return false;
			}
		}

		// Itse pizzan lisäys Pizza-taulukkoon
		sql = "INSERT INTO Pizza VALUES (null, ?, ?, null)";
		Paivitys paivitys = new Paivitys(yhteys.getYhteys());

		parametrit.clear();
		parametrit.add(nimi);
		parametrit.add(hinta);

		// Palauttaa onnistuneiden rivien määrän, 1 = ok, 0 = error
		int pizzasuccess = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		System.out.println("Pizzan lisäys kantaan palautti: " + pizzasuccess);

		/*
		 * Lisätään pizzan täytteet PizzanTayte-taulukkoon Ei tiedetä vasta
		 * lisätyn pizzan pizza_id:tä, koska se määräytyy tietokannan puolella
		 * Käytetään täytteiden lisäyksessä pizzan nimeä. Oletetaan että ei
		 * duplicateja. Pitäis tehdä checki. Oletetaan myös, että tayte_id:t on
		 * valideja.
		 */
		sql = "INSERT INTO PizzanTayte VALUES ((SELECT pizza_id FROM Pizza WHERE nimi = ?), ?)";
		parametrit.clear();
		parametrit.add(nimi);
		parametrit.add(taytteet[0]);

		if (taytteet.length > 1) {
			for (int i = 1; i < taytteet.length; i++) {
				sql += ", ((SELECT pizza_id FROM Pizza WHERE nimi = ?), ?)";
				parametrit.add(nimi);
				parametrit.add(taytteet[i]);
			}
		}

		// Palauttaa onnistuneiden määrän, pitäisi olla sama kuin täytteiden
		// määrä
		// Pienempi arvo = error
		int taytesuccess = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		System.out.println("Täytteiden määrä = " + taytteet.length + ", lisäys kantaan palautti: " + taytesuccess);

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Palautetaan true jos kaikki onnistui, false jos kaikki ei onnistunut
		if (pizzasuccess == 1 && taytesuccess == taytteet.length) {
			return true;
		} else {
			return false;
		}

	}

	public boolean paivitaTayte(String id, String nimi, String saatavilla) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		// Katsotaan, että kyseinen tayte_id on tietokannassa
		String sql = "SELECT tayte_id FROM Tayte WHERE tayte_id = ?";
		ArrayList<String> parametrit = new ArrayList<String>();
		parametrit.add(id);

		if (kysely.montaRivia(sql, parametrit) == 0) {
			System.out.println("Virhe! Täytettä ei löydy tietokannasta");
			return false;
		}

		// Katsotaan, että muilla täytteillä ei ole samaa nimeä
		sql = "SELECT nimi FROM Tayte WHERE nimi = ? AND tayte_id != ?";
		parametrit.clear();
		parametrit.add(nimi);
		parametrit.add(id);

		if (kysely.montaRivia(sql, parametrit) > 0) {
			System.out.println("Virhe! Saman niminen täyte on jo tietokannassa.");
			return false;
		}

		sql = "UPDATE Tayte SET nimi = ?, saatavilla = ? WHERE tayte_id = ?";
		Paivitys paivitys = new Paivitys(yhteys.getYhteys());

		parametrit.clear();
		parametrit.add(nimi);
		parametrit.add(saatavilla);
		parametrit.add(id);

		// Palauttaa onnistuneiden rivien määrän, 1 = ok, 0 = error
		int success = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);
		
		System.out.println("Täytteen päivitys palautti " + paivitys);

		if (success == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean paivitaPizza(String id, String nimi, String hinta, String[] taytteet) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		// Katsotaan, että kyseinen pizza_id on tietokannassa
		String sql = "SELECT pizza_id FROM Pizza WHERE pizza_id = ?";
		ArrayList<String> parametrit = new ArrayList<String>();
		parametrit.add(id);

		if (kysely.montaRivia(sql, parametrit) == 0) {
			System.out.println("Virhe! Pizzaa ei löydy tietokannasta");
			return false;
		}

		// Katsotaan, että muilla pizzoilla ei ole samaa nimeä
		sql = "SELECT nimi FROM Pizza WHERE nimi = ? AND pizza_id != ?";
		parametrit.clear();
		parametrit.add(nimi);
		parametrit.add(id);

		if (kysely.montaRivia(sql, parametrit) > 0) {
			System.out.println("Virhe! Saman niminen pizza on jo tietokannassa.");
			return false;
		} else {
			// Katsotaan, että kaikki täytteet ovat tietokannassa
			sql = "SELECT tayte_id FROM Tayte WHERE tayte_id = ?";
			parametrit.clear();
			parametrit.add(taytteet[0]);
			if (taytteet.length > 1) {
				for (int i = 1; i < taytteet.length; i++) {
					sql += " OR tayte_id = ?";
					parametrit.add(taytteet[i]);
				}
			}
			if (kysely.montaRivia(sql, parametrit) < taytteet.length) {
				System.out
						.println("Virhe! Kaikkia täytteitä ei ole tietokannassa, tai on valittu kaksi samaa täytettä.");
				return false;
			}
		}

		// Itse pizzan tietojen päivitys
		sql = "UPDATE Pizza SET nimi = ?, hinta = ? WHERE pizza_id = ?";
		Paivitys paivitys = new Paivitys(yhteys.getYhteys());

		parametrit.clear();
		parametrit.add(nimi);
		parametrit.add(hinta);
		parametrit.add(id);

		// Palauttaa onnistuneiden rivien määrän, 1 = ok, 0 = error
		int pizzasuccess = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		System.out.println("Pizzan päivitys kantaan palautti: " + pizzasuccess);

		// Poistetaan kokonaan vanhat täytteet kokonaan
		sql = "DELETE FROM PizzanTayte WHERE pizza_id = ?";
		parametrit.clear();
		parametrit.add(id);
		int deletesuccess = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		System.out.println("Vanhojen täytteiden poisto palautti " + deletesuccess);

		// Lisätään uudet täytteet
		sql = "INSERT INTO PizzanTayte VALUES ((SELECT pizza_id FROM Pizza WHERE nimi = ?), ?)";
		parametrit.clear();
		parametrit.add(nimi);
		parametrit.add(taytteet[0]);

		if (taytteet.length > 1) {
			for (int i = 1; i < taytteet.length; i++) {
				sql += ", ((SELECT pizza_id FROM Pizza WHERE nimi = ?), ?)";
				parametrit.add(nimi);
				parametrit.add(taytteet[i]);
			}
		}

		// Palauttaa onnistuneiden määrän, pitäisi olla sama kuin täytteiden
		// määrä
		// Pienempi arvo = error
		int taytesuccess = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		System.out.println("Täytteiden määrä = " + taytteet.length + ", lisäys kantaan palautti: " + taytesuccess);

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Palautetaan true jos kaikki onnistui, false jos kaikki ei onnistunut
		if (pizzasuccess == 1 && taytesuccess == taytteet.length) {
			return true;
		} else {
			return false;
		}

	}

	public boolean poistaPizza(String id) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		// Määritellään lause ja parametrit
		String sql = "SELECT pizza_id FROM Pizza WHERE pizza_id = ?";
		ArrayList<String> parametrit = new ArrayList<String>();
		parametrit.add(id);

		if (kysely.montaRivia(sql, parametrit) < 1) {
			System.out.println("Virhe! Poistettavaa pizzaa ei ole olemassa, tai tapahtui muu tietokantavirhe.");
			return false;
		}

		// Määritellään lause ja poistetaan pizza
		sql = "UPDATE Pizza SET poistomerkinta = NOW() WHERE pizza_id = ?";
		Paivitys paivitys = new Paivitys(yhteys.getYhteys());
		int success = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		if (success != 1) {
			System.out.println("Virhe! Pizzan poistomerkintää tehdessä tapahtui virhe.");
			return false;
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		return true;
	}

	public boolean palautaPizza(String id) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		// Määritellään lause ja parametrit
		String sql = "SELECT pizza_id FROM Pizza WHERE pizza_id = ? AND poistomerkinta IS NOT NULL";
		ArrayList<String> parametrit = new ArrayList<String>();
		parametrit.add(id);

		if (kysely.montaRivia(sql, parametrit) < 1) {
			System.out.println("Virhe! Pizzaa ei löydy, tai sillä ei ole poistomerkintää.");
			return false;
		}

		// Määritellään lause ja palautetaan pizza
		sql = "UPDATE Pizza SET poistomerkinta = null WHERE pizza_id = ?";
		Paivitys paivitys = new Paivitys(yhteys.getYhteys());
		int success = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		if (success != 1) {
			System.out.println("Virhe! Pizzaa palauttaessa tapahtui virhe.");
			return false;
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		return true;
	}

	public boolean lisaaTayte(String nimi, String saatavilla) {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());
		Paivitys paivitys = new Paivitys(yhteys.getYhteys());

		// Katsotaan ensin, että saman nimistä täytettä ei ole
		String sql = "SELECT nimi FROM Tayte WHERE nimi = ?";
		ArrayList<String> parametrit = new ArrayList<String>();
		parametrit.add(nimi);

		if (kysely.montaRivia(sql, parametrit) > 0) {
			System.out.println("Virhe! Saman niminen täyte on jo tietokannassa.");
			return false;
		}

		// Lisätään täyte
		sql = "INSERT INTO Tayte VALUES (null, ?, ?)";
		parametrit.add(saatavilla);

		// Palauttaa onnistuneiden rivien määrän, 1 = ok, 0 = error
		int success = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);

		if (success == 1) {
			return true;
		} else {
			return false;
		}

	}

}
