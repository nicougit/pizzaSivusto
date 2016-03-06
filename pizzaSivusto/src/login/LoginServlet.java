package login;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import apuluokka.Apuri;
import apuluokka.DeployAsetukset;
import bean.Kayttaja;
import daot.KayttajaDao;

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
		} else {
			doGet(request, response);
		}

	}

	// Sisäänkirjautuminen
	protected void kirjauduSisaan(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String kayttajanimi = request.getParameter("kayttajanimi");
		String salasana = request.getParameter("salasana");

		// Katsotaan onko parametreja olemassa
		if (kayttajanimi != null && salasana != null) {
			System.out.println("Kirjautumisyritys - user: " + kayttajanimi + " - pass: " + salasana);

			// Validoidaan käyttäjänimi (estetään ainakin injektiot)
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
					RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/loggedin.jsp");
					rd.forward(request, response);
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
			sessio.removeAttribute("kayttaja");
			sessio.invalidate();
			request.setAttribute("success", "Olet kirjautunut ulos onnistuneesti!");
			
			String rdPath = "WEB-INF/login.jsp";
			naytaSivu(request, response, rdPath);
		} else {
			// Suoritetaan, jos käyttäjä ei ole kirjautunut sisään
			String virhe = "Et ole kirjautunut sisään.";
			virhe(request, response, virhe);
		}
	}

	// Error-attribuutin asetus ja redirect
	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		request.setAttribute("virhe", virhe);
		
		String rdPath = "WEB-INF/login.jsp";
		naytaSivu(request, response, rdPath);
	}

}
