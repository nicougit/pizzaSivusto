<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori - Virhe</title>
<jsp:include page="WEB-INF/head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="WEB-INF/header.jsp"></jsp:include>
	<h1>Virhe!</h1>

	<div class="row">
		<div class="col s12 center-align errori">
	Hakemaasi sivua ei löytynyt.
		</div>
	</div>

	<jsp:include page="WEB-INF/footer.jsp"></jsp:include>

</body>
</html>