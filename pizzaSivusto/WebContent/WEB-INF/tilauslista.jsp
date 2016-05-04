<%@page import="fi.softala.pizzeria.login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Tilauslista</title>
<jsp:include page="head-include.jsp"></jsp:include>
<script src="build/react.js"></script>
<script src="build/react-dom.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="row headertext">
	<h1>Tilauslista</h1>
		<p class="flow-text">Tällä sivulla näkyy kaikki tilausjärjestelmässä olevat tilaukset.</p>
	</div>
<div id="main-content">
	<div class="row">
		<div class="col s12 m12" id="tilaussisalto">
		&nbsp;
		<div id="loginsisalto"></div>
		<noscript><span class="errori center-align">Tämä sivu vaatii JavaScriptin toimiakseen.</span></noscript>
		<script src="js/tilaukset-react.js" type="text/babel"></script>
		</div>
	</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>