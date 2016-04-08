<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello &#233; Fiori - Ostoskori</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="headertext">
		<h1>Ostoskori</h1>
		<p class="flow-text ostoskori-yhteismaara"></p>
	</div>
	<div class="row" id="main-content">
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
		<div class="row">
			<div class="col s12 right-align ostoskorinapit">
				<button
					class="btn waves-effect waves-light orange lighten-1 ostoskori-tyhjennysnappi"
					type="button" onClick="tyhjennaOstoskori()">
					<i class="material-icons left">clear</i>Tyhjennä
				</button> 
				<a class="btn waves-effect waves-light ostoskori-tilausnappi"
					href="<c:url value='/ostoskori'/>"><i
					class="material-icons left">shopping_cart</i>Tilaamaan</a>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>