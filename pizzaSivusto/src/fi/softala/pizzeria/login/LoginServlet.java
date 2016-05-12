package fi.softala.pizzeria.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.softala.pizzeria.apuluokka.Apuri;
import fi.softala.pizzeria.bean.Kayttaja;
import fi.softala.pizzeria.daot.KayttajaDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		// Tarkastetaan parametrit
		String logout = request.getParameter("logout");
		

		if (logout != null && logout.equals("true")) {
			kirjauduUlos(request, response);
		} else {

			// Jos käyttäjä on jo kirjautuneena, näytetään loggedin sivu, muuten
			// login
			if (sessio != null && sessio.getAttribute("kayttaja") != null) {
				String rdPath = "WEB-INF/loggedin.jsp";
				naytaSivu(request, response, rdPath);
			} else {
				String rdPath = "WEB-INF/login.jsp";
				naytaSivu(request, response, rdPath);
			}
		}
	}

	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response, String rdPath)
			throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		// Haetaan lista käyttäjistä kantayhteyden testausta varten
		KayttajaDao dao = new KayttajaDao();
		ArrayList<KayttajaLista> lista = dao.haeKayttajat();

		request.setAttribute("kayttajat", lista);

		// Request dispatcher
		RequestDispatcher rd = request.getRequestDispatcher(rdPath);
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// Katsotaan, onko action määritetty
		String action = request.getParameter("action");

		if (action != null && action.equals("login")) {
			kirjauduSisaan(request, response);
		} else if (action != null && action.equals("rekisteroidy")) {
			rekisteroidy(request, response);
		} else {
			doGet(request, response);
		}
	}

	// Käyttäjätilin luonti
	protected void rekisteroidy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String kayttajanimi = request.getParameter("kayttajatunnus");
		String salasana = request.getParameter("salasana-rek");
		String salasana2 = request.getParameter("salasana-rek2");
		String etunimi = request.getParameter("etunimi");
		String sukunimi = request.getParameter("sukunimi");
		String puhelinnro = request.getParameter("puhelinnro");

		if (kayttajanimi != null && salasana != null && etunimi != null && sukunimi != null) {
			Apuri apuri = new Apuri();
			
			// Email lowercaseen
			kayttajanimi = kayttajanimi.toLowerCase();

			System.out.println("Yritetään rekisteröidä käyttäjä:");
			System.out.println(kayttajanimi + " - " + etunimi + " " + sukunimi + " - " + puhelinnro
					+ " - salasanan pituus " + salasana.length() + " merkkiä");

			// Validointia

			if (apuri.validoiEmail(kayttajanimi) == false) {
				String virhe = "Virheellinen sähköpostiosoite!";
				virhe(request, response, virhe);
			} else {
				if (salasana.length() < 6) {
					String virhe = "Liian lyhyt salasana (alle 6 merkkiä)";
					virhe(request, response, virhe);
				} else if (!salasana.equals(salasana2)) {
					String virhe = "Syötetyt salasanat eivät täsmää!";
					virhe(request, response, virhe);
				} else {
					if (etunimi.length() < 3 || sukunimi.length() < 3) {
						String virhe = "Liian lyhyt etu- tai sukunimi!";
						virhe(request, response, virhe);
					} else if (apuri.validoiString(etunimi, "-", 50) == false
							|| apuri.validoiString(sukunimi, "-", 50) == false) {
						String virhe = "Etu- tai sukunimessä virheellisiä merkkejä!";
						virhe(request, response, virhe);
					} else {
						if (puhelinnro != null) {
							puhelinnro = puhelinnro.replace(" ", "");
							puhelinnro = puhelinnro.replace("-", "");

							if (apuri.validoiPuhNro(puhelinnro) == false) {
								String virhe = "Virheellinen puhelinnumero, asetetaan null.";
								System.out.println(virhe);
								puhelinnro = null;
							}
						}

						KayttajaDao dao = new KayttajaDao();

						HashMap<String, String> vastaus = dao.luoKayttaja(kayttajanimi, salasana, etunimi, sukunimi,
								puhelinnro);

						if (vastaus.get("virhe") != null) {
							String virhe = vastaus.get("virhe");
							virhe(request, response, virhe);
						} else if (vastaus.get("success") != null) {
							String success = vastaus.get("success");
							System.out.println("Success viesti: " + success);
							request.setAttribute("success", success);
							naytaSivu(request, response, "WEB-INF/login.jsp");
						} else {
							String virhe = "Tietokantaan viennissä tapahtui virhe!";
							virhe(request, response, virhe);
						}
					}

				}
			}
		} else {
			String virhe = "Kaikkia vaadittavia tietoja ei syötetty!";
			virhe(request, response, virhe);
		}
	}

	// Sisäänkirjautuminen
	protected void kirjauduSisaan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String reffi = request.getHeader("referer");
		String loppuosa = reffi.substring(reffi.lastIndexOf('/')+1);
		
		HashMap osoitteet = new HashMap<String, String>();
		
		osoitteet.put("pizza", "/pizza");
		osoitteet.put("ostoskori", "/ostoskori");
		
		String osoite = null;
		
		
		String[] parametrit = loppuosa.split("\\?");
		if (loppuosa.contains("?")) {
			loppuosa = loppuosa.substring(0, loppuosa.indexOf("?"));
		}
		
		if (osoitteet.containsKey(loppuosa)) {
		 osoite = (String)osoitteet.get(loppuosa);
		}
		
		if (parametrit.length == 2) {
			parametrit = parametrit[1].split("&");
		}
		
		for (int i = 0; i < parametrit.length; i++) {
			System.out.println(parametrit[i]);
			if (parametrit[i].equals("tilaukseen=true")) {
				osoite = "/tilaus";
			}
		}
		
		String kayttajanimi = request.getParameter("kayttajanimi");
		String salasana = request.getParameter("salasana");

		// Katsotaan onko parametreja olemassa
		if (kayttajanimi != null && salasana != null) {
			System.out.println("Kirjautumisyritys - tunnus: " + kayttajanimi + " - salasanan pituus: " + salasana.length() + " merkkiä");

			// Käyttäjänimi lowercaseen
			kayttajanimi = kayttajanimi.toLowerCase();
			
			// Validoidaan käyttäjänimi
			Apuri apuri = new Apuri();
			Boolean validity = apuri.validoiEmail(kayttajanimi);
			System.out.println("Email validity: " + validity);

			// Jos virheellinen email, annetaan errori
			if (validity == false) {
				System.out.println("Käyttäjätunnus annettu väärässä muodossa, redirectataan login sivulle");
				virhe(request, response, "Käyttäjätunnus annettu väärässä muodossa!");
			} else {

				KayttajaDao kayttajaDao = new KayttajaDao();
				Kayttaja kayttaja = kayttajaDao.kirjaudu(kayttajanimi, salasana);
				System.out.println(kayttaja.toString());
				if (kayttaja.getTunnus() != null) {
					HttpSession sessio = request.getSession(true);
					sessio.setAttribute("kayttaja", kayttaja);

					request.setAttribute("kayttaja", kayttaja);
					request.setAttribute("success", "Olet kirjautunut sisään onnistuneesti!");
					
					if (osoite == null) {
						response.sendRedirect(request.getContextPath() + "/kayttaja?loggedin=true");
					}
					else {
						response.sendRedirect(request.getContextPath() + osoite + "?loggedin=true");
					}
					
					
				} else {
					System.out.println("Virheellinen käyttäjätunnus/salasana, redirectataan login sivulle");
					virhe(request, response, "Virheellinen käyttäjätunnus/salasana!");
				}

			}

		} else {
			// Jos logineita ei ole määritetty, annetaan errori
			System.out.println("Login yritys ilman useria ja/tai passua.");
			virhe(request, response, "Käyttäjätunnusta ja/tai salasanaa ei syötetty!");
		}
	}

	// Uloskirjautuminen
	protected void kirjauduUlos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessio = request.getSession(false);

		// Katsotaan onko käyttäjä kirjautuneena sisään
		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			
			// Otetaan ostoskori talteen
			Object ostoskori = sessio.getAttribute("ostoskori");
			
			// Poistetaan käyttäjä ja invalidoidaan sessio
			sessio.removeAttribute("kayttaja");
			sessio.invalidate();
			
			// Luodaan heti uusi sessio ja siirretään ostoskori sille
			sessio = request.getSession(true);
			if (ostoskori != null ) {
				sessio.setAttribute("ostoskori", ostoskori);
			}
			
			request.setAttribute("success", "Olet kirjautunut ulos onnistuneesti!");

			String rdPath = "zeroindex.jsp";
			naytaSivu(request, response, rdPath);
		} else {
			String rdPath = "zeroindex.jsp";
			naytaSivu(request, response, rdPath);
		}
	}

	// Error-attribuutin asetus ja redirect
	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		request.setAttribute("virhe", virhe);
		System.out.println(virhe);
		String rdPath = "index.jsp";
		naytaSivu(request, response, rdPath);
	}

}
