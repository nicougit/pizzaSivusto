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
<link href="css/experimental.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="WEB-INF/header.jsp"></jsp:include>
	<div class="headertext">
		<div class="row">
			<div class="col s12">
				<h1>Virhe 404</h1>
				<p class="flow-text">Hakemaasi sivua ei löytynyt!</p>
				<a class="waves-effect waves-light btn btn-large"
					href="<c:url value='/'/>">Takaisin etusivulle</a>
			</div>
		</div>
	</div>
	<jsp:include page="WEB-INF/footer.jsp"></jsp:include>

</body>
</html>