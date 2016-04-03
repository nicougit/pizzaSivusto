<%@page import="bean.Kayttaja"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Castello E Fiori - Pääsy evätty</title>
<jsp:include page="head-include.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="row headertext">
	<h1>Pääsy evätty</h1>
	<p class="flow-text">
			<c:choose>
				<c:when test="${not empty kayttaja }">
					Sinulla ei ole oikeutta tälle sivulle. Käyttäjätasosi on ${kayttaja.tyyppi }.
				</c:when>
				<c:otherwise>
					Sinulla ei ole oikeutta tälle sivulle. Et ole kirjautunut sisään.
				</c:otherwise>
			</c:choose>
			</p>
									<a class="waves-effect waves-light btn btn-large"
					href="<c:url value='/'/>">Takaisin etusivulle</a>
		</div>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>