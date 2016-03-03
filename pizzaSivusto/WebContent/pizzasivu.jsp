<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Pizzojen muokkaus</title>
</head>
<body style="width: 100%;">

	<h1>Castello E Fiori</h1>

	<%
		// Katsotaan onko käyttäjä kirjautunut sisään
		if (session.getAttribute("kayttaja") != null) {
			int id = ((Kayttaja) session.getAttribute("kayttaja")).getId();
			String etunimi = ((Kayttaja) session.getAttribute("kayttaja")).getEtunimi();
			String sukunimi = ((Kayttaja) session.getAttribute("kayttaja")).getSukunimi();
			String tunnus = ((Kayttaja) session.getAttribute("kayttaja")).getTunnus();

			out.print("Olet kirjautunut sisään käyttäjänä " + tunnus + ".");

			// Varmaan ois selvempikin tapa tehdä tämä, mut toimii
			String logoutbutton = "<form action=\"" + request.getAttribute("pathi")
					+ "/login\" method=\"post\">\n<input type=\"submit\" name=\"action\" value=\"Kirjaudu ulos\"></form>";

			// Näytetään logout button kirjautuneille
			out.print("<br><br>" + logoutbutton);
		} else {
			out.print("Et ole kirjautuneena sisään. Pääset kirjautumaan sisään <a href=\""
					+ request.getAttribute("pathi") + "/login\">täältä</a>.");
		}
	%>
	<br>

	<%
		if (request.getAttribute("virhe") != null) {
			out.println("<span style=\"color: red;\">" + request.getAttribute("virhe") + "</span><br><br>");
		}
		else if (request.getAttribute("success") != null) {
			out.println("<span style=\"color: green;\">" + request.getAttribute("success") + "</span><br><br>");
		}
	%>

	<a href="<%out.print(request.getAttribute("pathi"));%>/hallinta">Takaisin hallinnointisivulle</a>

	<br>
	<br>

	<footer
		style="border-top: 1px solid lightgray; width: 600px; font-size: 9pt;">
	<br>
	By Reptile Mafia 2016</footer>

</body>
</html>