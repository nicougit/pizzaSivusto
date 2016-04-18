package daot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import bean.Juoma;
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
	
	public ArrayList<Juoma> haeKaikkiJuomat() {
		ArrayList<Juoma> juomat = new ArrayList<>();

		// Yhteyden m��ritys
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
			}
			else if (saatavillaKanta.equals("E")) {
				saatavilla = false;
			}
			else {
				System.out.println("Virheellinen 'saatavilla' arvo (" + saatavillaKanta + ") t�ytteell� ID" + idString);
			}
			
			Juoma juoma = new Juoma(idKanta, nimikanta, hintaKanta, kokoKanta, kuvausKanta, poistoKanta, saatavilla);
			juomat.add(juoma);			
		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		// Pizzojen palautus
		return juomat;

	}

}
