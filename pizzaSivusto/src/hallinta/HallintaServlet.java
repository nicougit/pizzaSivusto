package hallinta;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import apuluokka.Apuri;
import apuluokka.DeployAsetukset;
import bean.Juoma;
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

		String json = request.getParameter("json");

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
				String poistaPizzat = request.getParameter("poista-merkityt");
				String pizzatTaytteella = request.getParameter("pizzat-taytteella");
				String juomaPoista = request.getParameter("juoma-poista");
				String juomaPalauta = request.getParameter("juoma-palauta");
				String kaikkiJsonina = request.getParameter("kaikkiJsonina");

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
					taytteenMuokkaus(request, response, null);
				} else if (pizzaPoista != null && apuri.validoiInt(pizzaPoista, 11) == true) {
					poistaPizza(request, response);
				} else if (pizzaPalauta != null && apuri.validoiInt(pizzaPalauta, 11) == true) {
					palautaPizza(request, response);
				} else if (juomaPalauta != null && apuri.validoiInt(juomaPalauta, 11)) {
					palautaJuoma(request, response);
				} else if (juomaPoista != null && apuri.validoiInt(juomaPoista, 11)) {
					poistaJuoma(request, response);
				} else if (poistaPizzat != null) {
					if (kayttaja.getTyyppi().equals("admin")) {
						poistaMerkityt(request, response);
					} else if (kayttaja.getTyyppi().equals("staff")) {
						String virhe = "Vain admin voi poistaa pizzat lopullisesti";
						virhe(request, response, virhe);
					} else {
						naytaSivu(request, response);
					}
				} else if (pizzatTaytteella != null) {
					pizzatTaytteella(request, response);
				} else if (kaikkiJsonina != null) {
					kaikkiJsonina(request, response);
				} else {
					if (json != null) {
						String virhe = "Virheellinen JSON pyyntö";
						virhe(request, response, virhe);
					} else {
						naytaSivu(request, response);
					}
				}
			} else {
				if (json != null) {
					String virhe = "Pääsy evätty! Sinulla pitää olla staff- tai admin-tunnukset!";
					virhe(request, response, virhe);
				} else {
					paasyEvatty(request, response);
				}
			}
		} else {
			if (json != null) {
				String virhe = "Pääsy evätty! Et ole kirjautunut sisään!";
				virhe(request, response, virhe);
			} else {
				paasyEvatty(request, response);
			}
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

	protected void taytteenMuokkaus(HttpServletRequest request, HttpServletResponse response, String tayteId)
			throws ServletException, IOException {

		String tayteEdit = request.getParameter("tayte-edit");
		HallintaDao dao = new HallintaDao();

		if (tayteId != null) {
			tayteEdit = tayteId;
		}

		System.out.println("Täytettä '" + tayteEdit + "' halutaan muokata");

		Tayte tayte = dao.haeTayte(tayteEdit);

		if (tayte.getNimi() != null) {
			ArrayList<Pizza> pizzat = dao.haeKaikkiPizzat(1, tayteEdit);
			request.setAttribute("tayte", tayte);
			request.setAttribute("pizzat", pizzat);

			// Forwardataan pizzan muokkaukseen
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/tayte-muokkaa.jsp");
			rd.forward(request, response);

		} else {
			String virhe = "Muokattavaksi valittua täytettä ei ole tietokannassa.";
			virhe(request, response, virhe);
		}
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

		String json = request.getParameter("json");

		// Katsotaan oikeudet
		HttpSession sessio = request.getSession(false);

		if (sessio != null && sessio.getAttribute("kayttaja") != null) {
			Kayttaja kayttaja = (Kayttaja) sessio.getAttribute("kayttaja");
			if (kayttaja.getTyyppi().equals("admin") || kayttaja.getTyyppi().equals("staff")) {

				String action = request.getParameter("action");

				if (action != null && action.equals("lisaapizza")) {
					lisaaPizza(request, response);
				} else if (action != null && action.equals("lisaajuoma")) {
					lisaaJuoma(request, response);
				} else if (action != null && action.equals("paivitapizza")) {
					paivitaPizza(request, response);
				} else if (action != null && action.equals("paivitajuoma")) {
					paivitaJuoma(request, response);
				} else if (action != null && action.equals("lisaatayte")) {
					lisaaTayte(request, response);
				} else if (action != null && action.equals("paivitatayte")) {
					paivitaTayte(request, response);
				} else if (action != null && action.equals("haekaikki")) {
					kaikkiJsonina(request, response);
				} else if (action != null && action.equals("poistatayte")) {
					if (kayttaja.getTyyppi().equals("admin")) {
						poistaTayte(request, response);
					} else if (kayttaja.getTyyppi().equals("staff")) {
						String virhe = "Vain admin voi poistaa täytteen lopullisesti";
						virhe(request, response, virhe);
					} else {
						naytaSivu(request, response);
					}
				} else {
					naytaSivu(request, response);
				}
			} else {
				if (json != null) {
					String virhe = "Pääsy evätty! Sinulla pitää olla staff- tai admin-tunnukset!";
					virhe(request, response, virhe);
				} else {
					paasyEvatty(request, response);
				}
			}
		} else {
			if (json != null) {
				String virhe = "Pääsy evätty! Et ole kirjautunut sisään!";
				virhe(request, response, virhe);
			} else {
				paasyEvatty(request, response);
			}
		}
	}

	public void paivitaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String pizzaid = request.getParameter("pizzaid");
		String pizzanimi = request.getParameter("pizzanimi");
		String pizzakuvaus = request.getParameter("pizzakuvaus");
		String pizzahinta = request.getParameter("pizzahinta").replace(",", ".");
		String[] taytetaulu = request.getParameterValues("pizzatayte");

		if (pizzaid != null && pizzanimi != null && pizzakuvaus != null && pizzahinta != null && taytetaulu != null) {

			// Entryjen validointia
			Apuri apuri = new Apuri();

			if (apuri.validoiString(pizzanimi, "-", 50) != true) {
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

							if (apuri.validoiKuvaus(pizzakuvaus) == false) {
								String virhe = "Muokattavan pizzan kuvaus on virheellinen";
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
										HashMap<String, String> vastaus = dao.paivitaPizza(pizzaid, pizzanimi,
												pizzakuvaus, pizzahinta, taytetaulu);
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

		}

		else

		{
			String virhe = "Kaikkia vaadittavia tietoja ei syötetty!";
			virhe(request, response, virhe);
		}

	}

	public void paivitaJuoma(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String juomaid = request.getParameter("juomaid");
		String juomanimi = request.getParameter("juomanimi");
		String juomahinta = request.getParameter("juomahinta").replace(",", ".");
		String juomakuvaus = request.getParameter("juomakuvaus");
		String juomakoko = request.getParameter("juomakoko");
		String juomasaatavilla = request.getParameter("juomasaatavilla");
		String json = request.getParameter("json");

		if (juomaid != null && juomanimi != null && juomahinta != null && juomakuvaus != null && juomakoko != null
				&& juomasaatavilla != null) {

			// Entryjen validointia
			Apuri apuri = new Apuri();

			if (apuri.validoiInt(juomaid, 11) != true) {
				String virhe = "Muokattavan juoman id on virheellinen";
				virhe(request, response, virhe);
			} else {
				if (apuri.validoiString(juomanimi, "-", 30) != true) {
					String virhe = "Muokattavan juoman nimi on virheellinen";
					virhe(request, response, virhe);
				} else {
					// Validoidaan hinta
					if (apuri.validoiDouble(juomahinta, 6) == false) {
						String virhe = "Muokattavan juoman hinta on virheellinen";
						virhe(request, response, virhe);
					} else {
						// Validoidaan koko
						if (apuri.validoiDouble(juomakoko, 6) != true) {
							String virhe = "Juoman koko on virheellinen";
							virhe(request, response, virhe);
						} else {
							if (apuri.validoiKuvaus(juomakuvaus) != true) {
								String virhe = "Muokattavan juoman kuvaus on virheellinen";
								virhe(request, response, virhe);
							} else {
								if (juomasaatavilla.equals("0") || juomasaatavilla.equals("1")) {

									if (juomasaatavilla.equals("0")) {
										juomasaatavilla = "E";
									} else {
										juomasaatavilla = "K";
									}

									HallintaDao dao = new HallintaDao();

									// Katsotaan, onnistuuko lisäys
									HashMap<String, String> vastaus = dao.paivitaJuoma(juomaid, juomanimi, juomakoko,
											juomahinta, juomakuvaus, juomasaatavilla);
									if (vastaus.get("virhe") != null) {
										String virhe = vastaus.get("virhe");
										request.setAttribute("virhe", virhe);
									} else if (vastaus.get("success") != null) {
										String success = vastaus.get("success");
										request.setAttribute("success", success);
									} else {
										request.setAttribute("virhe",
												"Tietokantaan viedessä tapahtui tuntematon virhe.");
									}
									if (json != null) {
										jsonVastaus(request, response, vastaus);
									} else {
										naytaSivu(request, response);
									}

								} else {
									String virhe = "Juomalla on virheellinen saatavuus";
									virhe(request, response, virhe);
								}

							}
						}
					}
				}
			}
		}

		else {
			String virhe = "Kaikkia vaadittavia tietoja ei syötetty!";
			virhe(request, response, virhe);
		}

	}

	public void lisaaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String pizzanimi = request.getParameter("pizzanimi");
		String pizzahinta = request.getParameter("pizzahinta").replace(",", ".");
		String pizzakuvaus = request.getParameter("pizzakuvaus");
		String[] taytetaulu = request.getParameterValues("pizzatayte");
		String json = request.getParameter("json");

		// Asetetaan 'Pizzan Lisäys'-sivu näytettäväksi kun palataan

		System.out.println("Käyttäjä yrittää lisätä pizzaa, katsotaan onko vaadittavat tiedot syötetty.");

		if (pizzanimi != null && pizzahinta != null && pizzakuvaus != null && taytetaulu != null) {

			System.out.println("Yritetään lisätä pizzaa attribuuteilla:");
			System.out.println(
					"Nimi: " + pizzanimi + " - Hinta: " + pizzahinta + " - Täytteitä " + taytetaulu.length + "kpl.");

			// Entryjen validointia
			Apuri apuri = new Apuri();

			if (apuri.validoiString(pizzanimi, "-", 30) != true) {
				String virhe = "Lisättävän pizzan nimi on virheellinen";
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

						if (apuri.validoiKuvaus(pizzakuvaus) == false) {
							String virhe = "Lisättävän pizzan kuvaus on virheellinen";
							virhe(request, response, virhe);
						}

						else {

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
									HashMap<String, String> vastaus = dao.lisaaPizza(pizzanimi, pizzakuvaus, pizzahinta,
											taytetaulu);
									if (vastaus.get("virhe") != null) {
										String virhe = vastaus.get("virhe");
										request.setAttribute("virhe", virhe);
									} else if (vastaus.get("success") != null) {
										String success = vastaus.get("success");
										request.setAttribute("success", success);
									} else {
										request.setAttribute("virhe",
												"Tietokantaan viedessä tapahtui tuntematon virhe.");
									}
									if (json != null) {
										jsonVastaus(request, response, vastaus);
									} else {
										naytaSivu(request, response);
									}

								} else {
									String virhe = "Ei yhtään täytettä valittuna!";
									virhe(request, response, virhe);
								}

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

	public void lisaaJuoma(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String juomanimi = request.getParameter("juomanimi");
		String juomahinta = request.getParameter("juomahinta").replace(",", ".");
		String juomakuvaus = request.getParameter("juomakuvaus");
		String juomakoko = request.getParameter("juomakoko");
		String juomasaatavilla = request.getParameter("juomasaatavilla");
		String json = request.getParameter("json");

		if (juomanimi != null && juomahinta != null && juomakuvaus != null && juomakoko != null
				&& juomasaatavilla != null) {

			// Entryjen validointia
			Apuri apuri = new Apuri();

			if (apuri.validoiString(juomanimi, "-", 30) != true) {
				String virhe = "Lisättävän juoman nimi on virheellinen";
				virhe(request, response, virhe);
			} else {
				// Validoidaan hinta
				if (apuri.validoiDouble(juomahinta, 6) == false) {
					String virhe = "Juoman hinta on virheellinen";
					virhe(request, response, virhe);
				} else {
					// Validoidaan koko
					if (apuri.validoiDouble(juomakoko, 6) != true) {
						String virhe = "Juoman koko on virheellinen";
						virhe(request, response, virhe);
					} else {
						if (apuri.validoiKuvaus(juomakuvaus) != true) {
							String virhe = "Lisättävän juoman kuvaus on virheellinen";
							virhe(request, response, virhe);
						} else {
							if (juomasaatavilla.equals("0") || juomasaatavilla.equals("1")) {

								if (juomasaatavilla.equals("0")) {
									juomasaatavilla = "E";
								} else {
									juomasaatavilla = "K";
								}

								HallintaDao dao = new HallintaDao();

								// Katsotaan, onnistuuko lisäys
								HashMap<String, String> vastaus = dao.lisaaJuoma(juomanimi, juomakoko, juomahinta,
										juomakuvaus, juomasaatavilla);
								if (vastaus.get("virhe") != null) {
									String virhe = vastaus.get("virhe");
									request.setAttribute("virhe", virhe);
								} else if (vastaus.get("success") != null) {
									String success = vastaus.get("success");
									request.setAttribute("success", success);
								} else {
									request.setAttribute("virhe", "Tietokantaan viedessä tapahtui tuntematon virhe.");
								}
								if (json != null) {
									jsonVastaus(request, response, vastaus);
								} else {
									naytaSivu(request, response);
								}

							} else {
								String virhe = "Juomalla on virheellinen saatavuus";
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
		String json = request.getParameter("json");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (tayteId != null && tayteNimi != null && tayteSaatavilla != null) {
			if (apuri.validoiInt(tayteId, 11) == true && apuri.validoiString(tayteNimi, " -", 50) == true) {
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

					// Katsotaan, onnistuuko päivitys
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
					if (json != null) {
						jsonVastaus(request, response, vastaus);
					} else {
						naytaSivu(request, response);
					}
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
		String json = request.getParameter("json");

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
			if (json != null) {
				jsonVastaus(request, response, vastaus);
			} else {
				naytaSivu(request, response);
			}

		}

	}

	public void poistaJuoma(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String poistajuoma = request.getParameter("juoma-poista");
		String json = request.getParameter("json");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (apuri.validoiInt(poistajuoma, 11) == false) {
			String virhe = "Poistettavan juoman ID ei ole validi!";
			virhe(request, response, virhe);
		} else {
			System.out.println("Yritetään poistaa juomaa ID: " + poistajuoma);

			HallintaDao dao = new HallintaDao();

			HashMap<String, String> vastaus = dao.poistaJuoma(poistajuoma);
			if (vastaus.get("virhe") != null) {
				String virhe = vastaus.get("virhe");
				request.setAttribute("virhe", virhe);
			} else if (vastaus.get("success") != null) {
				String success = vastaus.get("success");
				request.setAttribute("success", success);
			} else {
				request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
			}
			if (json != null) {
				jsonVastaus(request, response, vastaus);
			} else {
				naytaSivu(request, response);
			}

		}

	}

	public void palautaPizza(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String palautapizza = request.getParameter("pizza-palauta");
		String json = request.getParameter("json");

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
			if (json != null) {
				jsonVastaus(request, response, vastaus);
			} else {
				naytaSivu(request, response);
			}

		}

	}

	public void palautaJuoma(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String palautajuoma = request.getParameter("juoma-palauta");
		String json = request.getParameter("json");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (apuri.validoiInt(palautajuoma, 11) == false) {
			String virhe = "Palautettavan juoman ID ei ole validi!";
			virhe(request, response, virhe);
		} else {
			System.out.println("Yritetään palauttaa juomaa ID: " + palautajuoma);

			HallintaDao dao = new HallintaDao();

			HashMap<String, String> vastaus = dao.palautaJuoma(palautajuoma);
			if (vastaus.get("virhe") != null) {
				String virhe = vastaus.get("virhe");
				request.setAttribute("virhe", virhe);
			} else if (vastaus.get("success") != null) {
				String success = vastaus.get("success");
				request.setAttribute("success", success);
			} else {
				request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
			}
			if (json != null) {
				jsonVastaus(request, response, vastaus);
			} else {
				naytaSivu(request, response);
			}

		}

	}

	public void poistaMerkityt(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String poistaMerkityt = request.getParameter("poista-merkityt");
		String json = request.getParameter("json");

		if (poistaMerkityt.equals("true")) {
			HallintaDao dao = new HallintaDao();
			HashMap<String, String> vastaus = dao.poistaMerkityt();
			if (vastaus.get("virhe") != null) {
				String virhe = vastaus.get("virhe");
				request.setAttribute("virhe", virhe);
			} else if (vastaus.get("success") != null) {
				String success = vastaus.get("success");
				request.setAttribute("success", success);
			} else {
				request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
			}
			if (json != null) {
				jsonVastaus(request, response, vastaus);
			}
		} else {
			System.out
					.println("Saavuttiin poistaMerkityt-metodiin, mutta poista-merkityt oli '" + poistaMerkityt + "'");

			naytaSivu(request, response);
		}

	}

	public void poistaTayte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String poistaTayte = request.getParameter("id");
		String json = request.getParameter("json");

		// Validoidaan input
		Apuri apuri = new Apuri();

		if (apuri.validoiInt(poistaTayte, 11) == false) {
			String virhe = "Poistettavan täytteen ID ei ole validi!";
			virhe(request, response, virhe);
		} else {
			System.out.println("Yritetään poistaa täytettä ID: " + poistaTayte);
			HallintaDao dao = new HallintaDao();

			HashMap<String, String> vastaus = dao.poistaTayte(poistaTayte);
			if (json != null) {
				jsonVastaus(request, response, vastaus);
			} else {
				if (vastaus.get("virhe") != null) {
					String virhe = vastaus.get("virhe");
					request.setAttribute("virhe", virhe);
					taytteenMuokkaus(request, response, poistaTayte);
				} else if (vastaus.get("success") != null) {
					String success = vastaus.get("success");
					request.setAttribute("success", success);
					naytaSivu(request, response);
				} else {
					request.setAttribute("virhe", "Tietokantaa päivittäessä tapahtui tuntematon virhe.");
					naytaSivu(request, response);
				}
			}

		}

	}

	public void lisaaTayte(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Haetaan parametrit
		String tayteNimi = request.getParameter("taytenimi");
		String tayteSaatavilla = request.getParameter("taytesaatavilla");
		String json = request.getParameter("json");

		// Apuri validointiin
		Apuri apuri = new Apuri();

		// Dao
		HallintaDao dao = new HallintaDao();

		if (tayteNimi != null && apuri.validoiString(tayteNimi, " -", 50) == true && tayteSaatavilla != null) {

			if (tayteSaatavilla.equals("0")) {
				tayteSaatavilla = "E";
			} else if (tayteSaatavilla.equals("1")) {
				tayteSaatavilla = "K";
			} else {
				String virhe = "Virheellinen saatavuus";
				virhe(request, response, virhe);
			}

			// Kirjotin tätä yöllä kolmelta joten en tiedä mitä järkeä täs on
			if (tayteSaatavilla.equals("E") || tayteSaatavilla.equals("K")) {

				// Katsotaan, onnistuuko lisäys
				HashMap<String, String> vastaus = dao.lisaaTayte(tayteNimi, tayteSaatavilla);
				if (json != null) {
					jsonVastaus(request, response, vastaus);
				} else {
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

		} else {
			String virhe = "Täytteen nimi on virheellinen";
			virhe(request, response, virhe);
		}
	}

	protected void pizzatTaytteella(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String tayteid = request.getParameter("pizzat-taytteella");
		String json = request.getParameter("json");

		Apuri apuri = new Apuri();

		if (tayteid != null && apuri.validoiInt(tayteid, 11) == true) {
			// Pizzojen haku
			HallintaDao dao = new HallintaDao();
			ArrayList<String> pizzat = dao.haePizzatJoillaTayte(tayteid);

			// Json Array
			JSONArray pizzatJson = new JSONArray();

			for (int i = 0; i < pizzat.size(); i++) {
				JSONObject pizzaobjekti = new JSONObject();
				pizzaobjekti.put("nimi", pizzat.get(i));
				pizzatJson.add(pizzaobjekti);
			}

			// Encoding ja printtaus
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");

			PrintWriter out = response.getWriter();
			out.print(pizzatJson);
		}

	}

	protected void kaikkiJsonina(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Tietojen haku
		HallintaDao dao = new HallintaDao();
		ArrayList<Tayte> taytteet = dao.haeKaikkiTaytteet();
		ArrayList<Pizza> pizzat = dao.haeKaikkiPizzat(0, "");
		ArrayList<Juoma> juomat = dao.haeKaikkiJuomat();

		// Pizzojen JSON-array
		JSONArray pizzatJson = new JSONArray();
		for (int i = 0; i < pizzat.size(); i++) {
			Pizza pizza = pizzat.get(i);
			JSONObject pizzaobjekti = new JSONObject();
			JSONArray taytearray = new JSONArray();
			pizzaobjekti.put("id", pizza.getId());
			pizzaobjekti.put("nimi", pizza.getNimi());
			pizzaobjekti.put("hinta", pizza.getHinta());
			pizzaobjekti.put("kuvaus", pizza.getKuvaus());
			pizzaobjekti.put("poistomerkinta", pizza.getPoistomerkinta());
			for (int j = 0; j < pizza.getTaytteet().size(); j++) {
				JSONObject tayteobjekti = new JSONObject();
				tayteobjekti.put("id", pizza.getTaytteet().get(j).getId());
				tayteobjekti.put("nimi", pizza.getTaytteet().get(j).getNimi());
				tayteobjekti.put("saatavilla", pizza.getTaytteet().get(j).getSaatavilla());
				taytearray.add(tayteobjekti);
			}
			pizzaobjekti.put("taytteet", taytearray);
			pizzatJson.add(pizzaobjekti);
		}

		// Täytteiden JSON-array
		JSONArray taytteetJson = new JSONArray();
		for (int i = 0; i < taytteet.size(); i++) {
			Tayte tayte = taytteet.get(i);
			JSONObject tayteobjekti = new JSONObject();
			tayteobjekti.put("id", tayte.getId());
			tayteobjekti.put("nimi", tayte.getNimi());
			tayteobjekti.put("saatavilla", tayte.getSaatavilla());
			taytteetJson.add(tayteobjekti);
		}

		// Juomien JSON-array
		JSONArray juomatJson = new JSONArray();
		for (int i = 0; i < juomat.size(); i++) {
			Juoma juoma = juomat.get(i);
			JSONObject juomaobjekti = new JSONObject();
			JSONArray taytearray = new JSONArray();
			juomaobjekti.put("id", juoma.getId());
			juomaobjekti.put("nimi", juoma.getNimi());
			juomaobjekti.put("hinta", juoma.getHinta());
			juomaobjekti.put("koko", juoma.getKoko());
			juomaobjekti.put("saatavilla", juoma.getSaatavilla());
			juomaobjekti.put("kuvaus", juoma.getKuvaus());
			juomaobjekti.put("poistomerkinta", juoma.getPoistomerkinta());
			juomatJson.add(juomaobjekti);
		}

		JSONObject datat = new JSONObject();
		datat.put("pizzat", pizzatJson);
		datat.put("taytteet", taytteetJson);
		datat.put("juomat", juomatJson);

		// Encoding ja printtaus
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		out.print(datat);

	}

	protected void jsonVastaus(HttpServletRequest request, HttpServletResponse response,
			HashMap<String, String> vastaus) throws ServletException, IOException {

		JSONArray jsonarray = new JSONArray();
		JSONObject jsonvastaus = new JSONObject();

		if (vastaus.get("virhe") != null) {
			jsonvastaus.put("virhe", vastaus.get("virhe"));
		} else if (vastaus.get("success") != null) {
			jsonvastaus.put("success", vastaus.get("success"));
		} else {
			jsonvastaus.put("virhe", "Tuntematon virhe JSONia käsitellessä");
		}

		jsonarray.add(jsonvastaus);

		// Encoding ja printtaus
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		out.print(jsonarray);

	}

	// Error-attribuutin asetus ja redirect
	protected void virhe(HttpServletRequest request, HttpServletResponse response, String virhe)
			throws ServletException, IOException {
		String json = request.getParameter("json");
		System.out.println(virhe);
		if (json != null) {
			HashMap<String, String> vastaus = new HashMap<>();
			vastaus.put("virhe", virhe);
			jsonVastaus(request, response, vastaus);
		} else {
			request.setAttribute("virhe", virhe);
			naytaSivu(request, response);
		}
	}

}
