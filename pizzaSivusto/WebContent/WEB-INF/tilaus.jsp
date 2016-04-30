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
	<form method="post" action="tilaus">
	<div class="row">
	<div class="col s12">
	<h2>Tilauksen tiedot</h2>
	<div class="row">
	<div class="col s12 m4 l4">
	<h5 class="tilaustitle">Tilaaja</h5>
	<c:out value="${kayttaja.etunimi }"></c:out> <c:out value="${kayttaja.sukunimi }"></c:out>
	</div>
	<div class="col s12 m4 l4">
	<h5 class="tilaustitle">Sähköpostiosoite</h5>
	<c:out value="${kayttaja.tunnus }"></c:out>
	</div>
	<div class="col s12 m4 l4">
	<h5 class="tilaustitle">Puhelinnumero</h5>
	<c:choose>
	<c:when test="${not empty kayttaja.puhelin }"><c:out value="${kayttaja.puhelin }"></c:out></c:when>
	<c:otherwise>
	&nbsp;
	</c:otherwise>
	</c:choose>
	</div>
	<div class="col s12 m4 sl4" id="toimitustapadiv">
	<h5 class="tilaustitle">Toimitustapa</h5>
	<input type="radio" name="tilaustapa" value="0" id="kotiinkuljetus" checked>
	<label for="kotiinkuljetus">Kotiinkuljetus (Lisämaksu 5€)</label><br>
	<input type="radio" name="tilaustapa" value="1" id="nouto">
	<label for="nouto">Nouto ravintolasta</label><br>
	<input type="radio" name="tilaustapa" value="2" id="paikanpaalla">
	<label for="paikanpaalla">Ruokailu ravintolassa</label>
	</div>
	<div class="col s12 m3 sl3">	
	<h5 class="tilaustitle">Tilauksen maksutapa</h5>
	<input type="radio" name="maksutapa" value="0" id="kateinen" checked>
	<label for="kateinen">Käteinen</label><br>
	<input type="radio" name="maksutapa" value="1" id="luottokortti">
	<label for="luottokortti">Luottokortti</label><br>
	<input type="radio" name="maksutapa" value="2" id="verkkomaksu">
	<label for="verkkomaksu">Verkkomaksu</label><br>
	</div>
	<div class="col s12 m5 sl5">
	<h5 class="tilaustitle">Tilauksen lisätiedot</h5>
	<textarea id="lisatiedot" class="materialize-textarea" name="lisatiedot" length="250"></textarea>
	</div>
	<div class="col s12" id="osoitediv">
	<h5 class="tilaustitle">Toimitusosoite</h5>
	<c:choose>
	<c:when test="${empty kayttaja.osoitteet }">
	<p class="flow-text center-align">Et ole vielä määrittänyt osoitettasi. Lisää osoite alla olevasta painikkeesta.</p>
	</c:when>
	<c:otherwise>
	<ul class="collection">
	<c:forEach items="${kayttaja.osoitteet }" var="osoite" varStatus="status">
    <li class="collection-item">
      <input type="radio" name="osoitevalinta" id="<c:out value="${osoite.osoiteid }"></c:out>cb" value="<c:out value="${osoite.osoiteid }"></c:out>" <c:if test="${status.index == 0 }">checked</c:if>>
      <label class="osoitelabel" for="<c:out value="${osoite.osoiteid }"></c:out>cb">
      <c:out value="${osoite.osoite }"></c:out>, 
      <c:out value="${osoite.postinro }"></c:out> <c:out value="${osoite.postitmp }"></c:out>
      </label>
       <a href="?poista-osoite=<c:out value="${osoite.osoiteid }"></c:out>" class="secondary-content taytteenpoisto tooltipped" data-position="left" data-delay="500" data-tooltip="Poista osoite"><i class="material-icons">delete</i></a>
    </li>
	</c:forEach>
	</ul>
	</c:otherwise>
	</c:choose>
	<a class="btn waves-effect waves-light modal-trigger right" href="#osoitemodal"><i class="material-icons left">add</i>Lisää osoite</a>
	</div>
	</div>
	</div>
	<div class="col s12">
		<h2>Tilattavat tuotteet</h2>
		<p class="flow-text center-align">Voit lisätä tai poistaa tuotteita palaamalla takaisin ostoskoriin</p>
		<table class="bordered">
			<thead>
				<tr>
					<th>Tuotteen nimi</th>
					<th></th>
					<th class="center">Hinta</th>
				</tr>
			</thead>
			<tbody>
			<c:set var="yhteishinta" value="0"></c:set>
			<c:forEach items="${ostoskoriPizzat }" var="pizza" varStatus="status">
			<c:set var="hinta"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber></c:set>
			<tr class="tilaus-nobottom">
			<td>
			<span class="strong-id"><c:out value="${pizza.nimi }"></c:out></span>
			<span class="pienifontti"> | 
			<c:set var="taytteita" value="${fn:length(pizza.taytteet) }"></c:set>
			<c:forEach items="${pizza.taytteet }" var="tayte" varStatus="taytestatus">
			<c:out value="${tayte.nimi }"></c:out><c:if test="${taytestatus.count < taytteita }">, </c:if>
			</c:forEach>
			</span>
			</td>
			<td>
			</td>
			<td class="center">${fn:replace(hinta, ".", ",") } €</td>
			</tr>
			<tr>
			<td class="tilaus-tietorivi" colspan="3">
			<input type="checkbox" name="pizzatieto" id="<c:out value="${status.index }-oregano"></c:out>" value="<c:out value="${status.index }-oregano"></c:out>">
			<label class="tilaus-tietolabel" for="<c:out value="${status.index }-oregano"></c:out>">Oregano</label>
			<input type="checkbox" name="pizzatieto" id="<c:out value="${status.index }-valkosipuli"></c:out>" value="<c:out value="${status.index }-valkosipuli"></c:out>">
			<label class="tilaus-tietolabel" for="<c:out value="${status.index }-valkosipuli"></c:out>">Valkosipuli</label>
			<input type="checkbox" name="pizzatieto" id="<c:out value="${status.index }-gluteeniton"></c:out>" value="<c:out value="${status.index }-gluteeniton"></c:out>">
			<label class="tilaus-tietolabel" for="<c:out value="${status.index }-gluteeniton"></c:out>">Gluteeniton</label>
			<input type="checkbox" name="pizzatieto" id="<c:out value="${status.index }-vl"></c:out>" value="<c:out value="${status.index }-vl"></c:out>">
			<label class="tilaus-tietolabel" for="<c:out value="${status.index }-vl"></c:out>">Laktoositon</label>
			</td>
			</tr>
			<c:set var="yhteishinta" value="${yhteishinta + pizza.hinta }"></c:set>
			</c:forEach>
			<c:forEach items="${ostoskoriJuomat }" var="juoma">
			<c:set var="koko" value="${juoma.koko }"></c:set>
			<c:set var="hinta"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${juoma.hinta }"></fmt:formatNumber></c:set>
			<tr>
			<td><span class="strong-id"><c:out value="${juoma.nimi }"></c:out></span> <span class="pienifontti"> | ${fn:replace(koko, ".", ",") }l juoma</span></td>
			<td></td>
			<td class="center">${fn:replace(hinta, ".", ",") } €</td>
			<c:set var="yhteishinta" value="${yhteishinta + juoma.hinta }"></c:set>
			</tr>
			</c:forEach>
			<tr class="tilaus-nobottom">
			<c:set var="yhteishinta"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${yhteishinta }"></fmt:formatNumber></c:set>
			<td colspan="2" class="strong-id right-align">Tuotteiden yhteishinta</td>
			<td class="center strong-id">${fn:replace(yhteishinta, ".", ",") } €</td>
			</tr>
			</tbody>
		</table>
		<br>
	</div>
	</div>
	<a class="btn waves-effect waves-light left red lighten-2" href="<c:url value='/ostoskori'/>">Palaa ostoskoriin</a>
	<button class="btn waves-effect waves-light right" type="submit" name="action" value="tilausvahvistukseen">Tilaa</button>
	<br><br>
	</form>
			<div id="osoitemodal" class="modal">
		<form id="osoiteformi" action="tilaus" method="post">
		<div class="modal-content center-align">
			<h4>Lisää osoite</h4>
			<div class="col s12 m12 l10 offset-l1">
			<div class="row">
			<div class="input-field col s12 m6 l6">
			<input type="text" name="lahiosoite" id="lahiosoite">
			<label for="lahiosoite">Lähiosoite</label>
			</div>
			<div class="input-field col s12 m3 l3">
			<input type="text" name="postinumero" id="postinumero">
			<label for="postinumero">Postinumero</label>
			</div>
			<div class="input-field col s12 m3 l3">
			<input type="text" name="postitoimipaikka" id="postitoimipaikka">
			<label for="postitoimipaikka">Postitoimipaikka</label>
			</div>
			</div>
			</div>
		<a href="#!" class="modal-action modal-close waves-effect waves-light btn red lighten-2" >Sulje</a>
		<button class="btn waves-effect waves-light" type="submit" name="action" value="lisaaosoite"><i class="material-icons left">add</i>Lisää osoite</button>
		</div>
		</form>
	</div>
	</div>
	<script src="js/tilaus.js" type="text/javascript"></script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>