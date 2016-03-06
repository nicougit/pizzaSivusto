<%@page import="apuluokka.DeployAsetukset"%>
<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="navbar-fixed">
	<nav>
		<div class="nav-wrapper">
			<a href="/pizzaSivusto" class="brand-logo">Castello é Fiori</a>
			<ul class="right hide-on-med-and-down">
				<li><a href="#!">Pizzat</a></li>
				<li><a href="#!">Yrityksemme</a></li>
				<li><a href="#!">Ostoskori (0)</a>
				<%
					DeployAsetukset da = new DeployAsetukset();
					da.getPathi();
					// Katsotaan onko käyttäjä kirjautunut sisään
					if (session.getAttribute("kayttaja") != null) {
						int id = ((Kayttaja) session.getAttribute("kayttaja")).getId();
						String etunimi = ((Kayttaja) session.getAttribute("kayttaja")).getEtunimi();
						String sukunimi = ((Kayttaja) session.getAttribute("kayttaja")).getSukunimi();
						String tunnus = ((Kayttaja) session.getAttribute("kayttaja")).getTunnus();
						String tyyppi = ((Kayttaja) session.getAttribute("kayttaja")).getTyyppi();

						if (tyyppi.equals("admin") || tyyppi.equals("staff")) {
							out.print("<li><a href=\"" + da.getPathi() + "/hallinta\">Hallinta</a></li>");
						}

						out.print("<li id=\"username\"><form action=\"login\" method=\"post\"><button class=\"btn waves-effect waves-light\" type=\"submit\" name=\"action\" value=\"Kirjaudu ulos\">" + tunnus + " logout</button></form></li>");

					} else {
						out.print("<li><a href=\"" + da.getPathi() + "/login\">Kirjaudu sisään</a></li>");
					}
				%>
			</ul>
		</div>
	</nav>
	</div>
	<div class="container" id="main-container">