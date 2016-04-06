package bean;

public class Ostos {

	int id;
	String tyyppi;

	public Ostos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ostos(int id, String tyyppi) {
		super();
		this.id = id;
		this.tyyppi = tyyppi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTyyppi() {
		return tyyppi;
	}

	public void setTyyppi(String tyyppi) {
		this.tyyppi = tyyppi;
	}

	@Override
	public String toString() {
		return "Ostos [id=" + id + ", tyyppi=" + tyyppi + "]";
	}

}
