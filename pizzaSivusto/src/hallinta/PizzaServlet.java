package hallinta;

import java.io.IOException;

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
		
		System.out.println("Saavuttiin PizzaServlettiin. Action: " + action);
		
		if (action != null && action.equals("Lisaa pizza")) {
			lisaaPizza(request, response);
		} else {
			response.sendRedirect(sivustopath + "/hallinta");
		}

	}

	public void lisaaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		// Täytteiden lisäykselle voi myöhemmin keksiä paremmin keinon, toteutetaan nyt jotenkin
		String pizzanimi = request.getParameter("pizzanimi");
		String pizzahinta = request.getParameter("pizzahinta").replace(",", ".");
		String pizzat1 = request.getParameter("pizzatayte1");
		String pizzat2 = request.getParameter("pizzatayte2");
		String pizzat3 = request.getParameter("pizzatayte3");
		String pizzat4 = request.getParameter("pizzatayte4");
		String pizzat5 = request.getParameter("pizzatayte5");
		
		// Helpompaa validity checkiä varten
		String taytepotko = pizzat1 + " " + pizzat2 + " " + pizzat3 + " " + pizzat4 + " " + pizzat5;

		System.out.println("Yritetään lisätä pizzaa attribuuteilla:");
		System.out.println("Nimi: " + pizzanimi + " - Hinta: " + pizzahinta + " - Taytteet: " + taytepotko);

		if (pizzanimi != null && pizzahinta != null && taytepotko.length() > 5) {

			// Entryjen validointia
			Apuri apuri = new Apuri();

			// Huom, tässä ei validoida mitään. Pitää tehdä se validointi.
			
			if (apuri.validoiString(pizzanimi, "", 30) != true) {
				String virhe = "Lisättävän pizzan nimi on virheellinen!";
				System.out.println(virhe);
				virhe(request, response, virhe);
			} else {

				try {
					double hinta = Double.parseDouble(pizzahinta);
					
					if (apuri.validoiString(taytepotko, "", 100) != true) {
						String virhe = "Lisättävän pizzan täytteissä oli virheitä!";
						System.out.println(virhe);
						virhe(request, response, virhe);
					}
					else {
						/*
						 * Tässä parsitaan not-null täytestringit arraylistiin
						 * 
						 * Pizzojen lisäys kantaa myöhemmin PizzaDaossa:
						 * 
						 * Ensin varmistetaan kannasta että ei duplicateja
						 * Tätä varten kirjoitettava toiminto tietokanta.Kysely-luokkaan
						 * 
						 * Insert-lausekkeiden rakenne:
						 * 
						 * INSERT INTO Pizza VALUES (null, 'pizzanimi', 'hinta', null)
						 * 
						 * INSERT INTO PizzanTayte VALUES ((SELECT pizza_id FROM Pizza WHERE nimi = 'pizzanimi'), pizzat1)
						 * Repeat joka täytteelle						 * 
						 * 
						 */
						
						
						System.out.println("Lisättävä pizza on virheetön!");
					}
					
				} catch (Exception ex) {
					String virhe = "Lisättävän pizzan hinta on virheellinen!";
					System.out.println(virhe);
					virhe(request, response, virhe);

				}

			}

		}

	}

	// Error-attribuutin asetus ja redirect
	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		request.setAttribute("virhe", virhe);
		doGet(request, response);
	}

}
