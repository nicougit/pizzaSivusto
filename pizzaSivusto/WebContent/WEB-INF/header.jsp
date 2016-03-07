<%@page import="apuluokka.DeployAsetukset"%>
<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pathi" value="/pizzaSivusto"></c:set>
<div class="navbar-fixed">
	<c:if test="${not empty kayttaja }">
		<ul id="user-dropdowni" class="dropdown-content">
			<li><a href="#!">Profiili</a></li>
			<li><a href="#!">Tilaushistoria</a></li>
			<li class="divider"></li>
			<li><a href="${pathi }/login?logout=true">Kirjaudu ulos</a></li>
		</ul>
	</c:if>
	<nav>
		<div class="nav-wrapper">
			<a href="${pathi }" class="brand-logo">Castello é Fiori</a>
			<ul class="right hide-on-med-and-down">
				<li><a href="#!">Pizzat</a></li>
				<li><a href="#!">Yrityksemme</a></li>
				<li><a href="#!">Ostoskori (0)</a> <c:choose>
						<c:when
							test="${kayttaja.tyyppi == 'admin' || kayttaja.tyyppi == 'staff' }">
							<li class="linkki"><a href="${pathi }/hallinta">Hallinta</a></li>
						</c:when>
					</c:choose> <c:choose>
						<c:when test="${not empty kayttaja }">
							<li><a class="dropdown-button" href="#!"
								data-activates="user-dropdowni"> ${kayttaja.tunnus } <i
									class="material-icons right">arrow_drop_down</i></a></li>
						</c:when>
						<c:otherwise>
							<li>
							<li><a href="${pathi }/login">Kirjaudu sisään</a></li>
						</c:otherwise>
					</c:choose>
			</ul>
		</div>
	</nav>
</div>
<div class="container" id="main-container">