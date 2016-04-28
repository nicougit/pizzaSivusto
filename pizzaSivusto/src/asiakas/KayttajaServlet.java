package asiakas;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Kayttaja;
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
			
			Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
			
			// Haetaan tilaushistoria
			KayttajaDao kayttajadao = new KayttajaDao();
			ArrayList<Tilaus> tilaushistoria = kayttajadao.haeTilaushistoria(String.valueOf(kayttaja.getId()));
			
			// Tilaushistoria attribuutiksi ja sivun näyttö
			request.setAttribute("tilaushistoria", tilaushistoria);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/profiili.jsp");
			rd.forward(request, response);
			
		} else {
			// Jos käyttäjä ei ole kirjautunut, ohjataan login -sivulle
			response.sendRedirect(request.getContextPath() + "/login");
		}
		
	}
	
	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/profiili.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
