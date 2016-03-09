<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Pizzojen muokkaus</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="row">
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
					<div class="col s12">
					<br><br>
						<a class="btn waves-effect waves-light btn-large red lighten-2"
							href="${pathi }/hallinta">Peruuta</a>
						<button class="btn waves-effect waves-light btn-large"
							type="submit" name="action" value="paivitatayte">Päivitä
							tiedot</button>
					</div>
				</div>
			</form>
		</div>
		<div class="col s12 m12 l5 offset-l1">
			<c:choose>
				<c:when test="${empty pizzat }">
					<h3>Yksikään pizza ei käytä tätä täytettä</h3>
				</c:when>
				<c:otherwise>
					<c:set var="pizzasana" value="Pizzaa"></c:set>
					<c:if test="${fn:length(pizzat) == 1 }">
						<c:set var="pizzasana" value="Pizza"></c:set>
					</c:if>
					<h3><c:out value="${fn:length(pizzat) } ${pizzasana } käyttää tätä täytettä"></c:out></h3>
					<table class="striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Nimi</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pizzat}" var="pizza">
								<tr>
									<td class="strong-id">${pizza.id }</td>
									<td>${pizza.nimi }</td>
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