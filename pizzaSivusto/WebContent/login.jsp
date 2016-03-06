<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Kirjautuminen</title>
<jsp:include page="WEB-INF/head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="WEB-INF/header.jsp"></jsp:include>
	<h1>Castello é Fiori</h1>
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
							<td>${kayttaja.etunimi }${kayttaja.sukunimi }</td>
							<td>${kayttaja.tyyppi }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
		</div>

		<div class="col s5 offset-s1">
			<h2>Kirjaudu sisään</h2>
			<div class="row">
				<form method="post">
					<div class="input-field col s12">
						<input type="email" name="kayttajanimi" class="validate"
							id="kayttajanimi" autocomplete="off"> <label
							for="kayttajanimi" data-error="Virheellinen tunnus">Sähköpostiosoitteesi</label>
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
					name="action" value="Kirjaudu">Kirjaudu</button>
				</form>
			</div>
			<c:if test="${not empty virhe }">
				<div class="row errori">${virhe }<br>
				</div>
			</c:if>
		</div>

	</div>

	<jsp:include page="WEB-INF/footer.jsp"></jsp:include>
</body>
</html>