<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Hallintasivu</title>
</head>
<body style="width: 90%;">

	<h1>Castello E Fiori</h1>

	Tällä sivuilla tehdään pizzojen sekä täytteiden lisäys, poisto ja
	muokkaus. Tällä hetkellä vain listaus toimii.
	<br>
	<br>

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

	<h2>Pizzojen hallinnointi</h2>
	<form action="hallinta" method="post">
		<table style="width: 45%;">
			<tr>
				<th style="text-align: left;">Pizzan tiedot</th>
				<th>Hinta</th>
				<th>Toiminnot</th>
			</tr>
			<c:forEach items="${pizzat}" var="pizza">
				<tr>
					<td>${pizza.nimi }</td>
					<td style="text-align: center;"><fmt:formatNumber
							type="number" minFractionDigits="2" maxFractionDigits="2"
							value="${pizza.hinta }"></fmt:formatNumber> EUR</td>
					<td style="text-align: center;">
						<button name="muokkaapizzaa" type="submit" value="${pizza.id }">Muokkaa</button>
						<button name="poistapizza" type="submit" value="${pizza.id }">Poista</button>
					</td>
				</tr>
				<tr>
					<td colspan="3"
						style="font-size: 9pt; padding-bottom: 5px; padding-left: 10px; border-bottom: 1px solid lightgray;">
						${pizza.taytteet }</td>
				</tr>
			</c:forEach>
		</table>
		<br>
	</form>

	<h2>Pizzan lisäys</h2>
	<form action="hallinta" method="post">
		<table style="width: 45%;">
			<tr>
				<td>Pizzan nimi</td>
				<td><input type="text" name="nimi" autocomplete="off"></td>
			</tr>
			<tr>
				<td>Pizzan hinta</td>
				<td><input type="number" autocomplete="off"> EUR</td>
			</tr>
			<tr>
				<td>Täytteet</td>
				<td><input type="text" name="nimi" autocomplete="off" style="width: 100%;"></td>
			</tr>
			<tr>
				<td><input type="submit" name="action" value="Lisää pizza"></td>
				<td style="font-size: 8pt;">Kirjoita täytteet pilkulla ja välilyönnillä toisistaan eroteltuna. Esim. Kinkku, Kebab, Ananas</td>
			</tr>

		</table>

	</form>

	<h2>Täytteiden hallinnointi</h2>
	<form action="hallinta" method="post">
		<table style="width: 45%;">
			<tr>
				<th style="text-align: left;">Täyte</th>
				<th>Saatavilla</th>
				<th>Hallinta</th>
			</tr>
			<c:forEach items="${taytteet}" var="tayte">
				<tr>
					<td>${tayte.nimi }</td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${tayte.saatavilla == true }">Kyllä</c:when>
							<c:otherwise>
								<span style="color: red;">Ei</span>
							</c:otherwise>
						</c:choose></td>
					<td style="text-align: center;">
						<button name="muokkaataytetta" type="submit" value="${tayte.id }">Muokkaa</button>
						<button name="poistatayte" type="submit" value="${tayte.id }">Poista</button>
					</td>
				</tr>
			</c:forEach>
		</table>
		<br>
	</form>

	<h2>Täytteiden lisäys</h2>

	<form action="hallinta" method="post">
		<table style="width: 45%;">
			<tr>
				<td>Täytteen nimi</td>
				<td><input type="text" name="nimi" autocomplete="off"></td>
			</tr>
			<tr>
				<td>Saatavilla</td>
				<td><select><option value="1">Kyllä</option>
						<option value="0">Ei</option></select>
			</tr>
			<tr>
			<td colspan="2"><input type="submit" name="action" value="Lisää täyte">
			</td>
			</tr>
		</table>
	</form>
	
	<br><br>

	<div
		style="border-top: 1px solid lightgray; width: 600px; font-size: 9pt;">
		<br>By Reptile Mafia 2016
	</div>
</body>
</html>