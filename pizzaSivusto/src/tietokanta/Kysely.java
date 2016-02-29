package tietokanta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

// Luokka tietokantakyselyille

public class Kysely {

	private ArrayList<HashMap> tulokset;
	private Connection yhteys;

	public Kysely(Connection avattuYhteys) {
		tulokset = new ArrayList<HashMap>();
		yhteys = avattuYhteys;
	}

	// SQL-lausekkeen suorittaminen
	public void suoritaYksiKyselyParam(String sql, ArrayList<String> parametrit) {
		
		// Tyhjennetään tulosten arraylist
		tulokset.clear();
		
		ResultSet resultSetti = null;
		
		// Valmistellaan SQL-lause
		try {
			PreparedStatement lause = yhteys.prepareStatement(sql);
			
			for (int i = 0; i < parametrit.size(); i++) {
				lause.setObject(i + 1, parametrit.get(i));
			}
			
			// Tehdään kantahaku
			System.out.println("Suoritettava SQL-lause: " + lause.toString());
			resultSetti = lause.executeQuery();
			
			while (resultSetti.next()) {
				HashMap tulosrivi = hashMappiin(resultSetti);
				tulokset.add(tulosrivi);
			}
			
		} catch (SQLException ex) {
			System.out.println("Virhe SQL-lausekkeen suorituksessa - " + ex);
		}
		
	}
	
	// SQL-vastausten tallentaminen HashMappiin
	public HashMap hashMappiin(ResultSet resultSetti) {
		HashMap<String, String> tulosrivi = new HashMap<String, String>();
		
		try {
			ResultSetMetaData metaData = resultSetti.getMetaData();
			int sarakkeita = metaData.getColumnCount();
			
			// Laitetaan jokaisen rivin tiedot HashMappiin
			for (int i = 1; i <= sarakkeita; i++) {
				tulosrivi.put(metaData.getColumnName(i), resultSetti.getString(i));
			}
			
		} catch (Exception ex) {
			System.out.println("Virhe HashMappiin tallentaessa - " + ex);
		}
		
		return tulosrivi;
	}

	public ArrayList getTulokset() {
		return tulokset;
	}	

}
