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

<div class="row headertext">
				<h1>${pizza.nimi }</h1>
				<p class="flow-text">${pizza.kuvaus }</p>
</div>
	<div class="row" id="main-content">
		<form action="hallinta" method="post">
			<div class="col s12">
			<h2>Muokkaa pizzaa</h2>
				<div class="row">
					<div class="col s12 m12 l10 offset-l1">
						<div class="row">
							<div class="input-field col s4 m2 l2">
								<input type="text" name="pizzaid" id="pizzaid"
									value="${pizza.id }" disabled> <label for="pizzaid">Pizzan
									ID</label>
							</div>
							<div class="input-field col s8 m7 l7">
								<input type="hidden" name=pizzaid value="${pizza.id }">
								<input type="text" name="pizzanimi" id="pizzanimi"
									autocomplete="off" value="${pizza.nimi }"> <label
									for="pizzanimi">Pizzan nimi</label>
							</div>
							<div class="input-field col s12 m3 l3">
								<input type="number" step="0.05" name="pizzahinta"
									class="validate" id="pizzahinta" min="0" autocomplete="off"
									value="${pizza.hinta }"> <label for="pizzahinta"
									data-error="Virhe">Pizzan hinta</label>
							</div>
							<div class="input-field col s12">
								<textarea class="materialize-textarea" name="pizzakuvaus" id="pizzakuvaus" length="255">${pizza.kuvaus }</textarea>
								<label for="pizzakuvaus">Pizzan kuvaus</label>
							</div>
						</div>
						<div class="row" id="pizza-taytteet">
							<label id="taytteet-label">Täytteet</label>
							<br><br>
							<div class="row">
								<c:forEach items="${taytteet}" var="tayte" varStatus="loopCount">
									<c:forEach items="${pizza.tayteIdt }" var="pizzantaytteet">
										<c:if test="${pizzantaytteet == tayte.id }">
											<c:set var="ontayte" value="1"></c:set>
										</c:if>
									</c:forEach>
									<div class="col s6 m4 l3 taytediv">
										<input type="checkbox" id="${tayte.id }" name="pizzatayte"
											value="${tayte.id }"
											<c:if test="${ontayte == 1 }"> checked</c:if>><label
											for="${tayte.id }"<c:if test="${tayte.saatavilla == false }"> class="errori-light"</c:if>>${tayte.nimi }</label>
									</div>
									<c:set var="ontayte" value="0"></c:set>
								</c:forEach>
							</div>
							<script src="js/tayte-input-limit.js"></script>
						</div>
						<div class="row">
							<a class="btn waves-effect waves-light btn-large red lighten-2"
								href="<c:url value='/hallinta'/>">Peruuta</a>
							<button class="btn waves-effect waves-light btn-large"
								type="submit" name="action" value="paivitapizza">Päivitä</button>

						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>