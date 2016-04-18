<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Menu</title>
<jsp:include page="head-include.jsp"></jsp:include>
<link href='https://fonts.googleapis.com/css?family=Martel:200'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Raleway:200italic' rel='stylesheet' type='text/css'>
<script>
// Törkeesti intternetistä 99% kopioitu skripti
// Scrollaa smoothisti pizza / juomat nappeja klikatessa
// Source:
// https://css-tricks.com/snippets/jquery/smooth-scrolling/
$(function() {
	  $('a[href*="#"]:not([href="#"], [href="#!"])').click(function() {
	    if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
	      var target = $(this.hash);
	      target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
	      if (target.length) {
	        $('html, body').animate({
	          scrollTop: target.offset().top -80
	        }, 500);
	        return false;
	      }
	    }
	  });
	});
</script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="headertext">
		<h1 class="firmanlogo hide-on-med-and-down">Castello é Fiori</h1>
		<p class="flow-text">Täydellinen pizzataikina syntyy
			hitaalla nostatuksella. Päällä tomaattikastiketta, joka on
			valmistettu salaisella sukureseptillä. Kokonaisuuden kruunaa aito
			oikea mozzarella. Lisäksi käytämme pizzoissamme vain tuoreita ja
			laadukkaita raaka-aineita, tarkoin valituilta tuottajilta.</p>
	</div>
	<div class="row" id="main-content">
		<c:choose>
			<c:when test="${empty pizzat}">
				<div class="errori center-align">Listalla ei ole pizzoja, tai
					niitä ei saatu noudettua tietokannasta.</div>
			</c:when>
			<c:otherwise>
				<h2 id="pizzat" class="menu-pizzaname menu-title">Pizzat</h2>
				<br>
				<c:forEach items="${pizzat}" var="pizza">
					<div class="center-align">
						<c:set var="pizzahinta">
							<fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${pizza.hinta }"></fmt:formatNumber>
						</c:set>
						<h3 class="menu-pizzaname"><c:out value="${pizza.nimi }"></c:out>
							<span class="menu-pizzahinta">${fn:replace(pizzahinta, ".", ",") }
								€</span>
						</h3>
						<span class="menu-pizzataytteet"> <c:forEach
								items="${pizza.taytteet }" var="tayte" varStatus="status">
								<c:out value="${tayte.nimi }"></c:out>
							<c:if
									test="${fn:length(pizza.taytteet) > status.count }">, </c:if>
							</c:forEach>
						</span>
						<p class="menu-pizzakuvaus">"<c:out value="${pizza.kuvaus }"></c:out>"</p>
						<br>
						<button class="btn waves-effect waves-light ostoskorinappi" type="button" onClick="lisaaOstoskoriin(<c:out value="${pizza.id}"></c:out>,'pizza')">Lisää
							ostoskoriin</button>
						<br> <br>
						<div class="divider"></div>
					</div>
					<br>
				</c:forEach>

				<h2 id="juomat" class="menu-pizzaname menu-title">Juomat</h2>
				<br>
						<c:forEach items="${juomat}" var="juoma">
					<div class="center-align">
						<c:set var="juomahinta">
							<fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${juoma.hinta }"></fmt:formatNumber>
						</c:set>
						<h3 class="menu-pizzaname"><c:out value="${juoma.nimi }"></c:out>
							<span class="menu-pizzahinta">${fn:replace(juomahinta, ".", ",") }
								€</span>
						</h3>
						<span class="menu-pizzataytteet"><fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${juoma.koko }"></fmt:formatNumber> l</span>
						<p class="menu-pizzakuvaus">"<c:out value="${juoma.kuvaus }"></c:out>"</p>
						<br>
						<button class="btn waves-effect waves-light ostoskorinappi" type="button">Lisää
							ostoskoriin</button>
						<br> <br>
						<div class="divider"></div>
					</div>
					<br>
				</c:forEach>

			</c:otherwise>
		</c:choose>

	</div>


	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>