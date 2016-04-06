package asiakas;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daot.AsiakasDao;
import bean.Ostos;
import bean.Pizza;

// Tämä tiedosto on vielä varhaisessa betavaiheessa! t:Pasi

@WebServlet(name = "ostoskori", urlPatterns = { "/ostoskori" })
public class Ostoskori extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Ostoskori() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// Katsotaan oikeudet
		HttpSession sessio = request.getSession(true);

		ArrayList<Ostos> ostoskori = (ArrayList<Ostos>) sessio
				.getAttribute("ostoskori");

		if (ostoskori == null) {
			ostoskori = new ArrayList<Ostos>();
		}
		
		PrintWriter out = response.getWriter();

		String lisaa = request.getParameter("lisaa");
		String id = request.getParameter("id");
		String tyyppi = request.getParameter("tyyppi");

		if (lisaa != null) {

			if (id != null && id != null && tyyppi != null) {
				int idint = Integer.parseInt(id);
				Ostos ostos = new Ostos(idint, tyyppi);
				ostoskori.add(ostos);
				System.out.println("Lisätty käyttäjän ostoskoriin tuote id: "
						+ ostos.getId() + " jonka tyyppi on "
						+ ostos.getTyyppi());
			}

			sessio.setAttribute("ostoskori", ostoskori);

		}
		
		if (ostoskori != null) {
			ArrayList<Pizza> pizzat = haeOstoskorinPizzat(ostoskori);
			
			for (int i = 0; i < ostoskori.size(); i++) {
				out.print(ostoskori.get(i).toString() + "\n");
			}
			
			out.print("\n\n\nTarkemmilla tiedoilla:\n\n\n");
			
			for (int i = 0; i < pizzat.size(); i++) {
				out.print("#" + pizzat.get(i).getId() + ", " + pizzat.get(i).getNimi() + " - Hinta: " + pizzat.get(i).getHinta() + "\n");
			}
		}
		
	}
	
	protected ArrayList<Pizza> haeOstoskorinPizzat(ArrayList<Ostos> ostoskori) {
		ArrayList<Pizza> pizzat= new ArrayList<>();
		AsiakasDao dao = new AsiakasDao();
	
		
		pizzat = dao.haeOstoskorinPizzat(ostoskori);
		
		return pizzat;
	}
}