<%@page import="bean.Kayttaja"%>
<%@page import="login.KayttajaLista"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori Hallintasivu</title>
<jsp:include page="head-include.jsp"></jsp:include>
<script src="build/react.js"></script>
<script src="build/react-dom.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/babel-core/5.8.23/browser.min.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<!-- Sisältö javascriptillä tänne -->
	<div id="hallintasisalto">
		<noscript>
			<div class="row headertext">
				<h1>Hallinta</h1>
				<p class="flow-text">Tämä sivu vaatii JavaScriptin toimiakseen.
				</p>
			</div>
		</noscript>
	</div>
	<script src="js/hallinta-react.js" type="text/babel"></script>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>