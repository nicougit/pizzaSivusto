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

	public ArrayList<Pizza> haeKaikkiPizzat() {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT pizza_id, p.nimi AS pizza, hinta, t.nimi AS tayte, p.poistomerkinta FROM PizzanTayte pt JOIN Pizza p USING(pizza_id) JOIN Tayte t USING(tayte_id) ORDER BY pizza_id ASC";

		kysely.suoritaKysely(sql);
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
				Pizza pizza = new Pizza(idKanta, nimikanta, hintaKanta, tayteKanta, poistoKanta);
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

	public boolean lisaaPizza(String nimi, String hinta, ArrayList<String> taytteet) {
		
		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());
		
		// Katsotaan ensin, että samannimistä pizzaa ei ole, ja että kaikki täytteet ovat tietokannassa
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
			parametrit.add(taytteet.get(0));
			if (taytteet.size() > 1) {
				for (int i = 1; i < taytteet.size(); i++) {
					sql += " OR tayte_id = ?";
					parametrit.add(taytteet.get(i));
				}
			}
			if (kysely.montaRivia(sql, parametrit) < taytteet.size()) {
				System.out.println("Virhe! Kaikkia täytteitä ei ole tietokannassa, tai on valittu kaksi samaa täytettä.");
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
		
		/* Lisätään pizzan täytteet PizzanTayte-taulukkoon
 		* Ei tiedetä vasta lisätyn pizzan pizza_id:tä, koska se määräytyy tietokannan puolella
 		* Käytetään täytteiden lisäyksessä pizzan nimeä. Oletetaan että ei duplicateja. Pitäis tehdä checki.
 		* Oletetaan myös, että tayte_id:t on valideja. */
		sql = "INSERT INTO PizzanTayte VALUES ((SELECT pizza_id FROM Pizza WHERE nimi = ?), ?)";
		parametrit.clear();
		parametrit.add(nimi);
		parametrit.add(taytteet.get(0));
		
		if (taytteet.size() > 1) {
			for (int i = 1; i < taytteet.size(); i++) {
				sql += ", ((SELECT pizza_id FROM Pizza WHERE nimi = ?), ?)";
				parametrit.add(nimi);
				parametrit.add(taytteet.get(i));
			}
		}
		
		// Palauttaa onnistuneiden määrän, pitäisi olla sama kuin täytteiden määrä
		// Pienempi arvo = error
		int taytesuccess = paivitys.suoritaSqlLauseParametreilla(sql, parametrit);
		
		System.out.println("Täytteiden määrä = " + taytteet.size() + ", lisäys kantaan palautti: " + taytesuccess);
		
		// Yhteyden sulkeminen
		yhteys.suljeYhteys();
		
		// Palautetaan true jos kaikki onnistui, false jos kaikki ei onnistunut
		if (pizzasuccess == 1 && taytesuccess == taytteet.size()) {
			return true;
		}
		else {
			return false;
		}
		
	}

}
