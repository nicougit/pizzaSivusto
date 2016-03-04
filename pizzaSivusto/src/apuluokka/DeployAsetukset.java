package apuluokka;

import java.util.ArrayList;

public class DeployAsetukset {
	
	public DeployAsetukset() {
		super();
	}

	public String getPathi() {		
		// Määritetään sivuston path linkkejä ja redirectejä varten
		// Määritys "/reptilemafia" koulun protoservua varten
		// Eclipsessä ajettaessa "/pizzaSivusto"
		String sivustopath = "/reptilemafia";
		return sivustopath;
	}
	
	public ArrayList<String> getSqlSettings() {
		
		String username;
		String password;
		String url;
		
		if (getPathi().equals("/pizzaSivusto")) {
			// Säädöt - mun oma servu
			username = "javatesti";
			password = "salsasana";
			url = "jdbc:mysql://192.168.10.35:3306/Pizzat";
		} else {
			// Säädöt - Koulun protokanta
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
