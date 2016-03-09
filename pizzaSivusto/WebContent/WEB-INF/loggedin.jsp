<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori - Tervetuloa</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div id="row">
		<div class="col s12 center-align">
			<h1 class="firmanlogo hide-on-med-and-down">Castello é Fiori</h1>
			<h2>
				Tervetuloa takaisin,
				<c:out value="${kayttaja.etunimi } ${kayttaja.sukunimi }"></c:out>
				!
			</h2>
			<div class="divider"></div>
			<p class="flow-text">Olemme pieni 1950-luvulla toimintansa
				aloittanut Hämeenlinnassa toimiva perheyritys. Viime vuosina olemme
				erikoistunut pizzoihin. Panostamme laatuun, joten hankimme kaikki
				raaka-aineemme tarkkaan harkituilta toimittajilta.</p>

		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>