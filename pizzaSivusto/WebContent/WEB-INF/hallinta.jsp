<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Hallintasivu</title>
<jsp:include page="head-include.jsp"></jsp:include>
<script>
	$(document).ready(function() {
		$('ul.tabs').tabs();
	});
</script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="row">
		<div class="col s10 offset-s1">
			<ul class="tabs">
				<li class="tab col s3"><a href="#pizza-h" class="active">Pizzojen
						hallinta</a></li>
				<li class="tab col s3"><a href="#pizza-l">Pizzan lisäys</a></li>
				<li class="tab col s3"><a href="#tayte-h">Täytteiden
						hallinta</a></li>
			</ul>
		</div>
	</div>

	<div class="row" id="pizza-h">
		<div class="col s12">
			<h2>Pizzat</h2>
			<table class="bordered">
				<thead>
					<tr>
						<th>#</th>
						<th>Pizzan tiedot</th>
						<th>Täytteet</th>
						<th>Hinta</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pizzat}" var="pizza">
						<c:choose>
							<c:when test="${pizza.poistomerkinta != null }">
								<tr class="red lighten-5">
							</c:when>
							<c:otherwise>
								<tr>
							</c:otherwise>
						</c:choose>
						<td class="strong-id">${pizza.id }.</td>
						<td>${pizza.nimi }<c:if
								test="${pizza.poistomerkinta != null }">
								<br>
								<span class="pienifontti"> Poistettu <fmt:parseDate
										value="${pizza.poistomerkinta}" var="parsittuDate"
										pattern="yyyy-MM-dd" /> <fmt:formatDate pattern="dd.MM.yyyy"
										value="${parsittuDate }" /></span>
							</c:if></td>
						<td class="pienifontti">${pizza.taytteet }</td>
						<td><fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber>
							EUR</td>
						<td><a class="waves-effect waves-light btn"
							href="?pizza-edit=${pizza.id }"><i class="material-icons">edit</i></a>
							<c:choose>
								<c:when test="${pizza.poistomerkinta == null}">
									<a class="waves-effect waves-light btn red lighten-2"
										href="?pizza-poista=${pizza.id }"> <i
										class="material-icons">delete</i>
									</a>
								</c:when>
								<c:otherwise>
									<a class="waves-effect waves-light btn teal lighten-3"
										href="?pizza-palauta=${pizza.id }"> <i
										class="material-icons">restore</i>
									</a>
								</c:otherwise>
							</c:choose></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div class="row" id="pizza-l">
		<div class="col s12">
			<h2>Pizzan lisäys tietokantaan</h2>
			<div class="row">
				<form action="hallinta" method="post">
					<div class="col s10 offset-s1">
						<div class="row">
							<div class="input-field col s9">
								<input type="text" name="pizzanimi" id="pizzanimi"
									autocomplete="off"> <label for="pizzanimi">Pizzan
									nimi</label>
							</div>
							<div class="input-field col s3">
								<input type="number" class="validate" min="0" step="0.05"
									name="pizzahinta" id="pizzahinta" autocomplete="off"><label
									for="pizzahinta" data-error="Virhe">Pizzan hinta</label>
							</div>
						</div>
						<div class="row" id="pizza-taytteet">
						<label id="taytteet-label">Täytteet</label>
							<table class="pizzataulu">
								<tr>
									<c:forEach items="${taytteet}" var="tayte"
										varStatus="loopCount">
										<c:if test="${loopCount.index % 4 == 0 && loopCount.index != 0}">
								</tr>
								<tr>
									</c:if>
									<td><input type="checkbox" id="${tayte.id }"
										 name="pizzatayte" value="${tayte.id }"
										<c:if test="${tayte.saatavilla == false }">disabled="disabled"</c:if>><label
										for="${tayte.id }">${tayte.nimi }</label></td>
										<c:if test="${fn:length(taytteet) == loopCount.count && fn:length(taytteet) % 4 != 0}"><td colspan="${4 - fn:length(taytteet) % 4 }"></td></c:if>
									</c:forEach>
									
								</tr>
							</table>
							<script src="js/tayte-input-limit.js"></script>
						</div>
						<div class="row">
							<div class="col s12">
								<button class="btn waves-effect waves-light btn-large"
									type="submit" name="action" value="lisaapizza">Lisää
									pizza</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>



	<div class="row" id="tayte-h">
		<div class="col s6">
			<h2>Täytteet</h2>
			<form action="hallinta" method="post">
				<table class="bordered">
					<thead>
						<tr>
							<th>#</th>
							<th>Täyte</th>
							<th>Saatavilla</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${taytteet}" var="tayte">
							<c:choose>
								<c:when test="${tayte.saatavilla == false }">
									<tr class="red lighten-5">
								</c:when>
								<c:otherwise>
									<tr>
								</c:otherwise>
							</c:choose>
							<td class="strong-id">${tayte.id }.</td>
							<td>${tayte.nimi }</td>
							<td><c:choose>
									<c:when test="${tayte.saatavilla == true }">Kyllä</c:when>
									<c:otherwise>
											Ei
										</c:otherwise>
								</c:choose></td>
							<td><a class="waves-effect waves-light btn"
								href="?tayte-edit=${tayte.id }"><i class="material-icons">edit</i></a>
								<a class="btn waves-effect waves-light red lighten-2 disabled"
								href="#!"> <i class="material-icons">delete</i>
							</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>
		</div>

		<div class="col s5 offset-s1">
			<h2>Lisää täyte</h2>
			<div class="row">
				<form action="hallinta" method="post">
					<div class="row">
						<div class="col s12">
							<input type="text" name="taytenimi" id="taytenimi"
								autocomplete="off" class="fieldi"> <label
								for="taytenimi">Täytteen nimi</label>
						</div>
					</div>
					<div class="row">
						<div class="col s6">
							<input name="taytesaatavilla" type="radio" id="saatavilla"
								value="1" checked> <label for="saatavilla">Saatavilla</label>
						</div>
						<div class="col s6">
							<input name="taytesaatavilla" type="radio" id="eisaatavilla"
								value="0"> <label for="eisaatavilla">Ei
								saatavilla</label>
						</div>
					</div>
					<div class="row">
						<div class="col s12">
							<button class="btn waves-effect waves-light btn-large"
								type="submit" name="action" value="lisaatayte">Lisää
								täyte</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>