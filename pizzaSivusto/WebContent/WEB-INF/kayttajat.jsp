<%@page import="fi.softala.pizzeria.login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Käyttäjät</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="headertext">
		<h1>Rekisteröityneet käyttäjät</h1>
	</div>
	<div class="row" id="main-content">
			<c:choose>
				<c:when test="${empty kayttajat }">
					<div class="row center-align errori">Tietokannassa ei ole
						käyttäjiä, tai niitä noutaessa tapahtui virhe.</div>
				</c:when>
				<c:otherwise>
					<div class="row">
						<table class="striped">
							<thead>
								<tr>
									<th>Tunnus</th>
									<th>Etunimi</th>
									<th>Sukunimi</th>
									<th class="hide-on-small-only">Tyyppi</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${kayttajat}" var="kayttaja">
									<tr>
										<td><c:out value="${kayttaja.tunnus }"></c:out></td>
										<td><c:out value="${kayttaja.etunimi }"></c:out></td>
										<td><c:out value="${kayttaja.sukunimi }"></c:out></td>
										<td class="hide-on-small-only"><c:out value="${kayttaja.tyyppi }"></c:out></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>

	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>