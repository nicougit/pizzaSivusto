package login;

public class KayttajaLista {

	// Login sivulle kehitysvaiheessa tulostettava käyttäjälista

	int id;
	String tunnus;
	String etunimi;
	String sukunimi;
	String tyyppi;

	public KayttajaLista() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KayttajaLista(int id, String tunnus, String etunimi, String sukunimi, String tyyppi) {
		super();
		this.id = id;
		this.tunnus = tunnus;
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.tyyppi = tyyppi;
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

	public String getTyyppi() {
		return tyyppi;
	}

	public void setTyyppi(String tyyppi) {
		this.tyyppi = tyyppi;
	}

}
