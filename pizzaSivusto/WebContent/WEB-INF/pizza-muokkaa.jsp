<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Pizzojen muokkaus</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="row">
		<div class="col s10 offset-s1">

			<c:if test="${not empty virhe }">
				<h1>Virhe</h1>
				<p class="flow-text center-align" style="color: red;">${virhe }<br>
					<br> <a class="btn waves-effect waves-light btn-large"
						href="${pathi }/hallinta">Takaisin hallintasivulle</a>
				</p>
			</c:if>

			<c:if test="${not empty success }">
				<h1>Onnistui</h1>
				<p class="flow-text center-align" style="color: green;">${success }<br>
					<br> <a class="btn waves-effect waves-light btn-large"
						href="${pathi }/hallinta">Takaisin hallintasivulle</a>
				</p>
			</c:if>
		</div>
	</div>

	<div class="row">
		<div class="col s12">
			<h2>Muokkaa pizzaa</h2>
			<br>
			<div class="row">
				<form action="hallinta" method="post">
					<div class="col s10 offset-s1">
						<div class="row">
							<div class="input-field col s2">
								<input type="text" name="pizzaid" id="pizzaid"
									value="${pizza.id }" disabled> <label for="pizzaid">Pizzan
									ID</label>
							</div>
							<div class="input-field col s7">
								<input type="hidden" name=pizzaid value="${pizza.id }">
								<input type="text" name="pizzanimi" id="pizzanimi"
									autocomplete="off" value="${pizza.nimi }"> <label
									for="pizzanimi">Pizzan nimi</label>
							</div>
							<div class="input-field col s3">
								<input type="number" step="0.05" name="pizzahinta"
									class="validate" id="pizzahinta" min="0" autocomplete="off"
									value="${pizza.hinta }"> <label for="pizzahinta"
									data-error="Virhe">Pizzan hinta</label>
							</div>
						</div>
						<div class="row" id="pizza-taytteet">
							<table class="taytetaulu">
								<tr>
									<c:forEach items="${taytteet}" var="tayte"
										varStatus="loopCount">
										<c:forEach items="${pizza.tayteIdt }" var="pizzantaytteet">
											<c:if test="${pizzantaytteet == tayte.id }">
												<c:set var="ontayte" value="1"></c:set>
											</c:if>
										</c:forEach>
										<c:if test="${loopCount.index % 4 == 0 }">
								</tr>
								<tr>
									</c:if>
									<td><input type="checkbox" id="${tayte.id }"
										name="pizzatayte" value="${tayte.id }"
										<c:if test="${ontayte == 1 }"> checked</c:if>><label
										for="${tayte.id }">${tayte.nimi }</label></td>
									<c:set var="ontayte" value="0"></c:set>
									</c:forEach>
								</tr>
							</table>
							<script src="js/tayte-input-limit.js"></script>
						</div>
						<div class="row">
							<a class="btn waves-effect waves-light btn-large red lighten-2"
								href="${pathi }/hallinta">Peruuta</a>
							<button class="btn waves-effect waves-light btn-large"
								type="submit" name="action" value="paivitapizza">Päivitä
								tiedot</button>
				</form>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>