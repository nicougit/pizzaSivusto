package fi.softala.pizzeria.hallinta;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fi.softala.pizzeria.bean.Kayttaja;
import fi.softala.pizzeria.daot.KayttajaDao;
import fi.softala.pizzeria.login.KayttajaLista;

@WebServlet(name = "kayttajat", urlPatterns = { "/kayttajat" })
public class KayttajaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public KayttajaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {

			Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
			if (kayttaja.getTyyppi().equals("admin") || kayttaja.getTyyppi().equals("staff")) {
				String rdPath = "WEB-INF/kayttajat.jsp";
				naytaSivu(request, response, rdPath);		
			} else {
				String rdPath = "WEB-INF/login.jsp";
				naytaSivu(request, response, rdPath);
			}
		} else {
			String rdPath = "WEB-INF/login.jsp";
			naytaSivu(request, response, rdPath);
		}

	}

	protected void naytaSivu(HttpServletRequest request,
			HttpServletResponse response, String rdPath)
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
}
