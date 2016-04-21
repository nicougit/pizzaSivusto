package bean;

public class Osoite {

	int osoiteid;
	String osoite;
	String postinro;
	String postitmp;

	public Osoite() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Osoite(int osoiteid, String osoite, String postinro, String postitmp) {
		super();
		this.osoiteid = osoiteid;
		this.osoite = osoite;
		this.postinro = postinro;
		this.postitmp = postitmp;
	}

	public int getOsoiteid() {
		return osoiteid;
	}

	public void setOsoiteid(int osoiteid) {
		this.osoiteid = osoiteid;
	}

	public String getOsoite() {
		return osoite;
	}

	public void setOsoite(String osoite) {
		this.osoite = osoite;
	}

	public String getPostinro() {
		return postinro;
	}

	public void setPostinro(String postinro) {
		this.postinro = postinro;
	}

	public String getPostitmp() {
		return postitmp;
	}

	public void setPostitmp(String postitmp) {
		this.postitmp = postitmp;
	}

	@Override
	public String toString() {
		return "Osoite [osoiteid=" + osoiteid + ", osoite=" + osoite + ", postinro=" + postinro + ", postitmp="
				+ postitmp + "]";
	}

}
