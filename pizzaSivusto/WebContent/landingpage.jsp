<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Tervetuloa</title>
<jsp:include page="WEB-INF/head-include.jsp"></jsp:include>
<script 
src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
            google.maps.event.addDomListener(window, 'load', init);
        
            function init() {
                // Lisää optionsseja löytyy:
                // https://developers.google.com/maps/documentation/javascript/reference#MapOptions
                var mapOptions = {
                	disableDefaultUI: true, //poistetaan zoominapit ja muut
                    zoom: 15, //alotuszoomi kartalle
                    center: new google.maps.LatLng(60.9970927, 24.46920160000002), //ravintolan sijainti
                    //Tyylit kartalle
                    styles: [{"featureType":"administrative","elementType":"all","stylers":[{"visibility":"on"},{"lightness":33}]},{"featureType":"landscape","elementType":"all","stylers":[{"color":"#f2e5d4"}]},{"featureType":"poi.park","elementType":"geometry","stylers":[{"color":"#c5dac6"}]},{"featureType":"poi.park","elementType":"labels","stylers":[{"visibility":"on"},{"lightness":20}]},{"featureType":"road","elementType":"all","stylers":[{"lightness":20}]},{"featureType":"road.highway","elementType":"geometry","stylers":[{"color":"#c5c6c6"}]},{"featureType":"road.arterial","elementType":"geometry","stylers":[{"color":"#e4d7c6"}]},{"featureType":"road.local","elementType":"geometry","stylers":[{"color":"#fbfaf7"}]},{"featureType":"water","elementType":"all","stylers":[{"visibility":"on"},{"color":"#acbcc9"}]}]
                };

                
                var mapElement = document.getElementById('map');

                
                var map = new google.maps.Map(mapElement, mapOptions);

                //marker
                var marker = new google.maps.Marker({
                    position: new google.maps.LatLng(60.9970927, 24.46920160000002),
                    map: map,
                    title: 'Castello é Fiori'
                });
            }
</script>
</head>
<body>
	<jsp:include page="WEB-INF/header.jsp"></jsp:include>
	<div class="headertext">
		<h1 class="firmanlogo hide-on-med-and-down">Castello é Fiori</h1>
		<p class="flow-text">Olemme Hämeenlinnalainen perheyritys, joka on
			aloittanut toimintansa jo 50-luvulla. Me Castello é Fiorissa haluamme
			tarjota jokaiselle jotain unohtumatonta. Taidolla, tunteella ja
			rakkaudella teemme Teille Hämeenlinnan maukkaimmat italialaiset
			pizzat.</p>
	</div>
	<div class="row">
		<div class="col s12 center-align">
			<a class="waves-effect waves-light btn-large"
				href="<c:url value='/pizza'/>"><i class="material-icons right">restaurant_menu</i>Menu
			</a>
		</div>
	</div>
	<br>
	<div class="row" id="main-content">
		<div class="col s12 l4 center-align">
			<h2>Aukioloajat</h2>
			<p class="flow-text">Arkisin 10 - 21 <br>Lauantaisin 10 - 22<br>Sunnuntaisin 12 - 21</p>
		</div>
		<div class="col s12 l4 center-align">
			<h2>Yhteystiedot</h2>
			<p class="flow-text">Raatihuoneenkatu 6<br> 13100 Hämeenlinna<br><br>Puhelin 023 231 2342</p>
		</div>
		<div class="col s12 l4 center-align">
			<h2>Kartta</h2><!--<p class="flow-text">Tähän karttakuva</p>-->
			
			<!-- kartan kooksi määritetty 300x200, vaihdettavissa style.css map id:n alta -->
			<div id="map"></div>
			  
		</div>
	</div>
	</div>
	<div class="center-align pienifontti footeri">
	<br>
	By Reptile Mafia 2016
	<br><br>
	</div>
	</body>
</html>
