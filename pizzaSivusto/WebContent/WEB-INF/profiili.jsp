<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Tervetuloa</title>
<jsp:include page="head-include.jsp"></jsp:include>
<script src="build/react.js"></script>
<script src="build/react-dom.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="headertext">
			<h1>
				<c:out value="${kayttaja.etunimi } ${kayttaja.sukunimi }"></c:out>
			</h1>
			<p class="flow-text">Tervetuloa profiilisivullesi!</p>
			<br>
	</div>

	<div class="row" id="main-content">
		<div class="row">
			<div class="col s12 m6 center-align">
				<h2>Suosikkipizzasi</h2>
				Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
				<br><br>
				Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
			</div>
			<div class="col s12 m6 center-align" id="tilaushistoriadiv">
			<noscript>
			Tilaushistoriaa ei voida ladata ilman JavaScriptiä!
			</noscript>
			</div>
		</div>
	</div>
	
	<script src="js/tilaushistoria.js" type="text/babel"></script>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>