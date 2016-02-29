<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori - Kirjautuminen onnistui!</title>
</head>
<body style="width: 100%;">

	<h1>Castello E Fiori</h1>

	<%
		int id = ((Kayttaja) session.getAttribute("kayttaja")).getId();
		String etunimi = ((Kayttaja) session.getAttribute("kayttaja")).getEtunimi();
		String sukunimi = ((Kayttaja) session.getAttribute("kayttaja")).getSukunimi();
		String tunnus = ((Kayttaja) session.getAttribute("kayttaja")).getTunnus();

		out.print("Onneksi olkoon " + etunimi + " " + sukunimi
				+ ", onnistuit kirjautumaan sisään käyttäjätunnuksellasi " + tunnus + "!");
	%>

	<br><br>
	<a href="#">Placeholder linkki hallinnointisivulle</a>
	<br>
	<br>
	<a href="/pizzaSivusto/login">Placeholder logout linkki</a>
	<br><br>

	<footer
		style="border-top: 1px solid lightgray; width: 600px; font-size: 9pt;">
	<br>
	By Reptile Mafia 2016</footer>


</body>
</html>