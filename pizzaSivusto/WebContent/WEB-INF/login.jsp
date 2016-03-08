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
	<h1 class="firmanlogo">Castello é Fiori</h1>
	<div class="center-align">
		<p class="flow-text">Olemme pieni 1950-luvulla toimintansa
			aloittanut Hämeenlinnassa toimiva perheyritys. Viime vuosina olemme
			erikoistunut pizzoihin. Panostamme laatuun, joten hankimme kaikki
			raaka-aineemme tarkkaan harkituilta toimittajilta.</p>
	</div>
	<div class="divider"></div>

	<div class="row">
		<div class="col s6">
			<h2>Rekisteröityneet käyttäjät</h2>
			Näytetään kehitysvaiheessa kaikki tietokannassa olevat käyttäjät.
			Salasanat käyttäjille on salasana123 tai salasana. <br> <br>
			<table class="striped">
				<thead>
					<tr>
						<th>ID</th>
						<th>Login</th>
						<th>Nimi</th>
						<th>Tyyppi</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${kayttajat}" var="kayttaja">
						<tr>
							<td class="strong-id">${kayttaja.id }</td>
							<td>${kayttaja.tunnus }</td>
							<td><c:out
									value="${kayttaja.etunimi } ${kayttaja.sukunimi }"></c:out></td>
							<td>${kayttaja.tyyppi }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
		</div>

		<div class="col s5 offset-s1">
			<div class="row">
				<div class="col s12">
					<br>
					<ul class="tabs">
						<li class="tab col s6"><a href="#kirjaudusisaan"
							class="active">Kirjaudu sisään</a></li>
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
						<button class="btn waves-effect waves-light" type="submit"
							name="action" value="login">Kirjaudu</button>
					</div>
					<c:if test="${not empty virhe }">
						<div class="row errori">${virhe }<br>
						</div>
					</c:if>
				</form>
				<div class="row">
					Ei käyttäjätiliä? Rekisteröidy <a href="#!">täältä</a>!
				</div>
			</div>
			<div class="row" id="rekisteroidy">
				<h2>Rekisteröidy</h2>
				<form action="login" method="post">
					<div class="row">
						<div class="input-field col s12">
							<input type="email" id="kayttajatunnus" name="kayttajatunnus"
								class="validate"> <label for="kayttajatunnus"
								data-error="Virheellinen muoto">Sähköpostiosoite</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<input type="password" id="salasana-rek" name="salasana-rek"
								class="validate"> <label for="salasana-rek"
								data-error="Syötä salasana">Salasana</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<input type="text" id="etunimi" name="etunimi" class="validate">
							<label for="etunimi" data-error="Syötä etunimi">Etunimi</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<input type="text" id="sukunimi" name="sukunimi" class="validate">
							<label for="sukunimi" data-error="Syötä sukunimi">Sukunimi</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<input type="text" id="puhelinnro" name="puhelinnro"> <label
								for="puhelinnro">Puhelinnumero</label>
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

	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>