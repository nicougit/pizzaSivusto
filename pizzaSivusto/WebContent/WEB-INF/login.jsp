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
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
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
				<noscript>
					<span class="errori center-align">Rekisteröityminen ja
						sisään kirjautuminen vaatii selaimessasi JavaScriptin toimiakseen.</span>
				</noscript>
				<script src="js/login-react.js" type="text/babel"></script>
			</div>
			<div class="col s12 m12 l6 pull-l5">
				<div class="row center-align">
					<h3>Kirjaudu sisään tai rekisteröidy asiakkaaksi</h3>
					<p class="logintitle">
						Parhaiden tuotteiden rinnalla tahdomme tarjota asiakkaillemme
						parasta mahdollista palvelua,<br> joten tarjoamme
						asiakkaillemme mahdollisuuden rekisteröityä. Rekisteröityminen
						mahdollistaa seuraavat henkilökohtaisemmat edut:
					</p>
				</div>
				<div class="row">
					<p class="loginkuvaus">
						<b>Tuotteiden tilaaminen verkkokaupan kautta.</b><br>
						<i>Tilatun tuotteen voimme toimittaa kotiinkuljetuksena,
							tuotteen voit tulla noutamaan ravintolastamme tai halutessanne
							voitte jäädä aterioimaan ravintolaan. <br>
						</i> <br><b>Useat maksutavat verkkokaupan tilauksiin.</b><br>
						<i>Käteinen, luottokortti ja verkkomaksu. </i><br> <br>
						<b>Mahdollisuus merkitä pizzoja suosikeiksi.</b><br>
						<i>Suosikit näkyvät omalla profiilisivulla, tämä mahdollistaa nopean verkkokauppatilauksen ilman
							täydellisen Menun selaamista.</i><br> 
						<br><b>Tilaushistoria.</b><br>
						<i>Jos vaikka unohdat mikä oli se herkullinen pizza kaksi
							kuukautta sitten ja haluaisit tänään samanlaisen.</i><br> <br>
						<b>Useiden toimitusosotteiden tallennus.</b><br>
						<i>Koti, työpaikka, vanhempien kesämökki, kotiinkuljetus
							helposti ilman osoitteen selvittelyä.</i>
					</p>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>