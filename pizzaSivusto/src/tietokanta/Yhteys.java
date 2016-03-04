package tietokanta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import apuluokka.DeployAsetukset;

// Luokka tietokantayhteyden avaamiselle ja sulkemiselle

public class Yhteys {

	private Connection yhteys = null;
	
	public Yhteys() {
		avaaYhteys();
	}

	private void avaaYhteys() {
		
		// Asetusten haku
		DeployAsetukset asetukset = new DeployAsetukset();
		ArrayList<String> asetuslista = asetukset.getSqlSettings();
		String username = asetuslista.get(0);
		String password = asetuslista.get(1);
		String url = asetuslista.get(2);

		try {
			// Ajurin lataus SQLite
			Class.forName("com.mysql.jdbc.Driver");

			// Yhteyden avaus
			yhteys = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			// Virheet
			System.out.println("Tietokantayhteyden avauksessa tapahtui virhe");
			e.printStackTrace();
		}

	}

	public void suljeYhteys() {
		try {
			if (yhteys != null && !yhteys.isClosed())
				yhteys.close();
		} catch (Exception e) {
			System.out.println("Tietokantayhteys ei jostain syystä suostu menemään kiinni.");
		}
	}
	
    public Connection getYhteys() {
        if (yhteys == null) {
            avaaYhteys();
        }

        return yhteys;
    }

}