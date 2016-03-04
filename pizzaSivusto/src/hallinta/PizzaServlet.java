package hallinta;

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
import bean.Pizza;
import daot.HallintaDao;

/**
 * Servlet implementation class Pizza
 */
@WebServlet(name = "pizza", urlPatterns = { "/pizza" })
public class PizzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Määritetään sivuston path linkkejä ja redirectejä varten
	// Määritys "/reptilemafia" koulun protoservua varten
	// Eclipsessä ajettaessa "/pizzaSivusto"
	private String sivustopath = "/pizzaSivusto";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PizzaServlet() {
		super();
		// TODO Auto-generated constructor stub
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

		// Asetetaan sivun path
		request.setAttribute("pathi", sivustopath);

		// Daon alustus
		HallintaDao dao = new HallintaDao();

		RequestDispatcher rd = request.getRequestDispatcher("pizzasivu.jsp");
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

		String action = request.getParameter("action");
		String poistapizza = request.getParameter("poistapizza");
		String palautapizza = request.getParameter("palautapizza");

		System.out.println("Saavuttiin PizzaServlettiin. Action: " + action);

		if (action != null && action.equals("Lisaa pizza")) {
			lisaaPizza(request, response);
		} else if (poistapizza != null) {
			poistaPizza(request, response);
		} else if (palautapizza != null) {
			palautaPizza(request, response);
		} else {
			response.sendRedirect(sivustopath + "/hallinta");
		}

	}

	public void lisaaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		// Täytteiden lisäykselle voi myöhemmin keksiä paremmin keinon,
		// toteutetaan nyt jotenkin
		String pizzanimi = request.getParameter("pizzanimi");
		String pizzahinta = request.getParameter("pizzahinta").replace(",", ".");
		String pizzat1 = request.getParameter("pizzatayte1");
		String pizzat2 = request.getParameter("pizzatayte2");
		String pizzat3 = request.getParameter("pizzatayte3");
		String pizzat4 = request.getParameter("pizzatayte4");
		String pizzat5 = request.getParameter("pizzatayte5");

		// Helpompi validointi ja debugaus, ei tosin hyvä tapa tehdä
		// Mutta pizzan lisäystä varmaan muutetaan muutenkin vielä
		String taytepotko = pizzat1 + pizzat2 + pizzat3 + pizzat4 + pizzat5;

		System.out.println("Yritetään lisätä pizzaa attribuuteilla:");
		System.out.println("Nimi: " + pizzanimi + " - Hinta: " + pizzahinta + " - Taytteet: " + taytepotko);

		if (pizzanimi != null && pizzahinta != null && pizzat1 != null) {

			// Entryjen validointia
			Apuri apuri = new Apuri();

			if (apuri.validoiString(pizzanimi, "", 30) != true) {
				String virhe = "Lisättävän pizzan nimi on virheellinen!";
				System.out.println(virhe);
				virhe(request, response, virhe);
			} else {

				try {
					// Tehdään vaan, jotta nähdään voiko muuntaa doubleksi
					double hinta = Double.parseDouble(pizzahinta);

					if (apuri.validoiInt(taytepotko) != true) {
						String virhe = "Lisättävän pizzan täytteissä oli virheitä!";
						System.out.println(virhe);
						virhe(request, response, virhe);
					} else {

						// Lisätään täytteet ArrayListiin
						ArrayList<String> taytteet = new ArrayList<>();

						if (!pizzat1.equals("0")) {
							taytteet.add(pizzat1);
						}
						if (!pizzat2.equals("0")) {
							taytteet.add(pizzat2);
						}
						if (!pizzat3.equals("0")) {
							taytteet.add(pizzat3);
						}
						if (!pizzat4.equals("0")) {
							taytteet.add(pizzat4);
						}
						if (!pizzat5.equals("0")) {
							taytteet.add(pizzat5);
						}

						if (taytteet.size() > 0) {
							System.out.println("Pizzan input virheetön, yritetään lisätä tietokantaan.");

							HallintaDao dao = new HallintaDao();

							// Katsotaan, onnistuuko lisäys
							boolean success = dao.lisaaPizza(pizzanimi, pizzahinta, taytteet);
							if (success == true) {
								request.setAttribute("success", "Pizza lisätty tietokantaan onnistuneesti!");
							} else {
								request.setAttribute("virhe",
										"Pizzan tiedot OK, mutta tietokantaan lisäyksessä tapahtui virhe.");
							}
							doGet(request, response);

						} else {
							String virhe = "Ei yhtään täytettä valittuna!";
							System.out.println(virhe);
							virhe(request, response, virhe);
						}

					}

				} catch (Exception ex) {
					String virhe = "Lisättävän pizzan hinta on virheellinen!";
					System.out.println(virhe);
					virhe(request, response, virhe);

				}

			}

		}

	}

	public void poistaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String poistapizza = request.getParameter("poistapizza");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (apuri.validoiInt(poistapizza) == false) {
			String virhe = "Poistettavan pizzan ID ei ole validi!";
			virhe(request, response, virhe);
		} else {
			System.out.println("Yritetään poistaa pizza ID: " + poistapizza);

			HallintaDao dao = new HallintaDao();

			boolean success = dao.poistaPizza(poistapizza);

			if (success == true) {
				request.setAttribute("success", "Pizzaan lisätty poistomerkintä onnistuneesti!");
			} else {
				request.setAttribute("virhe", "Poistomerkintä OK, mutta tietokantaan päivityksessä tapahtui virhe.");
			}
			doGet(request, response);

		}

	}

	public void palautaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String palautapizza = request.getParameter("palautapizza");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (apuri.validoiInt(palautapizza) == false) {
			String virhe = "Palautettavan pizzan ID ei ole validi!";
			virhe(request, response, virhe);
		} else {
			System.out.println("Yritetään palauttaa pizza ID: " + palautapizza);

			HallintaDao dao = new HallintaDao();

			boolean success = dao.palautaPizza(palautapizza);

			if (success == true) {
				request.setAttribute("success", "Pizzan poistomerkintä kumottu onnistuneesti!");
			} else {
				request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui virhe.");
			}
			doGet(request, response);

		}

	}

	// Error-attribuutin asetus ja redirect
	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		request.setAttribute("virhe", virhe);
		doGet(request, response);
	}

}
