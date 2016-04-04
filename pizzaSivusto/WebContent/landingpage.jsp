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
		<br> <a class="waves-effect waves-light btn-large"
			href="<c:url value='/pizza'/>"><i class="material-icons right">restaurant_menu</i>
			Menu</a> <br> <br>
	</div>
	<div class="row" id="main-content">
		<div class="col s12 l4 center-align">
			<h2>Bingo</h2>
			<p class="flow-text">Lorem ipsum dolor sit amet, consectetur
				adipiscing elit, sed do eiusmod tempor incididunt ut labore</p>
		</div>
		<div class="col s12 l4 center-align">
			<h2>Bango</h2>
			<p class="flow-text">At vero eos et accusamus et iusto odio
				dignissimos ducimus qui blanditiis praesentium voluptatum deleniti</p>
		</div>
		<div class="col s12 l4 center-align">
			<h2>Bongo</h2>
			<p class="flow-text">Nam libero tempore, cum soluta nobis est
				eligendi optio cumque nihil impedit quo minus id quod maxime placeat
				facere possimus</p>
		</div>
	</div>
	<jsp:include page="WEB-INF/footer.jsp"></jsp:include>
</body>
</html>
