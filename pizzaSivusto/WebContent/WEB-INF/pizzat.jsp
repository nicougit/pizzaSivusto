<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu</title>
<jsp:include page="head-include.jsp"></jsp:include>
<link href="css/experimental.css" rel="stylesheet" type="text/css" />
</head>
<body class="covertausta whitetext">
	<jsp:include page="header.jsp"></jsp:include>



	<div class="section" id="pizzat">
			<h2 class="brand-logo shadow">Pizzat</h2>
		</div>

	<c:choose>
		<c:when test="${empty pizzat}">
			<div class="errori center-align">Listalla ei ole pizzoja, tai
				niitä ei saatu noudettua tietokannasta.</div>
		</c:when>
		<c:otherwise>
		
		
			<div class="row" id="main-content" id="pizzat">
				<c:forEach items="${pizzat}" var="pizza">

					<div class="grey darken-2 col s12 m12 center tuotediv">
						<h3 class="shadow">${pizza.nimi}</h3>
							<p class="flow-text"> Tämä pizza on valmistettu ainoastaan parhaista raaka-aineista, jotka on hankittu 
						paikallisilta, vastuullisilta yrittäjiltä. Pizza on mehevä ja ruokaisa, sopuisa. </p>
						
						<div class="row">
							<c:forEach items="${pizza.taytteet}" var="pizzantayte"
								varStatus="taytecounter">
											${pizzantayte.nimi}
											<c:if
									test="${fn:length(pizza.taytteet) > taytecounter.count }">, </c:if>
							</c:forEach>
						</div>
						<p class="strong-id">
							<fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber>
							€
							<a href="#" class="waves-effect waves-light btn left-margin"><i class="material-icons">shopping_cart</i></a>
						</p>
					
					</div>
				</c:forEach>
				</div>
				
				<div class="section" id="juomat"></div>
	
	<div class="section">
			<h2 class="brand-logo shadow" style="padding-top:9pt;">Juomat</h2>
		</div>
	
	<div class="row" id="main-content">
				<c:forEach items="${pizzat}" var="pizza">

					<div class="grey darken-2 col s12 m12 center tuotediv">
						<h3 class="shadow">${pizza.nimi}</h3>
						<p class="flow-text"> Tämä pizza on valmistettu ainoastaan parhaista raaka-aineista, jotka on hankittu 
						paikallisilta, vastuullisilta yrittäjiltä. Pizza on mehevä ja ruokaisa, sopuisa. </p>
						<div class="row">
							<c:forEach items="${pizza.taytteet}" var="pizzantayte"
								varStatus="taytecounter">
											${pizzantayte.nimi}
											<c:if
									test="${fn:length(pizza.taytteet) > taytecounter.count }">, </c:if>
							</c:forEach>
						</div>
						<p class="strong-id">
							<fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber>
							€
							<a href="#" class="waves-effect waves-light btn left-margin"><i class="material-icons">shopping_cart</i></a>
						</p>
							
					</div>
				</c:forEach>
				
				</div>
		</c:otherwise>
	</c:choose>


	<%--
						<div class="col s10 m4 hide-on-med-and-down">
							<div class="card medium red lighten-2">
								<div class="card-image">
									<img src="img/pizza.jpg">
								</div>
								<div class="card-content white-text">
									<span class="card-title col s12">${pizza.nimi}</span>
									<div id="fixedhinta">
									<fmt:formatNumber
										type="number" minFractionDigits="2" maxFractionDigits="2"
										value="${pizza.hinta }"></fmt:formatNumber> €
									</div>

										<div class="col s9">
											<c:forEach items="${pizza.taytteet}" var="pizzantayte"
												varStatus="taytecounter">

											${pizzantayte.nimi}
											<c:if
													test="${fn:length(pizza.taytteet) > taytecounter.count }">, </c:if>
											</c:forEach>
										</div>
										<div class="card-action">
											<a href="#"><i class="material-icons right">shopping_cart</i></a>
											<br>
										</div>
									</div>
								</div>
							</div>
						


						<div class="col s12 m6 hide-on-large-only">
							<div class="card red lighten-2 ">
								<div class="card-content white-text">
								
									<span class="card-title col s10"> ${pizza.nimi}</span>
									<div id="fixedhintam">
									<fmt:formatNumber
										type="number" minFractionDigits="2" maxFractionDigits="2"
										value="${pizza.hinta }"></fmt:formatNumber> €
									</div>
										
									<div class="col s9">
										<c:forEach items="${pizza.taytteet}" var="pizzantayte"
											varStatus="taytecounter">

											${pizzantayte.nimi}
											<c:if
												test="${fn:length(pizza.taytteet) > taytecounter.count }">, </c:if>
										</c:forEach>
									</div>
									<div class="card-action">
										<a href="#"><i class="material-icons right col s2">shopping_cart</i></a>
									</div>
								</div>
							</div>
						</div>
						
						 --%>






	<%-- 
				<div class="row">
					<div class="col s12">
						<a class="waves-effect waves-light btn hide-on-med-and-down"
							onclick="window.print();"> <i class="material-icons left">print</i>
							Tulosta
						</a> <a href="#" class="waves-effect btn"> <i
							class="material-icons left">navigation</i> Alkuun
						</a>
					</div>
					</div>
					 --%>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>