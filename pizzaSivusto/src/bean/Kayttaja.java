package bean;

import java.util.ArrayList;

// Beani käyttäjälle

public class Kayttaja {
	private int id;
	private String tunnus;
	private String etunimi;
	private String sukunimi;
	private String puhelin;
	private String tyyppi;
	private ArrayList<Osoite> osoitteet;

	public Kayttaja() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Kayttaja(int id, String tunnus, String etunimi, String sukunimi, String puhelin, String tyyppi,
			ArrayList<Osoite> osoitteet) {
		super();
		this.id = id;
		this.tunnus = tunnus;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.puhelin = puhelin;
		this.tyyppi = tyyppi;
		this.osoitteet = osoitteet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTunnus() {
		return tunnus;
	}

	public void setTunnus(String tunnus) {
		this.tunnus = tunnus;
	}

	public String getEtunimi() {
		return etunimi;
	}

	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	public String getSukunimi() {
		return sukunimi;
	}

	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}

	public String getPuhelin() {
		return puhelin;
	}

	public void setPuhelin(String puhelin) {
		this.puhelin = puhelin;
	}

	public String getTyyppi() {
		return tyyppi;
	}

	public void setTyyppi(String tyyppi) {
		this.tyyppi = tyyppi;
	}

	public ArrayList<Osoite> getOsoitteet() {
		return osoitteet;
	}

	public void setOsoitteet(ArrayList<Osoite> osoitteet) {
		this.osoitteet = osoitteet;
	}

	@Override
	public String toString() {
		return "Kayttaja [id=" + id + ", tunnus=" + tunnus + ", etunimi=" + etunimi + ", sukunimi=" + sukunimi
				+ ", puhelin=" + puhelin + ", tyyppi=" + tyyppi + ", osoitteet=" + osoitteet + "]";
	}

	

}
