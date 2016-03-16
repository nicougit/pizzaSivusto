<%@page import="apuluokka.DeployAsetukset"%>
<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navbar-fixed">
	<c:if test="${not empty kayttaja }">
		<ul id="user-dropdowni" class="dropdown-content">
			<li><a href="#!">Profiili</a></li>
			<li><a href="#!">Tilaushistoria</a></li>
			<c:if
				test="${kayttaja.tyyppi == 'admin' || kayttaja.tyyppi == 'staff' }">

				<li><a href="<c:url value='/hallinta'/>">Hallinta</a></li>
			</c:if>
			<li class="divider"></li>
			<li><a href="<c:url value='/login?logout=true'/>">Kirjaudu
					ulos</a></li>
		</ul>
	</c:if>
	<nav>
		<div class="nav-wrapper">
			<a href="<c:url value='/login'/>" class="brand-logo">Castello é
				Fiori</a> <a href="#" data-activates="mobiili-nav"
				class="button-collapse"><i class="material-icons">menu</i></a>
			<ul class="right hide-on-med-and-down">
				<li><a href="#!">Pizzat</a></li>
				<li><a href="#!">Yrityksemme</a></li>
				<li><a href="#!"><i class="material-icons left">shopping_cart</i>
						Ostoskori (0)</a></li>
				<c:choose>
					<c:when test="${not empty kayttaja }">
						<li><a class="dropdown-button" href="#!"
							data-activates="user-dropdowni"> ${kayttaja.tunnus } <i
								class="material-icons right">arrow_drop_down</i></a></li>
					</c:when>
					<c:otherwise>
						<li><a class="modal-trigger" href="#loginmodal">Kirjaudu
								sisään</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
			<ul class="side-nav" id="mobiili-nav">
				<li><a href="#!">Pizzat</a></li>
				<li><a href="#!">Yrityksemme</a></li>
				<li><a href="#!">Ostoskori (0)</a> <c:choose>
						<c:when test="${not empty kayttaja }">
							<li class="divider"></li>
							<li><a href="#!">${kayttaja.tunnus }</a></li>
							<li><a href="#!">Tilaushistoria</a></li>
							<c:if
								test="${kayttaja.tyyppi == 'admin' || kayttaja.tyyppi == 'staff' }">
								<li><a href="<c:url value='/hallinta'/>">Hallinta</a></li>
							</c:if>
							<li><a href="<c:url value='/login?logout=true'/>">Kirjaudu
									ulos</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="<c:url value='/login'/>">Kirjaudu sisään</a></li>
						</c:otherwise>
					</c:choose>
			</ul>
		</div>
	</nav>
</div>
<div class="container" id="main-container">
	<c:if test="${empty kayttaja }">
		<div id="loginmodal" class="modal">
			<div class="modal-content center-align">
				<form method="post" action="login">
					<h4>Kirjaudu sisään</h4>
					<div class="row">
						<div class="input-field col s12">
							<input type="email" name="kayttajanimi" class="validate"
								id="kayttajanimi-modal" autocomplete="off"> <label
								for="kayttajanimi-modal" data-error="Virheellinen tunnus">Sähköpostiosoite</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<input type="password" name="salasana" id="salasana-modal"> <label
								for="salasana-modal">Salasana</label>
						</div>
					</div>
					<div class="row">
						<div class="input-field col s12">
							<a href="#!"
								class="modal-action modal-close waves-effect waves-light btn red lighten-2">Peruuta</a>
							<button class="btn waves-effect waves-light" type="submit"
								name="action" value="login">Kirjaudu</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</c:if>