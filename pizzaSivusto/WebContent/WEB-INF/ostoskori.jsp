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
			<c:set var="yhteishinta" value="0"></c:set>
				<table class="striped">
					<thead>
						<tr class="strong-id">
							<td>Nimi</td>
							<td>Hinta</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pizzat}" var="pizza">
						<c:set var="yhteishinta">${(yhteishinta + pizza.hinta) }</c:set>
							<tr>
								<td>${pizza.nimi }</td>
								<td><c:set var="pizzahinta">
										<fmt:formatNumber type="number" minFractionDigits="2"
											maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber>
									</c:set> ${fn:replace(pizzahinta, ".", ",") } €</td>
									<td></td>
							</tr>
						</c:forEach>
						<c:set var="yhteishinta">
										<fmt:formatNumber type="number" minFractionDigits="2"
											maxFractionDigits="2" value="${yhteishinta }"></fmt:formatNumber>
											</c:set>
						<tr class="strong-id">
						<td class="right-align">Yhteishinta</td>
						<td>${fn:replace(yhteishinta, ".", ",") } €</td>
						<td></td>
						</tr>
					</tbody>
				</table>
				<div class="row">
				<div class="col s12 right-align">
				<br>
				<form method="post">
				<button class="btn waves-effect waves-light orange lighten-1" name="action" value="tyhjenna"><i class="material-icons left">clear</i>Tyhjennä</button> 
				<button class="btn waves-effect waves-light" type="button"><i class="material-icons left">shopping_cart</i>Tilaa</button>
				</form>
				</div>
				</div>
			</c:otherwise>
		</c:choose>

	</div>


	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>