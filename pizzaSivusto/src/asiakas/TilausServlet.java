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

import bean.Juoma;
import bean.Pizza;
import daot.AsiakasDao;

@WebServlet(name = "tilaus", urlPatterns = { "/tilaus" })

public class TilausServlet extends HttpServlet {
		private static final long serialVersionUID = 1L;
	
	    public TilausServlet() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	    
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			HttpSession sessio = request.getSession(true);
					
			if (sessio != null && sessio.getAttribute("kayttaja") != null) {
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
