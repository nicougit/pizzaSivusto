<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Tilaus</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="headertext">
		<h1>Tilaus</h1>
	</div>
	<div class="row" id="main-content">
	<form>
	<div class="row">
	<div class="col s12">
	<h2>Asiakastiedot</h2>
	<div class="row">
	<div class="col s12 m4 l4">
	<h5 class="tilaustitle">Nimi</h5>
	<c:out value="${kayttaja.etunimi }"></c:out> <c:out value="${kayttaja.sukunimi }"></c:out>
	</div>
	<div class="col s12 m4 l4">
	<h5 class="tilaustitle">Sähköpostiosoite</h5>
	<c:out value="${kayttaja.tunnus }"></c:out>
	</div>
	<div class="col s12 m4 l4">
	<h5 class="tilaustitle">Puhelinnumero</h5>
	<c:out value="${kayttaja.puhelin }"></c:out>
	</div>
	<div class="col s12">
	<h5 class="tilaustitle">Toimitusosoite</h5>
	<c:choose>
	<c:when test="${empty kayttaja.osoitteet }">
	Et ole vielä lisännyt yhtään osoitetta. Lisää osoite alla olevasta painikkeesta.<br><br>
	</c:when>
	<c:otherwise>
	<ul class="collection">
	<c:forEach items="${kayttaja.osoitteet }" var="osoite">
    <li class="collection-item">
      <input type="radio" name="osoitevalinta" id="<c:out value="${osoite.osoiteid }"></c:out>cb" value="<c:out value="${osoite.osoiteid }"></c:out>">
      <label class="osoitelabel" for="<c:out value="${osoite.osoiteid }"></c:out>cb">
      <c:out value="${osoite.osoite }"></c:out>, 
      <c:out value="${osoite.postinro }"></c:out> <c:out value="${osoite.postitmp }"></c:out>
      </label>
      <a href="#!" class="secondary-content taytteenpoisto tooltipped" data-position="left" data-delay="500" data-tooltip="Poista osoite"><i class="material-icons">delete</i></a>
    </li>
	</c:forEach>
	</ul>
	</c:otherwise>
	</c:choose>
	<a class="btn waves-effect waves-light modal-trigger" href="#osoitemodal"><i class="material-icons left">add</i>Lisää osoite</a>
		<div id="osoitemodal" class="modal">
		<div class="modal-content center-align">
			<h4>Lisää osoite</h4>
			asdasdasdasd<br><br>
			
		<a href="#!" class="modal-action modal-close waves-effect waves-light btn red lighten-2" >Sulje</a>
		<a class="btn waves-effect waves-light" href="#!"><i class="material-icons left">add</i>Lisää osoite</a>
		</div>
	</div>
	</div>
	<div class="col s12">
	<h5 class="tilaustitle">Tilauksen maksutapa</h5>
	<ul class="collection">
	<li class="collection-item">
	<input type="radio" name="maksutapa" value="0" id="kateinen" checked>
	<label class="osoitelabel" for="kateinen">Käteinen</label>
	</li><li class="collection-item">
	<input type="radio" name="maksutapa" value="1" id="luottokortti">
	<label class="osoitelabel" for="luottokortti">Luottokortti</label>
	</li><li class="collection-item">
	<input type="radio" name="maksutapa" value="2" id="debitkortti">
	<label class="osoitelabel" for="debitkortti">Debit-kortti</label>
	</li><li class="collection-item">
	<input type="radio" name="maksutapa" value="3" id="oravannahat">
	<label class="osoitelabel" for="oravannahat">Oravannahat</label>
	</li>
	</ul>
	</div>
	</div>
	</div>
	<div class="col s12">
		<h2>Tilattavat tuotteet</h2>
		<table class="ostoskori-table striped">
			<thead>
				<tr>
					<th>Tuotteen nimi</th>
					<th></th>
					<th class="center">Hinta</th>
				</tr>
			</thead>
			<tbody class="ostoskori-tbody">
			</tbody>
		</table>
		<br>
	</div>
	</div>
	</form>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>