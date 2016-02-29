<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Kirjautuminen</title>
</head>
<body style="width: 100%;">

	<h1>Castello E Fiori</h1>

	Tervetuloa sivuille, täältä pääset kirjautumaan sisään.
	<br>
	<br> Näytetään nyt kehitysvaiheessa kaikki tietokannassa olevat
	käyttäjät:
	<br>
	<br>

	<table
		style="text-align: center; width: 600px;">
		<tr>
			<th>ID</th>
			<th>Login</th>
			<th>Nimi</th>
			<th>Tyyppi</th>
		</tr>
		<c:forEach items="${kayttajat}" var="kayttaja">
			<tr>
				<td>${kayttaja.id }</td>
				<td>${kayttaja.tunnus }</td>
				<td>${kayttaja.etunimi }&nbsp;${kayttaja.sukunimi }</td>
				<td>${kayttaja.tyyppi }</td>
			</tr>
		</c:forEach>
	</table>
	<br>

	<h2>Sisäänkirjautuminen</h2>
	
	<c:if test="${not empty param.error }"><span style="color: red;">Virhe kirjautumisessa!</span><br></c:if>

	<table>
		<form method="post">
			<tr>
				<td>Käyttäjänimi (email)</td>
				<td><input type="text" name="kayttajanimi"></td>
			</tr>
			<tr>
				<td>Salasana</td>
				<td><input type="password" name="salasana"></td>
			</tr>
			<tr>
				<td><input type="submit" name="action" value="Kirjaudu"></td>
				<td></td>
			</tr>
		</form>
	</table>
	
	<br>
	
	<footer style="border-top: 1px solid lightgray; width: 600px; font-size: 9pt;"><br>By Reptile Mafia 2016</footer>
	
</body>
</html>