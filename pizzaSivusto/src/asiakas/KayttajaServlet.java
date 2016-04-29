package asiakas;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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

import apuluokka.Apuri;
import bean.Kayttaja;
import bean.Pizza;
import bean.Tilaus;
import daot.KayttajaDao;

/**
 * Servlet implementation class Kayttaja
 */
@WebServlet(name = "kayttaja", urlPatterns = { "/kayttaja" })
public class KayttajaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KayttajaServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession sessio = request.getSession(false);

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			
			KayttajaDao kDao = new KayttajaDao();
			Apuri apuri = new Apuri();
			Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
			
			String lisaasuosikki = request.getParameter("lisaasuosikki");
			String poistasuosikki = request.getParameter("poistasuosikki");
			
			if (lisaasuosikki != null && apuri.validoiInt(lisaasuosikki, 11) == true) {
				HashMap<String, String> vastaus = kDao.lisaaSuosikkiPizza(String.valueOf(kayttaja.getId()), lisaasuosikki);
				if (vastaus.get("virhe") != null) {
					String virhe = vastaus.get("virhe");
					request.setAttribute("virhe", virhe);
				} else if (vastaus.get("success") != null) {
					String success = vastaus.get("success");
					request.setAttribute("success", success);
				} else {
					request.setAttribute("virhe",
							"Tietokantaa päivittäessä tapahtui tuntematon virhe.");
				}
				naytaSivu(request, response);
			}
			else if (poistasuosikki != null && apuri.validoiInt(poistasuosikki, 11) == true) {
				HashMap<String, String> vastaus = kDao.poistaSuosikkipizza(String.valueOf(kayttaja.getId()), poistasuosikki);
				if (vastaus.get("virhe") != null) {
					String virhe = vastaus.get("virhe");
					request.setAttribute("virhe", virhe);
				} else if (vastaus.get("success") != null) {
					String success = vastaus.get("success");
					request.setAttribute("success", success);
				} else {
					request.setAttribute("virhe",
							"Tietokantaa päivittäessä tapahtui tuntematon virhe.");
				}
				naytaSivu(request, response);
			}
			else {
				naytaSivu(request, response);
			}
			
		} else {
			// Jos käyttäjä ei ole kirjautunut, ohjataan login -sivulle
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
	}
	
	protected void tilaushistoriaJsonina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession sessio = request.getSession();
		
		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			
			Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
			
			// Haetaan tilaushistoria
			KayttajaDao kayttajadao = new KayttajaDao();
			ArrayList<Tilaus> tilaushistoria = kayttajadao.haeTilaushistoria(String.valueOf(kayttaja.getId()));
		
		// Tilaushistorian JSON-array
		JSONArray tilaushistoriaJson = new JSONArray();
		for (int i = 0; i < tilaushistoria.size(); i++) {
			Tilaus tilaus = tilaushistoria.get(i);
			JSONObject tilausobjekti = new JSONObject();
			
			// Parsetaan timestamp
			Timestamp ts = tilaus.getTilaushetki();
			long tilaushetki = ts.getTime();
			
			// Tungetaan objektiin jne
			tilausobjekti.put("tilaus_id", tilaus.getTilausid());
			tilausobjekti.put("tilaushetki", tilaushetki);
			tilausobjekti.put("kokonaishinta", tilaus.getKokonaishinta());
			tilaushistoriaJson.add(tilausobjekti);
		}
		
		// Encoding ja printtaus
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		out.print(tilaushistoriaJson);
		
		}
		else {
			naytaSivu(request, response);
		}
		
		
	}
	
	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessio = request.getSession(false);
		
		KayttajaDao kDao = new KayttajaDao();
		Apuri apuri = new Apuri();
		Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
		
		ArrayList<Pizza> suosikkipizzat = kDao.haeSuosikkiPizzat(String.valueOf(kayttaja.getId()));
		
		request.setAttribute("suosikkipizzat", suosikkipizzat);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/profiili.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		String json = request.getParameter("json");
		
		if (action != null && action.equals("haetilaukset") && json != null && json.equals("true")) {
			tilaushistoriaJsonina(request, response);
		}
		else {
		doGet(request, response);
		}
	}

}
