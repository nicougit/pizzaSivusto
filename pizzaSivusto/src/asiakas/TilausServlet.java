package asiakas;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import apuluokka.Apuri;
import bean.Kayttaja;
import daot.AsiakasDao;
import daot.HallintaDao;
import daot.KayttajaDao;

@WebServlet(name = "tilaus", urlPatterns = { "/tilaus" })

public class TilausServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TilausServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessio = request.getSession(false);

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			// Päivitetään käyttäjän osoitteet, siltä varalta että on lisätty
			// uusia sisäänkirjautumisen jälkeen
			KayttajaDao kayttajadao = new KayttajaDao();

			try {
				// Haetaan käyttäjä sessiosta
				Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");

				// Päivitetään osoitelista
				kayttaja.setOsoitteet(kayttajadao.haeOsoitteet(String.valueOf(kayttaja.getId())));

				// Korvataan session vanha käyttäjä uudella, jossa päivitetyt
				// osoitteet!
				sessio.setAttribute("kayttaja", kayttaja);
			} catch (Exception ex) {
				System.out.println("Käyttäjää castatessa virhe tilausservletissä!");
			}
			naytaSivu(request, response);
		} else {
			// Jos käyttäjä ei ole kirjautunut, ohjataan login -sivulle
			response.sendRedirect("/login");
		}

	}

	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/tilaus.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sessio = request.getSession(false);

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {

			String action = request.getParameter("action");
			if (action != null && action.equals("lisaaosoite")) {
				lisaaOsoite(request, response);
			} else {
				doGet(request, response);
			}
		} else {
			// Jos käyttäjä ei ole kirjautunut, ohjataan login -sivulle
			response.sendRedirect("/login");
		}

	}

	protected void lisaaOsoite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lahiosoite = request.getParameter("lahiosoite");
		String postinumero = request.getParameter("postinumero");
		String postitoimipaikka = request.getParameter("postitoimipaikka");
		String kayttajaid = null;
		
		HttpSession sessio = request.getSession(false);

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			try {
				Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
				kayttajaid = String.valueOf(kayttaja.getId());
			} catch(Exception ex) {
				System.out.println("Virhe käyttäjää castatessa");
			}
		}

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (lahiosoite != null && postinumero != null && postitoimipaikka != null && kayttajaid != null) {
			if (apuri.validoiString(lahiosoite, " -", 50) == true && apuri.validoiPostinro(postinumero) == true && apuri.validoiString(postitoimipaikka, "-", 50)) {

				KayttajaDao dao = new KayttajaDao();

				// Katsotaan, onnistuuko päivitys
				HashMap<String, String> vastaus = dao.lisaaOsoite(kayttajaid, lahiosoite, postinumero, postitoimipaikka);
				if (vastaus.get("virhe") != null) {
					String virhe = vastaus.get("virhe");
					request.setAttribute("virhe", virhe);
				} else if (vastaus.get("success") != null) {
					String success = vastaus.get("success");
					request.setAttribute("success", success);
				} else {
					request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
				}

				doGet(request, response);
			}
			else {
				String virhe = "Osoitetiedoissa oli virheitä";
				virhe(request, response, virhe);
			}

		} else {
			String virhe = "Kaikkia osoitetietoja ei syötetty";
			virhe(request, response, virhe);
		}
	}

	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		System.out.println(virhe);
		request.setAttribute("virhe", virhe);
		naytaSivu(request, response);
	}

}
