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
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
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
			<div class="col s12 m5 center-align">
				<h2>Suosikkipizzat</h2>
				<c:choose>
					<c:when test="${empty suosikkipizzat }">
				Sinulla ei ole vielä suosikkipizzoja
				</c:when>
					<c:otherwise>
					    <table class="bordered">
					    <thead>
					    <tr>
					    <th></th>
					    <th>Pizza</th>
					    <th>Hinta</th>
					    <th></th>
					    </tr>
					    </thead>
					    <tbody>
						<c:forEach items="${suosikkipizzat }" var="pizza">
			<tr>
			<td class="center-align"><a href="kayttaja?poistasuosikki=${pizza.suosikkiid }" class="ostoskori-poistonappi tooltipped" data-position="left" data-delay="500" data-tooltip="Poista suosikeista"><i class="material-icons tiny">clear</i></a></td>
			<td><c:out value="${pizza.nimi }"></c:out></td>
			<td>
			<c:set var="hinta"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber></c:set>
			${fn:replace(hinta, ".", ",") } €
			</td>
			<td>
			<button class="btn waves-effect waves-light ostoskorinappi right tooltipped" type="button" onClick="lisaaOstoskoriin(<c:out value="${pizza.id}"></c:out>,'pizza')" data-position="right" data-delay="500" data-tooltip="Lisää ostoskoriin"><i
						class="material-icons">shopping_cart</i></button>
						</td>
			</tr>
						</c:forEach>
						</tbody>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="col s12 m6 offset-m1 center-align" id="tilaushistoriadiv">
				<noscript>Tilaushistoriaa ei voida ladata ilman
					JavaScriptiä!</noscript>
			</div>
		</div>
	</div>

	<script src="js/tilaushistoria.js" type="text/babel"></script>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>