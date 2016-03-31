<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pizzat</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="row" id="pizza-h">
		<div class="col s12">
			<h1 class="brand-logo">Pizzat</h1>

			<c:choose>
				<c:when test="${empty pizzat}">
					<div class="errori center-align">Listalla ei ole pizzoja, tai
						niitä ei saatu noudettua tietokannasta.</div>
				</c:when>
				<c:otherwise>

					<c:forEach items="${pizzat}" var="pizza">


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
											<a href="#"><i class="material-icons right col s2">shopping_cart</i></a>
											<br>
										</div>
									</div>
								</div>
							</div>
						


						<div class="col s10 m6 hide-on-large-only">
							<div class="card red lighten-2">
								<div class="card-content white-text">
								
									<span class="card-title col s12"> ${pizza.nimi}</span>
									<div id="fixedhintam">
									<fmt:formatNumber
										type="number" minFractionDigits="2" maxFractionDigits="2"
										value="${pizza.hinta }"></fmt:formatNumber> €
									</div>
										
									<div class="col s8">
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
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>
</div>




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