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
		<h1>Tilausjärjestelmä beta v01</h1>
	</div>
	<div class="row" id="main-content">

		<div>
			<p>
				<b><c:out value="${kayttaja.etunimi } ${kayttaja.sukunimi }"></c:out></b>				
			<p>
		</div>
		
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

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>