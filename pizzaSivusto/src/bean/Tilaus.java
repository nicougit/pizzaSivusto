package bean;

import java.sql.Date;
import java.util.ArrayList;

public class Tilaus {
	int tilausid;
	Kayttaja kayttaja;
	Osoite osoite;
	Date tilaushetki;
	Date toimitusaika;
	String toimitustapa;
	String lisatiedot;
	String maksutapa;
	double kokonaishinta;
	boolean maksettu;
	ArrayList<Pizza> pizzat;
	ArrayList<Juoma> juomat;

	public Tilaus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tilaus(int tilausid, Kayttaja kayttaja, Osoite osoite, Date tilaushetki, Date toimitusaika,
			String toimitustapa, String lisatiedot, String maksutapa, double kokonaishinta, boolean maksettu,
			ArrayList<Pizza> pizzat, ArrayList<Juoma> juomat) {
		super();
		this.tilausid = tilausid;
		this.kayttaja = kayttaja;
		this.osoite = osoite;
		this.tilaushetki = tilaushetki;
		this.toimitusaika = toimitusaika;
		this.toimitustapa = toimitustapa;
		this.lisatiedot = lisatiedot;
		this.maksutapa = maksutapa;
		this.kokonaishinta = kokonaishinta;
		this.maksettu = maksettu;
		this.pizzat = pizzat;
		this.juomat = juomat;
	}

	public int getTilausid() {
		return tilausid;
	}

	public void setTilausid(int tilausid) {
		this.tilausid = tilausid;
	}

	public Kayttaja getKayttaja() {
		return kayttaja;
	}

	public void setKayttaja(Kayttaja kayttaja) {
		this.kayttaja = kayttaja;
	}

	public Osoite getOsoite() {
		return osoite;
	}

	public void setOsoite(Osoite osoite) {
		this.osoite = osoite;
	}

	public Date getTilaushetki() {
		return tilaushetki;
	}

	public void setTilaushetki(Date tilaushetki) {
		this.tilaushetki = tilaushetki;
	}

	public Date getToimitusaika() {
		return toimitusaika;
	}

	public void setToimitusaika(Date toimitusaika) {
		this.toimitusaika = toimitusaika;
	}

	public String getToimitustapa() {
		return toimitustapa;
	}

	public void setToimitustapa(String toimitustapa) {
		this.toimitustapa = toimitustapa;
	}

	public String getLisatiedot() {
		return lisatiedot;
	}

	public void setLisatiedot(String lisatiedot) {
		this.lisatiedot = lisatiedot;
	}

	public String getMaksutapa() {
		return maksutapa;
	}

	public void setMaksutapa(String maksutapa) {
		this.maksutapa = maksutapa;
	}

	public double getKokonaishinta() {
		return kokonaishinta;
	}

	public void setKokonaishinta(double kokonaishinta) {
		this.kokonaishinta = kokonaishinta;
	}

	public boolean isMaksettu() {
		return maksettu;
	}

	public void setMaksettu(boolean maksettu) {
		this.maksettu = maksettu;
	}

	public ArrayList<Pizza> getPizzat() {
		return pizzat;
	}

	public void setPizzat(ArrayList<Pizza> pizzat) {
		this.pizzat = pizzat;
	}

	public ArrayList<Juoma> getJuomat() {
		return juomat;
	}

	public void setJuomat(ArrayList<Juoma> juomat) {
		this.juomat = juomat;
	}

}
