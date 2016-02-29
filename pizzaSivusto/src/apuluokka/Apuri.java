package apuluokka;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Apuri {

	// Email validointi
	public boolean validoiEmail(String email) {

		// Regex pattern validille mailille
		Pattern patterni = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcheri = patterni.matcher(email);
		
		return matcheri.find();
	}

}
