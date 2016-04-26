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
			<div class="col s12 m6 center-align">
				<h2>Tilaushistoria</h2>
				
				<c:choose>
				<c:when test="${empty tilaushistoria }">Sinulla ei ole vielä tilauksia</c:when>
				<c:otherwise>
				<table class="bordered">
				<thead>
				<tr>
				<th class="center">Tilausnumero</th>
				<th>Tilausajankohta</th>
				<th class="center">Hinta</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${tilaushistoria }" var="tilaus">
				<tr class="taulukkorivi">
				<td class="center"><c:out value="${tilaus.tilausid }"></c:out></td>
				<td><fmt:formatDate value="${tilaus.tilaushetki }" pattern="dd.MM.yyyy HH:mm"/></td>
				<td class="center">
				<c:set var="hinta"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${tilaus.kokonaishinta }"></fmt:formatNumber></c:set>
				${fn:replace(hinta, ".", ",") } €
				</td>
				</tr>
				</c:forEach>
				</tbody>
				</table>
				</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>