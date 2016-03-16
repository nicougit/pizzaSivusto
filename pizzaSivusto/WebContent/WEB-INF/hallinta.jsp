<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Hallintasivu</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="row hide-on-small-only">
		<div class="col s12 m12 l10 offset-l1">
			<ul class="tabs">
				<li class="tab col s12"><a href="#pizza-h" class="active">Pizzojen
						hallinta</a></li>
				<li class="tab col s12"><a href="#pizza-l">Pizzan lisäys</a></li>
				<li class="tab col s12"><a href="#tayte-h">Täytteiden
						hallinta</a></li>
			</ul>
		</div>
	</div>


	<div class="row hide-on-med-and-up">
		<div class="col s12">
			<ul class="tabs">
				<li class="tab col s12"><a href="#pizza-h" class="active"><img
						src="img/pizza_gear.png" alt="P"> </a></li>
				<li class="tab col s12"><a href="#pizza-l"><img
						src="img/pizza_add.png" alt="L"></a></li>
				<li class="tab col s12"><a href="#tayte-h"><img
						src="img/pizza_zoom.png" alt="T"> </a></li>
			</ul>
		</div>
	</div>

	<div class="row" id="pizza-h">
		<div class="col s12">
			<h2>Pizzat</h2>
			<c:set var="poistettaviapizzoja" value="0"></c:set>
			<c:choose>
				<c:when test="${empty pizzat}">
					<div class="errori center-align">Listalla ei ole pizzoja, tai
						niitä ei saatu noudettua tietokannasta.</div>
				</c:when>
				<c:otherwise>
					<table class="bordered">
						<thead>
							<tr>
								<th>Nimi</th>
								<th class="hide-on-small-only">Täytteet</th>
								<th class="hide-on-small-only">Hinta</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pizzat}" var="pizza">
								<c:choose>
									<c:when test="${pizza.poistomerkinta != null }">
										<tr class="red lighten-5">
											<c:set var="poistettaviapizzoja"
												value="${poistettaviapizzoja + 1 }"></c:set>
									</c:when>
									<c:otherwise>
										<tr>
									</c:otherwise>
								</c:choose>
								<td>${pizza.nimi }<c:if
										test="${pizza.poistomerkinta != null }">
										<br>
										<span class="pienifontti"> Poistettu <fmt:parseDate
												value="${pizza.poistomerkinta}" var="parsittuDate"
												pattern="yyyy-MM-dd" /> <fmt:formatDate
												pattern="dd.MM.yyyy" value="${parsittuDate }" /></span>
									</c:if></td>
								<td class="pienifontti hide-on-small-only"><c:forEach
										items="${pizza.taytteet }" var="pizzantayte"
										varStatus="taytecounter">
										<c:choose>
											<c:when test="${pizzantayte.saatavilla == false }">
												<span class="errori tooltipped" data-position="bottom"
													data-delay="500"
													data-tooltip="${pizzantayte.nimi } ei saatavilla">${pizzantayte.nimi }</span>
												<c:if
													test="${fn:length(pizza.taytteet) > taytecounter.count }">, </c:if>
											</c:when>
											<c:otherwise>
												${pizzantayte.nimi }<c:if
													test="${fn:length(pizza.taytteet) > taytecounter.count }">, </c:if>
											</c:otherwise>
										</c:choose>

									</c:forEach></td>
								<td class="hide-on-small-only"><fmt:formatNumber
										type="number" minFractionDigits="2" maxFractionDigits="2"
										value="${pizza.hinta }"></fmt:formatNumber> €</td>
								<td class="right-align"><a
									class='dropdown-button btn hide-on-large-only' href='#'
									data-activates='dd-${pizza.id }'><i class="material-icons">edit</i></a>
									<ul id="dd-${pizza.id }" class="dropdown-content">
										<li><a href="?pizza-edit=${pizza.id }">Muokkaa</a></li>
										<c:choose>
											<c:when test="${pizza.poistomerkinta == null}">
												<li><a href="?pizza-poista=${pizza.id }">Poista</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="?pizza-palauta=${pizza.id }">Palauta</a></li>
											</c:otherwise>
										</c:choose>
									</ul> <a
									class="waves-effect waves-light btn tooltipped hide-on-med-and-down"
									href="?pizza-edit=${pizza.id }" data-position="left"
									data-delay="500" data-tooltip="Muokkaa"><i
										class="material-icons">edit</i></a> <c:choose>
										<c:when test="${pizza.poistomerkinta == null}">
											<a
												class="waves-effect waves-light btn red lighten-2 tooltipped hide-on-med-and-down"
												href="?pizza-poista=${pizza.id }" data-position="right"
												data-delay="500" data-tooltip="Poista"> <i
												class="material-icons large">delete</i>
											</a>
										</c:when>
										<c:otherwise>
											<a
												class="waves-effect waves-light btn red lighten-2 tooltipped hide-on-med-and-down"
												href="?pizza-palauta=${pizza.id }" data-position="right"
												data-delay="500" data-tooltip="Palauta"> <i
												class="material-icons">visibility_off</i>
											</a>
										</c:otherwise>
									</c:choose></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="row">
						<br>
						<c:if test="${kayttaja.tyyppi == 'admin' }">
							<div
								class="col s12 m6 l6 push-m6 push-l6 small-centteri right-align">
								<c:choose>
									<c:when test="${poistettaviapizzoja == 0 }">
										<a
											class="waves-effect waves-light btn modal-trigger red lighten-2 disabled tooltipped"
											href="#!" data-position="bottom" data-delay="500"
											data-tooltip="Yhtään pizzaa ei ole merkitty poistettavaksi"><i
											class="material-icons left">delete</i> Poista merkityt</a>
										<br>
										<br>
									</c:when>
									<c:otherwise>
										<a
											class="waves-effect waves-light btn modal-trigger red lighten-2 tooltipped"
											href="#poistomodal" data-position="bottom" data-delay="500"
											data-tooltip="Poistaa pizzat (${poistettaviapizzoja } kpl) tietokannasta pysyvästi"><i
											class="material-icons left">delete</i> Poista merkityt</a>
										<br>
										<br>
										<div id="poistomodal" class="modal">
											<div class="modal-content center-align">
												<h4>Oletko varma?</h4>
												<p>Poistettavaksi merkityt pizzat (${poistettaviapizzoja }
													kpl) poistetaan tietokannasta pysyvästi.</p>

												<table class="bordered">
													<thead>
														<tr>
															<th>Pizzan nimi</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${pizzat}" var="pizza">
															<c:if test="${pizza.poistomerkinta != null }">
																<tr>
																	<td>${pizza.nimi }<span class="pienifontti right">Merkitty
																			poistettavaksi <fmt:parseDate
																				value="${pizza.poistomerkinta}" var="parsittuDate"
																				pattern="yyyy-MM-dd" /> <fmt:formatDate
																				pattern="dd.MM.yyyy" value="${parsittuDate }" />
																	</span>
																	</td>
															</c:if>
														</c:forEach>
													</tbody>
												</table>
												<br> <a href="#!"
													class="modal-action modal-close waves-effect waves-light btn red lighten-2">Peruuta</a>
												<a href="?poista-pizzat=true"
													class="modal-action waves-effect waves-light btn">Poista</a>
											</div>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
						<div
							class="col s12 small-centteri <c:if test="${kayttaja.tyyppi == 'admin' }">m6 l6 pull-m6 pull-l6</c:if> ">
							<a class="waves-effect waves-light btn hide-on-med-and-down"
								onclick="window.print();"> <i class="material-icons left">print</i>
								Tulosta
							</a> <a href="#" class="waves-effect btn"> <i
								class="material-icons left">navigation</i> Alkuun
							</a>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="row" id="pizza-l">
		<div class="col s12">
			<h2>Pizzan lisäys tietokantaan</h2>
			<c:choose>
				<c:when test="${empty taytteet}">
					<div class="errori center-align">Pizzaa ei voi lisätä, koska
						täytteitä ei ole, tai niitä ei saatu noudettua tietokannasta.
						Kokeile lisätä ensin täytteet.</div>
				</c:when>
				<c:otherwise>
					<div class="row">
						<form action="hallinta" method="post">
							<div class="col s12 m12 l10 offset-l1">
								<div class="row">
									<div class="input-field col s12 m9 l9">
										<input type="text" name="pizzanimi" id="pizzanimi"
											autocomplete="off"> <label for="pizzanimi">Pizzan
											nimi</label>
									</div>
									<div class="input-field col s12 m3 l3">
										<input type="number" class="validate" min="0" step="0.05"
											name="pizzahinta" id="pizzahinta" autocomplete="off"><label
											for="pizzahinta" data-error="Virhe">Pizzan hinta</label>
									</div>
								</div>
								<!-- Täytteiden valinta -->
								<div class="row" id="pizza-taytteet">
									<label id="taytteet-label">Täytteet</label> <br> <br>
									<div class="row">
										<c:forEach items="${taytteet}" var="tayte"
											varStatus="loopCount">
											<div class="col s6 m4 l3 taytediv">
												<input type="checkbox" id="${tayte.id }" name="pizzatayte"
													value="${tayte.id }"><label for="${tayte.id }"
													<c:if test="${tayte.saatavilla == false }"> class="errori-light"</c:if>>${tayte.nimi }</label>

											</div>
										</c:forEach>
									</div>
									<script src="js/tayte-input-limit.js"></script>
									<button class="btn waves-effect waves-light btn-large"
										type="submit" name="action" value="lisaapizza">Lisää
										pizza</button>
								</div>
							</div>
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>



	<div class="row" id="tayte-h">
		<div class="col s12 m12 l5 push-l7 " id="taytel">
			<h2>Lisää täyte</h2>
			<div class="row">
				<form action="hallinta" method="post">
					<div class="row">
						<div class="col s12 input-field">
							<input type="text" name="taytenimi" id="taytenimi"
								autocomplete="off" class="fieldi"> <label
								for="taytenimi">Täytteen nimi</label>
						</div>
					</div>
					<div class="row">
						<div class="col s6">
							<input name="taytesaatavilla" type="radio" id="saatavilla"
								value="1" checked> <label for="saatavilla">Saatavilla</label>
						</div>
						<div class="col s6">
							<input name="taytesaatavilla" type="radio" id="eisaatavilla"
								value="0"> <label for="eisaatavilla">Ei
								saatavilla</label>
						</div>
					</div>
					<div class="row">
						<div class="col s12">
							<button class="btn waves-effect waves-light btn-large"
								type="submit" name="action" value="lisaatayte">Lisää
								täyte</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="col s12 m12 l6 pull-l5">
			<h2>Täytteet</h2>
			<c:choose>
				<c:when test="${empty taytteet}">
					<div class="row errori center-align">Täytteitä ei ole, tai
						niitä ei saatu noudettua tietokannasta.</div>
				</c:when>
				<c:otherwise>
					<div class="row">
						<form action="hallinta" method="post">
							<table class="bordered">
								<thead>
									<tr>
										<th>Täyte</th>
										<th>Saatavilla</th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${taytteet}" var="tayte">
										<c:choose>
											<c:when test="${tayte.saatavilla == false }">
												<tr class="red lighten-5">
											</c:when>
											<c:otherwise>
												<tr>
											</c:otherwise>
										</c:choose>
										<td>${tayte.nimi }</td>
										<td><c:choose>
												<c:when test="${tayte.saatavilla == true }">Kyllä</c:when>
												<c:otherwise>
											Ei
										</c:otherwise>
											</c:choose></td>
										<td><a class="waves-effect waves-light btn tooltipped"
											href="?tayte-edit=${tayte.id }" data-position="right"
											data-delay="500" data-tooltip="Muokkaa"><i
												class="material-icons">edit</i></a></td>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
					<!--  "alkuun" -nappula isommilla näytöillä -->
					<div class="row hide-on-small-only">
						<div class="col s12">
							<a href="#" class="waves-effect btn red lighten-2 "> <i
								class="material-icons">navigation</i> Alkuun
							</a>
						</div>
					</div>
					<!-- "alkuun" -nappula mobiililla -->
					<div class="row hide-on-med-and-up">
						<div class="col s12">
							<a href="#" class="waves-effect waves-light btn red lighten-2">
								<i class="material-icons">navigation</i>
							</a>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>