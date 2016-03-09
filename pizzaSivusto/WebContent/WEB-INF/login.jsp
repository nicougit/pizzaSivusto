<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Kirjautuminen</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<h1 class="firmanlogo hide-on-med-and-down">Castello é Fiori</h1>
	<div class="center-align">
		<p class="flow-text">Olemme pieni 1950-luvulla toimintansa
			aloittanut Hämeenlinnassa toimiva perheyritys. Viime vuosina olemme
			erikoistunut pizzoihin. Panostamme laatuun, joten hankimme kaikki
			raaka-aineemme tarkkaan harkituilta toimittajilta.</p>
	</div>
	<div class="divider"></div>

	<div class="row">
		<div class="col s12 m12 l5 push-l7">
			<div class="row">
				<div class="col s12">
					<br>
					<ul class="tabs">
						<li class="tab col s6"><a href="#kirjaudusisaan"
							class="active">Kirjaudu</a></li>
						<li class="tab col s6"><a href="#rekisteroidy">Rekisteröidy</a></li>
					</ul>
				</div>
			</div>
			<div class="row" id="kirjaudusisaan">
				<form method="post" action="login">
					<h2>Kirjaudu sisään</h2>
					<div class="row">
						<div class="input-field col s12">
							<input type="email" name="kayttajanimi" class="validate"
								id="kayttajanimi" autocomplete="off"> <label
								for="kayttajanimi" data-error="Virheellinen tunnus">Sähköpostiosoite</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<input type="password" name="salasana" id="salasana"> <label
								for="salasana">Salasana</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<button class="btn waves-effect waves-light" type="submit"
								name="action" value="login">Kirjaudu</button>
						</div>
					</div>
				</form>
			</div>
			<div class="row" id="rekisteroidy">
				<h2>Rekisteröidy</h2>
				<form action="login" method="post">
					<div class="row">
						<div class="input-field col s12">
							<input type="email" id="kayttajatunnus" name="kayttajatunnus"
								class="validate" required title="Sähköpostiosoite"> <label
								for="kayttajatunnus" data-error="Virhe">Sähköpostiosoite</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s6">
							<input type="password" id="salasana-rek" name="salasana-rek"
								class="validate" pattern=".{6,}" required
								title="Salasana - vähintään 6 merkkiä"> <label
								for="salasana-rek" data-error="Lyhyt">Salasana</label>
						</div>
						<div class="input-field col s6">
							<input type="password" id="salasana-rek2" name="salasana-rek2"
								class="validate" pattern=".{6,}" required
								title="Salasana uudelleen - vähintään 6 merkkiä"> <label
								for="salasana-rek2" data-error="Lyhyt">Salasana
								uudelleen</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s6">
							<input type="text" id="etunimi" name="etunimi" class="validate"
								pattern="[\wäöå-+]{2,}" required title="Etunimi"> <label
								for="etunimi" data-error="Virhe">Etunimi</label>
						</div>
						<div class="input-field col s6">
							<input type="text" id="sukunimi" name="sukunimi" class="validate"
								pattern="[\wäöå-+]{2,}" required title="Sukunimi"> <label
								for="sukunimi" data-error="Virhe">Sukunimi</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<input type="tel" id="puhelinnro" name="puhelinnro"
								class="validate" pattern=".{0}||[\d]{8,12}"
								title="Puhelinnumero - ilman välimerkkejä"> <label
								for="puhelinnro" data-error="Virhe">Puhelinnumero</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<button class="btn waves-effect waves-light" type="submit"
								name="action" value="rekisteroidy">Rekisteröidy</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="col s12 m12 l6 pull-l5">
			<div class="row center-align">
				<h2>Uudet käyttäjät</h2>
				Tässä näytetään ainakin kehitysvaiheessa uusimmat 10
				rekisteröitynyttä käyttäjää. user@pizza.fi, staff@pizza.fi ja
				admin@pizza.fi tilien salasanat on joko salasana tai salasana123.<br>
				<br>
			</div>
			<c:choose>
				<c:when test="${empty kayttajat }">
					<div class="row center-align errori">Tietokannassa ei ole
						käyttäjiä, tai niitä noutaessa tapahtui virhe.</div>
				</c:when>
				<c:otherwise>
					<div class="row">
						<table class="striped">
							<thead>
								<tr>
									<th>ID</th>
									<th>Login</th>
									<th>Tyyppi</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${kayttajat}" var="kayttaja">
									<tr>
										<td class="strong-id">${kayttaja.id }.</td>
										<td>${kayttaja.tunnus }</td>
										<td>${kayttaja.tyyppi }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>