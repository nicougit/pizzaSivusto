package login;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apuluokka.Apuri;
import kayttajaDao.KayttajaDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Haetaan lista käyttäjistä kantayhteyden testausta varten
		KayttajaDAO dao = new KayttajaDAO();
		ArrayList<KayttajaLista> lista = dao.haeKayttajat();
		
		request.setAttribute("kayttajat", lista);
		
		// Request dispatcher
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Haetaan attribuutit
		String kayttajanimi = request.getParameter("kayttajanimi");
		String salasana = request.getParameter("salasana");
		
		// Suoritetaan, jos kayttajanimi ja salasana on annettu
		if (kayttajanimi != null && salasana != null ) {
			System.out.println("Kirjautumisyritys - user: " + kayttajanimi + " - pass: " + salasana);
			
			// Validoidaan käyttäjänimi (estetään ainakin injektiot)
			Apuri apuri = new Apuri();
			Boolean validity = apuri.validoiEmail(kayttajanimi);
			System.out.println("Email validity: " + validity);
			
			// Jos virheellinen email, annetaan errori
			if (validity == false) {
				response.sendRedirect("/pizzaSivusto/login?error=true");
			}
			else {
				
			}
			
			response.sendRedirect("/pizzaSivusto/login");
		}
		else {
			response.sendRedirect("/pizzaSivusto/login?error=true");
		}
		
	}

}
