<%@page import="fi.softala.pizzeria.login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Kirjautuminen</title>
<jsp:include page="head-include.jsp"></jsp:include>
<script src="build/react.js"></script>
<script src="build/react-dom.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="row headertext">
	<h1>Tervetuloa!</h1>
		<p class="flow-text">Olemme pieni 1950-luvulla toimintansa
			aloittanut Hämeenlinnassa toimiva perheyritys. Viime vuosina olemme
			erikoistunut pizzoihin. Panostamme laatuun, joten hankimme kaikki
			raaka-aineemme tarkkaan harkituilta toimittajilta.</p>
	</div>
<div id="main-content">
	<div class="row">
		<div class="col s12 m12 l5 push-l7">
		&nbsp;
		<div id="loginsisalto"></div>
		<noscript><span class="errori center-align">Rekisteröityminen ja sisään kirjautuminen vaatii selaimessasi JavaScriptin toimiakseen.</span></noscript>
		<script src="js/login-react.js" type="text/babel"></script>
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
									<th class="hide-on-small-only">Tyyppi</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${kayttajat}" var="kayttaja">
									<tr>
										<td class="strong-id"><c:out value="${kayttaja.id }"></c:out>.</td>
										<td><c:out value="${kayttaja.tunnus }"></c:out></td>
										<td class="hide-on-small-only"><c:out value="${kayttaja.tyyppi }"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>