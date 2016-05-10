<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Kirjautuminen & Rekisteröityminen</title>
<jsp:include page="head-include.jsp"></jsp:include>
<script src="build/react.js"></script>
<script src="build/react-dom.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="row headertext">
		<h1>Rekisteröityminen ja kirjautuminen</h1>
		<p class="flow-text">Parhaiden tuotteiden rinnalla tahdomme tarjota asiakkaillemme
						parasta mahdollista palvelua, joten tarjoamme
						asiakkaillemme mahdollisuuden rekisteröityä. Reskiteröitymällä saat
						lukuisia henkilökohtaisia etuja!</p>
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
			<div class="col s12 m12 l6 pull-l5 center-align">
						<h5 class="login-title">Tuotteiden tilaaminen verkkokaupan kautta</h5>
						<span class="login-kuvaus">
						Tilatun tuotteen voimme toimittaa kotiinkuljetuksena,
							tuotteen voit tulla noutamaan ravintolastamme tai halutessanne
							voitte jäädä aterioimaan ravintolaan.</span>
							<br><br><div class="divider"></div>
						<h5 class="login-title">Useat maksutavat verkkokaupan tilauksiin</h5>
						<span class="login-kuvaus">Käteinen, luottokortti ja verkkomaksu.</span>
						<br><br><div class="divider"></div>
						<h5 class="login-title">Mahdollisuus merkitä pizzoja suosikeiksi</h5>
						<span class="login-kuvaus">Suosikit näkyvät omalla profiilisivulla, tämä mahdollistaa nopean verkkokauppatilauksen ilman
							täydellisen Menun selaamista.</span>
							<br><br><div class="divider"></div>
						<h5 class="login-title">Tilaushistoria</h5>
						<span class="login-kuvaus">Jos vaikka unohdat mikä oli se herkullinen pizza kaksi
							kuukautta sitten ja haluaisit tänään samanlaisen.</span>
							<br><br><div class="divider"></div>
						<h5 class="login-title">Useiden toimitusosotteiden tallennus</h5>
						<span class="login-kuvaus">Koti, työpaikka, vanhempien kesämökki, kotiinkuljetus helposti ilman osoitteen selvittelyä.</span>
				</div>
			</div>
		</div>
		
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>