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
	
	// Alla olevat ei toimi oikein. Jatketaan sit kun aivot taas toimii.
	
	// Regex patterni stringeille. Sallii A-Z ja 0-9
	public boolean validoiString(String stringi) {
		Pattern patterni = Pattern.compile("^[A-Z0-9]$");
		Matcher matcheri = patterni.matcher(stringi);
		return matcheri.find();
	}
	
	// Regex patterni lisättävien pizzojen täytteille. Sallii A-Z, välilyönnit ja pilkut
	public boolean validoiTayte(String stringi) {
		Pattern patterni = Pattern.compile("^[A-Z0-9,\\s]$");
		Matcher matcheri = patterni.matcher(stringi);
		return matcheri.find();
	}

}
