package fi.softala.pizzeria.bean;

public class Juoma {

	int id;
	String nimi;
	double hinta;
	double koko;
	String kuvaus;
	String poistomerkinta;
	Boolean saatavilla;

	public Juoma() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Juoma(int id, String nimi, double hinta, double koko, String kuvaus,
			String poistomerkinta, Boolean saatavilla) {
		super();
		this.id = id;
		this.nimi = nimi;
		this.hinta = hinta;
		this.koko = koko;
		this.kuvaus = kuvaus;
		this.poistomerkinta = poistomerkinta;
		this.saatavilla = saatavilla;
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

	public double getHinta() {
		return hinta;
	}

	public void setHinta(double hinta) {
		this.hinta = hinta;
	}

	public double getKoko() {
		return koko;
	}

	public void setKoko(double koko) {
		this.koko = koko;
	}

	public String getKuvaus() {
		return kuvaus;
	}

	public void setKuvaus(String kuvaus) {
		this.kuvaus = kuvaus;
	}

	public String getPoistomerkinta() {
		return poistomerkinta;
	}

	public void setPoistomerkinta(String poistomerkinta) {
		this.poistomerkinta = poistomerkinta;
	}

	public Boolean getSaatavilla() {
		return saatavilla;
	}

	public void setSaatavilla(Boolean saatavilla) {
		this.saatavilla = saatavilla;
	}

	@Override
	public String toString() {
		return "Juoma [id=" + id + ", nimi=" + nimi + ", hinta=" + hinta
				+ ", koko=" + koko + ", kuvaus=" + kuvaus + ", poistomerkinta="
				+ poistomerkinta + ", saatavilla=" + saatavilla + "]";
	}

}
