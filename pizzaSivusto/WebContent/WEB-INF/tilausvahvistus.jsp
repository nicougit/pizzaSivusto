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
		<h1>Tilausvahvistus</h1>
	</div>
	<div class="row" id="main-content">
		<c:choose>
		<c:when test="${empty tilaus }">
		<h2 class="errori">Tapahtu jotain hirveetä</h2>
		Saavuit tänne vaikka tilausta ei oo setattu.
		</c:when>
		<c:otherwise>
		<h2>Tilauksen tiedot</h2>
		Toimitustapa: <c:out value="${tilaus.toimitustapa }"></c:out><br>
		Maksutapa: <c:out value="${tilaus.maksutapa }"></c:out><br>
		Kokonaishinta: <c:out value="${tilaus.kokonaishinta } e"></c:out><br>
		<c:if test="${not empty tilaus.osoite }">Osoite: <c:out value="${tilaus.osoite.osoite }, ${tilaus.osoite.postitmp }"></c:out><br></c:if>
		<c:if test="${not empty tilaus.lisatiedot }">Lisätiedot: <c:out value="${tilaus.lisatiedot }"></c:out><br></c:if>
		<br>
		<h3>Tilatut tuotteet:</h3>
		Pizzat:<br><br>
		<c:forEach items="${tilaus.pizzat }" var="pizza">
		<c:out value="${pizza.nimi } -"></c:out>
		<c:if test="${pizza.oregano == true }">Oregano </c:if>
		<c:if test="${pizza.valkosipuli == true }">Valkosipuli </c:if>
		<c:if test="${pizza.gluteeniton == true }">Gluteeniton </c:if>
		<c:if test="${pizza.vl == true }">Vähälaktoosinen </c:if>
		<br>
		</c:forEach>
		<br>
		Juomat:<br><br>
		<c:forEach items="${tilaus.juomat }" var="juoma">
		<c:out value="${juoma.nimi }"></c:out><br>
		</c:forEach>
		</c:otherwise>
		</c:choose>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>