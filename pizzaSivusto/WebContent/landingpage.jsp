<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori - Tervetuloa</title>
<jsp:include page="WEB-INF/head-include.jsp"></jsp:include>
<link href="css/experimental.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="WEB-INF/header.jsp"></jsp:include>
	<div id="row">
		<div class="col s12 center-align whitetext valigni">
			<h1 class="firmanlogo hide-on-med-and-down">Castello é Fiori</h1>
			<p class="flow-text">Olemme pieni 1950-luvulla toimintansa
				aloittanut Hämeenlinnassa toimiva perheyritys. Viime vuosina olemme
				erikoistunut pizzoihin. Panostamme laatuun, joten hankimme kaikki
				raaka-aineemme tarkkaan harkituilta toimittajilta.</p>

		</div>
	</div>

	<jsp:include page="WEB-INF/footer.jsp"></jsp:include>
</body>
</html>