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
	
	// Puhelinnumeron validointi
	public boolean validoiPuhNro(String puhnro) {
		Pattern patterni = Pattern.compile("^[\\d]{8,12}$", Pattern.CASE_INSENSITIVE);
		Matcher matcheri = patterni.matcher(puhnro);
		return matcheri.find();
	}
	
	// Regex patterni stringeille. Sallii kirjaimet, numerot ja välilyönnit.
	public boolean validoiString(String stringi, String erikoismerkit, int maxpituus) {
		Pattern patterni = Pattern.compile("^[\\wÄÖ][\\w\\s+äöÄÖ" + erikoismerkit + "]{2," + (maxpituus - 2) + "}[\\wäö]$");
		Matcher matcheri = patterni.matcher(stringi);
		return matcheri.find();
	}
	
	// Lähinnä ID validointeja varten
	public boolean validoiInt(String stringi, int maxpituus) {
		try {
			// Koitetaan vääntää intiksi ja katsotaan onko positiivinen
			int intti = Integer.parseInt(stringi);
			if (intti < 0) {
				System.out.println("Apuri - Input on negatiivinen int");
				return false;
			}
			
		} catch (Exception ex) {
			System.out.println("Apuri - Inputtia ei voitu kääntää intiksi");
			return false;
		}
		Pattern patterni = Pattern.compile("^[\\d+]{1," + maxpituus + "}$");
		Matcher matcheri = patterni.matcher(stringi);
		return matcheri.find();
	}
	
	// Lähinnä hintojen validointia varten
	public boolean validoiDouble(String stringi, int maxpituus) {
		try {
			// Koitetaan vääntää doubleksi ja katsotaan onko positiivinen
			double arvo = Double.parseDouble(stringi);
			if (arvo < 0) {
				System.out.println("Apuri - Input on negatiivinen double");
				return false;
			}
			else if (arvo > 999) {
				System.out.println("Apuri - Liian iso double");
				return false;
			}
			
		} catch (Exception ex) {
			System.out.println("Apuri - Inputtia ei voitu kääntää doubleksi");
			return false;
		}
		Pattern patterni = Pattern.compile("^[\\d+.]{1," + maxpituus + "}$");
		Matcher matcheri = patterni.matcher(stringi);
		return matcheri.find();
	}
	
}

