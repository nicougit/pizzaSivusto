package fi.softala.pizzeria.bean;

public class Aukioloaika {

	String paiva;
	String aloitusaika;
	String sulkemisaika;
	
	public Aukioloaika() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Aukioloaika(String paiva, String aloitusaika, String sulkemisaika) {
		super();
		this.paiva = paiva;
		this.aloitusaika = aloitusaika;
		this.sulkemisaika = sulkemisaika;
	}

	public String getPaiva() {
		return paiva;
	}

	public void setPaiva(String paiva) {
		this.paiva = paiva;
	}

	public String getAloitusaika() {
		return aloitusaika;
	}

	public void setAloitusaika(String aloitusaika) {
		this.aloitusaika = aloitusaika;
	}

	public String getSulkemisaika() {
		return sulkemisaika;
	}

	public void setSulkemisaika(String sulkemisaika) {
		this.sulkemisaika = sulkemisaika;
	}

	@Override
	public String toString() {
		return "Aukioloaika [paiva=" + paiva + ", aloitusaika=" + aloitusaika + ", sulkemisaika=" + sulkemisaika + "]";
	}
	
	
}

