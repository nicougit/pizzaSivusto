package hallinta;

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
import apuluokka.DeployAsetukset;
import bean.Kayttaja;
import bean.Pizza;
import bean.Tayte;
import daot.HallintaDao;

/**
 * Servlet implementation class HallintaServlet
 */
@WebServlet(name = "hallinta", urlPatterns = { "/hallinta" })
public class HallintaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Määritetään sivuston path linkkejä ja redirectejä varten
	// Määritys "/reptilemafia" koulun protoservua varten
	// Eclipsessä ajettaessa "/pizzaSivusto"
	DeployAsetukset asetukset = new DeployAsetukset();
	private String sivustopath = asetukset.getPathi();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HallintaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// Katsotaan oikeudet
		HttpSession sessio = request.getSession(false);

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
			if (kayttaja.getTyyppi().equals("admin") || kayttaja.getTyyppi().equals("staff")) {
				// Asetetaan sivun path
				request.setAttribute("pathi", sivustopath);

				// Tarkastetaan parametrit
				String pizzaEdit = request.getParameter("pizza-edit");
				String tayteEdit = request.getParameter("tayte-edit");
				String pizzaPoista = request.getParameter("pizza-poista");
				String pizzaPalauta = request.getParameter("pizza-palauta");

				// Apuri validointiin
				Apuri apuri = new Apuri();

				// Daon alustus
				HallintaDao dao = new HallintaDao();

				// RequestDispatcher
				RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/hallinta.jsp");

				// Siirrytään pizzan muokkaukseen, jos ID on määritetty ja OK
				if (pizzaEdit != null && apuri.validoiInt(pizzaEdit, 11) == true) {

					System.out.println("Pizzaa '" + pizzaEdit + "' halutaan muokata");

					Pizza pizza = dao.haePizza(pizzaEdit);

					if (pizza.getNimi() != null) {
						ArrayList<Tayte> taytteet = dao.haeKaikkiTaytteet();
						request.setAttribute("pizza", pizza);
						request.setAttribute("taytteet", taytteet);

						// Forwardataan pizzan muokkaukseen
						rd = request.getRequestDispatcher("WEB-INF/pizza-muokkaa.jsp");
						rd.forward(request, response);

					} else {
						String virhe = "Muokattavaksi valittua pizzaa ei ole tietokannassa.";
						System.out.println(virhe);
						request.setAttribute("virhe", virhe);
					}

				} else if (tayteEdit != null && apuri.validoiInt(tayteEdit, 11) == true) {

					System.out.println("Täytettä '" + tayteEdit + "' halutaan muokata");

					Tayte tayte = dao.haeTayte(tayteEdit);

					if (tayte.getNimi() != null) {
						ArrayList<Pizza> pizzat = dao.haeKaikkiPizzat(1, tayteEdit);
						request.setAttribute("tayte", tayte);
						request.setAttribute("pizzat", pizzat);

						// Forwardataan pizzan muokkaukseen
						rd = request.getRequestDispatcher("WEB-INF/tayte-muokkaa.jsp");
						rd.forward(request, response);

					} else {
						String virhe = "Muokattavaksi valittua täytettä ei ole tietokannassa.";
						virhe(request, response, virhe);
					}
				} else if (pizzaPoista != null && apuri.validoiInt(pizzaPoista, 11) == true) {
					poistaPizza(request, response);
				} else if (pizzaPalauta != null && apuri.validoiInt(pizzaPalauta, 11) == true) {
					palautaPizza(request, response);
				} else {
					naytaSivu(request, response);
				}
			} else {
				paasyEvatty(request, response);
			}
		} else {
			paasyEvatty(request, response);
		}

	}

	protected void naytaSivu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Daon alustus
		HallintaDao dao = new HallintaDao();

		// RequestDispatcher
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/hallinta.jsp");

		// Pizzojen ja täytteiden haku
		ArrayList<Pizza> pizzat = dao.haeKaikkiPizzat(0, "");
		ArrayList<Tayte> taytteet = dao.haeKaikkiTaytteet();

		request.setAttribute("pizzat", pizzat);
		request.setAttribute("taytteet", taytteet);

		rd.forward(request, response);
	}

	protected void paasyEvatty(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/paasy-evatty.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Oleellinen jos halutaan siirrellä ääkkösiä POST-metodilla.
		// Pitää selvittää, saako tän toteutettua yksinkertaisemmin jotenkin
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		if (action != null && action.equals("lisaapizza")) {
			lisaaPizza(request, response);
		} else if (action != null && action.equals("paivitapizza")) {
			paivitaPizza(request, response);
		} else if (action != null && action.equals("lisaatayte")) {
			lisaaTayte(request, response);
		} else if (action != null && action.equals("paivitatayte")) {
			paivitaTayte(request, response);
		} else {
			naytaSivu(request, response);
		}

	}

	public void paivitaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String pizzaid = request.getParameter("pizzaid");
		String pizzanimi = request.getParameter("pizzanimi");
		String pizzahinta = request.getParameter("pizzahinta").replace(",", ".");
		String[] taytetaulu = request.getParameterValues("pizzatayte");

		System.out.println("Käyttäjä yrittää muokata pizzaa, katsotaan onko vaadittavat tiedot syötetty.");

		if (pizzaid != null && pizzanimi != null && pizzahinta != null && taytetaulu != null) {

			System.out.println("Yritetään muokata pizzaa attribuuteilla:");
			System.out.println("ID: " + pizzaid + " - Nimi: " + pizzanimi + " - Hinta: " + pizzahinta + " - Täytteitä "
					+ taytetaulu.length + "kpl.");

			// Entryjen validointia
			Apuri apuri = new Apuri();

			if (apuri.validoiString(pizzanimi, "-", 30) != true) {
				String virhe = "Muokattavan pizzan nimi on virheellinen!";
				virhe(request, response, virhe);
			} else {

				if (apuri.validoiInt(pizzaid, 11) == false) {
					String virhe = "Muokattavan pizzan ID on virheellinen!";
					virhe(request, response, virhe);
				} else {
					if (apuri.validoiDouble(pizzahinta, 6) == false) {
						String virhe = "Muokattavan pizzan hinta on virheellinen!";
						virhe(request, response, virhe);
					} else {
						// Täytteiden määrän ja sisällön validointi
						if (taytetaulu.length > 5) {
							String virhe = "Yli viisi täytettä valittuna!";
							virhe(request, response, virhe);
						} else {

							// Validoidaan jokainen täyte
							boolean taytteetOk = true;

							for (int i = 0; i < taytetaulu.length; i++) {
								if (apuri.validoiInt(taytetaulu[i], 11) == false || taytetaulu[i].equals("0")) {
									taytteetOk = false;
									i = taytetaulu.length;
								}
							}

							if (taytteetOk != true) {
								String virhe = "Muokattavan pizzan täytteissä oli virheitä!";
								virhe(request, response, virhe);
							} else {

								if (taytetaulu.length > 0) {
									System.out.println("Pizzan input virheetön, yritetään päivittää tietokantaan.");

									HallintaDao dao = new HallintaDao();

									// Katsotaan, onnistuuko lisäys
									HashMap<String, String> vastaus = dao.paivitaPizza(pizzaid, pizzanimi, pizzahinta,
											taytetaulu);
									if (vastaus.get("virhe") != null) {
										String virhe = vastaus.get("virhe");
										request.setAttribute("virhe", virhe);
									} else if (vastaus.get("success") != null) {
										String success = vastaus.get("success");
										request.setAttribute("success", success);
									} else {
										request.setAttribute("virhe",
												"Tietokantaa päivittäessä tapahtui tuntematon virhe.");
									}
									naytaSivu(request, response);

								} else {
									String virhe = "Ei yhtään täytettä valittuna!";
									virhe(request, response, virhe);
								}

							}
						}
					}

				}

			}

		}

		else

		{
			String virhe = "Kaikkia vaadittavia tietoja ei syötetty!";
			virhe(request, response, virhe);
		}

	}

	public void lisaaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String pizzanimi = request.getParameter("pizzanimi");
		String pizzahinta = request.getParameter("pizzahinta").replace(",", ".");
		String[] taytetaulu = request.getParameterValues("pizzatayte");

		// Asetetaan 'Pizzan Lisäys'-sivu näytettäväksi kun palataan

		System.out.println("Käyttäjä yrittää lisätä pizzaa, katsotaan onko vaadittavat tiedot syötetty.");

		if (pizzanimi != null && pizzahinta != null && taytetaulu != null) {

			System.out.println("Yritetään lisätä pizzaa attribuuteilla:");
			System.out.println(
					"Nimi: " + pizzanimi + " - Hinta: " + pizzahinta + " - Täytteitä " + taytetaulu.length + "kpl.");

			// Entryjen validointia
			Apuri apuri = new Apuri();

			if (apuri.validoiString(pizzanimi, "-", 30) != true) {
				String virhe = "Lisättävän pizzan nimi on virheellinen!";
				virhe(request, response, virhe);
			} else {
				// Validoidaan hinta
				if (apuri.validoiDouble(pizzahinta, 6) == false) {
					String virhe = "Pizzan hinta on virheellinen";
					virhe(request, response, virhe);
				} else {
					// Täytteiden määrän ja sisällön validointi
					if (taytetaulu.length > 5) {
						String virhe = "Yli viisi täytettä valittuna!";
						virhe(request, response, virhe);
					} else {
						// Validoidaan jokainen täyte
						boolean taytteetOk = true;

						for (int i = 0; i < taytetaulu.length; i++) {
							if (apuri.validoiInt(taytetaulu[i], 11) == false || taytetaulu[i].equals("0")) {
								taytteetOk = false;
								i = taytetaulu.length;
							}
						}

						if (taytteetOk != true) {
							String virhe = "Lisättävän pizzan täytteissä oli virheitä!";
							virhe(request, response, virhe);
						} else {

							if (taytetaulu.length > 0) {
								System.out.println("Pizzan input virheetön, yritetään lisätä tietokantaan.");

								HallintaDao dao = new HallintaDao();

								// Katsotaan, onnistuuko lisäys
								HashMap<String, String> vastaus = dao.lisaaPizza(pizzanimi, pizzahinta, taytetaulu);
								if (vastaus.get("virhe") != null) {
									String virhe = vastaus.get("virhe");
									request.setAttribute("virhe", virhe);
								} else if (vastaus.get("success") != null) {
									String success = vastaus.get("success");
									request.setAttribute("success", success);
								} else {
									request.setAttribute("virhe", "Tietokantaan viedessä tapahtui tuntematon virhe.");
								}
								naytaSivu(request, response);

							} else {
								String virhe = "Ei yhtään täytettä valittuna!";
								virhe(request, response, virhe);
							}

						}
					}
				}
			}

		} else {
			String virhe = "Kaikkia vaadittavia tietoja ei syötetty!";
			virhe(request, response, virhe);
		}

	}

	public void paivitaTayte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String tayteId = request.getParameter("tayteid");
		String tayteNimi = request.getParameter("taytenimi");
		String tayteSaatavilla = request.getParameter("taytesaatavilla");

		System.out.println(tayteId);
		System.out.println(tayteNimi);
		System.out.println(tayteSaatavilla);

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (tayteId != null && tayteNimi != null && tayteSaatavilla != null) {
			if (apuri.validoiInt(tayteId, 11) == true && apuri.validoiString(tayteNimi, "-", 20) == true) {
				if (tayteSaatavilla.equals("0")) {
					tayteSaatavilla = "E";
				} else if (tayteSaatavilla.equals("1")) {
					tayteSaatavilla = "K";
				} else {
					String virhe = "Täytteen saatavuustieto on virheellinen.";
					virhe(request, response, virhe);
				}

				if (tayteSaatavilla.equals("K") || tayteSaatavilla.equals("E")) {

					HallintaDao dao = new HallintaDao();

					// Katsotaan, onnistuuko lisäys
					HashMap<String, String> vastaus = dao.paivitaTayte(tayteId, tayteNimi, tayteSaatavilla);
					if (vastaus.get("virhe") != null) {
						String virhe = vastaus.get("virhe");
						request.setAttribute("virhe", virhe);
					} else if (vastaus.get("success") != null) {
						String success = vastaus.get("success");
						request.setAttribute("success", success);
					} else {
						request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
					}
					naytaSivu(request, response);
				}

			} else {
				String virhe = "Joku muokattavan täytteen arvoista on väärässä muodossa.";
				virhe(request, response, virhe);
			}
		} else {
			String virhe = "Kaikkia muokattavan täytteen tietoja ei annettu.";
			virhe(request, response, virhe);
		}

	}

	public void poistaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String poistapizza = request.getParameter("pizza-poista");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (apuri.validoiInt(poistapizza, 11) == false) {
			String virhe = "Poistettavan pizzan ID ei ole validi!";
			virhe(request, response, virhe);
		} else {
			System.out.println("Yritetään poistaa pizza ID: " + poistapizza);

			HallintaDao dao = new HallintaDao();

			HashMap<String, String> vastaus = dao.poistaPizza(poistapizza);
			if (vastaus.get("virhe") != null) {
				String virhe = vastaus.get("virhe");
				request.setAttribute("virhe", virhe);
			} else if (vastaus.get("success") != null) {
				String success = vastaus.get("success");
				request.setAttribute("success", success);
			} else {
				request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
			}

			naytaSivu(request, response);

		}

	}

	public void palautaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String palautapizza = request.getParameter("pizza-palauta");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (apuri.validoiInt(palautapizza, 11) == false) {
			String virhe = "Palautettavan pizzan ID ei ole validi!";
			virhe(request, response, virhe);
		} else {
			System.out.println("Yritetään palauttaa pizza ID: " + palautapizza);

			HallintaDao dao = new HallintaDao();

			HashMap<String, String> vastaus = dao.palautaPizza(palautapizza);
			if (vastaus.get("virhe") != null) {
				String virhe = vastaus.get("virhe");
				request.setAttribute("virhe", virhe);
			} else if (vastaus.get("success") != null) {
				String success = vastaus.get("success");
				request.setAttribute("success", success);
			} else {
				request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
			}

			naytaSivu(request, response);

		}

	}

	public void lisaaTayte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String tayteNimi = request.getParameter("taytenimi");
		String tayteSaatavilla = request.getParameter("taytesaatavilla");

		// Apuri validointiin
		Apuri apuri = new Apuri();

		// Dao
		HallintaDao dao = new HallintaDao();

		if (tayteNimi != null && apuri.validoiString(tayteNimi, "", 20) == true && tayteSaatavilla != null) {

			if (tayteSaatavilla.equals("0")) {
				tayteSaatavilla = "E";
			} else if (tayteSaatavilla.equals("1")) {
				tayteSaatavilla = "K";
			} else {
				String virhe = "Lisättävän täytteen saatavuustiedoissa oli virheitä.";
				virhe(request, response, virhe);
			}

			// Kirjotin tätä yöllä kolmelta joten en tiedä mitä järkeä täs on
			if (tayteSaatavilla.equals("E") || tayteSaatavilla.equals("K")) {

				// Katsotaan, onnistuuko lisäys
				HashMap<String, String> vastaus = dao.lisaaTayte(tayteNimi, tayteSaatavilla);
				if (vastaus.get("virhe") != null) {
					String virhe = vastaus.get("virhe");
					request.setAttribute("virhe", virhe);
				} else if (vastaus.get("success") != null) {
					String success = vastaus.get("success");
					request.setAttribute("success", success);
				} else {
					request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
				}

				naytaSivu(request, response);
			}

		} else {
			String virhe = "Lisättävän täytteen tiedoissa oli virheitä.";
			virhe(request, response, virhe);
		}
	}

	// Error-attribuutin asetus ja redirect
	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		request.setAttribute("virhe", virhe);
		System.out.println(virhe);
		naytaSivu(request, response);
	}

}
