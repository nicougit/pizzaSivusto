package fi.softala.pizzeria.hallinta;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import fi.softala.pizzeria.bean.Juoma;
import fi.softala.pizzeria.bean.Kayttaja;
import fi.softala.pizzeria.bean.Pizza;
import fi.softala.pizzeria.bean.Tayte;
import fi.softala.pizzeria.bean.Tilaus;
import fi.softala.pizzeria.daot.HallintaDao;
import fi.softala.pizzeria.daot.TilausDao;

/**
 * Servlet implementation class TilauksetServlet
 */
@WebServlet(name = "tilaukset", urlPatterns = { "/tilaukset" })
public class TilauksetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TilauksetServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String json = request.getParameter("json");

		// Katsotaan oikeudet
		HttpSession sessio = request.getSession(false);

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
			if (kayttaja.getTyyppi().equals("admin")
					|| kayttaja.getTyyppi().equals("staff")) {

				String action = request.getParameter("action");

				if (action != null && action.equals("tilauksetJsonina")) {
					tilauksetJsonina(request, response);
				} else {
					naytaSivu(request, response);
				}

			} else {
				if (json != null) {
					String virhe = "Pääsy evätty! Sinulla pitää olla staff- tai admin-tunnukset!";
					virhe(request, response, virhe);
				} else {
					paasyEvatty(request, response);
				}
			}
		} else {
			if (json != null) {
				String virhe = "Pääsy evätty! Et ole kirjautunut sisään!";
				virhe(request, response, virhe);
			} else {
				paasyEvatty(request, response);
			}
		}
	}

	protected void tilauksetJsonina(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Tietojen haku
		TilausDao dao = new TilausDao();
		ArrayList<Tilaus> tilaukset = dao.haeTilaukset();

		// Pizzojen JSON-array
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < tilaukset.size(); i++) {
			Tilaus tilaus = tilaukset.get(i);
			JSONObject objekti = new JSONObject();
			JSONObject kayttaja = new JSONObject();
			objekti.put("id", tilaus.getTilausid());
			objekti.put("kokonaishinta", tilaus.getKokonaishinta());
			objekti.put("lisatiedot", tilaus.getLisatiedot());
			objekti.put("tilaushetki", tilaus.getTilaushetki().getTime());
			objekti.put("maksutapa", tilaus.getMaksutapa());
			objekti.put("toimitustapa", tilaus.getToimitustapa());
			objekti.put("maksutilanne", tilaus.isMaksettu());
			objekti.put("status", tilaus.getStatus());

			kayttaja.put("id", tilaus.getKayttaja().getId());
			kayttaja.put("etunimi", tilaus.getKayttaja().getEtunimi());
			kayttaja.put("sukunimi", tilaus.getKayttaja().getSukunimi());
			kayttaja.put("puhelin", tilaus.getKayttaja().getPuhelin());
			kayttaja.put("tunnus", tilaus.getKayttaja().getTunnus());

			JSONArray pizzalista = new JSONArray();
			JSONArray juomalista = new JSONArray();

			if (tilaus.getPizzat() != null) {
				for (int j = 0; j < tilaus.getPizzat().size(); j++) {
					Pizza pizza = tilaus.getPizzat().get(j);
					JSONObject pizzaobjekti = new JSONObject();
					JSONArray taytearray = new JSONArray();
					pizzaobjekti.put("id", pizza.getId());
					pizzaobjekti.put("nimi", pizza.getNimi());
					pizzaobjekti.put("hinta", pizza.getHinta());
					/* Ei saada toistaiseksi tietokannasta täytteitä
					for (int k = 0; k < pizza.getTaytteet().size(); k++) {
						JSONObject tayteobjekti = new JSONObject();
						tayteobjekti.put("tayte", pizza.getTaytteet().get(k)
								.getNimi());
						taytearray.add(tayteobjekti);
					}
					pizzaobjekti.put("taytteet", taytearray);
					*/
					pizzalista.add(pizzaobjekti);
				}
			}

			if (tilaus.getJuomat() != null) {
				for (int j = 0; j < tilaus.getJuomat().size(); j++) {
					Juoma juoma = tilaus.getJuomat().get(j);
					JSONObject juomaobjekti = new JSONObject();
					juomaobjekti.put("id", juoma.getId());
					juomaobjekti.put("nimi", juoma.getNimi());
					juomaobjekti.put("hinta", juoma.getHinta());
					juomalista.add(juomaobjekti);
				}
			}

			objekti.put("pizzat", pizzalista);
			objekti.put("juomat", juomalista);

			objekti.put("kayttaja", kayttaja);

			jsonArray.add(objekti);
		}

		// Encoding ja printtaus
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		out.print(jsonArray);

	}

	protected void virhe(HttpServletRequest request,
			HttpServletResponse response, String virhe)
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

	protected void paasyEvatty(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request
				.getRequestDispatcher("WEB-INF/paasy-evatty.jsp");
		rd.forward(request, response);
	}

	protected void jsonVastaus(HttpServletRequest request,
			HttpServletResponse response, HashMap<String, String> vastaus)
			throws ServletException, IOException {

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

	protected void naytaSivu(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Daon alustus
		TilausDao dao = new TilausDao();

		// RequestDispatcher
		RequestDispatcher rd = request
				.getRequestDispatcher("WEB-INF/tilauslista.jsp");

		// Pizzojen ja täytteiden haku
		ArrayList<Tilaus> tilaukset = dao.haeTilaukset();

		request.setAttribute("tilaukset", tilaukset);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
