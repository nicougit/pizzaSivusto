<%@page import="fi.softala.pizzeria.bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="url">${pageContext.request.contextPath}</c:set>
<c:set var="pizzaurl" value="${url }/WEB-INF/pizzat.jsp"></c:set>
<c:set var="tilausurl" value="${url }/WEB-INF/tilaus.jsp"></c:set>
<c:set var="tilausvahvistusurl" value="${url }/WEB-INF/tilausvahvistus.jsp"></c:set>
<div class="navbar-fixed">
	<c:if test="${not empty kayttaja }">
	<c:if test="${kayttaja.tyyppi == 'admin' || kayttaja.tyyppi == 'staff' }">
		<ul id="hallinta-dropdown" class="dropdown-content">
				<li><a href="<c:url value='/hallinta'/>">Tuotteet</a></li>
				<li><a href="<c:url value='/tilaukset'/>">Tilaukset</a></li>
				<li><a href="<c:url value='/kayttajat'/>">Käyttäjät</a></li>
				<li><a href="<c:url value='/aukioloajat'/>">Aukioloajat</a></li>
		</ul>
		</c:if>
	</c:if>
	<nav>
		<div class="nav-wrapper">
			<a href="<c:url value='/'/>" class="brand-logo">Castello é Fiori</a>
			<a href="#" data-activates="mobiili-nav" class="button-collapse"><i
				class="material-icons">menu</i></a>
			<ul class="right hide-on-med-and-down">
				<c:if test="${pageContext.request.requestURI == pizzaurl }">
					<li><a href="#pizzat"><i class="material-icons left navicon">local_pizza</i>Pizzat</a></li>
					<li><a href="#juomat"><i class="material-icons left navicon">local_drink</i>Juomat</a></li>
				</c:if>
				<c:if test="${pageContext.request.requestURI != pizzaurl }">
					<li><a href="<c:url value='/pizza'/>"><i class="material-icons left navicon">local_pizza</i>Menu</a></li>
				</c:if>
				
				<li>
				<c:choose>
				<c:when test="${pageContext.request.requestURI != tilausurl && pageContext.request.requestURI != tilausvahvistusurl}">
				<a class="modal-trigger" href="#ostoskorimodal">
				</c:when>
				<c:otherwise>
				<a href="<c:url value='/ostoskori'/>">
				</c:otherwise>
				</c:choose>
				<i
						class="material-icons left navicon">shopping_cart</i> Ostoskori<span
						class="navbar-yhteishinta pienifontti"></span></a></li>
				<c:choose>
					<c:when test="${not empty kayttaja }">
						<li><a href="<c:url value="/kayttaja"></c:url>"><i class="material-icons left navicon">person</i><c:out value="${kayttaja.tunnus }"></c:out></a></li>
											<c:if test="${kayttaja.tyyppi == 'admin' || kayttaja.tyyppi == 'staff' }">
					<li><a class="dropdown-button" href="#!"
							data-activates="hallinta-dropdown"><i class="material-icons left navicon">settings</i>Hallinta<i
								class="material-icons right">arrow_drop_down</i></a></li>
								</c:if>
						<li class="tooltipped" data-position="left" data-delay="300" data-tooltip="Kirjaudu ulos"><a href="<c:url value='/login?logout=true'/>"><i class="material-icons navicon">power_settings_new</i></a></li>
					</c:when>
					<c:otherwise>
						<li><a onClick="loginModal()">Kirjaudu
								sisään</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<ul class="side-nav" id="mobiili-nav">
				<li><a href="<c:url value='/pizza'/>"><i class="material-icons left navicon">local_pizza</i> Menu</a></li>
				<li><a href="<c:url value='/ostoskori'/>"><i class="material-icons left navicon">shopping_cart</i> Ostoskori<span
						class="navbar-yhteishinta right"></span></a> <c:choose>
						<c:when test="${not empty kayttaja }">
							<c:if test="${kayttaja.tyyppi == 'admin' || kayttaja.tyyppi == 'staff' }">
								<li class="divider"></li>
								<li><a href="<c:url value='/hallinta'/>"><i class="material-icons left navicon">settings</i>Tuotteet</a></li>
								<li><a href="<c:url value='/tilaukset'/>"><i class="material-icons left navicon">settings</i>Tilaukset</a></li>
								<li><a href="<c:url value='/kayttajat'/>"><i class="material-icons left navicon">settings</i>Käyttäjät</a></li>
							</c:if>
							<li class="divider"></li>
							<li><a href="<c:url value='/kayttaja'/>"><i class="material-icons left navicon">person</i><c:out value="${kayttaja.tunnus }"></c:out></a></li>
							<li><a href="<c:url value='/login?logout=true'/>"><i class="material-icons navicon left">power_settings_new</i>Kirjaudu
									ulos</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="<c:url value='/login'/>">Kirjaudu sisään</a></li>
						</c:otherwise>
					</c:choose>
			</ul>
		</div>
	</nav>
</div>
<div class="container" id="main-container">

	<c:if test="${empty kayttaja }">
		<div id="loginmodal" class="modal loginmodaali">
			<div class="modal-content center-align">
				<form method="post" action="<c:url value='/login'/>">
					<h4>Kirjaudu sisään</h4>
					<div class="row">
						<div class="input-field col s12">
							<input type="email" name="kayttajanimi" class="validate"
								id="kayttajanimi-modal" autocomplete="off"> <label
								for="kayttajanimi-modal" data-error="Virheellinen tunnus">Sähköpostiosoite</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<input type="password" name="salasana" id="salasana-modal">
							<label for="salasana-modal">Salasana</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<a href="#!"
								class="modal-action modal-close waves-effect waves-light btn red lighten-2">Peruuta</a>
							<button class="btn waves-effect waves-light" type="submit"
								name="action" value="login">Kirjaudu</button>
							<br> <br> <span class="pienifontti">Jos sinulla ei ole vielä käyttäjätiliä, voit rekisteröityä <a
								href="<c:url value='/login#rekisteroidy'/>">täältä</a>.
							</span>
						</div>
					</div>
				</form>
			</div>
		</div>
	</c:if>
	<div id="ostoskorimodal" class="modal">
		<div class="modal-content center-align">
			<h4>Ostoskori</h4>
			<h5 class="ostoskori-yhteismaara"></h5>
			<table class="ostoskori-table striped">
				<thead>
					<tr>
						<th>Tuotteen nimi</th>
						<th></th>
						<th class="center">Hinta</th>
					</tr>
				</thead>
				<tbody class="ostoskori-tbody">
				</tbody>
			</table>
			<div class="row">
				<div class="input-field col s12">
					<a href="#!"
						class="modal-action modal-close waves-effect waves-light btn red lighten-2 left"
						id="ostoskori-sulkunappi">Sulje</a>
					<button
						class="btn waves-effect waves-light orange lighten-1 center ostoskori-tyhjennysnappi"
						type="button" onClick="tyhjennaOstoskori()">
						<i class="material-icons left">clear</i>Tyhjennä
					</button>
					<c:if test="${not empty kayttaja }">
					<a class="btn waves-effect waves-light right ostoskori-tilausnappi"
						href="<c:url value='/tilaus'/>"><i
						class="material-icons left">shopping_cart</i>Tilaamaan</a>
					</c:if>
					<c:if test="${empty kayttaja }">
					<a class="btn waves-effect waves-light right ostoskori-tilausnappi right"
						href="<c:url value='/login?tilaukseen=true'/>"><i
						class="material-icons left">shopping_cart</i>Kirjaudu sisään tilataksesi</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
