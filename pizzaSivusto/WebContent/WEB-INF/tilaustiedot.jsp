<%@page import="fi.softala.pizzeria.bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Tilauksen tiedot</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="headertext">
		<h1>Tilauksen tiedot</h1>
		<p class="flow-text"></p>
		<br>
	</div>

	<div class="row" id="main-content">
		<div class="col s12">
			<h2>
				Tilaus #
				<c:if test="${not empty tilaus }">
					<c:out value="${tilaus.tilausid }"></c:out>
				</c:if>
			</h2>
			<c:if
				test="${not empty param.tilaus && param.tilaus == \"success\" }">
				<script type="text/javascript">
					naytaSuccess('Tilauksesi on lähetetty!');
				</script>
				<p class="center-align">Tilauksesi lähetettiin onnistuneesti!</p>
			</c:if>
		</div>

		<div class="col s12 m12 l5">
			<div class="row">
				<div class="col s12 m6 l6">
					<h5 class="tilaustitle">Tilaaja</h5>
					<c:out value="${kayttaja.etunimi } ${kayttaja.sukunimi }"></c:out>
					<br>
					<c:out value="${kayttaja.tunnus }"></c:out>
					<c:if test="${not empty kayttaja.puhelin }">
						<br>
						<c:out value="${kayttaja.puhelin }"></c:out>
					</c:if>
				</div>
				<div class="col s12 m6 l6">
					<h5 class="tilaustitle">Toimitustapa</h5>
					<c:out value="${tilaus.toimitustapa }"></c:out>
				</div>
				<div class="col s12 m6 l6">
					<h5 class="tilaustitle">Maksutapa</h5>
					<c:out value="${tilaus.maksutapa }"></c:out>
				</div>
				<c:if test="${not empty tilaus.osoite }">
					<div class="col s12 m6 l6">
						<h5 class="tilaustitle">Toimitusosoite</h5>
						<c:out value="${tilaus.osoite.osoite }"></c:out>
						<br>
						<c:out value="${tilaus.osoite.postinro }"></c:out>
						<br>
						<c:out value="${tilaus.osoite.postitmp }"></c:out>
					</div>
				</c:if>
				<c:if test="${not empty tilaus.lisatiedot }">
				<div class="col s12">
					<h5 class="tilaustitle">Lisätiedot</h5>
					<c:out value="${tilaus.lisatiedot }"></c:out>
					</div>
				</c:if>
			</div>
		</div>

		<div class="col s12 m12 l6 offset-l1">
			<h3>Tilatut tuotteet</h3>
			<c:set var="yhteishinta" value="0"></c:set>
			<table class="bordered">
				<thead>
					<tr class="strong-id">
						<td>Tuote</td>
						<td class="center">Hinta</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tilaus.pizzat }" var="pizza">
						<c:set var="yhteishinta" value="${pizza.hinta + yhteishinta }"></c:set>
						<tr>
							<td><span class="strong-id"><c:out
										value="${pizza.nimi }"></c:out></span><br> <c:if
									test="${not empty pizza.lisatiedot }">
		Lisätiedot:
		<c:forEach items="${pizza.lisatiedot }" var="lisatieto"
										varStatus="lisatietostatus">
										<c:out value="${lisatieto }"></c:out>
										<c:if
											test="${fn:length(pizza.lisatiedot) > lisatietostatus.count}">
											<c:out value=", "></c:out>
										</c:if>
									</c:forEach>
								</c:if></td>
							<td class="center"><c:set var="hinta">
									<fmt:formatNumber type="number" minFractionDigits="2"
										maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber>
								</c:set> ${fn:replace(hinta, ".", ",") } €</td>
						</tr>
					</c:forEach>
					<c:forEach items="${tilaus.juomat }" var="juoma">
						<c:set var="yhteishinta" value="${juoma.hinta + yhteishinta }"></c:set>
						<tr>
							<td><span class="strong-id"><c:out
										value="${juoma.nimi }, ${juoma.koko }l"></c:out></span></td>
							<td class="center"><c:set var="hinta">
									<fmt:formatNumber type="number" minFractionDigits="2"
										maxFractionDigits="2" value="${juoma.hinta }"></fmt:formatNumber>
								</c:set> ${fn:replace(hinta, ".", ",") } €</td>
						</tr>
					</c:forEach>
					<tr class="tilausrivi tilaus-nobottom">
						<td class="right-align">Tuotteiden hinta yhteensä</td>
						<td class="center"><c:set var="hinta">
								<fmt:formatNumber type="number" minFractionDigits="2"
									maxFractionDigits="2" value="${yhteishinta }"></fmt:formatNumber>
							</c:set> ${fn:replace(hinta, ".", ",") } €</td>
					</tr>
					<c:if test="${tilaus.toimitustapa == 'Kotiinkuljetus' }">
						<tr class="tilausrivi tilaus-nobottom">
							<td class="right-align">Kuljetusmaksu</td>
							<td class="center"><c:set var="hinta">
									<fmt:formatNumber type="number" minFractionDigits="2"
										maxFractionDigits="2"
										value="${tilaus.kokonaishinta - yhteishinta }"></fmt:formatNumber>
								</c:set> ${fn:replace(hinta, ".", ",") } €</td>
						</tr>
					</c:if>
					<tr class="tilausrivi tilaus-nobottom pienifontti">
						<td class="right-align">Sisältää arvonlisäveroa (alv. 13,00%)</td>
						<td class="center"><c:set var="hinta">
								<fmt:formatNumber type="number" minFractionDigits="2"
									maxFractionDigits="2" value="${tilaus.kokonaishinta * 0.13 }"></fmt:formatNumber>
							</c:set> ${fn:replace(hinta, ".", ",") } €</td>
					</tr>
					<tr class="tilausrivi tilaus-nobottom pienifontti">
						<td class="right-align">Arvonlisäveroton hinta</td>
						<td class="center"><c:set var="hinta">
								<fmt:formatNumber type="number" minFractionDigits="2"
									maxFractionDigits="2" value="${tilaus.kokonaishinta * 0.87 }"></fmt:formatNumber>
							</c:set> ${fn:replace(hinta, ".", ",") } €</td>
					</tr>
					<tr class="taulukkorivi tilaus-nobottom">
						<td class="right-align strong-id">Tilauksen loppusumma</td>
						<td class="center strong-id"><c:set var="hinta">
								<fmt:formatNumber type="number" minFractionDigits="2"
									maxFractionDigits="2" value="${tilaus.kokonaishinta }"></fmt:formatNumber>
							</c:set> ${fn:replace(hinta, ".", ",") } €</td>
					</tr>
				</tbody>
			</table>
		</div>
	<a class="btn waves-effect waves-light left" href="<c:url value='kayttaja'/>">Palaa profiiliin</a>

	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>