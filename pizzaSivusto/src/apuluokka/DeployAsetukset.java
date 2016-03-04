package apuluokka;

import java.util.ArrayList;

/* Tämän luokan kautta asetusten helppo säätö kaikkiin tarkoituksiin
 * Pathin säätöä tarvitaan lähinnä 
 * 
 */

public class DeployAsetukset {

	public DeployAsetukset() {
		super();
	}

	public String getPathi() {
		// Määritetään sivuston path linkkejä ja redirectejä varten
		// Määritys "/reptilemafia" koulun protoservua varten
		// Eclipsessä ajettaessa "/pizzaSivusto"
		String sivustopath = "/pizzaSivusto";
		return sivustopath;
	}

	public ArrayList<String> getSqlSettings() {

		String username;
		String password;
		String url;

		// Proto servun säädöt = 1
		// Nicon oman servun säädöt = 2
		int saadot = 1;

		if (saadot == 2) {
			// Säädöt - mun oma servu
			username = "javatesti";
			password = "salsasana";
			url = "jdbc:mysql://192.168.10.35:3306/Pizzat";
		} else {
			// Säädöt - Koulun protokanta
			/*
			 * Huom. Oletuksena tossa Nicon loginit protoservun tietokantaan
			 * Voitte tehdä myös omaan prototietokantaan tarvittavat taulut Ja
			 * muuttaa noi arvot. Pitää myöttää myös urlista opiskelijanumero
			 */
			username = "a1500955";
			password = "paFAtd56t";
			url = "jdbc:mysql://localhost:3306/a1500955";
		}

		ArrayList<String> lista = new ArrayList<>();
		lista.add(username);
		lista.add(password);
		lista.add(url);

		return lista;

	}

}
