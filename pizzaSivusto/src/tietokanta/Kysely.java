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
	
	// Yksinkertainen kysely, esim. kaikki pizzat tai täytteet
	public void suoritaKysely(String sql) {
		
		// Tyhjennetään tulosten arraylist
		tulokset.clear();
		
		ResultSet resultSetti = null;
		
		// Valmistellaan SQL-lause
		try {
			PreparedStatement lause = yhteys.prepareStatement(sql);
			
			// Tehdään kantahaku
			System.out.println("Suoritettava SQL-lause: " + lause.toString());
			resultSetti = lause.executeQuery();
			
			while (resultSetti.next()) {
				HashMap tulosrivi = hashMappiin(resultSetti);
				tulokset.add(tulosrivi);
			//	System.out.println("Tulosrivi: " + tulosrivi.toString());
			}
			
		} catch (SQLException ex) {
			System.out.println("Virhe SQL-lausekkeen suorituksessa - " + ex);
		} finally {
			try {
				resultSetti.close();
			} catch (SQLException ex) {
				System.out.println("Virhe resultsetin sulkemisessa - " + ex);
			}
		}
		
	}

	// SQL-lausekkeen suorittaminen parametrilla
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
				System.out.println("Tulosrivi: " + tulosrivi.toString());
			}
			
		} catch (SQLException ex) {
			System.out.println("Virhe SQL-lausekkeen suorituksessa - " + ex);
		} finally {
			try {
				resultSetti.close();
			} catch (SQLException ex) {
				System.out.println("Virhe resultsetin sulkemisessa - " + ex);
			}
		}
		
	}
	
	// SQL-vastausten tallentaminen HashMappiin
	public HashMap hashMappiin(ResultSet resultSetti) {
		HashMap<String, String> tulosrivi = new HashMap<String, String>();
		
		try {
			ResultSetMetaData metaData = resultSetti.getMetaData();
			int sarakkeita = metaData.getColumnCount();
			
			// Laitetaan jokaisen rivin tiedot HashMappiin
			// Muutettu 'getColumnName' -> 'getColumnLabel' jotta voidaan käyttää kyselyssä AS-määrittelyä nimille
			// Ei toimi muuten datan hakeminen kahdesta taulukosta joissa sama nimi, esim.
			// Pizza ja Tayte taulukko joinattuna, joissa molemmissa 'nimi' attribuutti
			for (int i = 1; i <= sarakkeita; i++) {
				tulosrivi.put(metaData.getColumnLabel(i), resultSetti.getString(i));
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
