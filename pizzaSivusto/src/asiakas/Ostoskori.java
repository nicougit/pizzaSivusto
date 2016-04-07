package asiakas;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import apuluokka.Apuri;
import daot.AsiakasDao;
import daot.HallintaDao;
import bean.Ostos;
import bean.Pizza;
import bean.Tayte;

// Tämä tiedosto on vielä varhaisessa betavaiheessa! t:Pasi

@WebServlet(name = "ostoskori", urlPatterns = { "/ostoskori" })
public class Ostoskori extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Ostoskori() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		String ostoskoriJsonina = request.getParameter("ostoskoriJsonina");

		if (ostoskoriJsonina != null) {
			ostoskoriJsonina(request, response);
		} else {
			naytaSivu(request, response);
		}

	}

	// Varsinaisten toimintojen käsittely
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		String action = request.getParameter("action");

		if (action != null) {
			if (action.equals("lisaa")) {
				lisaaTuote(request, response);
			} else if (action.equals("tyhjenna")) {
				tyhjennaOstoskori(request, response);
			} else if (action.equals("poista")) {
				poistaTuote(request, response);
			}
			else {
				String virhe = "Tuntematon action";
				virhe(request, response, virhe);
			}
		} else {
			doGet(request, response);
		}

	}

	// Tuotteen lisäys ostoskoriin
	protected void lisaaTuote(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		// Haetaan ostoskori
		HashMap<String, ArrayList> ostoskori = haeOstoskori(request, response);
		ArrayList<Pizza> ostoskoriPizzat = ostoskori.get("pizzat");
		sessio.setAttribute("ostoskori", ostoskori);

		String id = request.getParameter("id");
		String tyyppi = request.getParameter("tyyppi");
		String json = request.getParameter("json");

		Apuri apuri = new Apuri();

		if (id != null && tyyppi != null) {
			// Pizzan lisäys
			if (tyyppi.equals("pizza")) {
				if (apuri.validoiInt(id, 11) == false) {
					String virhe = "Pizzalla on virheellinen ID";
					virhe(request, response, virhe);
				} else {
					AsiakasDao dao = new AsiakasDao();
					Pizza pizza = dao.haeYksiPizza(id);
					if (pizza.getNimi() == null) {
						String virhe = "Pizzaa ei ole tietokannassa, tai se ei ole saatavilla";
						virhe(request, response, virhe);
					} else {
						ostoskoriPizzat.add(pizza);
						ostoskori.put("pizzat", ostoskoriPizzat);
						sessio.setAttribute("ostoskori", ostoskori);
						if (json != null) {
							HashMap<String, String> vastaus = new HashMap<>();
							vastaus.put("success", pizza.getNimi() + " lisätty ostoskoriin!");
							jsonVastaus(request, response, vastaus);
						} else {
							naytaSivu(request, response);
						}
					}
				}
			} else {
				String virhe = "Tuotteella on tuntematon tyyppi";
				virhe(request, response, virhe);
			}
		} else {
			String virhe = "Kaikkia parametreja ei annettu";
			virhe(request, response, virhe);
		}
	}

	// Tuotteen poisto ostoskorista (tällä hetkellä vain pizzat)
	protected void poistaTuote(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		// Haetaan ostoskori
		HashMap<String, ArrayList> ostoskori = haeOstoskori(request, response);
		ArrayList<Pizza> ostoskoriPizzat = ostoskori.get("pizzat");

		String index = request.getParameter("index");
		String json = request.getParameter("json");

		Apuri apuri = new Apuri();

		if (index != null) {
			if (apuri.validoiInt(index, 10) == true) {
				int indexint = 9999;
				try {
					indexint = Integer.parseInt(index);
				} catch (Exception ex) {
					System.out.println("Virhe tuotteen poistossa ostoskorista - " + ex);
				}
				if (ostoskoriPizzat.size() >= indexint) {
					String nimi = ostoskoriPizzat.get(indexint).getNimi();
					ostoskoriPizzat.remove(indexint);
					ostoskori.put("pizzat", ostoskoriPizzat);
					sessio.setAttribute("ostoskori", ostoskori);
					if (json != null) {
						HashMap<String, String> vastaus = new HashMap<>();
						vastaus.put("success", nimi + " poistettu ostoskorista");
						jsonVastaus(request, response, vastaus);
					} else {
						request.setAttribute("success", nimi + " poistettu ostoskorista");
						naytaSivu(request, response);
					}

				} else {
					String virhe = "Virheellinen ID poistettavalle tuotteelle";
					virhe(request, response, virhe);
				}

			} else {
				String virhe = "Virheellinen ID poistettavalle tuotteelle";
				virhe(request, response, virhe);
			}
		} else {
			String virhe = "Virheellinen ID poistettavalle tuotteelle";
			virhe(request, response, virhe);
		}

	}

	// Perus .jsp sivun näyttäminen
	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan ostoskori
		HashMap<String, ArrayList> ostoskori = haeOstoskori(request, response);
		ArrayList<Pizza> ostoskoriPizzat = ostoskori.get("pizzat");

		// RequestDispatcher
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/ostoskori.jsp");

		request.setAttribute("pizzat", ostoskoriPizzat);
		rd.forward(request, response);
	}

	// Success- ja virheilmoitusten välitys JavaScriptille JSON-muodossa
	protected void jsonVastaus(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, String> vastaus) throws ServletException, IOException {

		JSONArray jsonarray = new JSONArray();
		JSONObject jsonvastaus = new JSONObject();

		if (vastaus.get("virhe") != null) {
			jsonvastaus.put("virhe", vastaus.get("virhe"));
		} else if (vastaus.get("success") != null) {
			jsonvastaus.put("success", vastaus.get("success"));
		} else {
			jsonvastaus.put("virhe", "Tuntematon virhe JSONia käsitellessä");
		}

		jsonarray.add(jsonvastaus);

		// Encoding ja printtaus
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		out.print(jsonarray);

	}

	// Error-attribuutin asetus ja redirect
	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		String json = request.getParameter("json");
		System.out.println(virhe);
		if (json != null) {
			HashMap<String, String> vastaus = new HashMap<>();
			vastaus.put("virhe", virhe);
			jsonVastaus(request, response, vastaus);
		} else {
			request.setAttribute("virhe", virhe);
			naytaSivu(request, response);
		}
	}

	// Ostoskorin tyhjennys
	protected void tyhjennaOstoskori(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		// Luodaan tyhjät oliot ostoskorille
		HashMap<String, ArrayList> ostoskori = new HashMap<>();
		ArrayList<Pizza> ostoskoriPizzat = new ArrayList<>();
		ostoskori.put("pizzat", ostoskoriPizzat);

		// Asetetaan tyhjät oliot sessiolle
		sessio.setAttribute("ostoskori", ostoskori);
		String json = request.getParameter("json");
		if (json != null) {
			HashMap<String, String> vastaus = new HashMap<>();
			vastaus.put("success", "Ostoskori tyhjennetty!");
			jsonVastaus(request, response, vastaus);
		} else {
			request.setAttribute("success", "Ostoskori tyhjennetty!");
			doGet(request, response);
		}

	}

	// Hakee ostoskorin sisällön, jos sisältöä ei ole, luo ostoskorin
	protected HashMap<String, ArrayList> haeOstoskori(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		HashMap<String, ArrayList> ostoskori = null;
		ArrayList<Pizza> ostoskoriPizzat = new ArrayList<>();
		try {
			ostoskori = (HashMap<String, ArrayList>) sessio.getAttribute("ostoskori");
		} catch (Exception ex) {
			System.out.println("Virhe ostoskoria hakiessa " + ex);
		}

		if (ostoskori == null) {
			ostoskori = new HashMap<>();
		} else {
			try {
				if (ostoskori.get("pizzat") != null) {
					ostoskoriPizzat = ostoskori.get("pizzat");
				} else {
					ostoskoriPizzat = new ArrayList<>();
				}
			} catch (Exception ex) {
				System.out.println("Virhe pizzojen noutamisessa ostoskorista " + ex);
			}
		}

		ostoskori.put("pizzat", ostoskoriPizzat);

		return ostoskori;
	}

	// Ostoskorin palautus Javascriptille JSON-muodossa
	protected void ostoskoriJsonina(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan ostoskori
		HashMap<String, ArrayList> ostoskori = haeOstoskori(request, response);
		ArrayList<Pizza> ostoskoriPizzat = ostoskori.get("pizzat");

		// Json Array
		JSONArray pizzatJson = new JSONArray();

		for (int i = 0; i < ostoskoriPizzat.size(); i++) {
			Pizza pizza = ostoskoriPizzat.get(i);
			JSONObject pizzaobjekti = new JSONObject();
			JSONArray taytearray = new JSONArray();
			pizzaobjekti.put("id", pizza.getId());
			pizzaobjekti.put("nimi", pizza.getNimi());
			pizzaobjekti.put("hinta", pizza.getHinta());
			pizzaobjekti.put("indeksi", i);
			pizzatJson.add(pizzaobjekti);
		}

		// Encoding ja printtaus
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		out.print(pizzatJson);

	}
}