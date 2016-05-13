package fi.softala.pizzeria.hallinta;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.softala.pizzeria.apuluokka.Apuri;
import fi.softala.pizzeria.bean.Aukioloaika;
import fi.softala.pizzeria.bean.Kayttaja;
import fi.softala.pizzeria.bean.Tilaus;
import fi.softala.pizzeria.daot.HallintaDao;
import fi.softala.pizzeria.daot.TilausDao;

/**
 * Servlet implementation class AukioloajatServlet
 */
@WebServlet("/aukioloajat")
public class AukioloajatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AukioloajatServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String toiminto = request.getParameter("action");

		System.out.println(toiminto);

		if (toiminto != null){
			System.out.println("Action-parametri oli:" + toiminto);
			if(toiminto.equals("haeAukiolot")) {
			naytaEtuSivu(request, response);
			
		}
		} else if(toiminto == null) {

			HttpSession sessio = request.getSession(false);

			if (sessio != null && sessio.getAttribute("kayttaja") != null) {
				Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
				if (kayttaja.getTyyppi().equals("admin") || kayttaja.getTyyppi().equals("staff")) {
					naytaSivu(request, response);
				}
			} else {
				paasyEvatty(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		paivitaAukioloajat(request, response);
	}

	public void paivitaAukioloajat(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HallintaDao dao = new HallintaDao();
		Apuri apuri = new Apuri();
		
		String arkisinavaaminen = request.getParameter("Arkisinavaaminen");
		String arkisinsulkeminen = request.getParameter("Arkisinsulkeminen");
		
		String laAvaaminen = request.getParameter("Lauantaisinavaaminen");
		String laSulkeminen = request.getParameter("Arkisinsulkeminen");
		
		String suAvaaminen = request.getParameter("Sunnuntaisinavaaminen");
		String suSulkeminen = request.getParameter("Sunnuntaisinsulkeminen");

		if ((arkisinavaaminen != null && apuri.validoiInt(arkisinavaaminen, 2) == true) && 
				(arkisinsulkeminen != null && apuri.validoiInt(arkisinsulkeminen, 2) == true) && 
				(laAvaaminen != null && apuri.validoiInt(laAvaaminen, 2) == true) && 
				(laSulkeminen != null && apuri.validoiInt(laSulkeminen, 2) == true) && 
				(suAvaaminen != null && apuri.validoiInt(suAvaaminen, 2) == true) && 
				(suSulkeminen != null && apuri.validoiInt(suSulkeminen, 2) == true)) {
			
			Aukioloaika arkiAukiolo = new Aukioloaika();
			arkiAukiolo.setAloitusaika(arkisinavaaminen);
			arkiAukiolo.setSulkemisaika(arkisinsulkeminen);
			arkiAukiolo.setPaiva("Arkisin");

			Aukioloaika laAukiolo = new Aukioloaika();
			laAukiolo.setAloitusaika(laAvaaminen);
			laAukiolo.setSulkemisaika(laSulkeminen);
			laAukiolo.setPaiva("Lauantaisin");
			
			Aukioloaika suAukiolo = new Aukioloaika();
			suAukiolo.setAloitusaika(suAvaaminen);
			suAukiolo.setSulkemisaika(suSulkeminen);
			suAukiolo.setPaiva("Sunnuntaisin");
			
			ArrayList<Aukioloaika> aukioloajat = new ArrayList<>();
			aukioloajat.add(arkiAukiolo);
			aukioloajat.add(laAukiolo);
			aukioloajat.add(suAukiolo);
			
			HashMap<String, String> vastaus = dao.paivitaAukioloajat(aukioloajat);

			if (vastaus.get("virhe") != null) {
				String virhe = vastaus.get("virhe");
				request.setAttribute("virhe", virhe);
			} else if (vastaus.get("success") != null) {
				String success = vastaus.get("success");
				request.setAttribute("success", success);
			} else {
				request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
			}

			System.out.println("Aukioloajoiksi on yritetty päivittää:" + "\n -Arkisin: klo " + arkiAukiolo.getAloitusaika()
					+ "-" + arkiAukiolo.getSulkemisaika() + "\n -Lauantaisin: klo " + laAukiolo.getAloitusaika() + "-"
					+ laAukiolo.getSulkemisaika() + "\n -Sunnuntaisin: klo " + suAukiolo.getAloitusaika() + "-"
					+ suAukiolo.getSulkemisaika());

			doGet(request, response);

			
		}
		else {
			String virhe = "Päivämäärä väärässä muodossa";
			request.setAttribute("virhe", virhe);
			doGet(request, response);
		}

	}

	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Daon alustus
		HallintaDao dao = new HallintaDao();

		ArrayList<Aukioloaika> edellisetAukioloajat = dao.haeAukioloajat();

		/* custom-selectit jsp-sivulle */
		String[] selectit = { "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "00", "01", "02", "03", "04", "05" };
		request.setAttribute("selectit", selectit);
		request.setAttribute("aukiolot", edellisetAukioloajat);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/aukioloajat.jsp");
		rd.forward(request, response);
	}

	protected void naytaEtuSivu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Daon alustus
		HallintaDao dao = new HallintaDao();

		ArrayList<Aukioloaika> Aukioloajat = dao.haeAukioloajat();

		request.setAttribute("aukiolot", Aukioloajat);
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	protected void paasyEvatty(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/paasy-evatty.jsp");
		rd.forward(request, response);
	}

}
