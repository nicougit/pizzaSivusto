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

		String pizzanimi = request.getParameter("pizzanimi");
		String pizzahinta = request.getParameter("pizzahinta").replace(",", ".");
		String pizzataytteet = request.getParameter("pizzataytteet");

		System.out.println("Yritetään lisätä pizzaa attribuuteilla:");
		System.out.println("Nimi: " + pizzanimi + " - Hinta: " + pizzahinta + " - Taytteet: " + pizzataytteet);

		if (pizzanimi != null && pizzahinta != null && pizzataytteet != null) {

			// Entryjen validointia
			Apuri apuri = new Apuri();

			// Huom, tässä ei validoida mitään. Pitää tehdä se validointi.
			
			if (pizzanimi.length() < 4) {
				String virhe = "Lisättävän pizzan nimi on virheellinen!";
				virhe(request, response, virhe);
			} else {

				try {
					double hinta = Double.parseDouble(pizzahinta);
					
					if (pizzataytteet.length() < 4) {
						String virhe = "Lisättävän pizzan täytteissä oli virheitä!";
						virhe(request, response, virhe);
					}
					else {
						System.out.println("Lisättävä pizza on virheetön!");
					}
					
				} catch (Exception ex) {
					String virhe = "Lisättävän pizzan hinta on virheellinen!";
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
