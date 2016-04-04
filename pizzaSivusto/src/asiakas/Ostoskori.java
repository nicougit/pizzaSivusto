package asiakas;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Tämä tiedosto on vielä varhaisessa betavaiheessa! t:Pasi

@WebServlet(name = "ostoskori", urlPatterns = { "/ostoskori" })
public class Ostoskori extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Ostoskori() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Keksin lisäys
		if (request.getParameter("lisaa") != null) {

			int tuote = Integer.parseInt(request.getParameter("item_id"));
			int kuvaus = Integer.parseInt(request.getParameter("item_quantity"));
	
			Cookie[] items = request.getCookies();
			
			if (items != null) {
				
				for (int i = 0; i < items.length; i++) {
					String tuoteString = Integer.toString(tuote);
					if (items[i].getName().equals(tuoteString)) {
					System.out.println("Tällainen löytyi jo! Vanha korvataan uudella.");
					kuvaus = kuvaus+1;
					}
				}
										
				Cookie item = new Cookie(Integer.toString(tuote), Integer.toString(kuvaus));
				
				item.setMaxAge(60);
				// Cookien ikä 60 sekuntia testimielessä
				
				response.addCookie(item);
				
				System.out.println("Keksi " + tuote + " lisätty kuvauksella "+kuvaus);
				
				request.getRequestDispatcher("ostoskori.jsp").forward(request,
						response);
				
			}
		}

		// Keksien haku
		if (request.getParameter("hae") != null) {

			Cookie items[] = request.getCookies();

			for (int i = 0; i < items.length; i++) {

				// SessioID tallentuu myös keksiksi joten getName ja getValue näyttää sessiokeksin tiedot
				// System.out.println(items[i].getName()+" "+items[i].getValue());
				
				// Eli tarvitaan täsmäytin
				if (items[i].getName().matches("\\d{1,3}")) {
					System.out.println(items[i].getName()+" "+items[i].getValue());
				}
				
			}
			request.getRequestDispatcher("ostoskori.jsp").forward(request,
					response);
		}

	}

	public void PoistaKeksi(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Keksin poistoa ei vielä toteutettu mutta helppo toteuttaa...");
	}

}