package asiakas;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Kayttaja;
import daot.AsiakasDao;
import daot.KayttajaDao;

@WebServlet(name = "tilaus", urlPatterns = { "/tilaus" })

public class TilausServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
	
	    public TilausServlet() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	    
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			HttpSession sessio = request.getSession(false);
					
			if (sessio != null && sessio.getAttribute("kayttaja") != null) {
				// Päivitetään käyttäjän osoitteet, siltä varalta että on lisätty uusia sisäänkirjautumisen jälkeen
				KayttajaDao kayttajadao = new KayttajaDao();
				
				try {
					// Haetaan käyttäjä sessiosta
					Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
					
					// Päivitetään osoitelista
					kayttaja.setOsoitteet(kayttajadao.haeOsoitteet(String.valueOf(kayttaja.getId())));
					
					// Korvataan session vanha käyttäjä uudella, jossa päivitetyt osoitteet!
					sessio.setAttribute("kayttaja", kayttaja);
				} catch (Exception ex) {
					System.out.println("Käyttäjää castatessa virhe tilausservletissä!");
				}
				
				// Ohjaus tilaussivulle
				String rdPath = "WEB-INF/tilaus.jsp";
				naytaSivu(request, response, rdPath);
			} else {
				// Jos käyttäjä ei ole kirjautunut, ohjataan login -sivulle				
				String rdPath = "WEB-INF/login.jsp";
				naytaSivu(request, response, rdPath);
			}
					
		}
		
		protected void naytaSivu(HttpServletRequest request, HttpServletResponse response, String rdPath)
				throws ServletException, IOException {

			RequestDispatcher rd = request.getRequestDispatcher(rdPath);
			rd.forward(request, response);

		}
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			
			doGet(request, response);
		}
		
}
