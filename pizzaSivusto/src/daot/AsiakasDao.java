package daot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import bean.Pizza;
import bean.Tayte;
import tietokanta.Kysely;
import tietokanta.Yhteys;

public class AsiakasDao {

	public ArrayList<Pizza> haeKaikkiPizzat() {
		ArrayList<Pizza> pizzat = new ArrayList<>();

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		if (yhteys.getYhteys() == null) {
			return pizzat;
		}
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT pizza_id, p.nimi AS pizza, hinta, t.nimi AS tayte, p.poistomerkinta, tayte_id, t.saatavilla"
				+ " FROM PizzanTayte pt JOIN Pizza p USING(pizza_id) JOIN Tayte t USING(tayte_id) WHERE NOT EXISTS "
				+ "(SELECT * FROM PizzanTayte JOIN Tayte USING(tayte_id)"
				+ " WHERE p.pizza_id = pizza_id AND saatavilla = 'E')"
				+ "	AND p.poistomerkinta IS NULL "		    	
					+"ORDER BY p.nimi ASC";
				

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
				Pizza pizza = new Pizza(idKanta, nimikanta, hintaKanta, taytteet, poistoKanta, null);
				pizzat.add(pizza);
			}
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Pizzojen palautus
		return pizzat;

	}

	public ArrayList<Tayte> haeKaikkiTaytteet() {
		ArrayList<Tayte> taytteet = new ArrayList<>();

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		if (yhteys.getYhteys() == null) {
			return taytteet;
		}
		Kysely kysely = new Kysely(yhteys.getYhteys());

		String sql = "SELECT tayte_id, nimi, saatavilla FROM Tayte ORDER BY nimi ASC";

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
