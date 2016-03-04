package apuluokka;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Apuri {

	// Email validointi, sallii normi email osoitteet
	public boolean validoiEmail(String email) {
		Pattern patterni = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcheri = patterni.matcher(email);
		return matcheri.find();
	}
	
	// Regex patterni stringeille. Sallii kirjaimet, numerot ja välilyönnit.
	public boolean validoiString(String stringi, String erikoismerkit, int maxpituus) {
		Pattern patterni = Pattern.compile("^[\\w\\s+äö" + erikoismerkit + "]{3," + maxpituus + "}$");
		Matcher matcheri = patterni.matcher(stringi);
		return matcheri.find();
	}
	
	// Lisättävien pizzojen täytteiden ID-validointia varten
	public boolean validoiInt(String stringi) {
		Pattern patterni = Pattern.compile("^[\\d+]{1,15}$");
		Matcher matcheri = patterni.matcher(stringi);
		return matcheri.find();
	}
	
}
