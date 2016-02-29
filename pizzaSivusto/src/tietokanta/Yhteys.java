package tietokanta;

import java.sql.Connection;
import java.sql.DriverManager;

// Luokka tietokantayhteyden avaamiselle ja sulkemiselle

public class Yhteys {

	private Connection yhteys = null;
	
	public Yhteys() {
		avaaYhteys();
	}

	private void avaaYhteys() {
		// Säädöt - Koulun protokanta
		// String username = "a1500955";
		// String password = "paFAtd56t";
		// String url = "jdbc:mysql://localhost:3306/a1500955";

		// Säädöt - mun oma servu
		String username = "javatesti";
		String password = "salsasana";
		String url = "jdbc:mysql://192.168.10.35:3306/Pizzat";

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