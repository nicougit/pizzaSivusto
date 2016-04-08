<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Tervetuloa</title>
<jsp:include page="WEB-INF/head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="WEB-INF/header.jsp"></jsp:include>
	<div class="headertext">
		<h1 class="firmanlogo hide-on-med-and-down">Castello é Fiori</h1>
		<p class="flow-text">Olemme Hämeenlinnalainen perheyritys, joka on
			aloittanut toimintansa jo 50-luvulla. Me Castello é Fiorissa haluamme
			tarjota jokaiselle jotain unohtumatonta. Taidolla, tunteella ja
			rakkaudella teemme Teille Hämeenlinnan maukkaimmat italialaiset
			pizzat.</p>
	</div>
	<div class="row">
		<div class="col s12 center-align">
			<a class="waves-effect waves-light btn-large"
				href="<c:url value='/pizza'/>"><i class="material-icons right">restaurant_menu</i>Menu
			</a>
		</div>
	</div>
	<br>
	<div class="row" id="main-content">
		<div class="col s12 l4 center-align">
			<h2>Aukioloajat</h2>
			<p class="flow-text">Arkisin 10 - 21 <br>Lauantaisin 10 - 22<br>Sunnuntaisin 12 - 21</p>
		</div>
		<div class="col s12 l4 center-align">
			<h2>Yhteystiedot</h2>
			<p class="flow-text">Raatihuoneenkatu 6<br> 13100 Hämeenlinna<br><br>Puhelin 023 231 2342</p>
		</div>
		<div class="col s12 l4 center-align">
			<h2>Kartta</h2>
			<p class="flow-text">Tähän karttakuva</p>
		</div>
	</div>
	</div>
	<div class="center-align pienifontti footeri">
	<br>
	By Reptile Mafia 2016
	<br><br>
	</div>
	</body>
</html>
