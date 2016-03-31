package asiakas;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Pizza;
import bean.Tayte;
import daot.AsiakasDao;

/**
 * Servlet implementation class PizzaServlet
 */
@WebServlet(name = "pizza", urlPatterns = { "/pizza" })
public class PizzaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PizzaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		naytaSivu(request, response);
		
	}
	
	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Daon alustus
		AsiakasDao dao = new AsiakasDao();

		// RequestDispatcher
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/pizzat.jsp");

		// Pizzojen ja täytteiden haku
		ArrayList<Pizza> pizzat = dao.haeKaikkiPizzat();
		ArrayList<Tayte> taytteet = dao.haeKaikkiTaytteet();

		request.setAttribute("pizzat", pizzat);
		request.setAttribute("taytteet", taytteet);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
	}

}
