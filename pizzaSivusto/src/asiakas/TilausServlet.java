package asiakas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import apuluokka.Apuri;
import bean.Juoma;
import bean.Kayttaja;
import bean.Osoite;
import bean.Pizza;
import bean.Tilaus;
import daot.AsiakasDao;
import daot.HallintaDao;
import daot.KayttajaDao;

@WebServlet(name = "tilaus", urlPatterns = { "/tilaus" })

public class TilausServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TilausServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessio = request.getSession(false);
		
		System.out.println("Tultiin dogettiin");

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {

			// Haetaan käyttäjän ostoskori
			HashMap<String, ArrayList> ostoskori = haeOstoskori(request, response);
			ArrayList<Pizza> ostoskoriPizzat = ostoskori.get("pizzat");
			ArrayList<Juoma> ostoskoriJuomat = ostoskori.get("juomat");

			if ((ostoskoriPizzat.size() + ostoskoriJuomat.size()) < 1) {
				// Jos käyttäjän ostoskori on tyhjä, ohjataan ostoskoriin
				// TODO: Pitää miettiä, miten saa redirectin kanssa välitettyä
				// errorviestin
				response.sendRedirect(request.getContextPath() + "/ostoskori");
			} else {

				if (request.getParameter("poista-osoite") != null) {
					poistaOsoite(request, response);
					System.out.println("osoitteen poisto");
				} else {
					naytaSivu(request, response);
				}
			}
		} else {
			// Jos käyttäjä ei ole kirjautunut, ohjataan login -sivulle ja
			// sieltä tilaukseen
			response.sendRedirect(request.getContextPath() + "/login?tilaukseen=true");
		}

	}

	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan käyttäjän ostoskori
		HashMap<String, ArrayList> ostoskori = haeOstoskori(request, response);
		ArrayList<Pizza> ostoskoriPizzat = ostoskori.get("pizzat");
		ArrayList<Juoma> ostoskoriJuomat = ostoskori.get("juomat");

		if ((ostoskoriPizzat.size() + ostoskoriJuomat.size()) < 1) {
			// Jos käyttäjän ostoskori on tyhjä, ohjataan ostoskoriin
			// TODO: Pitää miettiä, miten saa redirectin kanssa välitettyä
			// errorviestin
			response.sendRedirect(request.getContextPath() + "/ostoskori");
		} else {
			request.setAttribute("ostoskoriPizzat", ostoskoriPizzat);
			request.setAttribute("ostoskoriJuomat", ostoskoriJuomat);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/tilaus.jsp");
			rd.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sessio = request.getSession(false);

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			
			System.out.println("Tultiin dopostiin");

			String action = request.getParameter("action");
			if (action != null) {
				System.out.println("Action: " + action);
			}
			else {
				System.out.println("Ei actionii");
			}
			
			if (action != null && action.equals("lisaaosoite")) {
				lisaaOsoite(request, response);
			} else if (action != null && action.equals("tilausvahvistukseen")) {
				siirryTilausvahvistukseen(request, response);
			}
			else if (action != null && action.equals("lahetatilaus")) {
				lahetaTilaus(request, response);
			}
			else {
				doGet(request, response);
			}
		} else {
			// Jos käyttäjä ei ole kirjautunut, ohjataan login -sivulle
			response.sendRedirect(request.getContextPath() + "/login");
		}

	}
	
	protected void lahetaTilaus(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession sessio = request.getSession(false);
		Tilaus tilaus = new Tilaus();
		
		if (sessio.getAttribute("tilaus") != null) {
		tilaus = (Tilaus) sessio.getAttribute("tilaus");
		
		AsiakasDao dao = new AsiakasDao();
		
		HashMap<String, String> vastaus = dao.lisaaTilaus(tilaus);
		
		if (vastaus.get("virhe") != null) {
			String virhe = vastaus.get("virhe");
			request.setAttribute("virhe", virhe);
		} else if (vastaus.get("success") != null) {
			String success = vastaus.get("success");
			request.setAttribute("success", success);
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/tilaustiedot.jsp");
			rd.forward(request, response);
		} else {
			request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
		}
		
		}
		else {
			String virhe = "Sessiosta ei löytynyt tilausta";
			virhe(request, response, virhe);
		}
		
	}

	protected void siirryTilausvahvistukseen(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessio = request.getSession(false);
		Apuri apuri = new Apuri();
		
		System.out.println("Käyttäjä haluaa tehdä tilauksen");

		// Haetaan käyttäjän ostoskori
		HashMap<String, ArrayList> ostoskori = haeOstoskori(request, response);
		ArrayList<Pizza> ostoskoriPizzat = ostoskori.get("pizzat");
		ArrayList<Juoma> ostoskoriJuomat = ostoskori.get("juomat");

		// Haetaan käyttäjä
		Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");

		// Haetaan parametrit
		String tilaustapa = request.getParameter("tilaustapa");
		String maksutapa = request.getParameter("maksutapa");
		String lisatiedot = request.getParameter("lisatiedot");
		String osoitevalinta = request.getParameter("osoitevalinta");
		String[] pizzatiedot = request.getParameterValues("pizzatieto");

		/*
		 * Tilaustapa: 0 = kuljetus, 1 = nouto, 2 = ravintolassa Maksutapa: 0 =
		 * käteinen, 1 = luottokortti, 2 = verkkomaksu
		 */

		if (tilaustapa != null && maksutapa != null && lisatiedot != null && osoitevalinta != null) {

			if (apuri.validoiInt(tilaustapa, 11) == true && Integer.parseInt(tilaustapa) >= 0
					&& Integer.parseInt(tilaustapa) < 3) {
				if (apuri.validoiInt(maksutapa, 11) == true && Integer.parseInt(maksutapa) >= 0
						&& Integer.parseInt(maksutapa) < 3) {
					// TODO: Lisätietojen validointi
					if (lisatiedot != null) {
						if (apuri.validoiInt(osoitevalinta, 11) == true) {
							// Osoitteen tarkempi validointi ja haku
							ArrayList<Osoite> osoitteet = kayttaja.getOsoitteet();
							Osoite osoite = null;
							for (int i = 0; i < osoitteet.size(); i++) {
								if (osoitteet.get(i).getOsoiteid() == Integer.parseInt(osoitevalinta)) {
									osoite = osoitteet.get(i);
									i = osoitteet.size();
								}
							}
							if (osoite != null) {
								Boolean pizzatiedotOk = true;
								if (pizzatiedot != null) {
									for (int i = 0; i < pizzatiedot.length; i++) {
										boolean validointi = apuri.validoiPizzatieto(pizzatiedot[i]);
										if (!validointi) {
											System.out.println("Pizzatieto " + (i + 1) + " virheellinen!");
											System.out.println(pizzatiedot[i]);
											i = pizzatiedot.length;
											pizzatiedotOk = false;
										}
									}
								}
								if (pizzatiedotOk) {

									// Setataan eka pizzatiedoiks falset
									// Ilman tätä oli probleemia
									for (int i = 0; i < ostoskoriPizzat.size(); i++) {
										ostoskoriPizzat.get(i).setLisatiedot(new ArrayList<>());
									}
									// Pizzatietojen parsiminen ja liittäminen
									// pizzoille
									if (pizzatiedot != null) {
										for (int i = 0; i < pizzatiedot.length; i++) {
											String pizzaindex = pizzatiedot[i].substring(0,
													pizzatiedot[i].indexOf("-"));
											String pizzatieto = pizzatiedot[i]
													.substring(pizzatiedot[i].indexOf("-") + 1);
											int pizzaindeksi = Integer.parseInt(pizzaindex);
											if (pizzaindeksi <= ostoskoriPizzat.size()) {
												if (pizzatieto.equals("oregano")) {
													ostoskoriPizzat.get(pizzaindeksi).getLisatiedot().add("Oregano");
												} else if (pizzatieto.equals("valkosipuli")) {
													ostoskoriPizzat.get(pizzaindeksi).getLisatiedot().add("Valkosipuli");
												} else if (pizzatieto.equals("gluteeniton")) {
													ostoskoriPizzat.get(pizzaindeksi).getLisatiedot().add("Gluteeniton");
												} else if (pizzatieto.equals("vl")) {
													ostoskoriPizzat.get(pizzaindeksi).getLisatiedot().add("Vähälaktoosinen");
												} else {
													System.out.println("Virhe - tuntematon pizzatieto indeksissä "
															+ pizzaindex + " : " + pizzatieto);
												}
											}

										}
									}
									
									System.out.println("Kaikki tilauksen tiedot OK");

									// Tilaustavan parsiminen
									if (tilaustapa.equals("0")) {
										tilaustapa = "Kotiinkuljetus";
									} else if (tilaustapa.equals("1")) {
										tilaustapa = "Nouto";
									} else if (tilaustapa.equals("2")) {
										tilaustapa = "Ravintolassa";
									}

									// Maksutavan parsiminen
									if (maksutapa.equals("0")) {
										maksutapa = "Käteinen";
									} else if (maksutapa.equals("1")) {
										maksutapa = "Luottokortti";
									} else if (maksutapa.equals("2")) {
										maksutapa = "Verkkomaksu";
									}

									// Lasketaan yhteishinta
									double kokonaishinta = 0;
									for (int i = 0; i < ostoskoriPizzat.size(); i++) {
										kokonaishinta += ostoskoriPizzat.get(i).getHinta();
									}
									for (int i = 0; i < ostoskoriJuomat.size(); i++) {
										kokonaishinta += ostoskoriJuomat.get(i).getHinta();
									}
									
									// Kokonaishintaan kuljetusmaksu, staattinen 5 eur
									if (tilaustapa.equals("Kotiinkuljetus")) {
										kokonaishinta += 5;
									}
									
									// Luodaan tilausolio
									Tilaus tilaus = new Tilaus();
									tilaus.setKayttaja(kayttaja);
									tilaus.setToimitustapa(tilaustapa);
									tilaus.setMaksutapa(maksutapa);
									tilaus.setPizzat(ostoskoriPizzat);
									tilaus.setJuomat(ostoskoriJuomat);
									tilaus.setKokonaishinta(kokonaishinta);
									if (!lisatiedot.equals("")) {
									tilaus.setLisatiedot(lisatiedot);
									}
									if (tilaustapa.equals("Kotiinkuljetus")) {
										tilaus.setOsoite(osoite);
									}
									
									// Tilausvahvistuksen näyttö
									naytaTilausvahvistus(request, response, tilaus);
									
								} else {
									String virhe = "Pizzojen lisätiedoissa oli virheitä";
									virhe(request, response, virhe);
								}
							} else {
								String virhe = "Osoitetta ei löydy käyttäjältä";
								virhe(request, response, virhe);
							}

						} else {
							String virhe = "Osoitevalinta virheellinen";
							virhe(request, response, virhe);
						}
					} else {
						// Eclipse sanoo et dead codee, koska validointia ei vielä tehty
						String virhe = "Lisätiedoissa laittomia merkkejä";
						virhe(request, response, virhe);
					}

				} else {
					String virhe = "Virheellinen maksutapa";
					virhe(request, response, virhe);
				}

			} else {
				String virhe = "Virheellinen tilaustapa";
				virhe(request, response, virhe);
			}

		}

	}
	
	protected void naytaTilausvahvistus(HttpServletRequest request, HttpServletResponse response, Tilaus tilaus)
			throws ServletException, IOException {
		HttpSession sessio = request.getSession(false);
		sessio.setAttribute("tilaus", tilaus);
		request.setAttribute("tilaus", tilaus);
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/tilausvahvistus.jsp");
		rd.forward(request, response);
	}

	protected void lisaaOsoite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lahiosoite = request.getParameter("lahiosoite");
		String postinumero = request.getParameter("postinumero");
		String postitoimipaikka = request.getParameter("postitoimipaikka");
		String kayttajaid = null;
		Kayttaja kayttaja = null;

		HttpSession sessio = request.getSession(false);

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			try {
				kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
				kayttajaid = String.valueOf(kayttaja.getId());
			} catch (Exception ex) {
				System.out.println("Virhe käyttäjää castatessa");
			}
		}

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (lahiosoite != null && postinumero != null && postitoimipaikka != null && kayttajaid != null) {
			if (apuri.validoiString(lahiosoite, " -", 50) == true && apuri.validoiPostinro(postinumero) == true
					&& apuri.validoiString(postitoimipaikka, "-", 50)) {

				KayttajaDao dao = new KayttajaDao();

				// Katsotaan, onnistuuko päivitys
				HashMap<String, String> vastaus = dao.lisaaOsoite(kayttajaid, lahiosoite, postinumero,
						postitoimipaikka);
				if (vastaus.get("virhe") != null) {
					String virhe = vastaus.get("virhe");
					request.setAttribute("virhe", virhe);
				} else if (vastaus.get("success") != null) {
					String success = vastaus.get("success");
					request.setAttribute("success", success);

					// Päivitetään osoitelista
					kayttaja.setOsoitteet(dao.haeOsoitteet(String.valueOf(kayttaja.getId())));
					// Korvataan session vanha käyttäjä uudella, jossa
					// päivitetyt
					// osoitteet!
					sessio.setAttribute("kayttaja", kayttaja);

				} else {
					request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
				}

				doGet(request, response);
			} else {
				String virhe = "Osoitetiedoissa oli virheitä";
				virhe(request, response, virhe);
			}

		} else {
			String virhe = "Kaikkia osoitetietoja ei syötetty";
			virhe(request, response, virhe);
		}
	}

	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		System.out.println(virhe);
		request.setAttribute("virhe", virhe);
		naytaSivu(request, response);
	}

	public void poistaOsoite(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession sessio = request.getSession(false);
		Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");

		String poistaOsoite = request.getParameter("poista-osoite");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (apuri.validoiInt(poistaOsoite, 11) == false) {
			String virhe = "Poistettavan osoitteen ID ei ole validi!";
			virhe(request, response, virhe);
		} else {
			System.out.println("Yritetään poistaa osoite ID: " + poistaOsoite);
			KayttajaDao dao = new KayttajaDao();

			String kayttajaid = String.valueOf(kayttaja.getId());
			HashMap<String, String> vastaus = dao.poistaOsoite(kayttajaid, poistaOsoite);

			if (vastaus.get("virhe") != null) {
				String virhe = vastaus.get("virhe");
				request.setAttribute("virhe", virhe);
				naytaSivu(request, response);
			} else if (vastaus.get("success") != null) {
				String success = vastaus.get("success");
				request.setAttribute("success", success);

				try {

					// Päivitetään osoitelista
					kayttaja.setOsoitteet(dao.haeOsoitteet(String.valueOf(kayttaja.getId())));
					// Korvataan session vanha käyttäjä uudella, jossa
					// päivitetyt
					// osoitteet!
					sessio.setAttribute("kayttaja", kayttaja);
				} catch (Exception ex) {
					System.out.println("Käyttäjää castatessa virhe tilausservletissä!");
				}

				naytaSivu(request, response);
			} else {
				request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
				naytaSivu(request, response);

			}

		}

	}

	// Hakee ostoskorin sisällön, jos sisältöä ei ole, luo ostoskorin
	// Huono käytäntö, koska copypastettu sama koodi Ostoskori-servletistä
	// Katotaan, jos tekis tälle luokan, jotta vois kutsua samaa koodia
	// molemmista servleteistä
	protected HashMap<String, ArrayList> haeOstoskori(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Sessionhallintaa
		HttpSession sessio = request.getSession(true);

		HashMap<String, ArrayList> ostoskori = null;
		ArrayList<Pizza> ostoskoriPizzat = new ArrayList<>();
		ArrayList<Juoma> ostoskoriJuomat = new ArrayList<>();
		try {
			ostoskori = (HashMap<String, ArrayList>) sessio.getAttribute("ostoskori");
		} catch (Exception ex) {
			System.out.println("Virhe ostoskoria hakiessa " + ex);
		}

		if (ostoskori == null) {
			ostoskori = new HashMap<>();
		} else {
			try {
				if (ostoskori.get("pizzat") != null && ostoskori.get("juomat") != null) {
					ostoskoriPizzat = ostoskori.get("pizzat");
					ostoskoriJuomat = ostoskori.get("juomat");
				} else {
					ostoskoriPizzat = new ArrayList<>();
					ostoskoriJuomat = new ArrayList<>();
				}

			} catch (Exception ex) {
				System.out.println("Virhe pizzojen ja juomien noutamisessa ostoskorista " + ex);
			}
		}

		ostoskori.put("pizzat", ostoskoriPizzat);
		ostoskori.put("juomat", ostoskoriJuomat);

		return ostoskori;
	}

}
