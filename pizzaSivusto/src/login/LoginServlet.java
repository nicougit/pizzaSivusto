package login;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import apuluokka.Apuri;
import bean.Kayttaja;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Katsotaan mikä toiminto (tällä hetkellä vain Kirjaudu)
		String action = request.getParameter("action");
		if (action != null && action.equals("Kirjaudu")) {
			kirjaudu(request, response);
		}
		else {
			response.sendRedirect("/pizzaSivusto/login");
		}

	}
	
	public void kirjaudu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Haetaan parametrit
		String kayttajanimi = request.getParameter("kayttajanimi");
		String salasana = request.getParameter("salasana");

		// Katsotaan onko parametreja olemassa
		if (kayttajanimi != null && salasana != null) {
			System.out.println("Kirjautumisyritys - user: " + kayttajanimi + " - pass: " + salasana);

			// Validoidaan käyttäjänimi (estetään ainakin injektiot)
			Apuri apuri = new Apuri();
			Boolean validity = apuri.validoiEmail(kayttajanimi);
			System.out.println("Email validity: " + validity);

			// Jos virheellinen email, annetaan errori
			if (validity == false) {
				System.out.println("Käyttäjätunnus annettu väärässä muodossa, redirectataan login sivulle");
				response.sendRedirect("/pizzaSivusto/login?error=true");
			} else {
				
				KayttajaDAO kayttajaDao = new KayttajaDAO();
				Kayttaja kayttaja = kayttajaDao.kirjaudu(kayttajanimi, salasana);
				System.out.println(kayttaja.toString());
				if (kayttaja.getTunnus() != null) {
					HttpSession sessio = request.getSession(true);
					sessio.setAttribute("kayttaja", kayttaja);
					
					request.setAttribute("kayttaja", kayttaja);
					
					RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/loggedin.jsp");
					rd.forward(request, response);
				}
				else {
					System.out.println("Käyttäjätunnus ja salasana ei täsmää, redirectataan login sivulle");
					response.sendRedirect("/pizzaSivusto/login?error=true");
				}
							
			}

		}
		else {
			// Jos logineita ei ole määritetty, annetaan errori
			System.out.println("Login yritys ilman useria ja/tai passua.");
			response.sendRedirect("/pizzaSivusto/login?error=true");
		}
	}
	
}
