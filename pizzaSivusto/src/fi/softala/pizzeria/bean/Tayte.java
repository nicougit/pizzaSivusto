package fi.softala.pizzeria.bean;

public class Tayte {

	int id;
	String nimi;
	Boolean saatavilla;

	public Tayte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tayte(int id, String nimi, Boolean saatavuus) {
		super();
		this.id = id;
		this.nimi = nimi;
		this.saatavilla = saatavuus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNimi() {
		return nimi;
	}

	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	public Boolean getSaatavilla() {
		return saatavilla;
	}

	public void setSaatavilla(Boolean saatavuus) {
		this.saatavilla = saatavuus;
	}

}
