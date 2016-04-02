<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Pizzojen muokkaus</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="row headertext">
	<h1>${tayte.nimi }</h1>
	</div>
	<div class="row" id="main-content">
		<div class="col s12 m12 l5">
			<form action="hallinta" method="post">
				<h2>Muokkaa täytettä</h2>
				<div class="row">
					<div class="input-field col s12">
						<input type="text" name="tayteid" id="tayteid"
							value="${tayte.id }" disabled> <label for="tayteid">Täytteen
							ID</label> <input type="hidden" name="tayteid" value="${tayte.id }">
					</div>
					<div class="input-field col s12">
						<input type="text" name="taytenimi" id="taytenimi"
							autocomplete="off" value="${tayte.nimi }"> <label
							for="taytenimi">Täytteen nimi</label>
					</div>
				</div>
				<div class="row">
					<div class="input-field col s6">
						<input name="taytesaatavilla" type="radio" id="saatavilla"
							value="1"
							<c:if test="${tayte.saatavilla == true }"> checked</c:if>>
						<label for="saatavilla">Saatavilla</label><br>
					</div>
					<div class="input-field col s6">
						<input name="taytesaatavilla" type="radio" id="eisaatavilla"
							value="0"
							<c:if test="${tayte.saatavilla == false }"> checked</c:if>>
						<label for="eisaatavilla">Ei saatavilla</label>
					</div>
				</div>
				<div class="row">
					<div class="col s12 center-align">
						<br> <br> <a
							class="btn waves-effect waves-light btn-large red lighten-2"
							href="<c:url value='/hallinta#tayte-h'/>">Peruuta</a>
						<button class="btn waves-effect waves-light btn-large"
							type="submit" name="action" value="paivitatayte">Päivitä</button>
						<c:if test="${kayttaja.tyyppi == 'admin' }">
							<br>
							<br>
							<a
								class="waves-effect waves-light btn modal-trigger red lighten-2 tooltipped"
								href="#poistomodal" data-position="bottom" data-delay="500"
								data-tooltip="Poistaa täytteen tietokannasta"><i
								class="material-icons left">delete</i> Poista täyte</a>
							<br>
							<br>
							<div id="poistomodal" class="modal">
								<div class="modal-content center-align">
									<h4>Oletko varma?</h4>
									<p class="center-align">
										Täyte '${tayte.nimi }' poistetaan tietokannasta pysyvästi.
										<c:if test="${fn:length(pizzat) == 1 }">
											<br>
											<br>${fn:length(pizzat) } Pizza käyttää edelleen tätä täytettä. Täyte poistuu tältä pizzalta.
										</c:if>
										<c:if test="${fn:length(pizzat) > 1 }">
											<br>
											<br>${fn:length(pizzat) } Pizzaa käyttää edelleen tätä täytettä. Täyte poistuu näiltä pizzoilta:<br>
											<br>
										</c:if>
										<c:if test="${not empty pizzat }">
											<c:forEach items="${pizzat }" var="pizza">
											${pizza.nimi }<br>
											</c:forEach>
										</c:if>
									</p>
									<br> <a href="#!"
										class="modal-action modal-close waves-effect waves-light btn red lighten-2">Peruuta</a>
									<a href="?poista-tayte=${tayte.id }"
										class="modal-action waves-effect waves-light btn"><i
										class="material-icons left">delete</i>Poista</a>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</form>
		</div>
		<div class="col s12 m12 l6 offset-l1">
			<c:choose>
				<c:when test="${empty pizzat }">
					<h3>Yksikään pizza ei käytä tätä täytettä</h3>
				</c:when>
				<c:otherwise>
					<c:set var="pizzasana" value="Pizzaa"></c:set>
					<c:if test="${fn:length(pizzat) == 1 }">
						<c:set var="pizzasana" value="Pizza"></c:set>
					</c:if>
					<h3>
						<c:out
							value="${fn:length(pizzat) } ${pizzasana } käyttää tätä täytettä"></c:out>
					</h3>
					<table class="bordered">
						<thead>
							<tr>
								<th>Pizzan nimi</th>
								<th class="center-align">Täytteitä</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pizzat}" var="pizza">
								<tr
									<c:if test="${pizza.poistomerkinta != null }"> class="red lighten-5"</c:if>>
									<td>${pizza.nimi }<c:if
											test="${pizza.poistomerkinta != null }">
											<span class="pienifontti right hide-on-small-only">Poistettu
												<fmt:parseDate value="${pizza.poistomerkinta}"
													var="parsittuDate" pattern="yyyy-MM-dd" /> <fmt:formatDate
													pattern="dd.MM.yyyy" value="${parsittuDate }" />
											</span>
										</c:if></td>
									<td class="center-align">${fn:length(pizza.taytteet) }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>

	</div>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>