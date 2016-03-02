package daot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import bean.Pizza;
import bean.Tayte;
import tietokanta.Kysely;
import tietokanta.Yhteys;

public class HallintaDao {

	public ArrayList<Pizza> haeKaikkiPizzat() {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		ArrayList<Pizza> pizzat = new ArrayList<>();

		String sql = "SELECT id, nimi, hinta, (SELECT nimi FROM Taytteet WHERE id = p.tayte1) AS tayte1, (SELECT nimi FROM Taytteet WHERE id = p.tayte2) AS tayte2, (SELECT nimi FROM Taytteet WHERE id = p.tayte3) AS tayte3, (SELECT nimi FROM Taytteet WHERE id = p.tayte4) AS tayte4, (SELECT nimi FROM Taytteet WHERE id = p.tayte5) AS tayte5 FROM Pizzat p";

		kysely.suoritaKysely(sql);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		while (iteraattori.hasNext()) {
			HashMap kayttajaMappi = (HashMap) iteraattori.next();
			String idString = (String) kayttajaMappi.get("id");
			String nimikanta = (String) kayttajaMappi.get("nimi");
			String hintaString = (String) kayttajaMappi.get("hinta");
			String tayte1Kanta = (String) kayttajaMappi.get("tayte1");
			String tayte2Kanta = (String) kayttajaMappi.get("tayte2");
			String tayte3Kanta = (String) kayttajaMappi.get("tayte3");
			String tayte4Kanta = (String) kayttajaMappi.get("tayte4");
			String tayte5Kanta = (String) kayttajaMappi.get("tayte5");
			int idKanta = Integer.parseInt(idString);
			double hintaKanta = Double.parseDouble(hintaString);

			// Oliolle attribuutit
			Pizza pizza = new Pizza(idKanta, nimikanta, hintaKanta, tayte1Kanta, null, null, null, null);

			if (tayte2Kanta != "NULL") {
				pizza.setTayte2(tayte2Kanta);
				if (tayte3Kanta != "NULL") {
					pizza.setTayte3(tayte3Kanta);
					if (tayte4Kanta != "NULL") {
						pizza.setTayte4(tayte4Kanta);
						if (tayte5Kanta != "NULL") {
							pizza.setTayte5(tayte5Kanta);
						}
					}
				}
			}

			pizzat.add(pizza);

		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		return pizzat;

	}
	
	public ArrayList<Tayte> haeKaikkiTaytteet() {

		// Yhteyden määritys
		Yhteys yhteys = new Yhteys();
		Kysely kysely = new Kysely(yhteys.getYhteys());

		ArrayList<Tayte> taytteet = new ArrayList<>();

		String sql = "SELECT id, nimi, saatavilla FROM Taytteet";

		kysely.suoritaKysely(sql);
		ArrayList<HashMap<String, String>> tulokset = kysely.getTulokset();

		// Iteraattorin luonti
		Iterator iteraattori = kysely.getTulokset().iterator();

		while (iteraattori.hasNext()) {
			HashMap kayttajaMappi = (HashMap) iteraattori.next();
			String idString = (String) kayttajaMappi.get("id");
			String nimikanta = (String) kayttajaMappi.get("nimi");
			String saatavillaKanta = (String) kayttajaMappi.get("saatavilla");
			int idKanta = Integer.parseInt(idString);

			// Oliolle attribuutit
			Tayte tayte = new Tayte();
			
			tayte.setId(idKanta);
			tayte.setNimi(nimikanta);
			if (saatavillaKanta.equals("K")) {
				tayte.setSaatavilla(true);
			}
			else if (saatavillaKanta.equals("E")) {
				tayte.setSaatavilla(false);
			}
			else {
				System.out.println("Täytteellä ID" + idKanta + " (" + nimikanta + ") virheellinen saatavuus: '" + saatavillaKanta + "', asetetaan false");
				tayte.setSaatavilla(false);
			}

			taytteet.add(tayte);

		}

		// Yhteyden sulkeminen
		yhteys.suljeYhteys();

		return taytteet;

	}

}
