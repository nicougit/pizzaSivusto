package daot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import bean.Pizza;
import bean.Tayte;
import tietokanta.Kysely;
import tietokanta.Yhteys;

public class HallintaDao {

	public ArrayList<Pizza> haeKaikkiPizzat() {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT pizza_id, p.nimi AS pizza, hinta, t.nimi AS tayte FROM PizzanTayte pt JOIN Pizza p USING(pizza_id) JOIN Tayte t USING(tayte_id) ORDER BY pizza_id ASC";

		kysely.suoritaKysely(sql);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		// Hashmap jossa pizzan id ja pizzaolio
		// Pointtina tässä on yhdistää yhteen pizzaolioon jokainen rivi jossa sama pizza_id
		// Kaikki täytteet menevät 'taytteet' Stringiin pilkuilla eroteltuina
		HashMap<Integer, Pizza> pizzaVarasto = new HashMap<>();

		while (iteraattori.hasNext()) {
			HashMap pizzaMappi = (HashMap) iteraattori.next();
			String idString = (String) pizzaMappi.get("pizza_id");
			String nimikanta = (String) pizzaMappi.get("pizza");
			String hintaString = (String) pizzaMappi.get("hinta");
			String tayteKanta = (String) pizzaMappi.get("tayte");
			int idKanta = Integer.parseInt(idString);
			double hintaKanta = Double.parseDouble(hintaString);
			
			// Jos HashMapissa on jo pizza tällä ID:llä, lisätään siihen uusi täyte
			// Muuten lisätään HashMappiin uusi pizza
			if (pizzaVarasto.containsKey(idKanta)) {
				Pizza pizza = pizzaVarasto.get(idKanta);
				pizza.setTaytteet(pizza.getTaytteet() + ", " + tayteKanta);
				pizzaVarasto.put(idKanta, pizza);
				
				System.out.println("Pizzaan " + idKanta + " lisätty täyte: " + tayteKanta);
				
			} else {
				// Olion luonti ja vienti HashMappiin
				Pizza pizza = new Pizza(idKanta, nimikanta, hintaKanta, tayteKanta);
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

}
