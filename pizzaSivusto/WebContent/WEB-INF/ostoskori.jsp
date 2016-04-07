<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Ostoskori</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="headertext">
		<h1>Ostoskori</h1>
		<p class="flow-text">
			<c:choose>
				<c:when test="${fn:length(pizzat) == 0 }">
		Ostoskorisi on tyhjä
		</c:when>
				<c:otherwise>
				Ostoskorissa yhteensä ${fn:length(pizzat) } pizzaa
		</c:otherwise>
			</c:choose>
		</p>
	</div>
	<div class="row" id="main-content">
		<h2>Ostoskorin sisältö</h2>
		<c:choose>
			<c:when test="${fn:length(pizzat) == 0 }">
				<p class="center-align"> Ostoskorisi on tyhjä. Lisää
					tuotteita ostoskoriin menustamme!</p>
			</c:when>
			<c:otherwise>
				<table>
					<thead>
						<tr>
							<td>Nimi</td>
							<td>Hinta</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pizzat}" var="pizza">
							<tr>
								<td>${pizza.nimi }</td>
								<td><c:set var="pizzahinta">
										<fmt:formatNumber type="number" minFractionDigits="2"
											maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber>
									</c:set> ${fn:replace(pizzahinta, ".", ",") } €</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</c:otherwise>
		</c:choose>

	</div>


	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>