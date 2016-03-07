<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
			<h2>Muokkaa täytettä</h2>
			<div class="row">
				<form action="hallinta" method="post">
					<div class="col s10 offset-s1">
						<div class="row">
							<div class="input-field col s2">
								<input type="text" name="tayteid" id="tayteid"
									value="${tayte.id }" disabled> <label for="tayteid">Täytteen
									ID</label>
									<input type="hidden" name="tayteid" value="${tayte.id }">
							</div>
							<div class="input-field col s4">
								<input type="text" name="taytenimi" id="taytenimi"
									autocomplete="off" value="${tayte.nimi }"> <label
									for="taytenimi">Täytteen nimi</label>
							</div>
							<div class="input-field col s2">
								<input name="taytesaatavilla" type="radio" id="saatavilla"
									value="1"
									<c:if test="${tayte.saatavilla == true }"> checked</c:if>>
								<label for="saatavilla">Saatavilla</label><br>
							</div>
							<div class="input-field col s3">
								<input name="taytesaatavilla" type="radio" id="eisaatavilla"
									value="0"
									<c:if test="${tayte.saatavilla == false }"> checked</c:if>>
								<label for="eisaatavilla">Ei saatavilla</label>
							</div>
						</div>
						<div class="row">
							<a class="btn waves-effect waves-light btn-large red lighten-2"
								href="${pathi }/hallinta">Peruuta</a>
							<button class="btn waves-effect waves-light btn-large"
								type="submit" name="action" value="paivitatayte">Päivitä
								tiedot</button>
				</form>
			</div>
			<div class="row">
				<div class="col s12">
					<h3>Tätä täytettä käyttää ${fn:length(pizzat) } pizzaa</h3>
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
				</div>

			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>