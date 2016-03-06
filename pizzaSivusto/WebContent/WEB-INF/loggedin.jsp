<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori - Kirjautuminen onnistui!</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<h1>
		Tervetuloa,
		<c:out value="${kayttaja.etunimi } ${kayttaja.sukunimi }"></c:out>
		!
	</h1>
	<%
		if (request.getAttribute("virhe") != null) {
			out.println("<span style=\"color: red;\">" + request.getAttribute("virhe") + "</span><br><br>");
		}
	%>
	<div class="center-align">
		<c:out value="${kayttaja.tunnus }, ${kayttaja.tyyppi }"></c:out>
		<p class="flow-text">Olemme pieni 1950-luvulla toimintansa
			aloittanut Hämeenlinnassa toimiva perheyritys. Viime vuosina olemme
			erikoistunut pizzoihin. Panostamme laatuun, joten hankimme kaikki
			raaka-aineemme tarkkaan harkituilta toimittajilta.</p>

		<div class="divider"></div>
		<br>Tässä voidaan näyttää käyttäjän tilaushistoriaa, joku hieno
		kuva, tai jotain muuta jännää. <br>

	</div>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>